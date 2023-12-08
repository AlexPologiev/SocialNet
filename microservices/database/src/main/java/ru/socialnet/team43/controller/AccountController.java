package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.service.UserAuthService;

import java.util.Optional;
@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {
    private final UserAuthService userAuthService;

    @GetMapping("/me")
    public ResponseEntity<PersonDto> getAccountInfo(@RequestParam("email") String email) {
        Optional<PersonDto> accountInfo = userAuthService.getAccountInfo(email);
        return accountInfo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/me")
    public ResponseEntity<PersonDto> updateAccount(@RequestBody PersonDto dto) {
        Optional<PersonDto> accountInfo = userAuthService.updateAccount(dto);
        return accountInfo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteAccount(@RequestParam("email") String email) {
        int result = userAuthService.deleteAccount(email);
        return result > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
