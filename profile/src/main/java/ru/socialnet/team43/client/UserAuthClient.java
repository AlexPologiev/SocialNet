package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.socialnet.team43.dto.UserAuthDto;

@FeignClient(name = "userAuth",
        url = "/database/auth")
public interface UserAuthClient {

    @GetMapping("/user")
    UserAuthDto getUserByEmail(@RequestParam("email") String email);
}
