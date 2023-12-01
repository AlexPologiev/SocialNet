package ru.socialnet.team43.repository;

import jooq.db.tables.records.UserAuthRecord;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.UserAuthDto;
import java.util.Optional;

/**
 * репозиторий для работы с таблицей user_auth
 */
public interface UserAuthRepository
{
    Optional<UserAuthDto> getUserByEmail(String email);

    int getUsersCountByEmail(String email);

    Optional<UserAuthRecord> insertUserAuth(UserAuthRecord userAuthRecord);

    Optional<PersonDto> getAccountInfo(String email);

    void deleteUserAuthById(Long id);
}