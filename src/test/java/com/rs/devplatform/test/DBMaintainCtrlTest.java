package com.rs.devplatform.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.DevPlatformApp;
import com.rs.devplatform.persistent.SysTables;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.service.admin.DBMaintainService;
import com.rs.devplatform.test.base.TestBase;
import com.rs.devplatform.vo.MaintainColumnVO;
import com.rs.devplatform.vo.MaintainIndexVO;
import com.rs.devplatform.vo.MaintainTableVO;
import com.rs.framework.common.exception.RsCommonException;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class DBMaintainCtrlTest extends TestBase {
	
	private static final String basePath = "/admin/db";
	
	@Autowired
	DBMaintainService dbService;
	
	@Test
	public void add(){
		SysTables bxx = addTable(null, SUCCESS);
		String tablename = bxx.getName();
		addTable(tablename, DUPLICATE);
		deleteTable(bxx.getId(), bxx.getVersion());
		
		JSONObject json = httpPost(basePath+"/table", new JSONObject(), BAD_REQUEST);
		System.err.println(json);
		Assert.assertNotNull(json.get(VALIDATE_ERRORS));
	}
	@Test
	public void get(){
		SysTables bxx = addTable(null, SUCCESS);
		String id = bxx.getId();
		JSONObject json = httpGet(basePath+"/table/"+id);
		System.err.println("======get api==============");
		System.err.println(json);
		Assert.assertNotNull(json.get("isdbsyn"));
		Assert.assertNotNull(json.get("version"));
		Assert.assertNotNull(json.get("id"));
		System.err.println("====================");
		deleteTable(bxx.getId(), bxx.getVersion());
	}
	
	@Test
	public void list(){
		SysTables bxx = addTable(null, SUCCESS);
		JSONObject parm = new JSONObject();
		parm.put("tablename", bxx.getName());
		parm.put("tabledescp", bxx.getComment());
		parm.put("isdbsyn", "N");
		parm.put(CURRENT, 1);
		JSONObject json = httpPost(basePath+"/tables", parm);
		Assert.assertEquals(1, json.getIntValue(TOTAL));
		deleteTable(bxx.getId(), bxx.getVersion());
	}
	
	
	@Test
	public void updateDirectly(){
		SysTables bxx = addTable(null, SUCCESS);
		String id = bxx.getId();
		Integer version = bxx.getVersion();
		JSONObject json = httpGet(basePath+"/table/"+id);
		System.err.println(json);
		MaintainTableVO tableVO = json.toJavaObject(MaintainTableVO.class);
		MaintainColumnVO[] cols = tableVO.getColumns();
		MaintainIndexVO[] indexs = tableVO.getIndexs();
		
		List<Object> colList = new ArrayList<Object>();
		for(int i=0;i<cols.length;++i){
			if(i%2==0){
				MaintainColumnVO colvo = cols[i];
				colvo.setCol_defval("abc");
				colList.add(colvo);
			}
		}
		for(int i=0;i<3;++i){
			JSONObject column = new JSONObject();
			column.put("colSeq", 6+i);
			column.put("colName", "name_"+i);
			column.put("colMark", "mark_"+i);
			column.put("colLen", 10+i);
			column.put("colDecpoint", 0);
			column.put("colDefval", "");
			column.put("colType", "string");
			column.put("colIspk", i==0?"":"");
			column.put("colIsnull", i==0?"":"Y");
			colList.add(column);
		}
		
		List<Object> indexList = new ArrayList<>();
		for(int i=0;i<indexs.length;++i){
			if(i%2==0){
				indexList.add(indexs[i]);//nothing updated
			}
		}
		MaintainIndexVO vo1 = new MaintainIndexVO();
		vo1.setColName("name_2");
		vo1.setIndexName("name_2_index_new");
		vo1.setIndexSeq(3);
		vo1.setIndexType("normal");
		indexList.add(vo1);
		
		JSONObject parm = new JSONObject();
		parm.put("version", version);
		parm.put("tablename", bxx.getName());
		parm.put("pktype", bxx.getPktype());
		parm.put("tabledescp", "upd tabledescp");
		parm.put("columns", colList);
		parm.put("indexs", indexList);
		System.err.println("=====update form columns/index parm jsonstr:");
		System.err.println(parm.toJSONString());
		System.err.println("========================");
		json = httpPut(basePath+"/table/"+id, parm);
		System.err.println(json);
		Assert.assertEquals(version+1, json.getIntValue(VERSION));
		deleteTable(bxx.getId(), version, MODIFIED);
		deleteTable(bxx.getId(), version+1,SUCCESS);
	}
	
	@Test
	public void confirmUpdateTest(){
		SysTables bxx = addTable(null, SUCCESS);
		String id = bxx.getId();
		Integer version = bxx.getVersion();
		JSONObject json = httpGet(basePath+"/table/"+id);
		System.err.println(json);
		MaintainTableVO tableVO = json.toJavaObject(MaintainTableVO.class);
		MaintainColumnVO[] cols = tableVO.getColumns();
		MaintainIndexVO[] indexs = tableVO.getIndexs();
		
		List<Object> colList = new ArrayList<Object>();
		for(int i=0;i<cols.length;++i){
			if(i%2==0){
				MaintainColumnVO colvo = cols[i];
				colvo.setCol_defval("abc");
				colList.add(colvo);
			}
		}
		for(int i=0;i<3;++i){
			JSONObject column = new JSONObject();
			column.put("colSeq", 6+i);
			column.put("colName", "name_"+i);
			column.put("colMark", "mark_"+i);
			column.put("colLen", 10+i);
			column.put("colDecpoint", 0);
			column.put("colDefval", "");
			column.put("colType", "string");
			column.put("colIspk", i==0?"":"");
			column.put("colIsnull", i==0?"":"Y");
			colList.add(column);
		}
		
		List<Object> indexList = new ArrayList<>();
		for(int i=0;i<indexs.length;++i){
			if(i%2==0){
				indexList.add(indexs[i]);//nothing updated
			}
		}
		MaintainIndexVO vo1 = new MaintainIndexVO();
		vo1.setColName("name_2");
		vo1.setIndexName("name_2_index_new");
		vo1.setIndexSeq(3);
		vo1.setIndexType("normal");
		indexList.add(vo1);
		
		JSONObject parm = new JSONObject();
		parm.put("version", version);
		parm.put("tablename", bxx.getName());
		parm.put("pktype", bxx.getPktype());
		parm.put("tabledescp", "upd tabledescp");
		parm.put("columns", colList);
		parm.put("indexs", indexList);
		System.err.println("=====confirm update parm jsonstr:");
		System.err.println(parm.toJSONString());
		System.err.println("========================");
		json = httpPut(basePath+"/table/"+id+"/confirm", parm);
		System.err.println(json);
		
		deleteTable(bxx.getId(), version,SUCCESS);
	}
	
	@Test
	public void updateConfirmDataTest(){
		SysTables bxx = addTable(null, SUCCESS);
		String id = bxx.getId();
		Integer version = bxx.getVersion();
		JSONObject json = httpGet(basePath+"/table/"+id);
		System.err.println(json);
		MaintainTableVO tableVO = json.toJavaObject(MaintainTableVO.class);
		MaintainColumnVO[] cols = tableVO.getColumns();
		MaintainIndexVO[] indexs = tableVO.getIndexs();
		
		List<Object> colList = new ArrayList<Object>();
		for(int i=0;i<cols.length;++i){
			if(i%2==0){
				MaintainColumnVO colvo = cols[i];
				colvo.setCol_defval("abc");
				colList.add(colvo);
			}
		}
		for(int i=0;i<3;++i){
			JSONObject column = new JSONObject();
			column.put("colSeq", 6+i);
			column.put("colName", "name_"+i);
			column.put("colMark", "mark_"+i);
			column.put("colLen", 10+i);
			column.put("colDecpoint", 0);
			column.put("colDefval", "");
			column.put("colType", "string");
			column.put("colIspk", i==0?"":"");
			column.put("colIsnull", i==0?"":"Y");
			colList.add(column);
		}
		
		List<Object> indexList = new ArrayList<>();
		for(int i=0;i<indexs.length;++i){
			if(i%2==0){
				indexList.add(indexs[i]);//nothing updated
			}
		}
		MaintainIndexVO vo1 = new MaintainIndexVO();
		vo1.setColName("name_2");
		vo1.setIndexName("name_2_index_new");
		vo1.setIndexSeq(3);
		vo1.setIndexType("normal");
		indexList.add(vo1);
		
		JSONObject parm = new JSONObject();
		parm.put("version", version);
		parm.put("tablename", bxx.getName());
		parm.put("pktype", bxx.getPktype());
		parm.put("tabledescp", "upd tabledescp");
		parm.put("columns", colList);
		parm.put("indexs", indexList);
		System.err.println("=====confirm update parm jsonstr:");
		System.err.println(parm.toJSONString());
		System.err.println("========================");
		json = httpPut(basePath+"/table/"+id+"/confirm", parm);
		System.err.println(json);
		
		json = httpPut(basePath+"/table/"+id+"", json);
		Assert.assertEquals(version+1, json.getIntValue(VERSION));
		
		json = httpGet(basePath+"/table/"+id);
		System.err.println("after update by confirm data=====");
		System.err.println(json);
		System.err.println("========================");
		
		deleteTable(bxx.getId(), version+1,SUCCESS);
	}
	
	@Test
	public void addTableSyncTest(){
		try {
			dbService.deleteTableByName(null, "xiaobin_test123", 1, true);
		} catch (RsCommonException e) {
			e.printStackTrace();
		}
		SysTables bxx = addTable("xiaobin_test123", SUCCESS);
		JSONObject parm = new JSONObject();
		parm.put(VERSION, bxx.getVersion());
		JSONObject result = httpPut(basePath+"/table/"+bxx.getId()+"/sync", parm);
		Assert.assertEquals(bxx.getVersion()+1, result.getIntValue(VERSION));
		try {
			dbService.deleteTableByName(null, "xiaobin_test123", 2, true);
		} catch (RsCommonException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}
	
	@Test
	public void delete(){
		SysTables bxx = addTable(null, SUCCESS);
		deleteTable(bxx.getId(), bxx.getVersion());
		
		bxx = addTable("sys_test", SUCCESS);
		int version = bxx.getVersion();
		String tableId = bxx.getId();
		JSONObject parm = new JSONObject();
		parm.put(VERSION, bxx.getVersion());
		JSONObject json = httpDelete(basePath+"/table/"+bxx.getId(), parm, BAD_REQUEST);
		System.err.println(json);
		Assert.assertNotNull(json.get(VALIDATE_ERRORS));
		try {
			dbService.deleteTableById(null, tableId, version, false);
		} catch (RsCommonException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void deleteSysTable() throws RsCommonException{
		SysTables bxx = addTable("sys_xbtest", SUCCESS);
		String id = bxx.getId();
		JSONObject parm = new JSONObject();
		parm.put(VERSION, bxx.getVersion());
		JSONObject json = httpDelete(basePath+"/table/"+id, parm, BAD_REQUEST);
		Assert.assertNotNull(json.getJSONArray(VALIDATE_ERRORS));
		
		SysZdxx zdxx = new SysZdxx();
		zdxx.setBid(id);
		System.err.println("delete from zdxx:");
		dbService.deleteTableById(null, id, bxx.getVersion(), false);
	}
	
	@Test
	public void deleteWithForceDrop(){
		SysTables bxx = addTable("jutest_xb", SUCCESS);
		String tableId = bxx.getId();
		int version = bxx.getVersion();
		System.err.println("deleteWithForceDrop: tableName = "+bxx.getName());
		JSONObject parm = new JSONObject();
		parm.put(VERSION, version);
		httpPut(basePath+"/table/"+tableId+"/sync", parm);
		
		parm.put(VERSION, version+1);
		parm.put("forceDrop", true);
		httpDelete(basePath+"/table/"+tableId, parm);
	}
	
	public static SysTables addAndSync(String tablename){
		SysTables bxx = addTable(tablename, SUCCESS);
		JSONObject parm = new JSONObject();
		parm.put(VERSION, bxx.getVersion());
		JSONObject result = httpPut(basePath+"/table/"+bxx.getId()+"/sync", parm);
		Integer version = result.getInteger(VERSION);
		bxx.setVersion(version);
		return bxx;
	}
	
	public static void deleteTable(String tableId, Integer version){
		deleteTable(tableId, version, SUCCESS);
	}
	
	private static void deleteTable(String tableId, Integer version, Matcher<Object> eq){
		JSONObject parm = new JSONObject();
		parm.put(VERSION, version);
		httpDelete(basePath+"/table/"+tableId, parm, eq);
	}
	
	public static void deleteTableforceDrop(String tableId, Integer version, Matcher<Object> eq){
		JSONObject parm = new JSONObject();
		parm.put(VERSION, version);
		parm.put("forceDrop", true);
		httpDelete(basePath+"/table/"+tableId, parm, eq);
	}
	public static void deleteTableforceDrop(String tableId, Integer version){
		JSONObject parm = new JSONObject();
		parm.put(VERSION, version);
		parm.put("forceDrop", true);
		httpDelete(basePath+"/table/"+tableId, parm, SUCCESS);
	}
	
	public static SysTables addTable(String tablename, Matcher<Object> eq){
		JSONObject parm = prepareData(tablename);
		return addTableByJSON(parm.getString("tablename"), parm, eq);
	}
	
	private static JSONObject prepareData(String tablename){
		JSONObject parm = new JSONObject();
		int randomInt = new Random().nextInt()%10000;
		if(tablename==null){
			tablename = "tbl_ju"+randomInt;
		}
		String tabledescp = tablename+randomInt;
		parm.put("tablename", tablename);
		parm.put("tabledescp", tabledescp);
		parm.put("pktype", "UUID");
		
		JSONArray array = new JSONArray();
		for(int i=0;i<5;++i){
			JSONObject column = new JSONObject();
			column.put("colSeq", i+1);
			column.put("colName", "name_"+i);
			column.put("colMark", "mark_"+i);
			column.put("colLen", 10+i);
			column.put("colDecpoint", 0);
			column.put("colDefval", "");
			column.put("colType", "string");
			column.put("colIspk", i==0?"Y":"");
			column.put("colIsnull", i==0?"":"Y");
			array.add(column);
		}
		parm.put("columns", array);
		
		JSONArray indexArray = new JSONArray();
		JSONObject indexObj = new JSONObject();
		indexObj.put("indexSeq", 1);
		indexObj.put("indexName", "name_0_index");
		indexObj.put("colName", "name_0");
		indexObj.put("indexType", "normal");
		indexArray.add(indexObj);
		
		indexObj = new JSONObject();
		indexObj.put("indexSeq", 2);
		indexObj.put("indexName", "name_1_index");
		indexObj.put("colName", "name_1");
		indexObj.put("indexType", "unique");
		indexArray.add(indexObj);
		
		parm.put("indexs", indexArray);
		return parm;
	}
	
	private static SysTables addTableByJSON(String tablename, JSONObject parm, Matcher<Object> eq){
		System.err.println("addTableJsonParm:");
		System.err.println(parm.toJSONString());
		JSONObject json = httpPost(basePath+"/table", parm, eq);
		SysTables tab = new SysTables();
		tab.setId(json.getString(ID));
		tab.setVersion(json.getInteger(VERSION));
		tab.setSync(json.getString("isdbsyn"));
		tab.setName(tablename);
		tab.setComment(parm.getString("tabledescp"));
		tab.setPktype(parm.getString("pktype"));
		System.err.println("tablename="+tablename+", id="+tab.getId());
		return tab;
	}
	
}
