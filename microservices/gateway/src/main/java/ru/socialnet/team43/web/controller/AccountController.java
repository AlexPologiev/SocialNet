package ru.socialnet.team43.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.ProfileClient;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.StatusCode;
import ru.socialnet.team43.util.ControllerUtil;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {
    private final ProfileClient profileClient;
    private final ControllerUtil controllerUtil;

    @GetMapping("/me")
    public ResponseEntity<PersonDto> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity<PersonDto> inputResponseEntity = profileClient.getMyProfile(userDetails.getUsername());
        return controllerUtil.createNewResponseEntity(inputResponseEntity);
    }

    @PutMapping("/me")
    public ResponseEntity<PersonDto> updateMyProfile(@RequestBody PersonDto dto,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        log.info("update person with email: {}",email);
        dto.setEmail(email);
        ResponseEntity<PersonDto> inputResponseEntity = profileClient.updateMyProfile(dto);
        HttpStatusCode statusCode = inputResponseEntity.getStatusCode();
        if (statusCode.isSameCodeAs(HttpStatusCode.valueOf(404))) {
            return ResponseEntity.badRequest().build();
        }

        return controllerUtil.createNewResponseEntity(inputResponseEntity);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        log.info("delete person with email: {}",email);

        HttpStatusCode statusCode = profileClient.deleteMyProfile(email).getStatusCode();

        if (statusCode.isSameCodeAs(HttpStatusCode.valueOf(404))) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(statusCode).build();
    }
}
