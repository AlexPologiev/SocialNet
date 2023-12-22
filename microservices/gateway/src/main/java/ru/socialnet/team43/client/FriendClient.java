package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.socialnet.team43.dto.PersonDto;

import java.util.List;

@FeignClient(name = "friendClient", url = "${environment.url}" + "/api/v1/friends")
public interface FriendClient {

    @GetMapping("/count")
    long getFriendsCount(@RequestParam String email);

    @GetMapping("/recommendations")
    ResponseEntity<List<PersonDto>> getRecommendations(@RequestParam String email);
}
