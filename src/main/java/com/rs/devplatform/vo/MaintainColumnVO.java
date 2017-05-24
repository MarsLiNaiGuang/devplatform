package com.rs.devplatform.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.rs.devplatform.persistent.SysColumns;

public class MaintainColumnVO {
	
	private static final Logger logger = LoggerFactory.getLogger("MaintainColumnVO");
	
	public static final String CHG_PROPERTY_NAME = TableUpdateConfirmVO.CHG_PROPERTY_NAME;
	public static final String CHG_PROPERTY_DESCP = TableUpdateConfirmVO.CHG_PROPERTY_DESCP;
	public static final String CHG_BEFORE = TableUpdateConfirmVO.CHG_BEFORE;
	public static final String CHG_AFTER = TableUpdateConfirmVO.CHG_AFTER;
	public static final String CHG_FLAG = TableUpdateConfirmVO.CHG_FLAG;
	
	public static final String CHG_FLAG_ADD = TableUpdateConfirmVO.CHG_FLAG_ADD;
	public static final String CHG_FLAG_UPDATE = TableUpdateConfirmVO.CHG_FLAG_UPDATE;
	public static final String CHG_FLAG_DELETE = TableUpdateConfirmVO.CHG_FLAG_DELETE;
	

	@JSONField(name="colSeq")
	@JsonProperty(value="colSeq")
	Integer col_seq;
	
	@JSONField(name="colName")
	@JsonProperty(value="colName")
	String col_name;
	
	@JSONField(name="colMark")
	@JsonProperty(value="colMark")
	String col_mark;
	
	@JSONField(name="colLen")
	@JsonProperty(value="colLen")
	Integer col_length;
	
	@JSONField(name="colDecpoint")
	@JsonProperty(value="colDecpoint")
	Integer col_decpoint;
	
	@JSONField(name="colDefval")
	@JsonProperty(value="colDefval")
	String col_defval;
	
	@JSONField(name="colType")
	@JsonProperty(value="colType")
	String col_type;
	
	@JSONField(name="colIspk")
	@JsonProperty(value="colIspk")
	String col_ispk;
	
	@JSONField(name="colIsnull")
	@JsonProperty(value="colIsnull")
	String col_isnull;
	
	@JSONField(name="id")
	@JsonProperty(value="id")
	String col_id;
	String deleted;
	Integer version;
	
	@JSONField(serialize=false)
	@JsonProperty(access=Access.WRITE_ONLY)
	private transient String[] columnChangeTypes;//在比较change的时候，记录具体change的类型：重命名？类型？注释？
	
	private transient String colOldname;
	private transient String chgType;
	
	public String getChgType() {
		return chgType;
	}
	public void setChgType(String chgType) {
		this.chgType = chgType;
	}
	public String getColOldname() {
		return colOldname;
	}
	public void setColOldname(String colOldname) {
		this.colOldname = colOldname;
	}
	public Integer getCol_seq() {
		return col_seq;
	}
	public void setCol_seq(Integer col_seq) {
		this.col_seq = col_seq;
	}
	public String getCol_name() {
		return col_name;
	}
	public void setCol_name(String col_name) {
		if(col_name!=null){
			col_name = col_name.toUpperCase();
		}
		this.col_name = col_name;
	}
	public String getCol_mark() {
		return col_mark;
	}
	public void setCol_mark(String col_mark) {
		this.col_mark = col_mark;
	}
	public Integer getCol_length() {
		return col_length;
	}
	public void setCol_length(Integer col_length) {
		this.col_length = col_length;
	}
	public Integer getCol_decpoint() {
		return col_decpoint;
	}
	public void setCol_decpoint(Integer col_decpoint) {
		this.col_decpoint = col_decpoint;
	}
	public String getCol_defval() {
		return col_defval;
	}
	public void setCol_defval(String col_defval) {
		this.col_defval = col_defval;
	}
	public String getCol_type() {
		return col_type;
	}
	public void setCol_type(String col_type) {
		this.col_type = col_type;
	}
	public String getCol_ispk() {
		return col_ispk;
	}
	public void setCol_ispk(String col_ispk) {
		this.col_ispk = col_ispk;
	}
	public String getCol_isnull() {
		return col_isnull;
	}
	public void setCol_isnull(String col_isnull) {
		this.col_isnull = col_isnull;
	}
	public String getCol_id() {
		return col_id;
	}
	public void setCol_id(String col_id) {
		this.col_id = col_id;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public String[] getColumnChangeTypes() {
		return columnChangeTypes;
	}
	public void setColumnChangeTypes(String[] columnChangeTypes) {
		this.columnChangeTypes = columnChangeTypes;
	}
	@Override
	public String toString() {
		return "ColumnMaintainVO [col_seq=" + col_seq + ", col_name=" + col_name + ", col_mark=" + col_mark
				+ ", col_length=" + col_length + ", col_decpoint=" + col_decpoint + ", col_defval=" + col_defval
				+ ", col_type=" + col_type + ", col_ispk=" + col_ispk + ", col_isnull=" + col_isnull + ", col_id="
				+ col_id + ", deleted=" + deleted + ", version=" + version + "]";
	}
	
	public JSONObject compareChgJSON(MaintainColumnVO updateVO){
		JSONObject json = new JSONObject();
		json.put("colName", col_name);
		boolean isAdd = false;
		boolean isDelete = false;
		if(col_name==null){
			json.put("colName", updateVO.getCol_name());
			json.put("chgFlag", CHG_FLAG_ADD);
			json.put("id", "");
			isAdd = true;
		}else if(updateVO==null){
			json.put("chgFlag", CHG_FLAG_DELETE);
			json.put("id", col_id);
			isDelete = true;
		}else{
			json.put("chgFlag", CHG_FLAG_UPDATE);
			json.put("id", col_id);
		}
		JSONArray chgs = new JSONArray();
		
		JSONObject chg = null;
		String updateColname = updateVO==null?null:updateVO.getCol_name();
		if(isAdd || isDelete || !col_name.equalsIgnoreCase(updateColname)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "colName");
			chg.put(CHG_PROPERTY_DESCP, "字段名称");
			chg.put(CHG_BEFORE, isAdd?"": col_name);
			chg.put(CHG_AFTER, isDelete?"": updateColname);
			chgs.add(chg);
		}
		String updateColmark = updateVO==null?null:updateVO.getCol_mark();
		if(isAdd || isDelete || (col_mark!=null ? !col_mark.equalsIgnoreCase(updateColmark) : updateColmark!=null)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "colMark");
			chg.put(CHG_PROPERTY_DESCP, "字段备注");
			chg.put(CHG_BEFORE, isAdd?"":col_mark);
			chg.put(CHG_AFTER, isDelete?"":updateVO.getCol_mark());
			chgs.add(chg);
		}
		Integer updateLen = updateVO==null?null:updateVO.getCol_length();
		if(isAdd || isDelete || (col_length!=null ? !col_length.equals(updateLen) : updateLen!=null)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "colLen");
			chg.put(CHG_PROPERTY_DESCP, "字段长度");
			chg.put(CHG_BEFORE, isAdd?"":col_length);
			chg.put(CHG_AFTER, isDelete?"":updateLen);
			chgs.add(chg);
		}
		Integer updateDecpoint = updateVO==null?null:updateVO.getCol_decpoint();
		if(isAdd || isDelete || (col_decpoint!=null ? !col_decpoint.equals(updateDecpoint) : updateDecpoint!=null)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "colDecpoint");
			chg.put(CHG_PROPERTY_DESCP, "小数点");
			chg.put(CHG_BEFORE, isAdd?"":col_decpoint);
			chg.put(CHG_AFTER, isDelete?"":updateDecpoint);
			chgs.add(chg);
		}
		String udpateDefVal = updateVO==null?null:updateVO.getCol_defval();
		if(isAdd || isDelete || (col_defval!=null? !col_defval.equals(udpateDefVal) : udpateDefVal!=null)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "colDefval");
			chg.put(CHG_PROPERTY_DESCP, "默认值");
			chg.put(CHG_BEFORE, isAdd?"":col_defval);
			chg.put(CHG_AFTER, isDelete?"":udpateDefVal);
			chgs.add(chg);
		}
		String updateColType = updateVO==null?null:updateVO.getCol_type();
		if(isAdd || isDelete || (col_type!=null ? !col_type.equals(updateColType) : updateColType!=null)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "colType");
			chg.put(CHG_PROPERTY_DESCP, "字段类型");
			chg.put(CHG_BEFORE, isAdd?"":col_type);
			chg.put(CHG_AFTER, isDelete?"":updateColType);
			chgs.add(chg);
		}
		String updateIspk = updateVO==null?null:updateVO.getCol_ispk();
		if(isAdd || isDelete || (col_ispk!=null ? !col_ispk.equals(updateIspk) : updateIspk!=null)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "colIspk");
			chg.put(CHG_PROPERTY_DESCP, "主键");
			chg.put(CHG_BEFORE, isAdd?"":col_ispk);
			chg.put(CHG_AFTER, isDelete?"":updateIspk);
			chgs.add(chg);
		}
		String updateIsnull = updateVO==null?null:updateVO.getCol_isnull();
		if(isAdd || isDelete || (col_isnull!=null ? !col_isnull.equals(updateIsnull) : updateIsnull!=null)){
		chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "colIsnull");
			chg.put(CHG_PROPERTY_DESCP, "允许空值");
			chg.put(CHG_BEFORE, isAdd?"":col_isnull);
			chg.put(CHG_AFTER, isDelete?"":updateIsnull);
			chgs.add(chg);
		}
		json.put("chgs", chgs);
		return json;
	}
	
	public boolean compareAndSetChange(MaintainColumnVO updateVO){
		if(updateVO==null || StringUtils.isBlank(updateVO.getCol_id())){
			return false;
		}
		List<String> changeTypeList = new ArrayList<>(4);
		boolean typeChanged = false;
		boolean isUpdated = false;
		String updateColname = updateVO.getCol_name();
		if(updateColname!=null && !updateColname.equalsIgnoreCase(col_name)){
			isUpdated = true;
			colOldname = col_name;
			col_name = updateColname;
			changeTypeList.add(SysColumns.DB_CHANGE_NAME);
		}
		String updateColMark = updateVO.getCol_mark();
		if(updateColMark!=null && !updateColMark.equalsIgnoreCase(col_mark)){
			isUpdated = true;
			col_mark = updateColMark;
			changeTypeList.add(SysColumns.DB_CHANGE_COMMENT);
		}
		Integer updateColLen = updateVO.getCol_length();
		if(updateColLen!=null && !updateColLen.equals(col_length)){
			isUpdated = true;
			col_length = updateColLen;
			if(!typeChanged){
				typeChanged = true;
				changeTypeList.add(SysColumns.DB_CHANGE_TYPE);
			}
		}
		Integer updateDecpoint = updateVO.getCol_decpoint();
		if(updateDecpoint!=null && !updateDecpoint.equals(col_decpoint)){
			isUpdated = true;
			col_decpoint = updateDecpoint;
			if(!typeChanged){
				typeChanged = true;
				changeTypeList.add(SysColumns.DB_CHANGE_TYPE);
			}
		}
		String updateDefval = updateVO.getCol_defval();
		if((updateDefval==null || updateDefval.trim().isEmpty()) && (col_defval==null || col_defval.trim().isEmpty())){
			//consider as no change
		}else{
			if(!updateDefval.equals(col_defval)){
				isUpdated = true;
				col_defval = updateDefval;
			}
		}
		String updateType = updateVO.getCol_type();
		if(updateType!=null && !updateType.equalsIgnoreCase(col_type)){
			isUpdated = true;
			col_type = updateType;
			if(!typeChanged){
				typeChanged = true;
				changeTypeList.add(SysColumns.DB_CHANGE_TYPE);
			}
		}
		String updateIspk = updateVO.getCol_ispk();
		if(updateIspk!=null && !updateIspk.equalsIgnoreCase(col_ispk)){
			isUpdated = true;
			col_ispk = updateIspk;
			//TODO: pk change not support
		}
		String updateIsnull = updateVO.getCol_isnull();
		if(updateIsnull!=null && !updateIsnull.equalsIgnoreCase(col_isnull)){
			isUpdated = true;
			col_isnull = updateIsnull;
			changeTypeList.add(SysColumns.DB_CHANGE_NULLABLE);
		}
		String updateChgType = updateVO.getChgType();
		//TODO: 测试：原来是""或者null，后续更新了，但是前面的那些字段都没变，怎么调整？
		//怎么加入一个flag，让修改可以知道是修改了什么类型
		if(updateChgType!=null && !updateChgType.equalsIgnoreCase(chgType)){
			isUpdated = true;
			chgType = updateChgType;
		}
		if(!changeTypeList.isEmpty()){
			String[] array = new String[changeTypeList.size()];
			changeTypeList.toArray(array);
			this.setColumnChangeTypes(array);
		}
		return isUpdated;
	}
	
	public static MaintainColumnVO[] parseChgJSON(JSONObject json){
		MaintainColumnVO[] chgPair = new MaintainColumnVO[2];
		MaintainColumnVO beforeVO = new MaintainColumnVO();
		MaintainColumnVO afterVO = new MaintainColumnVO();
		String chgFlag = json.getString(CHG_FLAG);
		JSONArray chgs = json.getJSONArray("chgs");
		for(int i=0;i<chgs.size();++i){
			JSONObject jsn = chgs.getJSONObject(i);
			String propName = jsn.getString(CHG_PROPERTY_NAME);
			switch (propName) {
			case "colName":
				beforeVO.setCol_name(jsn.getString(CHG_BEFORE));
				afterVO.setCol_name(jsn.getString(CHG_AFTER));
				break;
			case "colMark":
				beforeVO.setCol_mark(jsn.getString(CHG_BEFORE));
				afterVO.setCol_mark(jsn.getString(CHG_AFTER));
				break;
			case "colLen":
				beforeVO.setCol_length(jsn.getInteger(CHG_BEFORE));
				afterVO.setCol_length(jsn.getInteger(CHG_AFTER));
				break;
			case "colDecpoint":
				beforeVO.setCol_decpoint(jsn.getInteger(CHG_BEFORE));
				afterVO.setCol_decpoint(jsn.getInteger(CHG_AFTER));
				break;
			case "colDefval":
				beforeVO.setCol_defval(jsn.getString(CHG_BEFORE));
				afterVO.setCol_defval(jsn.getString(CHG_AFTER));
				break;
			case "colType":
				beforeVO.setCol_type(jsn.getString(CHG_BEFORE));
				afterVO.setCol_type(jsn.getString(CHG_AFTER));
				break;
			case "colIspk":
				beforeVO.setCol_ispk(jsn.getString(CHG_BEFORE));
				afterVO.setCol_ispk(jsn.getString(CHG_AFTER));
				break;
			case "colIsnull":
				beforeVO.setCol_isnull(jsn.getString(CHG_BEFORE));
				afterVO.setCol_isnull(jsn.getString(CHG_AFTER));
				break;
			default:
				logger.warn("parseChgJSON(): invalid CHG_PROPERTY_NAME: name={}", propName);
				break;
			}
		}
		switch (chgFlag) {
		case CHG_FLAG_ADD:
			beforeVO = null;
			break;
		case CHG_FLAG_DELETE:
			afterVO = null;
			beforeVO.setCol_id(json.getString("id"));
			break;
		case CHG_FLAG_UPDATE:
			beforeVO.setCol_id(json.getString("id"));
			afterVO.setCol_id(json.getString("id"));
			break;
		default:
			break;
		}
		chgPair[0] = beforeVO;
		chgPair[1] = afterVO;
		return chgPair;
	}
	
	public JSONObject toAddChgJSON(){
		return new MaintainColumnVO().compareChgJSON(this);
	}
	
	public JSONObject toDeleteChgJSON(){
		return this.compareChgJSON(null);
	}
	
	
	public static MaintainColumnVO convert(SysColumns column){
		MaintainColumnVO vo = new MaintainColumnVO();
		vo.setCol_id(column.getId());
		vo.setCol_name(column.getName());
		vo.setCol_mark(column.getComment());
		vo.setCol_ispk(column.getIspk());
		vo.setCol_type(column.getType());
		vo.setCol_length(column.getLength());
		vo.setCol_decpoint(column.getDecpoint());
		vo.setCol_isnull(column.getIsnull());
		vo.setCol_defval(column.getDefval());
		vo.setCol_seq(column.getSeq());
		vo.setVersion(column.getVersion());
		vo.setDeleted(column.getDeleted());
		vo.setChgType(column.getChgtype());
		return vo;
	}
	
	
}
