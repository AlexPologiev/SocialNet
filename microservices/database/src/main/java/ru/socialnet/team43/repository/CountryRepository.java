package ru.socialnet.team43.repository;

import jooq.db.Tables;
import jooq.db.tables.records.CountryRecord;

import lombok.AllArgsConstructor;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

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

    public void truncateCountry() {
        context.truncate(Tables.COUNTRY).execute();
    }

    public void insertCountries(List<CountryRecord> countries) {
        context.batchInsert(countries).execute();
    }

    public Long getCountCountries() {
        return context.selectCount().from(Tables.COUNTRY).fetchOne(0, Long.class);
    }
}
