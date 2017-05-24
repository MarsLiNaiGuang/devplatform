package com.rs.devplatform.service.admin.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rs.devplatform.persistent.mapper.SysRoles2UserMapper;
import com.rs.devplatform.service.admin.U2RService;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.persistent.SysU2r;
import com.rs.framework.common.entity.persistent.SysUsers;

@Service
public class U2RServiceImpl implements U2RService{
	
	private final Logger log = LoggerFactory.getLogger(U2RServiceImpl.class);
	
	@Autowired
	SysRoles2UserMapper u2rMapper;
	
	@Autowired
	RsGeneralMapper generalMapper;
	
	@Override
	@Transactional
	public boolean addU2r(String roleId, List<String> userIdList) {
		SysU2r u2r = new SysU2r();
		u2r.setRolesId(roleId);
		RsEntityWrapper<SysU2r> wrapper = new RsEntityWrapper<>(u2r);
		wrapper.in("U2R_USERID", userIdList);
		List<SysU2r> urList = generalMapper.selectList(wrapper);
		if(urList!=null && !urList.isEmpty()){
			Set<String> existUserIds = new HashSet<String>(urList.size());
			for(SysU2r ur:urList){
				existUserIds.add(ur.getUsersId());
			}
			Object[] filteredIds = userIdList.stream().filter((x)->{
				return !existUserIds.contains(x);
			}).toArray();
			userIdList.clear();
			for(Object obj:filteredIds){
				userIdList.add((String)obj);
			}
		}
		List<SysU2r> sysu2rList = new ArrayList<>(userIdList.size());
		for(String userId:userIdList){
			sysu2rList.add(new SysU2r(userId, roleId));
		}
		generalMapper.insertBatch(sysu2rList);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteU2r(String roleId, List<String> userIdList) {
		SysU2r parm = new SysU2r();
		parm.setRolesId(roleId);
		RsEntityWrapper<SysU2r> wrapper = new RsEntityWrapper<>(parm);
		wrapper.in("U2R_USERID", userIdList);
		List<SysU2r> urList = generalMapper.selectList(wrapper);
		if(urList==null || urList.isEmpty()){
			log.warn("deleteU2R(): no u2r records found for roleId="+roleId);
			return true;
		}
		List<String> u2rIdList = new ArrayList<String>(urList.size());
		for(SysU2r u2r:urList){
			u2rIdList.add(u2r.getId());
		}
		generalMapper.deleteBatchIds(u2rIdList, SysU2r.class);
		return true;
	}
	
	@Override
	@Transactional
	public List<SysUsers> getU2R(String roleId) {
		return u2rMapper.getUsers4Role(roleId);
	}

	@Override
	@Transactional
	public boolean updateu2r(SysU2r u2r) {
		int effRows = generalMapper.updateById(u2r);
		return (effRows > 0);
	}

}
