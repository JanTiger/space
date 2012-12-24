package org.jan.webapp.hms.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;


/**
 * @author Jan.Wang
 *
 */
@Configuration
public class HibernateConfig {

    @Inject
    private StandardEnvironment environment;

    @Bean
    public SessionFactory sessionFactory(){
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource());
        sf.setPackagesToScan("com.jan.web.model");
        sf.setHibernateProperties(loadHibernateProperties());
        return sf.getObject();
    }

    public DataSource dataSource(){
        return null;
    }

    private Properties loadHibernateProperties(){
        Properties hibernateProperties = new Properties();
        System.out.println(environment.getPropertySources().get("default.properties").getProperty("hibernate.hbm2ddl.auto"));
        System.out.println(environment.getSystemEnvironment().get("hibernate.hbm2ddl.auto"));
        System.out.println(environment.getSystemProperties().get("hibernate.hbm2ddl.auto"));
        return hibernateProperties;
    }
}
