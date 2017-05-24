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
@TableName("sys_pj2res")
public class SysPj2res extends CUBaseTO implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(value = "PJ2RES_ID", type = IdType.UUID)
	private String id;

	/**  */
	@TableField(value = "PJ2RES_PJ_ID")
	private String pjId;

	/**  */
	@TableField(value = "PJ2RES_DB_TYPE")
	private String dbType;

	/**  */
	@TableField(value = "PJ2RES_DB_URL")
	private String dbUrl;

	/**  */
	@TableField(value = "PJ2RES_DB_USER")
	private String dbUser;

	/**  */
	@TableField(value = "PJ2RES_DB_PWD")
	private String dbPwd;

	/**  */
	@TableField(value = "PJ2RES_WHR")
	private String whr;

	/**  */
	@TableField(value = "PJ2RES_WHSJ")
	private Date whsj;

	/**  */
	@TableField(value = "PJ2RES_WHRID")
	private String whrid;

	/**  */
	@TableField(value = "PJ2RES_CJR")
	private String cjr;

	/**  */
	@TableField(value = "PJ2RES_CJSJ")
	private Date cjsj;

	/**  */
	@TableField(value = "PJ2RES_CJRID")
	private String cjrid;

	/**  */
	@TableField(value = "PJ2RES_VERSION")
	private Integer version;

	/**  */
	@TableField(value = "PJ2RES_DELETED")
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

	public String getDbType() {
		return this.dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbUrl() {
		return this.dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return this.dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPwd() {
		return this.dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
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
