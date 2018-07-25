package com.mukund.structure.model;

import java.util.Arrays;

public enum RequestStatusE {
	INVALID(""), 
	DRAFT("Draft"), 
	PENDING_APPROVAL("Pending Approval"), 
	PARTIAL_APPROVAL("Partial Approval"), 
	APPROVED("Approved"), 
	REJECTED("Rejected"), 
	UPDATE_REQUIRE("Update Require"),
	HIERARCHY_PENDING("Hierarchy Pending"), 
	ENRICHMENT("Enrichment"), 
	CLOSER("Closer");

	private String val;

	private RequestStatusE(String value) {
		this.val = value;
	}

	public String val() {
		return this.val;
	}

	public RequestStatusE enumOf(String val) {
		return Arrays.stream(RequestStatusE.values()).
				filter(v -> v.val().equalsIgnoreCase(val)).
				distinct().findAny().get();
	}

}
