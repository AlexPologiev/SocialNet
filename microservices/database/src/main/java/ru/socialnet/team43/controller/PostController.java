package ru.socialnet.team43.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.PostDto;
import ru.socialnet.team43.repository.PostRepository;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private PostRepository repository;

    @PostMapping
    public List<PostDto> getAll(@RequestParam(required = false) List<Long> ids,
                                @RequestParam(required = false) List<Long> accountsId,
                                @RequestParam(required = false) List<Long> blockedIds,
                                @RequestParam(required = false) String author,
                                @RequestParam(required = false) String text,
                                @RequestParam(required = false, defaultValue = "true") Boolean withFriends,
                                @RequestParam(required = false) Boolean isBlocked,
                                @RequestParam(required = false, defaultValue = "false")  Boolean isDeleted,
                                @RequestParam(required = false) OffsetDateTime dateFrom,
                                @RequestParam(required = false) OffsetDateTime dateTo,
                                @RequestParam(required = false, defaultValue = "time,desc") String sort) {
        return repository.getAll(ids, accountsId, blockedIds, author, text, withFriends, isBlocked, isDeleted
                                ,dateFrom, dateTo, sort);
    }
}
