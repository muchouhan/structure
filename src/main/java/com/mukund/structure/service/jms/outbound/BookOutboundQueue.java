package com.mukund.structure.service.jms.outbound;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class BookOutboundQueue extends BaseOutboundQueue {

	public void send(String destination, Object message) {
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOG.info("BookOutboundQueue : sending message : {} to {}", message, destination);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		jmsTemplate.convertAndSend(destination, message);
	}

	// 2 threads should be enough, but leave headroom especially for running CI
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);

	public static void send(String queueName, String text, int delayMillis) {
		EXECUTOR.submit(() -> {
			try {
				LOG.info("*** artificial delay {}: {}", queueName, delayMillis);
				Thread.sleep(delayMillis);
				Connection connection = getConnection();
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				Destination destination = session.createQueue(queueName);
				MessageProducer producer = session.createProducer(destination);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				TextMessage message = session.createTextMessage(text);
				producer.send(message);
				LOG.info("*** sent message {}: {}", queueName, text);
				session.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	public static Connection getConnection() {
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
			Connection connection = connectionFactory.createConnection();
			connection.start();
			return connection;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
