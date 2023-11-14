package ru.socialnet.team43.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Repository;
import ru.socialnet.team43.dto.UserAuthDto;
import jooq.db.Tables;
import jooq.db.tables.records.UserAuthRecord;

import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class UserAuthRepository {

    private DSLContext context;

    public Optional<UserAuthDto> getUserByEmail(String email) {
        Result<UserAuthRecord> user = context.selectFrom(Tables.USER_AUTH)
                .where(Tables.USER_AUTH.EMAIL.eq(email))
                .fetch();
        return Optional.of((UserAuthDto) user);
    }
}
