package com.mukund.structure.dao;

import com.mukund.structure.model.Book;

public interface WorkflowRepository {
	
	public void add(Book book);
	
	public void get(Integer bookId);
	
	public void search();
}
