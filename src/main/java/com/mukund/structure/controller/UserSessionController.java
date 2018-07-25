package com.mukund.structure.controller;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.mukund.structure.service.UserService;
import com.mukund.structure.utils.JsfUtil;

@Named
@ViewScoped
public class UserSessionController implements Serializable {

	@Autowired
	UserService userService;
	public static final String USER_ROLE = "USER_ROLE";
	private static final long serialVersionUID = 7765876811740798583L;
	private String username;
	private String password;
	private boolean loggedIn;

	public String doLogin() {
		if (username == null || password == null) {
			 JsfUtil.addErrorMessage("InvalidLogin");
			return "/pages/loginPage.xhtml";
		}

		if (userService.authenticate(username, password)) {
			loggedIn = true;
			if (FacesContext.getCurrentInstance() != null) {
				HttpServletRequest requestObj = (HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest();
				requestObj.getSession().setAttribute(USER_ROLE, userService.fetchRoles(username));
			}
			return "/pages/user/dashboard.xhtml?faces-redirect=true";
		}

        JsfUtil.addErrorMessage("InvalidLogin");
		return "/pages/loginPage.xhtml";
	}

	public String doLogout() {
		loggedIn = false;
		return "/pages/loginPage.xhtml";
	}

	public boolean isLoggedIn() {
		if (loggedIn == false) {
			System.out.println("FacesContext instance is null: " + (FacesContext.getCurrentInstance() == null));
			if (FacesContext.getCurrentInstance() != null) {
				HttpServletRequest requestObj = (HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest();
				requestObj.getSession().invalidate();
			}

		}

		System.out.println("LogedIn: " + loggedIn);
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
