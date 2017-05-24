package com.rs.devplatform.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.devplatform.service.admin.GroupService;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.persistent.SysGroup;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	RsGeneralMapper generalMapper;
	
	@Override
	public boolean addGroup(SysGroup group) {
		int count = generalMapper.insert(group);
		return count > 0;
	}

	@Override
	public boolean deleteGroup(List<String> groupIds) {
		int count = generalMapper.deleteBatchIds(groupIds, SysGroup.class);
		return count > 0;
	}

	@Override
	public boolean updateGroup(SysGroup group) {
		int count = generalMapper.updateSelectiveById(group);
		return count > 0;
	}

	@Override
	public List<SysGroup> listGroup() {
		SysGroup group = new SysGroup();
		return generalMapper.selectList(group);
	}

}
