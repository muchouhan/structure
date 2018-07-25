package com.mukund.structure.metatool;

public interface MetaToolDAO {
	
	boolean addHierarchyD(String code, String label);
	
	boolean addLevelD(String hierachy,String code, String label);

	boolean addPropertyD(String code, String label);

	boolean addNoteTypeD(String hierachy,String code, String label);

	boolean addNotePropertyD(String noteCode, String property, String val);

	boolean addNoteUsageD(String level, String code);

	boolean addListTypeD(String hierachy,String code, String label);

	boolean addListPropertyD(String listCode, String property, String val);

	boolean addListTypeItemD(String listCode, String code, String val);

	boolean addListUsageD(String level, String code);

	boolean addFlagTypeD(String hierachy,String code, String label);

	boolean addFlagPropertyD(String flagCode, String property, String val);

	boolean addFlagUsageD(String level, String code);
	
	boolean addDroolRuleD(String code,String requestType,String hierarchy, String condition);
	
	boolean addDroolActionD(String code,String method,String className);
	
	boolean addDroolRuleActionD(String ruleCode,String actionCode);
	
	
}
