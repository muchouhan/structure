package com.mukund.structure.service.jms.inbound;

import javax.jms.Message;

import org.springframework.stereotype.Component;

@Component
public class SectorDomainInboundQueue extends BaseInboundQueue {

	@Override
	public void onMessage(Message message) {
		System.out.println("SectorDomainInboundQueue"+message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOG.info("SectorDomainInboundQueue : received : {}", message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

}
