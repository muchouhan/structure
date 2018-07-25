package com.mukund.structure.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mukund.structure.config.ApplicationConfiguration;
import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookActionE;
import com.mukund.structure.service.MetaDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "mypcdev")
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class BookRepositoryTest {

	@Autowired
	BookRepository repo;

	@Autowired
	private MetaDataService metaService;

	@Test
	public void testSaveAndUpdate() {

		Book gbs1 = metaService.newBook("muchouhan", null);
		gbs1.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund Submit");
		gbs1.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan Submit");
		gbs1.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		gbs1.getApprovers().add("sarthak");
		gbs1.getApprovers().add("surabhi");

		repo.saveAndUpdate(gbs1);
	}

}
