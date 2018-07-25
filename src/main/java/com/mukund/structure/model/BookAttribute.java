package com.mukund.structure.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BookAttribute implements Comparable<BookAttribute>, Serializable {

	private static final long serialVersionUID = -5062485591890305508L;
	private String code;
	private String value;
	private String previousValue;
	private Integer seq;
	private String label;
	private String tooltip;
	private String tab;
	private AttributeTypeE type;
	private List<String> validValues;
	private boolean isMandatory;
	private boolean isEnable;
	private String errorMessage;

	@Override
	public int compareTo(BookAttribute o) {
		return this.getSeq().compareTo(o.getSeq());
	}

	@Override
	public BookAttribute clone() {
		BookAttribute attr = new BookAttribute();
		attr.setCode(code);
		attr.setValue(value);
		attr.setPreviousValue(previousValue);
		attr.setSeq(seq);
		attr.setLabel(label);
		attr.setTooltip(tooltip);
		attr.setTab(tab);
		attr.setType(type);
		attr.setValidValues(validValues);
		attr.setMandatory(isMandatory);
		attr.setEnable(isEnable);
		attr.setErrorMessage(errorMessage);
		return attr;
	}

	/**
	 * This method is used to identify whether attribute has changed or not.
	 * 
	 * @return
	 */
	public boolean isChanged() {
		return value != null && !value.equals(previousValue);
	}

}
