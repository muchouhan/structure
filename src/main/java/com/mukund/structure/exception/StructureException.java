package com.mukund.structure.exception;

import java.util.Date;
import org.apache.log4j.Logger;

public class StructureException extends Exception {
	/**
	 * Field ERROR_PREFIX - I contain the prefix for all error numbers.
	 */
	protected static String ERROR_PREFIX = "DPH-";

	private static final Logger LOGGER = Logger.getLogger(StructureException.class);
	static final long serialVersionUID = -3910982946902410522L;
	/**
	 * Field logId - I contain a time marker relating nested exceptions together
	 * for logging purposes
	 */
	protected long logId = 0;

	public StructureException() {
		super();
		setLogId();
	}

	public StructureException(String aMsg) {
		super(aMsg);
		setLogId();
	}

	public StructureException(String message, Throwable cause) {
		super(message, cause);
		setLogIdFrom(cause);
	}

	public StructureException(Throwable cause) {
		super(cause);
		setLogIdFrom(cause);
	}

	public String getErrorNumber() {
		return ERROR_PREFIX + "10001";
	}

	public long getLogId() {
		return logId;
	}

	public String getLogIdString() {
		return (new Long(getLogId())).toString();
	}

	protected void setLogId() {
		setLogId(new Date().getTime());
	}

	protected void setLogId(long aL) {
		logId = aL;
	}

	protected void setLogIdFrom(Throwable aCause) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setLogIdFrom(Throwable aCause = " + aCause + ") - start");
		}
		try {
			StructureException e = (StructureException) aCause;
			setLogId(e.getLogId());
		} catch (ClassCastException e) {
			LOGGER.debug("setLogIdFrom(Throwable) - argument not a StepsException but " + aCause + " exception: "
					+ e.getMessage());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("setLogIdFrom(Throwable aCause = " + aCause + ") - end");
		}
	}
}
