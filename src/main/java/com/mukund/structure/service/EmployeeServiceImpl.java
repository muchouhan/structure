package com.mukund.structure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mukund.structure.dao.UserDAO;
import com.mukund.structure.model.User;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	
	@Autowired
	UserDAO userDAO;
	
	@Override
	@Transactional
	public void addPerson(User p) {
		this.userDAO.addPerson(p);
	}

	@Override
	@Transactional
	public void updatePerson(User p) {
		this.userDAO.updatePerson(p);
	}

	@Override
	@Transactional
	public List<User> listPersons() {
//		return this.userDAO.listPersons();
	return null;
	}

	@Override
	@Transactional
	public User getPersonById(int id) {
		return this.userDAO.getPersonById(id);
	}

	@Override
	@Transactional
	public void deleteByName(String name) {
		this.userDAO.deleteByName(name);
	}
	
	@Override
	@Transactional
	public void deleteById(int id) {
		this.userDAO.deleteById(id);
	}

}
