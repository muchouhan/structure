package com.mukund.structure.model;

import java.util.List;

public class DropDownValues {
	private String name;
	private List<Pair<String, String>> values;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Pair<String, String>> getValues() {
		return values;
	}
	public void setValues(List<Pair<String, String>> values) {
		this.values = values;
	}
	
	@Override
	public String toString() {
		return "DropDownValues [name=" + name + ", values=" + values + "]";
	}
}
