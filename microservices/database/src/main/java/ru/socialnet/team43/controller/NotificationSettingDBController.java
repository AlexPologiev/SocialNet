package ru.socialnet.team43.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.notifications.NotificationSettingDto;
import ru.socialnet.team43.dto.notifications.NotificationUpdateDto;
import ru.socialnet.team43.repository.mapper.NotificationSettingMapper;
import ru.socialnet.team43.service.notifications.NotificationSettingDBServiceImpl;

import java.util.Optional;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationSettingDBController {
    private final NotificationSettingDBServiceImpl settingService;
    private final NotificationSettingMapper mapper;

    @GetMapping("/settings")
    public ResponseEntity<NotificationSettingDto> getSettings(@RequestParam("email") String email) {
        //description: Получение настроек оповещений
        return settingService
                .getSettings(email)
                .map(settingRecord -> ResponseEntity
                        .ok(mapper.notificationSettingToDtoMapper(settingRecord))
                )
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/settings")
    public ResponseEntity<NotificationSettingDto> updateSetting(
            @RequestBody NotificationUpdateDto notificationUpdateDto,
            @RequestParam("email") String email) {
        //description: Коррекция настроек оповещений
        return settingService
                .updateSetting(notificationUpdateDto, email)
                .map(settingRecord -> ResponseEntity
                        .ok(mapper.notificationSettingToDtoMapper(settingRecord))
                )
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PostMapping("/settings{id}")
    public ResponseEntity<NotificationSettingDto> createSettings(@PathVariable Long id) {
        //description: Создание настроек оповещений
        return settingService
                .createSetting(id)
                .map(settingRecord -> ResponseEntity
                        .ok(mapper.notificationSettingToDtoMapper(settingRecord))
                )
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
