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
import com.rs.devplatform.persistent.mapper.BmpzMapper;


import com.rs.devplatform.persistent.jcsj.Bmpz;
import com.rs.devplatform.service.jcsj.BmpzService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsGeneralMapper;


@Service
public class BmpzServiceImpl implements BmpzService {

	@Autowired
	RsGeneralMapper generalMapper;
	@Autowired
	BmpzMapper bmpzMapper;
	
	@Transactional
	public boolean add(Bmpz bmpz){
	    bmpz.setDeleted(Constants.DelInd.FALSE);
		generalMapper.insert(bmpz);
		return true;
	}
	
	@Transactional
	public boolean update(Bmpz bmpz){
		String id = bmpz.getId();
		Integer version = bmpz.getVersion();
		if(id==null || version==null){
			return false;
		}
		Bmpz entityWhere = new Bmpz();
		entityWhere.setId(id);
		entityWhere.setDeleted(Constants.DelInd.FALSE);
		entityWhere.setVersion(version);
		bmpz.setVersion(version+1);
		bmpz.setDeleted(Constants.DelInd.FALSE);
		int effRows = generalMapper.update(bmpz, entityWhere);
		if(effRows==0){//update failed
			return false;
		}else{
			return true;
		}
	}
	
	@Transactional
	public boolean delete(Bmpz bmpz){
		String id = bmpz.getId();
		Integer version = bmpz.getVersion();
		if(id==null || version==null){
			return false;
		}
		Bmpz entityWhere = new Bmpz();
		entityWhere.setId(id);
		entityWhere.setDeleted(Constants.DelInd.FALSE);
		entityWhere.setVersion(version);
		bmpz.setDeleted(Constants.DelInd.TRUE);
		bmpz.setVersion(version + 1);
		int effRows = generalMapper.updateSelective(bmpz, entityWhere);
		if(effRows==0){//update failed
			return false;
		}else{
			return true;
		}
	}
	
	
	   @Transactional
	   public boolean deleteBatch(String[] ids){
		List<String> idList = Arrays.asList(ids);
		int effRow = generalMapper.deleteBatchIds(idList, Bmpz.class);
		if(effRow!=0){
			return true;
		}
		return false;
	}
	

	
	
}
