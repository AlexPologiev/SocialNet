package ru.socialnet.team43.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.dto.geo.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.socialnet.team43.service.GeoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geo")
@RequiredArgsConstructor
public class GeoController {

    private final GeoService geoService;

    @GetMapping("/country")
    public ResponseEntity<List<CountryDto>> getCountry() {
        return ResponseEntity.ok(geoService.getCountry());
    }
}