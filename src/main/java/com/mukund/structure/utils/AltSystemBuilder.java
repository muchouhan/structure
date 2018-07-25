package com.mukund.structure.utils;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.mukund.structure.model.BookAttribute;

public class AltSystemBuilder {

	private List<Object[]> rows;
	
	public static List<BookAttribute> convert(List<Object[]> rows) {
		AltSystemBuilder builder = new AltSystemBuilder(rows);	
		return builder.map();
	} 
	
	private AltSystemBuilder(List<Object[]> rows) {
		this.rows = rows;
	}
	
	private List<BookAttribute> map(){
		List<BookAttribute> list = new ArrayList<>();
		for (int row = 0; row < rows.size(); row++) {
				Object[] t = (Object[]) rows.get(row);
				BookAttribute item = new BookAttribute();
				item.setCode(t[0].toString());
				item.setLabel(t[1].toString());
				if(t[3].toString().equals("ISMAND")) {
					item.setMandatory(t[4].toString().equals("Y")?true:false);	
				}else if(t[3].toString().equals("SEQ")) {
					item.setSeq(new Integer(t[4].toString()));
				}else if(t[3].toString().equals("PANEL")) {
					item.setTab(t[4].toString());
				}
				
				BookAttribute listItem = getItemByCode(t[0].toString(), list);
				
				if(listItem!=null) {
					BeanUtils.copyProperties(item,listItem,getNullPropertyNames(listItem) );
				}else {
					list.add(item);
				}
			}
			
			return list;
			
	} 
	
	public static String[] getNullPropertyNames(Object source) {
	    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
	    return Stream.of(wrappedSource.getPropertyDescriptors())
	            .map(FeatureDescriptor::getName)
	            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) != null)
	            .toArray(String[]::new);
	}
	
	private BookAttribute getItemByCode(String code, List<BookAttribute> list){
		 
		 for (BookAttribute item : list) {
			if(item.getCode().equals(code)) return item;
		 }
		 return null;
	} 

}
