package com.mukund.structure.config;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.template.jdbc.ResultSetGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.mukund.structure.model.RuleClassMapping;

@Configuration
public class DroolsConfiguration {

	private static final String drlFile = "drools\\structure.drt";

	@Autowired
	Environment environment;

	@Autowired
	@Qualifier("drools")
	BasicDataSource dataSource;

	@Bean(name = "rules")
	public RuleClassMapping getRules() {
		RuleClassMapping rules = new RuleClassMapping();
		try (Statement stmt = dataSource.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(environment.getProperty("query.drools.conditions"));) {
			while (rs.next()) {
				rules.addMapping(rs.getString("request_type"), rs.getString("structure"),rs.getString("ruleclass"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return rules;
	}

	@Bean
	public KnowledgeBase getKnowledgeBase() throws Exception {
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		try (Statement stmt = dataSource.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(environment.getProperty("query.drools.conditions"));) {
			final ResultSetGenerator converter = new ResultSetGenerator();
			final String drl = converter.compile(rs, this.getClass().getClassLoader().getResourceAsStream(drlFile));
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			kbuilder.add(ResourceFactory.newByteArrayResource(drl.getBytes()), ResourceType.DRL);
//			System.out.println(drl);
			KnowledgeBuilderErrors errors = kbuilder.getErrors();
			if (errors.size() > 0) {
				for (KnowledgeBuilderError error : errors) {
					System.err.println(error);
				}
				throw new IllegalArgumentException("Could not parse knowledge.");
			}
			kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return kbase;
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

		@Bean(destroyMethod = "close", name = "drools")
		public BasicDataSource activityDataSource() {
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName(environment.getRequiredProperty("drools.datasource.driverClassName"));
			dataSource.setUrl(environment.getRequiredProperty("drools.datasource.url"));
			dataSource.setUsername(environment.getRequiredProperty("drools.datasource.username"));
			dataSource.setPassword(environment.getRequiredProperty("drools.datasource.password"));
			return dataSource;
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
