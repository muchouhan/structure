package com.mukund.structure.dao;

import java.util.List;

import com.mukund.structure.model.DropDownValues;
import com.mukund.structure.model.MetaDataBook;

public interface MetaDataRepository {

	public MetaDataBook bookMetaData();

	public List<DropDownValues> dropDownValues();

	int fetchBookID();

}
