package com.rs.devplatform.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.DevPlatformApp;
import com.rs.devplatform.common.CGConstants.InputTypes;
import com.rs.devplatform.common.CGConstants.SearchTypes;
import com.rs.devplatform.test.base.TestBase;
import com.rs.devplatform.vo.ColumnVO;
import com.rs.devplatform.vo.onlinefc.OnlineFcColumn;
import com.rs.devplatform.vo.onlinefc.OnlineFcCondition;
import com.rs.devplatform.vo.onlinefc.OnlineFuncVO;
import com.rs.devplatform.vo.onlinefc.SubtablesVO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DevPlatformApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class OnlineFuncCtrlTest extends TestBase {
	
	private static final String sqlPath = "/cg/onlinefc/sql";
	private static final String basePath = "/cg/form";
	private static final String SQL_PARM = "sql";
	private static final String YES = "Y";
	private static final String NO = "N";
	
	
	@Test
	public void testParseSqlConditionParams(){
		JSONObject parm = new JSONObject();
		String sql = "";
		parm.put(SQL_PARM, sql);
		JSONObject result = httpPost(sqlPath, parm, BAD_REQUEST);
		System.err.println(result);
		sql = "select username as name, password  from user where username=#{abc} and password='123456'";
		parm.put(SQL_PARM, sql);
		result = httpPost(sqlPath, parm, SUCCESS);
		System.err.println(result);
		sql = "select username as name, password as pwd from user where username=#{abc}  password='123456'";
		parm.put(SQL_PARM, sql);
		result = httpPost(sqlPath, parm, BAD_REQUEST);
		System.err.println(result);
	}
	@Test
	public void testParseSqlConditionParams2(){
		JSONObject parm = new JSONObject();
		String sql = "";
		JSONObject result = null;
		sql = "select username as name, password, dept.dept_name from user, sys_dept dept where dept.dept_id=user.dept_id and  username=#{abc} and password='123456'";
		parm.put(SQL_PARM, sql);
		result = httpPost(sqlPath, parm, SUCCESS);
		System.err.println(result);
	}
	
	@Test
	public void testSave(){
		OnlineFuncVO funcVO = addFuncBySQL("jutest_sqlsave");
		CGCtrlTest.deleteTable(funcVO.getTableId(), funcVO.getVersion(), SUCCESS);
	}
	
	public static OnlineFuncVO addFuncBySQL(String tablename){
		if(tablename==null){
			tablename = "jutest_sqlsave";
		}
		JSONObject parm = new JSONObject();
		String sql = "";
		JSONObject result = null;
		sql = "select username as name, password, dept.dept_name from user, sys_dept dept where dept.dept_id=user.dept_id and  username=#{abc} and password='123456'";
		parm.put(SQL_PARM, sql);
		result = httpPost(sqlPath, parm, SUCCESS);
		System.err.println(result);
		OnlineFuncVO funcVO = new OnlineFuncVO();
		funcVO.setTablename(tablename);
		funcVO.setTabledescp("test");
		funcVO.setPktype("UUID");
		funcVO.setTabletype(1);
		funcVO.setSql(sql);
		JSONArray columns = result.getJSONArray("columns");
		JSONArray conditions = result.getJSONArray("conditions");
		
		OnlineFcColumn[] columnsArray = new OnlineFcColumn[columns.size()];
		for(int i=0;i<columns.size();++i){
			JSONObject json = columns.getJSONObject(i);
			OnlineFcColumn col = json.toJavaObject(OnlineFcColumn.class);
			col.setIsformdisp(YES);
			col.setColType("string");
			col.setColMark("mark"+i);
			col.setCol_isnull(YES);
			col.setIsformdisp(YES);
			col.setIssearch(YES);
			col.setInputtype(InputTypes.text);
			if(i==0){
				col.setCol_ispk(YES);
				col.setCol_isnull(NO);
				col.setIsformdisp(NO);
				col.setIssearch(NO);
			}else{
				col.setSearchtype(SearchTypes.FUZZY);
				
			}
			columnsArray[i] = col;
		}
		funcVO.setColumns(columnsArray);
		int conditionSize = conditions==null?0:conditions.size();
		OnlineFcCondition[] conditionsArray = new OnlineFcCondition[conditionSize+2];
		for(int i=0;i<conditionSize;++i){
			JSONObject json = conditions.getJSONObject(i);
			OnlineFcCondition cond = json.toJavaObject(OnlineFcCondition.class);
			cond.setInputtype(InputTypes.text);
			cond.setColMark("mark"+i);
			cond.setSearchtype(SearchTypes.FUZZY);
			cond.setIssearch(YES);
			cond.setColType("string");
			conditionsArray[i] = cond;
		}
		OnlineFcCondition cond = new OnlineFcCondition();
		BeanUtils.copyProperties(columnsArray[1], cond);
		conditionsArray[conditionSize]=cond;//duplicate column check
		OnlineFcCondition addCond = new OnlineFcCondition();
		addCond.setColName("newColumn");
		addCond.setColMark("newColumn mark");
		addCond.setColType("int");
		addCond.setInputtype(InputTypes.text);
		addCond.setInputlen(10);
		addCond.setSearchtype("range");
		conditionsArray[conditionSize+1]=addCond;
		funcVO.setConditions(conditionsArray);
		
		SubtablesVO[] subtables = new SubtablesVO[3];
		for(int i=0;i<3;++i){
			subtables[i] = new SubtablesVO();
			subtables[i].setFcflag("sub_fcflag"+i);
			subtables[i].setColName("sub_col"+i);
			subtables[i].setTablename("sub_table"+i);
			subtables[i].setMainColName("main_col"+i);
		}
		funcVO.setSubtables(subtables);
		funcVO.setFcflag(funcVO.getTablename()+"_flag");
		parm = (JSONObject)JSON.toJSON(funcVO);
		System.err.println("testSave.parm="+parm.toJSONString());
		result = httpPost(basePath, parm, SUCCESS);
		System.err.println(result);
		String tableId = result.getString(ID);
		Integer version = result.getInteger(VERSION);
		funcVO.setTableId(tableId);
		funcVO.setVersion(version);
		return funcVO;
	}
	
	@Test
	public void testGetFuncVO(){
		OnlineFuncVO funcVO = addFuncBySQL("jutest_sqlsave");
		String id = funcVO.getTableId();
		Integer version = funcVO.getVersion();
		
		JSONObject resultJSON = httpGet(basePath+"/"+id);
		JSONArray columnArray = resultJSON.getJSONArray("columns");
		JSONArray conditionArray = resultJSON.getJSONArray("conditions");
		
		Assert.assertEquals(3, columnArray.size());
		Assert.assertEquals(3, conditionArray.size());
		System.err.println(resultJSON);
		
		JSONObject json = httpGet(basePath+"/"+id);
		Assert.assertEquals(resultJSON.toJSONString(), json.toJSONString());
		CGCtrlTest.deleteTable(id, version, SUCCESS);
	}
	
	@Test
	public void testUpdate(){
		OnlineFuncVO createVO = addFuncBySQL("jutest_sqlupd");
		/* insert */
		String id = createVO.getTableId();
		/* get */
		JSONObject resultJSON = httpGet(basePath+"/"+id);
		JSONArray columnArray = resultJSON.getJSONArray("columns");
		JSONArray conditionArray = resultJSON.getJSONArray("conditions");
		JSONArray subtablesArray = resultJSON.getJSONArray("subtables");
		Assert.assertEquals(3, columnArray.size());
		Assert.assertEquals(3, conditionArray.size());
		Assert.assertEquals(3, subtablesArray.size());
		
		System.err.println("before update, json=");
		System.err.println(resultJSON);
		OnlineFuncVO beforeUpdate = resultJSON.toJavaObject(OnlineFuncVO.class);
		Assert.assertEquals(resultJSON.toJSONString(), resultJSON.toJSONString());
		resultJSON = httpPut("/cg/form/"+id, resultJSON);
		Integer versionAfterUpdate = resultJSON.getInteger(VERSION);
		System.err.println("after update, json = "+resultJSON);
		//no change
		Assert.assertEquals(beforeUpdate.getVersion(), versionAfterUpdate);
		
		OnlineFcColumn[] columnVOArray = beforeUpdate.getColumns();
		OnlineFcCondition[] conditionVOArray = beforeUpdate.getConditions();
		SubtablesVO[] subtableVOArray = beforeUpdate.getSubtables();
		int size = columnVOArray.length;
		Map<String,ColumnVO> columnMap = new HashMap<>();
		for(OnlineFcColumn fc:columnVOArray){
			columnMap.put(fc.getCol_id(), fc);
		}
		boolean isUpdated = false;
		boolean isDeleted = false;
		System.err.println("===compare==="+JSON.toJSON(beforeUpdate));
		for(OnlineFcCondition fcc : conditionVOArray){
			String colID_ = fcc.getCol_id();
			String columName = fcc.getColName();
			if(!isUpdated && columnMap.containsKey(colID_)){
				columnMap.get(colID_).setColName(columName+"upd1");
				fcc.setColName(columName+"upd1");
				isUpdated = true;
			}
			if(!isDeleted && !columnMap.containsKey(colID_)){
				fcc.setCol_id(null);
				fcc.setColName(columName+"del1");
				isDeleted = true;
			}
		}
		beforeUpdate.setTabledescp(beforeUpdate.getTabledescp()+" update1");
		subtableVOArray[0].setColName("sub_update_col1");
		
		JSONObject parm = (JSONObject)JSON.toJSON(beforeUpdate);
		System.err.println("===compare==="+parm);
		resultJSON = httpPut("/cg/form/"+id, parm);
		Assert.assertEquals(2, resultJSON.getIntValue(VERSION));
		
		resultJSON = httpGet("/cg/form/"+id);
		OnlineFuncVO afterUpdate = resultJSON.toJavaObject(OnlineFuncVO.class);
		OnlineFcColumn[] columnVOArrayAfter = afterUpdate.getColumns();
		OnlineFcCondition[] conditionVOArrayAfter =afterUpdate.getConditions();
		SubtablesVO[] subtableVOArrayAfter = afterUpdate.getSubtables();
		Assert.assertEquals(size, columnVOArrayAfter.length);
		Assert.assertEquals(size, conditionVOArrayAfter.length);
		Assert.assertEquals(size, subtableVOArrayAfter.length);
		
		Assert.assertEquals("sub_update_col1", subtableVOArrayAfter[0].getColName());
		
		Set<String> set = new HashSet<>(size);
		for(OnlineFcColumn col:columnVOArray){
			set.add(col.getCol_id());
		}
		String newColId = null;
		for(OnlineFcColumn col:columnVOArrayAfter){
			String colID = col.getCol_id();
			if(set.contains(colID)){
				set.remove(colID);
			}else{
				newColId = colID;
			}
		}
		Assert.assertEquals(0, set.size());
		
		set.clear();
		for(OnlineFcCondition col: conditionVOArray){
			set.add(col.getCol_id());
		}
		newColId = null;
		for(OnlineFcCondition col: conditionVOArrayAfter){
			String colID = col.getCol_id();
			if(set.contains(colID)){
				set.remove(colID);
			}else{
				newColId = colID;
			}
		}
		Assert.assertEquals(1, set.size());
		Iterator<String> it = set.iterator();
		String remainID = it.next();
		Assert.assertNotEquals(remainID, newColId);
		CGCtrlTest.deleteTable(id, 2, SUCCESS);
	}
	
	@Test
	public void deleteOnlineFunc(){
		OnlineFuncVO funcVO = addFuncBySQL(null);
		String tableId = funcVO.getTableId();
		Integer version = funcVO.getVersion();
		deleteOnlineFunc(tableId, version);
	}
	
	public static void deleteOnlineFunc(String tableId, Integer version){
		JSONObject parm = new JSONObject();
		parm.put(VERSION, version);
		httpDelete(basePath+"/"+tableId, parm);
		
	}
}
