package ru.socialnet.team43.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserAuthDto {

    private long id;
    private String email;
    private String password;
    private Roles role;

}
