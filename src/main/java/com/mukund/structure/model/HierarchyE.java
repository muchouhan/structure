package com.mukund.structure.model;

import java.io.Serializable;
import java.util.Arrays;

public enum HierarchyE implements Serializable {
	GBS("GBS"), RDM("RDM"), GFA("GFA"), ALTCODE("ALTCODE");

	private String val;

	private HierarchyE(String value) {
		this.val = value;
	}

	public String description() {
		return this.val;
	}

	public HierarchyE enumOf(String val) {
		return Arrays.stream(HierarchyE.values()).filter(v -> v.description().equalsIgnoreCase(val)).distinct()
				.findAny().get();
	}
}
