package com.mukund.structure.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mukund.structure.model.User;
import com.mukund.structure.model.UserPrivilege;

@Repository
public class UserDAOImpl extends BaseRepository implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	@Autowired
	UserAuthenticationRepository authenticator;

	@Override
	public void addPerson(User p) {
//		getSession().persist(p);
		logger.info("Person saved successfully, Person Details=" + p);
	}

	@Override
	public void updatePerson(User p) {
//		getSession().update(p);
		logger.info("Person updated successfully, Person Details=" + p);
	}

	@Override
	public List<User> listPersons() {
//		Session session = getSession();
//		List<User> personsList = session.createQuery("from Person").list();
//
//		for (User p : personsList) {
//			logger.info("Person List::" + p);
//		}
//		return personsList;
		return null;
	}

	@Override
	public User getPersonById(int id) {
//		User p = (User) getSession().load(User.class, new Integer(id));
//		logger.info("Person loaded successfully, Person details=" + p);
//		return p;
		return null;
	}

	@Override
	public void deleteByName(String name) {
//		Session session = getSession();
//		@SuppressWarnings("unchecked")
//		List<User> personsList = session.createQuery(" from Person where name=:name").setParameter("name", name)
//				.list();
//
//		for (User p : personsList) {
//			session.delete(p);
//			logger.info("Person deleted successfully, person details=" + p);
//		}

	}

	@Override
	public void deleteById(int id) {
//		Session session = getSession();
//		User p = (User) getSession().load(User.class, new Integer(id));
//		session.delete(p);
//		logger.info("Person deleted successfully, person details=" + p);

	}

	@Override
	public boolean authenticate(String user, String password) {
		return authenticator.authenticate(user, password);
	}

	@Override
	public List<UserPrivilege> fetchRoles(String user) {
		return authenticator.fetchRoles(user);
	}

}
