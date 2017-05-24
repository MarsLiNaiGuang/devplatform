package com.rs.devplatform.vo;

public class BaseColumnVO {

	private String columnName;
	private String columnDescp;
	private String columnType;
	private Integer columnLength;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnDescp() {
		return columnDescp;
	}
	public void setColumnDescp(String columnDescp) {
		this.columnDescp = columnDescp;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public Integer getColumnLength() {
		return columnLength;
	}
	public void setColumnLength(Integer columnLength) {
		this.columnLength = columnLength;
	}
	public BaseColumnVO(String columnName, String columnDescp, String columnType, Integer columnLength) {
		super();
		this.columnName = columnName;
		this.columnDescp = columnDescp;
		this.columnType = columnType;
		this.columnLength = columnLength;
	}
	public BaseColumnVO() {
		super();
	}
	@Override
	public String toString() {
		return "BaseColumnVO [columnName=" + columnName + ", columnDescp=" + columnDescp + ", columnType=" + columnType
				+ ", columnLength=" + columnLength + "]";
	}
	
}
