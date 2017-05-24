package com.rs.devplatform.service.admin.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.rs.devplatform.common.CGConstants;
import com.rs.devplatform.conf.LiquibaseConfig.MyLiquibase;
import com.rs.devplatform.persistent.SysColumns;
import com.rs.devplatform.persistent.SysTables;
import com.rs.devplatform.service.admin.RsLiquibaseService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.exception.SystemException;
import com.rs.framework.common.utils.RsDateUtil;
import com.rs.framework.common.utils.UserSessionUtil;

import liquibase.change.AddColumnConfig;
import liquibase.change.ColumnConfig;
import liquibase.change.ConstraintsConfig;
import liquibase.change.core.AddColumnChange;
import liquibase.change.core.CreateTableChange;
import liquibase.change.core.DropColumnChange;
import liquibase.change.core.DropTableChange;
import liquibase.change.core.ModifyDataTypeChange;
import liquibase.change.core.RenameColumnChange;
import liquibase.change.core.SetColumnRemarksChange;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.datatype.core.BlobType;
import liquibase.datatype.core.ClobType;
import liquibase.datatype.core.DateTimeType;
import liquibase.datatype.core.DateType;
import liquibase.datatype.core.DecimalType;
import liquibase.datatype.core.DoubleType;
import liquibase.datatype.core.IntType;
import liquibase.datatype.core.VarcharType;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.precondition.core.TableExistsPrecondition;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;
import liquibase.structure.core.Column;
import liquibase.structure.core.DataType;

@Service
public class RsLiquibaseServiceImpl implements RsLiquibaseService {
	
	private final Logger logger = LoggerFactory.getLogger(RsLiquibaseServiceImpl.class);
	
	private final String dbmsList = "mysql,oracle";
	
	@Autowired
	RsGeneralMapper generalMapper;
	@Autowired
	MyLiquibase liquibase;
	
	@Override
	public String generateLiquibaseChangeLog(String tableId, String tableName, String tableComment, boolean isaddTable,
			List<SysColumns> ziduanAddList, List<SysColumns> ziduanDeleteList, List<SysColumns> ziduanUpdateList,
			boolean isForceDrop) throws LiquibaseException {
		XMLChangeLogSerializer xmlChangeLogSerializer = new XMLChangeLogSerializer();
		String changeLogFilePath =  generateChangeLogFilePath(tableName);
		File changeLogFile = new File(changeLogFilePath);
		try (
				FileOutputStream fos = new FileOutputStream(changeLogFile);
				) {
			DatabaseChangeLog dbChLog = null;
			if(isaddTable){
				dbChLog = generateChangeLog4AddInner(null, tableName, null, ziduanAddList, isForceDrop);
			}else{
				dbChLog = generateChangeLog4Update(null, tableId, tableName, tableComment, ziduanAddList, ziduanDeleteList, ziduanUpdateList, isForceDrop);
			}
			if(dbChLog==null){
				return null;
			}
			xmlChangeLogSerializer.write(dbChLog.getChangeSets(), fos);
			return changeLogFilePath;
		} catch (IOException e) {
			String errorMsg = "Error when generate changeLogFile for tableId="+tableId+", tableName="+tableName+", isaddTable="+isaddTable+",isForceDrop="+isForceDrop;
			logger.error("generateLiquibaseChangeLog():"+errorMsg, e);
			throw new LiquibaseException(errorMsg);
		}
	}
	@Override
	public String generateLiquibaseChangeLog4Pj(String pjId, String tableId, String tableName, String tableComment, boolean isaddTable,
			List<SysColumns> ziduanAddList, List<SysColumns> ziduanDeleteList, List<SysColumns> ziduanUpdateList,
			boolean isForceDrop) throws LiquibaseException {
		XMLChangeLogSerializer xmlChangeLogSerializer = new XMLChangeLogSerializer();
		String changeLogFilePath =  generateChangeLogFilePath(tableName);
		File changeLogFile = new File(changeLogFilePath);
		try (
				FileOutputStream fos = new FileOutputStream(changeLogFile);
				) {
			DatabaseChangeLog dbChLog = null;
			if(isaddTable){
				dbChLog = generateChangeLog4AddInner(pjId, tableName, tableComment, ziduanAddList,isForceDrop);
			}else{
				dbChLog = generateChangeLog4Update(pjId, tableId, tableName, tableComment, ziduanAddList, ziduanDeleteList, ziduanUpdateList, isForceDrop);
			}
			if(dbChLog==null){
				return null;
			}
			xmlChangeLogSerializer.write(dbChLog.getChangeSets(), fos);
			return changeLogFilePath;
		} catch (IOException e) {
			logger.error("generateLiquibaseChangeLog():", e);
			throw new LiquibaseException("Error when generate changeLogFile for tableId="+tableId+", tableName="+tableName+", isaddTable="+isaddTable+",isForceDrop="+isForceDrop);
		}
	}

	private DatabaseChangeLog generateLiquibaseChangeLog4Add(String tableId, String tableName, String tableComment) throws DatabaseException{
		if(tableComment==null){
			SysTables bxx = generalMapper.selectById(tableId, SysTables.class);
			tableComment = bxx.getComment();
		}
		SysColumns parm = new SysColumns();
		parm.setTableid(tableId);
		parm.setDeleted(Constants.DelInd.FALSE);
		List<SysColumns> ziduanList = generalMapper.selectList(new RsEntityWrapper<>(parm));
		Map<String,SysColumns> distMap = new HashMap<>(ziduanList.size());
		for(SysColumns ziduan : ziduanList){
			String key = ziduan.getName();
			if(distMap.containsKey(key)){
				if(!StringUtils.isEmpty(ziduan.getChgtype())){//existing record chgType=null -> override
					distMap.put(key, ziduan);
				}
			}else{
				distMap.put(key, ziduan);
			}
		}
		List<SysColumns> ziduanAddList = new ArrayList<>(distMap.size());
		distMap.values().stream().filter((ziduan)->{
			String chgType = ziduan.getChgtype();
			if(chgType!=null && CGConstants.ColChgTypes.DELETE.equals(chgType)){
				return false;
			}
			return true;
		}).forEach(ziduanAddList::add);
		return generateChangeLog4AddInner(null, tableName, tableComment, ziduanAddList, true);
	}
	
	
	private DatabaseChangeLog generateChangeLog4AddInner(String pjId, String tableName, String tableComment,  List<SysColumns> ziduanAddList, boolean forceDrop) throws DatabaseException{
		String userId = UserSessionUtil.getCurrentUserId();
		DatabaseConnection conn = null;
		Connection pjConnection = null;
		try{
			if(pjId==null || Constants.DEFAULT_PJID.equals(pjId)){
				conn = new JdbcConnection(liquibase.getConnection());//generate logfile, connection should be basicConnection
			}else{
				pjConnection = liquibase.getConnection4Pj(pjId);
				conn = new JdbcConnection(pjConnection);//generate logfile, pjConnection should be closed after done
			}
			Database myDs = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(conn);
			String schemaName = myDs.getDefaultSchemaName();
			
			DatabaseChangeLog dbChLog = new DatabaseChangeLog();
			
			String changeLogId = RsDateUtil.getCurrentDateTimeStr();
			ChangeSet changeSetCreate = new ChangeSet("create_"+tableName+changeLogId, userId, false,false, null,null,dbmsList, dbChLog);
			TableExistsPrecondition tbExistPrecondition = new TableExistsPrecondition();
			tbExistPrecondition.setSchemaName(schemaName);
			tbExistPrecondition.setTableName(tableName);
			boolean isTableExist = false;
			try {
				tbExistPrecondition.check(myDs, dbChLog, changeSetCreate);
				isTableExist = true;
			} catch (PreconditionFailedException | PreconditionErrorException e) {
				//table not exist
			}
			if(isTableExist){
				if(forceDrop){
					ChangeSet dropTableSet = new ChangeSet("drop"+tableName+changeLogId, userId,false,false, null,null,dbmsList, dbChLog);
					DropTableChange dropTable = new DropTableChange();
					dropTable.setTableName(tableName);
					dropTableSet.addChange(dropTable);
					dbChLog.addChangeSet(dropTableSet);
				}else{
					logger.warn("generateChangeLog4Add(): table="+tableName+" already existed, but NOT force sync");
					return null;//表存在，但不是强制ADD，直接return null
				}
			}
			CreateTableChange createChange = new CreateTableChange();
			createChange.setTableName(tableName);
			createChange.setRemarks(tableComment);
			ziduanAddList.forEach((ziduan)->{
				createChange.addColumn(genColumn(ziduan, myDs));
			});
			changeSetCreate.addChange(createChange);
			dbChLog.addChangeSet(changeSetCreate);
			return dbChLog;
		}
		catch(Exception e){
			String errMsg = "failed to generate databaseChangeLogFile for pjId="+pjId+", tableName="+tableName;
			logger.error(errMsg, e);
			throw new DatabaseException(errMsg);
		}
		finally{
			if(pjConnection!=null){ try {pjConnection.close();} catch (SQLException e1) {	e1.printStackTrace();}	}
		}
	}
	
	private DatabaseChangeLog generateChangeLog4Update(String pjId, String tableId, String tableName, String tableComment, List<SysColumns> ziduanAddList, List<SysColumns> ziduanDeleteList, List<SysColumns> ziduanUpdateList, boolean forceDrop) throws DatabaseException,LiquibaseException{
		String userId = UserSessionUtil.getCurrentUserId();
		
		DatabaseConnection conn = null;
		Connection pjConnection = null;
		try{
			if(pjId==null || Constants.DEFAULT_PJID.equals(pjId)){
				conn = new JdbcConnection(liquibase.getConnection());//generate logfile, connection should be basicConnection
			}else{
				pjConnection = liquibase.getConnection4Pj(pjId);
				conn = new JdbcConnection(pjConnection);//generate logfile, connection should be basicConnection
			}
			Database myDs = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(conn);
			DatabaseChangeLog dbChLog = new DatabaseChangeLog();
			String changeLogId = RsDateUtil.getCurrentDateTimeStr();
			ChangeSet changeSetUpdate = new ChangeSet("update_"+tableName+changeLogId, userId, false,false, null,null,dbmsList, dbChLog);
			TableExistsPrecondition tbExistPrecondition = new TableExistsPrecondition();
			tbExistPrecondition.setSchemaName(myDs.getDefaultSchemaName());
			tbExistPrecondition.setTableName(tableName);
			tbExistPrecondition.check(myDs, dbChLog, changeSetUpdate);//no exception, means table exist
			if(forceDrop){
				DatabaseChangeLog dbChLogForce = generateLiquibaseChangeLog4Add(tableId, tableName, tableComment);
				if(dbChLogForce!=null){
					List<ChangeSet> setList = dbChLogForce.getChangeSets();
					for(ChangeSet set:setList){
						dbChLog.addChangeSet(set);
					}
				}
				return dbChLog;
			}
			if(ziduanDeleteList!=null && !ziduanDeleteList.isEmpty()){
				ChangeSet dropColumnSet = new ChangeSet("delete_"+tableName+changeLogId, userId,false,false, null,null,dbmsList, dbChLog);
				for(SysColumns ziduan:ziduanDeleteList){
					DropColumnChange dropColumn = new DropColumnChange();
					dropColumn.setTableName(tableName);
					dropColumn.setColumnName(ziduan.getName());
	//				dropColumn.addColumn(ColumnConfig.fromName(ziduan.getName()));//cannot use addColumn as the sql will be generated as drop column1,drop column2 from...
					dropColumnSet.addChange(dropColumn);
				}
				dbChLog.addChangeSet(dropColumnSet);
			}
			if(ziduanUpdateList!=null && !ziduanUpdateList.isEmpty()){
				ChangeSet modifySet = new ChangeSet("alter_"+tableName+changeLogId, userId,false,false, null,null,dbmsList, dbChLog);
				for(SysColumns ziduan:ziduanUpdateList){
					String columnName = ziduan.getName();
					String oldColumnName = ziduan.getOldname();
					if(oldColumnName==null || oldColumnName.trim().isEmpty()){
						oldColumnName = columnName;//本段逻辑为了保留alter字段后的备注Liquibase Core : CORE-3037
					}
					ModifyDataTypeChange modifyChange = new ModifyDataTypeChange();
					modifyChange.setTableName(tableName);
					modifyChange.setColumnName(oldColumnName);
					modifyChange.setNewDataType(getColumnType(ziduan, myDs).toString());
					modifySet.addChange(modifyChange);
					if(!columnName.equalsIgnoreCase(oldColumnName)){
						RenameColumnChange renameChange = new RenameColumnChange();
						renameChange.setTableName(tableName);
						renameChange.setOldColumnName(oldColumnName);
						renameChange.setNewColumnName(columnName);
						renameChange.setRemarks(ziduan.getComment());//本段逻辑为了保留alter字段后的备注Liquibase Core : CORE-3037
						renameChange.setColumnDataType(getColumnType(ziduan, myDs).toString());
						modifySet.addChange(renameChange);
					}else{
						if(myDs.getShortName().equalsIgnoreCase("oracle")){//SetColumnRemarksChange : mysql not support
							SetColumnRemarksChange remarkChange = new SetColumnRemarksChange();//not supported in mysql
							remarkChange.setTableName(tableName);
							remarkChange.setColumnName(oldColumnName);
							remarkChange.setRemarks(ziduan.getComment());
							modifySet.addChange(remarkChange);
						}
					}
				}
				dbChLog.addChangeSet(modifySet);
			}
			if(ziduanAddList!=null && !ziduanAddList.isEmpty()){
				ChangeSet addSet = new ChangeSet("add_"+tableName+changeLogId, userId,false,false, null,null,dbmsList, dbChLog);
				for(SysColumns ziduan:ziduanAddList){
					AddColumnChange addChange = new AddColumnChange();
					addChange.setTableName(tableName);
					addChange.addColumn(genColumn4Add(ziduan, myDs));
					addSet.addChange(addChange);
				}
				dbChLog.addChangeSet(addSet);
			}
			return dbChLog;
			
		}catch (PreconditionFailedException | PreconditionErrorException e) {//table not exist
			if(ziduanAddList==null){
				ziduanAddList = new ArrayList<>();
			}
			if(ziduanUpdateList!=null){
				ziduanAddList.addAll(ziduanUpdateList);
			}
			return generateChangeLog4Add(pjId, tableName, tableComment, ziduanAddList, forceDrop);
		}catch(Exception e){
			String errMsg = "generateChangeLog4Update(): failed to generate databaseChangeLogFile for pjId="+pjId+", tableName="+tableName;
			logger.error(errMsg, e);
			throw new DatabaseException(errMsg);
		}
		finally{
			if(pjConnection!=null){ try {pjConnection.close();} catch (SQLException e1) {	e1.printStackTrace();}	}
		}
	}
	
	private AddColumnConfig genColumn4Add(SysColumns ziduan, Database myDs){
		return new AddColumnConfig(new Column(genColumn(ziduan,myDs)));
	}
	
	private ColumnConfig genColumn(SysColumns ziduan,Database myDs){
		DataType dtype = getColumnType(ziduan, myDs);
		ConstraintsConfig constraints = new ConstraintsConfig();//TODO: 约束条件
		if(Constants.YES.equals(ziduan.getIspk())){
			constraints.setPrimaryKey(true);
		}
		if(Constants.YES.equals(ziduan.getIsnull())){
			constraints.setNullable(true);
		}else{
			constraints.setNullable(false);
		}
		ColumnConfig colConf = ColumnConfig.fromName(ziduan.getName())
				.setType(dtype.toString())
				.setConstraints(constraints)
				.setRemarks(ziduan.getComment());
		String defValStr = ziduan.getDefval();
		if(defValStr==null || defValStr.trim().isEmpty()){
			return colConf;
		}
		switch (ziduan.getType()) {
		case CGConstants.ColTypes.string:
			colConf.setDefaultValue(defValStr);
			break;
		case CGConstants.ColTypes.int_:
			try{
				int defInt = Integer.parseInt(defValStr);
				colConf.setDefaultValueNumeric(defInt);
			}catch(Exception e){}
			break;
		default:
			break;
		}
		return colConf;
		
	}
	
	private DataType getColumnType(SysColumns ziduan,Database myDs){
		DataType dtype = null;
		switch (ziduan.getType()) {
		case CGConstants.ColTypes.string:
			dtype = new DataType(new VarcharType().toDatabaseDataType(myDs).getType());
			dtype.setColumnSize(ziduan.getLength());
			break;
		case CGConstants.ColTypes.int_:
			dtype = new DataType(new IntType().toDatabaseDataType(myDs).getType());
			dtype.setColumnSize(ziduan.getLength());
			break;
		case CGConstants.ColTypes.double_:
			dtype = new DataType(new DoubleType().toDatabaseDataType(myDs).getType());
			dtype.setDecimalDigits(ziduan.getDecpoint());
			dtype.setColumnSize(ziduan.getLength());
			break;
		case CGConstants.ColTypes.decimal:
			dtype = new DataType(new DecimalType().toDatabaseDataType(myDs).getType());
			dtype.setDecimalDigits(ziduan.getDecpoint());
			dtype.setColumnSize(ziduan.getLength());
			break;
		case CGConstants.ColTypes.date:
			dtype = new DataType(new DateType().toDatabaseDataType(myDs).getType());
			break;
		case CGConstants.ColTypes.datetime:
			dtype = new DataType(new DateTimeType().toDatabaseDataType(myDs).getType());
			break;
		case CGConstants.ColTypes.text:
			dtype = new DataType(new ClobType().toDatabaseDataType(myDs).getType());
			dtype.setColumnSize(ziduan.getLength());
			break;
		case CGConstants.ColTypes.blob:
			dtype = new DataType(new BlobType().toDatabaseDataType(myDs).getType());
			break;
		case CGConstants.ColTypes.clob:
			dtype = new DataType(new ClobType().toDatabaseDataType(myDs).getType());
			break;
		case CGConstants.ColTypes.bytes:
			dtype = new DataType(new BlobType().toDatabaseDataType(myDs).getType());
			break;
		default:
			logger.error("getColumnType({}) : failed to convert dbType", ziduan.toString());
			throw new SystemException("Unrecognized dbType");
//			dtype = new DataType(new VarcharType().toDatabaseDataType(myDs).getType());
//			dtype.setColumnSize(ziduan.getLength());
//			break;
		}
		return dtype;
	}

	@Override
	public void executeUpdate4File(String pjId, String changeLogFilePath) throws LiquibaseException {
		try {
			if(pjId==null || Constants.DEFAULT_PJID.equals(pjId)){
				liquibase.setup(changeLogFilePath).update();
			}else{
				liquibase.update4Project(pjId, changeLogFilePath);
			}
		} catch (LiquibaseException e) {
			logger.error("executeUpdate4File():", e);
			throw e;
		} 
	}

	@Override
	public String generateChangeLogFilePath(String tableName) {
		String basePath = CGConstants.GEN_CHANGELOG_FILE_PATH;
		if(basePath.endsWith("/") || basePath.endsWith("\\")){
			return basePath+tableName.toLowerCase()+"-chglog"+".xml";
		}else{
			return basePath + "/" + tableName.toLowerCase()+"-chglog"+".xml";
		}
	}

	@Override
	public String generateChangeLogFile4DropTable(String pjId, String tableName) throws LiquibaseException{
		String userId = UserSessionUtil.getCurrentUserId();
		
		DatabaseConnection conn = null;
		Connection pjConnection = null;
		DatabaseChangeLog dbChLog = null;
		try{
			if(pjId==null || Constants.DEFAULT_PJID.equals(pjId)){
				conn = new JdbcConnection(liquibase.getConnection());//generate logfile, connection should be basicConnection
			}else{
				pjConnection = liquibase.getConnection4Pj(pjId);
				conn = new JdbcConnection(pjConnection);//generate logfile, connection should be basicConnection
			}
			Database myDs = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(conn);
			
			String changeLogId = RsDateUtil.getCurrentDateTimeStr();
			dbChLog = new DatabaseChangeLog();
			ChangeSet dropTableSet = new ChangeSet("drop"+tableName+changeLogId, userId,false,false, null,null,dbmsList, dbChLog);
			TableExistsPrecondition tbExistPrecondition = new TableExistsPrecondition();
			tbExistPrecondition.setSchemaName(myDs.getDefaultSchemaName());
			tbExistPrecondition.setTableName(tableName);
			tbExistPrecondition.check(myDs, dbChLog, dropTableSet);//no exception, table exist
			
			DropTableChange dropTable = new DropTableChange();
			dropTable.setTableName(tableName);
			dropTableSet.addChange(dropTable);
			dbChLog.addChangeSet(dropTableSet);
		}catch(PreconditionFailedException | PreconditionErrorException e){//table not exist
			if(logger.isDebugEnabled()){
				logger.debug("generateChangeLogFile4DropTable(pjId="+pjId+", tableName="+tableName+"): table not exist, no need to generate Drop change log");
			}
			return null;
		} catch (DatabaseException e) {
			logger.error("generateChangeLogFile4DropTable(pjId="+pjId+", tableName="+tableName+"): databaseException", e);
			throw new LiquibaseException(e);
		} 
		finally{
			if(pjConnection!=null){ try {pjConnection.close();} catch (SQLException e1) {	e1.printStackTrace();}	}
		}
		XMLChangeLogSerializer xmlChangeLogSerializer = new XMLChangeLogSerializer();
		String changeLogFilePath =  generateChangeLogFilePath(tableName);
		File changeLogFile = new File(changeLogFilePath);
		try (
			FileOutputStream fos = new FileOutputStream(changeLogFile);
				) {
			xmlChangeLogSerializer.write(dbChLog.getChangeSets(), fos);
			return changeLogFilePath;
		} catch (IOException e) {
			logger.error("generateChangeLogFile4DropTable(pjId="+pjId+", tableName="+tableName+"): IOException when generate changeLog file", e);
			throw new LiquibaseException(e);
		}
	}
	@Override
	public DatabaseChangeLog generateChangeLog4Add(String pjId, String tableName, String tableComment, List<SysColumns> ziduanAddList,
			boolean forceDrop) throws LiquibaseException {
		try{
			return generateChangeLog4AddInner(pjId, tableName, tableComment, ziduanAddList, forceDrop);
		}catch(DatabaseException e){
			throw new LiquibaseException(e);
		}
	}

}
