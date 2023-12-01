package ru.socialnet.team43.service;

import jooq.db.tables.records.UserAuthRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.RegDtoDb;
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
    public Optional<UserAuthRecord> createUserAuth(RegDtoDb regDtoDb)
    {
        Optional<UserAuthRecord> saveResult = userAuthRepository.insertUserAuth(mapper.regDtoDbToUserAuthRecord(regDtoDb));
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
    public Optional<PersonDto> getAccountInfo(String email)
    {
        Optional<PersonDto> accountDtoOpt = userAuthRepository.getAccountInfo(email);
        return accountDtoOpt;
    }

    @Override
    public void deleteUserAuthById(Long id) {
        userAuthRepository.deleteUserAuthById(id);
    }
}
