package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.PostDto;

import java.time.OffsetDateTime;
import java.util.List;

@FeignClient(name = "communicationClient",
        dismiss404 = true,
        url = "${communication.url}" + "/api/v1")
public interface CommunicationClient {

    @GetMapping("/post")
    Page<PostDto> getAll(@RequestParam List<Long> ids,
                         @RequestParam List<Long> accountIds,
                         @RequestParam List<Long> blockedIds,
                         @RequestParam String author,
                         @RequestParam String text,
                         @RequestParam Boolean withFriends,
                         @RequestParam Boolean isBlocked,
                         @RequestParam Boolean isDeleted,
                         @RequestParam OffsetDateTime dateFrom,
                         @RequestParam OffsetDateTime dateTo,
                         @RequestParam String sort,
                         Pageable pageable);

    @GetMapping("/post/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable Long id);

    @PostMapping("/post")
    PostDto addNewPost(@RequestBody PostDto postDto);

    @PutMapping("/post")
    ResponseEntity<Long> editPost(@RequestBody PostDto postDto);

    @DeleteMapping("post/{id}")
    ResponseEntity<Long> deletePost(@PathVariable Long id);
}
