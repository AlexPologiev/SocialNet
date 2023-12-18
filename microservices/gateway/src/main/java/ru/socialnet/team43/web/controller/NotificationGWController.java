package ru.socialnet.team43.web.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.ProfileClient;
import ru.socialnet.team43.dto.CountDto;
import ru.socialnet.team43.dto.DataCount;
import ru.socialnet.team43.dto.notifications.NotificationSettingDto;
import ru.socialnet.team43.dto.notifications.NotificationUpdateDto;
import ru.socialnet.team43.util.ControllerUtil;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/v1/notifications")
@AllArgsConstructor
public class NotificationGWController {
    private final ProfileClient profileClient;
    private final ControllerUtil controllerUtil;

    @GetMapping("/count")
    public ResponseEntity<CountDto> getNotificationCount() {
        CountDto dto = CountDto.builder()
                .data(new DataCount(0))
                .timeStamp(LocalDateTime.now())
                .build();
        log.info("getNotificationsCount {}", dto);
        return ResponseEntity.ok(dto);

    }

    @GetMapping("/settings")
    public ResponseEntity<NotificationSettingDto> getSettings(
            @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        log.info("setting up notifications for the user: {}", email);
        return controllerUtil.createNewResponseEntity(
                profileClient.getSettings(email));
    }

    @PutMapping("/settings")
    public ResponseEntity<NotificationSettingDto> updateSetting(
            @RequestBody NotificationUpdateDto notificationUpdateDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        log.info("Update notification settings for email: {}", email);

        ResponseEntity<NotificationSettingDto> inputResponseEntity =
                profileClient.updateSetting(notificationUpdateDto, email);

        HttpStatusCode statusCode = inputResponseEntity.getStatusCode();

        if (statusCode.isSameCodeAs(HttpStatusCode.valueOf(404))) {
            return ResponseEntity.badRequest().build();
        }

        return controllerUtil.createNewResponseEntity(inputResponseEntity);
    }

    @PostMapping("/settings{id}")
    public ResponseEntity<NotificationSettingDto> createSettings(@PathVariable Long id) {
        log.info("create a notification setting for the user: {}", id);
        return controllerUtil.createNewResponseEntity(profileClient.createSettings(id));
    }
}
