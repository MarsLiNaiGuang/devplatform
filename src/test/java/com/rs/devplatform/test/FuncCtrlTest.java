package com.rs.devplatform.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.DevPlatformApp;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysTables;
import com.rs.devplatform.service.admin.MenuService;
import com.rs.devplatform.test.base.TestBase;
import com.rs.devplatform.vo.onlinefc.OnlineFuncVO;
import com.rs.framework.common.entity.persistent.SysMenus;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class FuncCtrlTest extends TestBase {
	
	private static final String basePath = "/admin/func";
	
	@Autowired
	MenuService menuService;
	
	@Test
	public void listTest(){
		int size = 5;
		JSONObject parm = new JSONObject();
		parm.put(CURRENT, 1);
		parm.put(SIZE, size);
		parm.put("name", "");
		JSONObject json = httpPost(basePath+"/list", parm);
		Assert.assertEquals(size, json.getJSONArray(RECORDS).size());
		System.err.println(json);
	}
	
	@Test
	public void getButtonsByFuncId(){
		System.err.println(httpGet(basePath+"/button/zp_resume"));
	}
	
	@Test
	public void getColumns4NoSQLByMenuId(){
		String tablename = "jutest_nosq2";
		SysTables table = DBMaintainCtrlTest.addAndSync(tablename);
		SysBxx bxx = CGCtrlTest.addTable(tablename, SUCCESS);
		SysMenus menu = new SysMenus();
		String fcflag = tablename+"_flag";
		String menuId = tablename+"_flag";
		menu.setId(menuId);
		menu.setFuncId(fcflag);
		menu.setName("test");
		menu.setType("1");
		menu.setUrl("/test");
		menuService.addMenu(menu);
		
		JSONObject json = httpGet("/admin/func/column/"+menuId);
		System.err.println("getColumns4NoSQLByMenuId="+json);
		menuService.deleteMenu(menuId);
		CGCtrlTest.deleteTable(bxx.getId(), bxx.getVersion(), SUCCESS);
		DBMaintainCtrlTest.deleteTableforceDrop(table.getId(), table.getVersion());
	}
	
	@Test
	public void getColumns4SQLByMenuId(){
		String tablename = "jutest_bysqa";
		OnlineFuncVO funcVO = OnlineFuncCtrlTest.addFuncBySQL(tablename);
		SysMenus menu = new SysMenus();
		String fcflag = tablename+"_flag";
		String menuId = tablename+"_flag";
		menu.setId(menuId);
		menu.setFuncId(fcflag);
		menu.setName("test");
		menu.setType("1");
		menu.setUrl("/test");
		menuService.addMenu(menu);
		JSONObject json = httpGet("/admin/func/column/"+menuId);
		System.err.println("getColumns4SQLByMenuId="+json);
		menuService.deleteMenu(menuId);
		OnlineFuncCtrlTest.deleteOnlineFunc(funcVO.getTableId(), funcVO.getVersion());
	}
}
