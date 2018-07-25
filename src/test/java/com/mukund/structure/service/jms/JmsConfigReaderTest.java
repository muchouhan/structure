package com.mukund.structure.service.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mukund.structure.config.ApplicationConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "mypcdev")
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class JmsConfigReaderTest {

	@Test
	public void testRead() throws Exception {

	}
}
