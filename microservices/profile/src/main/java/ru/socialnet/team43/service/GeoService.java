package ru.socialnet.team43.service;

import ru.socialnet.team43.dto.geo.CityDto;
import ru.socialnet.team43.dto.geo.CountryDto;

import java.util.List;

public interface GeoService {
    List<CountryDto> getCountry();

    List<CityDto> getCitiesByCountryId(Long countryId);
}
