package ru.socialnet.team43.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.socialnet.team43.dto.geo.CityDto;
import ru.socialnet.team43.dto.geo.CountryDto;

import java.util.List;

@FeignClient(name = "geo", url = "${database.url}" + "/geo")
public interface GeoFeignClient {

    @GetMapping("/country")
    List<CountryDto> getAllCountry();

    @GetMapping("/country/{countryId}/city")
    List<CityDto> getCitiesByCountryId(@PathVariable Long countryId);
}
