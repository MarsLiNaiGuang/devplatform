package com.rs.devplatform.service.admin.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.rs.devplatform.conf.db.DynamicDataSource;
import com.rs.devplatform.persistent.SysPj;
import com.rs.devplatform.persistent.SysPj2res;
import com.rs.devplatform.persistent.SysPj2u;
import com.rs.devplatform.persistent.mapper.SysProjectMapper;
import com.rs.devplatform.service.admin.ProjectService;
import com.rs.devplatform.vo.PjDBInfoVO;
import com.rs.devplatform.vo.ProjectInfo;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.persistent.SysUsers;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	private final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Autowired
	SysProjectMapper pjMapper;
	@Autowired
	RsGeneralMapper generalMapper;
	
	@Override
	public List<SysPj> getPjList4User(String userId) {
		return pjMapper.getProjects4User(userId, Constants.DelInd.FALSE);
	}

	@Override
	@Transactional
	public SysPj createProject(ProjectInfo pjInfo, String userId) {
		SysPj project = new SysPj();
		project.setName(pjInfo.getName());
		generalMapper.insert(project);
		String pjId = project.getId();
		SysPj2res resDB = pjInfo.getDb();
		if(resDB!=null){
			resDB.setPjId(pjId);
			generalMapper.insert(resDB);
		}
		/*SysPj2u pj2u = new SysPj2u();
		pj2u.setPjId(pjId);
		pj2u.setUserId(userId);
		generalMapper.insert(pj2u);*///platform admin no need to insert midTable@20161103
		return project;
	}

	@Override
	public boolean updateProject(ProjectInfo pjInfo, String userId) {
		String pjId = pjInfo.getId();
		Integer version = pjInfo.getVersion();
		if(version==null){
			return false;
		}
		SysPj projectWhere = new SysPj();
		projectWhere.setId(pjId);
		projectWhere.setDeleted(Constants.DelInd.FALSE);
		projectWhere.setVersion(version);
		SysPj project = new SysPj();
		project.setName(pjInfo.getName());
		project.setVersion(version+1);
		int effRows = generalMapper.updateSelective(project, projectWhere);
		if(effRows==0){//update failed
			return false;
		}
		pjInfo.setVersion(project.getVersion());
		
		SysPj2res resDB = pjInfo.getDb();
		if(resDB==null){
			return true;
		}
		SysPj2res resParm = new SysPj2res();
		resParm.setPjId(pjId);
		resParm.setDeleted(Constants.DelInd.FALSE);
		List<SysPj2res> resList = generalMapper.selectList(new RsEntityWrapper<>(resParm));
		if(resList==null || resList.isEmpty()){
			resDB.setPjId(pjId);
			generalMapper.insert(resDB);
		}else{
			SysPj2res res = resList.get(0);
			res.setDbType(resDB.getDbType());
			res.setDbUrl(resDB.getDbUrl());
			res.setDbUser(resDB.getDbUser());
			res.setDbPwd(resDB.getDbPwd());
			res.setVersion(res.getVersion()+1);
			generalMapper.updateById(res);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean deleteProject(String pjId, String userId, int version) {
		SysPj projectWhere = new SysPj();
		projectWhere.setId(pjId);
		projectWhere.setDeleted(Constants.DelInd.FALSE);
		projectWhere.setVersion(version);
		SysPj project = new SysPj();
		project.setDeleted(Constants.DelInd.TRUE);
		project.setVersion(version+1);
		int effRows = generalMapper.updateSelective(project, projectWhere);
		if(effRows==0){//update failed
			return false;
		}
		SysPj2res entity = new SysPj2res();
		entity.setDeleted(Constants.DelInd.TRUE);
		SysPj2res whereEntity = new SysPj2res();
		whereEntity.setPjId(pjId);
		whereEntity.setDeleted(Constants.DelInd.FALSE);
		generalMapper.updateSelective(entity, whereEntity);
		return true;
	}

	@Override
	@Transactional
	public boolean assignManager2Pj(ProjectInfo info, String userId, String optType) {
		String pjId = info.getId();
		JSONArray pmIdArrays = info.getManagers();
		if(pmIdArrays==null || pmIdArrays.isEmpty()){
			return true;
		}
		int size = pmIdArrays.size();
		Set<String> existPmIds = new HashSet<String>();
		String pmId = null;
		SysPj2u pu = null;
		if (Constants.OptTypes.ADD.equals(optType)) {
			SysPj2u p2uParm = new SysPj2u();
			p2uParm.setPjId(pjId);
			p2uParm.setUserType(Constants.UserType.PM);
			List<SysPj2u> existPmList = generalMapper.selectList(new RsEntityWrapper<>(p2uParm));
			if(existPmList!=null && !existPmList.isEmpty()){
				for(SysPj2u p2u:existPmList){
					existPmIds.add(p2u.getUserId());
				}
			}
			List<SysPj2u> npmList = new ArrayList<SysPj2u>(size);
			for (int i = 0; i < size; ++i) {
				pmId = pmIdArrays.getString(i);
				if (!pmId.equals(userId) && !existPmIds.contains(pmId)) {
					pu = new SysPj2u();
					pu.setPjId(pjId);
					pu.setUserId(pmId);
					pu.setUserType(Constants.UserType.PM);
					npmList.add(pu);
				}
			}
			if (!npmList.isEmpty()) {
				generalMapper.insertBatch(npmList);
			}
		}
		else if(Constants.OptTypes.DELETE.equals(optType)){
			for (int i = 0; i < size; ++i) {
				pmId = pmIdArrays.getString(i);
				if (!pmId.equals(userId)) {
					pu = new SysPj2u();
					pu.setPjId(pjId);
					pu.setUserId(pmId);
					pu.setUserType(Constants.UserType.PM);
					generalMapper.deleteSelective(pu);
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean testdb(PjDBInfoVO dbInfo) {
		String dbType = dbInfo.getDbType();
		String dbDriver = null;
		String dbUrl = null;
		if (DynamicDataSource.DBTypes.mysql.equals(dbType)) {
			dbDriver = DynamicDataSource.DBDrivers.mysqlDriver;
			dbUrl = DynamicDataSource.DBDrivers.mysqlDriverPrefix+dbInfo.getDbUrl();
		} else if (DynamicDataSource.DBTypes.oracle.equals(dbType)) {
			dbDriver = DynamicDataSource.DBDrivers.oracleDriver;
			dbUrl = DynamicDataSource.DBDrivers.oracleDriverPrefix+dbInfo.getDbUrl();
		} else {
			logger.error("testdb(): unsupportted db type ["+dbInfo.toString()+"]");
			return false;
		}
		try{
			Class.forName(dbDriver);
		}
		catch(ClassNotFoundException e){
			logger.error("testdb(): no corresponding db driver found ["+dbInfo.toString()+"]");
			return false;
		}
		try(
				Connection conn = DriverManager.getConnection(dbUrl, dbInfo.getDbUser(), dbInfo.getDbPwd());
				){
			return true;
		} catch (SQLException e) {
			logger.error("testdb(): cannot connect to db for ["+dbInfo+"]");
			return false;
		}
	}

	@Override
	public List<SysUsers> getAssignedUser4Pj(String pjId, String userType) {
		return pjMapper.getAssignedUser4Pj(pjId, userType,null);
	}

	@Override
	public List<SysUsers> getAssignedPM4Pj(String pjId) {
		return getAssignedUser4Pj(pjId, Constants.UserType.PM);
	}

	
}
