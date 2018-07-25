package com.mukund.structure.service.jms;

import java.util.concurrent.CountDownLatch;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JMSMessageSender {
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	public CountDownLatch getLatch() {
        return latch;
    }
		
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@NotNull
	private String destination;

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Autowired
	Environment environment;

	public void send(String message) {
		jmsTemplate.convertAndSend(destination, message);
	}
}
