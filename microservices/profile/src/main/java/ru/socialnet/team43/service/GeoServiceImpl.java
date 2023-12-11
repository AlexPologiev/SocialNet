package ru.socialnet.team43.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.client.GeoFeignClient;
import ru.socialnet.team43.dto.geo.CityDto;
import ru.socialnet.team43.dto.geo.CountryDto;

import java.util.List;

@Service
@AllArgsConstructor
public class GeoServiceImpl implements GeoService {

    private GeoFeignClient feignClient;

    @Override
    public List<CountryDto> getCountry() {
        return feignClient.getAllCountry();
    }

    @Override
    public List<CityDto> getCitiesByCountryId(Long countryId) {
        return feignClient.getCitiesByCountryId(countryId);
    }
}