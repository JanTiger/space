package org.jan.webapp.hms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration for application @Components such as @Services, @Repositories, and @Controllers.
 *
 * @author Jan.Wang
 */
@Configuration
@ComponentScan(basePackages="org.jan.webapp.hms")
@PropertySource("classpath:default.properties")
public class AppConfig {

}
