package ru.socialnet.team43.dto.geo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CountryDto {
    private Long id;
    private boolean isDeleted;
    private String title;
    private List<CityDto> cities;
}
