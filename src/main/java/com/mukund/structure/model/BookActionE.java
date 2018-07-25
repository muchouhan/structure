package com.mukund.structure.model;

import java.util.Arrays;

public enum BookActionE {
	INVALID(""), 
	NEW("New"), 
	CREATE_LEGACY("Create Legacy"), 
	EDIT("Edit"), 
	CLOSE("Close"), 
	CLOSE_LEGACY("Close Legacy"), 
	REMAP("Remap"), 
	REPARENT("Reparent");

	private String val;

	private BookActionE(String value) {
		this.val = value;
	}

	public String description() {
		return this.val;
	}

	public BookActionE enumOf(String val) {
		return Arrays.stream(BookActionE.values()).
				filter(v -> v.description().equalsIgnoreCase(val)).
				distinct().findAny().get();
	}

}
