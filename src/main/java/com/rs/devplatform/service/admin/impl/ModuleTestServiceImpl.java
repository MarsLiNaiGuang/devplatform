package com.rs.devplatform.service.admin.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.rs.devplatform.common.CGConstants;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysColumns;
//import com.rs.devplatform.persistent.SysTables;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.persistent.SysZqsql;
import com.rs.devplatform.persistent.mapper.CGMapper;
import com.rs.devplatform.service.admin.ModueTestService;
import com.rs.devplatform.service.cg.CGService;
import com.rs.devplatform.vo.common.RsCommonResponse;
import com.rs.devplatform.vo.common.RsCommonResponse.Detail;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.persistent.SysGp2tb;
import com.rs.framework.common.utils.RsDateUtil;
import com.rs.framework.common.utils.RsIDGenerator;
import com.rs.framework.common.utils.RsIDGenerator.SerialNumStrategy;
import com.rs.framework.common.utils.UserSessionUtil;


@Service
public class ModuleTestServiceImpl implements ModueTestService {
	
	private static final Logger logger = LoggerFactory.getLogger("ModuleTestServiceImpl");

	@Autowired
	CGMapper cgMapper;
	
	@Autowired
	CGService cgService;
	
	@Autowired
	RsGeneralMapper generalMapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private ThreadLocal<String> returnMessage;
	
	
	enum DataState{   
		Insert, Update, Delete, Inactive;   
	}
	
	private void SetReturnMessage(String s){
		if (returnMessage == null){
			returnMessage = new ThreadLocal<>();
		}
		returnMessage.set(s);
	}
	
	private boolean isNumeric(String str) {
		return NumberUtils.isNumber(str);
	}
	
	private DataState getDataState(JSONObject json){
		if ("A".equals(json.getString("tag"))){
			return DataState.Insert;
		}else if ("U".equals(json.getString("tag"))){
			return DataState.Update;
		}else if ("D".equals(json.getString("tag"))){
			return DataState.Delete;
		}else{
			return DataState.Inactive;
		}
	};
	
	//value.equals("0") || 
	
	private boolean valueIsNull(String lx, String value){
		if (CGConstants.ColTypes.int_.equals(lx) || CGConstants.ColTypes.double_.equals(lx) || 
			CGConstants.ColTypes.decimal.equals(lx) || CGConstants.ColTypes.date.equals(lx) || 
			CGConstants.ColTypes.datetime.equals(lx)){
			return (value == null) || value.equals("") || "null".equals(value.toLowerCase());
		} else {
			return (value == null) || value.equals("");
		}
	}
	
	private String getParamValue(String lx, String value){
		if (valueIsNull(lx, value)){
			if (CGConstants.ColTypes.string.equals(lx) || CGConstants.ColTypes.text.equals(lx)) {
				return "''";
			} else if (CGConstants.ColTypes.int_.equals(lx) || CGConstants.ColTypes.double_.equals(lx)
					|| CGConstants.ColTypes.decimal.equals(lx)) {
				return "0";
			} else if (CGConstants.ColTypes.date.equals(lx) || CGConstants.ColTypes.datetime.equals(lx)) {
				return "null";
			} else {
				return "''";
			}
		}else{
			if (CGConstants.ColTypes.string.equals(lx) || CGConstants.ColTypes.text.equals(lx)) {
				return "'" + value + "'";
			} else if (CGConstants.ColTypes.int_.equals(lx) || CGConstants.ColTypes.double_.equals(lx)
					|| CGConstants.ColTypes.decimal.equals(lx)) {
				return value;
			} else if (CGConstants.ColTypes.date.equals(lx) || CGConstants.ColTypes.datetime.equals(lx)) {
				if (RsDateUtil.parseDate4LongStr(value) == null){
					return "null";
				}else{
					return "'" + RsDateUtil.formatDate(RsDateUtil.parseDate4LongStr(value)) + "'";
				}
				
			} else {
				return "''";
			}
		}		
	}
	
	private Object getParamValue(String lx, Object value){
		if ((value == null)||(valueIsNull(lx, value.toString()))){
			return null;
		}else{
			if (CGConstants.ColTypes.date.equals(lx) || CGConstants.ColTypes.datetime.equals(lx)) {
				return RsDateUtil.parseDate4LongStr(value.toString());
			} else {
				return value;
			}
		}
	}
		
	private boolean validateValue(String lx, String value){
		if (!valueIsNull(lx, value)){
			if (CGConstants.ColTypes.int_.equals(lx) || CGConstants.ColTypes.double_.equals(lx)
					|| CGConstants.ColTypes.decimal.equals(lx)) {
				return isNumeric(value);
			} else if (CGConstants.ColTypes.date.equals(lx) || CGConstants.ColTypes.datetime.equals(lx)) {
				if (RsDateUtil.parseDate(value) == null  && RsDateUtil.parseDate4LongStr(value) == null)
					return false;
				else
					return true;
			} else {
				return true;
			}
		}else{
			return true;
		}
	}
	
	public String getField(String table, String field){
		return (table.substring(table.indexOf("_")+1) + "_" + field).toUpperCase();
	}
	
	private Boolean canUpdateByUsersField(String table, String field) {
		return (!field.equals(getField(table, "cjr"))) && (!field.equals(getField(table, "cjrid")))
				&& (!field.equals(getField(table, "cjsj"))) && (!field.equals(getField(table, "id")))
				&& (!field.equals(getField(table, "version")));
	}
	
	// 业务逻辑方面
	private String getInsertSql(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columslist, JSONObject entity, List<Object> valuelist) {
		int i = 0;
		String insert = "insert into " + sysBxx.getBm() + "(";
		String value = " values(";
		for(SysColumns colums : columslist ){
			if (i != 0){
				insert += ", ";
			}
			insert += colums.getName();
			if (i != 0){
				value += ", " ;
			}
			value += "?";
			valuelist.add(getParamValue(colums.getType(),entity.get(colums.getName())));
			i++;	
		}	
		insert += ")";
		value += ")";
		if (i > 0){
			return insert + value;
		}else{
			return "";
		}		
	}
	
	//获取update语句
	private String getUpdateSql(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, JSONObject entity, List<Object> valuelist) {
		String updateSql = " update " + sysBxx.getBm() + " set ";
		String whereSql = " where 1 <> 1";
		int iSet = 0;
		int iWhere = 0;
		String verField = getField(sysBxx.getBm(), "version");	
		//拼接update字段
		for(SysColumns updatecolumns : columnslist ){
			if (canUpdateByUsersField(sysBxx.getBm(), updatecolumns.getName()) && (entity.getString(updatecolumns.getName()) != null)){
				if (iSet != 0){
					updateSql += ", " ;
				}
				updateSql += updatecolumns.getName() + " = ? ";
				valuelist.add(getParamValue(updatecolumns.getType(),entity.get(updatecolumns.getName())));
				iSet++;
			}
		}
		//增加版本的update条件
		if (iSet > 0){
			updateSql += ", " + verField + "=" + verField + "+1"; 
		}
		
		//拼接where字段
		for(SysColumns wherecolumns : columnslist ){
			if ((wherecolumns.getIspk() != null) && (wherecolumns.getIspk().equals("Y"))){
				if (iWhere != 0){
					whereSql += " and ";
				}else{
					whereSql += " or ( ";
				}
				whereSql += wherecolumns.getName() + " = ? ";
				valuelist.add(getParamValue(wherecolumns.getType(),entity.get(wherecolumns.getName())));
				iWhere++;
			}			
		}
		//增加版本的where条件
		if (iWhere > 0){
			whereSql += " and " + verField + "= ? ";
			valuelist.add(getParamValue(CGConstants.ColTypes.int_,entity.get(verField)));
			whereSql += ")";
		}	
		if (iSet > 0){
			return updateSql + whereSql;
		}
		else{
			return "";
		}		
	}
	
	private String getDeleteSql(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, String id, List<Object> valuelist) {
		String sql = " delete from " + sysBxx.getBm() + " where 1 <> 1";
		int i = 0;
		for(SysColumns Columns : columnslist ){
			if ((Columns.getIspk() != null) && (Columns.getIspk().equals("Y"))){
				if (i != 0){
					sql += "  and ";
				}else{ 
					sql += " or ( ";
				}
				//目前系统认为只有一个主键
				sql += Columns.getName() +  "= ? ";
				valuelist.add(id);
				i++;
			}
			
		}
		if (i > 0){
			sql += ")";
		}
		return sql;
	}
	
	private String getDeleteSqlByFid(SysBxx masterbxx, SysBxx detailbxx,
			List<SysZdxx> list, List<SysColumns> columnslist, String masterId, List<Object> valuelist) {
		String sql = " delete from " + detailbxx.getBm() + " where 1 <> 1";
		JSONObject zbjson = new JSONObject();
		String zb = masterbxx.getZb();
		JSONArray detailarray = new JSONArray();
		detailarray = JSONArray.parseArray(zb);
		if (detailarray != null && detailarray.size() > 0){
			for(int i=0;i<detailarray.size();i++){
				JSONObject json = detailarray.getJSONObject(i);
				String tablename = json.getString("tablename");
				if (tablename.equals(detailbxx.getBm())){
					String mainColName = json.getString("mainColName");
					String colName = json.getString("colName");
					zbjson.put(colName, mainColName);
				}
			}
		}
		
		if (zbjson != null && zbjson.size() > 0){
	        //获取关联主表的字段值
			String mastersql = "";
			for (String colName:zbjson.keySet()){
				if (mastersql.equals("")){
					mastersql += "select " + zbjson.getString(colName) + " as " + colName;
				}
				else {
					mastersql += ", " + zbjson.getString(colName) + " as " + colName;			
				}
			}
			mastersql += " from " + masterbxx.getBm() + " where " + getField(masterbxx.getBm(), "id") + " = '" + masterId + "'";
			List<Map<String,Object>> maplist = cgMapper.selectSql(mastersql);
			
			if (maplist != null && maplist.size() == 1){
				Map<String, Object> map = maplist.get(0);
				if (map != null && map.size() > 0){
					String wheresql ="";
					for (SysColumns syscolumns : columnslist){
						if (map.containsKey(syscolumns.getName())){
							if (wheresql.equals("")){
								wheresql = " (" + syscolumns.getName() + "= ? ) ";
								valuelist.add(getParamValue(syscolumns.getType(), map.get(syscolumns.getName())));
								}
							else{
								wheresql += " and (" + syscolumns.getName() + "= ? ) ";
								valuelist.add(getParamValue(syscolumns.getType(), map.get(syscolumns.getName())));							
								}
							}}
					if (!wheresql.equals("")){
						sql += " or (" + wheresql + ")";
						}			
					}	
				}
			}
		return sql;		
	}
	
	private String getKey(String pklx){
		if (pklx.equals("UUID")){
			return RsIDGenerator.init().serialNum(SerialNumStrategy.UUID).toString();
			}else{
			return "''";
			}	
	}
	
	//系统自动赋值项
	private boolean autoValueField(String tableName, String fieldName){
		return fieldName.equals(getField(tableName, "id")) ||
				fieldName.equals(getField(tableName, "cjr")) ||
				fieldName.equals(getField(tableName, "cjrid")) ||
				fieldName.equals(getField(tableName, "cjsj")) ||
				fieldName.equals(getField(tableName, "deleted")) ||
				fieldName.equals(getField(tableName, "version")) ||
				fieldName.equals(getField(tableName, "whr")) ||
				fieldName.equals(getField(tableName, "whrid")) ||
				fieldName.equals(getField(tableName, "whsj"));
	}
	
	//保存前数据校验
	private Boolean postDataValid(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> Columnslist, 
			JSONObject entity, DataState state){
		if ((state == DataState.Insert) || (state == DataState.Update)){
			String value;
			for (SysZdxx zdxx : list){
				if (!autoValueField(sysBxx.getBm(), zdxx.getName())){
					value = entity.getString(zdxx.getName());
					if (!validateValue(zdxx.getLx(), value)){
						SetReturnMessage(zdxx.getNr() + "数据类型不正确！");
						return false;
					}
					if (!"Y".equals(zdxx.getIsnull()) && valueIsNull(zdxx.getLx(), value) && zdxx.getIsshow().equals(Constants.YES)){
						SetReturnMessage(zdxx.getNr() + "必须填写！");
						return false;
					}
				}	
			}
			/*预留部分，以免以后要使用
			for (SysColumns columns : Columnslist){

			}
			*/
		}
		return true;
	}

	//删除前数据校验
	private Boolean deleteDataValid(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, String id){
		return true;
	}
	
	//保存前更新其他表数据或更新本实体相关信息
	private void beforePost(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, JSONObject entity){
		// 设置系统字段内容
		entity.put(getField(sysBxx.getBm(), "whr"), UserSessionUtil.getCurrentUserId());
		entity.put(getField(sysBxx.getBm(), "whrid"), UserSessionUtil.getCurrentUserId());
		entity.put(getField(sysBxx.getBm(), "whsj"), RsDateUtil.getCurrentDataTimeLongStr());
	}
	
	//新增前单据号根据规则生成等操作
	private void beforeInsert(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, JSONObject entity){
		//key赋值
		entity.put(getField(sysBxx.getBm(), "id"), getKey(sysBxx.getPklx()));
		//设置系统字段内容
		entity.put(getField(sysBxx.getBm(), "cjr"), UserSessionUtil.getCurrentUserId());
		entity.put(getField(sysBxx.getBm(), "cjrid"), UserSessionUtil.getCurrentUserId());
		entity.put(getField(sysBxx.getBm(), "cjsj"), RsDateUtil.getCurrentDataTimeLongStr());
		entity.put(getField(sysBxx.getBm(), "deleted"), Constants.DelInd.FALSE);
		entity.put(getField(sysBxx.getBm(), "version"), 1);
        
	}
	
	private boolean beforeDelete(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, String id){
		JSONObject record = new JSONObject();
		String key = getField(sysBxx.getBm(), "id");
		record.put(key, id);
		return executeZqsql(sysBxx.getId(), "delete", record);
	}
	
	private int InternalInsert(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, JSONObject entity){
		List<Object> valuelist = new ArrayList<Object>();
		String sql = getInsertSql(sysBxx, list, columnslist, entity, valuelist);
		if ("".equals(sql)){
			return 0;
		}else{
			return jdbcTemplate.update(sql,valuelist.toArray());
		}
	}
	
	private int InternalUpdate(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, JSONObject entity){
		List<Object> valuelist = new ArrayList<Object>();
		String sql = getUpdateSql(sysBxx, list, columnslist, entity, valuelist);
		if ("".equals(sql)){
			return 0;
		}else{
			//return cgMapper.executSql(sql);
			return jdbcTemplate.update(sql,valuelist.toArray());
		}
		
	}
	
	private int InternalDelete(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, String id){
		List<Object> valuelist = new ArrayList<Object>();
		String sql = getDeleteSql(sysBxx, list, columnslist, id, valuelist);
		return jdbcTemplate.update(sql,valuelist.toArray());
	}
	
	private boolean afterPost(SysBxx sysBxx, List<SysZdxx> list,  List<SysColumns> columnslist, JSONObject entity, DataState state){
		if (state == DataState.Insert){
			return executeZqsql(sysBxx.getId(), "add", entity);
		}else if (state == DataState.Update){
			return executeZqsql(sysBxx.getId(), "edit", entity);
		}else{
			return true;
		}
		
	}
	
	private void afterInsert(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, JSONObject entity){
		
	}
	
	private void afterDelete(SysBxx sysBxx, List<SysZdxx> list,  List<SysColumns> columnslist, String id){
		
	}
	
	@Transactional
	private int doRecordInsert(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, JSONObject entity){
		int result = 0;
		beforePost(sysBxx, list, columnslist, entity);
		result = InternalInsert(sysBxx, list, columnslist, entity);
		if (result > 0){
			if (!afterPost(sysBxx, list, columnslist, entity, DataState.Insert)){
				//回滚事务
			}
		}else{
			//回滚事务 
		}
		return result;
	} 
	
	@Transactional
	private int doRecordUpdate(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, JSONObject entity){
		int result = 0;
		beforePost(sysBxx, list, columnslist, entity);
		result = InternalUpdate(sysBxx, list, columnslist, entity);
		if (result > 0){
			if (!afterPost(sysBxx, list, columnslist, entity, DataState.Update)){
				//回滚事务
			}
		}else{
			//回滚事务 
		}
		return result;
	}
	
	@Transactional
	private int doRecordDelete(SysBxx sysBxx, List<SysZdxx> list, List<SysColumns> columnslist, String id){
		int result = 0;
		beforeDelete(sysBxx, list, columnslist, id);
		result = InternalDelete(sysBxx, list, columnslist, id);
		if (result > 0){
			afterDelete(sysBxx, list, columnslist, id);
		}else{
			//回滚事务 
		}
		return result;
	}
	
	@Transactional
	private Boolean doRecordInsert(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap,
			HashMap<String, List<SysColumns>> columnsMap, RsCommonResponse resp) {
		//先保存主表
		int rowAffected = doRecordInsert(bxxMap.get(resp.getId()), zdxxMap.get(resp.getId()),
				columnsMap.get(resp.getId()), resp.getMaster());
		if ((rowAffected > 0) && (resp.getDetail() != null)){
			// 循环从表明细数据
			for (int i = 0; i < resp.getDetail().length; i++) {
				Detail detail = resp.getDetail()[i];
				System.out.println(detail.getRecords().size());
				for (int j = 0; j < detail.getRecords().size(); j++) {
					rowAffected = doRecordInsert(bxxMap.get(detail.getId()), zdxxMap.get(detail.getId()),
							columnsMap.get(detail.getId()), detail.getRecords().getJSONObject(j));
					if (rowAffected == 0) {
						// 事务回滚
						break;
					}
				}
			}
		}
		if (rowAffected <= 0){
			//回滚事务
		}
		return rowAffected > 0;
	}
	
	@Transactional
	private Boolean doRecordUpdate(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap, 
			HashMap<String, List<SysColumns>> columnsMap, RsCommonResponse resp){
		// 先保存主表
		int rowAffected = doRecordUpdate(bxxMap.get(resp.getId()), zdxxMap.get(resp.getId()),
				columnsMap.get(resp.getId()), resp.getMaster());
		if (rowAffected > 0  && resp.getDetail() != null) {
			// 循环从表明细数据
			for (int i = 0; i < resp.getDetail().length; i++) {
				Detail detail = resp.getDetail()[i];
				for (int j = 0; j < detail.getRecords().size(); j++) {
					JSONObject entity = detail.getRecords().getJSONObject(j);
					if (getDataState(entity) == DataState.Insert){
						rowAffected = doRecordInsert(bxxMap.get(detail.getId()), zdxxMap.get(detail.getId()),
								columnsMap.get(detail.getId()), detail.getRecords().getJSONObject(j));
					}else if (getDataState(entity) == DataState.Update){
						rowAffected = doRecordUpdate(bxxMap.get(detail.getId()), zdxxMap.get(detail.getId()),
								columnsMap.get(detail.getId()), detail.getRecords().getJSONObject(j));
					}else if (getDataState(entity) == DataState.Delete){
						rowAffected = doRecordDelete(bxxMap.get(detail.getId()), zdxxMap.get(detail.getId()), columnsMap.get(detail.getId()),
								detail.getRecords().getJSONObject(j).getString(getField(bxxMap.get(detail.getId()).getBm(), "id")));
					}
					if (rowAffected == 0)
						break;
				}
			}
		}
		if (rowAffected <= 0){
			//回滚事务
		}
		return rowAffected > 0;
	}
	
	//@Transactional
	private Boolean doRecordDelete(String tableid, String id, HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap,
			HashMap<String, List<SysColumns>> columnsMap){
		//删除子表
		Iterator<Entry<String, SysBxx>> iter = bxxMap.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry<String, SysBxx> entry = ( Map.Entry<String, SysBxx> ) iter.next();
		    SysBxx sysBxx = entry.getValue();
		    if (!tableid.equals(sysBxx.getId())){
		    	List<Object> valuelist = new ArrayList<Object>();
		    	String sql = getDeleteSqlByFid(bxxMap.get(tableid), sysBxx, zdxxMap.get(sysBxx.getId()), columnsMap.get(sysBxx.getId()), id, valuelist);
		    	jdbcTemplate.update(sql,valuelist.toArray());
		    }
		}
		//删除主表
		int rowAffected = doRecordDelete(bxxMap.get(tableid), zdxxMap.get(tableid), columnsMap.get(tableid), id);
		if (rowAffected == 0){
			return false;
		}
		return true;
	}
	
	private void beforeInsert(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap,
			HashMap<String, List<SysColumns>> columnsMap, RsCommonResponse resp) {
		// 主表beforeInsert
		beforeInsert(bxxMap.get(resp.getId()), zdxxMap.get(resp.getId()),
				columnsMap.get(resp.getId()), resp.getMaster());
		if (resp.getDetail() != null){
			// 循环从表明细数据
			for (int i = 0; i < resp.getDetail().length; i++) {
				Detail detail = resp.getDetail()[i];
				for (int j = 0; j < detail.getRecords().size() ; j++) {
					beforeInsert(bxxMap.get(detail.getId()), zdxxMap.get(detail.getId()),
							columnsMap.get(detail.getId()), detail.getRecords().getJSONObject(j));
					//设置子表关联字段的值
					setsubFieldValue(bxxMap.get(resp.getId()), bxxMap.get(detail.getId()), zdxxMap.get(detail.getId()),
							columnsMap.get(detail.getId()), resp.getMaster(), detail.getRecords().getJSONObject(j));
				}
			}
		}
//		//设置明细表外键
//		resetDetailForeignKey(bxxMap, zdxxMap, columnsMap, resp);		
	}
	
	//设置明细表与主表关联字段的值
	private void setsubFieldValue(SysBxx masterbxx, SysBxx detailbxx, List<SysZdxx> zdxxlist,
			List<SysColumns> columnslist, JSONObject Masterresp, JSONObject detailresp){
		String zb = masterbxx.getZb();
		JSONArray detailarray = new JSONArray();
		detailarray = JSONArray.parseArray(zb);
		if (detailarray != null && detailarray.size() > 0){
			for(int i=0;i<detailarray.size();i++){
				JSONObject json = detailarray.getJSONObject(i);
				String tablename = json.getString("tablename");
				if (tablename.equals(detailbxx.getBm())){
					String mainColName = json.getString("mainColName");
					String colName = json.getString("colName");
					detailresp.put(colName, Masterresp.getString(mainColName));
				}
			}
		}
	}

	private void afterInsert(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap,
			HashMap<String, List<SysColumns>> columnsMap, RsCommonResponse resp) {
		// 主表beforeInsert
		afterInsert(bxxMap.get(resp.getId()), zdxxMap.get(resp.getId()),
				columnsMap.get(resp.getId()), resp.getMaster());
		if (resp.getDetail() != null){
			// 循环从表明细数据
			for (int i = 0; i < resp.getDetail().length; i++) {
				Detail detail = resp.getDetail()[i];
				for (int j = 0; j < detail.getRecords().size() ; j++){
					afterInsert(bxxMap.get(detail.getId()), zdxxMap.get(detail.getId()), 
							columnsMap.get(resp.getId()), detail.getRecords().getJSONObject(j));
				}
			}
		}
	}
	
	//保存前数据校验
	private boolean postDataValid(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap,
			HashMap<String, List<SysColumns>> columnsMap, RsCommonResponse resp, DataState state) {
		boolean result = true;
		Detail detail;
		if (postDataValid(bxxMap.get(resp.getId()), zdxxMap.get(resp.getId()),
				columnsMap.get(resp.getId()), resp.getMaster(), state)) {
			if (resp.getDetail() != null){
				// 循环校验明细数据
				for (int i = 0; i < resp.getDetail().length; i++) {
					detail = resp.getDetail()[i];
					for (int j = 0; j < detail.getRecords().size() ; j++){
						JSONObject entity = detail.getRecords().getJSONObject(j);
						result = postDataValid(bxxMap.get(detail.getId()), zdxxMap.get(detail.getId()), columnsMap.get(detail.getId()),
								entity, getDataState(entity));
						if (!result) break;
					}
					if (!result) break;
				}
			}
		} else {
			result = false;
		}
		return result;
	}
	
	public int recordInsert(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap, 
			HashMap<String, List<SysColumns>> columnsMap, RsCommonResponse resp) {
		int result;
		//先校验主表
		if (postDataValid(bxxMap, zdxxMap, columnsMap, resp, DataState.Insert)){
			beforeInsert(bxxMap, zdxxMap, columnsMap, resp);
			if (doRecordInsert(bxxMap, zdxxMap, columnsMap, resp)){
				result = 1;
			}else
				result = 0;
			if (result > 0) {
				afterInsert(bxxMap, zdxxMap, columnsMap, resp);
			}
		}else{
			result = -1;
		}
		return result;
	}
	
	private String getTableIDByTableName(HashMap<String, SysBxx> bxxMap, String TableName){
		Iterator<Entry<String, SysBxx>> iter = bxxMap.entrySet().iterator();
		String result = "";
		while (iter.hasNext()) {
		    Map.Entry<String, SysBxx> entry = ( Map.Entry<String, SysBxx> ) iter.next();
		    SysBxx sysBxx = entry.getValue();
		    if (TableName.equals(sysBxx.getBm())){
		    	result = sysBxx.getId();
		    	break;
		    }
		}
		return result;
	}
	@SuppressWarnings("unused")
	@Deprecated
	private void resetDetailForeignKey(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap,
			HashMap<String, List<SysColumns>> ColumnsMap, RsCommonResponse resp) {
		// 循环从表明细数据
		JSONObject record;
		if (resp.getDetail() != null){
			for (int i = 0; i < resp.getDetail().length; i++) {
				Detail detail = resp.getDetail()[i];
				List<SysZdxx> zdxxList = getZdxxList(detail.getId());
				for (int j = 0; j < detail.getRecords().size() ; j++) {
					record = detail.getRecords().getJSONObject(j);
					if (getDataState(record) == DataState.Insert){
						//为所有需要主表赋值的字段赋值，此处目前只支持了一级，未考虑多级的情况
						for (int k = 0; k < zdxxList.size(); k++){
							if ((zdxxList.get(k).getZb() != null) && (zdxxList.get(k).getZzd() != null)){
								String bid = getTableIDByTableName(bxxMap, zdxxList.get(k).getZb());
								if (!bid.equals("")){
									String mFieldName = getField(bxxMap.get(bid).getBm(), zdxxList.get(k).getZzd());
									record.put(zdxxList.get(k).getName(), resp.getMaster().getString(mFieldName));
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void resetDetailKey(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap,
			HashMap<String, List<SysColumns>> columnsMap, RsCommonResponse resp){
		if (resp.getDetail() != null){
			// 循环从表明细数据
			for (int i = 0; i < resp.getDetail().length; i++) {
				Detail detail = resp.getDetail()[i];
				for (int j = 0; j < detail.getRecords().size() ; j++) {
					JSONObject entity = detail.getRecords().getJSONObject(j);
					if (getDataState(entity) == DataState.Insert) {
						beforeInsert(bxxMap.get(detail.getId()), zdxxMap.get(detail.getId()),
								columnsMap.get(detail.getId()), detail.getRecords().getJSONObject(j));
					}
					//设置子表关联字段的值
					setsubFieldValue(bxxMap.get(resp.getId()), bxxMap.get(detail.getId()), zdxxMap.get(detail.getId()),
							columnsMap.get(detail.getId()), resp.getMaster(), detail.getRecords().getJSONObject(j));
				}
			}
		}
	}
	
	private void beforeUpdate(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap,
			HashMap<String, List<SysColumns>> columnsMap, RsCommonResponse resp) {
		//resetDetailForeignKey(bxxMap, zdxxMap, columnsMap, resp);
		resetDetailKey(bxxMap, zdxxMap, columnsMap, resp);
	}
	
	private void afterUpdate(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap,
			RsCommonResponse resp) {
		
	}
	
	public int recordUpdate(HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap, 
			HashMap<String, List<SysColumns>> columnsMap, RsCommonResponse resp){
		int result;
		if (postDataValid(bxxMap, zdxxMap, columnsMap, resp, DataState.Update)){
			beforeUpdate(bxxMap, zdxxMap, columnsMap, resp);
			if (doRecordUpdate(bxxMap, zdxxMap, columnsMap, resp)){
				result = 1;
			}else
				result = 0; 
			if (result > 0) {
				afterUpdate(bxxMap, zdxxMap, resp);
			}
		}else{
			result = -1;
		}
		return result;
	}
	
	private boolean deleteDataValid(String tableid, String id, HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap,
			HashMap<String, List<SysColumns>> columnsMap){
		return deleteDataValid(bxxMap.get(tableid), zdxxMap.get(tableid), columnsMap.get(tableid), id);
	}
	
	public int recordDelete(String tableid, String id, HashMap<String, SysBxx> bxxMap, HashMap<String, List<SysZdxx>> zdxxMap, 
			HashMap<String, List<SysColumns>> columnsMap ){
		//根据tableid、id查找所有明细数据,注意，此处全部为倒序
		if (deleteDataValid(tableid, id, bxxMap, zdxxMap, columnsMap)){
			if (doRecordDelete(tableid, id, bxxMap, zdxxMap, columnsMap))
				return 1;
			else
				return 0;
		}else{
			return 0;
		}
	}
	
	//获取功能列信息
	private List<SysZdxx> getZdxxList(String bid){
		SysZdxx zdxxParm = new SysZdxx();
		zdxxParm.setBid(bid);
		zdxxParm.setDeleted(Constants.DelInd.FALSE);
		RsEntityWrapper<SysZdxx> zdxxWrapper = new RsEntityWrapper<>(zdxxParm);
		zdxxWrapper.orderBy("ZDXX_XH");
		List<SysZdxx> zdxxList = generalMapper.selectList(zdxxWrapper);
		//增加ID、VERSION列
		Map<String, SysZdxx> zdxxmap = new HashMap<String, SysZdxx>();
		if (zdxxList.size() > 0)
		{
			for(SysZdxx list :zdxxList){
				zdxxmap.put(list.getName(), list);
			}
		}
		
		SysBxx bxx = generalMapper.selectById(bid, SysBxx.class);
		
		if (!zdxxmap.containsKey(getField(bxx.getBm(), "id"))){
			SysZdxx zdxx_id = new SysZdxx();
			zdxx_id.setName(getField(bxx.getBm(),"id"));
			zdxx_id.setLx("string");
			zdxx_id.setIsshow(Constants.NO);
			zdxx_id.setIsshowlb(Constants.NO);
			zdxxList.add(zdxx_id);
		}
		
		if (!zdxxmap.containsKey(getField(bxx.getBm(), "version"))){
			SysZdxx zdxx_version = new SysZdxx();
			zdxx_version.setName(getField(bxx.getBm(),"version"));
			zdxx_version.setLx("int");
			zdxx_version.setIsshow(Constants.NO);
			zdxx_version.setIsshowlb(Constants.NO);
			zdxxList.add(zdxx_version);
		}
		
		return zdxxList;
	}
	
	//获取功能表信息
	private SysBxx getBxx(String bid){	
		SysBxx bxxParm = new SysBxx();
		bxxParm.setId(bid);
		bxxParm.setDeleted(Constants.DelInd.FALSE);
		return generalMapper.selectById(bid, SysBxx.class);
	}
		
	//参数： bid为主表ID，从表的表ID全部在detail里边
	private HashMap<String, SysBxx> getBxxMap(RsCommonResponse resp){
		HashMap<String, SysBxx> result = new HashMap<>();
		SysBxx sysBxx = getBxx(resp.getId());
		if (sysBxx != null)
			result.put(resp.getId(), sysBxx);
		else 
			return null;
		if (resp.getDetail() != null){
			for (int i = 0; i < resp.getDetail().length; i++){
				sysBxx = getBxx(resp.getDetail()[i].getId());
				if (sysBxx != null)
					result.put(resp.getDetail()[i].getId(), sysBxx);
				else
					return null;
			}
		}
		return result;
	}
	
	// 参数： bid为主表ID，从表的表ID全部在detail里边
	private HashMap<String, List<SysZdxx>> getZdxxMap(HashMap<String, SysBxx> bxxMap) {
		HashMap<String, List<SysZdxx>> result = new HashMap<>();
		Iterator<Entry<String, SysBxx>> iter = bxxMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, SysBxx> entry = (Map.Entry<String, SysBxx>) iter.next();
			SysBxx sysBxx = entry.getValue();
			result.put(sysBxx.getId(), getZdxxList(sysBxx.getId()));
		}
		return result;
	}
	
	/*
	//获取字段表信息
	private SysTables getTables(String tableid){	
		SysTables TablesParm = new SysTables();
		TablesParm.setId(tableid);
		TablesParm.setDeleted(Constants.DelInd.FALSE);
		return generalMapper.selectById(tableid, SysTables.class);
	}
	*/
		
	/*
	//获取字段表Map
	private HashMap<String, SysTables> getSysTablesMap(HashMap<String, SysBxx> bxxMap){
		HashMap<String, SysTables> result = new HashMap<>();
		Iterator<Entry<String, SysBxx>> iter = bxxMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, SysBxx> entry = (Map.Entry<String, SysBxx>) iter.next();
			SysBxx sysBxx = entry.getValue();
			String tableid="";
			result.put(sysBxx.getId(), getTables(tableid));
		}
		return result;
	}
	*/
	
	private HashMap<String, List<SysColumns>> getColumnsMap(HashMap<String, SysBxx> bxxMap) {
		HashMap<String, List<SysColumns>> result = new HashMap<>();
		Iterator<Entry<String, SysBxx>> iter = bxxMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, SysBxx> entry = (Map.Entry<String, SysBxx>) iter.next();
			SysBxx sysBxx = entry.getValue();       
			result.put(sysBxx.getId(), cgService.getCurrentAvailableColumns(sysBxx.getBm()));
		}
		return result;
	}
		
	@Override
	public boolean moduleAdd(RsCommonResponse resp){
		HashMap<String, SysBxx> bxxMap = getBxxMap(resp);
		HashMap<String, List<SysZdxx>> zdxxMap = getZdxxMap(bxxMap);
		HashMap<String, List<SysColumns>> ColumnsMap = getColumnsMap(bxxMap);
		if ((bxxMap != null) && (ColumnsMap != null)){
			return recordInsert(bxxMap, zdxxMap, ColumnsMap, resp) > 0;
		}else{
			return false;
		}	
	}
	
	@Override
	public boolean moduleUpdate(RsCommonResponse resp){
		HashMap<String, SysBxx> bxxMap = getBxxMap(resp);
		HashMap<String, List<SysZdxx>> zdxxMap = getZdxxMap(bxxMap);
		HashMap<String, List<SysColumns>> columnsMap = getColumnsMap(bxxMap);		
		if ((bxxMap != null) && (zdxxMap != null)) {
			return recordUpdate(bxxMap, zdxxMap, columnsMap, resp) > 0;
		}else{
			return false;
		}	
	}
	
	//根据主表的zb字段获取字表信息
	private List<SysBxx> GetDetailBXXByzb(String zb){
		JSONArray detailarray = new JSONArray();
		detailarray = JSONArray.parseArray(zb);
		String wheresql = "1<>1";
		//循环获取字表的fcflag
		if (detailarray != null && detailarray.size() > 0){
			for(int i=0;i<detailarray.size();i++){
				JSONObject json = detailarray.getJSONObject(i);				
				String fcflag = json.getString("fcflag");
				wheresql += " or BXX_FCFLAG = '" + fcflag + "'";  
			}
		}
		//根据fcflag值获取bxx
		SysBxx parm = new SysBxx();
		RsEntityWrapper<SysBxx> ew = new RsEntityWrapper<SysBxx>(parm);
		ew.andNew(wheresql);
		return generalMapper.selectList(ew);	
	}
	
	//参数： bid为主表ID，获取主表的全部从表
	private void getBxxMap(String bid, HashMap<String, SysBxx> bxxMap) {
		SysBxx bxxMaster = getBxx(bid);
		//根据BXX主表中的zb字表信息
		List<SysBxx> bxxList = GetDetailBXXByzb(bxxMaster.getZb());
		
		for (SysBxx sysBxx : bxxList) {
			getBxxMap(sysBxx.getId(), bxxMap);
			bxxMap.put(sysBxx.getId(), getBxx(sysBxx.getId()));
		}
	}
	
	@Override
	public boolean moduleDelete(String id, String tableId){
		SysBxx sysBxx = getBxx(tableId);
		HashMap<String, SysBxx> bxxMap = new HashMap<>();
		getBxxMap(sysBxx.getId(), bxxMap);
		bxxMap.put(tableId, getBxx(tableId));
		HashMap<String, List<SysZdxx>> zdxxMap = getZdxxMap(bxxMap);
		HashMap<String, List<SysColumns>> columnsMap = getColumnsMap(bxxMap);	
		if ((bxxMap != null) && (zdxxMap != null)) {
			return recordDelete(tableId, id, bxxMap, zdxxMap, columnsMap) > 0;
		}else{
			return false;
		}	
	}
	
	private String getWhereSql(SysBxx sysBxx, List<SysZdxx> list, JSONObject entity){
		String whereSql = " where 1=1 ";
		String conditionValue = "";
		for(SysZdxx zdxx : list ){
			if ("range".equals(zdxx.getCxms())){
				conditionValue = entity.getString("(B)"+zdxx.getName());
				//是否作为查询条件列以及值不为空
				if (!valueIsNull(zdxx.getLx(),conditionValue))
					whereSql += " and " + zdxx.getName() + " >= " + getParamValue(zdxx.getLx(), conditionValue);
				conditionValue = entity.getString("(E)"+zdxx.getName());
				//是否作为查询条件列以及值不为空
				if (!valueIsNull(zdxx.getLx(), conditionValue))
					whereSql += " and " + zdxx.getName() + " <= " + getParamValue(zdxx.getLx(), conditionValue);
			}else{
				conditionValue = entity.getString(zdxx.getName());
				//是否作为查询条件列以及值不为空
				if (!valueIsNull(zdxx.getLx(), conditionValue)){
					if ((CGConstants.ColTypes.string.equals(zdxx.getLx()) || CGConstants.ColTypes.text.equals(zdxx.getLx())) &&
							(CGConstants.InputTypes.text.equals(zdxx.getXslx()) || CGConstants.InputTypes.textarea.equals(zdxx.getXslx()))){
						whereSql += " and " + zdxx.getName() + " like '%" + conditionValue + "%'";
					}else{
						whereSql += " and " + zdxx.getName() + " = " + getParamValue(zdxx.getLx(), conditionValue);
					}
				}
					
			}
		}
		return whereSql;
	}
	@SuppressWarnings("unused")
	@Deprecated
	private String getGroupId(String tableName){
		if (tableName == null || tableName.equals("")){
			return "";
		}else{
			SysGp2tb param = new SysGp2tb();
			param.setTbname(tableName);	
			param.setDeleted(Constants.DelInd.FALSE);
			List<SysGp2tb> gp2tbList = generalMapper.selectList(new RsEntityWrapper<>(param));
			if (gp2tbList.size() > 0){
				return gp2tbList.get(0).getGpid();
			}else{
				return "";
			} 
		}
	}
	
	@Override
	public List<Map<String,Object>> moduleGetList(JSONObject entity, String bid, Pagination page){
		SysBxx sysBxx = getBxx(bid);
		List<SysZdxx> list = getZdxxList(bid);
		String sql;
		if ((sysBxx != null) && (list != null)) {
			//处理sql语句中自定义变量的赋值
			if (sysBxx.getIsql() != null && !sysBxx.getIsql().equals("")){
				//获取自定义的sql语句、同时给自定义变量赋值
				sql = getsqlAndSetDefinevalue(sysBxx, list, entity);
				
				//在select中增加Id、Version
				sql = GetSelectIdAndVersion(sysBxx,sql);
				
				//增加查询条件
				String whereSql = GetCondition(sysBxx, list, entity);
				if (!whereSql.equals("")){
					sql += whereSql;
				}
			}
			else{
				String selectSql = " select ";
				String fromSql = " from " + sysBxx.getBm();
				String whereSql = getWhereSql(sysBxx, list, entity);
				int iSelect = 0;
				for(SysZdxx zdxx : list ){
					if (iSelect != 0){
						selectSql += ", ";
					}
					selectSql += zdxx.getName();			
					iSelect++;	
				}		
				sql = selectSql + fromSql + whereSql;
			}
//			String funcGroupId = getGroupId(sysBxx.getBm());
//			RsFuncOptHolder.setMenuId(funcGroupId);
			List<Map<String,Object>> result ;
			if (page != null && page.getCurrent() != 0 && page.getSize() != 0){
				result = cgMapper.selectSql(page, sql);
			}else{
				result = cgMapper.selectSql(sql);
			}
			filterOracleTimestamp(result);
			return result;
		}else{
			return null;
		}	
	}
	
	private void filterOracleTimestamp(List<Map<String,Object>> list){
		for(Map<String,Object> mp : list){
			Set<Entry<String, Object>> set = mp.entrySet();
			try {
				for(Entry<String, Object> ety:set){
					String key = ety.getKey();
					Object val = ety.getValue();
					if(val instanceof oracle.sql.Datum){
						mp.put(key, ((oracle.sql.Datum)val).timestampValue());
					}else if(val instanceof oracle.sql.BLOB){
						byte[] bytes =((oracle.sql.BLOB)val).getBytes();
						mp.put(key, new String(bytes));
					}else if(val instanceof oracle.sql.CLOB){
						byte[] bytes =((oracle.sql.CLOB)val).getBytes();
						mp.put(key, new String(bytes));
					}
				}
			} catch (SQLException e) {
				logger.warn("filterOracleTimestamp", e);
			}
		}
	}
	
	//给sql语句的自定义变量赋值，返回新的sql语句
	private String getsqlAndSetDefinevalue(SysBxx sysBxx, List<SysZdxx> list, JSONObject entity){
		//获取各字段的字段类型
		Map<String, String> map = new HashMap<>();
		for(SysZdxx syszdxx: list){
			map.put(syszdxx.getName(),syszdxx.getLx());
		}
		
		String sql = sysBxx.getIsql().toLowerCase();
		if (sql.indexOf("where") >= 0){
			sql = sql.replaceAll("where", "where 1=1 and");			
		}
		else
		{
			sql = sql + " where 1=1 ";
		}
		//变量值替换
		for(String key: entity.keySet()){
			String value = entity.getString(key);
			if ((value != null) && (!value.equals(""))){		
				if (sql.indexOf("#{" + key + "}") >= 0){
					String param = "#\\{" + key.toLowerCase() + "\\}";					
					sql = sql.replaceAll(param, getParamValue(map.get(key.toLowerCase()), value));					
				}
			}
		}
		//变量值为空则去掉条件
		while (sql.indexOf("#{")>=0){
			String newSql="";
			int index = sql.indexOf("#{");
			//去掉and或or条件
			String tempsql = sql.substring(0,index);
			
			int NewIndex = -1;
			
			int LastOrIndex = tempsql.lastIndexOf("or");
			NewIndex = Math.max(LastOrIndex, NewIndex);
			
			int LastAndIndex = tempsql.lastIndexOf("and");
			NewIndex = Math.max(LastAndIndex, NewIndex);
			
			//int LastOnIndex = tempsql.lastIndexOf("on");
			//NewIndex = Math.max(LastOnIndex, NewIndex);
			if (NewIndex >= 0){
				newSql += tempsql.substring(0,NewIndex);
				//去掉变量条件
				tempsql = sql.substring(index + 2,sql.length());
				NewIndex = tempsql.indexOf("}");
				newSql += tempsql.substring(NewIndex + 1,tempsql.length());	
			}
			else{
				break;
			}
			sql = newSql;
		}
		return sql;
	}
		
	//给sql语句的select中增加Id、Version，返回新的sql语句
	private String GetSelectIdAndVersion(SysBxx sysBxx, String Oldsql){
		String newsql = Oldsql;
		int selectindex = newsql.toUpperCase().indexOf("SELECT", 0);
		int fromindex = newsql.toUpperCase().indexOf("FROM",0);
		if (fromindex > selectindex + 6){
			String fieldlist = newsql.substring(selectindex + 6, fromindex);
			String[] fieldarray = fieldlist.split(",");
			Map<String,String> fieldmap = new HashMap<String, String>();
			for(String field: fieldarray){
				fieldmap.put(field.trim().toUpperCase(), field.trim().toUpperCase());
			}
			
			//
			String addField = "";
			
			//ID
			if (!fieldmap.containsKey(getField(sysBxx.getBm(), "id"))){
				fieldmap.put(getField(sysBxx.getBm(), "id"),getField(sysBxx.getBm(), "id"));
			}
			
			//VERSION
			if (!fieldmap.containsKey(getField(sysBxx.getBm(), "version"))){
				fieldmap.put(getField(sysBxx.getBm(), "version"),getField(sysBxx.getBm(), "version"));
			}
			
			for(String s: fieldmap.keySet()){
				if (addField.equals("")){
					addField = s + " ";
				}
				else{
					addField += "," + s + " ";						
				}
			}
			
			if (!addField.equals("")){
				newsql = "select " + addField + newsql.substring(fromindex, newsql.length());
			}	
		}		
		return newsql;
	}
	
	private String GetCondition(SysBxx sysBxx, List<SysZdxx> list, JSONObject entity){
		String whereSql = "";
		String conditionValue = "";
		for(SysZdxx zdxx : list ){
			if ((zdxx.getCondflag() != null) && (!zdxx.getCondflag().equals(""))){
				continue;
			}
			if ("range".equals(zdxx.getCxms())){
				conditionValue = entity.getString("(B)"+zdxx.getName());
				//是否作为查询条件列以及值不为空
				if (!valueIsNull(zdxx.getLx(),conditionValue))
					whereSql += " and " + zdxx.getName() + " >= " + getParamValue(zdxx.getLx(), conditionValue);
				conditionValue = entity.getString("(E)"+zdxx.getName());
				//是否作为查询条件列以及值不为空
				if (!valueIsNull(zdxx.getLx(), conditionValue))
					whereSql += " and " + zdxx.getName() + " <= " + getParamValue(zdxx.getLx(), conditionValue);
			}else{
				conditionValue = entity.getString(zdxx.getName());
				//是否作为查询条件列以及值不为空
				if (!valueIsNull(zdxx.getLx(), conditionValue)){
					if ((CGConstants.ColTypes.string.equals(zdxx.getLx()) || CGConstants.ColTypes.text.equals(zdxx.getLx())) &&
							(CGConstants.InputTypes.text.equals(zdxx.getXslx()) || CGConstants.InputTypes.textarea.equals(zdxx.getXslx()))){
						whereSql += " and " + zdxx.getName() + " like '%" + conditionValue + "%'";
					}else{
						whereSql += " and " + zdxx.getName() + " = " + getParamValue(zdxx.getLx(), conditionValue);
					}
				}
					
			}
		}
		return whereSql;
	}
	
	@Override
	public int moduleGetRecordCount(JSONObject entity, String bid){
		SysBxx sysBxx = getBxx(bid);
		List<SysZdxx> list = getZdxxList(bid);
		if ((sysBxx != null) && (list != null)) {
			String selectSql = " select count(*) as RECORDCOUNT ";
			String fromSql = " from " + sysBxx.getBm();
			String whereSql = getWhereSql(sysBxx, list, entity);
			String sql = selectSql + fromSql + whereSql;
			List<Map<String,Object>> record = new ArrayList<>();
			record = cgMapper.selectSql(sql);
			if (record != null && record.size() > 0){
				return Integer.parseInt(record.get(0).get("RECORDCOUNT").toString());		
			}else{
				return 0;
			}
		}else{
			return 0;
		}	
	}
	
	private boolean isLiteral(char ch){
		return (ch == '\'' || ch == '"' || ch == '`');
	}
	
	private String getParamSql(String sql, List<String> result, Object[] params) {
		String paramSql = "";
		int curPos = 0;
		Integer paramCount = 0;
		char curChar;
		boolean literal = false;
		while (curPos < sql.length()) {
			curChar = sql.charAt(curPos);
			if ((curChar == '#') && (!literal) && 
				(curPos + 1 < sql.length()) && (sql.charAt(curPos + 1) == '{')) {
				String paramName = "";
				while (literal || curChar != '}') {
					if (curPos < sql.length()) {
						curChar = sql.charAt(curPos);
					} else {
						curChar = ' ';
						break;
					}
					if (isLiteral(curChar)) {
						literal = !literal;
					}
					if (curChar != '{' && curChar != '}' && curChar != '#'){
						paramName = paramName + String.valueOf(curChar);
					}
					curPos++;
				}
				if (curPos < sql.length()){
					curChar = sql.charAt(curPos);
				}else{
					curChar = ' ';
				}
				//paramSql += " {" + paramCount.toString() + "} ";
				if (result != null)
					result.add(paramName);
				if (params != null && params.length > paramCount){
					paramSql += params[paramCount].toString();
				}
				paramCount++;
			} else if ((curChar == '#') && (!literal) && (curPos + 1 < sql.length())
					&& ((sql.charAt(curPos + 1) == '#'))) {
				curPos++;
			} else if (isLiteral(curChar)) {
				literal = !literal;
			} 
			paramSql += String.valueOf(curChar);
			curPos++;
		}
		return paramSql;
	}
	
	private List<String> getSqlListBySplit(String s, char split) {
		List<String> result = new ArrayList<String>();
		int curPos = 0;
		char curChar;
		boolean literal = false;
		String sql = "";
		s += split;
		while (curPos < s.length()) {
			curChar = s.charAt(curPos);
			if ((curChar == split) && (!literal)) {
				if (!sql.equals("")){
					result.add(sql);
					sql = "";
				}
			}else {
				if (curChar == '\'') {
					literal = !literal;
				} 
				sql += String.valueOf(curChar);
			}
			curPos++;
		}
		return result;
	}
	
	@Transactional
	private boolean doExecuteZqsql(SysBxx sysBxx, List<SysZdxx> zdxxList, 
			List<String> sqlList, Map<String,Object> entityResult){
		String paramField;
		String paramSql;
		for(String sql : sqlList){
			if (!sql.equals("")){
				List<String> sqlParams = new ArrayList<>(); 
				paramSql = getParamSql(sql, sqlParams, null);
				if (sqlParams.size() > 0){
					Object[] params = new String[sqlParams.size()];
					for(int i = 0; i < sqlParams.size(); i++){
						paramField = "";
						if (sqlParams.get(i).equals("UUID")){
							params[i] = "'"+RsIDGenerator.init().serialNum(SerialNumStrategy.UUID).toString()+"'";
						}else{
							if ("id".equals(sqlParams.get(i).toLowerCase()))
								paramField = getField(sysBxx.getBm(), sqlParams.get(i));
							else
								paramField = sqlParams.get(i).toUpperCase();
							for(SysZdxx zdxx : zdxxList){
								if (zdxx.getName().equals(paramField)){
									//获取值
									String value;
									if (entityResult.containsKey(paramField))
									{
										value = entityResult.get(paramField).toString();										
									}
									else
									{
										value = "";
									}
									params[i] = getParamValue(zdxx.getLx(), value);
									break;
								}
							}
							
						}
					}
					paramSql = getParamSql(sql, null, params);
				}	
				try{
					cgMapper.executSql(paramSql);
				}catch(Exception e){
					//事务回滚
					e.printStackTrace();
				}
				
			}
		}
		return true;
	}	
	
	private List<String> getZqsqlList(String bid, String code){
		SysZqsql param = new SysZqsql();
		param.setBdid(bid);
		param.setCode(code);
		param.setDeleted(Constants.DelInd.FALSE);
		List<SysZqsql> zqsql = generalMapper.selectList(new RsEntityWrapper<>(param));
		if (zqsql.size() > 0){
			String sqlText = new String(zqsql.get(0).getZqsql());
			return getSqlListBySplit(sqlText, ';');
		}else{
			return null;
		}		
	}
	
	public boolean executeZqsql(String bid, String code, JSONObject record){
		boolean result = false;
		JSONObject param = new JSONObject();
		List<SysZdxx> zdxxList = getZdxxList(bid);
		SysBxx sysBxx = getBxx(bid);
		String key = getField(sysBxx.getBm(),"id");
		
		//获取记录的关键字
		param.put(key, record.getString(key));
		List<Map<String,Object>> resultList = moduleGetList(param, bid, null);
		
		if (resultList != null && resultList.size() == 1){
			result = true;
			List<String> sqlList = getZqsqlList(bid, code);
			if (sqlList != null && sqlList.size() > 0 && resultList.size() == 1){
				if (resultList.size() > 0){
					Map<String, Object> recordMap = resultList.get(0);
					//将前台传过来的值替换已有的值
					for(String FieldName:record.keySet()){
						recordMap.put(FieldName, record.getString(FieldName));
					}
					//循环record,更新 recordMap
					result = doExecuteZqsql(sysBxx, zdxxList, sqlList, recordMap);
				}
			}
		}else{
			if (resultList == null){
				SetReturnMessage("无法执行操作，请选择一条数据！");
			}else if (resultList.size() == 0){
				SetReturnMessage("无法执行操作，请选择一条合法的数据！");
			}else if (resultList.size() > 1){
				SetReturnMessage("无法执行操作，请选择一条合法的数据！");
			}
			
		}
		return result;
	}
	
	@Override
	public String getReturnMessage(){
		if (returnMessage != null){
			return returnMessage.get();
		}else{
			return "";
		}
		
	}

	@Override
	public List<Map<String, Object>> moduleQueryRelationTable(JSONObject entity, String sourceTable, String targetTable) {
		//查询主表当前选中记录
		List<Map<String,Object>> maserRecords = moduleGetList(entity, sourceTable, null);
		if (maserRecords.size() > 0){
			SysBxx masterBxx = getBxx(sourceTable);
			List<SysZdxx> zdxxList = getZdxxList(targetTable);
			JSONObject param = new JSONObject();
			for(SysZdxx zdxx : zdxxList){
				if (masterBxx.getBm().equals(zdxx.getZdbm())){
					param.put(zdxx.getName(), maserRecords.get(0).get(getField(zdxx.getZdbm(), zdxx.getZdzd())));
				}
			}
			return moduleGetList(param, targetTable, null);
		}else{
			return null;
		}
		
	}
	
}
