package com.rs.devplatform.service.admin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.devplatform.persistent.SysCdtype;
import com.rs.devplatform.persistent.SysCdval;
import com.rs.devplatform.service.admin.DataDictService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;

@Service
public class DataDictServiceImpl implements DataDictService {
	
	@Autowired
	RsGeneralMapper generalMapper;
	
	@Override
	public List<SysCdval> getValListByCdtype(String cdtype) {
		SysCdval cdvalParm = new SysCdval();
		cdvalParm.setCdtype(cdtype);
		cdvalParm.setDeleted(Constants.DelInd.FALSE);
		return generalMapper.selectList(new RsEntityWrapper<>(cdvalParm));
	}

	@Override
	public SysCdtype createCodeType(SysCdtype cdtype) {
		generalMapper.insert(cdtype);
		return cdtype;
	}

	@Override
	public boolean updateCodeType(SysCdtype cdtype) {
		SysCdtype whereEntity = new SysCdtype();
		whereEntity.setId(cdtype.getId());
		whereEntity.setVersion(cdtype.getVersion());
		whereEntity.setDeleted(Constants.DelInd.FALSE);
		cdtype.setVersion(cdtype.getVersion()+1);
		int effRows = generalMapper.updateSelective(cdtype, whereEntity);
		if(effRows==0){//update failed
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteCodeType(SysCdtype cdtype) {
		String typeId = cdtype.getId();
		SysCdtype whereEntity = new SysCdtype();
		whereEntity.setId(typeId);
		whereEntity.setVersion(cdtype.getVersion());
		whereEntity.setDeleted(Constants.DelInd.FALSE);
		
		SysCdtype entity = new SysCdtype();
		entity.setId(typeId);
		entity.setVersion(cdtype.getVersion()+1);
		entity.setDeleted(Constants.DelInd.TRUE);
		int effRows = generalMapper.updateSelective(entity, whereEntity);
		if(effRows==0){
			return false;
		}
		cdtype = generalMapper.selectById(typeId, SysCdtype.class);
		SysCdval cdValWhere = new SysCdval();
		cdValWhere.setCdtype(cdtype.getCdtype());
		cdValWhere.setDeleted(Constants.DelInd.FALSE);
		SysCdval cdVal = new SysCdval();
		cdVal.setDeleted(Constants.DelInd.TRUE);
		generalMapper.updateSelective(cdVal, cdValWhere);
		return true;
	}

	@Override
	public boolean addCodeVal4Type(String typeId, SysCdval[] valArray) {
		SysCdtype cdtype = generalMapper.selectById(typeId, SysCdtype.class);
		if(cdtype==null || Constants.DelInd.TRUE.equals(cdtype.getDeleted())){
			return false;
		}
		String codeTypeStr = cdtype.getCdtype();
		List<SysCdval> existList = getValListByCdtype(codeTypeStr);
		Map<String,String> valMap = new HashMap<String,String>();
		Set<String> valSet = new HashSet<String>();
		if(existList!=null){
			existList.stream().forEach((x)->{
				valMap.put(x.getCdval(), x.getId());
				valSet.add(x.getCdval());
			});
		}
		List<SysCdval> list = new ArrayList<SysCdval>(valArray.length);
		for(SysCdval cdval:valArray){
			if(!valSet.contains(cdval.getCdval())){
				cdval.setCdtype(codeTypeStr);
				list.add(cdval);
			}else{
				cdval.setId(valMap.get(cdval.getCdval()));//return id to front
			}
		}
		generalMapper.insertBatch(list);
		return true;
	}

	@Override
	public boolean deleteCodeVal(String cdvalId) {
		SysCdval cdval = new SysCdval();
		cdval.setId(cdvalId);
		cdval.setDeleted(Constants.DelInd.TRUE);
		generalMapper.updateSelectiveById(cdval);
		return true;
	}

	@Override
	public boolean updateCodeVal(SysCdval cdval) {
		SysCdval cdvalDB = generalMapper.selectById(cdval.getId(), SysCdval.class);
		if(cdvalDB==null || Constants.DelInd.TRUE.equals(cdvalDB.getDeleted())){
			return false;
		}
		cdval.setVersion(cdvalDB.getVersion()+1);
		generalMapper.updateSelectiveById(cdval);
		return true;
	}

}
