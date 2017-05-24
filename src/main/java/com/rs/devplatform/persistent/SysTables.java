package com.rs.devplatform.persistent;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.rs.framework.common.entity.base.CUBaseTO;

/**
 *
 * 数据库表维护-表
 *
 */
@TableName("sys_tables")
public class SysTables extends CUBaseTO {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@TableId(value = "TABLES_ID", type = IdType.UUID)
	private String id;

	/** 表名 */
	@TableField(value = "TABLES_NAME")
	private String name;

	/** 表注解 */
	@TableField(value = "TABLES_COMMENT")
	private String comment;

	/** 是否同步，Y:已同步，N:未同步 */
	@TableField(value = "TABLES_SYNC")
	private String sync;

	/** 注解类型：UUID... */
	@TableField(value = "TABLES_PKTYPE")
	private String pktype;

	/** 主键seq（适用于oracle等） */
	@TableField(value = "TABLES_PKSEQ")
	private String pkseq;

	/** 维护人名 */
	@TableField(value = "TABLES_WHR")
	private String whr;

	/** 维护时间 */
	@TableField(value = "TABLES_WHSJ")
	private Date whsj;

	/** 维护人ID */
	@TableField(value = "TABLES_WHRID")
	private String whrid;

	/** 创建人名 */
	@TableField(value = "TABLES_CJR")
	private String cjr;

	/** 创建时间 */
	@TableField(value = "TABLES_CJSJ")
	private Date cjsj;

	/** 创建人ID */
	@TableField(value = "TABLES_CJRID")
	private String cjrid;

	/** 版本 */
	@TableField(value = "TABLES_VERSION")
	private Integer version;

	/** 逻辑删除标识符 */
	@TableField(value = "TABLES_DELETED")
	private String deleted;

	@TableField(exist = false)
	private transient List<SysColumns> columnList;
	@TableField(exist = false)
	private transient List<SysIndexs> indexList;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSync() {
		return this.sync;
	}

	public void setSync(String sync) {
		this.sync = sync;
	}

	public String getPktype() {
		return this.pktype;
	}

	public void setPktype(String pktype) {
		this.pktype = pktype;
	}

	public String getPkseq() {
		return this.pkseq;
	}

	public void setPkseq(String pkseq) {
		this.pkseq = pkseq;
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

	public List<SysColumns> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<SysColumns> columnList) {
		this.columnList = columnList;
	}

	public List<SysIndexs> getIndexList() {
		return indexList;
	}

	public void setIndexList(List<SysIndexs> indexList) {
		this.indexList = indexList;
	}

	@Override
	public String toString() {
		return "SysTables [id=" + id + ", name=" + name + ", comment=" + comment + ", sync=" + sync + ", pktype="
				+ pktype + ", pkseq=" + pkseq + ", whr=" + whr + ", whsj=" + whsj + ", whrid=" + whrid + ", cjr=" + cjr
				+ ", cjsj=" + cjsj + ", cjrid=" + cjrid + ", version=" + version + ", deleted=" + deleted 
				+ ", columnList="+(columnList==null?0:columnList.size())
				+ ", indexList="+(indexList==null?0:indexList.size())
				+"]";
	}

}
