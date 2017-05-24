package com.rs.devplatform.vo.onlinefc;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.rs.devplatform.utils.onlinefunc.RsSqlTokenHandler;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;

public class OnlineFuncVO {

	@JSONField(name = "id")
	private String tableId;
	private Integer version;
	
	@NotEmpty(message="sql不能为空")
	private String sql;
	@NotEmpty(message="表名不能为空")
	private String tablename;
	
	@NotEmpty(message="功能标识不能为空")
	private String fcflag;//#264
	
	@NotEmpty(message="表描述不能为空")
	private String tabledescp;
	@NotNull(message="表类型不能为空")
	private Integer tabletype;
	@NotEmpty(message="主键类型不能为空")
	private String pktype;
	
	private String relationType;
	private String multiselect;
	private String ispage;
	private String istree;
	private String child;
	private String parent;
	
	@NotEmpty(message="数据列不能为空")
	@Valid
	private OnlineFcColumn[] columns;
	@Valid
	private OnlineFcCondition[] conditions;
	@Valid
	private SubtablesVO[] subtables;

	public String getFcflag() {
		return fcflag;
	}

	public void setFcflag(String fcflag) {
		this.fcflag = fcflag;
	}

	public SubtablesVO[] getSubtables() {
		return subtables;
	}

	public void setSubtables(SubtablesVO[] subtables) {
		this.subtables = subtables;
	}
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public OnlineFcColumn[] getColumns() {
		return columns;
	}
	public void setColumns(OnlineFcColumn[] columns) {
		this.columns = columns;
	}
	public OnlineFcCondition[] getConditions() {
		return conditions;
	}
	public void setConditions(OnlineFcCondition[] conditions) {
		this.conditions = conditions;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	
	public String getTabledescp() {
		return tabledescp;
	}
	public void setTabledescp(String tabledescp) {
		this.tabledescp = tabledescp;
	}
	public String getPktype() {
		return pktype;
	}
	public void setPktype(String pktype) {
		this.pktype = pktype;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Integer getTabletype() {
		return tabletype;
	}
	public void setTabletype(Integer tabletype) {
		this.tabletype = tabletype;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public String getMultiselect() {
		return multiselect;
	}
	public void setMultiselect(String multiselect) {
		this.multiselect = multiselect;
	}
	public String getIspage() {
		return ispage;
	}
	public void setIspage(String ispage) {
		this.ispage = ispage;
	}
	public String getIstree() {
		return istree;
	}
	public void setIstree(String istree) {
		this.istree = istree;
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

	@JSONField(serialize=false)
	@JsonProperty(access=Access.READ_ONLY)
	public boolean isValidSQL(){
		if(sql==null || sql.trim().isEmpty()){
			return false;
		}
		RsSqlTokenHandler handler = new RsSqlTokenHandler();
		GenericTokenParser parser = new GenericTokenParser(RsSqlTokenHandler.OPEN_TOKEN,
				RsSqlTokenHandler.CLOSE_TOKEN, handler);
		String formatSql = parser.parse(sql);
		try {
			CCJSqlParserUtil.parse(formatSql);
			return true;
		} catch (JSQLParserException e) {
			return false;
		}
	}

	public String getSubtablesJSONString(){
		if(subtables==null || subtables.length==0){
			return null;
		}
		return JSON.toJSONString(subtables);
	}
	
	public boolean isInColumn(String colName){
		for(int i = 0; i < columns.length; i++){
			if(colName.equals(columns[i].getColName())){
				return true;
			}
		}
		return false;
	}
}
