package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.client.DatabaseClient;
import ru.socialnet.team43.dto.PersonDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendController {

    private final DatabaseClient client;

    @GetMapping("/count")
    public long getFriendCount(@RequestParam String email) {
        return client.getFriendsCount(email);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<PersonDto>> getRecommendations(@RequestParam String email) {
        return client.getRecommendations(email);
    }
}
