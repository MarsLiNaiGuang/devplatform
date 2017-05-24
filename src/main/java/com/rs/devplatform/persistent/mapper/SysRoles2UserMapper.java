package com.rs.devplatform.persistent.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rs.framework.common.entity.persistent.SysUsers;

public interface SysRoles2UserMapper {
	
	public List<SysUsers> getUsers4Role(@Param("roleId") String roleId);

}
