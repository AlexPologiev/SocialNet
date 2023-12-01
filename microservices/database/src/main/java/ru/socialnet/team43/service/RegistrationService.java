package ru.socialnet.team43.service;

import jooq.db.tables.records.PersonRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.RegDtoDb;
import ru.socialnet.team43.repository.PersonRepo;
import ru.socialnet.team43.repository.mapper.PersonDtoPersonRecordMapper;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final PersonRepo personRepo;
    private final PersonDtoPersonRecordMapper mapper;

    public Optional<PersonRecord> createPerson(RegDtoDb regDtoDb, long id)
    {
        PersonRecord recordToSave = mapper.regDtoDbToPersonRecord(regDtoDb);
        recordToSave.setUserId(id);
        return personRepo.insertPerson(recordToSave);
    }

}
