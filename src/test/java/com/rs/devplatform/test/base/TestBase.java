package com.rs.devplatform.test.base;

import static com.jayway.restassured.RestAssured.given;

import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ValidatableResponse;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.controller.base.BaseController;

public class TestBase {
	
	public static class PARAM{
		public static final String ID = "id";
		public static final String USER_TYPE = "userType";
		public static final String VERSION = "version";
	}
	
	public static final String ID = PARAM.ID;
	public static final String VERSION = PARAM.VERSION;
	public static final String RECORDS = BaseController.RECORDS;
	public static final String TOTAL = BaseController.TOTAL;
	public static final String CURRENT = BaseController.CURRENT;
	public static final String SIZE = BaseController.SIZE;
	public static final String VALIDATE_ERRORS = BaseController.VALIDATE_ERRORS;

	@Value("${local.server.port}")   //3
    int port;
	
	public static JSONObject httpGet(String httpUrl){
		return 
				JSONObject.parseObject(
					given().request()
			        .when().get(httpUrl)
			        .then()
			        .statusCode(SUCCESS)
			        .extract().asString()
		        );
	}
	
	public static JSONObject httpGet(String httpUrl, Matcher<Object> matcher){
		return 
				JSONObject.parseObject(
					given().request()
			        .when().get(httpUrl)
			        .then()
			        .statusCode(matcher)
			        .extract().asString()
		        );
	}
	
	public static JSONObject httpPost(String httpUrl, JSONObject parm){
		return 
				JSONObject.parseObject(
						given().contentType("application/json")
				        .request().body(parm==null?null:parm.toJSONString())
				        .when().post(httpUrl)
				        .then()
				        .statusCode(SUCCESS)
				        .extract().asString()
		        );
	}
	
	public static JSONObject httpPost(String httpUrl, JSONObject parm, Matcher<Object> matcher){
		ValidatableResponse response = given().contentType("application/json")
				.request().body(parm==null?null:parm.toJSONString())
				.when().post(httpUrl)
				.then();
		System.err.println("httpPost: result="+response.extract().asString());
		return 
				JSONObject.parseObject(
						response
						.statusCode(matcher)
						.extract().asString()
						);
	}
	
	public static JSONObject httpPut(String httpUrl, JSONObject parm){
		return 
				JSONObject.parseObject(
						given().contentType("application/json")
						.request().body(parm==null?null:parm.toJSONString())
						.when().put(httpUrl)
						.then()
						.statusCode(SUCCESS)
						.extract().asString()
				        );
	}
	public static JSONObject httpDelete(String httpUrl, JSON parm){
		System.err.println(parm.toJSONString());
		return 
				JSONObject.parseObject(
						given().contentType("application/json")
						.request().body(parm==null?null:parm.toJSONString())
						.when().delete(httpUrl)
						.then()
						.statusCode(SUCCESS)
						.extract().asString()
						);
	}
	public static JSONObject httpDelete(String httpUrl, JSON parm, Matcher<Object> matcher){
		System.err.println(parm.toJSONString());
		return 
				JSONObject.parseObject(
						given().contentType("application/json")
						.request().body(parm==null?null:parm.toJSONString())
						.when().delete(httpUrl)
						.then()
						.statusCode(matcher)
						.extract().asString()
						);
	}
	
	
	
	@Autowired
	protected RsGeneralMapper generalMapper;

    @Before
    public void doBefore(){
        RestAssured.port = port;//4: 告诉restAssured使用哪个端口来访问
    }
    
    @Transactional
    public void deleteData(Object entity){
    	generalMapper.deleteSelective(entity);
    }
    
    @Transactional
    public void deleteBatchIds(List<String> idList, Class<?> clazz){
    	generalMapper.deleteBatchIds(idList, clazz);
    }
    
    public static final Matcher<Object> SUCCESS = new Equals(200);
    public static final Matcher<Object> BAD_REQUEST = new Equals(400);
    public static final Matcher<Object> DUPLICATE = new Equals(302);
    public static final Matcher<Object> MODIFIED = new Equals(409);
    
    /**
     * 如何保证后续请求使用的是同一个session（登录过后，下一个restAssured请求是同一个session）
     * 
     * JSONObject parm = new JSONObject();
		parm.put("username", "admin");
		parm.put("password", "1234561");
		parm.put("useFlag", "A");
     * ValidatableResponse response = 
     * 		given().contentType("application/json")
	        .request().body(parm.toJSONString())
	        .when().post("/loginrest");
     * String jsessionId = response.extract().cookie(JSESSIONID);	//获取sessionId
     * 
     * given().contentType("application/json")
	        .request().cookie(JSESSIONID, jsessionId)//请求带上这个jsessionId
	        .when().get("/admin/menu/list");
     * 
     * refer to {@code LoginCtrlTest.loginAndCreateTest()}
     */
    public final String JSESSIONID = "JSESSIONID";
}
