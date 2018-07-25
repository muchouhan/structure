package com.mukund.structure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukund.structure.dao.BookRepository;
import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookSearchCriteria;
import com.mukund.structure.model.WorkflowBook;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	WorkflowService workflowService;
	
	@Autowired
	BookRepository bookDAO;
	
	@Override
	public List<Book> search(BookSearchCriteria criteria) {
		return null;
	}

	@Override
	public List<Book> fetchBooks() {
		return null;
	}

	@Override
	public WorkflowBook submit(Book book) {
		return workflowService.startWorkflow(book);
	}

	@Override
	public String approve(String user, Book book) {
		return workflowService.approve(user, book);
	}

	@Override
	public String reject(String user, Book book) {
		return workflowService.reject(user, book);
	}

	@Override
	public String updateRequire(String user, Book book) {
		return workflowService.updateRequire(user, book);
	}

	@Override
	public boolean saveAndUpdate(WorkflowBook book) {
		return bookDAO.saveAndUpdate(book);
	}
}
