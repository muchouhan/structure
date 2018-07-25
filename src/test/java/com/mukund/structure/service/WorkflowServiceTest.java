package com.mukund.structure.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mukund.structure.aop.StructureAspect;
import com.mukund.structure.builder.BookBuilder;
import com.mukund.structure.config.ActivitiConfiguration;
import com.mukund.structure.config.ApplicationConfiguration;
import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookActionE;
import com.mukund.structure.model.HierarchyE;
import com.mukund.structure.model.WorkflowBook;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "mypcdev")
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { ApplicationConfiguration.class,
		ActivitiConfiguration.class,StructureAspect.class})
public class WorkflowServiceTest {
	@Autowired
	private MetaDataService metaService;

	@Autowired
	private WorkflowService workflowService;

	@Test
	public void testFetchBooks() {
	
	}

//	@Test
	public void testStartWorkflow() {
		Book gbs1 = metaService.newBook("muchouhan", null);
		gbs1.getAttributeByCode("GBS_BOOK_NAME").setValue("Mukund1");
		gbs1.getAttributeByCode("GBS_BOOK_SHORT_NAME").setValue("Chouhan1");
		gbs1.getAttributeByCode("GBS_ACTION").setValue(BookActionE.NEW.description());
		gbs1.getApprovers().add("sarthak");
		gbs1.getApprovers().add("surabhi");
		workflowService.startWorkflow(gbs1);
		
		// Book gbs
		// =BookBuilder.forBook(null).id(98).requester("muchouhan").attribute(null).structure(HierarchyE.GFA).build();
		// workflowService.reject("surabhi", gbs1);
		// workflowService.approve("sarthak", gbs1);
	}

//	@Test
	public void testFindWorkFlowBookByUserId() {
		String userId= "surabhi";
		List<WorkflowBook> books = workflowService.findWorkFlowBookByUserId(userId,false);
		System.out.println("Records for User: "+userId+" \t"+books);
	}

	@Test
	public void testFindWorkFlowBookById() {
		 Book gbs=BookBuilder.forBook(null).id(106).requester("muchouhan").attribute(null).structure(HierarchyE.GFA).build();
		 List<WorkflowBook> books = workflowService.findWorkFlowBookById(gbs,false);
//		 System.out.println("Records for Book Id:"+gbs.getId()+" \t"+books);
	}
	
	@Test
	public void testReject() {
		// Book gbs
		// =BookBuilder.forBook(null).id(98).requester("muchouhan").attribute(null).structure(HierarchyE.GFA).build();
		// workflowService.deleteWorkflow("muchouhan", gbs);
	}

}
