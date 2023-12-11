package ru.socialnet.team43.repository;

import jooq.db.Tables;
import jooq.db.tables.records.PersonRecord;
import jooq.db.tables.records.UserAuthRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.UserAuthDto;
import ru.socialnet.team43.repository.mapper.PersonDtoPersonInfoMapper;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserAuthRepositoryImpl implements UserAuthRepository
{
    private final DSLContext dslContext;

    private final PersonDtoPersonInfoMapper personDtoPersonInfoMapper;

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
    public Optional<PersonDto> getAccountInfo(String email)
    {
        return dslContext
                        .select().
                        from(Tables.USER_AUTH.leftOuterJoin(Tables.PERSON).on(Tables.USER_AUTH.ID.eq(Tables.PERSON.USER_ID)))
                        .where(Tables.USER_AUTH.EMAIL.eq(email))
                        .fetchOptionalInto(PersonDto.class);
    }

    @Override
    public void deleteUserAuthById(Long id) {
        int result = dslContext
                            .deleteFrom(Tables.USER_AUTH)
                            .where(Tables.USER_AUTH.ID.eq(id))
                            .execute();
    }




}
