package com.rs.devplatform.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rs.devplatform.DevPlatformApp;
import com.rs.devplatform.test.base.TestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class MenuCtrlTest extends TestBase {
	private static final String basePath = "/admin/menu";
	@Test
	public void listMenuTest(){
		httpGet(basePath + "/list");
	}
}
