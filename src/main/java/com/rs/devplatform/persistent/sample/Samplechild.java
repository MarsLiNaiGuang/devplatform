package com.rs.devplatform.persistent.sample;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.rs.framework.common.annotations.RsFKField;
import com.rs.framework.common.entity.base.CUBaseTO;

/**
 *
 * 
 *
 */
@TableName("tbl_samplechild")
public class Samplechild extends CUBaseTO {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(value = "SAMPLECHILD_ID", type = IdType.UUID)
	private String id;

	/**  */
	@TableField(value = "SAMPLECHILD_NAME")
	private String name;

	/**  */
	@TableField(value = "SAMPLECHILD_FK")
	@RsFKField(parentClass=CgSample.class)
	private String fk;

	/**  */
	@TableField(value = "SAMPLECHILD_WHR")
	private String whr;

	/**  */
	@TableField(value = "SAMPLECHILD_WHSJ")
	private Date whsj;

	/**  */
	@TableField(value = "SAMPLECHILD_WHRID")
	private String whrid;

	/**  */
	@TableField(value = "SAMPLECHILD_CJR")
	private String cjr;

	/**  */
	@TableField(value = "SAMPLECHILD_CJSJ")
	private Date cjsj;

	/**  */
	@TableField(value = "SAMPLECHILD_CJRID")
	private String cjrid;

	/**  */
	@TableField(value = "SAMPLECHILD_VERSION")
	private Integer version;

	/**  */
	@TableField(value = "SAMPLECHILD_DELETED")
	private String deleted;

	public Samplechild() {
		super();
	}

	public Samplechild(String name) {
		super();
		this.name = name;
	}

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

	public String getFk() {
		return this.fk;
	}

	public void setFk(String fk) {
		this.fk = fk;
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

}
