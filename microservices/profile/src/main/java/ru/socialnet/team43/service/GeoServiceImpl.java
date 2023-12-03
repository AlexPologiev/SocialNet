package ru.socialnet.team43.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.client.GeoFeignClient;
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
}