package com.rs.devplatform.generator;

import java.util.List;

public class EntityTable {
	
	String tableName;
	String beanName;
	String tableComments;
	List<EntityField> entityFields;
	List<String> imports;
	
	String basePackage;
	String subModule;
	String author;
	Boolean isMainTable = false;
	List<String> subTableNames;
	List<String> subFcFlagNames;//just used for code generation
	String sqlInTable = "";
	Boolean isSql = false;
	Boolean isNeedMapXML = true;
	String tableId;
	List<String> cusCodeList;

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public List<String> getImports() {
		return imports;
	}

	public void setImports(List<String> imports) {
		this.imports = imports;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getSubModule() {
		return subModule;
	}

	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComments() {
		return tableComments;
	}

	public void setTableComments(String tableComments) {
		this.tableComments = tableComments;
	}
	

	public Boolean getIsMainTable() {
		return isMainTable;
	}

	public void setIsMainTable(Boolean isMainTable) {
		this.isMainTable = isMainTable;
	}

	public List<EntityField> getEntityFields() {
		return entityFields;
	}

	public void setEntityFields(List<EntityField> entityFields) {
		this.entityFields = entityFields;
	}

	public List<String> getSubTableNames() {
		return subTableNames;
	}

	public void setSubTableNames(List<String> subTableNames) {
		this.subTableNames = subTableNames;
	}

	public String getSqlInTable() {
		return sqlInTable;
	}

	public void setSqlInTable(String sqlInTable) {
		this.sqlInTable = sqlInTable;
	}

	public Boolean getIsSql() {
		return isSql;
	}

	public void setIsSql(Boolean isSql) {
		this.isSql = isSql;
	}

	public List<String> getSubFcFlagNames() {
		return subFcFlagNames;
	}

	public void setSubFcFlagNames(List<String> subFcFlagNames) {
		this.subFcFlagNames = subFcFlagNames;
	}

	public Boolean getIsNeedMapXML() {
		return isNeedMapXML;
	}

	public void setIsNeedMapXML(Boolean isNeedMapXML) {
		this.isNeedMapXML = isNeedMapXML;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public List<String> getCusCodeList() {
		return cusCodeList;
	}

	public void setCusCodeList(List<String> cusCodeList) {
		this.cusCodeList = cusCodeList;
	}
	
	

}
