package com.mukund.structure.model;

public class User {
	private String userid;
	private String password;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;

	public User(String userid, String password, String firstName, String middleName, String lastName, String email) {
		super();
		this.userid = userid;
		this.password = password;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", password=" + password + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", email=" + email + "]";
	}
}
