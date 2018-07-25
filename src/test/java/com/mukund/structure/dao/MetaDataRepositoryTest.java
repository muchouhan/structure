package com.mukund.structure.dao;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mukund.structure.config.ApplicationConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "mypcdev")
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class MetaDataRepositoryTest {

	@Autowired
	MetaDataRepository repository;

	@Test
	public void testBookMetaData() {
		System.out.println(repository.bookMetaData());
		//assertNotEquals(new Book(HierarchyE.GBS), repository.bookMetaData());
	}

	@Test
	public void testDropDownValues() {
		assertEquals(true, repository.dropDownValues().size()>0);
	}
	
	@Test
	@Transactional
    @Rollback(true)
	public void testFetchBookID() {
		assertEquals(true, repository.fetchBookID()>0);
	}
}
