package com.mukund.structure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class BPMNDeploy {

	private static String fileName = "application_int.properties";

	public static void main(String[] args) {
		System.out.println("Environment:"+System.getProperty("env"));
		BPMNDeploy runner = new BPMNDeploy(System.getProperty("env"));
		runner.deploy();
	}
	
	private BPMNDeploy(String env){
		if(env!=null){
			fileName = "application_"+env+".properties";
			if(getClass().getClassLoader().getResourceAsStream(fileName)==null){
				System.out.println("'"+fileName+"' file not found in class path.");
				System.exit(-1);
			} 
		}else{
			System.out.println("Environment name is not given. Please provide env name \n eg.-Denv=mypcdev ");
			System.exit(-1);
		}
	}
	private void deploy() {
		Properties properties = new Properties();
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
			properties.load(inputStream);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		ProcessEngine processEngine = getProcessEngineConfiguration(properties).buildProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource(properties.getProperty("activiti.bmpn.definition")).deploy();
		System.out.println("Deployed");
	}

	private BasicDataSource dataSource(Properties prop) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(prop.getProperty("activiti.datasource.driverClassName"));
		dataSource.setUrl(prop.getProperty("activiti.datasource.url"));
		dataSource.setUsername(prop.getProperty("activiti.datasource.username"));
		dataSource.setPassword(prop.getProperty("activiti.datasource.password"));
		return dataSource;
	}

	private SpringProcessEngineConfiguration getProcessEngineConfiguration(Properties prop) {
		SpringProcessEngineConfiguration conf = new SpringProcessEngineConfiguration();
		conf.setDatabaseType(prop.getProperty("activiti.datasource.type"));
		conf.setDataSource(dataSource(prop));
		conf.setTransactionManager(new DataSourceTransactionManager(dataSource(prop)));
		conf.setDatabaseSchemaUpdate("true");
//		conf.setAsyncExecutorActivate(false);
		conf.setHistory("full");
		conf.setCreateDiagramOnDeploy(true);
		Resource[] resource = new Resource[1];
		resource[0]= new ClassPathResource(prop.getProperty("activiti.bmpn.definition")); 
		conf.setDeploymentResources(resource);
//		conf.setAsyncExecutorEnabled(true);
//		conf.setJobExecutorActivate(false);
//		conf.setEnableSafeBpmnXml(true);
		
		

		return conf;
	}

}
