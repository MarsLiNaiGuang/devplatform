/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.service.sample.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rs.devplatform.persistent.sample.CgSample;
import com.rs.devplatform.service.sample.CgSampleService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsGeneralMapper;

@Service
public class CgSampleServiceImpl implements CgSampleService {

	@Autowired
	RsGeneralMapper generalMapper;
	
	@Transactional
	public boolean add(CgSample cgSample){
		generalMapper.insert(cgSample);
		return true;
	}
	
	@Transactional
	public boolean update(CgSample cgSample){
		String id = cgSample.getId();
		Integer version = cgSample.getVersion();
		if(id==null || version==null){
			return false;
		}
		CgSample entityWhere = new CgSample();
		entityWhere.setId(id);
		entityWhere.setDeleted(Constants.DelInd.FALSE);
		entityWhere.setVersion(version);
		cgSample.setVersion(version+1);
		int effRows = generalMapper.updateSelective(cgSample, entityWhere);
		if(effRows==0){//update failed
			return false;
		}else{
			return true;
		}
	}
	
	@Transactional
	public boolean delete(CgSample cgSample){
		String id = cgSample.getId();
		Integer version = cgSample.getVersion();
		if(id==null || version==null){
			return false;
		}
		CgSample entityWhere = new CgSample();
		entityWhere.setId(id);
		entityWhere.setDeleted(Constants.DelInd.FALSE);
		entityWhere.setVersion(version);
		CgSample entity = new CgSample();
		entity.setDeleted(Constants.DelInd.TRUE);
		entity.setVersion(version+1);
		int effRows = generalMapper.updateSelective(entity, entityWhere);
		if(effRows==0){//update failed
			return false;
		}else{
			return true;
		}
	}
	
	@Transactional
	public boolean deleteBatch(String[] ids){
		List<String> idList = Arrays.asList(ids);
		int effRow = generalMapper.deleteBatchIds(idList, CgSample.class);
		if(effRow!=0){
			return true;
		}
		return false;
	}

}
