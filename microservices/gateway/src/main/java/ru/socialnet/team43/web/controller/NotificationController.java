package ru.socialnet.team43.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.dto.CountDto;
import ru.socialnet.team43.dto.DataCount;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @GetMapping("/count")
    public ResponseEntity<CountDto> getNotificationCount(){
        CountDto dto = CountDto.builder()
                .data(new DataCount(0))
                .timeStamp(LocalDateTime.now())
                .build();
        log.info("getNotificationsCount {}",dto);
        return ResponseEntity.ok(dto);

    }
}
