package com.rs.devplatform.persistent;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.annotation.JSONField;
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
@TableName("sys_zqjs")
public class SysZqjs extends CUBaseTO {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 增强JS主键 */
	@TableId(value = "ZQJS_ID", type = IdType.UUID)
	private String id;

	/** 增强JS */
	@JSONField(deserialize=false)
	@TableField(value = "ZQJS_JS")
	private byte[] js;

	/** 增强JS类型 */
	@TableField(value = "ZQJS_JSLX")
	@NotEmpty
	private String jslx;

	/** 增强JS内容 */
	@TableField(value = "ZQJS_NR")
	private String nr;

	/** 表单ID */
	@TableField(value = "ZQJS_BDID")
	private String bdid;

	/** 维护人名字 */
	@TableField(value = "ZQJS_WHR")
	private String whr;

	/** 维护时间 */
	@TableField(value = "ZQJS_WHSJ")
	private Date whsj;

	/** 维护人ID */
	@TableField(value = "ZQJS_WHRID")
	private String whrid;

	/** 创建人名字 */
	@TableField(value = "ZQJS_CJR")
	private String cjr;

	/** 创建时间 */
	@TableField(value = "ZQJS_CJSJ")
	private Date cjsj;

	/** 创建人ID */
	@TableField(value = "ZQJS_CJRID")
	private String cjrid;

	/** 数据记录版本 */
	@TableField(value = "ZQJS_VERSION")
	private Integer version;

	/** 逻辑删除标记位 */
	@TableField(value = "ZQJS_DELETED")
	private String deleted;


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getJs() {
		return this.js;
	}

	public void setJs(byte[] js) {
		this.js = js;
	}

	public String getJslx() {
		return this.jslx;
	}

	public void setJslx(String jslx) {
		this.jslx = jslx;
	}

	public String getNr() {
		return this.nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public String getBdid() {
		return this.bdid;
	}

	public void setBdid(String bdid) {
		this.bdid = bdid;
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
