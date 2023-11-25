package ru.socialnet.team43.dto;

import lombok.Builder;
import lombok.Data;


import java.util.UUID;

@Builder
@Data
public class RegDto {
    private final boolean isDeleted;
    private final String email;
    private final String password1;
    private final String password2;
    private final String firstName;
    private final String lastName;
    private final String captchaCode;
    private final String captchaSecret;

}
