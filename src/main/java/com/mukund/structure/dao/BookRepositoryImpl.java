package com.mukund.structure.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookSearchCriteria;

@Repository
public class BookRepositoryImpl extends BaseRepository implements BookRepository {

	@Override
	public List<Book> search(BookSearchCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void edit(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reParent(Book sourceBook, Book targetBook) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewChild(Book sourceBook) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delink(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeLegacy(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reOpen(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reOpenLegacy(Book book) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reMap(Book sourceBook, Book targetBook) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean saveAndUpdate(Book book) {
		// Save GBS Book changes
		if (book.isChanged()) {
			System.out.println("changed attributes:" + book.changedAttribute());
		}

		// Save Legacy Book changes
		book.getLegacyBooks().forEach(b -> {
			if (b.isChanged()) {
				// System.out.println("changed
				// attributes:"+book.changedAttribute());
			}
		});

		return true;
	}

}
