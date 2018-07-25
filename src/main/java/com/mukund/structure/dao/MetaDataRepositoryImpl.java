package com.mukund.structure.dao;

import java.beans.FeatureDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mukund.structure.model.AttributeTypeE;
import com.mukund.structure.model.BookAttribute;
import com.mukund.structure.model.DropDownValues;
import com.mukund.structure.model.HierarchyE;
import com.mukund.structure.model.MetaDataBook;
import com.mukund.structure.model.Pair;
import com.mukund.structure.model.mapper.BookAttributeMapper;

@Repository
public class MetaDataRepositoryImpl extends BaseRepository implements MetaDataRepository {

	@Autowired
	Environment queries;

	private static final Logger logger = LoggerFactory.getLogger(MetaDataRepositoryImpl.class);

	@Override
	public MetaDataBook bookMetaData() {
		// Fetch GFA Note attribute
		Collection<BookAttribute> gfaAttrs = getJdbcTemplate().query(queries.getProperty("query.metadata.note"),
				new Object[] { "GFA" }, new BookAttributeMapper(AttributeTypeE.TEXTBOX));
		// Fetch GFA List attribute
		gfaAttrs.addAll(getJdbcTemplate().query(queries.getProperty("query.metadata.list"), new Object[] { "GFA" },
				new BookAttributeMapper(AttributeTypeE.DROPDOWN)));
		// Fetch GFA flag attribute
		gfaAttrs.addAll(getJdbcTemplate().query(queries.getProperty("query.metadata.flag"), new Object[] { "GFA" },
				new BookAttributeMapper(AttributeTypeE.CHECKBOX)));

		Collection<BookAttribute> gfaUnique = gfaAttrs.stream()
				.collect(Collectors.toMap(BookAttribute::getCode, Function.identity(), (left, right) -> {
					BeanUtils.copyProperties(right, left, emptyFieldsName(left));
					return left;
				})).values();
//		System.out.println(gfaUnique);
		MetaDataBook gfaBook = new MetaDataBook(HierarchyE.GFA, gfaUnique, null);

		// Fetch RDM Note attribute
		Collection<BookAttribute> rdmAttrs = getJdbcTemplate().query(queries.getProperty("query.metadata.note"),
				new Object[] { "RDM" }, new BookAttributeMapper(AttributeTypeE.TEXTBOX));
		// Fetch GFA List attribute
		rdmAttrs.addAll(getJdbcTemplate().query(queries.getProperty("query.metadata.list"), new Object[] { "RDM" },
				new BookAttributeMapper(AttributeTypeE.DROPDOWN)));
		// Fetch GFA flag attribute
		rdmAttrs.addAll(getJdbcTemplate().query(queries.getProperty("query.metadata.flag"), new Object[] { "RDM" },
				new BookAttributeMapper(AttributeTypeE.CHECKBOX)));

		Collection<BookAttribute> rdmUnique = rdmAttrs.stream()
				.collect(Collectors.toMap(BookAttribute::getCode, Function.identity(), (left, right) -> {
					BeanUtils.copyProperties(right, left, emptyFieldsName(left));
					return left;
				})).values();
//		System.out.println(rdmUnique);
		MetaDataBook rdmBook = new MetaDataBook(HierarchyE.RDM, rdmUnique, null);

		// Fetch RDM Note attribute
		Collection<BookAttribute> gbsAttrs = getJdbcTemplate().query(queries.getProperty("query.metadata.note"),
				new Object[] { "GBS" }, new BookAttributeMapper(AttributeTypeE.TEXTBOX));
		// Fetch GFA List attribute
		gbsAttrs.addAll(getJdbcTemplate().query(queries.getProperty("query.metadata.list"), new Object[] { "GBS" },
				new BookAttributeMapper(AttributeTypeE.DROPDOWN)));
		// Fetch GFA flag attribute
		gbsAttrs.addAll(getJdbcTemplate().query(queries.getProperty("query.metadata.flag"), new Object[] { "GBS" },
				new BookAttributeMapper(AttributeTypeE.CHECKBOX)));

		Collection<BookAttribute> gbsUnique = gbsAttrs.stream()
				.collect(Collectors.toMap(BookAttribute::getCode, Function.identity(), (left, right) -> {
					BeanUtils.copyProperties(right, left, emptyFieldsName(left));
					return left;
				})).values();
//		System.out.println(gbsUnique);

		List<MetaDataBook> legacy = new ArrayList<>();
		legacy.add(rdmBook);
		legacy.add(gfaBook);

		MetaDataBook gbsBook = new MetaDataBook(HierarchyE.GBS, gbsUnique, legacy);

		logger.debug(gbsBook.toString());
		return gbsBook;
	}

	private String[] emptyFieldsName(Object source) {
		final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
		
		return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
				.filter(propertyName -> (wrappedSource.isWritableProperty(propertyName) && ((wrappedSource.getPropertyValue(propertyName) != null 
										&& !wrappedSource.getPropertyType(propertyName).getName().equals("boolean"))
										|| (wrappedSource.getPropertyValue(propertyName) != null &&
											wrappedSource.getPropertyType(propertyName).getName().equals("boolean")
											&& wrappedSource.getPropertyValue(propertyName).toString().equals("true")))))
				.toArray(String[]::new);
	}

	@Override
	public List<DropDownValues> dropDownValues() {
		List<Map<String, Object>> ids = getJdbcTemplate().queryForList(queries.getProperty("query.metadata.listd"));
		return (List<DropDownValues>) ids.stream().map(t -> {
			DropDownValues value = new DropDownValues();
			value.setName(t.get("CODE").toString());
			List<Pair<String, String>> dbValues = getJdbcTemplate().query(
					queries.getProperty("query.metadata.list.value.by.code"), new Object[] { t.get("id") },
					new RowMapper<Pair<String, String>>() {
						public Pair<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
							return Pair.factory(resultSet.getNString("code"), resultSet.getNString("narrative"));
						}
					});
			value.setValues(dbValues);
			return value;
		}).collect(Collectors.toList());
	}

	@Override
	public int fetchBookID() {
		String query = "select Next_ID('BookD') ID";
		return getJdbcTemplate().query(query, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs.next() ? rs.getInt("ID") : 0;
			}
		});
	}
}
