/**
 * 
 */
package com.mukund.structure.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mukund.structure.config.ApplicationConfiguration;
import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookActionE;
import com.mukund.structure.model.DroolsBookFacts;
import com.mukund.structure.model.WorkflowBook;
import com.mukund.structure.service.jms.JmsConfigFactory;

/**
 * @author hp
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "mypcdev")
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class BookServiceTest {

	@Autowired
	private MetaDataService metaService;

	@Autowired
	private DroolsService droolsService;

	@Autowired
	private BookService bookService;
	
	@Autowired
	PublishService ps;

	/**
	 * Test method for
	 * {@link com.mukund.structure.service.BookService#search(com.mukund.structure.model.BookSearchCriteria)}.
	 */
//	@Test
	public void testSearch() {
		// fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.mukund.structure.service.BookService#fetchBooks()}.
	 */
//	@Test
	public void testFetchBooks() {
		// fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.mukund.structure.service.BookService#submit(com.mukund.structure.model.Book)}.
	 */
//	@Test
	public void testSubmit() {
		 Book gbs1 = metaService.newBook("muchouhan", null);
		 gbs1.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund Submit");
		 gbs1.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan Submit");
		 gbs1.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		 gbs1.getApprovers().add("sarthak");
		 gbs1.getApprovers().add("surabhi");
		 ps.publish(gbs1);
		 //bookService.submit(gbs1);
	}

	/**
	 * 1. create new book 2. validate book it should return error for mandatory
	 * fields 3. provide few of mandatory fields and again validate it should
	 * give error for remaining one 4. provide all mandatory fields and submit
	 * into workflow 5. approve from all approver
	 */
//	@Test
	public void testBookPersist() {

		Book gbs1 = metaService.newBook("muchouhan", null);
		gbs1.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		gbs1.getApprovers().add("sarthak");
		gbs1.getApprovers().add("surabhi");

		DroolsBookFacts fact = new DroolsBookFacts(BookActionE.NEW, gbs1);
		DroolsBookFacts newfact = (DroolsBookFacts) droolsService.execute(fact);
		assertThat(newfact.getErrors().keySet(), is(not(empty())));

		gbs1.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund Submit");
		gbs1.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan Submit");

		fact = new DroolsBookFacts(BookActionE.NEW, gbs1);
		newfact = (DroolsBookFacts) droolsService.execute(fact);
		assertThat(newfact.getErrors().keySet(), is(empty()));

		WorkflowBook b = bookService.submit(gbs1);
		assertThat(b.getId(), is(gbs1.getId()));

//		String id = bookService.reject("surabhi", gbs1);
//		assertThat(id, is(gbs1.getId().toString()));

		 String approver1 = bookService.approve("surabhi", gbs1);
		 assertThat(approver1,is("surabhi"));

		String approver2 = bookService.approve("sarthak", gbs1);
		 assertThat(approver2,is("sarthak"));
		
		
	}

}
