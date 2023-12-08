package ru.socialnet.team43.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.ProfileClient;
import ru.socialnet.team43.dto.CaptchaDto;
import ru.socialnet.team43.dto.RegDto;
import ru.socialnet.team43.security.SecurityService;
import ru.socialnet.team43.util.ControllerUtil;
import ru.socialnet.team43.web.model.AuthRequest;
import ru.socialnet.team43.web.model.JwtResponse;
import ru.socialnet.team43.web.model.RefreshRequest;
import ru.socialnet.team43.web.model.SimpleResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController
{
    private final SecurityService securityService;
    private final ProfileClient profileClient;
    private final ControllerUtil controllerUtil;

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
    public ResponseEntity<SimpleResponse> logout() {
        SimpleResponse responseBody = securityService.logout();

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> RegistrationPerson(@RequestBody RegDto regDto) {

        if(securityService.doPasswordsMatch(regDto)){
            ResponseEntity<Void> inputResponseEntity = profileClient.RegistrationPerson(securityService.getRegDtoWithEncryptedPassword(regDto));
            HttpStatusCode statusCode = inputResponseEntity.getStatusCode();

            if (statusCode.isSameCodeAs(HttpStatusCode.valueOf(404))) {
                return ResponseEntity.badRequest().build();
            }

            return controllerUtil.createNewResponseEntity(inputResponseEntity);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaDto> getCaptcha() {
        ResponseEntity<CaptchaDto> inputResponseEntity = profileClient.getCaptcha();
        return controllerUtil.createNewResponseEntity(inputResponseEntity);
    }
}