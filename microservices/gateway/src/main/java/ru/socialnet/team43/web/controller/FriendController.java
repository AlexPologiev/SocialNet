package ru.socialnet.team43.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.client.FriendClient;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.util.ControllerUtil;

import java.util.List;

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
    public ResponseEntity<List<PersonDto>> getRecommendations(@AuthenticationPrincipal UserDetails userDetails) {
        return util.createNewResponseEntity(friendClient.getRecommendations(userDetails.getUsername()));
    }
}
