package com.rs.devplatform.service.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.devplatform.conf.db.DynamicDataSource;
import com.rs.devplatform.service.common.LoginService;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.persistent.SysUsers;
import com.rs.framework.common.utils.RsPwdUtil;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	RsGeneralMapper generalMapper;

	@Override
	public SysUsers login(String username, String password) {
		SysUsers user = new SysUsers();
		user.setName(username);
		List<SysUsers> users = generalMapper.selectList(new RsEntityWrapper<>(user));
		if(users!=null && !users.isEmpty()){
			user = users.get(0);
			if(RsPwdUtil.validatePwd(password, user.getPwd())){
				return users.get(0);
			}else{
				return null;
			}
		}
		return null;
	}

	@Override
	public SysUsers login(String username, String password, String pjId) {
		DynamicDataSource.setDs4Project(pjId);
		return login(username, password);
	}

}
