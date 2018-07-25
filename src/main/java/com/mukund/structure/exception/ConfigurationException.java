package com.mukund.structure.exception;

public class ConfigurationException extends StructureException {
	static final long serialVersionUID = 6818587619349268109L;

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String aString) {
		super(aString);
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationException(Throwable cause) {
		super(cause);
	}

	public String getErrorNumber() {
		return ERROR_PREFIX + "10003";
	}
}
