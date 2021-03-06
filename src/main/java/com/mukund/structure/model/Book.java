package com.mukund.structure.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public class Book implements Cloneable, Serializable {

	private static final long serialVersionUID = -589358314286376795L;
	private final Integer id;
	private final String requester;
	private final HierarchyE hierarchy;
	private final List<BookAttribute> attributes;
	private final List<Book> legacyBooks;
	private List<AltSystem> altSysBooks = new ArrayList<>();

	@Setter
	private List<String> approvers = new ArrayList<>();

	public Book(String user, Integer id, HierarchyE hierarchy, List<BookAttribute> attributes, List<Book> legacy) {
		requester = user;
		this.id = id;
		this.hierarchy = hierarchy;
		this.attributes = attributes != null ? new ArrayList<BookAttribute>(attributes) : new ArrayList<>();
		this.legacyBooks = legacy != null ? new ArrayList<>(legacy) : new ArrayList<>();
	}

	public List<BookAttribute> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}

	public Book getBookByCode(String stbk) {
		return null;
	}

	public BookAttribute getAttributeByCode(final String code) {
		return (BookAttribute) attributes.stream().filter((item) -> item.getCode().equals(code)).findAny().get();
	}

	public BookAttribute getAttributeByName(final String name) {
		return (BookAttribute) attributes.stream().filter((item) -> item.getLabel().equals(name)).findAny().get();
	}

	public List<BookAttribute> getAttributeByType(final AttributeTypeE type) {
		List<BookAttribute> items = attributes.stream().filter((item) -> item.getType().equals(type))
				.collect(Collectors.toList());
		return Collections.unmodifiableList(items);
	}

	public List<Book> getBookByType(final HierarchyE hierarchy) {
		List<Book> books = legacyBooks.stream().filter((item) -> item.getHierarchy().equals(hierarchy))
				.collect(Collectors.toList());
		return Collections.unmodifiableList(books);
	}

	/**
	 * This method is used to identify whether Book has changed or not.
	 * 
	 * @return
	 */
	public boolean isChanged() {
		return !attributes.stream().filter((item) -> item.isChanged()).collect(Collectors.toList()).isEmpty();
	}

	/**
	 * This method is used to fetch changed attribute of Book.
	 * 
	 * @return
	 */
	public List<BookAttribute> changedAttribute() {
		return attributes.stream().filter((item) -> item.isChanged()).collect(Collectors.toList());
	}

	@Override
	public Object clone() {
		return null;
	}

}
