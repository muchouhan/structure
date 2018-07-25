package com.mukund.structure.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.mukund.structure.model.AttributeTypeE;
import com.mukund.structure.model.BookAttribute;

public class BookAttributeMapper implements RowMapper<BookAttribute> {
	private static final Logger logger = LoggerFactory.getLogger(BookAttributeMapper.class);
	
	private AttributeTypeE type;
	
	public BookAttributeMapper(AttributeTypeE type) {
		super();
		this.type = type;
	}
	@Override
	public BookAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
		logger.info(rs.getNString("code") + "\t" + rs.getNString("label") + "\t" + rs.getNString("property") + "\t"
				+ rs.getNString("value"));
		BookAttribute item = new BookAttribute();
		item.setCode(rs.getNString("code"));
		item.setLabel(rs.getNString("label"));
		item.setType(type);
		if ("ISMAND".equalsIgnoreCase(rs.getNString("property"))) {
			item.setMandatory("Y".equalsIgnoreCase(rs.getNString("value")) ? true : false);
		} else if ("SEQ".equalsIgnoreCase(rs.getNString("property"))) {
			item.setSeq(new Integer(rs.getNString("value")));
		} else if ("PANEL".equalsIgnoreCase(rs.getNString("property"))) {
			item.setTab(rs.getNString("value"));
		}
		return item;
	}
}
