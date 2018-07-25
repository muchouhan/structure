package com.mukund.structure.model;

public enum WorkflowE {

	NAMESPACE("studerw.com"),
	PROCESS_ID("structure_workflow"),
    APPROVAL_SUBPROCESS_ID("approval_sub_process"),
    APPROVAL_TASK_ID("approval_Task_listner"),
    USER_UPDATE_TASK_ID("user_update_task"),
    
    PROP_CANCELLED("Process Cancelled"),
    PROP_DELETED("Process Deleted"),
    PROP_REJECTED("Process Rejected"),
    
    APPROVER("approver"),
    TASK_STATUS("taskStatus"),
    REQUEST_STATUS("requestStatus"),
    
    NR_OF_COMPLETED_INSTANCES("nrOfCompletedInstances"),
    NR_OF_INSTANCES("nrOfInstances"),
    
    PROCESS_ID_USER_APPROVAL("NEW_USER"),
    PROCESS_NAME_USER_APPROVAL("Approve New User"),
    TASK_ID_USER_APPROVAL("approveUserTask"),
    TASK_NAME_USER_APPROVAL("Approve New User Account");

    
	private String val;

	private WorkflowE(String value) {
		this.val = value;
	}

	public String val() {
		return this.val;
	}
}
