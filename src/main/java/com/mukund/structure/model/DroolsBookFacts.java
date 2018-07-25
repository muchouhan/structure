package com.mukund.structure.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

public class DroolsBookFacts {

	private Book book;
	private BookActionE action;
	
	public String getAction() {
		return action.name();
	}
	
	public String getHierarchy() {
		return book.getHierarchy().name();
	}
	

	private Map<String, String> attributes = Maps.newHashMap();
	private Map<String, String> errors = Maps.newHashMap();
	public Map<String, String> getErrors() {
		return errors;
	}
	private List<Object> validators = new ArrayList<>();

	public DroolsBookFacts(BookActionE action, Book book) {
		this.action = action;
		this.book = book;
		attributes = book.getAttributes().stream().filter((item) -> StringUtils.isNotEmpty(item.getValue()))
				.collect(Collectors.toMap(BookAttribute::getCode, BookAttribute::getValue));
		System.out.println("attributes:" + attributes.get("Book Name"));
		
	}
	
	public Map<String, String> getHas(){
		return attributes;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public List<Object> getValidators() {
		return validators;
	}

	public void setValidators(List<Object> validators) {
		this.validators = validators;
	}

}
