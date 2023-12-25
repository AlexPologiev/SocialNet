package ru.socialnet.team43.repository;

import jooq.db.Tables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.OrderField;
import org.springframework.stereotype.Repository;
import ru.socialnet.team43.dto.PostDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import static org.jooq.impl.DSL.*;

@AllArgsConstructor
@Repository
@Slf4j
public class PostRepository {

    private final DSLContext context;

    public List<PostDto> getAll(List<Long> ids, List<Long> accountsId,
                                List<Long> blockedIds, String author,
                                String text, Boolean withFriends,
                                Boolean isBlocked, Boolean isDeleted,
                                OffsetDateTime dateFrom, OffsetDateTime dateTo,
                                String sort) {
        return context.select(asterisk()).from(Tables.POST)
                .where(getAllPostCondition(ids, accountsId, blockedIds, author,
                        text, withFriends, isBlocked, isDeleted, dateFrom, dateTo))
                .orderBy((OrderField<?>) sorted(sort))
                .fetchInto(PostDto.class);
    }

    public Condition getAllPostCondition(List<Long> ids, List<Long> accountsId,
                                         List<Long> blockedIds, String author,
                                         String text, Boolean withFriends,
                                         Boolean isBlocked, Boolean isDeleted,
                                         OffsetDateTime dateFrom, OffsetDateTime dateTo) {

        Condition condition = Tables.POST.IS_DELETED.eq(isDeleted);

        if (ids != null) {
            condition = condition.and(Tables.POST.ID.in(ids));
        }
        if (accountsId != null) {
            condition = condition.and(Tables.POST.AUTHOR_ID.in(accountsId));
        }
        if (blockedIds != null) {
            condition = condition.and(Tables.POST.AUTHOR_ID.in(blockedIds));
        }
        if (author != null) {
            condition = condition.and(Tables.POST.AUTHOR_ID.in(authorsIdsByFullName(author)));
        }
        if (text != null) {
            condition = condition.and(Tables.POST.POST_TEXT.like("%" + text + "%"));
        }
        if (isBlocked != null) {
            condition = condition.and(Tables.POST.IS_BLOCKED.eq(isBlocked));
        }
        if (dateFrom != null) {
            condition = condition.and(Tables.POST.TIME.greaterOrEqual(dateFrom));
        }
        if (dateTo != null) {
            condition = condition.and(Tables.POST.TIME.lessOrEqual(dateTo));
        }
        return condition;
    }

    public List<Long> authorsIdsByFullName(String author) {
        return context.select(Tables.PERSON.ID).from(Tables.PERSON)
                .where(concat(Tables.PERSON.FIRST_NAME, Tables.PERSON.LAST_NAME).like("%" + author + "%"))
                .fetchInto(Long.class);
    }

    public Object sorted(String sort) {
        Object sortField = null;
        String[] params = sort.split(",+");
        if (Objects.equals(params[0], "time")) {
            if (Objects.equals(params[1], "desc")) {
                sortField = Tables.POST.TIME_CHANGED.desc();
            } else {
                sortField = Tables.POST.TIME_CHANGED.asc();
            }
        }
        return sortField;
    }

}
