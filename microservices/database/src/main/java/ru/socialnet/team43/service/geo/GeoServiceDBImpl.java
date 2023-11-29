package ru.socialnet.team43.service.geo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.geo.CityDto;
import ru.socialnet.team43.dto.geo.CountryDto;
import jooq.db.tables.records.CountryRecord;
import ru.socialnet.team43.repository.CityRepository;
import ru.socialnet.team43.repository.CountryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GeoServiceDBImpl implements GeoServiceDB {

    private final CountryRepository countryRepo;
    private final CityRepository cityRepo;

    public List<CountryDto> getCountries() {
        List<CountryRecord> countries = countryRepo.getCountries();
        List<CityDto> cities = cityRepo.getCitiesByCountriesId(
                countries.stream().map(CountryRecord::getId).collect(Collectors.toList()));
        List<CountryDto> countryDtoList = new ArrayList<>();

        for (CountryRecord country : countries) {
            CountryDto countryDto = CountryDto.builder()
                    .id(country.getId())
                    .isDeleted(country.getIsDeleted())
                    .title(country.getTitle())
                    .cities(new ArrayList<>())
                    .build();
            countryDtoList.add(countryDto);
        }

        for (CountryDto country : countryDtoList) {
            List<CityDto> cityDtoList = country.getCities();
            for (CityDto city : cities) {
                if (country.getId().equals(city.getCountryId())) {
                    cityDtoList.add(city);
                }
            }
        }

        return countryDtoList;
    }
}
