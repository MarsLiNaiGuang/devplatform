package com.rs.devplatform.test;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
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
import com.rs.devplatform.persistent.SysPj;
import com.rs.devplatform.persistent.SysPj2res;
import com.rs.devplatform.persistent.SysPj2u;
import com.rs.devplatform.test.base.TestBase;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.entity.persistent.SysUsers;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ManagerCtrlTest extends TestBase {
	
	String projectId;
	int version;
	
	private static final String basePath = "/manager";
	
	@Test
	public void testUpdate(){
		Object[] array = ProjectCtrlTest.addRandom();
		String pjId = (String) array[0];
		JSONObject parm = new JSONObject();
		String pjName = "ju-pj-"+new Random().nextInt()%10000;
//		parm.put("id", array[0]);
		parm.put("name", pjName);
		parm.put("version", (int)array[2]);
		ValidatableResponse response = given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().put(basePath+"/project/"+array[0])
	        .then()
	        .statusCode(SUCCESS);
		JSONObject jsonReturn = JSONObject.parseObject(response.extract().asString());
		
		ProjectCtrlTest.genDB(parm);
		parm.put("version", jsonReturn.getInteger("version"));
		response = given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().put(basePath+"/project/"+array[0])
	        .then()
	        .statusCode(SUCCESS);
		jsonReturn = JSONObject.parseObject(response.extract().asString());
		System.err.println(jsonReturn);
		deleteProject_res(pjId);
	}
	
	private void deleteProject_res(String pjId){
		SysPj pj = new SysPj();
		pj.setId(pjId);
		deleteData(pj);
		SysPj2res res = new SysPj2res();
		res.setPjId(pjId);
		deleteData(res);
		SysPj2u user = new SysPj2u();
		user.setPjId(pjId);
		List<SysPj2u> userList = generalMapper.selectList(new RsEntityWrapper<>(user));
		List<String> userIdList = new ArrayList<>();
		if(userList!=null && !userList.isEmpty()){
			userList.stream().forEach((x)->{
				userIdList.add(x.getUserId());
			});
			generalMapper.deleteBatchIds(userIdList, SysUsers.class);
		}
		deleteData(user);
	}
	
	@Test
	public void testAssignerList(){
		String[] user1 = UserCtrlTest.addRandom(null);
		String[] user2 = UserCtrlTest.addRandom(null);
		String[] user3 = UserCtrlTest.addRandom(null);
		String userId1 = user1[0];
		String userId2 = user2[0];
		String userId3 = user3[0];
		
		Object[] pj = ProjectCtrlTest.addRandom();
		String pjId = (String)pj[0];
		addUser4Pj(pjId, userId1,userId2,userId3);
		String url = basePath+"/project/"+pjId+"/users";
		System.err.println("getUsers:"+httpGet(url).toString());
		url = url+"?userType=PM";
		System.err.println("getUser for PM:"+httpGet(url).toString());
		generalMapper.deleteBatchIds(Arrays.asList(userId1,userId2,userId3), SysUsers.class);
		generalMapper.deleteById(pjId, SysPj.class);
	}
	
	@Test
	public void testUpdateAssigner(){
		String[] user1 = UserCtrlTest.addRandom(null);
		String[] user2 = UserCtrlTest.addRandom(null);
		String[] user3 = UserCtrlTest.addRandom(null);
		String userId1 = user1[0];
		String userId2 = user2[0];
		String userId3 = user3[0];
		String[] userIds = new String[]{userId1,userId2,userId3};
		
		Object[] pj = ProjectCtrlTest.addRandom();
		String pjId = (String) pj[0];
		
		JSONObject parm = new JSONObject();
		JSONArray users = new JSONArray(3);
		for(int i=0;i<3;++i){
			JSONObject user = new JSONObject();
			user.put(PARAM.ID, userIds[i]);
			user.put(PARAM.USER_TYPE,  i%2==0?"TM":"PM");
			users.add(user);
		}
		parm.put("users", users);
		String url = basePath+"/project/"+pjId+"/users";
		httpPost(url, parm);
		JSONObject result = httpGet(url);
		Assert.assertEquals(3, result.getJSONArray(RECORDS).size());
		System.err.println("before update:"+result.toJSONString());
		
		String userId4 = UserCtrlTest.addRandom(null)[0];
		users.clear();
		JSONObject user1js = new JSONObject();
		user1js.put(PARAM.ID, userId4);
		user1js.put(PARAM.USER_TYPE, "TM");
		users.add(user1js);
		user1js = new JSONObject();
		user1js.put(PARAM.ID, userId1);
		user1js.put(PARAM.USER_TYPE, "PM");
		users.add(user1js);
		parm.put("users", users);
		httpPost(url, parm);
		result = httpGet(url);
		Assert.assertEquals(2, result.getJSONArray(RECORDS).size());
		System.err.println("after update:"+result.toJSONString());
	}
	
	@Test
	public void testPjList(){
		JSONObject parm = new JSONObject();
		parm.put("id", "1505dc9adb5541e5b641f0a09affe8e8");
		ValidatableResponse response = given().contentType("application/json")
				.request().body(parm.toJSONString()).header("userId", "088b1c492e584cda89950de98965fdbb")
				.when().post(basePath+"/projects")
				.then()
				.statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toString());
	}
	
	private void addUser4Pj(String pjId, String... userIds){
		if(userIds!=null &&userIds.length!=0){
			JSONObject parm = new JSONObject();
			JSONArray users = new JSONArray(userIds.length);
			for(int i=0;i<userIds.length;++i){
				JSONObject user = new JSONObject();
				user.put(PARAM.ID, userIds[i]);
				user.put(PARAM.USER_TYPE,  i%2==0?"TM":"PM");
				users.add(user);
			}
			parm.put("users", users);
			given().contentType("application/json")
					.request().body(parm.toJSONString())
					.when().post(basePath+"/project/"+pjId+"/users")
					.then()
					.statusCode(SUCCESS);
			
			users = new JSONArray(userIds.length);
			for(int i=0;i<userIds.length;++i){
				JSONObject user = new JSONObject();
				user.put(PARAM.ID, userIds[i]);
				user.put(PARAM.USER_TYPE,  "PM");
				users.add(user);
			}
			parm.put("users", users);
			given().contentType("application/json")
					.request().body(parm.toJSONString())
					.when().post(basePath+"/project/"+pjId+"/users")
					.then()
					.statusCode(SUCCESS);
			
		}
	}
	@Test
	public void addUser4Project(){
		String[] user1 = UserCtrlTest.addRandom(null);
		String[] user2 = UserCtrlTest.addRandom(null);
		String[] user3 = UserCtrlTest.addRandom(null);
		String userId1 = user1[0];
		String userId2 = user2[0];
		String userId3 = user3[0];
		
		Object[] pj = ProjectCtrlTest.addRandom();
		String pjId = (String) pj[0];
		addUser4Pj(pjId, userId1, userId2, userId3);
	}
	
	@Test
	public void deleteUser4PjTest(){
		String[] user1 = UserCtrlTest.addRandom(null);
		String[] user2 = UserCtrlTest.addRandom(null);
		String[] user3 = UserCtrlTest.addRandom(null);
		String userId1 = user1[0];
		String userId2 = user2[0];
		String userId3 = user3[0];
		
		Object[] pj = ProjectCtrlTest.addRandom();
		String pjId = (String) pj[0];
		String url = basePath+"/project/"+pjId+"/users";
		
		addUser4Pj(pjId, userId1, userId2, userId3);
		Assert.assertEquals(3, httpGet(url).getJSONArray(RECORDS).size());
		
		JSONObject parm = new JSONObject();
		JSONArray users = new JSONArray();
		JSONObject user = new JSONObject();
		user.put(PARAM.ID, userId3);
		user.put(PARAM.USER_TYPE,  "PM");
		users.add(user);
		user = new JSONObject();
		user.put(PARAM.ID, userId2);
		user.put(PARAM.USER_TYPE,  "TM");
		users.add(user);
		parm.put("users", users);
		given().contentType("application/json")
			.request().body(parm.toJSONString())//.header("userId", "31b52ff88d154118bb1495a236c3c520")
			.when().post(url)
			.then()
			.statusCode(SUCCESS);
		
		Assert.assertEquals(2, httpGet(url).getJSONArray(RECORDS).size());
		
	}
	
	@Test
	public void testInitDB(){
		Object[] arr = ProjectCtrlTest.addRandom();
		String pjId = (String) arr[0];
		Integer version = (Integer) arr[2];
		String url = basePath+"/project/"+pjId+"/init";
		JSONObject parm = new JSONObject();
		parm.put("version", version);
		httpPost(url,parm);
	}
	
}
