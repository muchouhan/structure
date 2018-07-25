package com.mukund.structure.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AltSystem implements Cloneable {

	private String code;
	private String name;
	private String label;
	private boolean isLink;
	private boolean isLinkInRDM;
	private List<BookAttribute> attributes = new ArrayList<BookAttribute>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isLink() {
		return isLink;
	}

	public void setLink(boolean isLink) {
		this.isLink = isLink;
	}

	public boolean isLinkInRDM() {
		return isLinkInRDM;
	}

	public void setLinkInRDM(boolean isLinkInRDM) {
		this.isLinkInRDM = isLinkInRDM;
	}

	public List<BookAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<BookAttribute> attributes) {
		this.attributes = attributes;
	}

	public void addAttributes(BookAttribute attribute) {
		this.attributes.add(attribute);
	}

	public void addAllAttributes(Collection<BookAttribute> attributes) {
		this.attributes.addAll(attributes);
	}

	public BookAttribute getAttributeByName(String name) {
		return null;
	}

	public List<BookAttribute> getAttributeByType(AttributeTypeE type) {
		return null;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
