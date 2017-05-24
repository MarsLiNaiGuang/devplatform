package com.rs.devplatform.test;

import static com.jayway.restassured.RestAssured.given;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.restassured.response.ValidatableResponse;
import com.rs.devplatform.DevPlatformApp;
import com.rs.devplatform.test.base.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CGTest extends TestBase {
	
	private static final String basePath = "/cg";
	
	@Test
	public void zqjsAdd(){
		JSONObject parm = new JSONObject();
		parm.put("bdid", "1");
		parm.put("js", "js");
		parm.put("jslx", "form");
		parm.put("nr", "nr");
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().post(basePath+"/form/js")
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println("id="+json.getString("id"));
	}
	
	private String getZqjsId(){
		JSONObject parm = new JSONObject();
		parm.put("jslx", "form");
		String bid = "1"; 
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().post(basePath+"/form/js/getlist/"+bid)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		JSONArray records = json.getJSONArray(RECORDS);
		if ( records.size() > 0 ) {
			return records.getJSONObject(0).getString("id");
		}else{
			return "";
		}
	}
	
	@Test
	public void zqjsGetList(){
		JSONObject parm = new JSONObject();
		parm.put("jslx", "form");
		String bid = "1";
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().post(basePath+"/form/js/getlist/"+bid)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void zqjsUpdate(){
		String id = getZqjsId();
		JSONObject parm = new JSONObject();
		parm.put("jslx", "form");
		parm.put("bdid", "1");
		parm.put("nr", "unr");
		parm.put("js", "12313");
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().put(basePath+"/form/js/"+id)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void zqjsDelete(){
		String id = getZqjsId();
		ValidatableResponse response = given().contentType("application/json")
		        .request()
		        .when().delete(basePath+"/form/js/"+id)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void custbtAdd(){
		JSONObject parm = new JSONObject();
		parm.put("code", "1");
		parm.put("icon", "icon1");
		parm.put("name", "insert");
		parm.put("zt", "zt");
		parm.put("ys", "zt");
		parm.put("exp", new Date());
		parm.put("bdid", "1");
		parm.put("czlx", "js");
		parm.put("xh", 1);
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().post(basePath+"/form/custbt")
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println("id="+json.getString("id"));
	}
	
	private String getCustbtId(){
		JSONObject parm = new JSONObject();
		String bid = "1"; 
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().post(basePath+"/form/custbt/getlist/"+bid)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		JSONArray records = json.getJSONArray(RECORDS);
		if ( records.size() > 0 ) {
			return records.getJSONObject(0).getString("id");
		}else{
			return "1";
		}
	}
	
	@Test
	public void custbtGetList(){
		String bid = "1";
		ValidatableResponse response = given().contentType("application/json")
		        .request()
		        .when().post(basePath+"/form/custbt/getlist/"+bid)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void custbtUpdate(){
		String id = getCustbtId();
		JSONObject parm = new JSONObject();
		parm.put("code", "1");
		parm.put("icon", "icon1");
		parm.put("name", "update");
		parm.put("zt", "zt");
		parm.put("ys", "ys");
		parm.put("exp", new Date());
		parm.put("bdid", "1");
		parm.put("czlx", "js");
		parm.put("xh", 1);
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().put(basePath+"/form/custbt/"+id)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void custbtDelete(){
		JSONArray parm = new JSONArray();
		parm.add(getCustbtId());
		System.out.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().delete(basePath+"/form/custbt")
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void zqsqlAdd(){
		JSONObject parm = new JSONObject();
		parm.put("bdid", "c35e5a1bf0504495aaca3d07fce977aa");
		parm.put("sql", "update BG_TEST set test_name = #{sex}");
		parm.put("code", "add");
		parm.put("nr", "nr");
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().post(basePath+"/form/sql")
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println("id="+json.getString("id"));
	}
	
	private String getZqsqlId(){
		JSONObject parm = new JSONObject();
		String bid = "c35e5a1bf0504495aaca3d07fce977aa"; 
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().post(basePath+"/form/sql/getlist/"+bid)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		JSONArray records = json.getJSONArray(RECORDS);
		if ( records.size() > 0 ) {
			return records.getJSONObject(0).getString("id");
		}else{
			return "";
		}
	}
	
	@Test
	public void zqsqlGetList(){
		JSONObject parm = new JSONObject();
		parm.put("code", "add");
		String bid = "1";
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().post(basePath+"/form/sql/getlist/"+bid)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void zqsqlUpdate(){
		String id = getZqsqlId();
		JSONObject parm = new JSONObject();
		parm.put("code", "update");
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
		        .request().body(parm.toJSONString())
		        .when().put(basePath+"/form/sql/"+id)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void zqsqlDelete(){
		String id = getZqsqlId();
		ValidatableResponse response = given().contentType("application/json")
		        .request()
		        .when().delete(basePath+"/form/sql/"+id)
		        .then()
		        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toJSONString());
	}
}
