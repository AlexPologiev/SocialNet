package ru.socialnet.team43.repository;

import lombok.extern.slf4j.Slf4j;
import jooq.db.Tables;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import jooq.db.tables.records.PersonRecord;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class PersonRepo {

    private final DSLContext dslContext;

    public int getCountPersonsByEmail(String email) {
        return dslContext.selectFrom(Tables.PERSON)
                .where(Tables.PERSON.E_MAIL.eq(email))
                .execute();
    }

    public Optional<PersonRecord> insertPerson(PersonRecord personRecord) {
        return dslContext.insertInto(Tables.PERSON)
                .set(personRecord)
                .returning()
                .fetchOptional();
    }


}
