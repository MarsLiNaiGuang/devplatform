/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.persistent.sample;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.rs.framework.common.entity.base.CUBaseTO;

/**
 *
 * 
 *	@author sample test
 */
@TableName("tbl_sample")
public class CgSample extends CUBaseTO implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	
	/** PK */
	@TableId(value = "sample_id", type = IdType.UUID)
	private String id;
	/**  */
	@TableField(value = "sample_name")
	private String name;
	/**  */
	@TableField(value = "sample_nkname")
	private String nkname;
	/**  */
	@TableField(value = "sample_age")
	private Integer age;
	/**  */
	@TableField(value = "sample_whr")
	private String whr;
	/**  */
	@TableField(value = "sample_whrid")
	private String whrid;
	/**  */
	@TableField(value = "sample_whsj")
	private Date whsj;
	/**  */
	@TableField(value = "sample_cjr")
	private String cjr;
	/**  */
	@TableField(value = "sample_cjrid")
	private String cjrid;
	/**  */
	@TableField(value = "sample_cjsj")
	private Date cjsj;
	/**  */
	@TableField(value = "sample_deleted")
	private String deleted;
	/**  */
	@TableField(value = "sample_version")
	private Integer version;

	public String getId () {
		return this.id;
	}

	public void setId (String id) {
		this.id = id;
	}
	public String getName () {
		return this.name;
	}

	public void setName (String name) {
		this.name = name;
	}
	public String getNkname () {
		return this.nkname;
	}

	public void setNkname (String nkname) {
		this.nkname = nkname;
	}
	public Integer getAge () {
		return this.age;
	}

	public void setAge (Integer age) {
		this.age = age;
	}
	public String getWhr () {
		return this.whr;
	}

	public void setWhr (String whr) {
		this.whr = whr;
	}
	public String getWhrid () {
		return this.whrid;
	}

	public void setWhrid (String whrid) {
		this.whrid = whrid;
	}
	public Date getWhsj () {
		return this.whsj;
	}

	public void setWhsj (Date whsj) {
		this.whsj = whsj;
	}
	public String getCjr () {
		return this.cjr;
	}

	public void setCjr (String cjr) {
		this.cjr = cjr;
	}
	public String getCjrid () {
		return this.cjrid;
	}

	public void setCjrid (String cjrid) {
		this.cjrid = cjrid;
	}
	public Date getCjsj () {
		return this.cjsj;
	}

	public void setCjsj (Date cjsj) {
		this.cjsj = cjsj;
	}
	public String getDeleted () {
		return this.deleted;
	}

	public void setDeleted (String deleted) {
		this.deleted = deleted;
	}
	public Integer getVersion () {
		return this.version;
	}

	public void setVersion (Integer version) {
		this.version = version;
	}
	
	public String asSqlString(){
		StringBuilder sb = new StringBuilder();
		sb.append("sample_id").append(" as ").append("id").append(",");
		sb.append("sample_name").append(" as ").append("name").append(",");
		sb.append("sample_nkname").append(" as ").append("nkname").append(",");
		sb.append("sample_age").append(" as ").append("age").append(",");
		sb.append("sample_whr").append(" as ").append("whr").append(",");
		sb.append("sample_whrid").append(" as ").append("whrid").append(",");
		sb.append("sample_whsj").append(" as ").append("whsj").append(",");
		sb.append("sample_cjr").append(" as ").append("cjr").append(",");
		sb.append("sample_cjrid").append(" as ").append("cjrid").append(",");
		sb.append("sample_cjsj").append(" as ").append("cjsj").append(",");
		sb.append("sample_deleted").append(" as ").append("deleted").append(",");
		sb.append("sample_version").append(" as ").append("version").append(",");
		return sb.toString();
	}

	@Override
	public String toString() {
		return "CgSample [id=" + id + ", name=" + name + ", nkname=" + nkname + ", age=" + age + ", whr=" + whr
				+ ", whrid=" + whrid + ", whsj=" + whsj + ", cjr=" + cjr + ", cjrid=" + cjrid + ", cjsj=" + cjsj
				+ ", deleted=" + deleted + ", version=" + version + "]";
	}
	
}
