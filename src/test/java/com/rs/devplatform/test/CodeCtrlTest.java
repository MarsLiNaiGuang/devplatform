package com.rs.devplatform.test;

import org.junit.Assert;
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
public class CodeCtrlTest extends TestBase {
	
	private static final String basePath = "/common/code";
	
	@Test
	public void getCodesFromCodeTable(){
		String httpUrl = basePath+"/values?cdtype=BASE_COLUMNS_TYPE";
		JSONObject json = httpGet(httpUrl);
		JSONArray cdvals = json.getJSONArray("cdvals");
		Assert.assertNotNull(cdvals);
		System.err.println(json);
	}
	
	@Test
	public void getCodesFromTables(){
		String httpUrl = basePath+"/values?dictTable=sys_cdval&cdvalCol=CDVAL_CDVAL&cddescpCol=CDVAL_CDDESCP";
		JSONObject json = httpGet(httpUrl);
		JSONArray cdvals = json.getJSONArray("cdvals");
		Assert.assertNotNull(cdvals);
		System.err.println(json);
	}

}
