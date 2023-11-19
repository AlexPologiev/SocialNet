package ru.socialnet.team43.repository.mapper;

import org.mapstruct.Mapper;
import ru.socialnet.team43.dto.UserAuthDto;
import ru.socialnet.team43.jooq.db.tables.records.UserAuthRecord;

@Mapper(componentModel = "spring")
public interface UserAuthMapper {

    UserAuthDto userRecordToDtoMapper(UserAuthRecord record);
}
