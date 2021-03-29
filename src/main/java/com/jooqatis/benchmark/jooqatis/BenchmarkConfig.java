/**
 * DriveWealth, 2021
 */
package com.jooqatis.benchmark.jooqatis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * <p>Title: BenchmarkConfig</p>
 * <p>Description:  </p>
 * <p>Author: nwhitehead (at) drivewealth dot it</p>
 * <p><code>com.jooqatis.benchmark.jooqatis.BenchmarkConfig</code></p>
 */
@Configuration
public class BenchmarkConfig {
	protected final MeterRegistry registry;
	/**
	 * Creates a new BenchmarkConfig
	 */
	public BenchmarkConfig(MeterRegistry registry) {
		this.registry = registry;
	}
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();
		config.setMetricRegistry(registry);
		config.setReadOnly(true);
	    return config;
	}
}
