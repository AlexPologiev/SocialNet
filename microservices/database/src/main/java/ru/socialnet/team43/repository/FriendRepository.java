package ru.socialnet.team43.repository;

import jooq.db.Tables;
import jooq.db.tables.Friendship;
import jooq.db.tables.Person;
import jooq.db.tables.UserAuth;
import jooq.db.tables.records.FriendshipRecord;
import jooq.db.tables.records.PersonRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.DatePart;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.socialnet.team43.dto.FriendDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.enums.FriendshipStatus;
import ru.socialnet.team43.repository.mapper.PersonDtoPersonRecordMapping;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.util.Optional.ofNullable;
import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FriendRepository {

    private final DSLContext dslContext;
    private static final int COUNT_FRIENDS_RECOMMENDATIONS = 10;
    private final UserInteraction userInteraction;
    private final PersonRepository personRepo;
    private final PersonDtoPersonRecordMapping mapper;
    private final Random random = new Random();

    public int getFriendsCount(String email) {
        Optional<Integer> count = ofNullable(dslContext.selectCount().from(Tables.FRIENDSHIP)
                .where(Tables.FRIENDSHIP.SRC_PERSON_ID.eq(userInteraction.findUserIdByEmail(email)))
                .and(Tables.FRIENDSHIP.FRIENDSHIP_STATUS.eq(FriendshipStatus.FRIEND.name()))
                .fetchOne(0, int.class));
        return count.orElse(0);
    }

    public List<FriendDto> getRecommendations(String email) {
        return dslContext.selectFrom(Tables.FRIENDSHIP)
                .where(Tables.FRIENDSHIP.FRIENDSHIP_STATUS.notEqual(FriendshipStatus.FRIEND.name()))
                .and(Tables.FRIENDSHIP.SRC_PERSON_ID.eq(
                        dslContext.select(Tables.PERSON.ID)
                                .from(Tables.PERSON)
                                .where(Tables.PERSON.USER_ID.eq(
                                        dslContext.select(Tables.USER_AUTH.ID)
                                                .from(Tables.USER_AUTH)
                                                .where(Tables.USER_AUTH.EMAIL.eq(email))))))
                .limit(COUNT_FRIENDS_RECOMMENDATIONS)
                .stream()
                .map(f -> new FriendDto(FriendshipStatus.valueOf(f.getFriendshipStatus()),
                        f.getDscPersonId(),
                        FriendshipStatus.NONE,
                        random.nextInt(10) + 1))
                .collect(Collectors.toList());
    }

    public List<Long> findFriendsIdsByStatus(String status, String email, Pageable page) {

        return dslContext.selectFrom(Tables.FRIENDSHIP)
                    .where(Tables.FRIENDSHIP.FRIENDSHIP_STATUS.eq(status))
                    .and(Tables.FRIENDSHIP.SRC_PERSON_ID.eq(
                            dslContext.select(Tables.PERSON.ID)
                                    .from(Tables.PERSON)
                                    .where(Tables.PERSON.USER_ID.eq(
                                            dslContext.select(Tables.USER_AUTH.ID)
                                                    .from(Tables.USER_AUTH)
                                                    .where(Tables.USER_AUTH.EMAIL.eq(email))))))
                    .limit(page.getPageSize())
                    .stream()
                    .map(FriendshipRecord::getDscPersonId)
                    .collect(Collectors.toList());
    }

    public List<PersonDto> searchFriends(String status,
                                    String firstName,
                                    Integer ageFrom,
                                    Integer ageTo,
                                    String country,
                                    String city,
                                    String email,
                                    Pageable page) {

        Condition searchCondition = getConditions(firstName,ageFrom,ageTo,country,city);

        return dslContext.selectFrom(Tables.PERSON)
                        .where(Tables.PERSON.ID.in(
                                dslContext.select(Tables.FRIENDSHIP.DSC_PERSON_ID)
                                        .from(Tables.FRIENDSHIP)
                                        .where(Tables.FRIENDSHIP.SRC_PERSON_ID.eq(
                                                dslContext.select(Tables.PERSON.ID)
                                                        .from(Tables.PERSON)
                                                        .where(Tables.PERSON.USER_ID.eq(
                                                                dslContext.select(Tables.USER_AUTH.ID)
                                                                        .from(Tables.USER_AUTH)
                                                                        .where(Tables.USER_AUTH.EMAIL.eq(email))))))
                                        .and(Tables.FRIENDSHIP.FRIENDSHIP_STATUS.eq(status))))
                        .and(searchCondition)
                        .limit(page.getPageSize())
                        .stream()
                        .map(mapper::PersonRecordToPersonDto)
                        .collect(Collectors.toList());
    }

    private Condition getConditions(String firstName,
                                    Integer ageFrom,
                                    Integer ageTo,
                                    String country,
                                    String city){

        Condition condition = trueCondition();

        if(!firstName.equals("")){
            condition = condition.and(Tables.PERSON.FIRST_NAME.equalIgnoreCase(firstName));
        }

        if (ageFrom != 0){
            condition = condition.and(Tables.PERSON.BIRTH_DATE.le(localDateTimeSub(
                    currentLocalDateTime(),
                    ageFrom,
                    DatePart.YEAR)));
        }

        if (ageTo != 99){
            condition = condition.and(Tables.PERSON.BIRTH_DATE.ge(localDateTimeSub(
                    currentLocalDateTime(),
                    ageTo,
                    DatePart.YEAR)));
        }

        if (!country.equals("")){
            condition = condition.and(Tables.PERSON.COUNTRY.eq(country));
        }

        if (!city.equals("")){
            condition = condition.and(Tables.PERSON.CITY.eq(city));
        }

        return condition;
    }

}
