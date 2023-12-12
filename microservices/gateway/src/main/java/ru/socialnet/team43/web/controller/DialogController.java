package ru.socialnet.team43.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.dto.UnreadCountDto;

@Slf4j
@RestController
@RequestMapping("/api/v1/dialogs")
public class DialogController {

    @GetMapping("/unread")
    public ResponseEntity<UnreadCountDto> getCountUnreadDialogs(){
        log.info("getCountUnreadDialogs");
        return ResponseEntity.ok(new UnreadCountDto(0));
    }
}
