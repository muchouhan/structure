package com.mukund.structure.config;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

import com.mukund.structure.exception.JmsConfigurationException;
import com.mukund.structure.model.xml.ConfigNodeSpec;
import com.mukund.structure.service.jms.JmsConfigFactory;

/**
 * @author muchouhan
 */
@Configuration
public class JMSConfiguration {

	@Inject
	ActiveMQConnectionFactory jmsFactory;

	@Inject
	JmsTemplate template;

	@Bean
	public JmsConfigFactory jmsconfig() throws JmsConfigurationException {
		JmsConfigFactory cf = new JmsConfigFactory();
		cf.loadInboundQueue();
		return cf;
	}

	@Configuration
	@Profile("mypcdev")
	@PropertySource("classpath:application_mypcdev.properties")
	static class MyPCDev {

		@Inject
		private Environment environment;

		@Bean
		public ActiveMQConnectionFactory factory() {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
			factory.setBrokerURL(environment.getRequiredProperty("structure.activemq.destination.brokerurl"));
			factory.setUserName(environment.getRequiredProperty("structure.activemq.borker.username"));
			factory.setPassword(environment.getRequiredProperty("structure.activemq.borker.password"));
			return factory;
		}

		@Bean
		public JmsTemplate jmsTemplate() {
			JmsTemplate template = new JmsTemplate();
			template.setConnectionFactory(factory());
			return template;
		}
	}

	@Configuration
	@Profile("development")
	@PropertySource("classpath:application_development.properties")
	static class Development {

		@Inject
		private Environment environment;

		@Bean
		public ActiveMQConnectionFactory factory() {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
					environment.getRequiredProperty("structure.activemq.destination.brokerurl"));
			return factory;
		}

	}

}