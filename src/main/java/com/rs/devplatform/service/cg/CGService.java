package com.rs.devplatform.service.cg;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.common.BuzException;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysColumns;
import com.rs.devplatform.persistent.SysCustbt;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.persistent.SysZqjs;
import com.rs.devplatform.persistent.SysZqsql;
import com.rs.devplatform.vo.TableVO;

public interface CGService {

	public SysBxx getForm(String tableId, String flag) throws BuzException;
	
	public JSONObject getFormMasterDetail(String tableId, String flag) throws BuzException;
	
	public List<SysZdxx> getZdxxList(String tableId, String flag);

	public boolean addForm(TableVO tableVO) throws BuzException;
	
	public boolean updateForm(TableVO tableVO) throws BuzException;
	
	public boolean deleteForm(String pjId, SysBxx table, boolean forceDrop);
	
	//自定义按钮的增、删、改
	public boolean addCustbt(SysCustbt custbt);
	
	public boolean updateCustbt(SysCustbt custbt);
	
	public boolean deleteCustbt(List<String> idList);
	
	//增强JS的增、删、改
	public boolean addZqjs(SysZqjs zqjs);
	
	public boolean updateZqjs(SysZqjs zqjs);
	
	public boolean deleteZqjs(String id);
	
	//增强sql的增、删、改
	public boolean addZqsql(SysZqsql zqsql);
		
	public boolean updateZqsql(SysZqsql zqsql);
		
	public boolean deleteZqsql(String id);

	/**
	 * 通过表名获取当前可用的字段列表
	 * 
	 * @param tablename
	 * @return
	 */
	public List<SysColumns> getCurrentAvailableColumns(String tablename);
}
