package ru.socialnet.team43.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class AccountDto {

    private boolean isDeleted;
    private String firstName;
    private String lastName;
    private String eMail;
    private String phone;
    private String photo;
    private String profileCover;
    private String about;
    private String city;
    private String country;
    private String statusCode;
    private LocalDateTime birthDate;
    private String messagePermission;
    private LocalDateTime lastOnlineTime;
    private boolean isOnline;
    private boolean isBlocked;
    private String emojiStatus;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}

