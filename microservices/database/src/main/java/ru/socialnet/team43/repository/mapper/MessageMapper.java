package ru.socialnet.team43.repository.mapper;

import jooq.db.tables.records.MessageRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.socialnet.team43.dto.dialogs.MessageDto;
import ru.socialnet.team43.dto.dialogs.MessageShortDto;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(source = "authorId", target = "conversationPartner1")
    @Mapping(source = "recipientId", target = "conversationPartner2")
    @Mapping(target = "time", source = "time", qualifiedByName = "mapTime")
    MessageDto messageRecordToMessageDto(MessageRecord messageRecord);

    @Mapping(source = "conversationPartner1", target = "authorId")
    @Mapping(source = "conversationPartner2", target = "recipientId")
    @Mapping(target = "time", source = "time", qualifiedByName = "mapTime2")
    MessageRecord messageDtoToMessageRecord(MessageDto messageDto);

    @Mapping(source = "authorId", target = "conversationPartner1")
    @Mapping(source = "recipientId", target = "conversationPartner2")
    @Mapping(target = "time", source = "time", qualifiedByName = "mapTime")
    MessageShortDto messageRecordToMessageShortDto(MessageRecord messageRecord);

    List<MessageDto> mapToList(List<MessageRecord> messageRecordList);

    List<MessageShortDto> mapToShortList(List<MessageRecord> messageRecordList);

    @Named("mapTime")
    default ZonedDateTime mapTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime.atZoneSameInstant(ZoneId.systemDefault());
    }

    @Named("mapTime2")
    default OffsetDateTime mapTime2(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toOffsetDateTime();
    }
}
