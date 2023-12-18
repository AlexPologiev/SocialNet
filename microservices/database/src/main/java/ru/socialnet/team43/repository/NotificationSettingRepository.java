package ru.socialnet.team43.repository;

import jooq.db.Tables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import jooq.db.tables.records.NotificationSettingRecord;

import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class NotificationSettingRepository {
    private final DSLContext dsl;

    public Optional<NotificationSettingRecord> getNotificationSettingById(Long userId) {
        return dsl
                .selectFrom(Tables.NOTIFICATION_SETTING)
                .where(Tables.NOTIFICATION_SETTING.ID.eq(userId))
                .fetchOptional();
    }

    public int deleteNotificationSettingById(Long userId) {
        return dsl
                .deleteFrom(Tables.NOTIFICATION_SETTING)
                .where(Tables.NOTIFICATION_SETTING.ID.eq(userId))
                .execute();
    }

    public Optional<NotificationSettingRecord> updateNotificationSettingRecord(
            NotificationSettingRecord notificationSettingRecord) {
        return dsl
                .update(Tables.NOTIFICATION_SETTING)
                .set(notificationSettingRecord)
                .where(Tables.NOTIFICATION_SETTING.ID.eq(notificationSettingRecord.getId()))
                .returning()
                .fetchOptional();
    }

    public Optional<NotificationSettingRecord> createNotificationSettingByRecord(
            NotificationSettingRecord notificationSettingRecord) {
        return dsl
                .insertInto(Tables.NOTIFICATION_SETTING)
                .set(notificationSettingRecord)
                .returning()
                .fetchOptional();
    }
}
