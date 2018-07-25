package com.mukund.structure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mukund.structure.model.Book;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
    JavaMailSender emailSender;
	
	@Override
	public boolean send(Book b,String message) {
		System.out.println(message+" Sending this message to user for Book :"+b.toString());
		return true;
	}

}
