package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiOutput.Enabled;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p>Title: JooqatisApplication</p>
 * <p>Description:  </p>
 * <p>Author: nwhitehead (at) drivewealth dot it</p>
 * <p><code>com.jooqatis.benchmark.jooqatis.JooqatisApplication</code></p>
 */
@SpringBootApplication
@EnableConfigurationProperties
public class JooqatisApplication {

	public static void main(String[] args) {
		AnsiOutput.setEnabled(Enabled.DETECT);
		SpringApplication.run(JooqatisApplication.class, args);
	}

}
