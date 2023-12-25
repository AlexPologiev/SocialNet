package ru.socialnet.team43.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.client.PostClient;
import ru.socialnet.team43.dto.PostDto;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostClient postClient;

    @Override
    public Page<PostDto> getAll(List<Long> ids,
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
                                int offset, int limit, Pageable pageable) {
        List<PostDto> posts = postClient.getAll(ids, accountsId, blockedIds, author, text, withFriends,
                isBlocked, isDeleted, dateFrom, dateTo, sort);
        Pageable pageRequest = createPage(offset, limit);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), posts.size());

        List<PostDto> pageContent = posts.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, posts.size());
    }

    public Pageable createPage(int offset, int limit) {
        return PageRequest.of(offset, limit);
    }
}