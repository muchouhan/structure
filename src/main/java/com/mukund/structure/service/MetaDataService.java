package com.mukund.structure.service;

import java.util.List;

import com.mukund.structure.model.Book;
import com.mukund.structure.model.DropDownValues;
import com.mukund.structure.model.HierarchyE;
import com.mukund.structure.model.MetaDataBook;

public interface MetaDataService extends StructureService {

	MetaDataBook bookMetaData();

	List<DropDownValues> fetchDropDownValues();

	void fetchApproverData();

	void fetchOwnersData();

	void fetchInternalDeskData();

	List<String> fetchLegalEntities();

	Book newBook(String user, Book pBook);

	Book newLegacyBook(String user, Book gbsBook, Book pBook, HierarchyE hier);

}
