package ru.socialnet.team43.dto.notifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDBDto {
    private BigInteger id;
    private int typeId;
    private LocalDateTime sentTime;
    private Long personId;
    private Long entityId;
    private String contact;
    private boolean isRead;
}
