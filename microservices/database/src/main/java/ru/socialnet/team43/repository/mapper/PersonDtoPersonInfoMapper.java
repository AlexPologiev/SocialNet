package ru.socialnet.team43.repository.mapper;

import org.jooq.Record;
import org.jooq.RecordMapper;
import ru.socialnet.team43.dto.PersonDto;

public interface PersonDtoPersonInfoMapper extends RecordMapper<Record, PersonDto>
{

    @Override
    PersonDto map(Record record);
}
