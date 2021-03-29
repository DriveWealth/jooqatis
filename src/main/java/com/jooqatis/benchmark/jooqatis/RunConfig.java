/**
 * DriveWealth, 2021
 */
package com.jooqatis.benchmark.jooqatis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: RunConfig</p>
 * <p>Description:  </p>
 * <p>Author: nwhitehead (at) drivewealth dot it</p>
 * <p><code>com.jooqatis.benchmark.jooqatis.RunConfig</code></p>
 */
@Configuration
@ConfigurationProperties(prefix = "benchmark.config")
@ConstructorBinding
public class RunConfig {
	protected int runLoops = 5_000;
	protected int warmupLoops = 2_000;
	protected int runConcurrency = 2;
	protected int warmupConcurrency = 2;
	
	public RunConfig() {
		
	}
	
	/**
	 * Returns the runLoops
	 * @return the runLoops
	 */
	public int getRunLoops() {
		return runLoops;
	}
	/**
	 * Sets the runLoops
	 * @param runLoops the runLoops to set
	 */
	public void setRunLoops(int runLoops) {
		this.runLoops = runLoops;
	}
	/**
	 * Returns the warmupLoops
	 * @return the warmupLoops
	 */
	public int getWarmupLoops() {
		return warmupLoops;
	}
	/**
	 * Sets the warmupLoops
	 * @param warmupLoops the warmupLoops to set
	 */
	public void setWarmupLoops(int warmupLoops) {
		this.warmupLoops = warmupLoops;
	}
	/**
	 * Returns the runConcurrency
	 * @return the runConcurrency
	 */
	public int getRunConcurrency() {
		return runConcurrency;
	}
	/**
	 * Sets the runConcurrency
	 * @param runConcurrency the runConcurrency to set
	 */
	public void setRunConcurrency(int runConcurrency) {
		this.runConcurrency = runConcurrency;
	}
	/**
	 * Returns the warmupConcurrency
	 * @return the warmupConcurrency
	 */
	public int getWarmupConcurrency() {
		return warmupConcurrency;
	}
	/**
	 * Sets the warmupConcurrency
	 * @param warmupConcurrency the warmupConcurrency to set
	 */
	public void setWarmupConcurrency(int warmupConcurrency) {
		this.warmupConcurrency = warmupConcurrency;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RunConfig [runLoops=");
		builder.append(runLoops);
		builder.append(", warmupLoops=");
		builder.append(warmupLoops);
		builder.append(", runConcurrency=");
		builder.append(runConcurrency);
		builder.append(", warmupConcurrency=");
		builder.append(warmupConcurrency);
		builder.append("]");
		return builder.toString();
	}
	
	


}
