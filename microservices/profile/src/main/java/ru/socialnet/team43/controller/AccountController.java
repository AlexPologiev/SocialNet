package ru.socialnet.team43.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.client.DatabaseClient;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.util.ControllerUtil;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController
{
    private final DatabaseClient databaseClient;
    private final ControllerUtil controllerUtil;

    @GetMapping("/me")
    public ResponseEntity<PersonDto> getMyProfile(@RequestParam("email") String email)
    {
        ResponseEntity<PersonDto> inputResponseEntity = databaseClient.getAccountInfo(email);
        return controllerUtil.createNewResponseEntity(inputResponseEntity);
    }
}
