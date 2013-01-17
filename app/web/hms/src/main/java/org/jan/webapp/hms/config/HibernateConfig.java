package org.jan.webapp.hms.config;

import java.io.IOException;
import java.util.Properties;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.jan.webapp.hms.exception.AppException;
import org.jan.webapp.hms.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSourceFactory;
/**
 * @author Jan.Wang
 *
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Inject
    private StandardEnvironment environment;

    @Bean(initMethod="init", destroyMethod="close")
    public DataSource dataSource(){
        try {
			return DruidDataSourceFactory.createDataSource(loadPrefixProperties(Constants.DRUID_PROPS_KEY_PREFIX));
		} catch (Exception e) {
			throw new AppException("Can not correctly configure a data source!");
		}
    }

    @Bean
    public SessionFactory sessionFactory(){
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource());
        sf.setPackagesToScan("org.jan.webapp.hms.model");
        sf.setHibernateProperties(loadPrefixProperties(Constants.HIBERNATE_PROPS_KEY_PREFIX));
        try {
			sf.afterPropertiesSet();
		} catch (IOException e) {
			throw new AppException(e);
		}
        return sf.getObject();
    }

    @Bean
    public HibernateTransactionManager transactionManager(){
    	return new HibernateTransactionManager(sessionFactory());
    }

    private Properties loadPrefixProperties(String prefix){
        Properties properties = new Properties();
        Properties props = (Properties) environment.getPropertySources().get(Constants.DEFAULT_PROPS_NAME).getSource();
        String key = null;
        for (Entry<Object, Object> entry : props.entrySet()) {
        	key = (String) entry.getKey();
        	if(key.startsWith(prefix))
        		properties.put(key.replace(prefix, ""), entry.getValue());
        }
        return properties;
    }

}
