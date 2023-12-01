package ru.socialnet.team43.repository.mapper;

import jooq.db.Tables;
import org.jooq.Record;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.StatusCode;


@Component
public class PersonDtoPersonInfoMapperImpl implements PersonDtoPersonInfoMapper
{

    @Override
    public  PersonDto map(Record record)
    {
        PersonDto personDto = new PersonDto();

        personDto.setFirstName(record.get(Tables.PERSON.FIRST_NAME));
        personDto.setLastName(record.get(Tables.PERSON.LAST_NAME));
        personDto.setEmail(record.get(Tables.USER_AUTH.EMAIL));
        personDto.setPassword(record.get(Tables.USER_AUTH.PASSWORD));
        personDto.setPhone(record.get(Tables.PERSON.PHONE));
        personDto.setPhoto(record.get(Tables.PERSON.PHOTO));
        personDto.setProfileCover(record.get(Tables.PERSON.PROFILE_COVER));
        personDto.setAbout(record.get(Tables.PERSON.ABOUT));
        personDto.setCity(record.get(Tables.PERSON.CITY));
        personDto.setCountry(record.get(Tables.PERSON.COUNTRY));
        personDto.setRegDate(record.get(Tables.PERSON.REG_DATE));

        String statusCodeStr = record.get(Tables.PERSON.STATUS_CODE);
        if(StringUtils.hasText(statusCodeStr)){
            personDto.setStatusCode(StatusCode.valueOf(statusCodeStr));
        }
        personDto.setBirthDate(record.get(Tables.PERSON.BIRTH_DATE));
        personDto.setMessagePermission(record.get(Tables.PERSON.MESSAGES_PERMISSION));
        personDto.setLastOnlineTime(record.get(Tables.PERSON.LAST_ONLINE_TIME));
        personDto.setIsOnline(record.get(Tables.PERSON.IS_ONLINE));
        personDto.setIsBlocked(record.get(Tables.PERSON.IS_BLOCKED));
        personDto.setEmojiStatus(record.get(Tables.PERSON.EMOJI_STATUS));
        personDto.setDeletionTimestamp(record.get(Tables.PERSON.DELETION_TIMESTAMP));
        personDto.setCreatedDate(record.get(Tables.PERSON.CREATED_DATE));
        personDto.setLastModifiedDate(record.get(Tables.PERSON.LAST_MODIFIED_DATE));

        return personDto;
    }
}
