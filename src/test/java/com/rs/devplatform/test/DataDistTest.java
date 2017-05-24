package com.rs.devplatform.test;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Equals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.restassured.response.ValidatableResponse;
import com.rs.devplatform.DevPlatformApp;
import com.rs.devplatform.persistent.SysCdtype;
import com.rs.devplatform.persistent.SysCdval;
import com.rs.devplatform.test.base.TestBase;
import com.rs.framework.common.controller.base.BaseController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class DataDistTest extends TestBase {
	
	private static final String basePath = "/admin/datadict";
	
	@Test
	public void addType(){
		SysCdtype type = addCodeType();
		
		JSONObject parm = new JSONObject();
		parm.put("name", type.getName());
		parm.put("cdtype", type.getCdtype());
		given().contentType("application/json")
			.request().body(parm.toJSONString())
			.when().post(basePath)
			.then()
			.statusCode(DUPLICATE);
		
		deleteData(type);
	}
	
	@Test
	public void updateType(){
		SysCdtype type = addCodeType();
		
		JSONObject parm = new JSONObject();
		parm.put("name", type.getName()+"_upd");
		parm.put(VERSION, type.getVersion());
		given().contentType("application/json")
			.request().body(parm.toJSONString())
			.when().put(basePath+"/"+type.getId())
			.then()
			.statusCode(SUCCESS);
		
		given().contentType("application/json")
			.request()
			.when().get(basePath+"/"+type.getId())
			.then()
			.statusCode(SUCCESS)
			.body(VERSION, new Equals(2))
			.body("name", new Equals(type.getName()+"_upd"));
		
		parm.put(VERSION, type.getVersion());
			given().contentType("application/json")
			.request().body(parm.toJSONString())
			.when().put(basePath+"/"+type.getId())
			.then()
			.statusCode(MODIFIED);
			
		type.setName(null);
		type.setVersion(null);
		deleteData(type);
	}
	
	@Test
	public void deleteType(){
		SysCdtype type = addCodeType();
		JSONObject parm = new JSONObject();
		parm.put(VERSION, type.getVersion());
		given().contentType("application/json")
        .request().body(parm.toJSONString())
        .when().delete(basePath+"/"+type.getId())
        .then()
        .statusCode(SUCCESS);
		
		type.setVersion(null);
		type.setName(null);
		deleteData(type);
		
		type = addCodeType();
		parm = new JSONObject();
		parm.put(VERSION, type.getVersion()+2);
		given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().delete(basePath+"/"+type.getId())
	        .then()
	        .statusCode(MODIFIED);
		
		type.setVersion(null);
		type.setName(null);
		deleteData(type);
	}
	
	@Test
	public void getTypeSingle(){
		SysCdtype type = addCodeType();
		List<String> valIds = addCodeValues(type.getId());
		
		ValidatableResponse response = given().contentType("application/json")
	        .request()
	        .when().get(basePath+"/"+type.getId())
	        .then()
	        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json);
		
		deleteData(type);
		deleteBatchIds(valIds, SysCdval.class);
	}
	
	@Test
	public void listType(){
		JSONObject parm = new JSONObject();
		parm.put(BaseController.CURRENT, 1);
		parm.put(BaseController.SIZE, 5);
		parm.put("name", "types");
		JSONObject json = httpPost(basePath+"/list", parm);
		System.err.println(json);
		Assert.assertEquals(5, json.getJSONArray(RECORDS).size());
	}
	
	@Test
	public void addValue(){
		SysCdtype type = addCodeType();
		List<String> valIds = addCodeValues(type.getId());
		Assert.assertNotNull(valIds);
		
		deleteData(type);
		deleteBatchIds(valIds, SysCdval.class);
	}
	@Test
	public void updateValue(){
		SysCdtype type = addCodeType();
		List<String> valIds = addCodeValues(type.getId());
		JSONObject parm = new JSONObject();
		parm.put("cdval", "a");
		parm.put("cddescp", "updated");
		given().contentType("application/json")
        .request().body(parm.toJSONString())
        .when().put(basePath+"/cdval/"+valIds.get(0))
        .then()
        .statusCode(SUCCESS);
		
		deleteData(type);
		deleteBatchIds(valIds, SysCdval.class);
	}
	@Test
	public void deleteValue(){
		SysCdtype type = addCodeType();
		List<String> valIds = addCodeValues(type.getId());
		given().contentType("application/json")
        .request()
        .when().delete(basePath+"/cdval/"+valIds.get(0))
        .then()
        .statusCode(SUCCESS);
		
		given().contentType("application/json")
		.request()
		.when().get(basePath+"/"+type.getId())
		.then()
		.statusCode(SUCCESS);
		
		deleteData(type);
		deleteBatchIds(valIds, SysCdval.class);
	}
	@Test
	public void listValue(){
		given().contentType("application/json")
		.request().body(new JSONObject())
		.when().post(basePath+"/list")
		.then()
		.statusCode(SUCCESS);
	}

	
	private SysCdtype addCodeType(){
		JSONObject parm = new JSONObject();
		int randomInt = +new Random().nextInt()%10000;
		String name = "member types"+randomInt;
		String cdtype = "ju_member_type"+randomInt;
		parm.put("name", name);
		parm.put("cdtype", cdtype);
		ValidatableResponse response = given().contentType("application/json")
        .request().body(parm.toJSONString())
        .when().post(basePath)
        .then()
        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		String typeId = json.getString(ID);
		Integer version = json.getInteger(VERSION);
		SysCdtype type = new SysCdtype();
		type.setId(typeId);
		type.setVersion(version);
		type.setName(name);
		type.setCdtype(cdtype);
		return type;
	}
	
	private List<String> addCodeValues(String typeId){
		JSONObject parm = new JSONObject();
		JSONArray cdvalues = new JSONArray();
		JSONObject codeval = new JSONObject();
		codeval.put("cdval", "1");
		codeval.put("cddescp", "value1");
		cdvalues.add(codeval);
		codeval = new JSONObject();
		codeval.put("cdval", "2");
		codeval.put("cddescp", "value2");
		cdvalues.add(codeval);
		codeval = new JSONObject();
		codeval.put("cdval", "3");
		codeval.put("cddescp", "value3");
		cdvalues.add(codeval);
		parm.put("cdvals", cdvalues);
		ValidatableResponse response = given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().post(basePath+"/"+typeId)
	        .then()
	        .statusCode(SUCCESS);
		JSONObject result = JSONObject.parseObject(response.extract().asString());
		JSONArray array = result.getJSONArray("cdvals");
		Assert.assertNotNull(array);
		List<String> codevalIDs = new ArrayList<String>(array.size());
		for(int i=0;i<array.size();++i){
			codevalIDs.add(array.getJSONObject(i).getString(ID));
		}
		return codevalIDs;
	}
}
