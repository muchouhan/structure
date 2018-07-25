package com.mukund.structure.service.jms.inbound;

import javax.jms.Message;

import org.springframework.stereotype.Component;

@Component
public class BookInboundQueue extends BaseInboundQueue {

	@Override
	public void onMessage(Message message) {
		System.out.println("BookInbound"+message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOG.info("BookInboundQueue : received : {}", message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

}
