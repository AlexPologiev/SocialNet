package ru.socialnet.team43.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Builder
@Data
public class PersonDto {

    private String firstName;
    private String lastName;
    private OffsetDateTime regDate;
    private LocalDateTime birthDate;
    private String EMail;
    private String phone;
    private String password;
    private String photo;
    private String about;
    private String town;
    private String confirmationCode;
    private Boolean isApproved;
    private String messagesPermission;
    private OffsetDateTime lastOnlineTime;
    private Boolean isBlocked;


}
