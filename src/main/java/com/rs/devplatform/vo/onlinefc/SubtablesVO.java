package com.rs.devplatform.vo.onlinefc;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 维护主子表关联字段关系的对象
 * 
 * @author yuxiaobin
 *
 */
public class SubtablesVO {

//	@NotEmpty(message="子表表名不能为空")
	private String tablename;//子表表名
	
	@NotEmpty(message="子表关联字段名不能为空")
	private String colName;//子表关联字段名
	
	@NotEmpty(message="主表关联字段名不能为空")
	private String mainColName;//主表关联字段名
	
	@NotEmpty(message="功能编号不能为空")
	private String fcflag;//功能编号
	
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		if(colName!=null){
			colName = colName.toUpperCase();
		}
		this.colName = colName;
	}
	public String getMainColName() {
		return mainColName;
	}
	public void setMainColName(String mainColName) {
		if(mainColName!=null){
			mainColName = mainColName.toUpperCase();
		}
		this.mainColName = mainColName;
	}
	public String getFcflag() {
		return fcflag;
	}
	public void setFcflag(String fcflag) {
		this.fcflag = fcflag;
	}
	@Override
	public String toString() {
		return "SubtablesVO [tablename=" + tablename + ", colName=" + colName + ", mainColName=" + mainColName
				+ ", fcflag=" + fcflag + "]";
	}
	
	
}
