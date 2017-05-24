package com.rs.devplatform.vo;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSONArray;
import com.rs.devplatform.persistent.SysPj2res;

public class ProjectInfo{

	private String id;
	@NotEmpty
	private String name;
	private Integer version;
	private SysPj2res db;
	
	private JSONArray managers;
	
	public JSONArray getManagers() {
		return managers;
	}
	public void setManagers(JSONArray managers) {
		this.managers = managers;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public SysPj2res getDb() {
		return db;
	}

	public void setDb(SysPj2res db) {
		this.db = db;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
