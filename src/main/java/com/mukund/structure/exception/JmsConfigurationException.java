package com.mukund.structure.exception;

public class JmsConfigurationException extends ConfigurationException {
	private static final long serialVersionUID = 2100815472246751726L;

	public JmsConfigurationException() {
		super();
	}

	public JmsConfigurationException(String aString) {
		super(aString);
	}

	public JmsConfigurationException(String anMessage, Throwable anCause) {
		super(anMessage, anCause);
	}

	public JmsConfigurationException(Throwable anCause) {
		super(anCause);
	}
}
