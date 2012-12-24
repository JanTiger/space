package org.jan.webapp.hms.config;

import org.aspectj.lang.annotation.AdviceName;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuration for application @Components such as @Services, @Repositories, and @Controllers.
 *
 * @author Jan.Wang
 */
@Configuration
@Aspect
public class AppConfig {

	@Pointcut("execution(* sy.service..*Impl.*(..))")
	public void transactionPointcut(){
		
	}
	
	public void dd(){
		
	}
}
