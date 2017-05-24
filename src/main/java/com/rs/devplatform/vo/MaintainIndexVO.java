package com.rs.devplatform.vo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.persistent.SysIndexs;

public class MaintainIndexVO extends ColumnIndexVO{
	
	private static final Logger logger = LoggerFactory.getLogger("MaintainIndexVO");
	
	public static final String CHG_PROPERTY_NAME = TableUpdateConfirmVO.CHG_PROPERTY_NAME;
	public static final String CHG_PROPERTY_DESCP = TableUpdateConfirmVO.CHG_PROPERTY_DESCP;
	public static final String CHG_BEFORE = TableUpdateConfirmVO.CHG_BEFORE;
	public static final String CHG_AFTER = TableUpdateConfirmVO.CHG_AFTER;
	public static final String CHG_FLAG = TableUpdateConfirmVO.CHG_FLAG;
	
	public static final String CHG_FLAG_ADD = TableUpdateConfirmVO.CHG_FLAG_ADD;
	public static final String CHG_FLAG_UPDATE = TableUpdateConfirmVO.CHG_FLAG_UPDATE;
	public static final String CHG_FLAG_DELETE = TableUpdateConfirmVO.CHG_FLAG_DELETE;

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "MaintainIndexVO [toString()=" + super.toString() + "]";
	}

	public JSONObject compareChgJSON(MaintainIndexVO updateVO){
		JSONObject json = new JSONObject();
		json.put("indexName", indexName);
		boolean isAdd = false;
		boolean isDelete = false;
		String updateIndexName = updateVO==null?null:updateVO.getIndexName();
		if(indexName==null || indexName.trim().isEmpty()){
			json.put("indexName", updateIndexName);
			json.put(CHG_FLAG, CHG_FLAG_ADD);
			json.put("id", "");
			isAdd = true;
		}else if(updateVO==null){
			json.put(CHG_FLAG, CHG_FLAG_DELETE);
			json.put("id", indexId);
			isDelete = true;
		}else{
			json.put("id", indexId);
			json.put(CHG_FLAG, CHG_FLAG_UPDATE);
		}
		JSONArray chgs = new JSONArray();
		JSONObject chg = null;
		if(isAdd || isDelete || (indexName!=null ? !indexName.equalsIgnoreCase(updateIndexName) : updateIndexName!=null)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "indexName");
			chg.put(CHG_PROPERTY_DESCP, "索引名称");
			chg.put(CHG_BEFORE, isAdd?"": indexName);
			chg.put(CHG_AFTER, isDelete?"": updateIndexName);
			chgs.add(chg);
		}
		String updateColname = updateVO==null?null:updateVO.getColName();
		if(isAdd || isDelete || (colName!=null ? !colName.equalsIgnoreCase(updateColname) : updateColname!=null)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "colName");
			chg.put(CHG_PROPERTY_DESCP, "索引栏位");
			chg.put(CHG_BEFORE, isAdd?"": colName);
			chg.put(CHG_AFTER, isDelete?"": updateColname);
			chgs.add(chg);
		}
		String updateIndexType = updateVO==null?null:updateVO.getIndexType();
		if(isAdd || isDelete || (indexType!=null ? !indexType.equalsIgnoreCase(updateIndexType) : updateIndexType!=null)){
			chg = new JSONObject();
			chg.put(CHG_PROPERTY_NAME, "indexType");
			chg.put(CHG_PROPERTY_DESCP, "索引类型");
			chg.put(CHG_BEFORE, isAdd?"": indexType);
			chg.put(CHG_AFTER, isDelete?"": updateIndexType);
			chgs.add(chg);
		}
		json.put("chgs", chgs);
		return json;
	}
	
	public boolean compareAndSetChange(MaintainIndexVO updateVO){
		if(updateVO==null || StringUtils.isBlank(updateVO.getIndexId())){
			return false;
		}
		boolean isUpdated = false;
		String updateName = updateVO.getIndexName();
		if(updateName!=null && !updateName.equals(indexName)){
			indexName = updateName;
			isUpdated = true;
		}
		String updateColName = updateVO.getColName();
		if(updateColName!=null && !updateColName.equals(colName)){
			colName = updateColName;
			isUpdated = true;
		}
		String updateType = updateVO.getIndexType();
		if(updateType!=null && !updateType.equals(indexType)){
			indexType = updateType;
			isUpdated = true;
		}
		return isUpdated;
	}
	
	public static MaintainIndexVO[] parseChgJSON(JSONObject json){
		MaintainIndexVO[] chgPair = new MaintainIndexVO[2];
		String id = json.getString("id");
		
		MaintainIndexVO beforeVO = new MaintainIndexVO();
		MaintainIndexVO afterVO = new MaintainIndexVO();
		JSONArray chgs = json.getJSONArray("chgs");
		for(int i=0;i<chgs.size();++i){
			JSONObject jsn = chgs.getJSONObject(i);
			String propName = jsn.getString(CHG_PROPERTY_NAME);
			switch (propName) {
			case "indexName":
				beforeVO.setIndexName(jsn.getString(CHG_BEFORE));
				afterVO.setIndexName(jsn.getString(CHG_AFTER));
				break;
			case "colName":
				beforeVO.setColName(jsn.getString(CHG_BEFORE));
				afterVO.setColName(jsn.getString(CHG_AFTER));
				break;
			case "indexType":
				beforeVO.setIndexType(jsn.getString(CHG_BEFORE));
				afterVO.setIndexType(jsn.getString(CHG_AFTER));
				break;
			default:
				logger.warn("parseChgJSON(): invalid CHG_PROPERTY_NAME: name={}.", propName);
				break;
			}
		}
		String chgFlag = json.getString(CHG_FLAG);
		switch (chgFlag) {
		case CHG_FLAG_ADD:
			beforeVO = null;
			break;
		case CHG_FLAG_DELETE:
			beforeVO.setIndexId(id);
			afterVO = null;
			break;
		case CHG_FLAG_UPDATE:
			beforeVO.setIndexId(id);
			afterVO.setIndexId(id);
			break;
		default:
			logger.warn("parseChgJSON(): invalid CHG_FLAG={}.", chgFlag);
			break;
		}
		chgPair[0] = beforeVO;
		chgPair[1] = afterVO;
		return chgPair;
	}
	
	public JSONObject toAddChgJSON(){
		return new MaintainIndexVO().compareChgJSON(this);
	}
	
	public JSONObject toDeleteChgJSON(){
		return this.compareChgJSON(null);
	}
	
	public static MaintainIndexVO convert(SysIndexs index){
		MaintainIndexVO vo = new MaintainIndexVO();
		vo.setColName(index.getField());
		vo.setIndexId(index.getId());
		vo.setIndexName(index.getName());
		vo.setIndexSeq(index.getSeq());
		vo.setIndexType(index.getType());
		return vo;
	}
}
