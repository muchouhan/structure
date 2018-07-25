package com.mukund.structure.model.xml;

import org.apache.commons.lang3.StringUtils;

import com.mukund.structure.model.BookAttribute;
import com.thoughtworks.xstream.converters.SingleValueConverter;

public class BookAttributeConverter implements SingleValueConverter {

	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class clazz) {
		return clazz.equals(BookAttribute.class);
	}

	public Object fromString(String str) {
		BookAttribute person = new BookAttribute();
		// person.setName(string);
		return person;
	}

	@Override
	public String toString(Object obj) {
		BookAttribute attr = ((BookAttribute) obj);
		if (StringUtils.isNotEmpty(attr.getValue())) {
			return attr.getLabel() + "," + attr.getValue();
		}
		return "";
	}
}