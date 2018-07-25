package com.mukund.structure.workflow;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

@Service
public class BoundaryEventDelegate {

	public void approvalExpired(DelegateExecution execution) throws Exception {
		System.out.println("Inside ApprovalExpiryDelegate" + execution);

	}

	public void approvalReminder(DelegateExecution execution) throws Exception {
		System.out.println("Inside ApprovalExpiryDelegate" + execution);

	}
}
