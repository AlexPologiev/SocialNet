package ru.socialnet.team43.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.client.NotificationSettingClient;
import ru.socialnet.team43.dto.notifications.NotificationSettingDto;
import ru.socialnet.team43.dto.notifications.NotificationUpdateDto;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationSettingPRServiceImpl implements NotificationSettingPRService {
    private NotificationSettingClient settingClient;

    @Override
    public NotificationSettingDto getSettings(String email) {
        log.info("create a notification setting for the user: {}", email);
        return settingClient.getSettings(email);
    }

    @Override
    public NotificationSettingDto updateSetting(NotificationUpdateDto updateDto,
                                                String email) {
        log.info("Update notification settings for user: {}", email);
        return settingClient.updateSetting(updateDto, email);
    }

    @Override
    public NotificationSettingDto createSetting(Long id) {
        log.info("create a notification setting for the user: {}", id);
        return settingClient.createSettings(id);
    }

}
