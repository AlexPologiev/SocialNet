package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.dto.UserAuthDto;
import ru.socialnet.team43.repository.UserAuthRepository;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserAuthController {

    private UserAuthRepository authRepository;

    @GetMapping("/user")
    public ResponseEntity<UserAuthDto> getUser(@RequestParam("email") String email) {
        Optional<UserAuthDto> user = authRepository.getUserByEmail(email);

        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
