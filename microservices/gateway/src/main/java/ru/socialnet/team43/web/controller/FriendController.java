package ru.socialnet.team43.web.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.FriendClient;
import ru.socialnet.team43.dto.FriendDto;
import ru.socialnet.team43.dto.PersonDto;;
import ru.socialnet.team43.util.ControllerUtil;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendController {

    private final FriendClient friendClient;

    private final ControllerUtil util;

    @GetMapping("/count")
    public long getFriendCount(@AuthenticationPrincipal UserDetails userDetails) {
        return friendClient.getFriendsCount(userDetails.getUsername());
    }

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

    @PostMapping("/{id}/request")
    public ResponseEntity<FriendDto> friendRequest(@PathVariable Long id,
                                                   @AuthenticationPrincipal UserDetails userDetails){
        log.info("friendRequest with id: {} ", id);
        String email = userDetails.getUsername();

        ResponseEntity<FriendDto> inputResponseEntity = friendClient.friendRequest(id, email);
        return util.createNewResponseEntity(inputResponseEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long id,
                                             @AuthenticationPrincipal UserDetails userDetails){
        log.info("delete friend with id: {} ", id);
        String email = userDetails.getUsername();

        return friendClient.deleteFriend(id, email);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<FriendDto> approveFriendRequest(@PathVariable Long id,
                                                          @AuthenticationPrincipal UserDetails userDetails){
        log.info("approveFriendRequest with id: {} ", id);
        String email = userDetails.getUsername();
        ResponseEntity<FriendDto> inputResponseEntity = friendClient.approveFriendRequest(id, email);
        return util.createNewResponseEntity(inputResponseEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FriendDto> getFriendById(@PathVariable Long id,
                                                   @AuthenticationPrincipal UserDetails userDetails){
        log.info("getFriendById with id: {} ", id);
        String email = userDetails.getUsername();
        ResponseEntity<FriendDto> inputResponseEntity = friendClient.getFriendsById(id, email);
        return util.createNewResponseEntity(inputResponseEntity);
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<FriendDto> subscribe(@PathVariable Long id,
                                                   @AuthenticationPrincipal UserDetails userDetails){
        log.info("subscribe with id: {} ", id);
        String email = userDetails.getUsername();

        ResponseEntity<FriendDto> inputResponseEntity = friendClient.subscribe(id, email);
        return util.createNewResponseEntity(inputResponseEntity);
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
        log.debug("""

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
