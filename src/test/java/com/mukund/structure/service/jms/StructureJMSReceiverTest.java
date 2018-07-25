package com.mukund.structure.service.jms;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mukund.structure.config.ApplicationConfiguration;
import com.mukund.structure.config.JMSConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "mypcdev")
@ContextConfiguration(classes = { ApplicationConfiguration.class, JMSConfiguration.class })

public class StructureJMSReceiverTest {

	@Autowired
	JMSMessageSender sender;
	
	@Autowired
	JmsConfigFactory factory;

//	@Test
	public void testReceive() throws Exception {
		sender.setDestination("EmployeeRoleInbound");
		sender.send("EmployeeRoleInbound Hello Boot!");
		
		sender.setDestination("LegalEntityInbound");
		sender.send(" LegalEntityInbound Hello Boot!");
		
		sender.setDestination("SectorDomainInbound");
		sender.send(" SectorDomainInbound Hello Boot!");
		
		sender.setDestination("BookInbound");
		sender.send(" BookInbound Hello Boot!");
		
		sender.setDestination("CobraBookInbound");
		sender.send(" CobraBookInbound Hello Boot!");
		
		sender.setDestination("ActivitiReportInbound");
		sender.send( " ActivitiReportInbound Hello Boot!");
		
		sender.setDestination("RepublishBookInbound");
		sender.send( " RepublishBookInbound Hello Boot!");
	}

	@Test
	public void testSend() throws Exception {
		//factory.send(message);
		
		sender.getLatch().await(10000, TimeUnit.MILLISECONDS);
		
	}


}
