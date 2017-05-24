package com.rs.devplatform.service.manager;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.rs.devplatform.common.BuzException;
import com.rs.framework.common.entity.persistent.SysUsers;

public interface ManagerService {

	public List<SysUsers> getPjMembers(String pjId, String userType, String userId);
	
	public boolean addPjMembers(String pjId, JSONArray users);
	
	public boolean addUpdateOrDeletePjMembers(String pjId, JSONArray users);
	
	public boolean deletePjMembers(String pjId, JSONArray users);
	
	public boolean initProjectDB(String pjId, Integer version) throws BuzException;
}
