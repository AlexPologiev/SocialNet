package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.CommentDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;

import ru.socialnet.team43.dto.PostDto;
import ru.socialnet.team43.dto.dialogs.DialogDto;
import ru.socialnet.team43.dto.dialogs.MessageDto;
import ru.socialnet.team43.dto.dialogs.MessageShortDto;
import ru.socialnet.team43.dto.dialogs.UnreadCountDto;
import ru.socialnet.team43.dto.TagDto;

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

    @GetMapping("post/{postId}/comment")
    ResponseEntity<Page<CommentDto>> getComments(@PathVariable Long postId,
                                                 @RequestParam(required = false, defaultValue = "false") Boolean isDeleted,
                                                 @RequestParam(required = false, defaultValue = "time,desc") String sort,
                                                 Pageable pageable);

    @GetMapping("post/{postId}/comment/{commentId}/subcomment")
    ResponseEntity<Page<CommentDto>> getSubComments(@PathVariable Long postId,
                                                    @PathVariable Long commentId,
                                                    @RequestParam(required = false, defaultValue = "false") Boolean isDeleted,
                                                    @RequestParam(required = false, defaultValue = "time,desc") String sort,
                                                    Pageable pageable);

    @PostMapping("post/{postId}/comment")
    ResponseEntity<CommentDto> addNewComment(@PathVariable Long postId,
                                             @RequestBody CommentDto commentDto);

    @PutMapping("/post/{postId}/comment")
    ResponseEntity<CommentDto> editComment(@PathVariable Long postId,
                                           @RequestBody CommentDto commentDto);

    @DeleteMapping("post/{postId}/comment/{commentId}")
    ResponseEntity<Long> deleteComment(@PathVariable Long postId,
                                       @PathVariable Long commentId);


    @GetMapping("/tag")
    ResponseEntity<List<TagDto>> getByName(@RequestParam String name);

    @GetMapping("/dialogs")
    ResponseEntity<Page<DialogDto>> getDialogs(@RequestParam String email, Pageable page);

    @GetMapping("/dialogs/unread")
    ResponseEntity<UnreadCountDto> getCountUnreadDialogs(@RequestParam String email);

    @GetMapping("/dialogs/recipientId")
    ResponseEntity<DialogDto> getDialogByRecipientId(
            @RequestParam Long id, @RequestParam String email);

    @GetMapping("/dialogs/messages")
    ResponseEntity<Page<MessageShortDto>> getMessagesByRecipientId(
            @RequestParam Long recipientId, @RequestParam String email, Pageable page);

    @PutMapping("/dialogs/{dialogId}")
    boolean putDialog(@PathVariable Long dialogId, @RequestParam String email);

    @PostMapping("/dialogs/messages/save")
    boolean saveMessage(@RequestBody MessageDto messageDto);
}
