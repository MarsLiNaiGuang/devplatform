package com.rs.devplatform.service.admin;

import java.util.List;

import com.rs.framework.common.entity.persistent.SysRoles;
import com.rs.framework.common.entity.persistent.SysUsers;

public interface RoleService {
	
	public boolean addRole(SysRoles role);
	
	public List<SysUsers> getUsers2Role(String roleId);
	
	public boolean addUsers2Role(List<String> userIds, String roleId);
	
	public boolean deleteUsers2Role(List<String> userIds, String roleId);
	
	public boolean deleteRole(String roleId);
	
	public boolean deleteRoles(List<String> roleIds);
	
	public boolean checkRoleHasUsers(String roleId);
	
	public boolean updateRole(SysRoles role);
	

}
