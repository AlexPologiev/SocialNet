package ru.socialnet.team43.service;


import jooq.db.tables.records.UserAuthRecord;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.RegDtoDb;
import ru.socialnet.team43.dto.UserAuthDto;
import java.util.Optional;

/**
 * сервис для работы с таблицей user_auth
 */
public interface UserAuthService
{
    Optional<UserAuthRecord> createUserAuth (RegDtoDb dto);

    Optional<UserAuthDto> getUserByEmail(String email);

    int getUsersCountByEmail(String email);

    Optional<PersonDto> getAccountInfo(String email);
    Optional<PersonDto> updateAccount(PersonDto dto);

    int deleteAccount(String email);


    void deleteUserAuthById(Long id);

}
