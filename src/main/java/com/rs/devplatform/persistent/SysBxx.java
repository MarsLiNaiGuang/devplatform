package com.rs.devplatform.persistent;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@TableName("sys_bxx")
public class SysBxx extends CUBaseTO implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 表明细主键 */
	@TableId(value = "BXX_ID", type = IdType.UUID)
	private String id;

	/** 表明细内容,表描述 */
	@TableField(value = "BXX_NR")
	@NotEmpty
	private String nr;
	
	/** 功能编码 */
	@TableField(value = "BXX_FCFLAG")
	@NotEmpty
	private String fcflag;

	/** 是否是多选框 */
	@TableField(value = "BXX_ISCBOX")
	@NotEmpty
	private String iscbox;

	/** 是否数据库同步 */
	@TableField(value = "BXX_ISDBSYN")
	private String isdbsyn;

	/** 是否分页 */
	@TableField(value = "BXX_ISPAGE")
	@NotEmpty
	private String ispage;

	/** 是否显示为树结构 */
	@TableField(value = "BXX_ISTREE")
	@NotEmpty
	private String istree;

	/** 表单主键序列 */
	@TableField(value = "BXX_PKSEQ")
	private String pkseq;

	/** 主键类型 */
	@TableField(value = "BXX_PKLX")
	@NotEmpty
	private String pklx;

	/** 表类型：{1：单表，2：主表，3：子表 }*/
	@TableField(value = "BXX_LX")
	@NotEmpty
	private Integer lx;

	/** 表单版本 */
	@TableField(value = "BXX_VERSION")
	private Integer version;

	/** 查询模式 */
	@TableField(value = "BXX_CXMS")
	private String cxms;

	/** 关系类型 */
	@TableField(value = "BXX_GXLX")
	private Integer gxlx;

	/** 子表字符串，与子表关联关系 */
	@TableField(value = "BXX_ZB")
	private String zb;

	/** tab序号 */
	@TableField(value = "BXX_TABXH")
	private Integer tabxh;

	/** 表名 */
	@TableField(value = "BXX_BM")
	@NotEmpty
	private String bm;
	/** 自定义功能的SQL*/
	@TableField(value = "BXX_ISQL")
	private String isql;

	/** 树型结构中的父节点字段名字 */
	@TableField(value = "BXX_SFIDZDM")
	private String sfidzdm;

	/** 树结构id字段名字 */
	@TableField(value = "BXX_SIDZDM")
	private String sidzdm;

	/** 树字段名 */
	@TableField(value = "BXX_SZDM")
	private String szdm;

	/** 表单类别 */
	@TableField(value = "BXX_LB")
	private String lb;

	/** 表单模板 */
	@TableField(value = "BXX_MB")
	private String mb;

	/** 表单模板样式(移动端) */
	@TableField(value = "BXX_MBM")
	private String mbm;
	
	@TableField(value = "BXX_CHILD")
	private String child;
	
	@TableField(value = "BXX_PARENT")
	private String parent;

	/** 维护人名字 */
	@TableField(value = "BXX_WHR")
	private String whr;

	/** 维护时间 */
	@TableField(value = "BXX_WHSJ")
	private Date whsj;

	/** 维护人ID */
	@TableField(value = "BXX_WHRID")
	private String whrid;

	/** 创建人名字 */
	@TableField(value = "BXX_CJR")
	private String cjr;

	/** 创建时间 */
	@TableField(value = "BXX_CJSJ")
	private Date cjsj;

	/** 创建人ID */
	@TableField(value = "BXX_CJRID")
	private String cjrid;

	@TableField(value = "BXX_DELETED")
	private String deleted;
	
	@TableField(exist = false)
	private List<SysZdxx> ziduanList;
	@TableField(exist = false)
	private List<SysIndexs> indexList;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFcflag() {
		return fcflag;
	}

	public void setFcflag(String fcflag) {
		this.fcflag = fcflag;
	}

	public String getNr() {
		return this.nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public String getIscbox() {
		return this.iscbox;
	}

	public void setIscbox(String iscbox) {
		this.iscbox = iscbox;
	}

	public String getIsdbsyn() {
		return this.isdbsyn;
	}

	public void setIsdbsyn(String isdbsyn) {
		this.isdbsyn = isdbsyn;
	}

	public String getIspage() {
		return this.ispage;
	}

	public void setIspage(String ispage) {
		this.ispage = ispage;
	}

	public String getIstree() {
		return this.istree;
	}

	public void setIstree(String istree) {
		this.istree = istree;
	}

	public String getPkseq() {
		return this.pkseq;
	}

	public void setPkseq(String pkseq) {
		this.pkseq = pkseq;
	}

	public String getPklx() {
		return this.pklx;
	}

	public void setPklx(String pklx) {
		this.pklx = pklx;
	}

	public Integer getLx() {
		return this.lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCxms() {
		return this.cxms;
	}

	public void setCxms(String cxms) {
		this.cxms = cxms;
	}

	public Integer getGxlx() {
		return this.gxlx;
	}

	public void setGxlx(Integer gxlx) {
		this.gxlx = gxlx;
	}

	public String getZb() {
		return this.zb;
	}

	public void setZb(String zb) {
		this.zb = zb;
	}

	public Integer getTabxh() {
		return this.tabxh;
	}

	public void setTabxh(Integer tabxh) {
		this.tabxh = tabxh;
	}

	public String getBm() {
		return this.bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getSfidzdm() {
		return this.sfidzdm;
	}

	public void setSfidzdm(String sfidzdm) {
		this.sfidzdm = sfidzdm;
	}

	public String getSidzdm() {
		return this.sidzdm;
	}

	public void setSidzdm(String sidzdm) {
		this.sidzdm = sidzdm;
	}

	public String getSzdm() {
		return this.szdm;
	}

	public void setSzdm(String szdm) {
		this.szdm = szdm;
	}

	public String getLb() {
		return this.lb;
	}

	public void setLb(String lb) {
		this.lb = lb;
	}

	public String getMb() {
		return this.mb;
	}

	public void setMb(String mb) {
		this.mb = mb;
	}

	public String getMbm() {
		return this.mbm;
	}

	public void setMbm(String mbm) {
		this.mbm = mbm;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
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

	public String getDeleted() {
		return deleted;
	}

	public String getIsql() {
		return isql;
	}

	public void setIsql(String isql) {
		this.isql = isql;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public List<SysZdxx> getZiduanList() {
		return ziduanList;
	}

	public void setZiduanList(List<SysZdxx> ziduanList) {
		this.ziduanList = ziduanList;
	}

	public List<SysIndexs> getIndexList() {
		return indexList;
	}

	public void setIndexList(List<SysIndexs> indexList) {
		this.indexList = indexList;
	}

	@Override
	public String toString() {
		return "SysBxx [id=" + id + ", nr=" + nr + ", iscbox=" + iscbox + ", isdbsyn=" + isdbsyn + ", ispage=" + ispage
				+ ", istree=" + istree + ", pkseq=" + pkseq + ", pklx=" + pklx + ", lx=" + lx + ", version=" + version
				+ ", cxms=" + cxms + ", gxlx=" + gxlx + ", zb=" + zb + ", tabxh=" + tabxh + ", bm=" + bm + ", isql="
				+ isql + ", sfidzdm=" + sfidzdm + ", sidzdm=" + sidzdm + ", szdm=" + szdm + ", lb=" + lb + ", mb=" + mb
				+ ", mbm=" + mbm + ", child="  + child + ", parent=" + parent + ", whr=" + whr + ", whsj=" + whsj + ", whrid=" + whrid 
				+ ", cjr=" + cjr + ", cjsj=" + cjsj + ", cjrid=" + cjrid + ", deleted=" + deleted + "]";
	}

	/**
	 * 
	 * @param bxx
	 * @return
	 */
	public boolean compareChange(SysBxx bxx){
		return  
				(this.nr==null?(bxx.getNr()==null):this.nr.equals(bxx.getNr()))
				&& (this.lx==null?(bxx.getLx()==null):this.lx.equals(bxx.getLx()))
				&& (this.pklx==null?(bxx.getPklx()==null):this.pklx.equals(bxx.getPklx()))
				&& (this.iscbox==null?(bxx.getIscbox()==null):this.iscbox.equals(bxx.getIscbox()))
				&& (this.ispage==null?(bxx.getIspage()==null):this.ispage.equals(bxx.getIspage()))
				&& (this.istree==null?(bxx.getIstree()==null):this.istree.equals(bxx.getIstree()))
				&& (this.isql==null?(bxx.getIsql()==null):this.isql.equals(bxx.getIsql()))
				&& (this.zb==null?(bxx.getZb()==null):this.zb.equals(bxx.getZb()))
				;
	}
}
