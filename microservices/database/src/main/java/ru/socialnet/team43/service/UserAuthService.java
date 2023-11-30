package ru.socialnet.team43.service;


import jooq.db.tables.records.UserAuthRecord;
import ru.socialnet.team43.dto.AccountDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.UserAuthDto;
import java.util.Optional;

/**
 * сервис для работы с таблицей user_auth
 */
public interface UserAuthService
{
    Optional<UserAuthRecord> createUserAuth (PersonDto dto);

    Optional<UserAuthDto> getUserByEmail(String email);

    int getUsersCountByEmail(String email);

    Optional<AccountDto> getAccountInfo(String email);
}
