package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ru.socialnet.team43.dto.CaptchaDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.RegDto;
import ru.socialnet.team43.dto.geo.CityDto;
import ru.socialnet.team43.dto.geo.CountryDto;
import ru.socialnet.team43.dto.storage.StorageDto;

import java.util.List;

@FeignClient(name = "profileClient", dismiss404 = true, url = "${profile.url}" + "/api/v1")
public interface ProfileClient {

    @PostMapping("/auth/register")
    ResponseEntity<Void> RegistrationPerson(@RequestBody RegDto regDto);

    @GetMapping("/auth/captcha")
    ResponseEntity<CaptchaDto> getCaptcha();

    @GetMapping("/account/me")
    ResponseEntity<PersonDto> getMyProfile(@RequestParam("email") String email);

    @PutMapping("/account/me")
    ResponseEntity<PersonDto> updateMyProfile(@RequestBody PersonDto dto);

    @DeleteMapping("/account/me")
    ResponseEntity<Void> deleteMyProfile(@RequestParam("email") String email);

    @GetMapping("/geo/country")
    ResponseEntity<List<CountryDto>> getCountry();

    @GetMapping("geo/country/{countryId}/city")
    ResponseEntity<List<CityDto>> getCitiesByCountryId(@PathVariable Long countryId);
    @GetMapping("/account/{id}")
    ResponseEntity<PersonDto> getProfileById(@PathVariable Long id);

    @PostMapping(path = "/storage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<StorageDto> getStorage(@RequestBody MultipartFile file);
}
