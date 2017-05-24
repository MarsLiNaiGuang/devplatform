package com.rs.devplatform.service.manager.impl;

import static  com.rs.devplatform.vo.cg.SysColumnsConverter.convertZdxxList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.common.BuzException;
import com.rs.devplatform.common.CGConstants;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysPj;
import com.rs.devplatform.persistent.SysPj2u;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.persistent.mapper.SysProjectMapper;
import com.rs.devplatform.service.admin.RsLiquibaseService;
import com.rs.devplatform.service.manager.ManagerService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.persistent.SysUsers;

import liquibase.exception.LiquibaseException;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	private final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);

	@Autowired
	SysProjectMapper pjMapper;
	@Autowired
	RsGeneralMapper generalMapper;
	@Autowired
	RsLiquibaseService liquibaseService;
	
	
	@Override
	public List<SysUsers> getPjMembers(String pjId, String userType, String userId) {
		return pjMapper.getAssignedUser4Pj(pjId, userType, userId);
	}

	@Override
	@Transactional
	public boolean addPjMembers(String pjId, JSONArray users) {
		if(users==null){
			return true;
		}
		SysPj2u puParm = new SysPj2u();
		puParm.setPjId(pjId);
		puParm.setDeleted(Constants.DelInd.FALSE);
		List<SysPj2u> puList = generalMapper.selectList(new RsEntityWrapper<>(puParm));
		Set<String> userIds = new HashSet<String>();
		if(puList!=null){
			for(SysPj2u pu:puList){
				userIds.add(pu.getUserId());
			}
		}
		int size = users.size();
		SysPj2u pua = null;
		String uida = null;
		JSONObject jsn = null;
		List<SysPj2u> addList = new ArrayList<SysPj2u>(size);
		for(int i=0;i<size;++i){
			jsn = users.getJSONObject(i);
			uida = jsn.getString(Constants.ID);
			if(!userIds.contains(uida)){
				pua = new SysPj2u();
				pua.setPjId(pjId);
				pua.setUserId(uida);
				pua.setUserType(jsn.getString(Constants.USER_TYPE));
				addList.add(pua);
			}
		}
		if(!addList.isEmpty()){
			generalMapper.insertBatch(addList);
		}
		return true;
	}

	@Override
	@Transactional
	@Deprecated
	public boolean deletePjMembers(String pjId, JSONArray users) {
		if(users!=null){
			SysPj2u puParm = new SysPj2u();
			puParm.setPjId(pjId);
			for(int i=0;i<users.size();++i){
				String userId = users.getString(i);
				if(!StringUtils.isEmpty(userId)){
					puParm.setUserId(userId);
					generalMapper.deleteSelective(puParm);
				}
			}
		}
		return true;
	}

	@Override
	public boolean addUpdateOrDeletePjMembers(String pjId, JSONArray users) {
		SysPj2u puParm = new SysPj2u();
		puParm.setPjId(pjId);
		if(users==null || users.isEmpty()){
			generalMapper.deleteSelective(puParm);
			return true;
		}
		puParm.setDeleted(Constants.DelInd.FALSE);
		List<SysPj2u> puList = generalMapper.selectList(new RsEntityWrapper<>(puParm));
		Map<String,SysPj2u> userIdTypes = new HashMap<>();
		if(puList!=null){
			for(SysPj2u pu:puList){
				userIdTypes.put(pu.getUserId(), pu);
			}
		}
		int size = users.size();
		List<SysPj2u> addList = new ArrayList<>(size);
		List<String> deleteIdList = new ArrayList<>(size);
		
		String uida = null;
		JSONObject jsn = null;
		String typeUpd = null;
		for(int i=0;i<size;++i){
			jsn = users.getJSONObject(i);
			uida = jsn.getString(Constants.ID);
			typeUpd = jsn.getString(Constants.USER_TYPE);//更新的userType: PM/TM/""
			SysPj2u existPU = userIdTypes.get(uida);
			String originalType = existPU==null?null:existPU.getUserType();
			if(originalType!=null){
				userIdTypes.remove(uida);
				if(typeUpd==null || typeUpd.trim().isEmpty()){
					deleteIdList.add(existPU.getId());
				}else if(!originalType.equalsIgnoreCase(typeUpd)){
					SysPj2u updEntity = new SysPj2u();
					updEntity.setUserType(typeUpd);
					updEntity.setVersion(existPU.getVersion()+1);
					SysPj2u where = new SysPj2u();
					where .setPjId(pjId);
					where.setUserId(uida);
					generalMapper.updateSelective(updEntity, where);
				}
			}
			else{
				//no userId found
				if(typeUpd!=null && !typeUpd.trim().isEmpty()){
					SysPj2u insert = new SysPj2u();
					insert.setPjId(pjId);
					insert.setUserId(uida);
					insert.setUserType(typeUpd);
					addList.add(insert);
				}
			}
		}
		if(!addList.isEmpty()){
			generalMapper.insertBatch(addList);
		}
		userIdTypes.values().forEach((x)->{
			deleteIdList.add(x.getId());
		});
		if(!deleteIdList.isEmpty()){
			generalMapper.deleteBatchIds(deleteIdList, SysPj2u.class);
		}
		return true;
	}

	@Override
	public boolean initProjectDB(String pjId, Integer version) throws BuzException {
		if(StringUtils.isEmpty(pjId) || version==null){
			return false;
		}
		SysPj pjParm = new SysPj();
		pjParm.setId(pjId);
		pjParm.setVersion(version);
		SysPj pj = generalMapper.selectOne(pjParm);
		if(pj==null){
			return false;
		}
		String initFlag = pj.getInit();
		if(Constants.YES.equals(initFlag)){
			logger.warn("initProjectDB(pjId="+pjId+", version="+version+"): already init before, init ignored");
			return false;
		}
		List<String> initTableNames = CGConstants.InitTables.baseTables;
		List<String> changeLogFilePaths = new ArrayList<>(initTableNames.size());
		for(String tableName:initTableNames){
			SysBxx parm = new SysBxx();
			parm.setBm(tableName);
			SysBxx table = generalMapper.selectOne(parm);
			if(table==null){
				logger.warn("initProjectDB(): no record found for intiTableName="+tableName+", this table ignored");
				continue;
			}
			String tableId = table.getId();
			SysZdxx zdParm = new SysZdxx();
			zdParm.setBid(tableId);
			zdParm.setDeleted(Constants.DelInd.FALSE);
			RsEntityWrapper<SysZdxx> wrapper = new RsEntityWrapper<>(zdParm);
			List<SysZdxx> ziduanList = generalMapper.selectList(wrapper);
			if(ziduanList==null || ziduanList.isEmpty()){
				logger.warn("initProjectDB(): no zdxx record found for intiTableName="+tableName+", this table ignored");
				continue;
			}
			try {
				String changeLogFilePath = liquibaseService.generateLiquibaseChangeLog4Pj(pjId, tableId, tableName, table.getNr(), true, convertZdxxList(ziduanList), null, null, false);
				if(changeLogFilePath!=null){
					changeLogFilePaths.add(changeLogFilePath);
				}else{
					logger.warn("initProjectDB(pjId="+pjId+"): generate change log successfully, but no file generated");
				}
			} catch (LiquibaseException e) {
				e.printStackTrace();
				logger.error("initProjectDB():generateLiquibaseChangeLog failed for tableId="+tableId+", tableName="+tableName, e);
			}
		}
		if(changeLogFilePaths.isEmpty()){
			logger.error("initProjectDB(): no changeLogFile generated successfully for pjId="+pjId);
			throw new BuzException(HttpStatus.INTERNAL_SERVER_ERROR, "failed to generate changeLogFile");
		}
		for(String changeLogFilePath:changeLogFilePaths){
			try {
				liquibaseService.executeUpdate4File(pjId, changeLogFilePath);
			} catch (LiquibaseException e) {
				logger.error("initProjectDB(): failed to executeUpdate4File("+changeLogFilePath+").", e);
				throw new BuzException(HttpStatus.INTERNAL_SERVER_ERROR, "failed to init DB for pjId="+pjId);
			}
		}
		pj.setInit(Constants.YES);
		pj.setVersion(version+1);
		generalMapper.updateById(pj);
		return true;
	}

	
}
