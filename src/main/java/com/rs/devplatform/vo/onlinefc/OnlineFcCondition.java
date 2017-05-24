package com.rs.devplatform.vo.onlinefc;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rs.devplatform.vo.ColumnVO;

public class OnlineFcCondition extends ColumnVO{

	@JSONField(name="isSearch")
	@JsonProperty(value="isSearch")
	private String issearch="Y";

	public String getIssearch() {
		return issearch;
	}

	public void setIssearch(String issearch) {
		this.issearch = issearch;
	}
	
	@NotEmpty(message="字段名称不能为空")
	@Override
	public String getColName() {
		return super.getColName();
	}

	@NotEmpty(message="字段描述不能为空")
	@Override
	public String getColMark() {
		return super.getColMark();
	}

	@NotEmpty(message="字段类型不能为空")
	@Override
	public String getColType() {
		return super.getColType();
	}
}
