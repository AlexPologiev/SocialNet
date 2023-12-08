package ru.socialnet.team43.service;

import jooq.db.tables.records.PersonRecord;
import jooq.db.tables.records.UserAuthRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.RegDtoDb;
import ru.socialnet.team43.dto.UserAuthDto;
import ru.socialnet.team43.repository.PersonRepo;
import ru.socialnet.team43.repository.UserAuthRepository;
import ru.socialnet.team43.repository.mapper.PersonDtoPersonRecordMapping;
import ru.socialnet.team43.repository.mapper.PersonDtoUserAuthRecordMapper;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
    private final UserAuthRepository userAuthRepository;
    private final PersonRepo personRepo;
    private final PersonDtoUserAuthRecordMapper mapper;
    private final PersonDtoPersonRecordMapping personMapper;

    @Override
    public Optional<UserAuthRecord> createUserAuth(RegDtoDb regDtoDb) {
        return userAuthRepository.insertUserAuth(mapper.regDtoDbToUserAuthRecord(regDtoDb));
    }

    @Override
    public Optional<UserAuthDto> getUserByEmail(String email) {
        return userAuthRepository.getUserByEmail(email);
    }

    @Override
    public int getUsersCountByEmail(String email) {
        return userAuthRepository.getUsersCountByEmail(email);
    }

    @Override
    public Optional<PersonDto> getAccountInfo(String email) {
        return userAuthRepository.getAccountInfo(email);
    }

    @Override
    public Optional<PersonDto> updateAccount(PersonDto dto) {
        PersonRecord record = personMapper.PersonDtoToPersonRecord(dto);
        String email = dto.getEmail();
        Optional<PersonRecord> resultRecord = personRepo.updatePerson(record, email);
        log.info("Update person with email: {}", dto.getEmail());
        return resultRecord.map(personMapper::PersonRecordToPersonDto);
    }

    @Override
    public int deleteAccount(String email) {
        int result = personRepo.deletePerson(email);
        log.info("delete person with email: {}, modify {} fields", email, result);
        return result;
    }


    @Override
    public void deleteUserAuthById(Long id) {
        userAuthRepository.deleteUserAuthById(id);
    }
}
