package ru.socialnet.team43.service;

import jooq.db.tables.records.PostRecord;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.socialnet.team43.dto.PostDto;
import ru.socialnet.team43.repository.PostRepository;
import ru.socialnet.team43.repository.mapper.PostDtoPostRecordMapper;

import java.time.OffsetDateTime;
import java.util.*;

@AllArgsConstructor
@Component
public class PostService {

    private PostRepository repository;
    private PostDtoPostRecordMapper mapper;

    public List<PostDto> getAll(List<Long> ids, List<Long> accountIds, List<Long> blockedIds,
                                String author, String text, Boolean withFriends,
                                Boolean isBlocked, Boolean isDeleted, OffsetDateTime dateFrom,
                                OffsetDateTime dateTo, String sort) {
        return repository.getAll(ids, accountIds, blockedIds, author, text, withFriends, isBlocked, isDeleted
                ,dateFrom, dateTo, sort);
    }

    public ResponseEntity<PostDto> getPostById(Long id) {
        Optional<PostRecord> optionalRecord = repository.getPostById(id);
        Optional<PostDto> postDto = optionalRecord.map(mapper::postRecordToPostDto);
        if (postDto.isPresent()) {
            PostDto post = postDto.get();
            return ResponseEntity.ok().body(post);
        }
        return ResponseEntity.notFound().build();
    }

    public PostDto addNewPost(PostDto postDto) {
        PostRecord postRecord = mapper.postDtoToPostRecordForCreate(postDto);
        boolean isQueued = postRecord.getPublishDate() != null;
        Optional<PostRecord> post = repository.addNewPost(postRecord);
        if (post.isPresent() && isQueued) {
            addQueuedPost(post.get());
        }
        if (post.isPresent()) {
            return postDto;
        } else {
            return null;
        }
    }

    public ResponseEntity<Long> editPost(PostDto postDto) {
        if (repository.getPostById(postDto.getId()).isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            PostRecord postRecord = mapper.postDtoToPostRecordForEdit(postDto);
            repository.editPost(postRecord);
            return ResponseEntity.ok().body(postRecord.getId());
        }
    }

    public ResponseEntity<Long> deletePost(Long id) {
        if (repository.getPostById(id).isPresent()) {
            repository.deletePostById(id);
            return ResponseEntity.ok().body(id);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public void addQueuedPost(PostRecord postRecord) {
        Date date = Date.from(postRecord.getPublishDate().toInstant());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repository.publishQueuedPost(postRecord);
                timer.cancel();
            }
        }, date);
    }

}
