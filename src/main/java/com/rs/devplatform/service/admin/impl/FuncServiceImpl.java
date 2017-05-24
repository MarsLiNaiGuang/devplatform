package com.rs.devplatform.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rs.devplatform.service.admin.FuncService;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.persistent.SysFunc;

@Service
public class FuncServiceImpl implements FuncService {

	@Autowired
	private RsGeneralMapper generalMapper;
	
	@Override
	@Transactional
	public boolean addFunc(SysFunc func) {
		generalMapper.insert(func);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteFunc(List<String> idList) {
		Integer effRows = generalMapper.deleteBatchIds(idList, SysFunc.class);
		return effRows > 0;
	}

	@Override
	@Transactional
	public boolean updateFunc(SysFunc func) {
		String id = func.getId();
		if(id == null)
			return false;
		SysFunc entityWhere = new SysFunc();
		entityWhere.setId(id);
		int effRows = generalMapper.update(func, entityWhere);
		if(effRows == 0){
			return false;
		}else{
			return true;
		}
	}

}
