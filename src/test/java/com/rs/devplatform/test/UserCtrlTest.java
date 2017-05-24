package com.rs.devplatform.test;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
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
public class UserCtrlTest extends TestBase {
	
	String userId;
	
	private static final String basePath = "/admin/user";
	
	@Test
	public void userTest1Add(){
		JSONObject parm = new JSONObject();
		String username = "test"+new Random().nextInt()%100000;
		parm.put("name", username);
		parm.put("nkname", "nk_"+username);
		parm.put("pwd", "123456");
		parm.put("pwdConfirm", "123456");
		parm.put("email", "test123@123.com");
//		parm.put("tel", "13313313313");
		ValidatableResponse response = given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().post(basePath)
	        .then()
	        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		userId = json.getString("id");
		System.err.println("userId="+userId);
		given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().post(basePath)
	        .then()
	        .statusCode(DUPLICATE);
		deleteUser(userId);
	}
	
	public static void deleteUser(String userId){
		List<String> idList = new ArrayList<String>(1);
		idList.add(userId);
		deleteUsers(idList);
	}
	
	public static void deleteUsers(List<String> userIds){
		JSONArray parm = new JSONArray();
		parm.addAll(userIds);
		given().contentType("application/json")
        .request().body(parm.toJSONString())
        .when().delete(basePath)
        .then();
	}
	
	public static String[] addRandom(String username){
		JSONObject parm = new JSONObject();
		if(username==null){
			username = "test"+new Random().nextInt()%10000;
		}
		parm.put("name", username);
		parm.put("nkname", "nk_"+username);
		parm.put("pwd", "123456");
		parm.put("pwdConfirm", "123456");
		parm.put("email", username+"@123.com");
		JSONObject json = httpPost(basePath, parm);
		System.err.println(json.toJSONString());
		return new String[]{json.getString("id"), username};
	}
	
	@Test
	public void userTest1AddInvalid(){
		JSONObject parm = new JSONObject();
		parm.put("name", "test123"+new Random().nextInt()%1000);
		parm.put("pwd", "1234561");
		parm.put("pwdConfirm", "123456");
		parm.put("email", "test123@123.com");
		given().contentType("application/json")
				.request().body(parm.toJSONString())
				.when().post(basePath)
				.then()
				.statusCode(BAD_REQUEST);
		parm = new JSONObject();
		parm.put("name", "test123"+new Random().nextInt()%1000);
		parm.put("pwd", "1234561");
		parm.put("pwdConfirm", "");
		parm.put("email", "test123_123.com");
		given().contentType("application/json")
				.request().body(parm.toJSONString())
				.when().post(basePath)
				.then()
				.statusCode(BAD_REQUEST);
	}
	
	@Test
	public void userTest2Update(){
		String[] array = addRandom(null);
		JSONObject parm = new JSONObject();
		parm.put("id", array[0]);
		parm.put("email", "test323_123.com");
		parm.put("tel", "66554433");
		parm.put("nkname", "三哥");
		
		given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().put(basePath+"/"+array[0])
	        .then()
	        .statusCode(BAD_REQUEST);
		
		parm.put("email", "test323@123.com");
		given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().put(basePath+"/"+array[0])
	        .then()
	        .statusCode(SUCCESS);
		deleteUser(array[0]);
	}
	
	
	@Test
	public void userTest3List(){
		int size = 5;
		JSONObject parm = new JSONObject();
		parm.put(CURRENT, 1);
		parm.put(SIZE, size);
		JSONObject json = httpPost(basePath+"/list", parm);
		System.err.println(json.toString());
		Assert.assertEquals(size, json.getJSONArray(RECORDS).size());
		
		parm.put("name", "");
		Assert.assertEquals(size, httpPost(basePath+"/list", parm).getJSONArray(RECORDS).size());
		parm.put("nkname", "ad");
		System.err.println(httpPost(basePath+"/list", parm));
	}
	
	
	@Test
	public void userTest4Delete(){
		JSONArray parm = new JSONArray();
		String[] array = addRandom(null);
		parm.add(array[0]);
		array = addRandom(null);
		parm.add(array[0]);
		httpDelete(basePath, parm);
	}

	
}
