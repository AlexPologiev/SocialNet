package ru.socialnet.team43.repository;

import ru.socialnet.team43.dto.geo.CityDto;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import jooq.db.Tables;

import java.util.List;

@Repository
@AllArgsConstructor
public class CityRepository {

    private DSLContext context;

    public List<CityDto> getCitiesByCountriesId(List<Long> countriesId) {
        return context.selectFrom(Tables.CITY)
                .where(Tables.CITY.COUNTRY_ID.in(countriesId))
                .and(Tables.CITY.IS_DELETED.eq(false))
                .fetchInto(CityDto.class);
    }

}
