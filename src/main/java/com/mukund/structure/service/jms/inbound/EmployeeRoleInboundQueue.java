package com.mukund.structure.service.jms.inbound;

import javax.jms.Message;

import org.springframework.stereotype.Component;

@Component
public class EmployeeRoleInboundQueue extends BaseInboundQueue {

	@Override
	public void onMessage(Message message) {
		System.out.println("EmployeeRoleInboundQueue"+message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOG.info("EmployeeRoleInboundQueue : received : {}", message);
		LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

}
