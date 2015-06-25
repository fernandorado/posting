package com.student.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.student.controller","com.student.services","com.student.repositories"})
@PropertySource("classpath:resources/application.properties")
@EnableJpaRepositories("com.student.repositories")
public class RepositoryConfig {

	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
	private static final String PROPERTY_NAME_DATABASE_DRIVER = "jdbc.driverClassNam";
	private static final String PROPERTY_NAME_DATABASE_URL = "jdbc.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "jdbc.username";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "jdbc.password";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	
	@Autowired
	private Environment env;

	@Bean
	@Autowired(required=true)
	public Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT,env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		properties.put(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN, env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		return properties;
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
		lcemfb.setDataSource(dataSource());
		lcemfb.setPersistenceProviderClass(HibernatePersistence.class);
		lcemfb.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		lcemfb.setJpaProperties(getHibernateProperties());
		return lcemfb;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory()
				.getObject());
		return transactionManager;
	}
}
