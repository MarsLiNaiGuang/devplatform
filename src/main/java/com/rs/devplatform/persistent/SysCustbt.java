package com.rs.devplatform.persistent;

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
@TableName("sys_custbt")
public class SysCustbt extends CUBaseTO {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 自定义按钮主键 */
	@TableId(value = "CUSTBT_ID", type = IdType.UUID)
	private String id;

	/** 自定义按钮编码 */
	@TableField(value = "CUSTBT_CODE")
	private String code;

	/** 自定义按钮图标 */
	@TableField(value = "CUSTBT_ICON")
	private String icon;

	/** 自定义按钮名字 */
	@TableField(value = "CUSTBT_NAME")
	private String name;

	/** 自定义按钮状态 */
	@TableField(value = "CUSTBT_ZT")
	private String zt;

	/** 自定义按钮样式 */
	@TableField(value = "CUSTBT_YS")
	private String ys;

	/** 自定义按钮过期时间 */
	@TableField(value = "CUSTBT_EXP")
	private String exp;

	/** 表单ID */
	@TableField(value = "CUSTBT_BDID")
	private String bdid;

	/** 按钮操作类型 */
	@TableField(value = "CUSTBT_CZLX")
	private String czlx;

	/** 按钮序号 */
	@TableField(value = "CUSTBT_XH")
	private Integer xh;

	/** 维护人名字 */
	@TableField(value = "CUSTBT_WHR")
	private String whr;

	/** 维护时间 */
	@TableField(value = "CUSTBT_WHSJ")
	private Date whsj;

	/** 维护人ID */
	@TableField(value = "CUSTBT_WHRID")
	private String whrid;

	/** 创建人名字 */
	@TableField(value = "CUSTBT_CJR")
	private String cjr;

	/** 创建时间 */
	@TableField(value = "CUSTBT_CJSJ")
	private Date cjsj;

	/** 创建人ID */
	@TableField(value = "CUSTBT_CJRID")
	private String cjrid;

	/** 数据记录版本 */
	@TableField(value = "CUSTBT_VERSION")
	private Integer version;

	/** 逻辑删除标记位 */
	@TableField(value = "CUSTBT_DELETED")
	private String deleted;


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZt() {
		return this.zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getYs() {
		return this.ys;
	}

	public void setYs(String ys) {
		this.ys = ys;
	}

	public String getExp() {
		return this.exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getBdid() {
		return this.bdid;
	}

	public void setBdid(String bdid) {
		this.bdid = bdid;
	}

	public String getCzlx() {
		return this.czlx;
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	public Integer getXh() {
		return this.xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
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
