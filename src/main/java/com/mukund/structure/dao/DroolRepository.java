package com.mukund.structure.dao;

import com.mukund.structure.model.Book;

public interface DroolRepository {

	public void init();

	public Book validate(Book book);

	public Book loadDependentValue(Book book);

}
