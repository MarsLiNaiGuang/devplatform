package com.rs.devplatform.test;

import static com.jayway.restassured.RestAssured.given;

import java.util.Arrays;
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
public class RoleCtrlTest extends TestBase {
	
	String roleId;
	
	private static final String basePath = "/admin/role";
	
	
	@Test
	public void roleTest1Add(){
		JSONObject parm = new JSONObject();
		parm.put("name", "ju-dept"+new Random().nextInt()%1000);
		ValidatableResponse response = given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().post(basePath)
	        .then()
	        .statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		roleId = json.getString("id");
		System.err.println("roleId="+roleId);
		deleteRole(roleId);
	}
	
	@Test
	public void roleDeleteTest(){
		deleteRole("3");
	}
	
	public static String[] addRandomRole(String roleName){
		JSONObject parm = new JSONObject();
		if(roleName==null){
			roleName = "ju-dept"+new Random().nextInt()%1000;
		}
		parm.put("name", roleName);
		ValidatableResponse response = given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().post(basePath)
	        .then();
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		return new String[]{json.getString("id"), roleName};
	}
	
	private boolean deleteRole(String roleId){
		return deleteRoles(new String[]{roleId});
	}
	
	private boolean deleteRoles(String[] roleIds){
		JSONArray parm = new JSONArray();
		for(String id:roleIds){
			parm.add(id);
		}
		given().contentType("application/json")
        .request().body(parm.toJSONString())
        .when().delete(basePath)
        .then();
		return true;
	}
	
	@Test
	public void roleTest1AddDuplicate(){
		String[] array = addRandomRole(null);
		JSONObject parm = new JSONObject();
		parm.put("name", array[1]);
		given().contentType("application/json")
				.request().body(parm.toJSONString())
				.when().post(basePath)
				.then()
				.statusCode(DUPLICATE);
		deleteRole(array[0]);
	}
	
	@Test
	public void roleTest2Update(){
		String[] array = addRandomRole(null);
		JSONObject parm = new JSONObject();
//		parm.put("id", array[0]);
		parm.put("name", "dept"+new Random().nextInt()%100);
		parm.put(VERSION, 2);
		given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().put(basePath+"/"+array[0])
	        .then()
	        .statusCode(SUCCESS);
		deleteRole(array[0]);
	}
	
	@Test
	public void roleTest2UpdateInvalid(){
		JSONObject parm = new JSONObject();
		String[] roleIds = new String[2];
		String[] array = addRandomRole(null);
		roleIds[0] = (array[0]);
		String roleName1 = array[1];
		array = addRandomRole(null);
		roleIds[1] = (array[0]);
		parm = new JSONObject();
//		parm.put("id", array[0]);
		parm.put("name", roleName1);
		given().contentType("application/json")
        .request().body(parm.toJSONString())
	        .when().put(basePath+"/"+array[0])
	        .then()
	        .statusCode(DUPLICATE);
		deleteRoles(roleIds);
	}
	
	@Test
	public void roleTest3List(){
		int size = 5;
		JSONObject parm = new JSONObject();
		parm.put("current", 1);
		parm.put("size", size);
		parm.put("name", "");
		JSONObject json = httpPost(basePath+"/list", parm);
		Assert.assertEquals(size, json.getJSONArray(RECORDS).size());
		parm.put("name", "a");
		System.err.println(httpPost(basePath+"/list", parm));
	}
	
	
	@Test
	public void roleTest4Delete(){
		JSONArray parm = new JSONArray();
		parm.add(addRandomRole(null)[0]);
		parm.add(addRandomRole(null)[0]);
		given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().delete(basePath)
	        .then()
	        .statusCode(SUCCESS);
	}
	
	@Test
	public void addUser2RoleTest(){
		String roleId = addRandomRole(null)[0];
		String userId1 = UserCtrlTest.addRandom(null)[0];
		String userId2 = UserCtrlTest.addRandom(null)[0];
		String userId3 = UserCtrlTest.addRandom(null)[0];
		
		JSONObject param = new JSONObject();
//		param.put("id", roleId);
		JSONArray userIds = new JSONArray();
		userIds.add(userId1);
		userIds.add(userId2);
		userIds.add(userId3);
		param.put("users", userIds);
		given().contentType("application/json")
	        .request().body(param.toJSONString())
	        .when().post(basePath+"/"+roleId+"/users")
	        .then()
	        .statusCode(SUCCESS);
		List<String> userIdList = Arrays.asList(userId1,userId2,userId3);
		UserCtrlTest.deleteUsers(userIdList);
		deleteRole(roleId);
	}
	
	@Test
	public void deleteUser2RoleTest(){
		String roleId = addRandomRole(null)[0];
		String userId1 = UserCtrlTest.addRandom(null)[0];
		String userId2 = UserCtrlTest.addRandom(null)[0];
		String userId3 = UserCtrlTest.addRandom(null)[0];
		
		JSONObject param = new JSONObject();
//		param.put("id", roleId);
		JSONArray userIds = new JSONArray();
		userIds.add(userId1);
		userIds.add(userId2);
		userIds.add(userId3);
		param.put("users", userIds);
		given().contentType("application/json")
		.request().body(param.toJSONString())
			.when().post(basePath+"/"+roleId+"/users")
			.then();
		//get user list for role
		given().contentType("application/json")
			.request()
			.when().get(basePath+"/"+roleId+"/userlist")
			.then()
			.statusCode(SUCCESS);
		
//		userIds.remove(0);
		given().contentType("application/json")
		.request().body(param.toJSONString())
			.when().delete(basePath+"/"+roleId+"/users")
			.then()
			.statusCode(SUCCESS);
		
		ValidatableResponse response = given().contentType("application/json")
			.request()
			.when().get(basePath+"/"+roleId+"/userlist")
			.then()
			.statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		JSONArray records = json.getJSONArray(RECORDS);
		System.err.println("***records.size="+records.size());
		Assert.assertEquals(0, records.size(), 0);
		
		List<String> userIdList = Arrays.asList(userId1,userId2,userId3);
		UserCtrlTest.deleteUsers(userIdList);
		deleteRole(roleId);
	}
	
}
