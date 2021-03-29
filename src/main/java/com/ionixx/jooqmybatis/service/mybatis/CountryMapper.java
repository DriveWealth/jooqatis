package com.ionixx.jooqmybatis.service.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.jooqatis.benchmark.jooqatis.jooq.tables.City;
import com.jooqatis.benchmark.jooqatis.jooq.tables.Country;

@Mapper
public interface CountryMapper {

    @Select(value="select * from country")
    List<Country> fetchAllCountry();

    @Select("select * from city")
    List<City> fetchAllCity();

    @Select("select * from city left join country c on c.code = city.countrycode")
    List<Object> fetchCountriesWithCities();
}
