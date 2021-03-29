/**
 * DriveWealth, 2021
 */
package com.jooqatis.benchmark.jooqatis;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * <p>Title: BenchmarkRunner</p>
 * <p>Description:  </p>
 * <p>Author: nwhitehead (at) drivewealth dot it</p>
 * <p><code>com.jooqatis.benchmark.jooqatis.BenchmarkRunner</code></p>
 */
@Service
public class BenchmarkRunner {
	protected final Logger LOG = LogManager.getLogger(getClass());
	private static final String APP_CTX_ID = "application";
	protected final List<AbstractBenchmark> benchmarks;
	
	protected final RunConfig runConfig; 	
	/**
	 * Creates a new BenchmarkRunner
	 * @param benchmarks A list of registered benchmarks
	 */
	public BenchmarkRunner(List<AbstractBenchmark> benchmarks, RunConfig runConfig) {
		this.benchmarks = benchmarks;
		this.runConfig = runConfig;
		String b = benchmarks.stream()
			.map(a -> a.getClass().getSimpleName())
			.collect(Collectors.joining(","));
		LOG.info("Benchmarks: {}", b);
		LOG.info("RunConfig: {}", runConfig);
	}
	
	@EventListener(classes = ContextRefreshedEvent.class)
	public void start(ContextRefreshedEvent event) {
		if (APP_CTX_ID.equals(event.getApplicationContext().getId())) {
			Thread t = new Thread("BenchmarkMaster") {
				public void run() {
					LOG.info("Starting Benchmark Warmup: {}", event.getApplicationContext().getId());
					for(AbstractBenchmark benchmark: benchmarks) {
						runWarmup(benchmark);
					}
					LOG.info("Benchmark Warmup Complete");
					LOG.info("Starting Benchmark: {}", event.getApplicationContext().getId());
					for(AbstractBenchmark benchmark: benchmarks) {
						runBenchmark(benchmark);
					}
					LOG.info("Benchmark Complete");					
					
				}
			};
			t.setDaemon(true);
			t.start();
		}
	}
	
	protected void runWarmup(AbstractBenchmark benchmark) {
		String benchmarkName = benchmark.toString();
		LOG.info("\n\t===================\n\tStarting Warmup: {}\n\t===================", benchmarkName);
		benchmark.jooqWarmup(runConfig.getWarmupLoops(), runConfig.getWarmupConcurrency());
		benchmark.mybatWarmup(runConfig.getWarmupLoops(), runConfig.getWarmupConcurrency());
		LOG.info("Warmup Complete: {}", benchmarkName);
	}
	
	protected void runBenchmark(AbstractBenchmark benchmark) {
		String benchmarkName = benchmark.toString();
		LOG.info("\n\t===================\n\tStarting Run: {}\n\t===================", benchmarkName);
		benchmark.jooqExecution(runConfig.getRunLoops(), runConfig.getRunConcurrency());
		benchmark.mybatExecution(runConfig.getRunLoops(), runConfig.getRunConcurrency());
		LOG.info("Run Complete: {}", benchmarkName);		
	}
	

}
