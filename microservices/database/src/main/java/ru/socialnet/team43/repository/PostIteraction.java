package ru.socialnet.team43.repository;

import jooq.db.tables.records.PostRecord;
import org.jooq.Condition;
import ru.socialnet.team43.dto.PostDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface PostIteraction {

    List<PostDto> getAll(List<Long> ids, List<Long> accountsId,
                                List<Long> blockedIds, String author,
                                String text, Boolean withFriends,
                                Boolean isBlocked, Boolean isDeleted,
                                OffsetDateTime dateFrom, OffsetDateTime dateTo,
                                String sort);

    Condition getAllPostCondition(List<Long> ids, List<Long> accountIds,
                                  List<Long> blockedIds, String author,
                                  String text, Boolean withFriends,
                                  Boolean isBlocked, Boolean isDeleted,
                                  OffsetDateTime dateFrom, OffsetDateTime dateTo);

    List<Long> authorsIdsByFullName(String author);

    Object sorted(String sort);

    Optional<PostRecord> getPostById(Long id);

    Long addNewPost(PostRecord postRecord);

    void publishQueuedPost(Long id);

    void editPost(PostRecord postRecord);

    void deletePostById(Long id);
}
