package com.rs.devplatform.service.admin;

import java.util.List;

import com.rs.framework.common.entity.persistent.SysUsers;

public interface UserService {
	
	public boolean addUser(SysUsers user);
	public boolean updateUser(SysUsers user);
	public boolean deleteUsers(List<String> userIds);

}
