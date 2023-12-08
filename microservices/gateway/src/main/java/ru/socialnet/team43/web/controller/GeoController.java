package ru.socialnet.team43.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.socialnet.team43.client.ProfileClient;
import ru.socialnet.team43.dto.geo.CountryDto;
import ru.socialnet.team43.util.ControllerUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/geo")
public class GeoController {

    private final ProfileClient profileClient;
    private final ControllerUtil controllerUtil;

    @GetMapping("/country")
    public ResponseEntity<List<CountryDto>> getCountry() {
        return controllerUtil.createNewResponseEntity(profileClient.getCountry());
    }
}
