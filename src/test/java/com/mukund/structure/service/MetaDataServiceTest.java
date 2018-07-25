package com.mukund.structure.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mukund.structure.config.ApplicationConfiguration;
import com.mukund.structure.model.Book;
import com.mukund.structure.model.HierarchyE;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "mypcdev")
@ContextConfiguration(classes = { ApplicationConfiguration.class })
public class MetaDataServiceTest {

	@Autowired
	MetaDataService service;
	
//	@Test
	public void testNewGBSBook() {
		Book gbs1 = service.newBook("muchouhan", null);
		Book gbs2 = service.newBook("muchouhan", gbs1);
		Book gbs3 = service.newBook("muchouhan", gbs2);
		Book gbs4 = service.newBook("muchouhan", gbs3);
		Book gbs5 = service.newBook("muchouhan", gbs4);
		Book gbs6 = service.newBook("muchouhan", gbs5);
		Book gbs7 = service.newBook("muchouhan", gbs6);
		Book gbs8 = service.newBook("muchouhan", gbs7);
		Book gbs9 = service.newBook("muchouhan", gbs8);
		Book gbs10 = service.newBook("muchouhan", gbs9);
		Book gbs11 = service.newBook("muchouhan", gbs10);

		assertEquals("LEVEL1", gbs1.getAttributeByCode("GBS_LEVEL").getValue());
		assertEquals("LEVEL2", gbs2.getAttributeByCode("GBS_LEVEL").getValue());
		assertEquals("LEVEL3", gbs3.getAttributeByCode("GBS_LEVEL").getValue());
		assertEquals("LEVEL4", gbs4.getAttributeByCode("GBS_LEVEL").getValue());
		assertEquals("LEVEL5", gbs5.getAttributeByCode("GBS_LEVEL").getValue());
		assertEquals("LEVEL6", gbs6.getAttributeByCode("GBS_LEVEL").getValue());
		assertEquals("LEVEL7", gbs7.getAttributeByCode("GBS_LEVEL").getValue());
		assertEquals("LEVEL8", gbs8.getAttributeByCode("GBS_LEVEL").getValue());
		assertEquals("LEVEL9", gbs9.getAttributeByCode("GBS_LEVEL").getValue());
		assertEquals("LEVEL10", gbs10.getAttributeByCode("GBS_LEVEL").getValue());
		assertEquals("LEVEL11", gbs11.getAttributeByCode("GBS_LEVEL").getValue());
	}

	@Test
	public void testNewRDMBook() {
		Book gbs1 = service.newBook("muchouhan", null);
		Book gbs2 = service.newBook("muchouhan", gbs1);
		Book gbs3 = service.newBook("muchouhan", gbs2);
		Book gbs4 = service.newBook("muchouhan", gbs3);
		Book gbs5 = service.newBook("muchouhan", gbs4);
		Book gbs6 = service.newBook("muchouhan", gbs5);
		Book gbs7 = service.newBook("muchouhan", gbs6);
		Book gbs8 = service.newBook("muchouhan", gbs7);
		Book gbs9 = service.newBook("muchouhan", gbs8);
		Book gbs10 = service.newBook("muchouhan", gbs9);
		Book gbs11 = service.newBook("muchouhan", gbs10);

		Book rdm1 = service.newLegacyBook("muchouhan",gbs8, null, HierarchyE.RDM);
		Book rdm2 = service.newLegacyBook("muchouhan",gbs9, rdm1, HierarchyE.RDM);
		Book rdm3 = service.newLegacyBook("muchouhan",gbs10, rdm2, HierarchyE.RDM);
		Book rdm4 = service.newLegacyBook("muchouhan",gbs11, rdm3, HierarchyE.RDM);
		
		assertEquals("SP1", rdm1.getAttributeByCode("RDM_LEVEL").getValue());
		assertEquals("SP2", rdm2.getAttributeByCode("RDM_LEVEL").getValue());
		assertEquals("SP3", rdm3.getAttributeByCode("RDM_LEVEL").getValue());
		assertEquals("SP4", rdm4.getAttributeByCode("RDM_LEVEL").getValue());

	}

//	@Test
	public void testFetchDropDownValues() {
		// System.out.println(service.fetchDropDownValues());
	}

//	@Test
	public void testFetchApproverData() {
		// System.out.println(service.fetchApproverData());
	}

//	@Test
	public void testFetchBookFieldSets() {
		List<String> key = new ArrayList<String>(service.bookMetaData().getBookFieldSets().keySet());
		assertThat(key,containsInAnyOrder("Basic", "Classification","Dates","Owners"));
	}

}
