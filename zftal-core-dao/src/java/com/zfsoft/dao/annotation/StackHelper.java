package com.zfsoft.dao.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StackHelper {
	public String tableName;
	public String keyOf;
	public Map<String, SQLField> sqlMap=new LinkedHashMap<String, SQLField>();
	public Map<String, Method> methodMap=new LinkedHashMap<String, Method>();
	public Map<String, Field> fieldMap=new LinkedHashMap<String, Field>();
	public Map<String, String> sqlLocalNameMap=new HashMap<String, String>();

	public String getKeyOf() {
		return keyOf;
	}

	public void setKeyOf(String keyOf) {
		this.keyOf = keyOf;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, SQLField> getSqlMap() {
		return sqlMap;
	}

	public void setSqlMap(Map<String, SQLField> sqlMap) {
		this.sqlMap = sqlMap;
	}

	public Map<String, Method> getMethodMap() {
		return methodMap;
	}

	public void setMethodMap(Map<String, Method> methodMap) {
		this.methodMap = methodMap;
	}

	public Map<String, Field> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, Field> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public Map<String, String> getSqlLocalNameMap() {
		return sqlLocalNameMap;
	}

	public void setSqlLocalNameMap(Map<String, String> sqlLocalNameMap) {
		this.sqlLocalNameMap = sqlLocalNameMap;
	}
	
	
}