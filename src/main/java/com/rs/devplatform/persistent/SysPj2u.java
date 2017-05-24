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
@TableName("sys_pj2u")
public class SysPj2u extends CUBaseTO implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(value = "PJ2U_ID", type = IdType.UUID)
	private String id;

	/**  */
	@TableField(value = "PJ2U_PJ_ID")
	private String pjId;

	/**  */
	@TableField(value = "PJ2U_USER_ID")
	private String userId;
	
	@TableField(value = "PJ2U_USER_TYPE")
	private String userType;

	/**  */
	@TableField(value = "PJ2U_WHR")
	private String whr;

	/**  */
	@TableField(value = "PJ2U_WHSJ")
	private Date whsj;

	/**  */
	@TableField(value = "PJ2U_WHRID")
	private String whrid;

	/**  */
	@TableField(value = "PJ2U_CJR")
	private String cjr;

	/**  */
	@TableField(value = "PJ2U_CJSJ")
	private Date cjsj;

	/**  */
	@TableField(value = "PJ2U_CJRID")
	private String cjrid;

	/**  */
	@TableField(value = "PJ2U_VERSION")
	private Integer version;

	/**  */
	@TableField(value = "PJ2U_DELETED")
	private String deleted;


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPjId() {
		return this.pjId;
	}

	public void setPjId(String pjId) {
		this.pjId = pjId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
