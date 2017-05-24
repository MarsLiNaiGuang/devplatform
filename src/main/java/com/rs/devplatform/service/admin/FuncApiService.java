package com.rs.devplatform.service.admin;

import java.util.List;

import com.rs.framework.common.entity.persistent.SysFuncgp;
import com.rs.framework.common.entity.vo.FuncEntry;

public interface FuncApiService {
	
	@Deprecated
	public List<FuncEntry> getColumnsByFuncGroupId(String funcGroupId);
	
	public List<FuncEntry> getColumnsByMenuId(String menuId);
	
	@Deprecated
	public List<FuncEntry> getButtonsByFuncGroupId(String funcGroupId);
	
	public List<FuncEntry> getButtonsByMenuId(String menuId);
	
	@Deprecated
	public List<FuncEntry> getSearchCriteriasByFuncGroupId(String funcGroupId); 
	
	public List<SysFuncgp> getAllFunctions();

}
