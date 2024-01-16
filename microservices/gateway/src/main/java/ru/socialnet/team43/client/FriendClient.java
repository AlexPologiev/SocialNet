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

@FeignClient(name = "friendClient", url = "${environment.url}" + "/api/v1/friends")
public interface FriendClient {

    @GetMapping("/count")
    long getFriendsCount(@RequestParam String email);

    @GetMapping("/recommendations")
    ResponseEntity<List<FriendDto>> getRecommendations(@RequestParam String email);

    @GetMapping("")
    ResponseEntity<List<PersonDto>> searchFriends(@RequestParam String statusCode,
                                                  @RequestParam(defaultValue = "") String firstName,
                                                  @RequestParam(defaultValue = "0") String ageFrom,
                                                  @RequestParam(defaultValue = "99") String ageTo,
                                                  @RequestParam(defaultValue = "") String country,
                                                  @RequestParam(defaultValue = "") String city,
                                                  @RequestParam String email,
                                                  Pageable page);
    @GetMapping("/status")
    ResponseEntity<List<FriendDto>> searchFriendsByStatus(@RequestParam String statusCode,
                                                          @RequestParam String email,
                                                          Pageable page);
}
