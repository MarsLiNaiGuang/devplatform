package com.rs.devplatform.vo.onlinefc;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rs.devplatform.vo.ColumnVO;

public class OnlineFcColumn extends ColumnVO{
	
	@JSONField(name="isGriddisp")
	@JsonProperty(value="isGriddisp")
	private String isgriddisp = "Y";

	public String getIsgriddisp() {
		return isgriddisp;
	}

	public void setIsgriddisp(String isgriddisp) {
		this.isgriddisp = isgriddisp;
	}
	
	@NotEmpty(message="字段名称不能为空")
	public String getColName(){
		return super.getColName();
	}
//	@NotEmpty(message="字段名称不能为空")
//	@Override
//	public String getCol_name() {
//		return super.getCol_name();
//	}

	@NotEmpty(message="字段描述不能为空")
	public String getColMark() {
		return super.getColMark();
	}
//	@NotEmpty(message="字段描述不能为空")
//	@Override
//	public String getCol_mark() {
//		return super.getCol_mark();
//	}

	@NotEmpty(message="字段类型不能为空")
	public String getColType() {
		return super.getColType();
	}
//	@NotEmpty(message="字段类型不能为空")
//	@Override
//	public String getCol_type() {
//		return super.getCol_type();
//	}
	
}
