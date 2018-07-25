package com.mukund.structure.service;

import java.util.List;

import com.mukund.structure.model.User;

public interface EmployeeService extends StructureService {

	public void addPerson(User p);

	public void updatePerson(User p);

	public List<User> listPersons();

	public User getPersonById(int id);

	public void deleteByName(String name);

	public void deleteById(int id);

}
