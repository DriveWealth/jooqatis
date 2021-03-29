/**
 * DriveWealth, 2021
 */
package com.jooqatis.benchmark.jooqatis;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;

/**
 * <p>Title: AbstractBenchmark</p>
 * <p>Description:  </p>
 * <p>Author: nwhitehead (at) drivewealth dot it</p>
 * <p><code>com.jooqatis.benchmark.jooqatis.AbstractBenchmark</code></p>
 */

public abstract class AbstractBenchmark {
	protected final Logger LOG = LogManager.getLogger(getClass());
	protected final Supplier<Integer> jooqExecution;
	protected final Supplier<Integer> mybatExecution;
	protected final ExecutorService executorService;
	protected final MeterRegistry meterRegistry;
	protected final String benchmarkName = getClass().getSimpleName().replace("Benchmark", "")
			.replaceFirst("All", "")
			.toLowerCase();
	protected final String jooqType = "jooq";
	protected final String mybatType = "mybat";
	protected final Timer jooqTimer;
	protected final Timer mybatTimer;
	protected final Tags tags = Tags.of("benchmark", benchmarkName);
	
	protected static final double[] PERCENTILES = {0.5D, 0.75D, 0.9D, 0.99D};
	
	/**
	 * Creates a new AbstractBenchmark
	 * @param jooqCountryService
	 * @param myBatisCountryService
	 * @param executor
	 */
	public AbstractBenchmark(Supplier<Integer> jooqExecution, Supplier<Integer> myBatisExecution, MeterRegistry meterRegistry, ExecutorService executorService) {
		this.meterRegistry = meterRegistry;
		this.jooqExecution = jooqExecution;
		this.mybatExecution = myBatisExecution;
		this.executorService = executorService;
		jooqTimer = Timer.builder("call_elapsed_time")
			.publishPercentiles(PERCENTILES)
			.tags("benchmark", benchmarkName, "type", "jooq")
			.register(meterRegistry);
		mybatTimer = Timer.builder("call_elapsed_time")
				.publishPercentiles(PERCENTILES)
				.tags("benchmark", benchmarkName, "type", "mybat")
				.register(meterRegistry);
		
	}
	
	public long jooqWarmup(int loops, int concurrency) {
		return warmup(jooqType, jooqExecution, loops, concurrency);
	}
	
	public long jooqExecution(int loops, int concurrency) {
		return execute(jooqType, jooqExecution, loops, concurrency, jooqTimer);
	}
	
	public long mybatWarmup(int loops, int concurrency) {
		return warmup(mybatType, mybatExecution, loops, concurrency);
	}
	
	public long mybatExecution(int loops, int concurrency) {
		return execute(mybatType, mybatExecution, loops, concurrency, mybatTimer);
	}	
	
	
	private long warmup(String type, Supplier<Integer> benchEx, int loops, int concurrency) {
		LOG.info("Starting Warmup: bench={}, type={}, loops={}, concurrency={}", benchmarkName, type, loops, concurrency);
		final LongAdder counter = new LongAdder();
		final Callable<Void> task = () -> {
			for(int i = 0; i < loops; i++) {
				counter.add(benchEx.get());
			}
			return null;
		};
		CompletionService<Void> ecs = new ExecutorCompletionService<Void>(executorService);
		
		List<Future<Void>> futures = IntStream.range(0, concurrency)
			.mapToObj(idx -> ecs.submit(task))
			.collect(Collectors.toList());
		for(Future<Void> f : futures) {
			try {
				f.get();
			} catch (Exception ex) {
				LOG.info("Warmup Error: bench={}, type={}, loops={}, concurrency={}", benchmarkName, type, loops, concurrency, ex);
			}
		}
		LOG.info("Warmup Complete: bench={}, type={}, loops={}, concurrency={}", benchmarkName, type, loops, concurrency);
		return counter.longValue();
		
	}

	private long execute(String type, Supplier<Integer> benchEx, int loops, int concurrency, Timer timer) {
		LOG.info("Starting Execution: bench={}, type={}, loops={}, concurrency={}", benchmarkName, type, loops, concurrency);
		final LongAdder counter = new LongAdder();
		final Callable<Void> task = () -> {
			for(int i = 0; i < loops; i++) {
				long start = System.nanoTime();
				counter.add(benchEx.get());
				timer.record(System.nanoTime() - start, TimeUnit.NANOSECONDS);
			}
			return null;
		};
		CompletionService<Void> ecs = new ExecutorCompletionService<Void>(executorService);
		
		List<Future<Void>> futures = IntStream.range(0, concurrency)
			.mapToObj(idx -> ecs.submit(task))
			.collect(Collectors.toList());
		for(Future<Void> f : futures) {
			try {
				f.get();
			} catch (Exception ex) {
				LOG.info("Execution Error: bench={}, type={}, loops={}, concurrency={}", benchmarkName, type, loops, concurrency, ex);
			}
		}
		LOG.info("Execution Complete: bench={}, type={}, loops={}, concurrency={}", benchmarkName, type, loops, concurrency);
		return counter.longValue();
		
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return benchmarkName;
	}
	

}
