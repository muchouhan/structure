package com.mukund.structure.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mukund.structure.dao.ReportRepository;
import com.mukund.structure.model.Book;

public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportRepository reportRepo;

	@Override
	public void generate(Book book) {
		reportRepo.generate(book);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

}
