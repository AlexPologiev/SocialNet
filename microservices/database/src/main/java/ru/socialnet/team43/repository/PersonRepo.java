package ru.socialnet.team43.repository;

import jooq.db.Tables;
import jooq.db.tables.records.PersonRecord;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class PersonRepo {

    private final DSLContext dslContext;

    public Optional<PersonRecord> insertPerson(PersonRecord personRecord) {
        return dslContext.insertInto(Tables.PERSON)
                .set(personRecord)
                .returning()
                .fetchOptional();
    }


}
