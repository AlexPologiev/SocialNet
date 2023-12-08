package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
