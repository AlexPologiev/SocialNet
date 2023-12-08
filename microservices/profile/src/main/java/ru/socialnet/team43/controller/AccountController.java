package ru.socialnet.team43.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.DatabaseClient;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.util.ControllerUtil;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final DatabaseClient databaseClient;
    private final ControllerUtil controllerUtil;

    @GetMapping("/me")
    public ResponseEntity<PersonDto> getMyProfile(@RequestParam("email") String email) {
        ResponseEntity<PersonDto> inputResponseEntity = databaseClient.getAccountInfo(email);
        return controllerUtil.createNewResponseEntity(inputResponseEntity);
    }

    @PutMapping("/me")
    public ResponseEntity<PersonDto> updateMyProfile(@RequestBody PersonDto dto) {
        ResponseEntity<PersonDto> inputResponseEntity = databaseClient.updateMyProfile(dto);
        return controllerUtil.createNewResponseEntity(inputResponseEntity);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyProfile(@RequestParam("email") String email) {
        return databaseClient.deleteMyProfile(email);
    }
}
