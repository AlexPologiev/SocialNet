package ru.socialnet.team43.repository;

import jooq.db.Tables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.enums.FriendshipStatus;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FriendRepository {

    private final DSLContext dslContext;
    private static final int COUNT_FRIENDS_RECOMMENDATIONS = 10;
    private final UserInteraction userInteraction;

    public int getFriendsCount(String email) {
        Optional<Integer> count = ofNullable(dslContext.selectCount().from(Tables.FRIENDSHIP)
                .where(Tables.FRIENDSHIP.SRC_PERSON_ID.eq(userInteraction.findUserIdByEmail(email)))
                .and(Tables.FRIENDSHIP.FRIENDSHIPSTATUS.eq(FriendshipStatus.FRIEND.name()))
                .fetchOne(0, int.class));
        return count.orElse(0);
    }

    public List<PersonDto> getRecommendations(String email) {

        return dslContext.select()
                .from(Tables.PERSON)
                .where(Tables.PERSON.ID.in(
                        dslContext
                                .select(Tables.FRIENDSHIP.DST_PERSON_ID)
                                .from(Tables.FRIENDSHIP)
                                .where(Tables.FRIENDSHIP.SRC_PERSON_ID.eq(userInteraction.findUserIdByEmail(email)))
                                .orderBy(DSL.rand())
                                .limit(COUNT_FRIENDS_RECOMMENDATIONS)
                ))
                .fetchInto(PersonDto.class);
    }
}
