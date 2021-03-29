/**
 * DriveWealth, 2021
 */
package com.jooqatis.benchmark.jooqatis;

import java.util.concurrent.ExecutorService;

import org.springframework.stereotype.Service;

import com.ionixx.jooqmybatis.service.jooq.JooqCountryService;
import com.ionixx.jooqmybatis.service.mybatis.CountryService;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * <p>Title: AllCitiesBenchmark</p>
 * <p>Description:  </p>
 * <p>Author: nwhitehead (at) drivewealth dot it</p>
 * <p><code>com.jooqatis.benchmark.jooqatis.AllCitiesBenchmark</code></p>
 */
@Service
public class AllCitiesBenchmark extends AbstractBenchmark {

	
	/**
	 * Creates a new AllCountriesBenchmark
	 * @param meterRegistry
	 * @param jooqCountryService
	 * @param myBatisCountryService
	 */
	public AllCitiesBenchmark(
		MeterRegistry meterRegistry,
		JooqCountryService jooqCountryService,
		CountryService myBatisCountryService, 
		ExecutorService executorService		
		) {
		super(() -> jooqCountryService.getCityList().size(), () -> myBatisCountryService.getCityList().size(), meterRegistry, executorService);
	}


}
