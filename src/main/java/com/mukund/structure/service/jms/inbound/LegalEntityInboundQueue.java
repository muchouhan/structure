package com.mukund.structure.service.jms.inbound;

import javax.jms.Message;

import org.springframework.stereotype.Component;

@Component
public class LegalEntityInboundQueue extends BaseInboundQueue {

	@Override
	public void onMessage(Message message) {
		System.out.println("LegalEntityInbound"+message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOG.info("LegalEntityInboundQueue : received : {}", message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

}
