package com.rs.devplatform.persistent;

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
 *
 */
@TableName("sys_pjs")
public class SysPj extends CUBaseTO implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(value = "PJS_ID", type = IdType.UUID)
	private String id;

	/**  */
	@TableField(value = "PJS_NAME")
	private String name;

	/**  */
	@TableField(value = "PJS_WHR")
	private String whr;

	/**  */
	@TableField(value = "PJS_WHSJ")
	private Date whsj;

	/**  */
	@TableField(value = "PJS_WHRID")
	private String whrid;

	/**  */
	@TableField(value = "PJS_CJR")
	private String cjr;

	/**  */
	@TableField(value = "PJS_CJSJ")
	private Date cjsj;

	/**  */
	@TableField(value = "PJS_CJRID")
	private String cjrid;

	/**  */
	@TableField(value = "PJS_VERSION")
	private Integer version;

	/**  */
	@TableField(value = "PJS_DELETED")
	private String deleted;
	
	/**
	 * 是否初始化
	 */
	@TableField(value = "PJS_INIT")
	private String init;


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWhr() {
		return this.whr;
	}

	public void setWhr(String whr) {
		this.whr = whr;
	}

	public Date getWhsj() {
		return this.whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public String getWhrid() {
		return this.whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public String getCjr() {
		return this.cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public Date getCjsj() {
		return this.cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getCjrid() {
		return this.cjrid;
	}

	public void setCjrid(String cjrid) {
		this.cjrid = cjrid;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDeleted() {
		return this.deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getInit() {
		return init;
	}

	public void setInit(String init) {
		this.init = init;
	}

}
