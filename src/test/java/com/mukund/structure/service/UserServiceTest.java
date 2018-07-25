package com.mukund.structure.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mukund.structure.config.ApplicationConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "mypcdev")
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class UserServiceTest {
	
	@Autowired
	UserService service;

	@Test
	public void testAuthenticate() {
		 assertEquals(true,service.authenticate("admin", "admin123"));
	}

	@Test
	public void testfetchRoles() {
		 assertEquals(true,service.fetchRoles("mukund").size()>0);
	}

}
