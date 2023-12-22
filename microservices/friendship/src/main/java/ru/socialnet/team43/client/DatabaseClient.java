package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    ResponseEntity<List<PersonDto>> getRecommendations(@RequestParam String email);
}
