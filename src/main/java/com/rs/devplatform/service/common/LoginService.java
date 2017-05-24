package com.rs.devplatform.service.common;

import com.rs.framework.common.entity.persistent.SysUsers;

public interface LoginService {

	public SysUsers login(String username, String password);
	public SysUsers login(String username, String password, String pjId);
	
}
