package com.rs.devplatform.vo;

import java.util.Date;

import com.rs.framework.common.entity.base.CUBaseTO;

public class UserInfo extends CUBaseTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String whr;
	private Date whsj;
	private String whrid;
	private String cjr;
	private Date cjsj;
	private String cjrid;
	public String getWhr() {
		return whr;
	}
	public void setWhr(String whr) {
		this.whr = whr;
	}
	public Date getWhsj() {
		return whsj;
	}
	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}
	public String getWhrid() {
		return whrid;
	}
	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}
	public String getCjr() {
		return cjr;
	}
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	public Date getCjsj() {
		return cjsj;
	}
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	public String getCjrid() {
		return cjrid;
	}
	public void setCjrid(String cjrid) {
		this.cjrid = cjrid;
	}
	@Override
	public Integer getVersion() {
		return null;
	}
	@Override
	public void setVersion(Integer version) {
		
	}
	@Override
	public String getDeleted() {
		return null;
	}
	@Override
	public void setDeleted(String deleted) {
		
	}
	@Override
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
