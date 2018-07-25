package com.mukund.structure.service;

import java.util.List;

import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookSearchCriteria;
import com.mukund.structure.model.WorkflowBook;

public interface BookService extends StructureService  {

	List<Book> search(BookSearchCriteria criteria);

	List<Book> fetchBooks();

	WorkflowBook submit(Book book);

	String approve(String user, Book book);

	String reject(String user, Book book);

	String updateRequire(String user, Book book);

	boolean saveAndUpdate(WorkflowBook book);

}
