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
import com.rs.devplatform.service.admin.RoleService;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.persistent.SysRoles;
import com.rs.framework.common.entity.persistent.SysU2r;
import com.rs.framework.common.entity.persistent.SysUsers;

@Service
public class RoleServiceImpl implements RoleService {
	
	private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	RsGeneralMapper generalMapper;
	@Autowired
	SysRoles2UserMapper r2uMapper;
	
	@Override
	@Transactional
	public boolean addRole(SysRoles role) {
		generalMapper.insert(role);
		return true;
	}

	@Override
	@Transactional
	public boolean addUsers2Role(List<String> userIds, String roleId) {
		SysU2r parm = new SysU2r();
		parm.setRolesId(roleId);
		RsEntityWrapper<SysU2r> wrapper = new RsEntityWrapper<>(parm);
		wrapper.in("U2R_USERID", userIds);
		List<SysU2r> urList = generalMapper.selectList(wrapper);
		if(urList!=null && !urList.isEmpty()){
			Set<String> existUserIds = new HashSet<String>(urList.size());
			for(SysU2r ur:urList){
				existUserIds.add(ur.getUsersId());
			}
			Object[] filteredIds = userIds.stream().filter((x)->{
				return !existUserIds.contains(x);
			}).toArray();
			userIds.clear();
			for(Object obj:filteredIds){
				userIds.add((String)obj);
			}
		}
		List<SysU2r> sysu2rList = new ArrayList<>(userIds.size());
		for(String userId:userIds){
			sysu2rList.add(new SysU2r(userId, roleId));
		}
		generalMapper.insertBatch(sysu2rList);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteRole(String roleId) {
		generalMapper.deleteById(roleId, SysRoles.class);
		return true;
	}

	@Override
	public boolean checkRoleHasUsers(String roleId) {
		//TODO:
		return true;
	}

	@Override
	@Transactional
	public boolean updateRole(SysRoles role) {
		generalMapper.updateSelectiveById(role);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteRoles(List<String> roleIds) {
		generalMapper.deleteBatchIds(roleIds, SysRoles.class);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteUsers2Role(List<String> userIds, String roleId) {
		SysU2r parm = new SysU2r();
		parm.setRolesId(roleId);
		RsEntityWrapper<SysU2r> wrapper = new RsEntityWrapper<>(parm);
		wrapper.in("U2R_USERID", userIds);
		List<SysU2r> urList = generalMapper.selectList(wrapper);
		if(urList==null || urList.isEmpty()){
			log.warn("deleteUsers2Role(): no u2r records found for roleId="+roleId);
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
	public List<SysUsers> getUsers2Role(String roleId) {
		return r2uMapper.getUsers4Role(roleId);
	}

}
