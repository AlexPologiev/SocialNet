package ru.socialnet.team43.service.notifications;

import org.springframework.data.domain.Pageable;
import ru.socialnet.team43.dto.CountDto;
import ru.socialnet.team43.dto.notifications.NotificationsDto;

import java.util.List;

public interface NotificationDBService {

    List<NotificationsDto> findByPersonId(String email, Pageable pageable);

    CountDto getNotificationCount(String email);
}
