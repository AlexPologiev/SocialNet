package ru.socialnet.team43.service;

import jooq.db.tables.records.UserAuthRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.AccountDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.UserAuthDto;
import ru.socialnet.team43.repository.UserAuthRepository;
import ru.socialnet.team43.repository.mapper.PersonDtoUserAuthRecordMapper;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService
{
    private final UserAuthRepository userAuthRepository;
    private final PersonDtoUserAuthRecordMapper mapper;

    @Override
    public Optional<UserAuthRecord> createUserAuth(PersonDto dto)
    {
        Optional<UserAuthRecord> saveResult = userAuthRepository.insertUserAuth(mapper.personDtoUserAuthRecord(dto));
        return saveResult;
    }

    @Override
    public Optional<UserAuthDto> getUserByEmail(String email)
    {
        return userAuthRepository.getUserByEmail(email);
    }

    @Override
    public int getUsersCountByEmail(String email)
    {
        return userAuthRepository.getUsersCountByEmail(email);
    }

    @Override
    public Optional<AccountDto> getAccountInfo(String email)
    {
        Optional<AccountDto> accountDtoOpt = userAuthRepository.getAccountInfo(email);
        return accountDtoOpt;
    }

}
