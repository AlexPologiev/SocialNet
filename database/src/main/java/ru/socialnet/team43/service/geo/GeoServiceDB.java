package ru.socialnet.team43.service.geo;

import ru.socialnet.team43.dto.geo.CountryDto;

import java.util.List;

public interface GeoServiceDB {
    List<CountryDto> getCountries();
}
