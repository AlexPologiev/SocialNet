package ru.socialnet.team43.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.client.DatabaseClient;
import ru.socialnet.team43.dto.PersonDto;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController
{
    private final DatabaseClient databaseClient;

    @GetMapping("/me")
    public ResponseEntity<PersonDto> getMyProfile(@RequestParam("email") String email)
    {
        ResponseEntity<PersonDto> personDtoResponseEntity = databaseClient.getAccountInfo(email);
        return personDtoResponseEntity;
    }
}
