package com.rs.devplatform.vo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.vo.cg.Col2ZdxxConverter;
import com.rs.devplatform.vo.cg.Table2BxxConverter;
import com.rs.devplatform.vo.onlinefc.SubtablesVO;

public class TableVO {

	@NotEmpty(message="功能标识不能为空")
	private String fcflag;//功能唯一标识#264
	
	@NotEmpty(message="表名不能为空")
	private String tablename;
	@NotEmpty(message="表描述不能为空")
	private String tabledescp;
	@NotEmpty(message="主键类型不能为空")
	private String pktype;
	@NotNull(message="表类型不能为空")
	private Integer tabletype;

	private String multiselect;

	private String ispage;

	private String istree;
	private String isdbsyn;
	
	private String child;
	private String parent;

	private Integer relationType;
	private Integer relationSeq;

	@JSONField(name = "id")
	private String tableId;
	private Integer version;

	@NotEmpty
	@Valid
	private ColumnVO[] columns;

	private ColumnIndexVO[] indexs;
	
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

	public Integer getTabletype() {
		return tabletype;
	}

	public void setTabletype(Integer tabletype) {
		this.tabletype = tabletype;
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

	public ColumnIndexVO[] getIndexs() {
		return indexs;
	}

	public void setIndexs(ColumnIndexVO[] indexs) {
		this.indexs = indexs;
	}

	public ColumnVO[] getColumns() {
		return columns;
	}

	public void setColumns(ColumnVO[] columns) {
		this.columns = columns;
	}

	public String getIsdbsyn() {
		return isdbsyn;
	}

	public void setIsdbsyn(String isdbsyn) {
		this.isdbsyn = isdbsyn;
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

	public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	public Integer getRelationSeq() {
		return relationSeq;
	}

	public void setRelationSeq(Integer relationSeq) {
		this.relationSeq = relationSeq;
	}

	public static TableVO toTableVO(SysBxx bxx) {
		TableVO table = Table2BxxConverter.convert(bxx);
		List<SysZdxx> ziduanList = bxx.getZiduanList();
		if (ziduanList != null) {
			int size = ziduanList.size();
			ColumnVO[] columns = new ColumnVO[size];
			ColumnVO col = null;
			for (int i = 0; i < size; ++i) {
				SysZdxx x = ziduanList.get(i);
				col = Col2ZdxxConverter.convert(x);
				columns[i] = col;
			}
			table.setColumns(columns);
		}
		return table;
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
