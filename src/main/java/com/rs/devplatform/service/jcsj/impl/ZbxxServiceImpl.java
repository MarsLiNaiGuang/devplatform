/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.service.jcsj.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.rs.devplatform.persistent.mapper.ZbxxMapper;


import com.rs.devplatform.persistent.jcsj.Zbxx;
import com.rs.devplatform.service.jcsj.ZbxxService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsGeneralMapper;


@Service
public class ZbxxServiceImpl implements ZbxxService {

	@Autowired
	RsGeneralMapper generalMapper;
	@Autowired
	ZbxxMapper zbxxMapper;
	
	@Transactional
	public boolean add(Zbxx zbxx){
	    zbxx.setDeleted(Constants.DelInd.FALSE);
		generalMapper.insert(zbxx);
		return true;
	}
	
	@Transactional
	public boolean update(Zbxx zbxx){
		String id = zbxx.getId();
		Integer version = zbxx.getVersion();
		if(id==null || version==null){
			return false;
		}
		Zbxx entityWhere = new Zbxx();
		entityWhere.setId(id);
		entityWhere.setDeleted(Constants.DelInd.FALSE);
		entityWhere.setVersion(version);
		zbxx.setVersion(version+1);
		zbxx.setDeleted(Constants.DelInd.FALSE);
		int effRows = generalMapper.update(zbxx, entityWhere);
		if(effRows==0){//update failed
			return false;
		}else{
			return true;
		}
	}
	
	@Transactional
	public boolean delete(Zbxx zbxx){
		String id = zbxx.getId();
		Integer version = zbxx.getVersion();
		if(id==null || version==null){
			return false;
		}
		Zbxx entityWhere = new Zbxx();
		entityWhere.setId(id);
		entityWhere.setDeleted(Constants.DelInd.FALSE);
		entityWhere.setVersion(version);
		zbxx.setDeleted(Constants.DelInd.TRUE);
		zbxx.setVersion(version + 1);
		int effRows = generalMapper.updateSelective(zbxx, entityWhere);
		if(effRows==0){//update failed
			return false;
		}else{
			return true;
		}
	}
	
	
	   @Transactional
	   public boolean deleteBatch(String[] ids){
		List<String> idList = Arrays.asList(ids);
		int effRow = generalMapper.deleteBatchIds(idList, Zbxx.class);
		if(effRow!=0){
			return true;
		}
		return false;
	}
	

	
	
}
