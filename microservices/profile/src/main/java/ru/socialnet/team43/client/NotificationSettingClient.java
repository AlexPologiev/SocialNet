package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.notifications.NotificationSettingDto;
import ru.socialnet.team43.dto.notifications.NotificationUpdateDto;

@FeignClient(name = "notifications", url = "${database.url}" + "/notifications")
public interface NotificationSettingClient {
    @GetMapping("/settings")
    NotificationSettingDto getSettings(@RequestParam("email") String email);

    @PutMapping("/settings")
    NotificationSettingDto updateSetting(
            @RequestBody NotificationUpdateDto notificationUpdateDto,
            @RequestParam("email") String email);

    @PostMapping("/settings{id}")
    NotificationSettingDto createSettings(@PathVariable Long id);

}
