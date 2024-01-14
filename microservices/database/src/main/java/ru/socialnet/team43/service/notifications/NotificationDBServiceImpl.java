package ru.socialnet.team43.service.notifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.CountDto;
import ru.socialnet.team43.dto.DataCount;
import ru.socialnet.team43.dto.notifications.*;
import ru.socialnet.team43.repository.*;
import jooq.db.tables.records.PersonRecord;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationDBServiceImpl implements NotificationDBService {
    private final NotificationRepository notificationRepository;
    private final NotificationSettingRepository settingRepository;
    private final PersonRepository personRepository;

    @Override
    public List<NotificationsDto> findByPersonId(String email, Pageable pageable) {
        Long userId = personRepository.findUserIdByEmail(email);
        Long personId = notificationRepository.getPersonRecordByUserId(userId)
                .map(PersonRecord::getId)
                .orElse(0L);
        List<Integer> list = settingRepository.getListNotificationType(userId);
        log.info("notifications to user: {}; userId: {}; personId: {}; list: {};",
                email, userId, personId, list
        );

        List<NotificationsDto> contents = notificationRepository.findByPersonId(personId, list, pageable);
        log.info("receiving all notifications for the user: {}, userId: {}; personIds: {}; contents: {}",
                email, userId, personId, contents.size()
        );
        return contents;
    }

    @Override
    public CountDto getNotificationCount(String email) {
        Long userId = personRepository.findUserIdByEmail(email);
        Long personId = notificationRepository.getPersonRecordByUserId(userId)
                .map(PersonRecord::getId)
                .orElse(0L);
        List<Integer> list = settingRepository.getListNotificationType(userId);

        int count = notificationRepository.getCount(personId, list);

        CountDto countDto = CountDto.builder()
                .data(new DataCount(count))
                .timeStamp(LocalDateTime.now())
                .build();
        log.info("Count notifications for the user: {} - {}", email, countDto.getData().getCount());
        return countDto;
    }

    public void setIsRead(String email){
        Long userId = personRepository.findUserIdByEmail(email);
        Long personId = notificationRepository.getPersonRecordByUserId(userId)
                .map(PersonRecord::getId)
                .orElse(0L);
        List<Integer> list = settingRepository.getListNotificationType(userId);
        int result = notificationRepository.setIsRead(personId, list);
        log.info("all notifications for user: {} - personId {} are marked as read {}", email, personId, result);
    }
}
