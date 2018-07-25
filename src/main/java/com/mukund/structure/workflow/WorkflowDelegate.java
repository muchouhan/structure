package com.mukund.structure.workflow;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukund.structure.model.RequestStatusE;
import com.mukund.structure.model.WorkflowBook;
import com.mukund.structure.model.WorkflowBook.WorkFlowVariable;
import com.mukund.structure.model.WorkflowE;
import com.mukund.structure.service.BookService;
import com.mukund.structure.service.EmailService;
import com.mukund.structure.service.WorkflowService;

@Service
public class WorkflowDelegate {

	@Autowired
	BookService bookSrvc;
	
	@Autowired
	WorkflowService workFlowSrvc;

	@Autowired
	EmailService sender;

	public void start(DelegateExecution execution) throws Exception {
		System.out.println("StructureWorkflowDelegate start" + execution.getVariables());
	}

	public void assingApprover(DelegateExecution execution) throws Exception {
		WorkflowBook book = (WorkflowBook) execution.getVariable(WorkFlowVariable.OBJECT);
		execution.setVariable("approvers", book.getApprovers());
		sender.send(book, "Task has been assign to all approver");
	}

	public void approve(DelegateExecution execution) throws Exception {
		WorkflowBook book = (WorkflowBook) execution.getVariable(WorkFlowVariable.OBJECT);
		String approver = (String) execution.getVariable(WorkflowE.APPROVER.val());

		// change request status to partial approval
		execution.setVariable(WorkflowE.REQUEST_STATUS.val(), RequestStatusE.PARTIAL_APPROVAL.val());

		int nrOfCompletedInstances = Integer
				.parseInt(execution.getVariable(WorkflowE.NR_OF_COMPLETED_INSTANCES.val()).toString());
		int nrOfInstances = Integer.parseInt(execution.getVariable(WorkflowE.NR_OF_INSTANCES.val()).toString());

		if (nrOfInstances == nrOfCompletedInstances + 1) {
			execution.setVariable(WorkflowE.REQUEST_STATUS.val(), RequestStatusE.APPROVED.val());
		}

		execution.setVariable(WorkFlowVariable.OBJECT, book);
		sender.send(book, "book " + book.getId() + " is approved by " + approver);
	}

	public void reject(DelegateExecution execution) throws Exception {
		WorkflowBook book = (WorkflowBook) execution.getVariable(WorkFlowVariable.OBJECT);
		String approver = (String) execution.getVariable(WorkflowE.APPROVER.val());
		book.setRequestStatus(RequestStatusE.REJECTED.val());
		execution.setVariable(WorkflowE.REQUEST_STATUS.val(), RequestStatusE.REJECTED.val());

		execution.setVariable(WorkFlowVariable.OBJECT, book);
		sender.send(book, "book " + book.getId() + " is rejected by " + approver);
	}

	public void updateRequire(DelegateExecution execution) throws Exception {
		WorkflowBook book = (WorkflowBook) execution.getVariable(WorkFlowVariable.OBJECT);
		String approver = (String) execution.getVariable(WorkflowE.APPROVER.val());

		sender.send(book, "book " + book.getId() + " is sent for update require by " + approver);

	}

	public void persisting(DelegateExecution execution) throws Exception {
		System.out.println("StructureWorkflowDelegate mastering" + execution.getVariables());

		WorkflowBook book = (WorkflowBook) execution.getVariable(WorkFlowVariable.OBJECT);
		book.setRequestStatus(RequestStatusE.APPROVED.val());

		// Send notification to user
		sender.send(book, "book " + book.getId() + " is approved by all approvers ");
		
		bookSrvc.saveAndUpdate(book);
		
		
		// If parent book is not persisted in database then put on hold
		// book.setRequestStatus(RequestStatusE.HIERARCHY_PENDING.val());
		// after persisting book in database check if any child book is pending
		// for mastering

		// Update workflow object
		execution.setVariable(WorkFlowVariable.OBJECT, book);

	}

}
