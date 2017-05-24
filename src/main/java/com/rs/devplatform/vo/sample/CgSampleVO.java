/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.vo.sample;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.rs.devplatform.persistent.sample.CgSample;
import com.rs.framework.common.entity.page.PageVO;

/**
 * Entity VO:
 * 支持fastjson&jackson两种方式序列化对象
 *	@author RSDevPlatform
 */
public class CgSampleVO extends CgSample implements PageVO {

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
	private Integer ageFrom;
	private Integer ageTo;
	private Date dtFrom;
	private Date dtTo;
	
	@NotEmpty(message="Username用户名不能为空")
	public String getName () {
		return super.getName ();
	}
	@NotEmpty(message="NickName昵称不能为空")
	public String getNkname () {
		return super.getNkname ();
	}
	public Integer getAgeFrom () {
		return this.ageFrom;
	}
	public void setAgeFrom (Integer ageFrom) {
		this.ageFrom = ageFrom;
	}
	public Integer getAgeTo () {
		return this.ageTo;
	}
	public void setAgeTo (Integer ageTo) {
		this.ageTo = ageTo;
	}
	public Date getDtFrom () {
		return this.dtFrom;
	}
	public void setDtFrom (Date dtFrom) {
		this.dtFrom = dtFrom;
	}
	public Date getDtTo () {
		return this.dtTo;
	}
	public void setDtTo (Date dtTo) {
		this.dtTo = dtTo;
	}

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
