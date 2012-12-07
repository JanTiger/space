package ${package}.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration for Greenhouse application @Components such as @Services, @Repositories, and @Controllers.
 *
 * @author Jan.Wang
 */
@Configuration
@ComponentScan(basePackages="${package}")
@PropertySource("classpath:default.properties")
public class AppConfig {

}
