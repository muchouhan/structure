package com.mukund.structure.config;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class ActivitiConfiguration {

	@Autowired
	Environment environment;

	@Autowired
	ProcessEngineFactoryBean processEngine;
	
	@Bean
	public RepositoryService repositoryService() throws Exception {
		return processEngine.getObject().getRepositoryService();
	}

	@Bean
	public RuntimeService runtimeService() throws Exception {
		return processEngine.getObject().getRuntimeService();
	}

	@Bean
	public TaskService taskService() throws Exception {
		return processEngine.getObject().getTaskService();
	}

	@Bean
	public HistoryService historyService() throws Exception {
		return processEngine.getObject().getHistoryService();
	}

	@Bean
	public ManagementService managementService() throws Exception {
		return processEngine.getObject().getManagementService();
	}

	@Bean
	public IdentityService identityService() throws Exception {
		return processEngine.getObject().getIdentityService();
	}

	/**
	 * Data configuration.
	 * 
	 * @author
	 */
	@Configuration
	@Profile("mypcdev")
	@PropertySource("classpath:application_mypcdev.properties")
	static class MyPCDev {

		@Autowired
		private Environment environment;

		@Bean(destroyMethod = "close", name = "activiti")
		public BasicDataSource dataSource() {
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName(environment.getRequiredProperty("activiti.datasource.driverClassName"));
			dataSource.setUrl(environment.getRequiredProperty("activiti.datasource.url"));
			dataSource.setUsername(environment.getRequiredProperty("activiti.datasource.username"));
			dataSource.setPassword(environment.getRequiredProperty("activiti.datasource.password"));
			return dataSource;
		}

		@Bean
		public DataSourceTransactionManager dataSourceTransactionManager() {
			DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
			dataSourceTransactionManager.setDataSource(dataSource());
			return dataSourceTransactionManager;
		}

		@Bean
		public SpringProcessEngineConfiguration getProcessEngineConfiguration() {
			SpringProcessEngineConfiguration conf = new SpringProcessEngineConfiguration();
			conf.setDatabaseType(environment.getRequiredProperty("activiti.datasource.type"));
			conf.setDataSource(dataSource());
			conf.setTransactionManager(dataSourceTransactionManager());
			conf.setDatabaseSchemaUpdate("true");
			Resource[] resource = new Resource[1];
			resource[0] = new ClassPathResource(environment.getRequiredProperty("activiti.bmpn.definition"));
			conf.setDeploymentResources(resource);
			conf.setAsyncExecutorActivate(true);
			conf.setAsyncExecutorEnabled(true);
			conf.setJobExecutorActivate(false);
			conf.setHistory("full");
			conf.setCreateDiagramOnDeploy(true);
			conf.setEnableSafeBpmnXml(true);

			return conf;
		}

		@Bean
		public ProcessEngineFactoryBean processEngine() {
//			return getProcessEngineConfiguration().buildProcessEngine();
			ProcessEngineFactoryBean bean = new ProcessEngineFactoryBean();
			bean.setProcessEngineConfiguration(getProcessEngineConfiguration());
			return bean;
		}

	}

	@Configuration
	@Profile("development")
	@PropertySource("classpath:application_development.properties")
	static class Development {

	}

	@Configuration
	@Profile("production")
	@PropertySource("classpath:application_production.properties")
	static class Production {

	}
}
