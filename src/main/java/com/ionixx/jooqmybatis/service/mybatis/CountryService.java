package com.ionixx.jooqmybatis.service.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jooqatis.benchmark.jooqatis.jooq.tables.City;
import com.jooqatis.benchmark.jooqatis.jooq.tables.Country;

@Service
@Qualifier("MybatisCountryService")
public class CountryService {

    @Autowired
    CountryMapper mapper;

    public List<Country> getCountryList() {
        return mapper.fetchAllCountry();
    }

    public List<City> getCityList() {
        return mapper.fetchAllCity();
    }

    public List<Object> getCountryWithCities() {
        return mapper.fetchCountriesWithCities();
    }
}
