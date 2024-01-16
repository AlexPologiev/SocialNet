package ru.socialnet.team43.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.dialogs.DialogDto;
import ru.socialnet.team43.dto.dialogs.UnreadCountDto;
import ru.socialnet.team43.service.dialogs.DialogService;

@Slf4j
@RestController
@RequestMapping("/api/v1/dialogs")
@AllArgsConstructor
public class DialogController {

    private final DialogService dialogService;

    @GetMapping
    public ResponseEntity<Page<DialogDto>> getDialogs(@RequestParam String email, Pageable page) {
        log.info("/dialogs {}", email);
        return dialogService.getDialogs(email, page);
    }

    @GetMapping("/unread")
    public ResponseEntity<UnreadCountDto> getCountUnreadDialogs(@RequestParam String email) {
        log.info("/dialogs/unread {}", email);
        return dialogService.getCountUnreadDialogs(email);
    }
}