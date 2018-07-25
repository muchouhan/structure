package com.mukund.structure.metatool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class MetaToolDAOImpl implements MetaToolDAO {
	private JdbcTemplate template;

	public MetaToolDAOImpl(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public boolean addHierarchyD(String code, String label) {
		if (isCodeExist("HierarchyD", code)) {
			System.out.println(code + " already exist");
			return false;
		}

		String query = "INSERT INTO HierarchyD (code,narrative) VALUES ('" + code + "','" + label + "')";
		System.out.println(query);
		template.execute(query);
		return true;
	}

	@Override
	public boolean addLevelD(String hierachy,String code, String label) {
		int hierachy_id = fetchIdByTable("HierarchyD", hierachy);
		
		if (isCodeExist("LevelD", code)) {
			System.out.println(code + " already exist");
			return false;
		}

		String query = "INSERT INTO LevelD (code,narrative,hierarchy_id) VALUES ('" + code + "','" + label + "', "+hierachy_id+")";
		System.out.println(query);
		template.execute(query);
		return true;
	}

	@Override
	public boolean addPropertyD(String code, String label) {
		if (isCodeExist("PropertyD", code)) {
			System.out.println(code + " already exist");
			return false;
		}

		String query = "INSERT INTO PropertyD (code,narrative) VALUES ('" + code + "','" + label + "')";
		System.out.println(query);
		template.execute(query);
		return true;
	}

	private boolean isCodeExist(String table, String code) {
		String query = "select ID from " + table + " where code=?";
		return template.query(query, new Object[] { code }, new ResultSetExtractor<Boolean>() {
			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs.next();
			}
		});
	}
	
	private boolean isCodeExistForHierarchy(String table, String code,int hierarchy) {
		String query = "select ID from " + table + " where code=? and hierarchy_id=? ";
		return template.query(query, new Object[] { code,hierarchy }, new ResultSetExtractor<Boolean>() {
			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs.next();
			}
		});
	}

	private Integer fetchIdByTable(String table, String code) {
		String query = "select ID from " + table + " where code=?";
		return template.query(query, new Object[] { code }, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs.next() ? rs.getInt("ID"):0;
			}
		});
	}

	private boolean isPropertyExist(String table, int type_id, int prop_id) {
		String query = "select ID from " + table + " where type_id=? and property_id=?";
		return template.query(query, new Object[] { type_id, prop_id }, new ResultSetExtractor<Boolean>() {
			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs.next();
			}
		});
	}

	private boolean isUsageExist(String table, int level_id, int type_id) {
		String query = "select ID from " + table + " where level_id=? and type_id=?";
		return template.query(query, new Object[] { level_id, type_id }, new ResultSetExtractor<Boolean>() {
			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs.next();
			}
		});
	}

	private boolean isListItemExist(int list_id, String code) {
		String query = "select ID from ListTypeItemD where ListType_id=? and code=?";
		return template.query(query, new Object[] { list_id, code }, new ResultSetExtractor<Boolean>() {
			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs.next();
			}
		});
	}

	/**
	 * 
	 * @param structure
	 * @param code
	 * @param label
	 */
	@Override
	public boolean addNoteTypeD(String hierachy,String code, String label) {
		int hierachy_id = fetchIdByTable("HierarchyD", hierachy);
		if (isCodeExistForHierarchy("NoteTypeD", code,hierachy_id)) {
			System.out.println(code + " already exist");
			return false;
		}

		String query = "INSERT INTO NoteTypeD (code,narrative,hierarchy_id) VALUES ('" + code + "','" + label + "', "+hierachy_id+")";
		System.out.println(query);
		template.execute(query);
		return true;
	}

	@Override
	public boolean addNotePropertyD(String noteCode, String property, String val) {
		int type_id = fetchIdByTable("NoteTypeD", noteCode);
		int property_id = fetchIdByTable("PropertyD", property);

		if (isPropertyExist("NotePropertyD", type_id, property_id)) {
			System.out.println(type_id + " already exist");
			return false;
		}

		String query = "INSERT INTO NotePropertyD (type_id,property_id,value) VALUES (" + type_id + "," + property_id
				+ ", '" + val + "' )";
		System.out.println(query);
		template.execute(query);
		return true;
	}

	@Override
	public boolean addNoteUsageD(String level, String code) {
		int level_id = fetchIdByTable("LevelD", level);
		int type_id = fetchIdByTable("NoteTypeD", code);

		if (isUsageExist("NoteUsageD", level_id, type_id)) {
			System.out.println(code + " code for " + level + " already exist");
			return false;
		}
		String query = "INSERT INTO NoteUsageD (level_id,type_id) VALUES (" + level_id + "," + type_id + ")";
		System.out.println(query);
		template.execute(query);
		return true;
	}

	/**
	 * 
	 * @param code
	 * @param label
	 */
	@Override
	public boolean addListTypeD(String hierachy,String code, String label) {
		int hierachy_id = fetchIdByTable("HierarchyD", hierachy);
		if (isCodeExistForHierarchy("ListTypeD", code,hierachy_id)) {
			System.out.println(code + " already exist");
			return false;
		}

		String query = "INSERT INTO ListTypeD (code,narrative,hierarchy_id) VALUES ('" + code + "','" + label + "',"+hierachy_id+")";
		System.out.println(query);
		template.execute(query);
		return true;
	}

	@Override
	public boolean addListPropertyD(String listCode, String property, String val) {

		int type_id = fetchIdByTable("ListTypeD", listCode);
		int property_id = fetchIdByTable("PropertyD", property);

		if (isPropertyExist("ListPropertyD", type_id, property_id)) {
			System.out.println(type_id + " already exist");
			return false;
		}

		String query = "INSERT INTO ListPropertyD (type_id,property_id,value) VALUES (" + type_id + "," + property_id
				+ ", '" + val + "' )";
		System.out.println(query);
		template.execute(query);
		return true;

	}

	@Override
	public boolean addListTypeItemD(String listCode, String code, String val) {
		int type_id = fetchIdByTable("ListTypeD", listCode);

		if (isListItemExist(type_id, code)) {
			System.out.println(code + " already exist in list item");
			return false;
		}
		String query = "INSERT INTO ListTypeItemD (ListType_id,code,narrative) VALUES (" + type_id + ",'" + code
				+ "', '" + val + "')";
		System.out.println("addListTypeItemD\t" + query);
		template.execute(query);
		return true;
	}

	@Override
	public boolean addListUsageD(String level, String code) {

		int level_id = fetchIdByTable("LevelD", level);
		int type_id = fetchIdByTable("ListTypeD", code);

		if (isUsageExist("ListUsageD", level_id, type_id)) {
			System.out.println(code + " code for " + level + " already exist");
			return false;
		}
		String query = "INSERT INTO ListUsageD (level_id,type_id) VALUES (" + level_id + "," + type_id + ")";
		System.out.println(query);
		template.execute(query);
		return true;
	}

	/**
	 * 
	 * @param code
	 * @param label
	 */
	@Override
	public boolean addFlagTypeD(String hierachy,String code, String label) {
		int hierachy_id = fetchIdByTable("HierarchyD", hierachy);
		if (isCodeExistForHierarchy("FlagTypeD", code,hierachy_id)) {
			System.out.println(code + " already exist");
			return false;
		}

		String query = "INSERT INTO FlagTypeD (code,narrative,hierarchy_id) VALUES ('" + code + "','" + label + "',"+hierachy_id+")";
		System.out.println(query);
		template.execute(query);
		return true;
	}

	@Override
	public boolean addFlagPropertyD(String flagCode, String property, String val) {

		int type_id = fetchIdByTable("FlagTypeD", flagCode);
		int property_id = fetchIdByTable("PropertyD", property);

		if (isPropertyExist("FlagPropertyD", type_id, property_id)) {
			System.out.println(property + " already exist for " + flagCode);
			return false;
		}

		String query = "INSERT INTO FlagPropertyD (type_id,property_id,value) VALUES (" + type_id + "," + property_id
				+ ", '" + val + "' )";
		System.out.println(query);
		template.execute(query);
		return true;

	}

	@Override
	public boolean addFlagUsageD(String level, String code) {

		int level_id = fetchIdByTable("LevelD", level);
		int type_id = fetchIdByTable("FlagTypeD", code);

		if (isUsageExist("FlagUsageD", level_id, type_id)) {
			System.out.println(code + " code for " + level + " already exist");
			return false;
		}
		String query = "INSERT INTO FlagUsageD (level_id,type_id) VALUES (" + level_id + "," + type_id + ")";
		System.out.println(query);
		template.execute(query);
		return true;
	}

	@Override
	public boolean addDroolRuleD(String code, String requestType, String hierarchy, String condition) {
		int code_id = fetchIdByTable("DroolRuleD", code);
		if (code_id>0) {
			System.out.println(code + " code already exist for drools rule.");
			return false;
		}
		
		int request_id = fetchIdByTable("RequestTypeD", requestType);
		int hierarchy_id = fetchIdByTable("HierarchyD", hierarchy);

		String query = "INSERT INTO DroolRuleD(code,requestType,startDate,conditions,hierarchy) VALUES ('"+code+"', "+request_id+", now(), '"+condition+"', "+hierarchy_id+")";
		System.out.println("Query:"+query);		
		template.execute(query);
		return true;
	}

	@Override
	public boolean addDroolActionD(String code, String method, String className) {
		int code_id = fetchIdByTable("DroolActionD", code);
		if (code_id>0) {
			System.out.println(code + " code already exist for drools action.");
			return false;
		}
		
		String query = "INSERT INTO DroolActionD(code,startDate,method,className) VALUES ('"+code+"',  now(), '"+method+"', '"+className+"')";
		System.out.println("Query:"+query);		
		template.execute(query);
		return true;

	}

	@Override
	public boolean addDroolRuleActionD(String ruleCode, String actionCode) {
		int ruleCode_id = fetchIdByTable("DroolRuleD", ruleCode);
		int actionCode_id = fetchIdByTable("DroolActionD", actionCode);
		if (ruleCode_id<1) {
			System.out.println(ruleCode + " Code doesnt exist for drools rule.");
			return false;
		}
		if (actionCode_id<1) {
			System.out.println(actionCode+ " code doesnt exist for drools action.");
			return false;
		}
		String query= "INSERT INTO DroolRuleActionD(Rule_id,Action_id,startDate) VALUES ("+ruleCode_id+","+actionCode_id+",now())";
		System.out.println("Query:"+query);		
		template.execute(query);
		return true;
	}

}
