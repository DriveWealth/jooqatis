/**
 * DriveWealth, 2021
 */
package com.jooqatis.benchmark.jooqatis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * <p>Title: BenchmarkExecutor</p>
 * <p>Description:  </p>
 * <p>Author: nwhitehead (at) drivewealth dot it</p>
 * <p><code>com.jooqatis.benchmark.jooqatis.BenchmarkExecutor</code></p>
 */
@Service
public class BenchmarkExecutor {
	private final ThreadFactory threadFactory = new ThreadFactory() {
		final AtomicInteger serial = new AtomicInteger();
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r, "BenchmarkExecutor#" + serial.incrementAndGet());
			t.setDaemon(true);
			return t;
		}	
	};
	/**
	 * Creates a new BenchmarkExecutor
	 */
	public BenchmarkExecutor() {
	}
	
	@Bean
	protected ExecutorService executorService() {
		return Executors.newFixedThreadPool(16, threadFactory);
	}

}
