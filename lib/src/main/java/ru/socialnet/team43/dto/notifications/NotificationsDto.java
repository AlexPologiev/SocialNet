package ru.socialnet.team43.dto.notifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationsDto {
    private LocalDateTime timeStamp;
    private EventNotificationDto data;
}
