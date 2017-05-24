package com.rs.devplatform.service.sync.impl;

import static  com.rs.devplatform.vo.cg.SysColumnsConverter.convertZdxxList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rs.devplatform.conf.LiquibaseConfig.MyLiquibase;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.service.admin.RsLiquibaseService;
import com.rs.devplatform.service.sync.SyncService;
import com.rs.devplatform.service.sync.utils.RsDBTableReflectUtil;
import com.rs.devplatform.service.sync.utils.RsDBTableReflectUtil.ColumnInfo;
import com.rs.devplatform.service.sync.utils.RsDBTableReflectUtil.TableInfo;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.exception.db.RsSyncTableException;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.LiquibaseException;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;

@Service
public class SyncServiceImpl implements SyncService {
	
	private static final String ERROR_MSG = "{0}(): generate ChangeLog failed for pjId={1}, tableId={2}, tableName={3}, isForce={4}";
	
	private final Logger logger = LoggerFactory.getLogger(SyncServiceImpl.class);
	
	private static final String YES_RS_VAL = Constants.YES;
	private static final String NO_RS_VAL = Constants.NO;
	
	@Autowired
	RsGeneralMapper generalMapper;
	
	@Autowired
	RsLiquibaseService liquibaseService;
	
	@Autowired
	MyLiquibase liquibase;
	
	@Override
	@Transactional
	public void generateBxxRecords(String pjId, String... tableNames) throws RsSyncTableException {
		if(tableNames==null || tableNames.length==0){
			logger.warn("generateChangeLogFromBasePj(): no tableNames specified for pjId="+pjId);
			return;
		}
		Connection conn = liquibase.getConnection();
		try {
			for(String tableName : tableNames){
				TableInfo table = RsDBTableReflectUtil.getTableInfoFromDB(conn, tableName);
				SysBxx bxx = new SysBxx();
				bxx.setBm(table.getName());
				bxx.setNr(table.getComment());
				bxx.setLx(1);
				generalMapper.insert(bxx);
				String bid = bxx.getId();
				
				List<ColumnInfo> columns = RsDBTableReflectUtil.getTableColumnInfoFromDB(conn, tableName);
				int size = columns.size();
				List<SysZdxx> ziduanList = new ArrayList<>(size);
				for(int i=0;i<size;++i){
					ColumnInfo column = columns.get(i);
					SysZdxx zdxx = new SysZdxx();
					zdxx.setBid(bid);
					zdxx.setXh(i+1);
					zdxx.setCd(column.getColumnLength());
					zdxx.setLx(column.getColumnType().getRsType());
					zdxx.setName(column.getColumnName());
					zdxx.setIskey(column.isPk() ? YES_RS_VAL : NO_RS_VAL);
					zdxx.setIsnull(column.isNullable() ? YES_RS_VAL : NO_RS_VAL);
					zdxx.setNr(column.getColumnComment());
					ziduanList.add(zdxx);
				}
				generalMapper.insertBatch(ziduanList);
			}
		}catch (SQLException e) {
			logger.error("generateBxxRecords("+pjId+") failed to get table infos", e);
			throw new RsSyncTableException("生成表信息出错", tableNames);
		}
	}

	/**
	 * 
	 */
	@Override
	public String generateChangeLogFromBasePj(String pjId, boolean isForce, String... tableNames) throws RsSyncTableException {
		if(tableNames==null || tableNames.length==0){
			logger.warn("generateChangeLogFromBasePj(): no tableNames specified for pjId="+pjId);
			return null;
		}
		Connection conn = liquibase.getConnection();
		StringBuilder notIncludedTableNames = new StringBuilder();
		StringBuilder includedTableNames = new StringBuilder();
		DatabaseChangeLog dbChLog = new DatabaseChangeLog();
		for(String tableName : tableNames){
			try {
				TableInfo table = RsDBTableReflectUtil.getTableInfoFromDB(conn, tableName);
				List<ColumnInfo> columns = RsDBTableReflectUtil.getTableColumnInfoFromDB(conn, tableName);
				List<SysZdxx> ziduanList = new ArrayList<>(columns.size());
				columns.stream().map((column)->{
					SysZdxx zdxx = new SysZdxx();
					zdxx.setCd(column.getColumnLength());
					zdxx.setLx(column.getColumnType().getRsType());
					zdxx.setName(column.getColumnName());
					zdxx.setIskey(column.isPk() ? YES_RS_VAL : NO_RS_VAL);
					zdxx.setIsnull(column.isNullable() ? YES_RS_VAL : NO_RS_VAL);
					zdxx.setNr(column.getColumnComment());
					return zdxx;
				}).forEach(ziduanList::add);
				if(generateChangeLog(pjId, tableName, table.getComment(), ziduanList, isForce, dbChLog)){
					includedTableNames.append(tableName).append(",");
				}else{
					notIncludedTableNames.append(tableName).append(",");
				}
			} catch (SQLException e) {
				logger.error("generateChangeLogFromBasePj("+pjId+", "+isForce+", "+tableName+") failed to get table infos", e);
				notIncludedTableNames.append(tableName).append(",");
			}
		}
		if(notIncludedTableNames.length()!=0){
			logger.warn("generateChangeLogFromBasePj(): below tables not included:"+notIncludedTableNames.toString());
		}
		return generateChgLogFile(pjId, includedTableNames.toString(), dbChLog, isForce);
	}

	@Override
	public String generateChangeLogFromBxx(String pjId, boolean isForce, String... tableNames) throws RsSyncTableException {
		if(tableNames!=null){
			DatabaseChangeLog dbChLog = new DatabaseChangeLog();
			StringBuilder notIncludedTableNames = new StringBuilder();
			StringBuilder includedTableNames = new StringBuilder();
			for(String tableName:tableNames){
				SysBxx parm = new SysBxx();
				parm.setBm(tableName);
				SysBxx table = generalMapper.selectOne(parm);
				if(table==null){
					logger.warn("generateChangeLogFromBxx(): no record found for tableName="+tableName+", this table ignored");
					notIncludedTableNames.append(tableName).append(",");
					continue;
				}
				String tableId = table.getId();
				SysZdxx zdParm = new SysZdxx();
				zdParm.setBid(tableId);
				zdParm.setDeleted(Constants.DelInd.FALSE);
				RsEntityWrapper<SysZdxx> wrapper = new RsEntityWrapper<>(zdParm);
				if(generateChangeLog(pjId, tableName, table.getNr(), generalMapper.selectList(wrapper), isForce, dbChLog)){
					includedTableNames.append(tableName).append(",");
				}else{
					notIncludedTableNames.append(tableName).append(",");
				}
			}
			if(notIncludedTableNames.length()!=0){
				logger.warn("generateChangeLogFromBxx(): below tables not included:"+notIncludedTableNames.toString());
			}
			return generateChgLogFile(pjId, includedTableNames.toString(), dbChLog, isForce);
		}
		throw new RsSyncTableException("同步失败,请指定需要同步到表");
	}
	
	private String generateChgLogFile(String pjId, String includedTables, DatabaseChangeLog dbChLog, boolean isForce) throws RsSyncTableException{
		List<ChangeSet> changeSet = dbChLog.getChangeSets();
		if(changeSet==null || changeSet.isEmpty()){
			logger.error("generateChgLogFile(): changeSet is empty, failed to generate changelogFile for pjId="+pjId);
			throw new RsSyncTableException("同步失败");
		}
		XMLChangeLogSerializer xmlChangeLogSerializer = new XMLChangeLogSerializer();
		String changeLogFilePath =  generateChangeLogFilePath(pjId, includedTables);
		File changeLogFile = new File(changeLogFilePath);
		try (
				FileOutputStream fos = new FileOutputStream(changeLogFile);
				) {
			xmlChangeLogSerializer.write(changeSet, fos);
			return changeLogFilePath;
		} catch (IOException e) {
			logger.error(MessageFormat.format(ERROR_MSG, new Object[]{pjId,null,null,isForce}), e);
			throw new RsSyncTableException("同步失败");
		}
	}
	
	private String generateChangeLogFilePath(String pjId, String tableNames){
		StringBuilder sb = new StringBuilder();
		sb.append(pjId).append("_");
		if(tableNames.length()>15){
			sb.append(tableNames.substring(0,15));
		}else{
			sb.append(tableNames);
		}
		return liquibaseService.generateChangeLogFilePath(sb.toString());
	}
	
	
	private boolean generateChangeLog(String pjId, String tableName, String tableComment, List<SysZdxx> ziduanList, boolean isForce, DatabaseChangeLog dbChLog){
		if(tableName==null || ziduanList==null || ziduanList.isEmpty()){
			return false;
		}
		try {
			DatabaseChangeLog changeLog = liquibaseService.generateChangeLog4Add(pjId, tableName, tableComment, convertZdxxList(ziduanList), isForce);
			if(changeLog!=null){
				List<ChangeSet> changes = changeLog.getChangeSets();
				if(changes!=null && !changes.isEmpty()){
					for(ChangeSet cset : changes){
						dbChLog.addChangeSet(cset);
					}
					return true;
				}
			}
		} catch (LiquibaseException e) {
			logger.error(MessageFormat.format(ERROR_MSG, new Object[]{"generateChangeLogFromBxx",pjId, null, tableName,isForce}), e);
		}
		return false;
	}
}
