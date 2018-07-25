package com.mukund.structure.service.jms.inbound;

import javax.jms.Message;

import org.springframework.stereotype.Component;

@Component
public class RepublishBookInboundQueue extends BaseInboundQueue {

	@Override
	public void onMessage(Message message) {
		System.out.println("RepublishBookInbound:"+message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOG.info("RepublishBookInboundQueue : received : {}", message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

}
