package ru.socialnet.team43.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.socialnet.team43.dto.enums.StatusCode;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String photo;
    private String profileCover;
    private String about;
    private String city;
    private String country;
    private LocalDateTime regDate;
    private StatusCode statusCode;
    private LocalDateTime birthDate;
    private String messagePermission;
    private LocalDateTime lastOnlineTime;
    private Boolean isOnline;
    private Boolean isBlocked;
    private String emojiStatus;
    private LocalDateTime deletionTimestamp;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
