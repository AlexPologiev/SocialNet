package ru.socialnet.team43.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.dto.geo.CountryDto;
import ru.socialnet.team43.service.geo.GeoServiceDB;

import java.util.List;

@RestController
@RequestMapping("/geo")
@AllArgsConstructor
public class GeoController {

    private GeoServiceDB geoService;

    @GetMapping("/country")
    public ResponseEntity<List<CountryDto>> getCountry() {
        return ResponseEntity.ok(geoService.getCountries());
    }
}
