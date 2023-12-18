package ru.socialnet.team43.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.notifications.NotificationSettingDto;
import ru.socialnet.team43.dto.notifications.NotificationUpdateDto;
import ru.socialnet.team43.service.NotificationSettingPRServiceImpl;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationSettingPRController {

    private final NotificationSettingPRServiceImpl settingService;

    @GetMapping("/settings")
    public ResponseEntity<NotificationSettingDto> getSettings(@RequestParam("email") String email) {
        //description: Получение настроек оповещений
        return ResponseEntity.ok(settingService.getSettings(email));
    }

    @PutMapping("/settings")
    public ResponseEntity<NotificationSettingDto> updateSetting(
            @RequestBody NotificationUpdateDto notificationUpdateDto,
            @RequestParam("email") String email) {
        //description: Коррекция настроек оповещений
        return ResponseEntity.ok(settingService.updateSetting(notificationUpdateDto, email));
    }

    @PostMapping("/settings{id}")
    public ResponseEntity<NotificationSettingDto> createSettings(@PathVariable Long id) {
        //description: Создание настроек оповещений
        return ResponseEntity.ok(settingService.createSetting(id));
    }
}
