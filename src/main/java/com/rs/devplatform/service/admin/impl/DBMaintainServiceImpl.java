package com.rs.devplatform.service.admin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rs.devplatform.common.CGConstants.ColChgTypes;
import com.rs.devplatform.persistent.SysColumns;
import com.rs.devplatform.persistent.SysIndexs;
import com.rs.devplatform.persistent.SysTables;
import com.rs.devplatform.service.admin.DBMaintainService;
import com.rs.devplatform.service.admin.RsLiquibaseService;
import com.rs.devplatform.vo.ColumnIndexVO;
import com.rs.devplatform.vo.MaintainColumnVO;
import com.rs.devplatform.vo.MaintainIndexVO;
import com.rs.devplatform.vo.MaintainTableVO;
import com.rs.devplatform.vo.TableUpdateConfirmVO;
import com.rs.devplatform.vo.cg.Index2VOConverter;
import com.rs.devplatform.vo.cg.SysColumnsConverter;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.exception.RsCommonException;
import com.rs.framework.common.service.base.CRUDBaseService;

import liquibase.exception.LiquibaseException;

@Service
public class DBMaintainServiceImpl extends CRUDBaseService implements DBMaintainService {
	
	private static final Logger logger = LoggerFactory.getLogger("DBMaintainServiceImpl");
	private static final String column_prefix_sep = "_";
	
	@Autowired
	RsLiquibaseService liquibaseService;

	@Override
	public MaintainTableVO getTableWithColumnById(String tableId) {
		SysTables table = generalMapper.selectById(tableId, SysTables.class);
		return getTableWithColumnBySysTable(table);
	}
	
	private MaintainTableVO getTableWithColumnBySysTable(SysTables table){
		if(table==null){
			return null;
		}
		String tablename = table.getName();
		String columnPrefix = null;
		int index_ = tablename.lastIndexOf(column_prefix_sep);
		if (index_ != -1) {
			columnPrefix = tablename.substring(index_ + 1) + column_prefix_sep;
		} else {
			columnPrefix = tablename + column_prefix_sep;
		}
		columnPrefix = columnPrefix.toUpperCase();
		MaintainTableVO result = MaintainTableVO.fromSysTable(table);
		SysColumns ziduanParm = new SysColumns();
		ziduanParm.setTableid(table.getId());
		RsEntityWrapper<SysColumns> ew = new RsEntityWrapper<>(ziduanParm);
		ew.and("COLUMNS_CHGTYPE is null or COLUMNS_CHGTYPE!={0}", ColChgTypes.DELETE);
		ew.orderBy("COLUMNS_SEQ");
		result.setColumns(SysColumnsConverter.convertSysColumnList(columnPrefix, generalMapper.selectList(ew)));
		SysIndexs indexParm = new SysIndexs();
		indexParm.setTableid(table.getId());
		RsEntityWrapper<SysIndexs> indexWrapper = new RsEntityWrapper<>(indexParm);
		indexWrapper.orderBy("INDEXS_SEQ");
		result.setIndexs(Index2VOConverter.convertSysIndexList(generalMapper.selectList(indexWrapper)));
		return result;
	}

	@Override
	public MaintainTableVO getTableWithColumnByName(String tableName) {
		SysTables tableParm = new SysTables();
		tableParm.setName(tableName);
		SysTables table = generalMapper.selectOne(tableParm);
		return getTableWithColumnBySysTable(table);
	}

	@Override
	@Transactional
	public boolean addTableWithColumn(MaintainTableVO addTable) {
		SysTables table = addTable.toSysTable();
		table.setSync(Constants.NO);
		int effRow = generalMapper.insert(table);
		if(effRow==0){
			logger.error("addTableWithColumn(): failed to insert SysTables by "+addTable);
			return false;
		}
		String tableid = table.getId();
		List<SysIndexs> indexList = Index2VOConverter.convertIndexVOArray(addTable.getIndexs(), tableid);
		if(indexList!=null && !indexList.isEmpty()){
			generalMapper.insertBatch(indexList);
		}
		List<SysColumns> columnList = SysColumnsConverter.convertMaintainColumnArray(addTable.getColumns(), tableid);
		if(columnList!=null && !columnList.isEmpty()){
			for(SysColumns col:columnList){
				col.setChgtype(ColChgTypes.ADD);
			}
			generalMapper.insertBatch(columnList);
		}
		addTable.setTableid(tableid);
		addTable.setVersion(table.getVersion());
		return true;
	}

	@Override
	@Transactional
	public boolean updateTableWithColumnConfirm(TableUpdateConfirmVO updateTable) throws RsCommonException{
		MaintainTableVO tableVO = updateTable.getTableBefore();
		String tableid = tableVO.getTableid();
		boolean hasChange = false;
		
		List<SysColumns> addColumnList = SysColumnsConverter.convertMaintainColumnList(updateTable.getAddColList(), tableid, ColChgTypes.ADD);
		if(addColumnList!=null && !addColumnList.isEmpty()){
			hasChange = true;
			generalMapper.insertBatch(addColumnList);
		}
		List<SysColumns> updateColumnList = SysColumnsConverter.convertMaintainColumnUpdateList(updateTable.getUpdateColList(), tableid);
		if(updateColumnList!=null && !updateColumnList.isEmpty()){
			hasChange = true;
			for(SysColumns col: updateColumnList){
				generalMapper.updateSelectiveById(col);
			}
		}
		List<MaintainColumnVO> deleteColVOList = updateTable.getDeleteColList();
		if(deleteColVOList!=null && !deleteColVOList.isEmpty()){
			hasChange = true;
			SysColumns entity = new SysColumns();
			SysColumns where = new SysColumns();
			entity.setChgtype(ColChgTypes.DELETE);
			for(MaintainColumnVO vo:deleteColVOList){
				where.setId(vo.getCol_id());
				generalMapper.updateSelective(entity, where);
			}
		}
		List<SysIndexs> addIndexList = Index2VOConverter.convertIndexVOList(updateTable.getAddIndList(), tableid);
		if(addIndexList!=null && !addIndexList.isEmpty()){
			hasChange = true;
			generalMapper.insertBatch(addIndexList);
		}
		List<SysIndexs> updateIndexList = Index2VOConverter.convertMaintainIndexUpdateList(updateTable.getUpdateIndList(), tableid);
		if(updateIndexList!=null && !updateIndexList.isEmpty()){
			hasChange = true;
			for(SysIndexs ind:updateIndexList){
				generalMapper.updateSelectiveById(ind);
			}
		}
		List<MaintainIndexVO> deleteIndList = updateTable.getDeleteIndList();
		if(deleteIndList!=null && !deleteIndList.isEmpty()){
			List<String> indexIds = new ArrayList<>();
			for(MaintainIndexVO vo:deleteIndList){
				if(!StringUtils.isBlank(vo.getIndexId())){
					indexIds.add(vo.getIndexId());
				}
			}
			if(!indexIds.isEmpty()){
				hasChange = true;
				generalMapper.deleteBatchIds(indexIds, SysIndexs.class);
			}
		}
		if(hasChange || tableVO.compareAndSetChange(updateTable.getTableAfter())){
			SysTables table = tableVO.toSysTable();
			Integer version = table.getVersion();
			if(version==null){
				SysTables tableDB = generalMapper.selectById(tableid, SysTables.class);
				version = tableDB.getVersion()+1;
			}else{
				version++;
			}
			table.setVersion(version);
			table.setSync(Constants.NO);
			int effRow = generalMapper.updateSelectiveById(table);
			if(effRow==0){
				logger.error("updateTableWithColumnConfirm(): failed to update SysTables. effRow=0 for table={}",updateTable.toString());
				throw new RsCommonException(HttpStatus.CONFLICT, "更新失败了");
			}
			updateTable.setVersion(version);
		}
		return true;
	}

	@Override
	@Transactional
	public boolean deleteTableById(String pjId, String tableId, Integer version, boolean forceDrop) throws RsCommonException{
		if(StringUtils.isBlank(tableId) || version==null){
			return false;
		}
		SysTables parm = new SysTables();
		parm.setId(tableId);
		parm.setVersion(version);
		SysTables table = generalMapper.selectOne(parm);
		if(table==null){
			return false;
		}
		return deleteTableBySysTable(pjId, table, forceDrop);
	}
	
	private boolean deleteTableBySysTable(String pjId, SysTables table, boolean forceDrop) throws RsCommonException{
		int effRow = generalMapper.deleteById(table.getId(), SysTables.class);
		if(effRow==0){
			return false;
		}
		String tableid = table.getId();
		SysColumns column = new SysColumns();
		column.setTableid(tableid);
		generalMapper.deleteSelective(column);
		SysIndexs index = new SysIndexs();
		index.setTableid(tableid);
		generalMapper.deleteSelective(index);
		if(forceDrop){
			String changeLogFilePath;
			String tablename = table.getName();
			try {
				changeLogFilePath = liquibaseService.generateChangeLogFile4DropTable(pjId, tablename);
				if(changeLogFilePath!=null){
					liquibaseService.executeUpdate4File(pjId, changeLogFilePath);;
				}
			} catch (LiquibaseException e) {
				logger.error("deleteForm(pjId="+pjId+", tableName="+tablename+"):", e);
				throw new RsCommonException("删除物理表失败，请联系管理员");
			}
		}
		return true;
	}

	@Override
	@Transactional
	public boolean deleteTableByName(String pjId, String tableName, Integer version, boolean forceDrop) throws RsCommonException{
		if(StringUtils.isBlank(tableName) || version==null){
			return false;
		}
		SysTables parm = new SysTables();
		parm.setName(tableName);
		parm.setVersion(version);
		SysTables table = generalMapper.selectOne(parm);
		if(table==null){
			return false;
		}
		return deleteTableBySysTable(pjId, table, forceDrop);
	}
	
	@Override
	public TableUpdateConfirmVO confirmUpdateTable(MaintainTableVO updateTable) {
		String tableId = updateTable.getTableid();
		SysTables tableDB = null;
		List<SysColumns> originalColumnList = null;
		List<SysIndexs> originalIndexList = null;
		if(tableId!=null){
			SysTables tableParm = new SysTables();
			tableParm.setId(tableId);
			tableParm.setVersion(updateTable.getVersion());
			tableDB = generalMapper.selectOne(tableParm);
			SysColumns ziduanParm = new SysColumns();
			ziduanParm.setTableid(tableId);
			originalColumnList = generalMapper.selectList(ziduanParm);
			SysIndexs indexParm = new SysIndexs();
			indexParm.setTableid(tableId);
			originalIndexList = generalMapper.selectList(indexParm);
		}
		if(tableDB==null){
			logger.debug("confirmUpdateTable(): no record found for tableId={}, tableName={}", tableId, updateTable.getTablename());
		}
		TableUpdateConfirmVO result = new TableUpdateConfirmVO();
		compareTableChanges(tableDB, updateTable, result);
		if(!compareColumnChanges(originalColumnList, updateTable.getColumns(), result)){
			return null;
		}
		compareIndexChanges(originalIndexList, updateTable.getIndexs(), result);
		return result;
	}

	private void compareTableChanges(SysTables table, MaintainTableVO updateTable, TableUpdateConfirmVO result){
		if(table!=null){
			MaintainTableVO tableBefore = new MaintainTableVO();
			tableBefore.setTableid(table.getId());
			tableBefore.setTablename(table.getName());
			tableBefore.setTabledescp(table.getComment());
			tableBefore.setPktype(table.getPktype());
			tableBefore.setPkseq(table.getPkseq());
			tableBefore.setVersion(table.getVersion());
			result.setTableBefore(tableBefore);
		}
		result.setTableAfter(updateTable);
	}
	
	private boolean compareColumnChanges(List<SysColumns> originalColumnList, MaintainColumnVO[] updateColumns, TableUpdateConfirmVO result){
		Map<String,SysColumns> colMap = new HashMap<>();
		Map<String,String> nameIdMap = new HashMap<>();
		if(originalColumnList!=null){
			for(SysColumns col:originalColumnList){
				String colId = col.getId();
				if(!colMap.containsKey(colId)){
					colMap.put(colId, col);
				}
				nameIdMap.put(col.getName().trim().toUpperCase(), colId);
				String oldname = col.getOldname();
				if(!StringUtils.isBlank(oldname)){
					nameIdMap.put(oldname.trim().toUpperCase(), colId);
				}
			}
		}
		List<MaintainColumnVO> addColList = new ArrayList<>();
		List<MaintainColumnVO[]> updateColList = new ArrayList<>();
		List<MaintainColumnVO> deleteColList = new ArrayList<>();
		for(MaintainColumnVO vo:updateColumns){
			String colId = vo.getCol_id();
			if(!StringUtils.isBlank(colId)){
				SysColumns colOld = colMap.get(colId);
				if(colOld==null){
					addColList.add(vo);
					continue;
				}
				MaintainColumnVO[] updatePair = new MaintainColumnVO[2];
				updatePair[0] = MaintainColumnVO.convert(colOld);
				updatePair[1] = vo;
				updateColList.add(updatePair);
				colMap.remove(colId);
				continue;
			}
			String colName = vo.getCol_name();
			if(colName==null){
				logger.warn("compareColumnChanges(): MaintainColumnVO.colName is null, this record ignored");
				continue;	
			}
			String oldColId = nameIdMap.get(colName.trim().toUpperCase());
			if(oldColId==null){
				addColList.add(vo);
				continue;
			}else{
				SysColumns colOld = colMap.get(oldColId);
				if(colOld!=null){
					MaintainColumnVO[] updatePair = new MaintainColumnVO[2];
					updatePair[0] = MaintainColumnVO.convert(colOld);
					vo.setCol_id(oldColId);
					vo.setChgType(ColChgTypes.UPDATE);
					updatePair[1] = vo;
					updateColList.add(updatePair);
					colMap.remove(oldColId);
					continue;
				}else{
					addServiceError("修改过于复杂，请先完成同步后再尝试本次修改");
					logger.warn("compareColumnChanges(): cannot get SysColumns record for id={}, this should not happen.修改了名称后又添加了与之前相同名称的字段", oldColId);
					return false;
				}
			}
		}
		colMap.values().forEach((oldCol)->{deleteColList.add(MaintainColumnVO.convert(oldCol));});
		result.setAddColList(addColList);
		result.setUpdateColList(updateColList);
		result.setDeleteColList(deleteColList);
		return true;
	}
	
	private void compareIndexChanges(List<SysIndexs> originalIndexList, ColumnIndexVO[] updateIndexs, TableUpdateConfirmVO result){
		List<MaintainIndexVO> addIndList = new ArrayList<>();
		List<MaintainIndexVO[]> updateIndList = new ArrayList<>();
		List<MaintainIndexVO> deleteIndList = new ArrayList<>();
		if(originalIndexList==null || originalIndexList.isEmpty()){
			if(updateIndexs!=null){
				for(ColumnIndexVO vo: updateIndexs){
					MaintainIndexVO add = new MaintainIndexVO();
					BeanUtils.copyProperties(vo, add);
					addIndList.add(add);
				}
			}
			result.setAddIndList(addIndList);
			return;
		}
		if(updateIndexs==null || updateIndexs.length==0){
			for(SysIndexs delIndex: originalIndexList){
				deleteIndList.add(MaintainIndexVO.convert(delIndex));
			}
			result.setDeleteIndList(deleteIndList);
			return;
		}
		Map<String,SysIndexs> indexMap = new HashMap<>(originalIndexList.size());
		originalIndexList.forEach((ind)->{
			indexMap.put(ind.getId(), ind);
		});
		for(ColumnIndexVO updateVO : updateIndexs){
			String id = updateVO.getIndexId();
			if(StringUtils.isBlank(id) || !indexMap.containsKey(id)){
				MaintainIndexVO add = new MaintainIndexVO();
				BeanUtils.copyProperties(updateVO, add);
				addIndList.add(add);
			}else{
				SysIndexs oldIndex = indexMap.get(id);
				MaintainIndexVO[] updatePair = new MaintainIndexVO[2];
				updatePair[0] = MaintainIndexVO.convert(oldIndex);
				updatePair[1] = new MaintainIndexVO();
				BeanUtils.copyProperties(updateVO, updatePair[1] );
				updateIndList.add(updatePair);
				indexMap.remove(id);
			}
		}
		indexMap.values().forEach((delIndex)->{
			deleteIndList.add(MaintainIndexVO.convert(delIndex));
		});
		result.setAddIndList(addIndList);
		result.setUpdateIndList(updateIndList);
		result.setDeleteIndList(deleteIndList);
	}

	@Override
	@Transactional
	public boolean updateTableWithColumn(MaintainTableVO updateTable) throws RsCommonException{
		TableUpdateConfirmVO confirmVO = confirmUpdateTable(updateTable);
		if(confirmVO==null){
			return false;
		}
		if(updateTableWithColumnConfirm(confirmVO)){
			if(confirmVO.getVersion()!=null){
				updateTable.setVersion(confirmVO.getVersion());
			}
			return true;
		}
		return false;
		
	}

	@Override
	@Transactional
	public boolean syncTable(String pjId, SysTables table, boolean forceDrop) throws RsCommonException {
		String tableid = table.getId();
		SysColumns columnParm = new SysColumns();
		columnParm.setTableid(tableid);
		RsEntityWrapper<SysColumns> colEw = new RsEntityWrapper<SysColumns>(columnParm);
		colEw.and("COLUMNS_CHGTYPE is not null or COLUMNS_CHGTYPE!=''");
		List<SysColumns> columns = generalMapper.selectList(colEw);
		List<SysColumns> colAddList = new ArrayList<>();
		List<SysColumns> colUpdateList = new ArrayList<>();
		List<SysColumns> colDeleteList = new ArrayList<>();
		for(SysColumns col:columns){
			switch (col.getChgtype()) {
			case ColChgTypes.ADD:
				colAddList.add(col);
				break;
			case ColChgTypes.UPDATE:
				colUpdateList.add(col);
				break;
			case ColChgTypes.DELETE:
				colDeleteList.add(col);
				break;
			default:
				break;
			}
		}
		try {
			String changeLogFilePath = liquibaseService.generateLiquibaseChangeLog4Pj(pjId, tableid, table.getName(), table.getComment(), false, colAddList, colDeleteList, colUpdateList, forceDrop);
			//TODO: index sync2017-3-16
			if(changeLogFilePath==null){
				logger.error("syncTable(): no changeLogFile generated for {}", table.toString());
				return false;
			}
			liquibaseService.executeUpdate4File(pjId, changeLogFilePath);
			//delete
			SysColumns delCols = new SysColumns();
			delCols.setTableid(tableid);
			delCols.setChgtype(ColChgTypes.DELETE);
			generalMapper.deleteSelective(delCols);
			//update
			SysColumns where = new SysColumns();
			where.setTableid(tableid);
			SysColumns entity = new SysColumns();
			entity.setChgtype("");
			entity.setOldname("");
			generalMapper.updateSelective(entity, where);
			table.setSync(Constants.YES);
			table.setVersion(table.getVersion()+1);
			generalMapper.updateById(table);
			
			return true;
		} catch (LiquibaseException e) {
			logger.error("syncTable(): failed to generate change log file for {}", table.toString());
			return false;
		}
		
	}
	
}
