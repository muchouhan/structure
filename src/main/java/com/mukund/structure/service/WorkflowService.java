package com.mukund.structure.service;

import java.io.IOException;
import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;

import com.mukund.structure.model.Book;
import com.mukund.structure.model.WorkflowBook;

public interface WorkflowService extends StructureService {

	List<WorkflowBook> fetchWorkFlowBooks(boolean requireActive);
    
	/**
     * @param businessKey the document Id as returned from DAO classes
     * @return the associated ProcessInstance or null if one does not exist
     */
    List<WorkflowBook> findWorkFlowBookById(Book book,boolean requireActive);

	/**
     * @param businessKey the document Id as returned from DAO classes
     * @return the associated ProcessInstance or null if one does not exist
     */
    List<WorkflowBook> findWorkFlowBookByUserId(String userId,boolean requireActive);

    List<WorkflowBook> findApprovalWorkFlowBookByUserId(String userId);

    WorkflowBook startWorkflow(Book book);
	
	void cancelWorkflow(String user,Book book);
	
	void deleteWorkflow(String user,Book book);
	
	String approve(String user,Book book);
	
	String reject(String user,Book book);

	String updateRequire(String user,Book book);
	
	/**
     * @param processId the process <strong>Definition</strong> Id - NOT the process Instance Id.
     * @return png image of diagram - nothing highlighted since this is the process definition - not a specific instance.
     */
	byte[] generateWorkflowDiagram(Book book) throws IOException ;
	
    /**
     * @param bookId The book id.
     * @return png image of diagram with current activity highlighted.
     */
    byte[] generateActiveBookDiagram(Book book) throws IOException;
    
}
