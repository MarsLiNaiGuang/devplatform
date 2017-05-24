package com.rs.devplatform.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ColumnVO {

	@JSONField(name="colSeq")
	@JsonProperty(value="colSeq")
	Integer col_seq;
	
	@JSONField(name="colName")
	@JsonProperty(value="colName")
	String colName;
	
	@JSONField(name="colMark")
	@JsonProperty(value="colMark")
	String colMark;
	
	@JSONField(name="colLen")
	@JsonProperty(value="colLen")
	Integer col_length;
	
	@JSONField(name="colDecpoint")
	@JsonProperty(value="colDecpoint")
	Integer col_decpoint;
	
	@JSONField(name="colDefval")
	@JsonProperty(value="colDefval")
	String col_defval;
	
	@JSONField(name="colType")
	@JsonProperty(value="colType")
	String colType;
	
	@JSONField(name="colIspk")
	@JsonProperty(value="colIspk")
	String col_ispk;
	
	@JSONField(name="colIsnull")
	@JsonProperty(value="colIsnull")
	String col_isnull;
	
	@JSONField(name="isFormdisp")
	@JsonProperty(value="isFormdisp")
	String isformdisp;
	
	@JSONField(name="isGriddisp")
	@JsonProperty(value="isGriddisp")
	String isgriddisp;
	
	@JSONField(name="inputType")
	@JsonProperty(value="inputType")
	String inputtype;
	
	@JSONField(name="inputLen")
	@JsonProperty(value="inputLen")
	Integer inputlen;
	
	@JSONField(name="isSearch")
	@JsonProperty(value="isSearch")
	String issearch;
	
	@JSONField(name="searchType")
	@JsonProperty(value="searchType")
	String searchtype;
	
	String colHref;
	
	String validType;
	
	String dictTable;
	
	String dictCode;
	
	String dictText;
	
	String fkTableName;
	
	String fkColName;
	
	String extjson;
	
	/** 条件表达式 20170320 */
	private String expr;
	/** 字段名别名 */
	private String alias;
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getExpr() {
		return expr;
	}
	public void setExpr(String expr) {
		this.expr = expr;
	}

	@JSONField(name="id")
	@JsonProperty(value="id")
	String col_id;
	String deleted;
	Integer version;
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getCol_id() {
		return col_id;
	}
	public void setCol_id(String col_id) {
		this.col_id = col_id;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public Integer getCol_seq() {
		return col_seq;
	}
	public void setCol_seq(Integer col_seq) {
		this.col_seq = col_seq;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String col_name) {
		this.colName = col_name;
	}
	public String getColMark() {
		return colMark;
	}
	public void setColMark(String col_mark) {
		this.colMark = col_mark;
	}
	public Integer getCol_length() {
		return col_length;
	}
	public void setCol_length(Integer col_length) {
		this.col_length = col_length;
	}
	public Integer getCol_decpoint() {
		return col_decpoint;
	}
	public void setCol_decpoint(Integer col_decpoint) {
		this.col_decpoint = col_decpoint;
	}
	public String getCol_defval() {
		return col_defval;
	}
	public void setCol_defval(String col_defval) {
		this.col_defval = col_defval;
	}
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}
	public String getCol_ispk() {
		return col_ispk;
	}
	public void setCol_ispk(String col_ispk) {
		this.col_ispk = col_ispk;
	}
	public String getCol_isnull() {
		return col_isnull;
	}
	public void setCol_isnull(String col_isnull) {
		this.col_isnull = col_isnull;
	}
	public String getIsformdisp() {
		return isformdisp;
	}
	public void setIsformdisp(String isformdisp) {
		this.isformdisp = isformdisp;
	}
	public String getIsgriddisp() {
		return isgriddisp;
	}
	public void setIsgriddisp(String isgriddisp) {
		this.isgriddisp = isgriddisp;
	}
	public String getInputtype() {
		return inputtype;
	}
	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}
	public Integer getInputlen() {
		return inputlen;
	}
	public void setInputlen(Integer inputlen) {
		this.inputlen = inputlen;
	}
	public String getIssearch() {
		return issearch;
	}
	public void setIssearch(String issearch) {
		this.issearch = issearch;
	}
	public String getSearchtype() {
		return searchtype;
	}
	
	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}
	public String getExtjson() {
		return extjson;
	}
	public void setExtjson(String extjson) {
		this.extjson = extjson;
	}
	
	public String getColHref() {
		return colHref;
	}
	public void setColHref(String colHref) {
		this.colHref = colHref;
	}
	public String getValidType() {
		return validType;
	}
	public void setValidType(String validType) {
		this.validType = validType;
	}
	public String getDictTable() {
		return dictTable;
	}
	public void setDictTable(String dictTable) {
		this.dictTable = dictTable;
	}
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public String getDictText() {
		return dictText;
	}
	public void setDictText(String dictText) {
		this.dictText = dictText;
	}
	public String getFkTableName() {
		return fkTableName;
	}
	public void setFkTableName(String fkTableName) {
		this.fkTableName = fkTableName;
	}
	public String getFkColName() {
		return fkColName;
	}
	public void setFkColName(String fkColName) {
		this.fkColName = fkColName;
	}
	@Override
	public String toString() {
		return "ColumnVO [col_id=" + col_id+ ", colName="+colName + ", colMark=" + colMark + ", colType=" + colType 
				+ "]";
	}
	
	
	
}
