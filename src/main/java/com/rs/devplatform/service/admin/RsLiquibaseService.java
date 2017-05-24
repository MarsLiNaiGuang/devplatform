package com.rs.devplatform.service.admin;

import java.util.List;

import com.rs.devplatform.persistent.SysColumns;

import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;

public interface RsLiquibaseService {

	/**
	 * 生成数据库更新的changeLog文件
	 * 
	 * @param tableId
	 * @param tableName
	 * @param isaddTable		标记是否是新增(true: 子只需指定ziduanAddList
	 * @param ziduanAddList
	 * @param ziduanDeleteList
	 * @param ziduanUpdateList
	 * @param isForceDrop		标记是否强制drop
	 * @return
	 * @throws DatabaseException
	 */
	public String generateLiquibaseChangeLog(String tableId, String tableName, String tableComment, boolean isaddTable,
					List<SysColumns> ziduanAddList, List<SysColumns> ziduanDeleteList, List<SysColumns> ziduanUpdateList, boolean isForceDrop) throws LiquibaseException;
	
	/**
	 * 
	 * @param pjId		针对具体哪个项目的更新
	 * @param tableId
	 * @param tableName
	 * @param ziduanAddList
	 * @param ziduanDeleteList
	 * @param ziduanUpdateList
	 * @param isForceDrop
	 * @return
	 * @throws LiquibaseException
	 */
	public String generateLiquibaseChangeLog4Pj(String pjId, String tableId, String tableName, String tableComment, boolean isaddTable,
			List<SysColumns> ziduanAddList, List<SysColumns> ziduanDeleteList, List<SysColumns> ziduanUpdateList, boolean isForceDrop) throws LiquibaseException;
	/**
	 * 该方法用来执行数据库同步,同步到指定项目对应的数据库中
	 * 
	 * @param changeLogFilePath
	 * @throws LiquibaseException
	 */
	public void executeUpdate4File(String pjId, String changeLogFilePath) throws LiquibaseException;
	
	/**
	 * 该方法是根据表名来生成对应的文件路径（统一数据库变更的log文件地址生成策略）
	 * @param tableName
	 * @return
	 */
	public String generateChangeLogFilePath(String tableName);
	
	/**
	 * 根据表名，生成drop table change log file
	 * 
	 * @param pjId
	 * @param tableName
	 * @return changeLogPath
	 * @throws LiquibaseException
	 */
	public String generateChangeLogFile4DropTable(String pjId, String tableName) throws LiquibaseException;
	
	/**
	 * 根据表名，字段信息List，生成changeLog（包含changeSet）
	 * 
	 * @param pjId
	 * @param tableName
	 * @param ziduanAddList
	 * @param forceDrop
	 * @return
	 * @throws LiquibaseException
	 */
	public DatabaseChangeLog generateChangeLog4Add(String pjId, String tableName, String tableComment, List<SysColumns> ziduanAddList, boolean forceDrop) throws LiquibaseException;

}
