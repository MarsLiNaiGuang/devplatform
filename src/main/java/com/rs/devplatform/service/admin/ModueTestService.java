package com.rs.devplatform.service.admin;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.rs.devplatform.vo.common.RsCommonResponse;

public interface ModueTestService {
	
	public String getField(String table, String field);
	
	public List<Map<String,Object>> moduleGetList(JSONObject entity, String bid, Pagination page);
	
	public List<Map<String,Object>> moduleQueryRelationTable(JSONObject entity, String sourceTable, String targetTable);
	
	public int moduleGetRecordCount(JSONObject entity, String bid);
	
	public boolean moduleAdd(RsCommonResponse resp);
	
	public boolean moduleUpdate(RsCommonResponse resp);
	
	public boolean moduleDelete(String id, String bid);
	
	public boolean executeZqsql(String bid, String code, JSONObject record);
	
	public String getReturnMessage();

}
