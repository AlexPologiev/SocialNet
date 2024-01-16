package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.socialnet.team43.dto.UserAuthDto;
import ru.socialnet.team43.dto.dialogs.DialogDto;

@FeignClient(name = "databaseClient", url = "${database.url}")
public interface DatabaseClient {

    @GetMapping("/dialogs")
    Page<DialogDto> getDialogs(@RequestParam String email, Pageable page);

    @GetMapping("/dialogs/unread")
    Integer getCountUnreadDialogs(@RequestParam String email);

    @GetMapping("/auth/user")
    UserAuthDto getUserByEmail(@RequestParam String email);
}
