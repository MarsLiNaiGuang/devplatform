package com.rs.devplatform.test;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hamcrest.Matcher;
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
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysTables;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.test.base.TestBase;
import com.rs.devplatform.vo.ColumnVO;
import com.rs.devplatform.vo.TableVO;
import com.rs.devplatform.vo.onlinefc.SubtablesVO;
import com.rs.framework.common.RsEntityWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class CGCtrlTest extends TestBase {
	
	private static final String basePath = "/cg";
	
	@Test
	public void testGetCurrentAvailableColumnsByTableName(){
		SysTables table = DBMaintainCtrlTest.addAndSync("xb_columns");
		String tablename = table.getName();
		JSONObject json = httpGet(basePath+"/form/"+tablename+"/columns");
		System.err.println("GetCurrentAvailableColumn="+json);
		Assert.assertNotNull(json);
		Assert.assertNotNull(json.getJSONArray(RECORDS));
		DBMaintainCtrlTest.deleteTableforceDrop(table.getId(), table.getVersion());
	}
	
	
	@Test
	public void add(){
		addTable(null, BAD_REQUEST);
		SysBxx bxx = addTable(null, SUCCESS);
		Assert.assertNotNull(bxx.getZb());
		String tablename = bxx.getBm();
		addTable(tablename, DUPLICATE);
		deleteTable(bxx.getId(), bxx.getVersion());
	}
	
	
	@Test
	public void get(){
		SysBxx bxx = addTable(null, SUCCESS);
		String id = bxx.getId();
		JSONObject json = httpGet(basePath+"/form/"+id);
		System.err.println("======get api==============");
		System.err.println(json);
		Assert.assertNotNull(json.getJSONArray("subtables"));
		System.err.println("====================");
		SysZdxx ziduan = new SysZdxx();
		ziduan.setBid(id);
		generalMapper.selectList(new RsEntityWrapper<>(ziduan)).stream().forEach((x)->{
			System.out.print(x.getName()+", ");
		});
		deleteTable(bxx.getId(), bxx.getVersion());
	}
	
	@Test
	public void list(){
		SysBxx bxx = addTable("jutest_abc", SUCCESS);
		JSONObject parm = new JSONObject();
		parm.put("tablename", "");
		parm.put("fcflag", "abc");
		JSONObject json = httpPost(basePath+"/form/list", parm);
		System.err.println(json);
		deleteTable(bxx.getId(), bxx.getVersion());
	}
	
	
	@Test
	public void update(){
		SysBxx bxx = addTable(null, SUCCESS);
		String id = bxx.getId();
		Integer version = bxx.getVersion();
		JSONObject json = httpGet(basePath+"/form/"+id);
		System.err.println(json);
		TableVO tableVO = json.toJavaObject(TableVO.class);
		ColumnVO[] cols = tableVO.getColumns();
		
		List<Object> colList = new ArrayList<Object>();
		for(int i=0;i<cols.length;++i){
			if(i%2==0){
				ColumnVO colvo = cols[i];
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
			column.put("isFormdisp", i==0?"":"Y");
			column.put("isGriddisp", i==0?"":"Y");
			column.put("inputType", "text");
			column.put("inputLen", 100);
			column.put("isSearch", i==0?"":"Y");
			column.put("searchType", i%2==0?"normal":"range");
			column.put("extjson", "");
			column.put("colHref", "colHref2");
			column.put("validType", "validType2");
			column.put("dictTable", "dictTable2");
			column.put("dictText", "dictText2");
			colList.add(column);
		}
		
		SubtablesVO[] subtables = tableVO.getSubtables();
		subtables[0].setColName("subcol_update");
		
		JSONObject parm = new JSONObject();
		parm.put("version", version);
		parm.put("tabletype", 1);
		parm.put("tabledescp", "upd tabledescp");
		parm.put("columns", colList);
		parm.put("subtables", subtables);
		System.err.println("=====update form columns/index:");
		System.err.println(parm.toJSONString());
		System.err.println("========================");
		json = httpPut(basePath+"/form/"+id, parm);
		System.err.println(json);
		
		json = httpGet(basePath+"/form/"+id);
		System.err.println(json);
		tableVO = json.toJavaObject(TableVO.class);
		Assert.assertEquals("SUBCOL_UPDATE", tableVO.getSubtables()[0].getColName());
		
		deleteTable(bxx.getId(), version, MODIFIED);
		deleteTable(bxx.getId(), version+1,SUCCESS);
	}
	
	@Test
	public void delete(){
		SysBxx bxx = addTable(null, SUCCESS);
		deleteTable(bxx.getId(), bxx.getVersion());
	}
	
	private void deleteTable(String tableId, Integer version){
		deleteTable(tableId, version, SUCCESS);
	}
	
	public static void deleteTable(String tableId, Integer version, Matcher<Object> eq){
		JSONObject parm = new JSONObject();
		parm.put(VERSION, version);
		httpDelete(basePath+"/form/"+tableId, parm, eq);
	}
	/*******************************************************/
	private static enum BLX{
		SingleTable(1), ParentTable(2), ChildTable(3);
		int type;
		private BLX(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
//	@Test
	public void testAddTableWithChild(){
		SysBxx[] array = addParentAndAppendixTable("ju_parent_"+new Random().nextInt(10000), "ju_child_"+new Random().nextInt(10000), SUCCESS);
		SysBxx parentBxx = array[0];
		SysBxx childBxx = array[1];
		JSONObject json = httpGet(basePath+"/form/"+parentBxx.getId());
		System.err.println(json.toJSONString());
		System.err.println(httpGet(basePath+"/form/"+childBxx.getId()).toJSONString());
		deleteTableData(parentBxx);
	}
	
	private void deleteTableData(SysBxx parentTablexx){
		String parentTablename = parentTablexx.getBm();
		SysZdxx fkzd = new SysZdxx();
		fkzd.setZb(parentTablename);
		List<SysZdxx> fkzdList = generalMapper.selectList(new RsEntityWrapper<>(fkzd));
		for(SysZdxx zd:fkzdList){
			deleteTable(zd.getBid(), 1);
		}
		deleteTable(parentTablexx.getId(), parentTablexx.getVersion());
	}
	public static SysBxx addTable(String tablename, Matcher<Object> eq){
		return addTable(tablename, 1, eq);
	}
	
	private SysBxx[] addParentAndAppendixTable(String parentTableName, String relationTable, Matcher<Object> eq){
		JSONObject parentTable = prepareData(parentTableName, BLX.ParentTable.getType(), null, null, eq);
		SysBxx parentBxx = addTableByJSON(parentTableName, parentTable, eq);
		JSONArray columns = parentTable.getJSONArray("columns");
		JSONObject column3 = columns.getJSONObject(2);
		JSONObject childTable = prepareData(relationTable, BLX.ChildTable.getType(), parentTableName, column3.getString("colName"), eq);
		SysBxx childBxx = addTableByJSON(relationTable, childTable, eq);
		return new SysBxx[]{parentBxx,childBxx};
		
	}
	public static JSONObject prepareData(String tablename, Integer tabletype, String parentTableName, String relationColumn, Matcher<Object> eq){
		JSONObject parm = new JSONObject();
		int randomInt = new Random().nextInt()%10000;
		if(tablename==null){
			tablename = "tbl_ju"+randomInt;
		}
		String tabledescp = tablename+randomInt;
		parm.put("tablename", tablename);
		if(BAD_REQUEST!=eq){
			parm.put("fcflag", tablename+"_flag");
		}
		parm.put("tabledescp", tabledescp);
		parm.put("pktype", "UUID");
		parm.put("tabletype", tabletype==null?1:tabletype);
		parm.put("relationType", parentTableName==null?null:1);
		parm.put("relationSeq", parentTableName==null?null:1);
		parm.put("multiselect", "Y");
		parm.put("ispage", "Y");
		parm.put("istree", "N");
		
		JSONArray array = new JSONArray();
		for(int i=0;i<5;++i){
			JSONObject column = new JSONObject();
			column.put("colSeq", i+1);
			column.put("colName", "name_"+i);
			column.put("col_mark", "mark_"+i);
			column.put("colLen", 10+i);
			column.put("col_decpoint", 0);
			column.put("col_defval", "");
			column.put("col_type", "string");
			column.put("col_ispk", i==0?"Y":"");
			column.put("col_isnull", i==0?"":"Y");
			column.put("isformdisp", i==0?"":"Y");
			column.put("isgriddisp", i==0?"":"Y");
			column.put("inputtype", "text");
			column.put("colHref", "colHref");
			column.put("validType", "validType");
			column.put("dictTable", "dictTable");
			column.put("dictCode", "dictCode");
			column.put("dictText", "dictText");
			column.put("fkTableName", "");
			column.put("fkColName", "");
			column.put("inputlen", 100);
			column.put("issearch", i==0?"":"Y");
			column.put("searchtype", i%2==0?"normal":"range");
			column.put("extjson", "");
			array.add(column);
		}
		parm.put("columns", array);
		
		JSONArray subtablesArray = new JSONArray();
		JSONObject subtableObj = new JSONObject();
		subtableObj.put("fcflag", "fcflag0");
		subtableObj.put("tablename", "subtable0");
		subtableObj.put("colName", "subcolname_0");
		if(eq==SUCCESS || eq==DUPLICATE){
			subtableObj.put("mainColName", "maincol1");
		}else{
			subtableObj.put("mainColName", "");
		}
		subtablesArray.add(subtableObj);
		
		subtableObj = new JSONObject();
		subtableObj.put("fcflag", "fcflag0");
		subtableObj.put("tablename", "subtable0");
		subtableObj.put("colName", "subcolname_1");
		subtableObj.put("mainColName", "unique");
		subtablesArray.add(subtableObj);
		
		parm.put("subtables", subtablesArray);
		return parm;
	}
	
	public static SysBxx addTable(String tablename, Integer relationType, Matcher<Object> eq){
		JSONObject parm = prepareData(tablename, BLX.SingleTable.getType(), null, null, eq);
		return addTableByJSON(parm.getString("tablename"), parm, eq);
	}
	public static SysBxx addTableByJSON(String tablename, JSONObject parm, Matcher<Object> eq){
		System.err.println("addTableByJSON:");
		System.err.println(parm);
		JSONObject json = httpPost(basePath+"/form", parm, eq);
		SysBxx tab = new SysBxx();
		tab.setId(json.getString(ID));
		tab.setVersion(json.getInteger(VERSION));
		tab.setIsdbsyn(json.getString("isdbsyn"));
		tab.setBm(tablename);
		tab.setZb(parm.getJSONArray("subtables").toJSONString());
		System.err.println("tablename="+tablename+", id="+tab.getId());
		return tab;
	}
	
	@Test
	public void testBaseColumn(){
		ValidatableResponse response = given().request().then().get(basePath+"/form/basecolumns").then();
		JSONObject json = JSONObject.parseObject(response.extract().asString());
		System.err.println(json);
	}
	
}
