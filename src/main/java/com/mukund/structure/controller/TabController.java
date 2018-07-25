package com.mukund.structure.controller;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHint;
import javax.faces.context.FacesContext;
import javax.faces.event.BehaviorEvent;
import javax.inject.Inject;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.selectcheckboxmenu.SelectCheckboxMenu;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.selectoneradio.SelectOneRadio;
import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mukund.structure.model.MetaDataBook;
import com.mukund.structure.service.MetaDataService;

@Component
@Scope("session")
public class TabController implements Serializable {

	private static final long serialVersionUID = 4684260155844458081L;

	@Inject
	private MetaDataService service;

	private TabView tabs;

	public TabView getTabs() {
		return tabs;
	}

	public void setTabs(TabView tabs) {
		this.tabs = tabs;
	}

	@PostConstruct
	public void buildTabs() {
		this.tabs = new TabView();
		MetaDataBook gbs = service.bookMetaData();
		tabs.getChildren().add(createTabs(gbs));
		gbs.getLegacyBooks().stream().forEach(b -> {
			this.tabs.getChildren().add(createTabs(b));
		});
	}

	private Tab createTabs(MetaDataBook b) {
		Tab tab = new Tab();
		tab.setId(b.getHierarchy().name());
		tab.setTitle(b.getHierarchy().name());
		
		tab.getChildren().add(createPanel(b));
		return tab;
	}

	private PanelGrid createPanel(MetaDataBook b) {
		PanelGrid panel = new PanelGrid();
		panel.setColumns(6);
		b.getAttributes().stream().forEach(attr -> {
			switch (attr.getType()) {
			case CHECKBOX:
				OutputLabel checkboxLabel = new OutputLabel();
				SelectBooleanCheckbox checkbox = new SelectBooleanCheckbox();
				checkbox.setId(attr.getCode());
				checkbox.setStyle("width:150px");
				checkboxLabel.setFor(attr.getCode());
				checkboxLabel.setValue(attr.getLabel()+" : ");
				
				panel.getChildren().add(checkboxLabel);
				panel.getChildren().add(checkbox);
				
				break;
			case DROPDOWN:
				
				OutputLabel dropdownLabel = new OutputLabel();
				SelectOneMenu dropdown = new SelectOneMenu();
				dropdown.setId(attr.getCode());
				dropdown.setStyle("width:150px");
				dropdownLabel.setFor(attr.getCode());
				dropdownLabel.setValue(attr.getLabel()+" : ");
				
				panel.getChildren().add(dropdownLabel);
				panel.getChildren().add(dropdown);
				
				break;
			case MULTILIST:

				break;
			case MULTISELECT:
				
				OutputLabel multiCheckboxLabel = new OutputLabel();
				SelectCheckboxMenu multiCheckbox = new SelectCheckboxMenu();
				multiCheckbox.setId(attr.getCode());
				multiCheckbox.setStyle("width:150px");
				multiCheckbox.setFilter(true);
				multiCheckbox.setFilterMatchMode("startsWith");
				multiCheckboxLabel.setFor(attr.getCode());
				multiCheckboxLabel.setValue(attr.getLabel()+" : ");
				panel.getChildren().add(multiCheckboxLabel);
				panel.getChildren().add(multiCheckbox);
				
				break;
			case RADIO:
				OutputLabel radioLabel = new OutputLabel();
				SelectOneRadio radio = new SelectOneRadio();
				radio.setId(attr.getCode());
				radio.setStyle("width:150px");
				radioLabel.setFor(attr.getCode());
				radioLabel.setValue(attr.getLabel()+" : ");
				
				panel.getChildren().add(radioLabel);
				panel.getChildren().add(radio);
				break;
			case TEXTBOX:
				OutputLabel txtLabel = new OutputLabel();
				InputText text = new InputText();
				text.setId(attr.getCode());
				text.setStyle("width:150px");
				txtLabel.setFor(attr.getCode());
				txtLabel.setValue(attr.getLabel()+" : ");
				text.addClientBehavior("onBlur", new ClientBehavior() {
					
					@Override
					public void broadcast(BehaviorEvent event) {
						System.out.println("event:"+event);
					}
					
					@Override
					public String getScript(ClientBehaviorContext behaviorContext) {
						return null;
					}
					
					@Override
					public Set<ClientBehaviorHint> getHints() {
						return null;
					}
					
					@Override
					public void decode(FacesContext context, UIComponent component) {
						System.out.println("component:"+component);
					}
				});
				panel.getChildren().add(txtLabel);
				panel.getChildren().add(text);
				break;
			
			default:
				break;
			}
			
		});
		
		return panel;
	}

}
