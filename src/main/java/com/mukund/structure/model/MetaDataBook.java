package com.mukund.structure.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MetaDataBook implements Cloneable {

	private final HierarchyE hierarchy;
	private final List<BookAttribute> attributes;
	private final List<MetaDataBook> legacyBooks;
	private List<AltSystem> altSysBooks = new ArrayList<AltSystem>();

	public MetaDataBook(HierarchyE hierarchy, Collection<BookAttribute> attributes, List<MetaDataBook> legacy) {
		this.hierarchy = hierarchy;
		this.attributes = new ArrayList<BookAttribute>(attributes);
		this.legacyBooks = legacy != null ? new ArrayList<>(legacy) : new ArrayList<>();
	}

	public HierarchyE getHierarchy() {
		return hierarchy;
	}
	
	public List<BookAttribute> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}
	
	public BookAttribute getAttributeByName(final String name) {
		return (BookAttribute) attributes.stream().filter((item) -> item.getCode().equals(name)).findAny().get();
	}

	public List<BookAttribute> getAttributeByType(final AttributeTypeE type) {
		List<BookAttribute> items = attributes.stream().filter((item) -> item.getType().equals(type))
				.collect(Collectors.toList());
		return Collections.unmodifiableList(items);
	}

	public List<MetaDataBook> getLegacyBooks() {
		return Collections.unmodifiableList(legacyBooks);
	}

	@Override
	public Object clone() {
		return null;
	}

	@Override
	public String toString() {
		return "Book [hierarchy=" + hierarchy + ", attributes=" + attributes + ", legacyBooks=" + legacyBooks
				+ ", altSysBooks=" + altSysBooks + "]";
	}

	public Map<String, List<BookAttribute>> getBookFieldSets() {
		Map<String, List<BookAttribute>> groupList = attributes.stream()
				.collect(Collectors.groupingBy(BookAttribute::getTab, Collectors.toList()));
		return Collections.unmodifiableMap(groupList);
	}


	public MetaDataBook getBookByType(final HierarchyE hierarchy) {
		Optional<MetaDataBook> book = legacyBooks.stream().filter((item) -> item.getHierarchy().equals(hierarchy))
				.distinct().findAny();
		return book.get();
	}
}
