package com.ionixx.jooqmybatis.service.jooq;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import com.jooqatis.benchmark.jooqatis.jooq.Tables;
import com.jooqatis.benchmark.jooqatis.jooq.tables.pojos.City;
import com.jooqatis.benchmark.jooqatis.jooq.tables.pojos.Country;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;

@Service
public class JooqCountryService {
	
	private final MetricsDSLContext mDslContext;
	
	protected JooqCountryService(DSLContext mDslContext, MeterRegistry meterRegistry) {
		
		this.mDslContext = MetricsDSLContext				
				.withMetrics(mDslContext, meterRegistry, Tags.of("svc", "JooqCountryService"));
				
	}

    
    

    public List<Country> getCountryList() {
        return mDslContext.tag("op", "selectCountries").selectFrom(Tables.COUNTRY).fetchInto(Country.class);
    }

    public List<City> getCityList() {
        return mDslContext.tag("op", "selectCities").selectFrom(Tables.CITY).fetchInto(City.class);
    }

    public Object[] getCountryWithCities() {

        return mDslContext.tag("op", "selectCountriesWithCities").select().from(Tables.CITY).join(Tables.COUNTRY)
                .on(Tables.CITY.COUNTRYCODE.eq(Tables.COUNTRY.CODE)).fetchArray();
    }
}
