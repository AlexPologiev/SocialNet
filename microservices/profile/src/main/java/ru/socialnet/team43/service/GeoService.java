package ru.socialnet.team43.service;

import org.springframework.http.ResponseEntity;
import ru.socialnet.team43.dto.geo.CountryDto;

import java.util.List;

public interface GeoService {
    List<CountryDto> getCountry();
}
