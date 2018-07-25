package com.mukund.structure.dao;

import com.mukund.structure.model.Book;

public interface NotificationRepository {
	public void send(Book b);
}
