package com.rs.devplatform.service.sync;

import com.rs.framework.common.exception.db.RsSyncTableException;

public interface SyncService {
	
	/**
	 * 根据表名 生成bxx\zdxx记录
	 * 
	 * @param tableNames
	 */
	public void generateBxxRecords(String pjId, String... tableNames) throws RsSyncTableException;
	
	/**
	 * 根据表名生成Changelog文件
	 * 
	 * @param targetPjId 生成脚本执行的目标项目
	 * 
	 * @param isForce 是否强制同步
	 * @param tableNames
	 * @return
	 */
	public String generateChangeLogFromBasePj(String targetPjId, boolean isForce, String... tableNames) throws RsSyncTableException;
	
	/**
	 * 根据表名从BXX中生成Changelog文件
	 * 
	 * @param isForce 是否强制同步
	 * @param tableNames
	 * @return
	 * @throws RsSyncTableException
	 */
	public String generateChangeLogFromBxx(String pjId, boolean isForce, String... tableNames) throws RsSyncTableException;
	

}
