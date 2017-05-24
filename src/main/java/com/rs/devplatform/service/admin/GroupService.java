package com.rs.devplatform.service.admin;

import java.util.List;

import com.rs.framework.common.entity.persistent.SysGroup;

public interface GroupService {
	public boolean addGroup(SysGroup group);
	public boolean deleteGroup(List<String> groupIds);
	public boolean updateGroup(SysGroup group);
	public List<SysGroup> listGroup();
}
