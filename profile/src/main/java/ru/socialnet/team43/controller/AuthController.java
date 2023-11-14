package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.RegDto;
import ru.socialnet.team43.dto.CaptchaDto;
import ru.socialnet.team43.service.AuthService;
import ru.socialnet.team43.service.RegistrationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RegistrationService registrationService;

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        authService.login();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> RegistrationPerson(@RequestBody RegDto regDto) {
        boolean result = registrationService.registrationPerson(regDto);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();

    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaDto> getCaptcha() {
        return ResponseEntity.ok(authService.getCaptcha());
    }
}
