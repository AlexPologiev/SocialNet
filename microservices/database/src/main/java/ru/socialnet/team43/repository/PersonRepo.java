package ru.socialnet.team43.repository;

import jooq.db.Tables;
import jooq.db.tables.records.PersonRecord;
import jooq.db.tables.records.UserAuthRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.socialnet.team43.dto.enums.FriendshipStatus;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Repository
@Slf4j
public class PersonRepo {

    private final DSLContext dslContext;
    private final UserAuthRepository userRepo;

    public Optional<PersonRecord> insertPerson(PersonRecord personRecord) {
        return dslContext.insertInto(Tables.PERSON)
                .set(personRecord)
                .returning()
                .fetchOptional();
    }

    public int deletePerson(String email) {

        Long id = findUserIdByEmail(email);

        return dslContext.update(Tables.PERSON)
                .set(Tables.PERSON.IS_DELETED, true)
                .where(Tables.PERSON.USER_ID.eq(id))
                .execute();
    }


    public Optional<PersonRecord> updatePerson(PersonRecord record, String email) {

        Long id = findUserIdByEmail(email);

        Optional<PersonRecord> optionalDbRecord = dslContext.selectFrom(Tables.PERSON)
                .where(Tables.PERSON.USER_ID.eq(id))
                .fetchOptional();

        if (optionalDbRecord.isPresent()) {
            PersonRecord dbRecord = optionalDbRecord.get();
            fillAccount(record, dbRecord);

            return dslContext.update(Tables.PERSON)
                    .set(dslContext.newRecord(Tables.PERSON, record))
                    .where(Tables.PERSON.USER_ID.eq(id))
                    .returning()
                    .fetchOptional();
        }
        return Optional.empty();
    }

    public Long findUserIdByEmail(String email) {
        return dslContext.selectFrom(Tables.USER_AUTH)
                .where(Tables.USER_AUTH.EMAIL.eq(email))
                .fetchOptional()
                .map(UserAuthRecord::getId)
                .orElse(0L);
    }

    public int getFriendsCount(Long id) {
        Optional<Integer> count = ofNullable(dslContext.selectCount().from(Tables.FRIENDSHIP)
                .where(Tables.FRIENDSHIP.SRC_PERSON_ID.eq(id))
                .and(Tables.FRIENDSHIP.FRIENDSHIPSTATUS.eq(FriendshipStatus.FRIEND.name()))
                .fetchOne(0, int.class));
        return count.orElse(0);
    }
    public Optional<PersonRecord> getPersonById(Long id) {
        return dslContext.selectFrom(Tables.PERSON)
                .where(Tables.PERSON.ID.eq(id))
                .fetchOptional();

    }

    private void fillAccount(PersonRecord dest, PersonRecord src) {
        dest.setRegDate(src.getRegDate());
        dest.setStatusCode(src.getStatusCode());
        dest.setMessagesPermission(src.getMessagesPermission());
        dest.setIsBlocked(src.getIsBlocked());
        dest.setCreatedDate(src.getCreatedDate());
        dest.setLastOnlineTime(LocalDateTime.now());
        dest.setIsDeleted(src.getIsDeleted());
        dest.setIsOnline(true);
        dest.setLastModifiedDate(LocalDateTime.now());
    }


}
