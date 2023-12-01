package ru.socialnet.team43.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.ProfileClient;
import ru.socialnet.team43.dto.CaptchaDto;
import ru.socialnet.team43.dto.RegDto;
import ru.socialnet.team43.security.SecurityService;
import ru.socialnet.team43.web.model.AuthRequest;
import ru.socialnet.team43.web.model.JwtResponse;
import ru.socialnet.team43.web.model.RefreshRequest;
import ru.socialnet.team43.web.model.SimpleResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController
{
    private final SecurityService securityService;
    private final ProfileClient profileClient;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> signIn(@RequestBody AuthRequest authRequest)
    {
        JwtResponse responseBody = securityService.authenticateUser(authRequest);

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshRequest refreshRequest)
    {
        JwtResponse responseBody = securityService.refreshToken(refreshRequest);

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logout()
    {
        SimpleResponse responseBody = securityService.logout();

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> RegistrationPerson(@RequestBody RegDto regDto)
    {
        if(securityService.doPasswordsMatch(regDto)){
            return profileClient.RegistrationPerson(securityService.getRegDtoWithEncryptedPassword(regDto));
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaDto> getCaptcha()
    {
        return profileClient.getCaptcha();
    }
}