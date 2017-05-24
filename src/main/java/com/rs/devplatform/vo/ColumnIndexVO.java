package com.rs.devplatform.vo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 表单索引
 * @author yuxiaobin
 *
 */
public class ColumnIndexVO implements Serializable{

	private static final long serialVersionUID = 1L;

	@JSONField(name="id")
	@JsonProperty(value="id")
	protected String indexId;
	
	protected Integer indexSeq;
	protected String indexName;
	protected String colName;
	protected String indexType;
	
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public Integer getIndexSeq() {
		return indexSeq;
	}
	public void setIndexSeq(Integer indexSeq) {
		this.indexSeq = indexSeq;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getIndexType() {
		return indexType;
	}
	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}
	@Override
	public String toString() {
		return "ColumnIndexVO [indexId=" + indexId + ", indexSeq=" + indexSeq + ", indexName=" + indexName
				+ ", colName=" + colName + ", indexType=" + indexType + "]";
	}

}
