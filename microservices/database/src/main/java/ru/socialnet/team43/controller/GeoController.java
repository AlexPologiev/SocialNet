package ru.socialnet.team43.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.socialnet.team43.dto.geo.CityDto;
import ru.socialnet.team43.dto.geo.CountryDto;
import ru.socialnet.team43.service.geo.GeoServiceDB;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/geo")
@AllArgsConstructor
public class GeoController {

    private GeoServiceDB geoService;

    @GetMapping("/country")
    public List<CountryDto> getCountry() {
        log.info("/country");
        return geoService.getCountries();
    }

    @GetMapping("/country/{countryId}/city")
    public List<CityDto> getCitiesByCountryId(@PathVariable Long countryId) {
        log.info("/country/{}/city", countryId);
        return geoService.getCitiesByCountryId(countryId);
    }

    @PutMapping("/load")
    public ResponseEntity<Void> load(@RequestBody List<CountryDto> countries) {
        log.info("/load Trying to insert data to the DB");
        if (geoService.load(countries)) {
            return ResponseEntity.ok().build();
        }
        log.warn("/load Data could not be inserted into the database");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/load/check")
    public boolean checkEmpty() {
        log.info("/load/check");
        return geoService.checkEmpty();
    }
}
