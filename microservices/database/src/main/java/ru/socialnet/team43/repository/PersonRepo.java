package ru.socialnet.team43.repository;

import jooq.db.Tables;
import jooq.db.tables.records.PersonRecord;
import jooq.db.tables.records.UserAuthRecord;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

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
