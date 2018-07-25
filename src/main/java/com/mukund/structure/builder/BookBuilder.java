package com.mukund.structure.builder;

import java.util.List;

import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookAttribute;
import com.mukund.structure.model.BookLevelE;
import com.mukund.structure.model.HierarchyE;

public final class BookBuilder {

	private List<BookAttribute> attrs;
	private HierarchyE hierarchy;
	private Book pbook;
	private int id;
	private String requester;
	
	private BookBuilder(Book pbook) {
		this.pbook = pbook;
	}

	public static BookBuilder forBook(Book b) {
		return new BookBuilder(b);
	}

	public BookBuilder id(int id) {
		this.id = id;
		return this;
	}
	
	public BookBuilder attribute(List<BookAttribute> attr) {
		attrs = attr;
		return this;
	}

	public BookBuilder structure(HierarchyE hier) {
		hierarchy = hier;
		return this;
	}

	public BookBuilder requester(String user) {
		requester = user;
		return this;
	}
	
	public Book build() {
		Book b = new Book(requester,id,hierarchy, attrs, null);
		if (hierarchy == HierarchyE.GFA) {
//			b.getAttributeByName("GFA_LEVEL").setValue(getLevel().name());
		} else if (hierarchy == HierarchyE.RDM) {
			b.getAttributeByCode("RDM_LEVEL").setValue(getLevel().name());
		} else {
			b.getAttributeByCode("GBS_LEVEL").setValue(getLevel().name());
			b.getAttributeByCode("GBS_PARENT_URI")
					.setValue(pbook != null ? pbook.getAttributeByCode("GBS_URI").getValue() : "0");
		}

		return b;
	}

	private BookLevelE getLevel() {
		if (hierarchy == HierarchyE.RDM) {
			return (pbook == null) ? BookLevelE.SP1
					: BookLevelE.enumOf(pbook.getAttributeByCode("RDM_LEVEL").getValue()).next();
		} else if (hierarchy == HierarchyE.GFA) {
			return (pbook == null) ? BookLevelE.JPLvL1
					: BookLevelE.enumOf(pbook.getAttributeByCode("GFA_LEVEL").getValue()).next();
		} else {
			return (pbook == null) ? BookLevelE.LEVEL1
					: BookLevelE.enumOf(pbook.getAttributeByCode("GBS_LEVEL").getValue()).next();
		}
	}

}
