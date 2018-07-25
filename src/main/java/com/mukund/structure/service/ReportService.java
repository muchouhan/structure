package com.mukund.structure.service;

import com.mukund.structure.model.Book;
import com.mukund.structure.service.StructureService;

public interface ReportService extends StructureService {

	public void generate(Book book);
	
}
