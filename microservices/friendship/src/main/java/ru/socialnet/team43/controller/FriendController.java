package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.DatabaseClient;
import ru.socialnet.team43.dto.FriendDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.sevice.FriendService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendController {

    private final DatabaseClient client;
    private final FriendService friendService;

    @GetMapping("/count")
    public long getFriendCount(@RequestParam String email) {
        return client.getFriendsCount(email);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<FriendDto>> getRecommendations(@RequestParam String email) {
        return client.getRecommendations(email);
    }

    @GetMapping("")
    public ResponseEntity<List<PersonDto>> searchFriends(@RequestParam String statusCode,
                                                         @RequestParam(defaultValue = "") String firstName,
                                                         @RequestParam(defaultValue = "0") String ageFrom,
                                                         @RequestParam(defaultValue = "99") String ageTo,
                                                         @RequestParam(defaultValue = "") String country,
                                                         @RequestParam(defaultValue = "") String city,
                                                         @RequestParam String email,
                                                         Pageable page){

        return ResponseEntity.ok(friendService.searchFriends(statusCode,
                                                firstName,
                                                ageFrom,
                                                ageTo,
                                                country,
                                                city,
                                                email,
                                                page));
    }

    @GetMapping("/status")
    public ResponseEntity<List<FriendDto>> searchFriendsByStatus(@RequestParam String statusCode,
                                                             @RequestParam String email,
                                                             Pageable page){

        return ResponseEntity.ok(friendService.searchFriendsByStatus(statusCode, email, page));
    }

}
