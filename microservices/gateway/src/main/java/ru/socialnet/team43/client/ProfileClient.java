package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.socialnet.team43.dto.CaptchaDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.RegDto;
import ru.socialnet.team43.dto.geo.CountryDto;

import java.util.List;

@FeignClient(name = "profileClient",
        dismiss404 = true,
        url = "${profile.url}" + "/api/v1")
public interface ProfileClient
{

    @PostMapping("/auth/register")
    ResponseEntity<Void> RegistrationPerson(@RequestBody RegDto regDto);

    @GetMapping("/auth/captcha")
    ResponseEntity<CaptchaDto> getCaptcha();

    @GetMapping("/account/me")
    ResponseEntity<PersonDto> getMyProfile(@RequestParam("email") String email);

    @GetMapping("/geo/country")
    ResponseEntity<List<CountryDto>> getCountry();

}
