package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.FriendDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.service.FriendService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService service;

    @GetMapping("/count")
    public long getFriendCount(@RequestParam String email) {
        return service.getFriendsCount(email);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<FriendDto>> getRecommendations(@RequestParam String email) {
        return ResponseEntity.ok(service.getRecommendations(email));
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
        List<PersonDto> result = service.searchFriends(statusCode,
                firstName,
                Integer.parseInt(ageFrom),
                Integer.parseInt(ageTo),
                country,
                city,
                email,
                page);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/status")
    public ResponseEntity<List<Long>> searchFriendsByStatus(@RequestParam String statusCode,
                                                                 @RequestParam String email,
                                                                 Pageable page){
        List<Long> result = service.searchFriendsByStatus(statusCode, email, page);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Void> approveFriendRequest(@PathVariable Long id, @RequestParam String email){
        service.approveFriendRequest(id, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long id, @RequestParam String email){
       service.deleteFriend(id, email);
       return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/request")
    public ResponseEntity<Void> friendRequest(@PathVariable Long id, @RequestParam String email){
        service.friendRequest(id, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FriendDto> getFriendById(@PathVariable Long id, @RequestParam String email){
        return service.getFriendsById(id, email);
    }
}
