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
@TableName("jcsj_bmpz")
public class Bmpz extends CUBaseTO implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	

     
	 /** 维护人 */
	 @TableField(value = "bmpz_whr")
	private String whr;

     
	 /** 维护时间 */
	 @TableField(value = "bmpz_whsj")
	private Date whsj;

     
	 /** 上级部门 */
	 @TableField(value = "bmpz_sjbm")
	private String sjbm;

     
	 /** 部门编码 */
	 @TableField(value = "bmpz_bmid")
	private String bmid;

     
	 /** 部门名称 */
	 @TableField(value = "bmpz_mc")
	private String mc;

     
	 /** 部门职责 */
	 @TableField(value = "bmpz_bmzz")
	private String bmzz;

     
	 /** 编制人数 */
	 @TableField(value = "bmpz_bzrs")
	private Integer bzrs;

     
	 /** 备注 */
	 @TableField(value = "bmpz_bz")
	private String bz;

     
	/** PK */
	@TableId(value = "bmpz_id", type = IdType.UUID)
	private String id;

     
	 /** whrid */
	 @TableField(value = "bmpz_whrid")
	private String whrid;

     
	 /** cjr */
	 @TableField(value = "bmpz_cjr")
	private String cjr;

     
	 /** cjrid */
	 @TableField(value = "bmpz_cjrid")
	private String cjrid;

     
	 /** cjsj */
	 @TableField(value = "bmpz_cjsj")
	private Date cjsj;

     
	 /** deleted */
	 @TableField(value = "bmpz_deleted")
	private String deleted;

     
	 /** version */
	 @TableField(value = "bmpz_version")
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
	public String getSjbm () {
		return this.sjbm;
	}

	public void setSjbm (String sjbm) {
		this.sjbm = sjbm==null?null:sjbm.trim();
	}
	public String getBmid () {
		return this.bmid;
	}

	public void setBmid (String bmid) {
		this.bmid = bmid==null?null:bmid.trim();
	}
	public String getMc () {
		return this.mc;
	}

	public void setMc (String mc) {
		this.mc = mc==null?null:mc.trim();
	}
	public String getBmzz () {
		return this.bmzz;
	}

	public void setBmzz (String bmzz) {
		this.bmzz = bmzz==null?null:bmzz.trim();
	}
	public Integer getBzrs () {
		return this.bzrs;
	}

	public void setBzrs (Integer bzrs) {
		this.bzrs = bzrs;
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
		sb.append("bmpz_whr").append(" as ").append("whr").append(",");
		sb.append("bmpz_whsj").append(" as ").append("whsj").append(",");
		sb.append("bmpz_sjbm").append(" as ").append("sjbm").append(",");
		sb.append("bmpz_bmid").append(" as ").append("bmid").append(",");
		sb.append("bmpz_mc").append(" as ").append("mc").append(",");
		sb.append("bmpz_bmzz").append(" as ").append("bmzz").append(",");
		sb.append("bmpz_bzrs").append(" as ").append("bzrs").append(",");
		sb.append("bmpz_bz").append(" as ").append("bz").append(",");
		sb.append("bmpz_id").append(" as ").append("id").append(",");
		sb.append("bmpz_whrid").append(" as ").append("whrid").append(",");
		sb.append("bmpz_cjr").append(" as ").append("cjr").append(",");
		sb.append("bmpz_cjrid").append(" as ").append("cjrid").append(",");
		sb.append("bmpz_cjsj").append(" as ").append("cjsj").append(",");
		sb.append("bmpz_deleted").append(" as ").append("deleted").append(",");
		sb.append("bmpz_version").append(" as ").append("version").append(",");
		return sb.toString();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("whr").append("=").append(whr).append(",");
		sb.append("whsj").append("=").append(whsj).append(",");
		sb.append("sjbm").append("=").append(sjbm).append(",");
		sb.append("bmid").append("=").append(bmid).append(",");
		sb.append("mc").append("=").append(mc).append(",");
		sb.append("bmzz").append("=").append(bmzz).append(",");
		sb.append("bzrs").append("=").append(bzrs).append(",");
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
