package ru.socialnet.team43.service;

import ru.socialnet.team43.dto.notifications.NotificationSettingDto;
import ru.socialnet.team43.dto.notifications.NotificationUpdateDto;

public interface NotificationSettingPRService {
    NotificationSettingDto getSettings(String email);

    NotificationSettingDto updateSetting(NotificationUpdateDto notificationUpdateDto, String email);

    NotificationSettingDto createSetting(Long id);
}
