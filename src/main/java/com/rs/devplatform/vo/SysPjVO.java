package com.rs.devplatform.vo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.rs.devplatform.persistent.SysPj;
import com.rs.devplatform.persistent.SysPj2res;
import com.rs.framework.common.entity.page.PageVO;

public class SysPjVO extends SysPj implements PageVO{
	
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
	private String orderByGroup;//orderByGroup = user_name asc, user_age desc
	
	@TableField(exist = false)
	private List<SysPj2res> resList;

	public List<SysPj2res> getResList() {
		return resList;
	}

	public void setResList(List<SysPj2res> resList) {
		this.resList = resList;
	}

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

	public String getOrderByGroup() {
		return orderByGroup;
	}

	public void setOrderByGroup(String orderByGroup) {
		this.orderByGroup = orderByGroup;
	}

	@Override
	@JSONField(serialize=true)
	@JsonProperty(access=Access.READ_WRITE)
	public String getWhr() {
		return super.getWhr();
	}

	@Override
	@JSONField(serialize=true)
	@JsonProperty(access=Access.READ_WRITE)
	public String getCjr() {
		return super.getCjr();
	}
	
	
}
