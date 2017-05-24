/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.vo.jcsj;

import java.util.Date;
import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.rs.devplatform.persistent.jcsj.Bmpz;
import com.rs.framework.common.entity.page.PageVO;

/**
 * Entity VO:
 * 支持fastjson&jackson两种方式序列化对象
 *	@author RSDevPlatform
 */
public class BmpzVO extends Bmpz implements PageVO {

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
	

	public Integer getCurrent(){
		return current;
	}
	public void setCurrent(Integer current){
		this.current = current;
	}
	public Integer getSize(){
		return size;
	}
	public void setSize(Integer size){
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
}
