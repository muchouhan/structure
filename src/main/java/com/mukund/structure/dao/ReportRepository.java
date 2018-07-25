package com.mukund.structure.dao;

import com.mukund.structure.model.Book;

public interface ReportRepository {
	public void generate(Book book);
}
