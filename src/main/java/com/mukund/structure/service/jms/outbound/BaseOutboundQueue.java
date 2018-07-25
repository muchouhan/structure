package com.mukund.structure.service.jms.outbound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;


public abstract class BaseOutboundQueue {

	protected static final Logger LOG = LoggerFactory.getLogger(BaseOutboundQueue.class);

	@Autowired
	JmsTemplate jmsTemplate;

	public void send(String destination, Object message) {
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOG.info("BaseOutboundQueue : sending message : {} to {}", message, destination);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		jmsTemplate.convertAndSend(destination, message);
	}
}
