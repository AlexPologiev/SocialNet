package ru.socialnet.team43.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.CommunicationClient;
import ru.socialnet.team43.dto.PostDto;
import ru.socialnet.team43.util.ControllerUtil;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final CommunicationClient communicationClient;
    private final ControllerUtil controllerUtil;

    @GetMapping
    public ResponseEntity<Page<PostDto>> getAll(@RequestParam(required = false) List<Long> ids,
                                                @RequestParam(required = false) List<Long> accountsId,
                                                @RequestParam(required = false) List<Long> blockedIds,
                                                @RequestParam(required = false) String author,
                                                @RequestParam(required = false) String text,
                                                @RequestParam(defaultValue = "true") Boolean withFriends,
                                                @RequestParam(required = false) Boolean isBlocked,
                                                @RequestParam(defaultValue = "false") Boolean isDeleted,
                                                @RequestParam(required = false) OffsetDateTime dateFrom,
                                                @RequestParam(required = false) OffsetDateTime dateTo,
                                                @RequestParam(defaultValue = "time,desc") String sort,
                                                Pageable pageable) {
        ResponseEntity<Page<PostDto>> responseEntity = ResponseEntity.ok().body(communicationClient.getAll(ids, accountsId, blockedIds, author, text, withFriends, isBlocked, isDeleted, dateFrom, dateTo, sort, pageable));
        return controllerUtil.createNewResponseEntity(responseEntity);
    }
}
