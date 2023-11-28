package ru.socialnet.team43.service;

import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.AccountDto;

import java.time.LocalDateTime;

@Service
public class AccountService {

    public AccountDto getMyProfile() {
        return AccountDto.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .eMail("qwerty@gmail.com")
                .phone("123456789")
                .photo("some photo")
                .profileCover("some link")
                .about("some info")
                .city("Moscow")
                .country("Russia")
                .birthDate(LocalDateTime.now())
                .messagePermission("lalala")
                .lastOnlineTime(LocalDateTime.now())
                .isOnline(true)
                .isBlocked(false)
                .emojiStatus("status")
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }
}
