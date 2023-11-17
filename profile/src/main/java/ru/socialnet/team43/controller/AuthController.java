package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.socialnet.team43.dto.CaptchaDto;
import ru.socialnet.team43.service.AuthService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;


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

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaDto> getCaptcha() {
        return ResponseEntity.ok(authService.getCaptcha());
    }
}
