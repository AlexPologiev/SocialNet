package ru.socialnet.team43.web.controller;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.socialnet.team43.client.ProfileClient;
import ru.socialnet.team43.dto.geo.CityDto;
import ru.socialnet.team43.dto.geo.CountryDto;
import ru.socialnet.team43.util.ControllerUtil;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/geo")
public class GeoController {

    private final ProfileClient profileClient;
    private final ControllerUtil controllerUtil;

    @GetMapping("/country")
    public ResponseEntity<List<CountryDto>> getCountry() {
        log.info("/country");
        return controllerUtil.createNewResponseEntity(profileClient.getCountry());
    }

    @GetMapping("/country/{countryId}/city")
    public ResponseEntity<List<CityDto>> getCitiesByCountryId(@PathVariable Long countryId) {
        log.info("/country/{}/city", countryId);
        return controllerUtil.createNewResponseEntity(profileClient.getCitiesByCountryId(countryId));
    }

    @PutMapping("/load")
    public ResponseEntity<Void> load() {
        log.info("/load");
        return controllerUtil.createNewResponseEntity(profileClient.load());
    }

    @ExceptionHandler(FeignException.class)
    private ResponseEntity<Void> handler(FeignException ex) {
        log.warn("Error in the profile", ex);
        return ResponseEntity.status(ex.status()).build();
    }
}
