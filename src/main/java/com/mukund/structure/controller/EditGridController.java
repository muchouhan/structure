
package com.mukund.structure.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.component.column.Column;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.row.Row;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mukund.structure.model.MetaDataBook;
import com.mukund.structure.service.MetaDataService;

@Component
@Scope("session")
public class EditGridController implements Serializable {

	private static final long serialVersionUID = 1L;
	private DataTable grid = new DataTable();

	@Inject
	private MetaDataService service;

	public void setGrid(DataTable grid) {
		this.grid = grid;
	}

	public DataTable getGrid() {
		return grid;
	}

	@PostConstruct
	public void buildGrid() {
		this.grid = new DataTable();
		MetaDataBook gbs = service.bookMetaData();
		grid.getChildren().add(createHeader(gbs));
	}
	
	private ColumnGroup createHeader(MetaDataBook gbs){
		ColumnGroup grp = new ColumnGroup();
		grp.setType("header");
		grp.getChildren().add(createStructureRowHeader(gbs));
		grp.getChildren().add(createFieldsetRowHeader(gbs));
		grp.getChildren().add(createAttributeRowHeader(gbs));
		return grp;
	}

	private Row createFieldsetRowHeader(MetaDataBook b) {
		Row strRow = new Row();
		//ADD GBS Field sets
		b.getBookFieldSets().entrySet().stream().forEach(fieldset -> {
			Column lcol = new Column();
			lcol.setHeaderText(fieldset.getKey());
			lcol.setColspan(fieldset.getValue().size());
			strRow.getChildren().add(lcol);
		});
		
		b.getLegacyBooks().stream().forEach(lbook -> {
			lbook.getBookFieldSets().entrySet().stream().forEach(fieldset -> {
				Column lcol = new Column();
				lcol.setHeaderText(fieldset.getKey());
				lcol.setColspan(fieldset.getValue().size());
				strRow.getChildren().add(lcol);
			});
		});
		return strRow;
	}
	
	private Row createStructureRowHeader(MetaDataBook b) {
		Row strRow = new Row();
		//ADD GBS Structure
		Column col = new Column();
		col.setHeaderText(b.getHierarchy().name());
		col.setColspan(b.getAttributes().size());
		
		strRow.getChildren().add(col);
		
		b.getLegacyBooks().stream().forEach(lbook -> {
			Column lcol = new Column();
			lcol.setHeaderText(lbook.getHierarchy().name());
			lcol.setColspan(lbook.getAttributes().size());
			strRow.getChildren().add(lcol);
		});
		return strRow;
	}

	private Row createAttributeRowHeader(MetaDataBook b) {
		Row strRow = new Row();
		//ADD GBS Fields
		b.getBookFieldSets().entrySet().stream().forEach(fieldset -> {
			fieldset.getValue().stream().forEach(attr -> {
				Column lcol = new Column();
				lcol.setHeaderText(attr.getLabel());
				lcol.setWidth("100");
				lcol.setSortBy(attr.getLabel());
				strRow.getChildren().add(lcol);
			});
		});
		
		b.getLegacyBooks().stream().forEach(lbook -> {
			lbook.getBookFieldSets().entrySet().stream().forEach(fieldset -> {
				fieldset.getValue().stream().forEach(attr -> {
					Column lcol = new Column();
					lcol.setHeaderText(attr.getLabel());
					lcol.setWidth("100");
					lcol.setSortBy("#"+attr.getLabel());
					strRow.getChildren().add(lcol);
				});
			});
		});
		return strRow;
	}
	
}
