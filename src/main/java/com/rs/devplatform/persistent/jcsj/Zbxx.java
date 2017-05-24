/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.persistent.jcsj;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.rs.framework.common.entity.base.CUBaseTO;

/**
 *
 * 示例
 *
 *	@author RoadShare
 */
@TableName("jcsj_zbxx")
public class Zbxx extends CUBaseTO implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	

     
	 /** 维护人 */
	 @TableField(value = "zbxx_whr")
	private String whr;

     
	 /** 维护时间 */
	 @TableField(value = "zbxx_whsj")
	private Date whsj;

     
	 /** 装备ID */
	 @TableField(value = "zbxx_zbid")
	private String zbid;

     
	 /** 名称 */
	 @TableField(value = "zbxx_mc")
	private String mc;

     
	 /** 装备型号 */
	 @TableField(value = "zbxx_xh")
	private String xh;

     
	 /** 装备规格 */
	 @TableField(value = "zbxx_gg")
	private String gg;

     
	 /** 所属部门 */
	 @TableField(value = "zbxx_bmid")
	private String bmid;

     
	 /** 计量单位 */
	 @TableField(value = "zbxx_jldw")
	private String jldw;

     
	 /** 所属设备 */
	 @TableField(value = "zbxx_sbid")
	private String sbid;

     
	 /** 序列号 */
	 @TableField(value = "zbxx_xlh")
	private String xlh;

     
	 /** 重量 */
	 @TableField(value = "zbxx_zl")
	private Double zl;

     
	 /** 装机数量 */
	 @TableField(value = "zbxx_zjsl")
	private Double zjsl;

     
	 /** 装机时间 */
	 @TableField(value = "zbxx_zjsj")
	private Double zjsj;

     
	 /** 生产厂家 */
	 @TableField(value = "zbxx_sccj")
	private String sccj;

     
	 /** 生产时间 */
	 @TableField(value = "zbxx_scsj")
	private Date scsj;

     
	 /** 存放位置 */
	 @TableField(value = "zbxx_cfwz")
	private String cfwz;

     
	 /** 运行时间 */
	 @TableField(value = "zbxx_yxsj")
	private Date yxsj;

     
	 /** 运行时间单位 */
	 @TableField(value = "zbxx_yxdw")
	private String yxdw;

     
	 /** 备注 */
	 @TableField(value = "zbxx_bz")
	private String bz;

     
	/** PK */
	@TableId(value = "zbxx_id", type = IdType.UUID)
	private String id;

     
	 /** whrid */
	 @TableField(value = "zbxx_whrid")
	private String whrid;

     
	 /** cjr */
	 @TableField(value = "zbxx_cjr")
	private String cjr;

     
	 /** cjrid */
	 @TableField(value = "zbxx_cjrid")
	private String cjrid;

     
	 /** cjsj */
	 @TableField(value = "zbxx_cjsj")
	private Date cjsj;

     
	 /** deleted */
	 @TableField(value = "zbxx_deleted")
	private String deleted;

     
	 /** version */
	 @TableField(value = "zbxx_version")
	private Integer version;

	public String getWhr () {
		return this.whr;
	}

	public void setWhr (String whr) {
		this.whr = whr==null?null:whr.trim();
	}
	public Date getWhsj () {
		return this.whsj;
	}

	public void setWhsj (Date whsj) {
		this.whsj = whsj;
	}
	public String getZbid () {
		return this.zbid;
	}

	public void setZbid (String zbid) {
		this.zbid = zbid==null?null:zbid.trim();
	}
	public String getMc () {
		return this.mc;
	}

	public void setMc (String mc) {
		this.mc = mc==null?null:mc.trim();
	}
	public String getXh () {
		return this.xh;
	}

	public void setXh (String xh) {
		this.xh = xh==null?null:xh.trim();
	}
	public String getGg () {
		return this.gg;
	}

	public void setGg (String gg) {
		this.gg = gg==null?null:gg.trim();
	}
	public String getBmid () {
		return this.bmid;
	}

	public void setBmid (String bmid) {
		this.bmid = bmid==null?null:bmid.trim();
	}
	public String getJldw () {
		return this.jldw;
	}

	public void setJldw (String jldw) {
		this.jldw = jldw==null?null:jldw.trim();
	}
	public String getSbid () {
		return this.sbid;
	}

	public void setSbid (String sbid) {
		this.sbid = sbid==null?null:sbid.trim();
	}
	public String getXlh () {
		return this.xlh;
	}

	public void setXlh (String xlh) {
		this.xlh = xlh==null?null:xlh.trim();
	}
	public Double getZl () {
		return this.zl;
	}

	public void setZl (Double zl) {
		this.zl = zl;
	}
	public Double getZjsl () {
		return this.zjsl;
	}

	public void setZjsl (Double zjsl) {
		this.zjsl = zjsl;
	}
	public Double getZjsj () {
		return this.zjsj;
	}

	public void setZjsj (Double zjsj) {
		this.zjsj = zjsj;
	}
	public String getSccj () {
		return this.sccj;
	}

	public void setSccj (String sccj) {
		this.sccj = sccj==null?null:sccj.trim();
	}
	public Date getScsj () {
		return this.scsj;
	}

	public void setScsj (Date scsj) {
		this.scsj = scsj;
	}
	public String getCfwz () {
		return this.cfwz;
	}

	public void setCfwz (String cfwz) {
		this.cfwz = cfwz==null?null:cfwz.trim();
	}
	public Date getYxsj () {
		return this.yxsj;
	}

	public void setYxsj (Date yxsj) {
		this.yxsj = yxsj;
	}
	public String getYxdw () {
		return this.yxdw;
	}

	public void setYxdw (String yxdw) {
		this.yxdw = yxdw==null?null:yxdw.trim();
	}
	public String getBz () {
		return this.bz;
	}

	public void setBz (String bz) {
		this.bz = bz==null?null:bz.trim();
	}
	public String getId () {
		return this.id;
	}

	public void setId (String id) {
		this.id = id==null?null:id.trim();
	}
	public String getWhrid () {
		return this.whrid;
	}

	public void setWhrid (String whrid) {
		this.whrid = whrid==null?null:whrid.trim();
	}
	public String getCjr () {
		return this.cjr;
	}

	public void setCjr (String cjr) {
		this.cjr = cjr==null?null:cjr.trim();
	}
	public String getCjrid () {
		return this.cjrid;
	}

	public void setCjrid (String cjrid) {
		this.cjrid = cjrid==null?null:cjrid.trim();
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
		this.deleted = deleted==null?null:deleted.trim();
	}
	public Integer getVersion () {
		return this.version;
	}

	public void setVersion (Integer version) {
		this.version = version;
	}
	
	public String asSqlString(){
		StringBuilder sb = new StringBuilder();
		sb.append("zbxx_whr").append(" as ").append("whr").append(",");
		sb.append("zbxx_whsj").append(" as ").append("whsj").append(",");
		sb.append("zbxx_zbid").append(" as ").append("zbid").append(",");
		sb.append("zbxx_mc").append(" as ").append("mc").append(",");
		sb.append("zbxx_xh").append(" as ").append("xh").append(",");
		sb.append("zbxx_gg").append(" as ").append("gg").append(",");
		sb.append("zbxx_bmid").append(" as ").append("bmid").append(",");
		sb.append("zbxx_jldw").append(" as ").append("jldw").append(",");
		sb.append("zbxx_sbid").append(" as ").append("sbid").append(",");
		sb.append("zbxx_xlh").append(" as ").append("xlh").append(",");
		sb.append("zbxx_zl").append(" as ").append("zl").append(",");
		sb.append("zbxx_zjsl").append(" as ").append("zjsl").append(",");
		sb.append("zbxx_zjsj").append(" as ").append("zjsj").append(",");
		sb.append("zbxx_sccj").append(" as ").append("sccj").append(",");
		sb.append("zbxx_scsj").append(" as ").append("scsj").append(",");
		sb.append("zbxx_cfwz").append(" as ").append("cfwz").append(",");
		sb.append("zbxx_yxsj").append(" as ").append("yxsj").append(",");
		sb.append("zbxx_yxdw").append(" as ").append("yxdw").append(",");
		sb.append("zbxx_bz").append(" as ").append("bz").append(",");
		sb.append("zbxx_id").append(" as ").append("id").append(",");
		sb.append("zbxx_whrid").append(" as ").append("whrid").append(",");
		sb.append("zbxx_cjr").append(" as ").append("cjr").append(",");
		sb.append("zbxx_cjrid").append(" as ").append("cjrid").append(",");
		sb.append("zbxx_cjsj").append(" as ").append("cjsj").append(",");
		sb.append("zbxx_deleted").append(" as ").append("deleted").append(",");
		sb.append("zbxx_version").append(" as ").append("version").append(",");
		return sb.toString();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("whr").append("=").append(whr).append(",");
		sb.append("whsj").append("=").append(whsj).append(",");
		sb.append("zbid").append("=").append(zbid).append(",");
		sb.append("mc").append("=").append(mc).append(",");
		sb.append("xh").append("=").append(xh).append(",");
		sb.append("gg").append("=").append(gg).append(",");
		sb.append("bmid").append("=").append(bmid).append(",");
		sb.append("jldw").append("=").append(jldw).append(",");
		sb.append("sbid").append("=").append(sbid).append(",");
		sb.append("xlh").append("=").append(xlh).append(",");
		sb.append("zl").append("=").append(zl).append(",");
		sb.append("zjsl").append("=").append(zjsl).append(",");
		sb.append("zjsj").append("=").append(zjsj).append(",");
		sb.append("sccj").append("=").append(sccj).append(",");
		sb.append("scsj").append("=").append(scsj).append(",");
		sb.append("cfwz").append("=").append(cfwz).append(",");
		sb.append("yxsj").append("=").append(yxsj).append(",");
		sb.append("yxdw").append("=").append(yxdw).append(",");
		sb.append("bz").append("=").append(bz).append(",");
		sb.append("id").append("=").append(id).append(",");
		sb.append("whrid").append("=").append(whrid).append(",");
		sb.append("cjr").append("=").append(cjr).append(",");
		sb.append("cjrid").append("=").append(cjrid).append(",");
		sb.append("cjsj").append("=").append(cjsj).append(",");
		sb.append("deleted").append("=").append(deleted).append(",");
		sb.append("version").append("=").append(version).append(",");
		return sb.toString();
	}
}
