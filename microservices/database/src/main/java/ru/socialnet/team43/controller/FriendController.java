package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.service.FriendService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService service;

    /**
     * Эндпоинт получения количества друзей
     * @param email эл/почта пользователя
     * @return количество друзей
     */
    @GetMapping("/count")
    public long getFriendCount(@RequestParam String email) {
        return service.getFriendsCount(email);
    }

    /**
     * Эендпоинт получения списка рекомендованных друзей
     * @param email эл/почта пользователя
     * @return список рекомендованных друзей
     */
    @GetMapping("/recommendations")
    public ResponseEntity<List<PersonDto>> getRecommendations(@RequestParam String email) {
        return ResponseEntity.ok(service.getRecommendations(email));
    }
}
