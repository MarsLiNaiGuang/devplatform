package com.rs.devplatform.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rs.devplatform.persistent.SysTables;
import com.rs.framework.common.entity.page.PageVO;

public class SysTableVO extends SysTables implements PageVO {

	private static final long serialVersionUID = 1L;
	/*
	 * for pagination
	*/
	private Integer current;
	private Integer size;
	/*
	 * order by
	*/
	private String orderBy;
	private String asc;
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getAsc() {
		return asc;
	}
	public void setAsc(String asc) {
		this.asc = asc;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@JSONField(name="tablename")
	@JsonProperty(value="tablename")
	@Override
	public String getName() {
		return super.getName();
	}
	@JSONField(name="isdbsyn")
	@JsonProperty(value="isdbsyn")
	@Override
	public String getSync() {
		return super.getSync();
	}
	@JSONField(name="isdbsyn")
	@JsonProperty(value="isdbsyn")
	@Override
	public void setSync(String sync) {
		super.setSync(sync);
	}
	
	@JSONField(name="tablename")
	@JsonProperty(value="tablename")
	@Override
	public void setName(String name) {
		super.setName(name);
	}
	@JSONField(name="tabledescp")
	@JsonProperty(value="tabledescp")
	@Override
	public String getComment() {
		return super.getComment();
	}
	@JSONField(name="tabledescp")
	@JsonProperty(value="tabledescp")
	@Override
	public void setComment(String comment) {
		super.setComment(comment);
	}

}
