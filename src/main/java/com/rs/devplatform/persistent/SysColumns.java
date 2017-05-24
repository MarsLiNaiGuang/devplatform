package com.rs.devplatform.persistent;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.rs.framework.common.entity.base.CUBaseTO;

/**
 *
 * 数据库表维护-字段
 *
 */
@TableName("sys_columns")
public class SysColumns extends CUBaseTO {
	
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	
	@TableField(exist = false)
	public static final String DB_CHANGE_TYPE = "type";
	@TableField(exist = false)
	public static final String DB_CHANGE_NAME = "name";
	@TableField(exist = false)
	public static final String DB_CHANGE_COMMENT = "comment";
	@TableField(exist = false)
	public static final String DB_CHANGE_NULLABLE = "nullable";
	@TableField(exist = false)
	private String[] columnChangeTypes;

	/** 字段记录主键 */
	@TableId(value = "COLUMNS_ID", type = IdType.UUID)
	private String id;

	/** 对应表的ID */
	@TableField(value = "COLUMNS_TABLEID")
	private String tableid;

	/** 字段名 */
	@TableField(value = "COLUMNS_NAME")
	private String name;

	/** 字段备注 */
	@TableField(value = "COLUMNS_COMMENT")
	private String comment;

	/** 是否主键，Y:主键 */
	@TableField(value = "COLUMNS_ISPK")
	private String ispk;

	/** 字段类型 */
	@TableField(value = "COLUMNS_TYPE")
	private String type;

	/** 字段长度 */
	@TableField(value = "COLUMNS_LENGTH")
	private Integer length;

	/** 小数点后的位数 */
	@TableField(value = "COLUMNS_DECPOINT")
	private Integer decpoint;

	/** 是否允许为空 */
	@TableField(value = "COLUMNS_ISNULL")
	private String isnull;

	/** 字段默认值 */
	@TableField(value = "COLUMNS_DEFVAL")
	private String defval;

	/** 字段序号 */
	@TableField(value = "COLUMNS_SEQ")
	private Integer seq;

	/** 字段更改类型，A:新增字段，U:字段更新，D:删除字段 */
	@TableField(value = "COLUMNS_CHGTYPE")
	private String chgtype;

	/** 
	 * 修改字段名，老的字段名<BR>
	 * 改动只保留一条记录。
	 * 1）新增：有一条chgtype=A oldname=null的记录
	 * 2）修改：有一条chgtype=U oldname=原始的字段名
	 * 3）删除：有一条chgtype=D oldname=null/原始字段名（中间经过修改）
	 *  */
	@TableField(value = "COLUMNS_OLDNAME")
	private String oldname;

	/** 维护人名称 */
	@TableField(value = "COLUMNS_WHR")
	private String whr;

	/** 维护时间 */
	@TableField(value = "COLUMNS_WHSJ")
	private Date whsj;

	/** 维护人ID */
	@TableField(value = "COLUMNS_WHRID")
	private String whrid;

	/** 创建人名称 */
	@TableField(value = "COLUMNS_CJR")
	private String cjr;

	/** 创建时间 */
	@TableField(value = "COLUMNS_CJSJ")
	private Date cjsj;

	/** 创建人ID */
	@TableField(value = "COLUMNS_CJRID")
	private String cjrid;

	/** 版本号 */
	@TableField(value = "COLUMNS_VERSION")
	private Integer version;

	/** 逻辑删除标识符 */
	@TableField(value = "COLUMNS_DELETED")
	private String deleted;


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableid() {
		return this.tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getIspk() {
		return this.ispk;
	}

	public void setIspk(String ispk) {
		this.ispk = ispk;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getDecpoint() {
		return this.decpoint;
	}

	public void setDecpoint(Integer decpoint) {
		this.decpoint = decpoint;
	}

	public String getIsnull() {
		return this.isnull;
	}

	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}

	public String getDefval() {
		return this.defval;
	}

	public void setDefval(String defval) {
		this.defval = defval;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getChgtype() {
		return this.chgtype;
	}

	public void setChgtype(String chgtype) {
		this.chgtype = chgtype;
	}

	public String getOldname() {
		return this.oldname;
	}

	public void setOldname(String oldname) {
		this.oldname = oldname;
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

	@Override
	public String toString() {
		return "SysColumns [id=" + id + ", tableid=" + tableid + ", name=" + name + ", comment=" + comment + ", ispk="
				+ ispk + ", type=" + type + ", length=" + length + ", decpoint=" + decpoint + ", isnull=" + isnull
				+ ", defval=" + defval + ", seq=" + seq + ", chgtype=" + chgtype + ", oldname=" + oldname + "]";
	}

	public String[] getColumnChangeTypes() {
		return columnChangeTypes;
	}

	public void setColumnChangeTypes(String[] columnChangeTypes) {
		this.columnChangeTypes = columnChangeTypes;
	}
	
}
