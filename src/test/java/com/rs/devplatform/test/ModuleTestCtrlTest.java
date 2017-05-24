package com.rs.devplatform.test;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.restassured.response.ValidatableResponse;
import com.rs.devplatform.DevPlatformApp;
import com.rs.devplatform.common.CGConstants;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.persistent.mapper.CGMapper;
import com.rs.devplatform.test.base.TestBase;
import com.rs.devplatform.vo.common.RsCommonResponse;
import com.rs.framework.common.utils.RsDateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ModuleTestCtrlTest extends TestBase {
	
	@Autowired
	CGMapper cgMapper;
	
	private static final String basePath = "/admin/moduleTest";
	
	@Test
	public void getForm(){
		String id = getId();
		ValidatableResponse response = given().contentType("application/json")
			.request()
			.when().get(basePath+"/getForm/"+id)
			.then()
			.statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json);
	}
	
	@Test
	public void getCondition(){
		String id = getId();
		ValidatableResponse response = given().contentType("application/json")
			.request()
			.when().get(basePath+"/getCondition/"+id)
			.then()
			.statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json);
	}
	
	@Test
	public void getToolButton(){
		String id = getId();
		ValidatableResponse response = given().contentType("application/json")
			.request()
			.when().get(basePath+"/getToolButton/"+id)
			.then()
			.statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json);
	}
	
	@Test
	public void getList(){
		String id = getId();
		ValidatableResponse response = given().contentType("application/json")
			.request()
			.when().get(basePath+"/getList/"+id)
			.then()
			.statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json);
	}
		
	@Test
	public void getEdit(){
		String id = getId();
		ValidatableResponse response = given().contentType("application/json")
			.request()
			.when().get(basePath+"/getEdit/"+id)
			.then()
			.statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json);
	}
	
	
	@SuppressWarnings("rawtypes")
	private List<SysZdxx> getEditZdxxList(String bid){
		// 获取服务端字段
		ValidatableResponse response = given().contentType("application/json").request()
									   .when()
									   .get(basePath + "/getEdit/" + bid).then().statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		List list = JSONObject.toJavaObject(json.getJSONArray(RECORDS), List.class);
		ArrayList<SysZdxx> result = new ArrayList<SysZdxx>();
		for(int i=0;i<list.size();i++){
	        json = JSONObject.parseObject(list.get(i).toString());
			result.add(JSONObject.toJavaObject(json, SysZdxx.class));
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	private List<SysZdxx> getConditionZdxxList(String bid){
		// 获取服务端字段
		ValidatableResponse response = given().contentType("application/json").request()
									   .when()
									   .get(basePath + "/getCondition/" + bid).then().statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		List list = JSONObject.toJavaObject(json.getJSONArray(RECORDS), List.class);
		ArrayList<SysZdxx> result = new ArrayList<SysZdxx>();
		for(int i=0;i<list.size();i++){
	        json = JSONObject.parseObject(list.get(i).toString());
			result.add(JSONObject.toJavaObject(json, SysZdxx.class));
		}
		return result;
	}
	
	private SysBxx getBxx(String bid){
		ValidatableResponse response = given().contentType("application/json").request().when()
				.get(basePath + "/getForm/" + bid).then().statusCode(SUCCESS);
		JSONObject bxxJson = JSONObject.parseObject(response.extract().asString());
		return bxxJson.toJavaObject(SysBxx.class);
	}
	
	private JSONObject addRecord4UserDefind(String bid){
		// 获取服务端字段
		JSONObject master = new JSONObject();
		List<SysZdxx> zdxx = getEditZdxxList(bid);
		// 循环加入记录
		for (int i = 0; i < zdxx.size(); i++) {
			master.put(zdxx.get(i).getName(), getParamValue(zdxx.get(i).getLx()));
		}
        master.put("tag", "A");
		return master;
	}

	@Test
	public void moduleTestAdd(){
		String id = getId();
		SysBxx sysBxx = getBxx(id);
		String bm = sysBxx.getBm();
		JSONObject parm = new JSONObject();
		parm.put("id", id);
		parm.put("tablename", bm);
		parm.put("master", addRecord4UserDefind(id));
		System.err.println(parm.toJSONString());
		RsCommonResponse resp = parm.toJavaObject(RsCommonResponse.class);
		System.err.println(resp.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
				.request()
				.body(parm.toJSONString()).when()
				.post(basePath + "/add/")
				.then().statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json);
	}
	
	@Test
	public void masterDetailTestAdd(){
		String id = getMasterId();
		SysBxx sysBxx = getBxx(id);
		String bm = sysBxx.getBm();
		JSONObject parm = new JSONObject();
		parm.put("id", id);
		parm.put("tablename", bm);
		parm.put("master", addRecord4UserDefind(id));
		String detailId = getDetailId(bm);
		if (!"".equals(detailId)){
			JSONObject json = new JSONObject();
			json.put("id", detailId);
			ArrayList<JSONObject> list = new ArrayList<>();
			list.add(addRecord4UserDefind(detailId));
			list.add(addRecord4UserDefind(detailId));
			list.add(addRecord4UserDefind(detailId));
			json.put("records", list.toArray());
			ArrayList<JSONObject> detailList = new ArrayList<>();
			detailList.add(json);
			parm.put("detail", detailList.toArray());
		}
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
				.request()
				.body(parm.toJSONString()).when()
				.post(basePath + "/add/")
				.then().statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json);
	}
	
	private String getField(String table, String field){
		return table.substring(table.indexOf("_")+1) + "_" + field;
	}
	
	private String getParamValue(String lx){
		if (CGConstants.ColTypes.string.equals(lx) || CGConstants.ColTypes.text.equals(lx)) {
			return "test";
		} else if (CGConstants.ColTypes.int_.equals(lx) || CGConstants.ColTypes.double_.equals(lx)
				|| CGConstants.ColTypes.decimal.equals(lx)) {
			return "100";
		} else if (CGConstants.ColTypes.date.equals(lx) || CGConstants.ColTypes.datetime.equals(lx)) {
			return RsDateUtil.getCurrentDateStr();
		} else {
			return "";
		}		
	}
	
	@Test
	public void moduleTestUpdate(){
		String id = getId();
		JSONObject reJson = getModuleData(id);
		JSONArray records = reJson.getJSONArray("rows");
		if (records.size() > 0){
			JSONObject parm = new JSONObject();
			// 获取服务端表信息
			SysBxx sysBxx = getBxx(id);
			String bm = sysBxx.getBm();
			parm.put("id", id);
			parm.put("tablename", bm);
			// 获取服务端字段
			List<SysZdxx> zdxx = getEditZdxxList(id);
			String ywid = getField(bm, ID);
			String verion = getField(bm, "version");
			// 循环加入记录
			JSONObject master = new JSONObject();
			for (int i = 0; i < zdxx.size(); i++) {
				master.put(zdxx.get(i).getName(), getParamValue(zdxx.get(i).getLx()));
			}
			master.put(ywid, records.getJSONObject(0).getString(ywid));
			master.put(verion, records.getJSONObject(0).getInteger(verion));
			parm.put(RsCommonResponse.MASTER, master);
			System.err.println(parm.toJSONString());
			ValidatableResponse response = given().contentType("application/json")
											.request()
											.body(parm.toJSONString()).when()
											.put(basePath + "/update/")
											.then().statusCode(SUCCESS);
			JSONObject json = JSONObject.parseObject(response.extract().asString());
			System.err.println(json);
		}else{
			System.err.println("无可测试记录！");
		}
	}
	
	@Test
	public void moduleTestDelete(){
//		String id = getId();
//		JSONObject reJson = getModuleData(id);
//		JSONArray records = reJson.getJSONArray("rows");
//		if (records.size() > 0){
//			JSONObject parm = new JSONObject();
//			// 获取服务端表信息
//			SysBxx sysBxx = getBxx(id);
//			String bm = sysBxx.getBm();
//			parm.put("id", id);
//			parm.put("tablename", bm);
//			// 获取服务端字段
//			String ywid = getField(bm, ID);
//			List<String> idList = new ArrayList<String>();
//			idList.add(records.getJSONObject(0).getString(ywid));
//			JSONArray idparm = new JSONArray();
//			idparm.addAll(idList);
//			parm.put(RECORDS, idparm.toJSONString());
//			System.err.println(parm.toJSONString());
//			ValidatableResponse response = given().contentType("application/json")
//											.request().body(parm.toJSONString()).when()
//											.delete(basePath)
//											.then().statusCode(SUCCESS);
//			JSONObject json = JSONObject.parseObject(response.extract().asString());
//			System.err.println(json);
//		}
		String id = "0515ba20980e42f4b1e4d677ae981192";
		String tablename = "league";
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("tablename", tablename);
	}
	
	@Test
	public void moduleTestMasterDetailDelete(){
		String id = getMasterId();
		JSONObject reJson = getModuleData(id);
		JSONArray records = reJson.getJSONArray("rows");
		if (records.size() > 0){
			JSONObject parm = new JSONObject();
			// 获取服务端表信息
			SysBxx sysBxx = getBxx(id);
			String bm = sysBxx.getBm();
			parm.put("id", id);
			parm.put("tablename", bm);
			// 获取服务端字段
			String ywid = getField(bm, ID);
			List<String> idList = new ArrayList<String>();
			idList.add(records.getJSONObject(0).getString(ywid));
			JSONArray idparm = new JSONArray();
			idparm.addAll(idList);
			parm.put(RECORDS, idparm.toJSONString());
			System.err.println(parm.toJSONString());
			ValidatableResponse response = given().contentType("application/json")
											.request().body(parm.toJSONString()).when()
											.delete(basePath)
											.then().statusCode(SUCCESS);
			JSONObject json = JSONObject.parseObject(response.extract().asString());
			System.err.println(json);
		}
	}
	//------------------------------------
	
	public String getId(String lx, String master){
		JSONObject parm = new JSONObject();
		ValidatableResponse response = given()
				.contentType("application/json").request().body(parm.toJSONString())
				.when().post("/cg/form/list").then().statusCode(SUCCESS);
		JSONObject bxxJson = JSONObject.parseObject(response.extract().asString());
		JSONArray records = bxxJson.getJSONArray(RECORDS);
		String result = "";
		if (records.size() > 0){
			for (int i = 0; i<records.size(); i++){
			    //单表
				if (lx.equals(records.getJSONObject(i).getString("tabletype")) && lx.equals("1")){
					result = records.getJSONObject(i).getString("id"); 
					break;
			    }else if (lx.equals(records.getJSONObject(i).getString("tabletype")) && lx.equals("2")){
			    	result = records.getJSONObject(i).getString("id"); 	
			    	break;
			    }else if (lx.equals(records.getJSONObject(i).getString("tabletype")) && lx.equals("3") &&
			    		records.getJSONObject(i).getString("zb").indexOf(master)>=i){
					result = records.getJSONObject(i).getString("id"); 	
					break;
			    }
			}
			
	    };
	    return result;
	}
	
	public String getDetailTableId(String masterId){
		ValidatableResponse response = given()
				.contentType("application/json").request()
				.when().post(basePath + "/getDetailTableList/"+masterId).then().statusCode(SUCCESS);
		JSONObject bxxJson = JSONObject.parseObject(response.extract().asString());
		JSONArray records = bxxJson.getJSONArray(RECORDS);
		String result = "";
		if (records.size() > 0){
			result = records.getJSONObject(0).getString("id");
			
	    };
	    return result;
	}
	
	public String getId(){
		return getId("1", "");
	}
	
	public String getMasterId(){
		return getId("2", "");
	}
	
	public String getDetailId(String master){
		return getDetailTableId(master);
	}
	
	private String getParamValue(String lx, String value){
		if (value == null){
			if (CGConstants.ColTypes.string.equals(lx) || CGConstants.ColTypes.text.equals(lx)) {
				return "";
			} else if (CGConstants.ColTypes.int_.equals(lx) || CGConstants.ColTypes.double_.equals(lx)
					|| CGConstants.ColTypes.decimal.equals(lx)) {
				return "0";
			} else if (CGConstants.ColTypes.date.equals(lx) || CGConstants.ColTypes.datetime.equals(lx)) {
				return "null";
			} else {
				return "null";
			}
		}else{
			if (CGConstants.ColTypes.string.equals(lx) || CGConstants.ColTypes.text.equals(lx)) {
				return value ;
			} else if (CGConstants.ColTypes.int_.equals(lx) || CGConstants.ColTypes.double_.equals(lx)
					|| CGConstants.ColTypes.decimal.equals(lx)) {
				return value;
			} else if (CGConstants.ColTypes.date.equals(lx) || CGConstants.ColTypes.datetime.equals(lx)) {
				return value;
			} else {
				return "''";
			}
		}		
	}
	private JSONObject getModuleData(String tableId){
		List<SysZdxx> zdxxList = getConditionZdxxList(tableId);
		JSONObject json = new JSONObject();
		json.put("id", tableId);
		for(int i=0;i<zdxxList.size();i++){
			if ("range".equals(zdxxList.get(i).getCxms())){
				json.put("(B)"+zdxxList.get(i).getName(), getParamValue(zdxxList.get(i).getLx(), "0"));
				json.put("(E)"+zdxxList.get(i).getName(), getParamValue(zdxxList.get(i).getLx(), "10000"));
			}else{
				json.put(zdxxList.get(i).getName(), getParamValue(zdxxList.get(i).getLx(), "1"));
			}
		}
		System.err.println(json.toJSONString());
		ValidatableResponse response = given().contentType("application/json").request()
									   .body(json.toJSONString())
									   .when().post(basePath + "/query/")
									   .then().statusCode(SUCCESS);
		System.err.println(JSONObject.parseObject(response.extract().asString()));
		return JSONObject.parseObject(response.extract().asString());
	}
	
	@Test
	public void moduleTestSelect(){
		System.err.println(getModuleData(getId()));
	}
	
	@Test
	public void getZqjsList(){
		JSONObject parm = new JSONObject();
		parm.put("jslx", "form");
		String bid = "1";
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().post(basePath+"/getzqjs/"+bid)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void executZqsqList(){
		JSONObject parm = new JSONObject();
		parm.put("code", "audit");
		String bid = "46864e6d14e44f6aa6c6ae415b67a5ed";
		JSONArray records = new JSONArray();
		for(int i=0; i<3; i++){
			JSONObject record = new JSONObject();
			record.put("resume_id", i);
			record.put("resume_whr", "xyz");	
			record.put("resume_name", "test");
			records.add(record);
		}
		parm.put("rows", records);
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().post(basePath+"/sql/execute/"+bid)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void moduleTestQueryRelationTable(){
		JSONObject json = new JSONObject();
		json.put("sourceTable", "46864e6d14e44f6aa6c6ae415b67a5ed");
		json.put("targetTable", "4143ca0e5b6a496487226e95056cfa40");
		json.put("resume_id", "bd0a525b3103487ab2601f8473fe1b27");
		System.err.println(json.toJSONString());
		ValidatableResponse response = given().contentType("application/json").request()
									   .body(json.toJSONString())
									   .when().post(basePath + "/query/relationtable/")
									   .then().statusCode(SUCCESS);
		System.err.println(JSONObject.parseObject(response.extract().asString())); 
	}
}
