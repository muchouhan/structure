package com.mukund.structure.dao;

import java.util.List;

import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookSearchCriteria;

public interface BookRepository {

	List<Book> search(BookSearchCriteria criteria);

	void viewChild(Book sourceBook);
	
	void add(Book book);

	void edit(Book book);
	
	void delink(Book book);
	
	void close(Book book);
	
	void closeLegacy(Book book);
	
	void reOpen(Book book);
	
	void reOpenLegacy(Book book);

	void reParent(Book sourceBook,Book targetBook);
	
	void reMap(Book sourceBook,Book targetBook);

	boolean saveAndUpdate(Book book);	
}
