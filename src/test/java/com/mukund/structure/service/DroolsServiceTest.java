package com.mukund.structure.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mukund.structure.config.ApplicationConfiguration;
import com.mukund.structure.dao.MetaDataRepository;
import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookActionE;
import com.mukund.structure.model.DroolsBookFacts;
import com.mukund.structure.model.HierarchyE;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "mypcdev")
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class DroolsServiceTest {

	@Autowired
	MetaDataRepository metaDao;

	@Autowired
	MetaDataService metaService;

	@Autowired
	DroolsService droolService;

	@Autowired
	PublishService ps;

	@Test
	public void testExecute() {
		
		Book rdm = metaService.newLegacyBook("muchouhan", null, null, HierarchyE.RDM);
		assertThat(rdm, is(nullValue()));
		Book gfa = metaService.newLegacyBook("muchouhan", null, null, HierarchyE.GFA);
		assertThat(gfa, is(nullValue()));
		
		// Create Level 1 Book
		Book gbs1 = metaService.newBook("muchouhan", null);
		gbs1.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund1");
		gbs1.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan1");
		gbs1.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		
		rdm = metaService.newLegacyBook("muchouhan", gbs1, null, HierarchyE.RDM);
		assertThat(rdm, is(nullValue()));
		gfa = metaService.newLegacyBook("muchouhan", gbs1, null, HierarchyE.GFA);
		assertThat(gfa, is(nullValue()));
	
		
		Book gbs2 = metaService.newBook("muchouhan", gbs1);
		gbs2.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund2");
		gbs2.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan2");
		gbs2.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());

		rdm = metaService.newLegacyBook("muchouhan", gbs2, null, HierarchyE.RDM);
		assertThat(rdm, is(nullValue()));
		gfa = metaService.newLegacyBook("muchouhan", gbs2, null, HierarchyE.GFA);
		assertThat(gfa, is(nullValue()));
		
		Book gbs3 = metaService.newBook("muchouhan", gbs2);
		gbs3.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund3");
		gbs3.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan3");
		gbs3.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());

		rdm = metaService.newLegacyBook("muchouhan", gbs3, null, HierarchyE.RDM);
		assertThat(rdm, is(nullValue()));
		gfa = metaService.newLegacyBook("muchouhan", gbs3, null, HierarchyE.GFA);
		assertThat(gfa, is(nullValue()));
		
		Book gbs4 = metaService.newBook("muchouhan", gbs3);
		gbs4.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund4");
		gbs4.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan4");
		gbs4.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());

		rdm = metaService.newLegacyBook("muchouhan", gbs4, null, HierarchyE.RDM);
		assertThat(rdm, is(nullValue()));
		gfa = metaService.newLegacyBook("muchouhan", gbs4, null, HierarchyE.GFA);
		assertThat(gfa, is(nullValue()));
		
		Book gbs5 = metaService.newBook("muchouhan", gbs4);
		gbs5.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund5");
		gbs5.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan5");
		gbs5.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());

		rdm = metaService.newLegacyBook("muchouhan", gbs5, null, HierarchyE.RDM);
		assertThat(rdm, is(nullValue()));
		Book gfa1 = metaService.newLegacyBook("muchouhan", gbs5, null, HierarchyE.GFA);
		assertThat(gfa1,  is(notNullValue()));

		Book gbs6 = metaService.newBook("muchouhan", gbs5);
		gbs6.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund6");
		gbs6.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan6");
		gbs6.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		
		rdm = metaService.newLegacyBook("muchouhan", gbs6, null, HierarchyE.RDM);
		assertThat(rdm, is(nullValue()));
		Book gfa2 = metaService.newLegacyBook("muchouhan", gbs6, gfa1, HierarchyE.GFA);
		assertThat(gfa2,  is(notNullValue()));
		
		
		Book gbs7 = metaService.newBook("muchouhan", gbs6);
		gbs7.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund7");
		gbs7.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan7");
		gbs7.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		
		rdm = metaService.newLegacyBook("muchouhan", gbs7, null, HierarchyE.RDM);
		assertThat(rdm, is(nullValue()));
		Book gfa3 = metaService.newLegacyBook("muchouhan", gbs7, gfa2, HierarchyE.GFA);
		assertThat(gfa3,  is(notNullValue()));
		
		Book gbs8 = metaService.newBook("muchouhan", gbs7);
		gbs8.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund7");
		gbs8.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan7");
		gbs8.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		
		Book rdm1 = metaService.newLegacyBook("muchouhan", gbs8, null, HierarchyE.RDM);
		rdm1.getAttributeByCode("RDM_RECORD_STATUS").setValue("RDM Mukund1");
		assertThat(rdm1, is(notNullValue()));
		Book gfa4 = metaService.newLegacyBook("muchouhan", gbs8, gfa3, HierarchyE.GFA);
		assertThat(gfa4,  is(notNullValue()));
		
		
		Book gbs9 = metaService.newBook("muchouhan", gbs8);
		gbs9.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund7");
		gbs9.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan7");
		gbs9.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		
		Book rdm2 = metaService.newLegacyBook("muchouhan", gbs9, rdm1, HierarchyE.RDM);
		rdm2.getAttributeByCode("RDM_RECORD_STATUS").setValue("RDM Mukund2");
		assertThat(rdm2, is(notNullValue()));
		Book gfa5 = metaService.newLegacyBook("muchouhan", gbs9, gfa4, HierarchyE.GFA);
		assertThat(gfa5,  is(notNullValue()));
		
		Book gbs10 = metaService.newBook("muchouhan", gbs9);
		gbs10.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund7");
		gbs10.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan7");
		gbs10.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		
		Book rdm3 = metaService.newLegacyBook("muchouhan", gbs10, rdm2, HierarchyE.RDM);
		rdm3.getAttributeByCode("RDM_RECORD_STATUS").setValue("RDM Mukund3");
		assertThat(rdm3, is(notNullValue()));
		Book gfa6 = metaService.newLegacyBook("muchouhan", gbs10, gfa5, HierarchyE.GFA);
		assertThat(gfa6,  is(notNullValue()));
		
		Book gbs11 = metaService.newBook("muchouhan", gbs10);
		gbs11.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund7");
		gbs11.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan7");
		gbs11.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		
		Book rdm4 = metaService.newLegacyBook("muchouhan", gbs11, rdm3, HierarchyE.RDM);
		rdm4.getAttributeByCode("RDM_RECORD_STATUS").setValue("RDM Mukund4");
		assertThat(rdm4, is(notNullValue()));
		Book gfa7 = metaService.newLegacyBook("muchouhan", gbs11, gfa6, HierarchyE.GFA);
		assertThat(gfa7,  is(notNullValue()));
		
		gbs11.getLegacyBooks().add(rdm4);
		gbs11.getLegacyBooks().add(gfa7);
		 System.out.println("GBS " + gbs11);
//		 ps.publish(gbs11);
		DroolsBookFacts fact = new DroolsBookFacts(BookActionE.NEW, gbs11);
		DroolsBookFacts newfact = (DroolsBookFacts) droolService.execute(fact);
		// System.out.println(fact+"Message after drool rule:" + fact);
	}

}
