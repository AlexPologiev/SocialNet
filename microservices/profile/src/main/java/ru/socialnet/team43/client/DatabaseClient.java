package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.RegDtoDb;
import ru.socialnet.team43.dto.UserAuthDto;


@FeignClient(name = "databaseClient", dismiss404 = true, url = "${database.url}")
public interface DatabaseClient {

    @GetMapping("/auth/user")
    UserAuthDto getUserByEmail(@RequestParam("email") String email);

    @GetMapping("/auth/register/email")
    ResponseEntity<Void> isEmailExist(@RequestParam("email") String email);

    @PostMapping("/auth/register/create")
    ResponseEntity<Void> createPerson(RegDtoDb regDtoDb);

    @GetMapping("/account/me")
    ResponseEntity<PersonDto> getAccountInfo(@RequestParam("email") String email);
}