package com.mukund.structure.service.jms.inbound;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseInboundQueue implements MessageListener {

	protected static final Logger LOG = LoggerFactory.getLogger(BaseInboundQueue.class);

	@Override
	public void onMessage(Message message) {
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOG.info("BaseInboundQueue : received : {}", message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

}
