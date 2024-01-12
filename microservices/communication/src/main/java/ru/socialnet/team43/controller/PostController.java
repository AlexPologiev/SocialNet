package ru.socialnet.team43.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.PostClient;
import ru.socialnet.team43.dto.PostDto;
import ru.socialnet.team43.service.PostService;

import java.time.OffsetDateTime;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private PostService postService;
    private PostClient postClient;

    @GetMapping
    public ResponseEntity<Page<PostDto>> getAll(@RequestParam(required = false) List<Long> ids,
                                                @RequestParam(required = false) List<Long> accountIds,
                                                @RequestParam(required = false) List<Long> blockedIds,
                                                @RequestParam(required = false) String author,
                                                @RequestParam(required = false) String text,
                                                @RequestParam(required = false, defaultValue = "true") Boolean withFriends,
                                                @RequestParam(required = false) Boolean isBlocked,
                                                @RequestParam(required = false, defaultValue = "false")  Boolean isDeleted,
                                                @RequestParam(required = false) OffsetDateTime dateFrom,
                                                @RequestParam(required = false) OffsetDateTime dateTo,
                                                @RequestParam(required = false, defaultValue = "time,desc") String sort,
                                                @RequestParam(required = false, defaultValue = "0") int page,
                                                @RequestParam(required = false, defaultValue = "5") int size,
                                                Pageable pageable) {
        return ResponseEntity.ok().body(postService.getAll(ids, accountIds, blockedIds, author, text, withFriends, isBlocked, isDeleted, dateFrom, dateTo, sort, page, size, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id)  {
        return postClient.getPostById(id);
    }

    @PostMapping
    public PostDto addNewPost(@RequestBody PostDto postDto) {
        return postClient.addNewPost(postDto);
    }

    @PutMapping
    public ResponseEntity<Long> editPost(@RequestBody PostDto postDto) {
        return postClient.editPost(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long id) {
        return postClient.deletePost(id);
    }
}
