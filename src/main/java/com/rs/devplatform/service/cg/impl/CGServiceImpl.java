package com.rs.devplatform.service.cg.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.common.BuzException;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysCdval;
import com.rs.devplatform.persistent.SysColumns;
import com.rs.devplatform.persistent.SysCustbt;
import com.rs.devplatform.persistent.SysTables;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.persistent.SysZqjs;
import com.rs.devplatform.persistent.SysZqsql;
import com.rs.devplatform.persistent.mapper.CGMapper;
import com.rs.devplatform.service.admin.DataDictService;
import com.rs.devplatform.service.cg.CGService;
import com.rs.devplatform.vo.ColumnVO;
import com.rs.devplatform.vo.TableVO;
import com.rs.devplatform.vo.cg.Col2ZdxxConverter;
import com.rs.devplatform.vo.cg.Table2BxxConverter;
import com.rs.devplatform.vo.common.RsCommonResponse;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;

@Service
public class CGServiceImpl implements CGService {
	
	@Autowired
	RsGeneralMapper generalMapper;

	@Autowired
	CGMapper cgMapper;
	
	@Autowired
	DataDictService dataDictService;
	
	@Override
	// 获取指定模块的表单对应主表信息
	public SysBxx getForm(String tableId, String flag) throws BuzException {
		SysBxx sysBxx = generalMapper.selectById(tableId, SysBxx.class);
		sysBxx.setZiduanList(getZdxxList(tableId, flag));
		return sysBxx;
	}
	
	//根据主表的zb字段获取字表信息
	private List<SysBxx> GetDetailBXXByzb(String zb){
		JSONArray detailarray = new JSONArray();
		detailarray = JSONArray.parseArray(zb);
		String wheresql = "1<>1";
		//循环获取字表的fcflag
		if (detailarray != null && detailarray.size() > 0){
			for(int i=0;i<detailarray.size();i++){
				JSONObject json = detailarray.getJSONObject(i);				
				String fcflag = json.getString("fcflag");
				wheresql += " or BXX_FCFLAG = '" + fcflag + "'";  
			}
		}
		//根据fcflag值获取bxx
		SysBxx parm = new SysBxx();
		RsEntityWrapper<SysBxx> ew = new RsEntityWrapper<SysBxx>(parm);
		ew.andNew(wheresql);
		return generalMapper.selectList(ew);	
	}
	
	@Override
	// 获取指定模块的表单对应主从表完整信息
	public JSONObject getFormMasterDetail(String tableId, String flag) throws BuzException {
		JSONObject result = new JSONObject();
		SysBxx bxxMaster = getForm(tableId, flag);
		bxxMaster.setIsql("");
		result.put(RsCommonResponse.MASTER, bxxMaster);
		if (null != bxxMaster) {
			Map<String, List<SysCdval>> cdtypeMap = new HashMap<String, List<SysCdval>>();
			mergeDataDict(bxxMaster, cdtypeMap);
			//根据BXX主表中的zb字表信息
			List<SysBxx> bxxList = GetDetailBXXByzb(bxxMaster.getZb());
			
			for (int i = 0, len = bxxList.size(); i < len; ++i) {
				String tableIdDetail = bxxList.get(i).getId();
				SysBxx bxxDetail = getForm(tableIdDetail, flag);
				mergeDataDict(bxxDetail, cdtypeMap);
				bxxDetail.setIsql("");
				bxxList.set(i, bxxDetail);
			}
			result.put(RsCommonResponse.DETAIL, bxxList);
			result.put(RsCommonResponse.DATADICT, cdtypeMap);
		}
		return result;
	}

	private String getField(String table, String field){
		return (table.substring(table.indexOf("_")+1) + "_" + field).toUpperCase();
	}
	
	@Override
	// 获取指定模块的表单对应主表的字段列表信息
	public List<SysZdxx> getZdxxList(String tableId, String flag) {
		SysZdxx zdxxParm = new SysZdxx();
		zdxxParm.setBid(tableId);
		zdxxParm.setDeleted(Constants.DelInd.FALSE);
		RsEntityWrapper<SysZdxx> zdxxWrapper = new RsEntityWrapper<>(zdxxParm);
		zdxxWrapper.orderBy("ZDXX_XH");
		List<SysZdxx> zdxxList = generalMapper.selectList(zdxxWrapper);
		
		//增加ID、VERSION列
		Map<String, SysZdxx> zdxxmap = new HashMap<String, SysZdxx>();
		if (zdxxList.size() > 0)
		{
			for(SysZdxx list :zdxxList){
				zdxxmap.put(list.getName(), list);
			}
		}
		
		SysBxx bxx = generalMapper.selectById(tableId, SysBxx.class);
		
		if (!zdxxmap.containsKey(getField(bxx.getBm(), "id"))){
			SysZdxx zdxx_id = new SysZdxx();
			zdxx_id.setName(getField(bxx.getBm(),"id"));
			zdxx_id.setIsshow(Constants.NO);
			zdxx_id.setIsshowlb(Constants.NO);
			zdxx_id.setLx("string");
			zdxxList.add(zdxx_id);
		}
		
		if (!zdxxmap.containsKey(getField(bxx.getBm(), "version"))){
			SysZdxx zdxx_version = new SysZdxx();
			zdxx_version.setName(getField(bxx.getBm(),"version"));
			zdxx_version.setIsshow(Constants.NO);
			zdxx_version.setIsshowlb(Constants.NO);
			zdxx_version.setLx("int");
			zdxxList.add(zdxx_version);
		}
		if (Constants.TO_LOWCASE_FULL.equals(flag)) {
			for (SysZdxx sysZDXX : zdxxList) {
				String name = sysZDXX.getName();
				sysZDXX.setName(name.substring(name.indexOf("_") + 1).toLowerCase());
			}
		}else if(Constants.TO_LOWCASE_PART.equals(flag)) {
			for (SysZdxx sysZDXX : zdxxList) {
				String name = sysZDXX.getName();
				if (name.endsWith("_ID") || name.endsWith("_VERSION")) {
					sysZDXX.setName(name.substring(name.indexOf("_") + 1).toLowerCase());
				}
				
			}
		}
		
		
		
		return zdxxList;
	}
	
	@Override
	@Transactional
	public boolean addForm(TableVO tableVO) throws BuzException{
		SysBxx table = Table2BxxConverter.convert(tableVO);
		generalMapper.insert(table);
		String tableId = table.getId();
		tableVO.setTableId(tableId);
		tableVO.setVersion(table.getVersion());
		ColumnVO[] columns = tableVO.getColumns();
		List<SysZdxx> ziduanList = null;
		if(columns!=null){
			ziduanList = Col2ZdxxConverter.convertBatch(tableId, "", columns);
			if(!ziduanList.isEmpty()){
				generalMapper.insertBatch(ziduanList);
			}
		}
		return true;
	}

	@Override
	@Transactional
	public boolean updateForm(TableVO tableVO) throws BuzException{
		String tableId = tableVO.getTableId();
		SysBxx whereTable = new SysBxx();
		whereTable.setId(tableId);
		whereTable.setVersion(tableVO.getVersion());
		SysBxx entity = generalMapper.selectOne(whereTable);
		if(entity==null){
			return false;
		}
		compareColumnChangesAndUpdate(tableId, "", tableVO.getColumns());
		entity.setNr(tableVO.getTabledescp());
		entity.setPklx(tableVO.getPktype());
		entity.setLx(tableVO.getTabletype());
		entity.setIscbox(tableVO.getMultiselect());
		entity.setIspage(tableVO.getIspage());
		entity.setIstree(tableVO.getIstree());
		entity.setGxlx(tableVO.getRelationType());
		entity.setTabxh(tableVO.getRelationSeq());
		entity.setZb(tableVO.getSubtablesJSONString());
		entity.setVersion(tableVO.getVersion()+1);
		int effRow = generalMapper.update(entity, whereTable);
		if(effRow==0){
			return false;
		}
		tableVO.setVersion(entity.getVersion());
		return true;
	}
	
	
	/**
	 * 
	 * @param tableId
	 * @param columns
	 * @return
	 */
	private void compareColumnChangesAndUpdate(String tableId, String columnPrefix, ColumnVO[] columns){
		if(columns==null || columns.length==0){
			return;
		}
		Map<String,SysZdxx> colPrevMap = consolidateColumns4Table(tableId);
		List<SysZdxx> addList = new ArrayList<>();
		int index = 0;
		for(ColumnVO col:columns){
			col.setCol_seq(++index);
			if(!col.getColName().contains(columnPrefix)){
				col.setColName(columnPrefix+col.getColName());
			}
			String columnName = col.getColName();
			if(colPrevMap.containsKey(columnName)){
				SysZdxx original = colPrevMap.get(columnName);
				SysZdxx upd =Col2ZdxxConverter.convert(col);
				if(!upd.compareNonDBChange(original)){
					SysZdxx.copyNonDBChange(upd, original);
					generalMapper.updateById(original);
				}
				colPrevMap.remove(columnName);
			}
			else{
				SysZdxx upd = Col2ZdxxConverter.convert(col);
				upd.setId(null);
				upd.setBid(tableId);
				addList.add(upd);
			}
		}
		if(!addList.isEmpty()){
			generalMapper.insertBatch(addList);
		}
		//rest to be removed
		List<String> removeList = new ArrayList<>();
		colPrevMap.values().stream().forEach((arr)->{
			removeList.add(arr.getId());//需要删除该条记录
		});
		if(!removeList.isEmpty()){
			generalMapper.deleteBatchIds(removeList, SysZdxx.class);
		}
	}
	
	/**
	 * 
	 * @param tableId
	 * @return SysZdxx[] : [0]:chgType=null, [1]:chgType is not null
	 */
	private Map<String, SysZdxx> consolidateColumns4Table(String tableId){
		SysZdxx zdParm = new SysZdxx();
		zdParm.setBid(tableId);
		//need to add additional column to indicate if the column is update/delete/add.
		List<SysZdxx> colList = generalMapper.selectList(new RsEntityWrapper<>(zdParm));
		if(colList==null || colList.isEmpty()){
			return null;
		}
		Map<String,SysZdxx> map = new HashMap<>();
		for(SysZdxx zd : colList){
			map.put(zd.getName(), zd);
		}
		return map;
	}
	
	@Override
	@Transactional
	public boolean deleteForm(String pjId, SysBxx table, boolean forceDrop) {
		int effRow = generalMapper.deleteSelective(table);
		if(effRow==0){
			return false;
		}
		SysZdxx ziduan = new SysZdxx();
		ziduan.setBid(table.getId());
		generalMapper.deleteSelective(ziduan);
		return true;
	}

	private void mergeDataDict(SysBxx bxx, Map<String, List<SysCdval>> cdtypeMap) {
		if (null != bxx) {
			List<SysZdxx> zdxxList = bxx.getZiduanList();
			for (SysZdxx zdxx : zdxxList) {
				String zdzd = zdxx.getZdzd();
				if (null != zdzd && !zdzd.isEmpty()) {
					if (null == cdtypeMap.get(zdzd)) {
						List<SysCdval> cdvalList = dataDictService.getValListByCdtype(zdzd);
						if (null != cdvalList) cdtypeMap.put(zdzd, cdvalList);
					}
				}
			}
		}
	}
	
	/*自定义按钮的增删改*/
	@Override
	@Transactional
	public boolean addCustbt(SysCustbt custbt) {
		generalMapper.insert(custbt);
		return true;
	}

	@Override
	@Transactional
	public boolean updateCustbt(SysCustbt custbt) {
		generalMapper.updateSelectiveById(custbt);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteCustbt(List<String> idList) {
		generalMapper.deleteBatchIds(idList, SysCustbt.class);
		return true;
	}
	
	/*增强JS的增删改*/
	@Override
	@Transactional
	public boolean addZqjs(SysZqjs zqjs) {
		generalMapper.insert(zqjs);
		return true;
	}

	@Override
	@Transactional
	public boolean updateZqjs(SysZqjs zqjs) {
		generalMapper.updateSelectiveById(zqjs);
		return true;
	}

	@Override
	@Transactional
	public boolean deleteZqjs(String id) {
		generalMapper.deleteById(id, SysZqjs.class);
		return true;
	}
	
	//增强Sql的增、删、改
		@Override
		@Transactional
		public boolean addZqsql(SysZqsql zqsql) {
			generalMapper.insert(zqsql);
			return true;
		}
		
		@Override
		@Transactional
		public boolean updateZqsql(SysZqsql zqsql) {
			generalMapper.updateSelectiveById(zqsql);
			return true;
		}
		
		@Override
		@Transactional
		public boolean deleteZqsql(String id) {
			generalMapper.deleteById(id, SysZqsql.class);
			return true;
		}
		
	@Override
	public List<SysColumns> getCurrentAvailableColumns(String tablename) {
		SysTables parm = new SysTables();
		parm.setName(tablename.toLowerCase());
		RsEntityWrapper<SysTables> ew = new RsEntityWrapper<SysTables>(parm, "TABLES_ID as id");
		List<SysTables> tables = generalMapper.selectList(ew);
		if (tables != null && !tables.isEmpty()) {
			SysColumns colParm = new SysColumns();
			colParm.setTableid(tables.get(0).getId());
			RsEntityWrapper<SysColumns> colew = new RsEntityWrapper<SysColumns>(colParm);
			colew.where("COLUMNS_CHGTYPE is null or COLUMNS_CHGTYPE!='A'");
			colew.orderBy("COLUMNS_SEQ");
			List<SysColumns> list = generalMapper.selectList(colew);
			for (SysColumns col : list) {
				if (StringUtils.isNotBlank(col.getOldname())) {
					col.setName(col.getOldname());
				}
			}
			return list;
		}
		return null;
	}

}
