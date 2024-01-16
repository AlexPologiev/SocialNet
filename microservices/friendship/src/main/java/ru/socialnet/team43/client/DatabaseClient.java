package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.socialnet.team43.dto.FriendDto;
import ru.socialnet.team43.dto.PersonDto;


import java.util.List;

@FeignClient(name = "databaseClient", dismiss404 = true, url = "${database.url}" + "/friends")
public interface DatabaseClient {

    /**
     * Получение количества друзей
     * @param email эл/почта пользователя
     * @return количество друзей
     */
    @GetMapping("/count")
    long getFriendsCount(@RequestParam String email);

    /**
     * Получение списка рекомендованных друзей
     * @param email эл/почта пользователя
     * @return список рекомендуемых друзей
     */
    @GetMapping("/recommendations")
    ResponseEntity<List<FriendDto>> getRecommendations(@RequestParam String email);

    @GetMapping("")
    List<PersonDto> searchFriends(@RequestParam String statusCode,
                                  @RequestParam(defaultValue = "") String firstName,
                                  @RequestParam(defaultValue = "0") String ageFrom,
                                  @RequestParam(defaultValue = "99") String ageTo,
                                  @RequestParam(defaultValue = "") String country,
                                  @RequestParam(defaultValue = "") String city,
                                  @RequestParam String email,
                                  Pageable page);

    @GetMapping("/status")
    List<Long>  searchFriendsByStatus(@RequestParam String statusCode,
                                           @RequestParam String email,
                                           Pageable page);
}
