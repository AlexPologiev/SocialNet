package ru.socialnet.team43.web.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.FriendClient;
import ru.socialnet.team43.dto.FriendDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.enums.FriendshipStatus;
import ru.socialnet.team43.dto.enums.StatusCode;
import ru.socialnet.team43.util.ControllerUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendController {

    /** Клиент подключения */
    private final FriendClient friendClient;
    /** Утилитный класс для обертки ответа */
    private final ControllerUtil util;

    /**
     * Эндпоинт получения количества друзей
     * @return количество друзей
     */
    @GetMapping("/count")
    public long getFriendCount(@AuthenticationPrincipal UserDetails userDetails) {
        return friendClient.getFriendsCount(userDetails.getUsername());
    }

    /**
     * Эендпоинт получения списка рекомендованных друзей
     * @return список рекомендованных друзей
     */
    @GetMapping("/recommendations")
    public ResponseEntity<List<FriendDto>> getRecommendations(@AuthenticationPrincipal UserDetails userDetails) {
        return util.createNewResponseEntity(friendClient.getRecommendations(userDetails.getUsername()));

    }

    @GetMapping("")
    public ResponseEntity<?> searchFriends(@RequestParam String statusCode,
                                           @RequestParam(defaultValue = "") String firstName,
                                           @RequestParam(defaultValue = "0") String ageFrom,
                                           @RequestParam(defaultValue = "99") String ageTo,
                                           @RequestParam(defaultValue = "") String country,
                                           @RequestParam(defaultValue = "") String city,
                                           @AuthenticationPrincipal UserDetails userDetails,
                                           Pageable page){

        if (userDetails == null){
            log.info("userDetails is null.");
            return ResponseEntity.badRequest().build();
        }
        String email = userDetails.getUsername();
        logInfoSearchFriends(statusCode, firstName, ageFrom, ageTo, country, city, email, page);

        if(firstName.equals("")
                && ageFrom.equals("0")
                && ageTo.equals("99")
                && country.equals("")
                && city.equals("")){
            ResponseEntity<List<FriendDto>> inputResponseEntity =
                    friendClient.searchFriendsByStatus(statusCode, email, page);
            return ResponseEntity.ok(
                    new PageImpl<>(inputResponseEntity.getBody(),
                            page,
                            inputResponseEntity.getBody().size()));
        }

        ResponseEntity<List<PersonDto>> inputResponseEntity =
                friendClient.searchFriends(statusCode, firstName, ageFrom, ageTo, country, city, email, page);

        return ResponseEntity.ok(new PageImpl<>(inputResponseEntity.getBody(), page, inputResponseEntity.getBody().size()));
    }
    @ExceptionHandler(FeignException.class)
    private ResponseEntity<Void> handler(FeignException ex) {
        log.warn("Error in the gateway {}", ex.getMessage());
        return ResponseEntity.status(ex.status()).build();
    }

    private void logInfoSearchFriends(String statusCode,
                                      String firstName,
                                      String ageFrom,
                                      String ageTo,
                                      String country,
                                      String city,
                                      String email,
                                      Pageable page){
        log.info("getAllFriends");
        log.info("""

                         statusCode: {},
                         firstName: {},
                         ageFrom: {},
                         ageTo: {},
                         country: {},
                         city: {},
                         email: {},
                         page {}""",
                statusCode,
                firstName,
                ageFrom,
                ageTo,
                country,
                city,
                email,
                page);
    }

}
