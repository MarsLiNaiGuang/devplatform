package com.rs.devplatform.service.admin;

import java.util.List;

import com.rs.devplatform.persistent.SysTables;
import com.rs.devplatform.vo.MaintainTableVO;
import com.rs.devplatform.vo.TableUpdateConfirmVO;
import com.rs.framework.common.exception.RsCommonException;
import com.rs.framework.common.validator.ValidateError;

/**
 * 数据库表 在线维护<BR>
 * 
 * 从原来的CGService迁移出部分数据库相关功能(2017-3-13)。
 * 
 * @author yuxiaobin
 *
 */
public interface DBMaintainService {
	
	public MaintainTableVO getTableWithColumnById(String tableId);
	
	public MaintainTableVO getTableWithColumnByName(String tableName);

	public boolean addTableWithColumn(MaintainTableVO addTable);
	
	/**
	 * Confirm the updates.
	 * 
	 * @param updateTable
	 * @return
	 */
	public TableUpdateConfirmVO confirmUpdateTable(MaintainTableVO updateTable);
	
	public boolean updateTableWithColumnConfirm(TableUpdateConfirmVO updateTable) throws RsCommonException;
	
	public boolean updateTableWithColumn(MaintainTableVO updateTable) throws RsCommonException;
	
	public boolean deleteTableById(String pjId, String tableId, Integer version, boolean forceDrop) throws RsCommonException;
	
	public boolean deleteTableByName(String pjId, String tableName, Integer version, boolean forceDrop) throws RsCommonException;
	
	public boolean syncTable(String pjId, SysTables table, boolean forceDrop) throws RsCommonException;
	
	public List<ValidateError> getServiceErrors();
}
