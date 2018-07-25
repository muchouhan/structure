package com.mukund.structure.exception;

public class WorkflowException extends RuntimeException {

	private static final long serialVersionUID = -4070426946790134707L;

	public WorkflowException(String msgId) {
		super(msgId);
	}

	public WorkflowException(String msgId, Throwable cause) {
		super(msgId, cause);
	}
}
