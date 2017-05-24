package com.rs.devplatform.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rs.devplatform.service.admin.UserService;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.persistent.SysU2r;
import com.rs.framework.common.entity.persistent.SysUsers;
import com.rs.framework.common.utils.RsPwdUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	RsGeneralMapper generalMapper;

	@Override
	@Transactional
	public boolean addUser(SysUsers user) {
		user.setPwd(RsPwdUtil.encode(user.getPwd()));
		generalMapper.insert(user);
		return true;
	}

	@Override
	@Transactional
	public boolean updateUser(SysUsers user) {
		generalMapper.updateSelectiveById(user);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteUsers(List<String> userIds) {
		generalMapper.deleteBatchIds(userIds, SysUsers.class);
		SysU2r u2r = new SysU2r();
		RsEntityWrapper<SysU2r> wrapper = new RsEntityWrapper<>(u2r);
		wrapper.in("u2r_userid", userIds);
		generalMapper.deleteByEW(wrapper);
		return true;
	}

}
