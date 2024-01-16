package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.socialnet.team43.dto.dialogs.DialogDto;
import ru.socialnet.team43.service.dialogs.DialogService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dialogs")
@RequiredArgsConstructor
public class DialogController {

    private final DialogService dialogService;

    @GetMapping
    public Page<DialogDto> getDialogs(@RequestParam String email, Pageable page) {
        log.info("/dialogs {}", email);
        return dialogService.getDialogs(email, page);
    }

    @GetMapping("/unread")
    public Integer getCountUnreadDialogs(@RequestParam String email) {
        log.info("/dialogs/unread {}", email);
        return dialogService.getCountUnreadDialogs(email);
    }
}
