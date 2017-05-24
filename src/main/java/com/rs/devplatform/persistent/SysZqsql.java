package com.rs.devplatform.persistent;

import java.util.Date;

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
@TableName("sys_zqsql")
public class SysZqsql extends CUBaseTO {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 增强SQL主键 */
	@TableId(value = "ZQSQL_ID", type = IdType.UUID)
	private String id;

	/** 增强SQL编码 */
	@TableField(value = "ZQSQL_CODE")
	private String code;

	/** 增强SQL */
	@JSONField(deserialize=false)
	@TableField(value = "ZQSQL_SQL")
	private byte[] zqsql;

	/** 增强SQL名字 */
	@TableField(value = "ZQSQL_SQLNAME")
	private String sqlname;

	/** 内容 */
	@TableField(value = "ZQSQL_NR")
	private String nr;

	/** 表单ID */
	@TableField(value = "ZQSQL_BDID")
	private String bdid;

	/** 维护人名字 */
	@TableField(value = "ZQSQL_WHR")
	private String whr;

	/** 维护时间 */
	@TableField(value = "ZQSQL_WHSJ")
	private Date whsj;

	/** 维护人ID */
	@TableField(value = "ZQSQL_WHRID")
	private String whrid;

	/** 创建人名字 */
	@TableField(value = "ZQSQL_CJR")
	private String cjr;

	/** 创建时间 */
	@TableField(value = "ZQSQL_CJSJ")
	private Date cjsj;

	/** 创建人ID */
	@TableField(value = "ZQSQL_CJRID")
	private String cjrid;

	/** 数据记录版本 */
	@TableField(value = "ZQSQL_VERSION")
	private Integer version;

	/** 逻辑删除标记位 */
	@TableField(value = "ZQSQL_DELETED")
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

	

	public byte[] getZqsql() {
		return zqsql;
	}

	public void setZqsql(byte[] zqsql) {
		this.zqsql = zqsql;
	}

	public String getSqlname() {
		return this.sqlname;
	}

	public void setSqlname(String sqlname) {
		this.sqlname = sqlname;
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
