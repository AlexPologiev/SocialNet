package ru.socialnet.team43.repository.mapper;

import jooq.db.tables.records.UserAuthRecord;
import org.mapstruct.Mapper;

import ru.socialnet.team43.dto.PersonDto;

@Mapper(componentModel = "spring")
public interface PersonDtoUserAuthRecordMapper
{
    UserAuthRecord personDtoUserAuthRecord(PersonDto regDto);
}
