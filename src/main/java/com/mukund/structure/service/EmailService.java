package com.mukund.structure.service;

import com.mukund.structure.model.Book;

public interface EmailService extends StructureService {

	boolean send(Book b,String message);

}
