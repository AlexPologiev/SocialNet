package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.PostDto;

import java.time.OffsetDateTime;
import java.util.List;

@FeignClient(name = "databaseClient", dismiss404 = true, url = "${database.url}" + "/api/v1/post")
public interface PostClient {

    @GetMapping
    List<PostDto> getAll(@RequestParam List<Long> ids,
                         @RequestParam List<Long> accountIds,
                         @RequestParam List<Long> blockedIds,
                         @RequestParam String author,
                         @RequestParam String text,
                         @RequestParam Boolean withFriends,
                         @RequestParam Boolean isBlocked,
                         @RequestParam Boolean isDeleted,
                         @RequestParam OffsetDateTime dateFrom,
                         @RequestParam OffsetDateTime dateTo,
                         @RequestParam String sort);

    @GetMapping("/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable Long id);

    @PostMapping
    PostDto addNewPost(@RequestBody PostDto postDto);

    @PutMapping
    ResponseEntity<Long> editPost(@RequestBody PostDto postDto);

    @DeleteMapping("/{id}")
    ResponseEntity<Long> deletePost(@PathVariable Long id);
}
