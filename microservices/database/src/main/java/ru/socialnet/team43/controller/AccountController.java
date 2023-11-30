package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.dto.AccountDto;
import ru.socialnet.team43.service.UserAuthService;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController
{
    private final UserAuthService userAuthService;

    @GetMapping("/me")
    public ResponseEntity<AccountDto> getAccountInfo(@RequestParam("email") String email)
    {
        Optional<AccountDto> accountInfo = userAuthService.getAccountInfo(email);

        return accountInfo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
