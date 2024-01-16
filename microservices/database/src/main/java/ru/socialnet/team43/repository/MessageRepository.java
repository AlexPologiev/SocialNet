package ru.socialnet.team43.repository;

import jooq.db.Tables;
import jooq.db.tables.records.MessageRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {

    private final DSLContext dsl;

    public List<MessageRecord> getLastMessageByDialogId(Long dialogId, Long userId) {
        List<MessageRecord> messageList = dsl.selectFrom(Tables.MESSAGE)
                .where(Tables.MESSAGE.DIALOG_ID.eq(dialogId))
                .and(Tables.MESSAGE.RECIPIENT_ID.eq(userId))
                .and(Tables.MESSAGE.READ_STATUS.eq("SENT"))
                .and(Tables.MESSAGE.IS_DELETED.eq(false)) // TODO: сделать сортировку
                .fetchInto(MessageRecord.class);

        if (messageList.size() > 0) {
            return messageList;
        }

        MessageRecord lastMessage = dsl.selectFrom(Tables.MESSAGE)
                .where(Tables.MESSAGE.DIALOG_ID.eq(dialogId))
                .and(Tables.MESSAGE.IS_DELETED.eq(false))
                .orderBy(Tables.MESSAGE.TIME.desc())
                .fetchAny();

        messageList.add(lastMessage);
        return messageList;
    }

    public Integer unreadCountForDialog(Long dialogId, Long userId) {
        return dsl.selectCount().from(Tables.MESSAGE)
                .where(Tables.MESSAGE.DIALOG_ID.eq(dialogId))
                .and(Tables.MESSAGE.RECIPIENT_ID.eq(userId))
                .and(Tables.MESSAGE.READ_STATUS.eq("SENT"))
                .and(Tables.MESSAGE.IS_DELETED.eq(false))
                .fetchOne(0, Integer.class);
    }

    public Integer allUnreadCountByUser(Long userId) {
        return dsl.selectCount().from(dsl.selectDistinct(Tables.MESSAGE.AUTHOR_ID)
                        .from(Tables.MESSAGE)
                        .where(Tables.MESSAGE.RECIPIENT_ID.eq(userId))
                        .and(Tables.MESSAGE.READ_STATUS.eq("SENT"))
                        .and(Tables.MESSAGE.IS_DELETED.eq(false)))
                .fetchOne(0, Integer.class);
    }
}
