package ru.socialnet.team43.service.geo;

import ru.socialnet.team43.dto.geo.CityDto;
import ru.socialnet.team43.dto.geo.CountryDto;

import java.util.List;

public interface GeoServiceDB {
    List<CountryDto> getCountries();

    List<CityDto> getCitiesByCountryId(Long countryId);

    boolean load(List<CountryDto> countries);

    boolean checkEmpty();
}
