package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.repository.PersonRepo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendsController {

    private final PersonRepo personRepo;

    @GetMapping("/count/")
    public long getFriendCount(@RequestParam("id") Long id) {
        return personRepo.getFriendsCount(id);
    }
}
