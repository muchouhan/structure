package com.mukund.structure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukund.structure.dao.UserDAO;
import com.mukund.structure.model.UserPrivilege;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean authenticate(String user, String password) {
		return userDAO.authenticate(user, password);
	}

	@Override
	public List<UserPrivilege> fetchRoles(String user) {
		return userDAO.fetchRoles(user);
	}

}
