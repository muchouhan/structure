package com.mukund.structure.service.jms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mukund.structure.exception.JmsConfigurationException;
import com.mukund.structure.model.xml.BookAttributeConverter;
import com.mukund.structure.model.xml.ConfigNodeSpec;
import com.mukund.structure.model.xml.FactoryNodeSpec;
import com.mukund.structure.model.xml.QueueNodeSpec;
import com.mukund.structure.utils.SpringBeanUtil;
import com.thoughtworks.xstream.XStream;

public class JmsConfigFactory {

	private static final Logger LOGGER = Logger.getLogger(JmsConfigFactory.class);

	private static String CONFIGURATION_FILE = "config/jms-config.xml";

	private ConfigNodeSpec config;

	@Autowired
	SpringBeanUtil context;

	public JmsConfigFactory() throws JmsConfigurationException {
		InputStream strm = this.getClass().getClassLoader().getResourceAsStream(JmsConfigFactory.CONFIGURATION_FILE);
		config = readFrom(strm);
	}

	private XStream getXmlStream() {
		XStream xs = new XStream();
		xs.alias("queue", QueueNodeSpec.class);
		xs.alias("factory", FactoryNodeSpec.class);
		xs.alias("config", ConfigNodeSpec.class);

		xs.registerConverter(new BookAttributeConverter());
		xs.setMode(XStream.ID_REFERENCES);
		return xs;
	}

	/**
	 * This Method is used to create listener for inbound queues
	 */
	public void loadInboundQueue() {
		config.getQueues().stream().filter(q -> !q.isOutboundQueue()).forEach(queue -> {
			try {
				buildInboundQueue(queue);
			} catch (NamingException | JMSException | InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				System.out.println(queue.getName() + "\t" + e.getMessage());
				// e.printStackTrace();
			}
		});
	}

	@SuppressWarnings("static-access")
	private MessageConsumer buildInboundQueue(QueueNodeSpec spec) throws NamingException, JMSException,
			InstantiationException, IllegalAccessException, ClassNotFoundException {
		String className = spec.getFactorySpec().getConnectionFactoryClass();
		String brokerUrl = spec.getFactorySpec().getProviderUrl();

		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, className);
		props.setProperty(Context.PROVIDER_URL, brokerUrl);
		javax.naming.Context ctx = new InitialContext(props);
		ConnectionFactory cf = (ConnectionFactory) ctx.lookup("ConnectionFactory");

		Connection connection = cf.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer consumer = session.createConsumer(session.createQueue(spec.getName()));
		consumer.setMessageListener((MessageListener) context.getBean(Class.forName(spec.getQueueClass())));
		System.out.println(" build inbound queue:" + spec.getName() + "\t and factory class is :" + cf
				+ "\t and listner class is :" + spec.getQueueClass());
		return consumer;
	}

	public MessageProducer send(Object message) {

		// try {
		// System.out.println("*** artificial delay {}: {}", queueName,
		// delayMillis);
		//
		// Connection connection = getConnection();
		// Session session = connection.createSession(false,
		// Session.AUTO_ACKNOWLEDGE);
		// Destination destination = session.createQueue(queueName);
		// MessageProducer producer = session.createProducer(destination);
		// producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		// TextMessage message = session.createTextMessage(text);
		// producer.send(message);
		// System.out.println("*** sent message {}: {}", queueName, text);
		// session.close();
		// } catch (Exception e) {
		// throw new RuntimeException(e);
		// }

		return null;
	}

	private ConfigNodeSpec readFrom(InputStream strm) throws JmsConfigurationException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("readFrom(String=" + strm + ") - start");
		}
		ConfigNodeSpec aReadObject = (ConfigNodeSpec) getXmlStream().fromXML(strm);
		return aReadObject;
	}

	public void write(ConfigNodeSpec aConfig, String aFilePathname) throws JmsConfigurationException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("write(IJmsConfig=" + aConfig + ", String=" + aFilePathname + ") - start");
		}
		File aFile = new File(aFilePathname);
		Writer aWriter;
		try {
			aWriter = new FileWriter(aFile);
			getXmlStream().toXML(aConfig, aWriter);
			aWriter.close();
		} catch (IOException e) {
			LOGGER.error("write(IJmsConfig=" + aConfig + ", String=" + aFilePathname + ")", e);
			throw new JmsConfigurationException(e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("write(IJmsConfig=" + aConfig + ", String=" + aFilePathname + ") - end");
		}
	}

}
