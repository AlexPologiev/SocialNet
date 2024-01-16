package ru.socialnet.team43.service;

import jooq.db.tables.records.PostRecord;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.socialnet.team43.dto.PostDto;
import ru.socialnet.team43.repository.PostRepository;
import ru.socialnet.team43.repository.TagRepository;
import ru.socialnet.team43.repository.Post2TagRepository;
import ru.socialnet.team43.repository.mapper.PostDtoPostRecordMapper;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PostService {

    private PostRepository postRepository;
    private TagRepository tagRepository;
    private Post2TagRepository post2TagRepository;
    private PostDtoPostRecordMapper mapper;

    public List<PostDto> getAll(List<Long> ids, List<Long> accountIds, List<Long> blockedIds,
                                String author, String text, Boolean withFriends,
                                Boolean isBlocked, Boolean isDeleted, OffsetDateTime dateFrom,
                                OffsetDateTime dateTo, String sort) {
        return postRepository.getAll(ids, accountIds, blockedIds, author, text, withFriends, isBlocked, isDeleted
                ,dateFrom, dateTo, sort);
    }

    public ResponseEntity<PostDto> getPostById(Long id) {
        Optional<PostRecord> optionalRecord = postRepository.getPostById(id);
        Optional<PostDto> postDto = optionalRecord.map(mapper::postRecordToPostDto);
        if (postDto.isPresent()) {
            PostDto post = postDto.get();
            return ResponseEntity.ok().body(post);
        }
        return ResponseEntity.notFound().build();
    }

    public PostDto addNewPost(PostDto postDto) {
        PostRecord postRecord = mapper.postDtoToPostRecordForCreate(postDto);
        Long postId = postRepository.addNewPost(postRecord);
        if (Objects.equals(postRecord.getType(), "QUEUED")) {
            addQueuedPost(postId, postDto);
        } else if (postId != 0L) {
            List<Long> tagsIds = getTagsIds(postDto);
            post2TagRepository.addNewRecords(postId, tagsIds);
            return postDto;
        }
        return null;
    }

    public ResponseEntity<Long> editPost(PostDto postDto) {
        if (postRepository.getPostById(postDto.getId()).isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            PostRecord postRecord = mapper.postDtoToPostRecordForEdit(postDto);
            postRepository.editPost(postRecord);
            return ResponseEntity.ok().body(postRecord.getId());
        }
    }

    public ResponseEntity<Long> deletePost(Long id) {
        if (postRepository.getPostById(id).isPresent()) {
            postRepository.deletePostById(id);
            return ResponseEntity.ok().body(id);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public void addQueuedPost(Long postId, PostDto postDto) {
        Date date = Date.from(postDto.getPublishDate().toInstant());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                postRepository.publishQueuedPost(postId);
                List<Long> tagsIds = getTagsIds(postDto);
                post2TagRepository.addNewRecords(postId, tagsIds);
                timer.cancel();
            }
        }, date);
    }

    public List<Long> getTagsIds(PostDto postDto) {
        return postDto.getTags().stream()
                .map(tag -> tagRepository.addNewTag(tag.getName()))
                .collect(Collectors.toList());
    }

}