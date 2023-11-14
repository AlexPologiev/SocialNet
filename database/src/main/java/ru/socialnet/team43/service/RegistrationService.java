package ru.socialnet.team43.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.PersonDto;
import jooq.db.tables.records.PersonRecord;

import ru.socialnet.team43.repository.PersonRepo;
import ru.socialnet.team43.repository.mapper.PersonDtoPersonRecordMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final PersonRepo personRepo;
    private final PersonDtoPersonRecordMapper mapper;

    public Optional<PersonRecord> createPerson(PersonDto dto){
        return personRepo.insertPerson(mapper.PersonDtoToPersonRecord(dto));
    }

    public int getCountPersonByEmail(String email){
        return personRepo.getCountPersonsByEmail(email);
    }

}
