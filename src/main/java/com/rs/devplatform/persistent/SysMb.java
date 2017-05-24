package com.rs.devplatform.persistent;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 
 *
 */
@TableName("sys_mb")
public class SysMb implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(value = "MB_ID", type = IdType.UUID)
	private String id;

	/** 所属部门 */
	@TableField(value = "MB_XTZZDM")
	private String xtzzdm;

	/** 所属公司 */
	@TableField(value = "MB_XTGSDM")
	private String xtgsdm;

	/** 模板名称 */
	@TableField(value = "MB_NAME")
	private String name;

	/** 模板编码 */
	@TableField(value = "MB_CODE")
	private String code;

	/** 模板类型 */
	@TableField(value = "MB_LX")
	private String lx;

	/** 是否共享 */
	@TableField(value = "MB_FX")
	private String fx;

	/** 预览图 */
	@TableField(value = "MB_PIC")
	private String pic;

	/** 模板描述 */
	@TableField(value = "MB_NR")
	private String nr;

	/** 列表模板名称
 */
	@TableField(value = "MB_LBMZ")
	private String lbmz;

	/** 录入模板名称 */
	@TableField(value = "MB_TJMZ")
	private String tjmz;

	/** 编辑模板名
称 */
	@TableField(value = "MB_GXMZ")
	private String gxmz;

	/** 查看页面模
板名称 */
	@TableField(value = "MB_MXMZ")
	private String mxmz;

	/** 维护人名字 */
	@TableField(value = "MB_WHR")
	private String whr;

	/** 维护时间 */
	@TableField(value = "MB_WHSJ")
	private Date whsj;

	/** 维护人ID */
	@TableField(value = "MB_WHRID")
	private String whrid;

	/** 创建人名字 */
	@TableField(value = "MB_CJR")
	private String cjr;

	/** 创建时间 */
	@TableField(value = "MB_CJSJ")
	private Date cjsj;

	/** 创建人ID */
	@TableField(value = "MB_CJRID")
	private String cjrid;

	/** 数据记录版本 */
	@TableField(value = "MB_VERSION")
	private Integer version;

	/** 逻辑删除标记位 */
	@TableField(value = "MB_DELETED")
	private String deleted;


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXtzzdm() {
		return this.xtzzdm;
	}

	public void setXtzzdm(String xtzzdm) {
		this.xtzzdm = xtzzdm;
	}

	public String getXtgsdm() {
		return this.xtgsdm;
	}

	public void setXtgsdm(String xtgsdm) {
		this.xtgsdm = xtgsdm;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLx() {
		return this.lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getFx() {
		return this.fx;
	}

	public void setFx(String fx) {
		this.fx = fx;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getNr() {
		return this.nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public String getLbmz() {
		return this.lbmz;
	}

	public void setLbmz(String lbmz) {
		this.lbmz = lbmz;
	}

	public String getTjmz() {
		return this.tjmz;
	}

	public void setTjmz(String tjmz) {
		this.tjmz = tjmz;
	}

	public String getGxmz() {
		return this.gxmz;
	}

	public void setGxmz(String gxmz) {
		this.gxmz = gxmz;
	}

	public String getMxmz() {
		return this.mxmz;
	}

	public void setMxmz(String mxmz) {
		this.mxmz = mxmz;
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
