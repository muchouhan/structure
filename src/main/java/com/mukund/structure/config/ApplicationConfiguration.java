package com.mukund.structure.config;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:config/queries.properties")
@ComponentScan(basePackages = { "com.mukund.structure" })
public class ApplicationConfiguration {

	@Autowired
	Environment environment;

	@Autowired
	BasicDataSource dataSource;

	@Primary
	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		return jdbcTemplate;
	}

	/**
	 * Allows transactions to be managed against the RDBMS using the JDBC API.
	 */
	@Primary
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * Embedded Data configuration.
	 * 
	 * @author Keith Donald
	 */
	@Configuration
	@Profile("mypcdev")
	@PropertySource("classpath:application_mypcdev.properties")
	static class MyPCDev {

		@Autowired
		private Environment environment;

		@Primary
		@Bean(destroyMethod = "close")
		public BasicDataSource dataSource() {
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driverClassName"));
			dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
			dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
			dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
			return dataSource;
		}

	}

	@Configuration
	@Profile("development")
	@PropertySource("classpath:application_development.properties")
	static class Development {

		@Autowired
		private Environment environment;

		@Primary
		@Bean(destroyMethod = "close")
		public BasicDataSource dataSource() {
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driverClassName"));
			dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
			dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
			dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
			return dataSource;
		}

	}

	@Configuration
	@Profile("production")
	@PropertySource("classpath:application_production.properties")
	static class Production {

		@Autowired
		private Environment environment;

		@Primary
		@Bean(destroyMethod = "close")
		public BasicDataSource dataSource() throws Exception {
			Context ctx = new InitialContext();
			return (BasicDataSource) ctx.lookup("java:comp/env/jdbc/datasource");
		}

	}
}
