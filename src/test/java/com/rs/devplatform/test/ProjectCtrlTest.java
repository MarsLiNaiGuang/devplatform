package com.rs.devplatform.test;

import static com.jayway.restassured.RestAssured.given;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProjectCtrlTest extends TestBase {
	
	String projectId;
	int version;
	
	private static final String basePath = "/admin/project";
	
	
	@Test
	public void test1Add(){
		JSONObject parm = new JSONObject();
		parm.put("name", "ju-pj-"+new Random().nextInt()%1000);
		JSONObject json = httpPost(basePath, parm);
		projectId = json.getString("id");
		version = json.getInteger("version");
		System.err.println("pjId="+projectId);
		
		delete(projectId, version);
		
		parm.put("name", "ju-pj-"+new Random().nextInt()%1000);
		genDB(parm);
		System.err.println(parm);
		json = httpPost(basePath, parm);
		projectId = json.getString("id");
		version = json.getInteger("version");
		System.err.println("pjId="+projectId);
		delete(projectId, version);
	}
	
	@Test
	public void testGetSingle(){
		Object[] array = ProjectCtrlTest.addRandom();
		String pjId = (String) array[0];
		JSONObject json = httpGet(basePath+"/"+pjId);
		System.err.println(json);
		delete(pjId, (Integer)array[2]);
	}
	
	public static JSONObject genDB(JSONObject parm){
		JSONObject db = new JSONObject();
		db.put("dbType", "mysql");
		db.put("dbUrl", "192.168.10.64:3306/test_db");
		db.put("dbUser", "root");
		db.put("dbPwd", "root");
		parm.put("db", db);
		return parm;
	}
	
	public static Object[] addRandom(){
		JSONObject parm = new JSONObject();
		String name = "ju-pj-"+new Random().nextInt()%10000;
		parm.put("name", name);
		genDB(parm);
		System.err.println(parm.toJSONString());
		ValidatableResponse response = given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().post(basePath)
	        .then().statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		
		return new Object[]{json.getString("id"),name, json.getInteger("version")};
	}
	
	public boolean delete(String id, int version){
		/*JSONObject parm = new JSONObject();
		parm.put("id", id);
		parm.put("version", version);
		given().contentType("application/json")
        .request().body(parm.toJSONString())
        .when().delete(basePath)
        .then().body("return_code", new Equals(0));*/
		SysPj pj = new SysPj();
		pj.setId(id);
		SysPj2res res = new SysPj2res();
		res.setPjId(id);
		SysPj2u p2u = new SysPj2u();
		p2u.setPjId(id);
		deleteData(res);
		deleteData(p2u);
		deleteData(pj);
		return true;
	}
	
	@Test
	public void testAddDuplicate(){
		Object[] array = addRandom();
		JSONObject parm = new JSONObject();
		parm.put("name", array[1]);
		genDB(parm);
		given().contentType("application/json")
				.request().body(parm.toJSONString())
				.when().post(basePath)
				.then()
				.statusCode(DUPLICATE);
		delete((String)array[0], (int)array[2]);
	}
	
	@Test
	public void testUpdate(){
		Object[] array = addRandom();
		JSONObject parm = new JSONObject();
		String pjName = "ju-pj-"+new Random().nextInt()%100;
//		parm.put("id", array[0]);
		parm.put("name", pjName);
		parm.put("version", array[2]);
		ValidatableResponse response = given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().put(basePath+"/"+array[0])
	        .then()
	        .statusCode(SUCCESS);
		JSONObject jsonReturn = JSONObject.parseObject(response.extract().asString());
		
		genDB(parm);
		parm.put("version", jsonReturn.getInteger("version"));
		response = given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().put(basePath+"/"+array[0])
	        .then()
	        .statusCode(SUCCESS);
		jsonReturn = JSONObject.parseObject(response.extract().asString());
		
		Object[] array2 = addRandom();
		JSONObject parm2 = new JSONObject();
		parm2.put("id", array2[0]);
		parm2.put("name", pjName);
		parm2.put("version", (int)array2[2]);
		given().contentType("application/json")
	        .request().body(parm2.toJSONString())
	        .when().put(basePath+"/"+array2[0])
	        .then()
	        .statusCode(DUPLICATE);
		parm2.put("name",  "ju-pj-"+new Random().nextInt()%100);
		parm2.put("version", 11);
		given().contentType("application/json")
			.request().body(parm2.toJSONString())
			.when().put(basePath+"/"+array2[0])
			.then()
			.statusCode(MODIFIED);
		delete((String)array[0],jsonReturn.getInteger("version"));
		delete((String)array2[0],(int)array2[2]);
	}
	
	@Test
	public void testList(){
		JSONObject parm = new JSONObject();
		parm.put(CURRENT, 1);
		parm.put(SIZE, 5);
		parm.put("name", "ju");
		JSONObject json = httpPost(basePath+"/list", parm);
		System.err.println(json.toString());
		Assert.assertEquals(5, json.getJSONArray(RECORDS).size());
	}
	
	
	@Test
	public void testDelete(){
		JSONObject parm = new JSONObject();
		Object[] array = addRandom();
		parm.put("id",array [0]);
		parm.put("version", array[2]);
		given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().delete(basePath+"/"+array[0])
	        .then()
	        .statusCode(SUCCESS);
	}
	

	@Test
	public void testPMList(){
		JSONObject parm = new JSONObject();
		parm.put("id", "7d22f1be89f34f6b8ed8f7709c91bc08");
		ValidatableResponse response = given().contentType("application/json")
				.request()//.body(parm.toJSONString())
				.when().get(basePath+"/7d22f1be89f34f6b8ed8f7709c91bc08/managers")
				.then()
				.statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toString());
	}
	
	@Test
	public void addPm4Pj(){
		JSONObject parm = new JSONObject();
		parm.put("id", "7d22f1be89f34f6b8ed8f7709c91bc08");
		JSONArray managers = new JSONArray();
		managers.add("088b1c492e584cda89950de98965fdbb");
		managers.add("0f4934679ab24a1a80316a801f56e1b8");
		managers.add("48445be7c38b46ccb045f6620545a9fe");
		parm.put("managers", managers);
		given().contentType("application/json")
				.request().body(parm.toJSONString())
				.when().post(basePath+"/7d22f1be89f34f6b8ed8f7709c91bc08/managers")
				.then()
				.statusCode(SUCCESS);
		ValidatableResponse response = given().contentType("application/json")
			.request()//.body(parm.toJSONString())
			.when().get(basePath+"/7d22f1be89f34f6b8ed8f7709c91bc08/managers")
			.then()
			.statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toString());
	}
	
	@Test
	public void testDeletePM(){
		JSONObject parm = new JSONObject();
		parm.put("id", "7d22f1be89f34f6b8ed8f7709c91bc08");
		JSONArray managers = new JSONArray();
		managers.add("088b1c492e584cda89950de98965fdbb");
		managers.add("0f4934679ab24a1a80316a801f56e1b8");
		parm.put("managers", managers);
		ValidatableResponse response = given().contentType("application/json")
				.request().body(parm.toJSONString())
				.when().delete(basePath+"/7d22f1be89f34f6b8ed8f7709c91bc08/managers")
				.then()
				.statusCode(SUCCESS);
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toString());
		response = given().contentType("application/json")
			.request()//.body(parm.toJSONString())
			.when().get(basePath+"/7d22f1be89f34f6b8ed8f7709c91bc08/managers")
			.then()
			.statusCode(SUCCESS);
		json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json.toString());
	}
	
	@Test
	public void getPjDetailTest(){
		Object[] arr = addRandom();
		String id = (String) arr[0];
		System.err.println(httpGet(basePath+"/"+id));
		
	}
	
	@Test
	public void testDB(){
		JSONObject db = new JSONObject();
		db.put("dbType", "sqlserver");
		db.put("dbUrl", "192.168.10.64:3306/test_db"+new Random().nextInt()%1000);
		db.put("dbUser", "root");
		db.put("dbPwd", "123456");
		httpPost(basePath+"/testdb", db, BAD_REQUEST);
		db.put("dbType", "mysql");
		db.put("dbPwd", null);
		httpPost(basePath+"/testdb", db, BAD_REQUEST);
		db.put("dbPwd", "");
		httpPost(basePath+"/testdb", db, BAD_REQUEST);
		
		db.put("dbUrl", "192.168.10.64:3306/mybatis-plus");
		db.put("dbPwd", "root");
		httpPost(basePath+"/testdb", db, SUCCESS);
	}
	
}
