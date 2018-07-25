package com.mukund.structure.dao;

import java.util.List;

import com.mukund.structure.model.UserPrivilege;

public interface UserAuthenticationRepository {

	public boolean authenticate(String user, String password);
	
	public List<UserPrivilege> fetchRoles(String user);
}
