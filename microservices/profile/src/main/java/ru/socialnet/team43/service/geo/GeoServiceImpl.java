package ru.socialnet.team43.service.geo;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.socialnet.team43.client.GeoFeignClient;
import ru.socialnet.team43.client.LoadGeoClient;
import ru.socialnet.team43.dto.geo.CityDto;
import ru.socialnet.team43.dto.geo.CountryDto;
import ru.socialnet.team43.util.MapperGeoJson;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GeoServiceImpl implements GeoService {

    private GeoFeignClient feignClient;
    private LoadGeoClient loadGeoClient;

    @Override
    public List<CountryDto> getCountry() {
        return feignClient.getAllCountry();
    }

    @Override
    public List<CityDto> getCitiesByCountryId(Long countryId) {
        return feignClient.getCitiesByCountryId(countryId);
    }

    @Override
    public ResponseEntity<Void> load() {
        log.info("Trying to download updates.");
        String json = loadGeoClient.getData().getBody();

        MapperGeoJson mapper = new MapperGeoJson();
        try {
            List<CountryDto> dto = mapper.getCountryList(json);
            ResponseEntity<Void> response = feignClient.load(dto);
            log.info("Tables COUNTRY and CITY have been loaded successfully");
            return response;
        } catch (JsonProcessingException ex) {
            log.warn("{} cannot read the received object", mapper.getClass().getName(), ex);
            return ResponseEntity.status(422).build();
        }
    }
}
