package com.rs.devplatform.persistent;

import java.io.Serializable;
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
@TableName("sys_cdval")
public class SysCdval extends CUBaseTO implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(value = "CDVAL_ID", type = IdType.UUID)
	private String id;

	/**  */
	@TableField(value = "CDVAL_CDTYPE")
	@NotEmpty
	private String cdtype;

	/**  */
	@TableField(value = "CDVAL_CDVAL")
	@NotEmpty
	private String cdval;

	/**  */
	@TableField(value = "CDVAL_CDDESCP")
	@NotEmpty
	private String cddescp;

	/**  */
	@TableField(value = "CDVAL_WHR")
	private String whr;

	/**  */
	@TableField(value = "CDVAL_WHSJ")
	private Date whsj;

	/**  */
	@TableField(value = "CDVAL_WHRID")
	private String whrid;

	/**  */
	@TableField(value = "CDVAL_CJR")
	private String cjr;

	/**  */
	@TableField(value = "CDVAL_CJSJ")
	private Date cjsj;

	/**  */
	@TableField(value = "CDVAL_CJRID")
	private String cjrid;

	/**  */
	@TableField(value = "CDVAL_VERSION")
	private Integer version;

	/**  */
	@TableField(value = "CDVAL_DELETED")
	private String deleted;


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCdtype() {
		return this.cdtype;
	}

	public void setCdtype(String cdtype) {
		this.cdtype = cdtype;
	}

	public String getCdval() {
		return this.cdval;
	}

	public void setCdval(String cdval) {
		this.cdval = cdval;
	}

	public String getCddescp() {
		return this.cddescp;
	}

	public void setCddescp(String cddescp) {
		this.cddescp = cddescp;
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
