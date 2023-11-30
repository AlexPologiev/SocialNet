package ru.socialnet.team43.repository;

import jooq.db.Tables;
import jooq.db.tables.records.UserAuthRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.socialnet.team43.dto.AccountDto;
import ru.socialnet.team43.dto.UserAuthDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserAuthRepositoryImpl implements UserAuthRepository
{
    private final DSLContext dslContext;

    @Override
    public Optional<UserAuthDto> getUserByEmail(String email)
    {
        List<UserAuthDto> userList = dslContext.selectFrom(Tables.USER_AUTH)
                .where(Tables.USER_AUTH.EMAIL.eq(email))
                .fetchInto(UserAuthDto.class);

        UserAuthDto user = (0 != userList.size()) ? userList.get(0) : null;

        return Optional.ofNullable(user);
    }

    @Override
    public int getUsersCountByEmail(String email) {
        return dslContext.selectFrom(Tables.USER_AUTH)
                .where(Tables.USER_AUTH.EMAIL.eq(email))
                .execute();
    }

    @Override
    public Optional<UserAuthRecord> insertUserAuth(UserAuthRecord userAuthRecord) {
        return dslContext.insertInto(Tables.USER_AUTH)
                .set(userAuthRecord)
                .returning()
                .fetchOptional();
    }

    @Override
    public Optional<AccountDto> getAccountInfo(String email)
    {
        return dslContext
                        .select(Tables.PERSON.FIRST_NAME,
                                Tables.PERSON.LAST_NAME,
                                Tables.USER_AUTH.EMAIL,
                                Tables.PERSON.PHONE,
                                Tables.PERSON.PHOTO,
                                Tables.PERSON.ABOUT,
                                Tables.PERSON.TOWN,
                                Tables.PERSON.BIRTH_DATE,
                                Tables.PERSON.MESSAGES_PERMISSION,
                                Tables.PERSON.LAST_ONLINE_TIME,
                                Tables.PERSON.IS_BLOCKED,
                                Tables.PERSON.REG_DATE).
                        from(Tables.USER_AUTH.leftOuterJoin(Tables.PERSON).on(Tables.USER_AUTH.ID.eq(Tables.PERSON.ID)))
                        .where(Tables.USER_AUTH.EMAIL.eq(email))
                        .fetchOptional(record ->
                                AccountDto.builder()
                                        .isDeleted(false)
                                        .firstName(record.get(Tables.PERSON.FIRST_NAME))
                                        .lastName(record.get(Tables.PERSON.LAST_NAME))
                                        .email(record.get(Tables.USER_AUTH.EMAIL))
                                        .phone(record.get(Tables.PERSON.PHONE))
                                        .photo(record.get(Tables.PERSON.PHOTO))
                                        .profileCover("some link")
                                        .about(record.get(Tables.PERSON.ABOUT))
                                        .city(record.get(Tables.PERSON.TOWN))
                                        .country("Russia")
                                        .birthDate(record.get(Tables.PERSON.BIRTH_DATE))
                                        .messagePermission(record.get(Tables.PERSON.MESSAGES_PERMISSION))
                                        .lastOnlineTime(record.get(Tables.PERSON.LAST_ONLINE_TIME).toLocalDateTime())
                                        .isOnline(true)
                                        .isBlocked(record.get(Tables.PERSON.IS_BLOCKED))
                                        .emojiStatus("status")
                                        .createdOn(record.get(Tables.PERSON.REG_DATE).toLocalDateTime())
                                        .updatedOn(LocalDateTime.now())
                                        .build());
    }
}
