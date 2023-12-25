package ru.socialnet.team43.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.socialnet.team43.dto.PostDto;

import java.time.OffsetDateTime;
import java.util.List;


public interface PostService {

    Page<PostDto> getAll(List<Long> ids,
                         List<Long> accountsId,
                         List<Long> blockedIds,
                         String author,
                         String text,
                         Boolean withFriends,
                         Boolean isBlocked,
                         Boolean isDeleted,
                         OffsetDateTime dateFrom,
                         OffsetDateTime dateTo,
                         String sort,
                         int page, int size, Pageable pageable);
}
