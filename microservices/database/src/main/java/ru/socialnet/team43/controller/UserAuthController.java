package ru.socialnet.team43.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.UserAuthDto;
import jooq.db.tables.records.PersonRecord;
import ru.socialnet.team43.repository.UserAuthRepository;
import ru.socialnet.team43.service.RegistrationService;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserAuthController {
    private final RegistrationService regService;
    private UserAuthRepository authRepository;


    @PostMapping("/register/create")
    public ResponseEntity<PersonRecord> createPerson(@RequestBody PersonDto dto) {

        Optional<PersonRecord> person = regService.createPerson(dto);
        return person.isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/register/email")
    public ResponseEntity<Integer> getCountPersonByEmail(@RequestParam("email") String email) {

        int count = regService.getCountPersonByEmail(email);
        return count > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/user")
    public ResponseEntity<UserAuthDto> getUser(@RequestParam("email") String email) {
        Optional<UserAuthDto> user = authRepository.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
