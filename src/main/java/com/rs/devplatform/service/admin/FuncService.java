package com.rs.devplatform.service.admin;

import java.util.List;

import com.rs.framework.common.entity.persistent.SysFunc;

public interface FuncService {
	public boolean addFunc(SysFunc func);
	public boolean deleteFunc(List<String> idList);
	public boolean updateFunc(SysFunc func);
}
