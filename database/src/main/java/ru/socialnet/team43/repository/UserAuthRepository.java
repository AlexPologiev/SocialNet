package ru.socialnet.team43.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.springframework.stereotype.Repository;
import ru.socialnet.team43.dto.UserAuthDto;
import ru.socialnet.team43.jooq.db.Tables;
import ru.socialnet.team43.jooq.db.tables.records.PersonRecord;

import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class UserAuthRepository {

    private DSLContext context;

    public Optional<UserAuthDto> getUserByEmail(String email) {
        Result<PersonRecord> user = context.selectFrom(Tables.PERSON)
                .where(Tables.PERSON.E_MAIL.eq(email))
                .fetch();
        return Optional.of((UserAuthDto) user);
    }
}
