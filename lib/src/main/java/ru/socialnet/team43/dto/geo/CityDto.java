package ru.socialnet.team43.dto.geo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CityDto {
    private Long id;
    private boolean isDelete;
    private String title;
    private Long countryId;
}
