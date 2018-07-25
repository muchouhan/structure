package com.mukund.structure.model;

import java.util.Map;

import com.google.common.collect.Maps;

public class WorkflowBook extends Book {

	private static final long serialVersionUID = -250966556163120610L;
	private String requestStatus;

	public WorkflowBook(Book b) {
		super(b.getRequester(), b.getId(), b.getHierarchy(), b.getAttributes(), b.getLegacyBooks());
		this.setApprovers(b.getApprovers());
	}
	
	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Map<String, Object> getWorkFlowParam() {
		Map<String, Object> processVariables = Maps.newHashMap();
		processVariables.put(WorkFlowVariable.ID, getId());
		processVariables.put(WorkFlowVariable.OBJECT, this);
		processVariables.put(WorkFlowVariable.NAME, this.getAttributeByCode("GBS_BOOK_NAME").getValue());
		return processVariables;
	}

	public interface WorkFlowVariable {
		String ID = "Id";
		String OBJECT = "Object";
		String NAME = "Name";
	}

	@Override
	public String toString() {
		return "WorkflowBook [requestStatus=" + requestStatus + ", toString()=" + super.toString() + "]";
	}
	
}
