package ru.socialnet.team43.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import ru.socialnet.team43.client.CommunicationClient;
import ru.socialnet.team43.dto.dialogs.*;
import ru.socialnet.team43.util.ControllerUtil;

@Slf4j
@RestController
@RequestMapping("/api/v1/dialogs")
@AllArgsConstructor
public class DialogController {

    private final CommunicationClient communicationClient;
    private final ControllerUtil controllerUtil;

    @GetMapping
    public ResponseEntity<Page<DialogDto>> getDialogs(
            @AuthenticationPrincipal UserDetails userDetails, Pageable page) {

        log.info("/dialogs {}", userDetails.getUsername());
        ResponseEntity<Page<DialogDto>> response =
                communicationClient.getDialogs(userDetails.getUsername(), page);

        return controllerUtil.createNewResponseEntity(response);
    }

    @GetMapping("/unread")
    public ResponseEntity<UnreadCountDto> getCountUnreadDialogs(
            @AuthenticationPrincipal UserDetails userDetails) {

        log.info("/dialogs/unread {}", userDetails.getUsername());
        ResponseEntity<UnreadCountDto> response =
                communicationClient.getCountUnreadDialogs(userDetails.getUsername());

        return controllerUtil.createNewResponseEntity(response);
    }
}
