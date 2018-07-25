package com.mukund.structure.workflow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukund.structure.model.WorkflowBook;
import com.mukund.structure.model.WorkflowBook.WorkFlowVariable;
import com.mukund.structure.service.EmailService;

@Service
public class WorkflowListener {

	@Autowired
	EmailService sender;

	/**
	 * This is used by workflow when it create task based on approval list
	 * 
	 * @param execution
	 * @param task
	 */
	public void createTask(Execution execution, DelegateTask task) {
		WorkflowBook book = (WorkflowBook) task.getVariable(WorkFlowVariable.OBJECT);
		String approver = (String) task.getVariable("approver");
		task.setAssignee(approver);
		sender.send(book,"System is creating task for "+approver);
	}

	/**
	 * this method is used by workflow when each task is completed by user
	 * Reject/Approve/Update
	 * 
	 * @param execution
	 * @param task
	 */
	public void completeTask(Execution execution, DelegateTask task) {
		WorkflowBook book = (WorkflowBook) task.getVariable(WorkFlowVariable.OBJECT);
		String approver = (String) task.getVariable("approver");
		sender.send(book,"Task is completed by "+approver);

	}

	/**
	 * This is used by workflow when it create task based on approval list
	 * 
	 * @param execution
	 * @param task
	 */
	public void createUserUpdateTask(Execution execution, DelegateTask task) {
		System.out.println("ApprovalTaskListener create" + task.getVariables());

		// update request status
//		String approver = (String) task.getVariable("approver");
	}

	/**
	 * this method is used by workflow when each task is completed by user
	 * Reject/Approve/Update
	 * 
	 * @param execution
	 * @param task
	 */
	public void completeUserUpdateTask(Execution execution, DelegateTask task) {
		System.out.println("ApprovalTaskListener complete" + task.getVariables());

	}

}
