package ru.socialnet.team43.service;

import jooq.db.tables.records.PersonRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.repository.PersonRepo;
import ru.socialnet.team43.repository.UserAuthRepository;
import ru.socialnet.team43.repository.mapper.PersonDtoPersonRecordMapper;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final PersonRepo personRepo;
    private final PersonDtoPersonRecordMapper mapper;
    private final UserAuthRepository userAuthRepository;

    public Optional<PersonRecord> createPerson(PersonDto dto, long id)
    {
        PersonRecord recordToSave = mapper.PersonDtoToPersonRecord(dto);
        recordToSave.setId(id);
        return personRepo.insertPerson(recordToSave);
    }

}
