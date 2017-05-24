package com.rs.devplatform.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.DevPlatformApp;
import com.rs.devplatform.test.base.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class groupCtrlTest extends TestBase{

	private final static String basePath = "/admin/group";
	@Test
	public void addGroupTest(){
		JSONObject parm = new JSONObject();
		parm.put("name","test6");
		parm.put("actor", "1,4,");
		httpPost(basePath, parm);
	}
	
	@Test
	public void updateGroupTest(){
		JSONObject parm = new JSONObject();
		parm.put("name","test111");
		parm.put("actor", "1,4,");
		httpPut(basePath + "/01", parm);
	}
	
	@Test
	public void deleteGroupTest(){
		JSONArray array = new JSONArray();
		array.add("80b66b2220064ff3a7fa7997fa51edc0");
		JSONObject parm = new JSONObject();
		parm.put("ids", array);
		httpDelete(basePath, parm);
	}
}
