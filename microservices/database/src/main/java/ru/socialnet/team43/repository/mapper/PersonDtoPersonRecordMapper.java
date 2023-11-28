package ru.socialnet.team43.repository.mapper;

import org.mapstruct.Mapper;
import ru.socialnet.team43.dto.PersonDto;
import jooq.db.tables.records.PersonRecord;

@Mapper(componentModel = "spring")
public interface PersonDtoPersonRecordMapper {

    PersonRecord PersonDtoToPersonRecord(PersonDto regDto);
}
