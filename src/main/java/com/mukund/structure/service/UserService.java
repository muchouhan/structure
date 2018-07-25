package com.mukund.structure.service;

import java.util.List;

import com.mukund.structure.model.UserPrivilege;

public interface UserService extends StructureService {

	boolean authenticate(String user, String password);
	
	List<UserPrivilege> fetchRoles(String user);
	
}
