package ru.socialnet.team43.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CaptchaDto {

    private String captchaCode;
    private String captchaSecret;

}
