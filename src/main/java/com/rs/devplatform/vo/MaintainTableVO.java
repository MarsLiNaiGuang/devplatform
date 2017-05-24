package com.rs.devplatform.vo;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.rs.devplatform.persistent.SysTables;

/**
 * 数据库表维护
 * 
 * @author yuxiaobin
 *
 */
public class MaintainTableVO {
	
	private static final Logger logger = LoggerFactory.getLogger("MaintainTableVO");
	
	public static final String CHG_PROPERTY_NAME = TableUpdateConfirmVO.CHG_PROPERTY_NAME;
	public static final String CHG_PROPERTY_DESCP = TableUpdateConfirmVO.CHG_PROPERTY_DESCP;
	public static final String CHG_BEFORE = TableUpdateConfirmVO.CHG_BEFORE;
	public static final String CHG_AFTER = TableUpdateConfirmVO.CHG_AFTER;
	public static final String CHG_FLAG = TableUpdateConfirmVO.CHG_FLAG;
	
	public static final String CHG_FLAG_ADD = TableUpdateConfirmVO.CHG_FLAG_ADD;
	public static final String CHG_FLAG_UPDATE = TableUpdateConfirmVO.CHG_FLAG_UPDATE;
	public static final String CHG_FLAG_DELETE = TableUpdateConfirmVO.CHG_FLAG_DELETE;
	
	public MaintainTableVO() {
		super();
	}
	

	public MaintainTableVO(String tableid, String tablename, Integer version) {
		super();
		this.tableid = tableid;
		this.tablename = tablename;
		this.version = version;
	}

	@JSONField(name = "id")
	private String tableid;
	@NotEmpty(message="表名不能为空")
	private String tablename;
	@NotEmpty(message="表描述不能为空")
	private String tabledescp;
	@NotEmpty(message="主键类型不能为空")
	private String pktype;
	
	private String pkseq;
	private String isdbsyn;
	
	private Integer version;
	
	@NotEmpty(message="字段不能为空")
	private MaintainColumnVO[] columns;

	private MaintainIndexVO[] indexs;

	public String getIsdbsyn() {
		return isdbsyn;
	}
	public void setIsdbsyn(String isdbsyn) {
		this.isdbsyn = isdbsyn;
	}
	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
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

	public String getPkseq() {
		return pkseq;
	}

	public void setPkseq(String pkseq) {
		this.pkseq = pkseq;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public MaintainColumnVO[] getColumns() {
		return columns;
	}

	public void setColumns(MaintainColumnVO[] columns) {
		this.columns = columns;
	}

	public MaintainIndexVO[] getIndexs() {
		return indexs;
	}

	public void setIndexs(MaintainIndexVO[] indexs) {
		this.indexs = indexs;
	}

	@Override
	public String toString() {
		return "MaintainTableVO [tableid=" + tableid + ", tablename=" + tablename + ", tabledescp=" + tabledescp
				+ ", pktype=" + pktype + ", version=" + version + ", columns=" + Arrays.toString(columns) + ", indexs="
				+ Arrays.toString(indexs) + "]";
	}
	
	public JSONObject compareChgJSON(MaintainTableVO afterVO){
		JSONObject json = new JSONObject();
		json.put("tablename", tablename);
		json.put("id", tableid);
		json.put("version", version);
		boolean isAdd = false;
		boolean isDelete = false;
		if(StringUtils.isBlank(tablename) || StringUtils.isBlank(tableid)){
			json.put("tablename", afterVO.getTablename());
			json.put(CHG_FLAG, CHG_FLAG_ADD);
			json.put("id", "");
			isAdd = true;
		}else if(afterVO==null){
			isDelete = true;
			json.put(CHG_FLAG, CHG_FLAG_DELETE);
		}else{
			json.put(CHG_FLAG, CHG_FLAG_UPDATE);
		}
		JSONArray tableChgs = new JSONArray();
		JSONObject chg = null;
		String updateTabledescp = afterVO==null?null:afterVO.getTabledescp();
		if(isAdd || isDelete || (tabledescp!=null? !tabledescp.equals(updateTabledescp) : updateTabledescp!=null)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "tabledescp");
			chg.put(CHG_PROPERTY_DESCP, "表描述");
			chg.put(CHG_BEFORE, isAdd?"":tabledescp);
			chg.put(CHG_AFTER, isDelete?"":updateTabledescp);
			tableChgs.add(chg);
		}
		String updatePktype = afterVO==null?null: afterVO.getPktype();
		if(isAdd || isDelete || (pktype!=null ? pktype.equalsIgnoreCase(updatePktype) : updatePktype!=null)){
			JSONObject pkChg = new JSONObject();
			pkChg.put(CHG_PROPERTY_NAME, "pktype");
			pkChg.put(CHG_PROPERTY_DESCP, "主键策略");
			pkChg.put(CHG_BEFORE, isAdd?"" : pktype);
			pkChg.put(CHG_AFTER, isDelete?"" : updatePktype);
			tableChgs.add(pkChg);
		}
		json.put("tableChgs", tableChgs);
		return json;
	}
	
	public static MaintainTableVO[] parseChgJSON(JSONObject json){
		MaintainTableVO[] chgPair = new MaintainTableVO[2];
		String tablename = json.getString("tablename");
		String tableid = json.getString("id");
		Integer version = json.getInteger("version");
		String chgFlag = json.getString(CHG_FLAG);
		JSONArray chgs = json.getJSONArray("tableChgs");
		if(CHG_FLAG_ADD.equals(chgFlag)){
			chgPair[0] = null;
			MaintainTableVO add = new MaintainTableVO(tableid, tablename, version);
			for(int i=0;i<chgs.size();++i){
				JSONObject jsn = chgs.getJSONObject(i);
				String propName = jsn.getString(CHG_PROPERTY_NAME);
				switch (propName) {
				case "tabledescp":
					add.setTabledescp(jsn.getString(CHG_AFTER));
					break;
				case "pktype":
					add.setPktype(jsn.getString(CHG_AFTER));
					break;
				default:
					logger.warn("parseChgJSON(): invalid CHG_PROPERTY_NAME: name={}", propName);
					break;
				}
			}
			chgPair[1] = add;
			return chgPair;
		}else if(CHG_FLAG_DELETE.equals(chgFlag)){
			chgPair[1] = null;
			MaintainTableVO deleteVO = new MaintainTableVO(tableid, tablename, version);
			for(int i=0;i<chgs.size();++i){
				JSONObject jsn = chgs.getJSONObject(i);
				String propName = jsn.getString(CHG_PROPERTY_NAME);
				switch (propName) {
				case "tabledescp":
					deleteVO.setTabledescp(jsn.getString(CHG_BEFORE));
					break;
				case "pktype":
					deleteVO.setPktype(jsn.getString(CHG_BEFORE));
					break;
				default:
					logger.warn("parseChgJSON(): invalid CHG_PROPERTY_NAME: name={}", propName);
					break;
				}
			}
			chgPair[0] = deleteVO;
			return chgPair;
		}else{
			MaintainTableVO beforeVO = new MaintainTableVO(tableid, tablename, version);
			MaintainTableVO afterVO = new MaintainTableVO(tableid, tablename, version);
			for(int i=0;i<chgs.size();++i){
				JSONObject jsn = chgs.getJSONObject(i);
				String propName = jsn.getString(CHG_PROPERTY_NAME);
				switch (propName) {
				case "tabledescp":
					beforeVO.setTabledescp(jsn.getString(CHG_BEFORE));
					afterVO.setTabledescp(jsn.getString(CHG_AFTER));
					break;
				case "pktype":
					beforeVO.setPktype(jsn.getString(CHG_BEFORE));
					afterVO.setPktype(jsn.getString(CHG_AFTER));
					break;
				default:
					logger.warn("parseChgJSON(): invalid CHG_PROPERTY_NAME: name={}", propName);
					break;
				}
			}
			chgPair[0] = beforeVO;
			chgPair[1] = afterVO;
			return chgPair;
		}
	}
	
	public static MaintainTableVO fromSysTable(SysTables table){
		MaintainTableVO vo = new MaintainTableVO();
		vo.setPktype(table.getPktype());
		vo.setPkseq(table.getPkseq());
		vo.setTabledescp(table.getComment());
		vo.setTableid(table.getId());
		vo.setTablename(table.getName());
		vo.setVersion(table.getVersion());
		vo.setVersion(table.getVersion());
		vo.setIsdbsyn(table.getSync());
		return vo;
	}
	
	public SysTables toSysTable(){
		SysTables table = new SysTables();
		table.setComment(tabledescp);
		table.setId(tableid);
		table.setName(tablename.toLowerCase());
		table.setPktype(pktype);
		table.setPkseq(pkseq);
		table.setVersion(version);
		return table;
	}
	
	public boolean compareAndSetChange(MaintainTableVO updateVO){
		if(updateVO==null || StringUtils.isBlank(updateVO.getTableid())){
			return false;
		}
		boolean isUpdated = false;
		String updateDescp = updateVO.getTabledescp();
		if(updateDescp!=null && !updateDescp.equals(tabledescp)){
			tabledescp = updateDescp;
			isUpdated = true;
		}
		String updatePktype = updateVO.getPktype();
		if(updatePktype!=null && !updatePktype.equals(pktype)){
			pktype = updatePktype;
			isUpdated = true;
		}
		String updatePkseq = updateVO.getPkseq();
		if(updatePkseq==null && pkseq==null){
			return isUpdated;
		}
		if((updatePkseq==null && pkseq!=null) || (updatePkseq!=null && pkseq==null) || !updatePkseq.equals(pkseq)){
			pkseq = updatePkseq;
			isUpdated = true;
		}
		return isUpdated;
	}
	
	private static final String column_prefix_sep = "_";
	
	public JSONObject toJSON(){
		String columnPrefix = "";
		if(tablename!=null){
			int index_ = tablename.lastIndexOf(column_prefix_sep);
			if (index_ != -1) {
				columnPrefix = tablename.substring(index_ + 1) + column_prefix_sep;
			} else {
				columnPrefix = tablename + column_prefix_sep;
			}
		}else{
			return null;
		}
		JSONObject result = (JSONObject) JSONObject.toJSON(this);
		if(columns!=null){
			for(MaintainColumnVO col: columns){
				col.setCol_name(col.getCol_name().replace(columnPrefix, ""));
			}
			result.put("columns", columns);
		}
		if(indexs!=null){
			for(MaintainIndexVO vo:indexs){
				vo.setColName(vo.getColName().replace(columnPrefix, ""));
			}
			result.put("indexs", indexs);
		}
		return result;
	}
	
	public void formatColumnPrefix(){
		String columnPrefix = "";
		if(tablename!=null){
			int index_ = tablename.lastIndexOf(column_prefix_sep);
			if (index_ != -1) {
				columnPrefix = tablename.substring(index_ + 1) + column_prefix_sep;
			} else {
				columnPrefix = tablename + column_prefix_sep;
			}
		}else{
			return;
		}
		if(columns!=null){
			for(MaintainColumnVO col: columns){
				col.setCol_name(columnPrefix+col.getCol_name());
			}
		}
		if(indexs!=null){
			for(MaintainIndexVO vo:indexs){
				vo.setColName(columnPrefix+vo.getColName());
			}
		}
	}
}
