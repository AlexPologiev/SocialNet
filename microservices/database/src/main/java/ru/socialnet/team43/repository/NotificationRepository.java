package ru.socialnet.team43.repository;

import jooq.db.Tables;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.TableField;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import jooq.db.tables.records.PersonRecord;
import ru.socialnet.team43.dto.enums.NotificationType;
import ru.socialnet.team43.dto.notifications.*;

import java.time.LocalDateTime;
import java.util.*;
import java.lang.reflect.Field;

@Repository
@AllArgsConstructor
public class NotificationRepository {
    private final DSLContext dsl;

    public int getCount(Long personId, List<Integer> list) {
        return dsl
                .select()
                .from(Tables.NOTIFICATION)
                .where(Tables.NOTIFICATION.PERSON_ID.eq(personId)
                        .and(Tables.NOTIFICATION.TYPE_ID.in(list))
                        .and(Tables.NOTIFICATION.IS_READ.eq(false)))
                .execute();
    }

    public Optional<PersonRecord> getPersonRecordByUserId(Long userId) {
        return dsl
                .selectFrom(Tables.PERSON)
                .where(Tables.PERSON.USER_ID.eq(userId))
                .fetchOptional();
    }

    public List<NotificationsDto> findByPersonId(Long personId, List<Integer> list, Pageable pageable) {
        List<NotificationDBDto> queryResults = dsl
                .selectFrom(Tables.NOTIFICATION)
                .where(Tables.NOTIFICATION.PERSON_ID.eq(personId)
                        .and(Tables.NOTIFICATION.TYPE_ID.in(list))
                        .and(Tables.NOTIFICATION.IS_READ.eq(false)))
                .orderBy(getSortFields(pageable.getSort()))
                .fetchInto(NotificationDBDto.class);

        return convertQueryResultsToModelObjects(personId, queryResults);
    }

    public int setIsRead(Long personId, List<Integer> list) {
        return dsl
                .update(Tables.NOTIFICATION)
                .set(Tables.NOTIFICATION.IS_READ, true)
                .where(Tables.NOTIFICATION.PERSON_ID.eq(personId)
                        .and(Tables.NOTIFICATION.TYPE_ID.in(list))
                        .and(Tables.NOTIFICATION.IS_READ.eq(false)))
                .returning()
                .execute();
    }

    private Collection<SortField<?>> getSortFields(Sort sortSpecification) {
        Collection<SortField<?>> querySortFields = new ArrayList<>();

        if (sortSpecification == null) {
            return querySortFields;
        }

        Iterator<Sort.Order> specifiedFields = sortSpecification.iterator();

        while (specifiedFields.hasNext()) {
            Sort.Order specifiedField = specifiedFields.next();

            String sortFieldName = specifiedField.getProperty();
            Sort.Direction sortDirection = specifiedField.getDirection();

            TableField tableField = getTableField(sortFieldName);
            SortField<?> querySortField = convertTableFieldToSortField(tableField, sortDirection);
            querySortFields.add(querySortField);
        }

        return querySortFields;
    }

    private TableField getTableField(String sortFieldName) {
        TableField sortField = null;
        try {
            Field tableField = Tables.NOTIFICATION.getClass().getField(sortFieldName);
            sortField = (TableField) tableField.get(Tables.NOTIFICATION);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            String errorMessage = String.format("Could not find table field: {}", sortFieldName);
            throw new InvalidDataAccessApiUsageException(errorMessage, ex);
        }

        return sortField;
    }

    private SortField<?> convertTableFieldToSortField(TableField tableField, Sort.Direction sortDirection) {
        if (sortDirection == Sort.Direction.ASC) {
            return tableField.asc();
        } else {
            return tableField.desc();
        }
    }

    private List<NotificationsDto> convertQueryResultsToModelObjects(
            Long personId,
            List<NotificationDBDto> queryResults) {
        List<NotificationsDto> notificationsEntries = new ArrayList<>();

        for (NotificationDBDto queryResult : queryResults) {
            NotificationsDto notificationsEntry = convertQueryResultToModelObject(personId, queryResult);
            notificationsEntries.add(notificationsEntry);
        }

        return notificationsEntries;

    }

    private NotificationsDto convertQueryResultToModelObject(Long personId, NotificationDBDto dto) {
        int typeId = dto.getTypeId();
        LocalDateTime sentTime = dto.getSentTime();
        Long entityId = dto.getEntityId();

        EventNotificationDto event = setEvent(typeId, entityId, personId);

        return NotificationsDto.builder()
                .timeStamp(sentTime)
                .data(event)
                .build();
    }

    private EventNotificationDto setEvent(int typeId, Long entityId, Long personId) {
        return switch (typeId) {
            case 1 -> setEventPostLike(entityId, personId);
            case 2 -> setEventPost(entityId, personId);
            case 3 -> setEventPostComment(entityId, personId);
            case 4 -> setEventCommentComment(entityId, personId);
            case 5 -> setEventMessage(entityId, personId);
            default -> null;
        };
    }

    private EventNotificationDto setEventMessage(Long entityId, Long personId) {
        return null;
    }

    private EventNotificationDto setEventPostLike(Long entityId, Long personId) {
        return null;
    }

    private EventNotificationDto setEventPostComment(Long entityId, Long personId) {
        return null;
    }

    private EventNotificationDto setEventCommentComment(Long entityId, Long personId) {
        return null;
    }

    private EventNotificationDto setEventPost(Long entityId, Long personId) {
        return dsl
                .selectFrom(Tables.POST)
                .where(Tables.POST.ID.eq(entityId))
                .fetchOptional()
                .map(postRecord -> EventNotificationDto.builder()
                        .authorId(postRecord.getAuthorId())
                        .receiverId(personId)
                        .notificationType(NotificationType.POST)
                        .content(postRecord.getPostText().replaceAll("<[^>]*>", ""))
                        .sentTime(postRecord.getTime().toLocalDateTime())
                        .build()).orElse(null);
    }
}
