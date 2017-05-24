package com.rs.devplatform.test;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

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
public class LoginCtrlTest extends TestBase{
	
	private final String basePath = "/loginrest";
	
	@Test
	public void loginAndCreateTest(){
		JSONObject parm = new JSONObject();
		parm.put("username", "admin");
		parm.put("password", "123456");
		parm.put("useFlag", "A");
//		given().contentType("application/json")
//	        .request().body(parm.toJSONString())
//	        .when().post(basePath)
//	        .then()
//	        .statusCode(BAD_REQUEST);
//		parm.put("password", "123456");
		ValidatableResponse response =given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().post(basePath)
	        .then()
	        .statusCode(SUCCESS)
	        ;
		String jsessionId = response.extract().cookie(JSESSIONID);
//		given().contentType("application/json")
//	        .request().cookie(JSESSIONID, jsessionId)
//	        .when().get("/admin/menu/list");
        
//		UserCtrlTest.addRandom("user1_create"+new Random().nextInt()%1000);
		List<String> idList = new ArrayList<String>(1);
		idList.add("1");
		idList.add("2");
//		idList.add("3");
		JSONArray array = new JSONArray();
		array.addAll(idList);
		given().contentType("application/json")
		.request().cookie(JSESSIONID, jsessionId)
        .body(array.toJSONString())
        .when().delete("/admin/user")
        .then();
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
        .when().delete("/admin/user")
        .then();
	}
}
