package com.rs.devplatform.generator;

public class EntityField {
	
	String columnName;//数据库字段名
	
	String beanPropertyName;//实体类的属性名
	
	String formPropertyName;//表单属性名(中文描述等)
	
	boolean pk=false;
	boolean isExist=true;
	
	boolean unique=false;
	/**
	 * isFormData/isNullable 配合，生成@NotEmpty注解，在add方法调用validate()
	 */
	boolean formData=false;//是否是表单属性
	boolean gridData=false;//是否列表显示
	boolean nullable=true;
	boolean basicColumn=false;//是否是基础字段
	boolean hasDefaultVal=false;
	boolean query;//是否查询
	boolean rangeQuery;//是否范围查询
	boolean fuzzyQuery;//是否范围查询
	
	String defaultVal;
	String typeImport;//java.util.Date
	String typeSimple;//简单类型，Date
	
	public boolean isBasicColumn() {
		return basicColumn;
	}
	public void setBasicColumn(boolean basicColumn) {
		this.basicColumn = basicColumn;
	}
	public boolean isHasDefaultVal() {
		return hasDefaultVal;
	}
	public void setHasDefaultVal(boolean hasDefaultVal) {
		this.hasDefaultVal = hasDefaultVal;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getBeanPropertyName() {
		return beanPropertyName;
	}
	public void setBeanPropertyName(String beanPropertyName) {
		this.beanPropertyName = beanPropertyName;
	}
	public String getFormPropertyName() {
		return formPropertyName;
	}
	public void setFormPropertyName(String formPropertyName) {
		this.formPropertyName = formPropertyName;
	}
	public boolean isPk() {
		return pk;
	}
	public void setPk(boolean pk) {
		this.pk = pk;
	}
	public boolean isUnique() {
		return unique;
	}
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	public boolean isFormData() {
		return formData;
	}
	public void setFormData(boolean formData) {
		this.formData = formData;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public String getTypeSimple() {
		return typeSimple;
	}
	public void setTypeSimple(String typeSimple) {
		this.typeSimple = typeSimple;
	}
	public boolean isGridData() {
		return gridData;
	}
	public void setGridData(boolean gridData) {
		this.gridData = gridData;
	}
	public boolean isRangeQuery() {
		return rangeQuery;
	}
	public void setRangeQuery(boolean rangeQuery) {
		this.rangeQuery = rangeQuery;
	}
	public String getDefaultVal() {
		return defaultVal;
	}
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}
	public String getTypeImport() {
		return typeImport;
	}
	public void setTypeImport(String typeImport) {
		this.typeImport = typeImport;
	}
	public boolean isQuery() {
		return query;
	}
	public void setQuery(boolean query) {
		this.query = query;
	}
	public boolean isFuzzyQuery() {
		return fuzzyQuery;
	}
	public void setFuzzyQuery(boolean fuzzyQuery) {
		this.fuzzyQuery = fuzzyQuery;
	}
	
}
