package com.mukund.structure.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mukund.structure.builder.BookBuilder;
import com.mukund.structure.dao.MetaDataRepository;
import com.mukund.structure.model.Book;
import com.mukund.structure.model.BookAttribute;
import com.mukund.structure.model.BookLevelE;
import com.mukund.structure.model.DropDownValues;
import com.mukund.structure.model.HierarchyE;
import com.mukund.structure.model.MetaDataBook;

@Service
public class MetaDataServiceImpl implements MetaDataService {

	private static MetaDataBook bookMetaData;
	private static List<DropDownValues> dropDowns;

	@Autowired
	MetaDataRepository repo;

	@PostConstruct
	@Override
	public void initialize() {
		bookMetaData = metaBook();
		dropDowns = dropDownValues();
	}

	@Transactional
	private MetaDataBook metaBook() {
		return repo.bookMetaData();
	}

	@Transactional
	private List<DropDownValues> dropDownValues() {
		return repo.dropDownValues();
	}

	@Override
	public MetaDataBook bookMetaData() {
		return  bookMetaData;
	}

	@Override
	public void fetchApproverData() {

	}

	@Override
	public List<DropDownValues> fetchDropDownValues() {
		return Collections.unmodifiableList(dropDowns);
	}

	@Override
	public void fetchOwnersData() {
		
	}

	@Override
	public void fetchInternalDeskData() {
		
	}

	@Override
	public List<String> fetchLegalEntities() {
		return null;
	}
	
//	@Override
//	public Book newBook(String user,Book pBook,HierarchyE hier) {
//		List<BookAttribute> attrs = new ArrayList<>();
//		if (hier == HierarchyE.GFA) {
//			attrs = bookMetaData.getBookByType(hier).getAttributes().stream().map(BookAttribute::clone).collect(Collectors.toList());
//			return BookBuilder.forBook(pBook).id(repo.fetchBookID()).requester(user).attribute(attrs).structure(HierarchyE.GFA).build();
//		}else if (hier == HierarchyE.RDM) {
//			attrs = bookMetaData.getBookByType(hier).getAttributes().stream().map(BookAttribute::clone).collect(Collectors.toList());
//			return BookBuilder.forBook(pBook).id(repo.fetchBookID()).requester(user).attribute(attrs).structure(HierarchyE.RDM).build();
//		}else{
//			attrs =  bookMetaData.getAttributes().stream().map(BookAttribute::clone).collect(Collectors.toList());
//			return BookBuilder.forBook(pBook).id(repo.fetchBookID()).requester(user).attribute(attrs).structure(HierarchyE.GBS).build();
//		} 
//	}

	@Override
	public Book newBook(String user, Book pBook) {
		List<BookAttribute> attrs =  bookMetaData.getAttributes().stream().map(BookAttribute::clone).collect(Collectors.toList());
		return BookBuilder.forBook(pBook).id(repo.fetchBookID()).requester(user).attribute(attrs).structure(HierarchyE.GBS).build();
	}

	@Override
	public Book newLegacyBook(String user, Book gbsBook, Book pBook, HierarchyE hier) {
		List<BookAttribute> attrs = new ArrayList<>();
		
		//If valid level to create legacy book else return null
		if(gbsBook==null || !BookLevelE.enumOf(gbsBook.getAttributeByCode("GBS_LEVEL").getValue()).allow(hier)) return null;
		
		if (hier == HierarchyE.GFA) {
			attrs = bookMetaData.getBookByType(hier).getAttributes().stream().map(BookAttribute::clone).collect(Collectors.toList());
			return BookBuilder.forBook(pBook).id(repo.fetchBookID()).requester(user).attribute(attrs).structure(HierarchyE.GFA).build();
		}else{
			attrs = bookMetaData.getBookByType(hier).getAttributes().stream().map(BookAttribute::clone).collect(Collectors.toList());
			return BookBuilder.forBook(pBook).id(repo.fetchBookID()).requester(user).attribute(attrs).structure(HierarchyE.RDM).build();
		}
	}
}
