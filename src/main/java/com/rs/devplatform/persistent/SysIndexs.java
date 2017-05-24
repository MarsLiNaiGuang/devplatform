package com.rs.devplatform.persistent;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.rs.framework.common.entity.base.CUBaseTO;

/**
 *
 * sys_bxx里的表单所含有的index
 *
 */
@TableName("sys_indexs")
public class SysIndexs extends CUBaseTO {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**  */
	@TableId(value = "INDEXS_ID", type = IdType.UUID)
	private String id;

	/** 表单ID */
	@TableField(value = "INDEXS_TABLEID")
	private String tableid;

	/** normal,unique */
	@TableField(value = "INDEXS_TYPE")
	private String type;

	/** 字段名 */
	@TableField(value = "INDEXS_FIELD")
	private String field;

	/** index的名称 */
	@TableField(value = "INDEXS_NAME")
	private String name;
	
	/** index的seq */
	@TableField(value = "INDEXS_SEQ")
	private Integer seq;

	/**  */
	@TableField(value = "INDEXS_WHR")
	private String whr;

	/**  */
	@TableField(value = "INDEXS_WHSJ")
	private Date whsj;

	/**  */
	@TableField(value = "INDEXS_WHRID")
	private String whrid;

	/**  */
	@TableField(value = "INDEXS_CJR")
	private String cjr;

	/**  */
	@TableField(value = "INDEXS_CJSJ")
	private Date cjsj;

	/**  */
	@TableField(value = "INDEXS_CJRID")
	private String cjrid;

	/**  */
	@TableField(value = "INDEXS_VERSION")
	private Integer version;

	/**  */
	@TableField(value = "INDEXS_DELETED")
	private String deleted;


	@TableField(exist = false)
	private boolean dbChange;
	
	public boolean isDbChange() {
		return dbChange;
	}

	public void setDbChange(boolean dbChange) {
		this.dbChange = dbChange;
	}

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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public boolean compare(SysIndexs ind){
		return (this.type==null?(ind.getType()==null):(this.type.equals(ind.getType())))
				&&(this.field==null?(ind.getField()==null):(this.field.equals(ind.getField())))
				&&(this.name==null?(ind.getName()==null):(this.name.equals(ind.getName())))
				&&(this.seq==null?(ind.getSeq()==null):(this.seq.equals(ind.getSeq())))
				;
	}
	
	public boolean compareDBChange(SysIndexs ind){
		return (this.type==null?(ind.getType()==null):(this.type.equals(ind.getType())))
				&&(this.field==null?(ind.getField()==null):(this.field.equals(ind.getField())))
				&&(this.name==null?(ind.getName()==null):(this.name.equals(ind.getName())))
				;
	}

	@Override
	public String toString() {
		return "SysIndexs [id=" + id + ", tableid=" + tableid + ", type=" + type + ", field=" + field + ", name=" + name
				+ ", seq=" + seq + ", whr=" + whr + ", whsj=" + whsj + ", whrid=" + whrid + ", cjr=" + cjr + ", cjsj="
				+ cjsj + ", cjrid=" + cjrid + ", version=" + version + ", deleted=" + deleted + "]";
	}

}
