package com.mukund.structure.dao;

import java.util.List;

import com.mukund.structure.model.User;
import com.mukund.structure.model.UserPrivilege;

public interface UserDAO {

	public void addPerson(User p);

	public void updatePerson(User p);

	public List<User> listPersons();

	public User getPersonById(int id);

	public void deleteByName(String name);

	public void deleteById(int id);

	boolean authenticate(String user, String password);

	List<UserPrivilege> fetchRoles(String user);
}
