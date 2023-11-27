package ru.socialnet.team43.repository;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.socialnet.team43.jooq.db.Tables;
import ru.socialnet.team43.jooq.db.tables.records.CountryRecord;

import java.util.List;

@Repository
@AllArgsConstructor
public class CountryRepository {

    private DSLContext context;

    public List<CountryRecord> getCountries() {
        return context.selectFrom(Tables.COUNTRY)
                .where(Tables.COUNTRY.IS_DELETED.eq(false))
                .fetchInto(CountryRecord.class);
    }
}
