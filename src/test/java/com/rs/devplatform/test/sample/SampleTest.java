package com.rs.devplatform.test.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.DevPlatformApp;
import com.rs.devplatform.persistent.sample.CgSample;
import com.rs.devplatform.service.sample.CgSampleService;
import com.rs.devplatform.test.base.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SampleTest extends TestBase{
	
	@Autowired
	CgSampleService sampleSvc;
	
	@Test
	public void testAspectInterface(){
		CgSample samp = new CgSample();
		samp.setAge(100);
		samp.setName("100");
		samp.setNkname("100");
		sampleSvc.add(samp);
	}

	@Test
	public void testGet(){
		System.err.println(httpGet("/sample/cgSample/"+"abc", BAD_REQUEST));
		CgSample sample = new CgSample();
		sample.setName("ju-test-1");
		sample.setNkname(sample.getName());
		sample.setAge(13);
		generalMapper.insert(sample);
		JSONObject json = httpGet("/sample/cgSample/"+sample.getId());
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void testList(){
		JSONObject parm = new JSONObject();
		parm.put("current", 1);
		parm.put("size", 3);
		parm.put("ageFrom", 12);
		parm.put("ageTo", 13);
		parm.put("name", "1");
		parm.put("dtFrom", 1488298562523L);
		JSONObject json = httpPost("/sample/cgSample/list", parm);
		System.err.println(json.toJSONString());
	}
	
	@Test
	public void testAdd(){
		JSONObject parm = new JSONObject();
		JSONObject json = httpPost("/sample/cgSample", parm, BAD_REQUEST);
		System.err.println(json);
		
		
	}
}
