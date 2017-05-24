package com.rs.devplatform.persistent;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

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
@TableName("sys_cdtype")
public class SysCdtype extends CUBaseTO {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(value = "CDTYPE_ID", type = IdType.UUID)
	private String id;

	/**  */
	@TableField(value = "CDTYPE_NAME")
	@NotEmpty
	private String name;

	/**  */
	@TableField(value = "CDTYPE_CDTYPE")
	private String cdtype;

	/**  */
	@TableField(value = "CDTYPE_WHR")
	private String whr;

	/**  */
	@TableField(value = "CDTYPE_WHSJ")
	private Date whsj;

	/**  */
	@TableField(value = "CDTYPE_WHRID")
	private String whrid;

	/**  */
	@TableField(value = "CDTYPE_CJR")
	private String cjr;

	/**  */
	@TableField(value = "CDTYPE_CJSJ")
	private Date cjsj;

	/**  */
	@TableField(value = "CDTYPE_CJRID")
	private String cjrid;

	/**  */
	@TableField(value = "CDTYPE_VERSION")
	private Integer version;

	/**  */
	@TableField(value = "CDTYPE_DELETED")
	private String deleted;


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

	public String getCdtype() {
		return this.cdtype;
	}

	public void setCdtype(String cdtype) {
		this.cdtype = cdtype;
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
