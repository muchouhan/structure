package com.mukund.structure.service;

import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookAttribute;
import com.mukund.structure.model.xml.ConfigNodeSpec;
import com.mukund.structure.model.xml.FactoryNodeSpec;
import com.mukund.structure.model.xml.QueueNodeSpec;
import com.thoughtworks.xstream.XStream;

public interface PublishService {

	boolean publish(Book b);

	default XStream getXmlStream() {
		XStream xs = new XStream();
		xs.alias("queue", QueueNodeSpec.class);
		xs.alias("factory", FactoryNodeSpec.class);
		xs.alias("config", ConfigNodeSpec.class);
		xs.alias("book", Book.class);
		xs.alias("attribute", BookAttribute.class);
		xs.omitField(BookAttribute.class,"code");
		xs.omitField(BookAttribute.class,"previousValue");
		xs.omitField(BookAttribute.class,"seq");
		xs.omitField(BookAttribute.class,"tooltip");
		xs.omitField(BookAttribute.class,"tab");
		xs.omitField(BookAttribute.class,"type");
		xs.omitField(BookAttribute.class,"validValues");
		xs.omitField(BookAttribute.class,"isMandatory");
		xs.omitField(BookAttribute.class,"isEnable");
		xs.omitField(BookAttribute.class,"errorMessage");
		
		
		// xs.registerConverter(new BookAttributeConverter());
		xs.setMode(XStream.NO_REFERENCES);
		return xs;
	}

}
