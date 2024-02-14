package ru.socialnet.team43.repository.mapper;

import org.mapstruct.Mapper;
import jooq.db.tables.records.NotificationRecord;
import ru.socialnet.team43.dto.notifications.NotificationDBDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    List<NotificationRecord> mapToList(List<NotificationDBDto> notificationDBDtoList);

    NotificationRecord notificationDBDtoToNotificationRecord(NotificationDBDto notificationDBDto);
}
