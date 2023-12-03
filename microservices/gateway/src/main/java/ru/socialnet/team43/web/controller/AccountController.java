package ru.socialnet.team43.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.client.ProfileClient;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.util.ControllerUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController
{
    private final ProfileClient profileClient;
    private final ControllerUtil controllerUtil;

    @GetMapping("/me")
    public ResponseEntity<PersonDto> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {

        ResponseEntity<PersonDto> inputResponseEntity = profileClient.getMyProfile(userDetails.getUsername());
        return controllerUtil.createNewResponseEntity(inputResponseEntity);
    }
}
