package com.mukund.structure.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.webapp.FacetTag;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.toolbar.Toolbar;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mukund.structure.model.UserPrivilege;

@Component
@Scope("session")
public class MenuB implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<String> roles = new ArrayList<>();
	
	public boolean hasRole(String role) {
		return roles.contains(role);
	}

	@PostConstruct
	public void buildToolbar() {

		if (FacesContext.getCurrentInstance() != null) {
			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			List<UserPrivilege> role = (List<UserPrivilege>) req.getSession()
					.getAttribute(UserSessionController.USER_ROLE);
			roles.addAll(role.stream().map(UserPrivilege::getRole).distinct().collect(Collectors.toList()));
			
		} else {
			// return "/pages/loginPage.xhtml";
		}

	}

	private FacetTag buildFacet(Set<String> roles) {
		FacetTag tag = new FacetTag();
		tag.setName("left");

		return tag;
	}

}
