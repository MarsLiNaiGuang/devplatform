package com.rs.devplatform.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TableUpdateConfirmVO {
	
	public static final String CHG_PROPERTY_NAME = "name";
	public static final String CHG_PROPERTY_DESCP = "descp";
	public static final String CHG_BEFORE = "bf";
	public static final String CHG_AFTER = "af";
	
	public static final String CHG_FLAG = "chgFlag";
	
	public static final String CHG_FLAG_ADD = "A";
	public static final String CHG_FLAG_UPDATE = "U";
	public static final String CHG_FLAG_DELETE = "D";

	private Integer version;
	
	private MaintainTableVO tableBefore;
	private MaintainTableVO tableAfter;
	
	private List<MaintainColumnVO> addColList;
	private List<MaintainColumnVO[]> updateColList;
	private List<MaintainColumnVO> deleteColList;
	
	private List<MaintainIndexVO> addIndList;
	private List<MaintainIndexVO[]> updateIndList;
	private List<MaintainIndexVO> deleteIndList;
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public MaintainTableVO getTableBefore() {
		return tableBefore;
	}
	public void setTableBefore(MaintainTableVO tableBefore) {
		this.tableBefore = tableBefore;
	}
	public MaintainTableVO getTableAfter() {
		return tableAfter;
	}
	public void setTableAfter(MaintainTableVO tableAfter) {
		this.tableAfter = tableAfter;
	}
	public List<MaintainColumnVO> getAddColList() {
		return addColList;
	}
	public void setAddColList(List<MaintainColumnVO> addColList) {
		this.addColList = addColList;
	}
	public List<MaintainColumnVO[]> getUpdateColList() {
		return updateColList;
	}
	public void setUpdateColList(List<MaintainColumnVO[]> updateColList) {
		this.updateColList = updateColList;
	}
	public List<MaintainColumnVO> getDeleteColList() {
		return deleteColList;
	}
	public void setDeleteColList(List<MaintainColumnVO> deleteColList) {
		this.deleteColList = deleteColList;
	}
	public List<MaintainIndexVO> getAddIndList() {
		return addIndList;
	}
	public void setAddIndList(List<MaintainIndexVO> addIndList) {
		this.addIndList = addIndList;
	}
	public List<MaintainIndexVO[]> getUpdateIndList() {
		return updateIndList;
	}
	public void setUpdateIndList(List<MaintainIndexVO[]> updateIndList) {
		this.updateIndList = updateIndList;
	}
	public List<MaintainIndexVO> getDeleteIndList() {
		return deleteIndList;
	}
	public void setDeleteIndList(List<MaintainIndexVO> deleteIndList) {
		this.deleteIndList = deleteIndList;
	}
	
	/**
	 * {
	 *   id:"xxxid",
	 *   tablename:"xxxname",
	 *   version:1,
	 *   chgs:[
	 *     {
	 *       name:"tabledescp", bf:"123", af:"1234"
	 *     },
	 *     {
	 *       name:"pktype", bf:"UUID", af:"AUTO"
	 *     },
	 *   ]
	 * }
	 * 
	 * @param tableBefore
	 * @param tableAfter
	 * @return JSONObject
	 */
	public static JSONObject compareTable(MaintainTableVO tableBefore, MaintainTableVO tableAfter){
		JSONObject json = new JSONObject();
		json.put("id",tableBefore.getTableid());
		json.put("tablename", tableBefore.getTablename());
		json.put("version", tableBefore.getVersion());
		if(tableAfter==null){
			json.put(CHG_FLAG, CHG_FLAG_DELETE);
		}else{
			json.put(CHG_FLAG, CHG_FLAG_UPDATE);
		}
		JSONArray tableChgs = new JSONArray();
		String tableDescBf = tableBefore.getTabledescp();
		String tableDescAf = tableAfter==null?null:tableAfter.getTabledescp();
		if(StringUtils.isBlank(tableDescBf) && StringUtils.isBlank(tableDescAf)){
			//ignore
		}else if(!tableDescBf.equals(tableDescAf)){
			JSONObject descChg = new JSONObject();
			descChg.put(CHG_PROPERTY_NAME, "tabledescp");
			descChg.put(CHG_PROPERTY_DESCP, "表描述");
			descChg.put(CHG_BEFORE, tableDescBf);
			descChg.put(CHG_AFTER, tableDescAf);
			tableChgs.add(descChg);
		}
		if(!tableBefore.getPktype().equals(tableAfter.getPktype())){
			JSONObject pkChg = new JSONObject();
			pkChg.put(CHG_PROPERTY_NAME, "pktype");
			pkChg.put(CHG_PROPERTY_DESCP, "主键策略");
			pkChg.put(CHG_BEFORE, tableBefore.getPktype());
			pkChg.put(CHG_AFTER, tableAfter.getPktype());
			tableChgs.add(pkChg);
		}
		json.put("tableChgs", tableChgs);
		return json;
	}
	
	public static JSONObject compareColumn(List<MaintainColumnVO> addList, List<MaintainColumnVO[]> updateList, List<MaintainColumnVO> deleteList){
		JSONObject json = new JSONObject();
		JSONArray columnChgs = new JSONArray();
		if(addList!=null && !addList.isEmpty()){
			for(MaintainColumnVO col:addList){
				columnChgs.add(col.toAddChgJSON());
			}
		}
		if(updateList!=null && !updateList.isEmpty()){
			for(MaintainColumnVO[] vos:updateList){
				columnChgs.add(vos[0].compareChgJSON(vos[1]));
			}
		}
		if(deleteList!=null && !deleteList.isEmpty()){
			for(MaintainColumnVO col:deleteList){
				columnChgs.add(col.toDeleteChgJSON());
			}
		}
		json.put("columnChgs", columnChgs);
		return json;
	}
	
	public static JSONObject compareIndex(List<MaintainIndexVO> addList, List<MaintainIndexVO[]> updateList, List<MaintainIndexVO> deleteList){
		JSONObject json = new JSONObject();
		JSONArray indexChgs = new JSONArray();
		if(addList!=null && !addList.isEmpty()){
			for(MaintainIndexVO col:addList){
				indexChgs.add(col.toAddChgJSON());
			}
		}
		if(updateList!=null && !updateList.isEmpty()){
			for(MaintainIndexVO[] vos:updateList){
				indexChgs.add(vos[0].compareChgJSON(vos[1]));
			}
		}
		if(deleteList!=null && !deleteList.isEmpty()){
			for(MaintainIndexVO col:deleteList){
				indexChgs.add(col.toDeleteChgJSON());
			}
		}
		json.put("indexChgs", indexChgs);
		return json;
	}
	
	/**
	 * {
	 *   tablename:"",
	 *   id:"",
	 *   version:"",
	 *   tableChgs:[
	 *     {
	 *       name:"",
	 *       descp:"",
	 *       bf:"",
	 *       af:""
	 *     }, ...
	 *   ],
	 *   columnChgs:[
	 *     {
	 *       columnName:"colA", chgFlag:"", id:"", chgs:[
	 *         {
	 *           name:"",
	 *           descp:"",
	 *           bf:"",
	 *           af:""
	 *         },...
	 *       ]
	 *     }, ....
	 *   ],
	 *   indexChgs:[
	 *     {
	 *       indexName:"", chgFlag:"", id:"", chgs:[
	 *         {
	 *           name:"",
	 *           descp:"",
	 *           bf:"",
	 *           af:""
	 *         },
	 *       ]
	 *     }
	 *   ]
	 * }
	 * @return
	 */
	public JSONObject getCompareResult(){
		JSONObject result = compareTable(tableBefore, tableAfter);
		result.putAll(compareColumn(addColList, updateColList, deleteColList));
		result.putAll(compareIndex(addIndList, updateIndList, deleteIndList));
		return result;
	}
	
	/**
	 * {
	 *   tablename:"",
	 *   id:"",
	 *   version:"",
	 *   tableChgs:[
	 *     {
	 *       name:"",
	 *       descp:"",
	 *       bf:"",
	 *       af:""
	 *     }, ...
	 *   ],
	 *   columnChgs:[
	 *     {
	 *       columnName:"colA", chgFlag:"", id:"", chgs:[
	 *         {
	 *           name:"",
	 *           descp:"",
	 *           bf:"",
	 *           af:""
	 *         },...
	 *       ]
	 *     }, ....
	 *   ],
	 *   indexChgs:[
	 *     {
	 *       indexName:"", chgFlag:"", id:"", chgs:[
	 *         {
	 *           name:"",
	 *           descp:"",
	 *           bf:"",
	 *           af:""
	 *         },
	 *       ]
	 *     }
	 *   ]
	 * }
	 * @param json
	 * @return
	 */
	public static TableUpdateConfirmVO parseJSON(JSONObject json){
		TableUpdateConfirmVO confirmVO = new TableUpdateConfirmVO();
		MaintainTableVO[] tableVOs = MaintainTableVO.parseChgJSON(json);
		confirmVO.setTableBefore(tableVOs[0]);
		confirmVO.setTableAfter(tableVOs[1]);
		JSONArray columnChgs = json.getJSONArray("columnChgs");
		List<MaintainColumnVO> addColList = new ArrayList<>();
		List<MaintainColumnVO[]> updateColList = new ArrayList<>();
		List<MaintainColumnVO> deleteColList = new ArrayList<>();
		if(columnChgs!=null){
			for(int i=0;i<columnChgs.size();++i){
				MaintainColumnVO[] chgs = MaintainColumnVO.parseChgJSON(columnChgs.getJSONObject(i));
				if(chgs[0]==null){
					addColList.add(chgs[1]);
				}else if(chgs[1]==null){
					deleteColList.add(chgs[0]);
				}else{
					updateColList.add(chgs);
				}
			}
		}
		confirmVO.setAddColList(addColList);
		confirmVO.setUpdateColList(updateColList);
		confirmVO.setDeleteColList(deleteColList);
		
		List<MaintainIndexVO> addIndList = new ArrayList<>();
		List<MaintainIndexVO[]> updateIndList = new ArrayList<>();
		List<MaintainIndexVO> deleteIndList = new ArrayList<>();
		JSONArray indexChgs = json.getJSONArray("indexChgs");
		if(indexChgs!=null){
			for(int i=0;i<indexChgs.size();++i){
				MaintainIndexVO[] chgs = MaintainIndexVO.parseChgJSON(indexChgs.getJSONObject(i));
				if(chgs[0]==null){
					addIndList.add(chgs[1]);
				}else if(chgs[1]==null){
					deleteIndList.add(chgs[0]);
				}else{
					updateIndList.add(chgs);
				}
			}
		}
		confirmVO.setAddIndList(addIndList);
		confirmVO.setUpdateIndList(updateIndList);
		confirmVO.setDeleteIndList(deleteIndList);
		return confirmVO;
	}
	
	public String toString(){
		return getCompareResult().toJSONString();
	}
}
