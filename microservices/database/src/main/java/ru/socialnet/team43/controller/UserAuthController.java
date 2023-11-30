package ru.socialnet.team43.controller;

import jooq.db.tables.records.PersonRecord;
import jooq.db.tables.records.UserAuthRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.UserAuthDto;
import ru.socialnet.team43.service.RegistrationService;
import ru.socialnet.team43.service.UserAuthService;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private final RegistrationService regService;
    private final UserAuthService userAuthService;

    @PostMapping("/register/create")
    public ResponseEntity<PersonRecord> createPerson(@RequestBody PersonDto dto)
    {
        boolean isSuccessful = false;

        Optional<UserAuthRecord> userAuthRecord = userAuthService.createUserAuth(dto);

        if(userAuthRecord.isPresent())
        {
            long userId = userAuthRecord.get().getId();

            Optional<PersonRecord> person = regService.createPerson(dto, userId);

            isSuccessful = person.isPresent();
        }

        return isSuccessful ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/register/email")
    public ResponseEntity<Integer> getCountPersonByEmail(@RequestParam("email") String email) {

        int count = userAuthService.getUsersCountByEmail(email);
        return count > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/user")
    public ResponseEntity<UserAuthDto> getUser(@RequestParam("email") String email) {
        Optional<UserAuthDto> user = userAuthService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
