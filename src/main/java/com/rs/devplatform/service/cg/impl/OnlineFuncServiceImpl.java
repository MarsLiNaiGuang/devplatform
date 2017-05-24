package com.rs.devplatform.service.cg.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.service.cg.OnlineFuncService;
import com.rs.devplatform.vo.cg.Col2ZdxxConverter;
import com.rs.devplatform.vo.cg.Table2BxxConverter;
import com.rs.devplatform.vo.onlinefc.OnlineFcColumn;
import com.rs.devplatform.vo.onlinefc.OnlineFcCondition;
import com.rs.devplatform.vo.onlinefc.OnlineFuncVO;
import com.rs.framework.common.Constants;
import com.rs.framework.common.service.base.CRUDBaseService;

@Service
public class OnlineFuncServiceImpl extends CRUDBaseService implements OnlineFuncService {

	@Override
	@Transactional
	public boolean saveOnlineFunc(OnlineFuncVO funcVO) {
		SysBxx bxx = Table2BxxConverter.convertFromOnlineFuncVO(funcVO);
		generalMapper.insert(bxx);
		String tableId = bxx.getId();
		//filter duplicate with condition then insert
		List<SysZdxx> allColumns = consolidateColumnConditions(tableId, funcVO.getColumns(), funcVO.getConditions());
		generalMapper.insertBatch(allColumns);
		funcVO.setTableId(tableId);
		funcVO.setVersion(bxx.getVersion());
		return true;
	}
	
	private List<SysZdxx> consolidateColumnConditions(String tableId, OnlineFcColumn[] columns, OnlineFcCondition[] conditions){
		List<SysZdxx> columnList = Col2ZdxxConverter.convertBatch(tableId, "", columns);
		List<SysZdxx> conditionList = Col2ZdxxConverter.convertBatch(tableId, "", conditions);
		Map<String,SysZdxx> columnMap = new LinkedHashMap<>();
		int columnSeq = 0;
		for(SysZdxx zd:columnList){
			zd.setXh(++columnSeq);
			columnMap.put(zd.getName().toUpperCase(), zd);
		}
		if(conditionList!=null){
			int conditionSeq = 0;
			for(SysZdxx zd:conditionList){
				String name = zd.getName().toUpperCase();
				if(columnMap.containsKey(name)){
					SysZdxx col = columnMap.get(name);
					col.setXslx(zd.getXslx());
					col.setLen(zd.getLen());
					col.setCxms(zd.getCxms());
					col.setKzjson(zd.getKzjson());
					col.setIsquery(zd.getIsquery());
					col.setCondflag("B");//both:即作为数据列，又作为条件
				}else{
					if(StringUtils.isBlank(zd.getIsshow())){
						zd.setIsshow(Constants.NO);
					}
					if(StringUtils.isBlank(zd.getIsshowlb())){
						zd.setIsshowlb(Constants.NO);
					}
					zd.setCondflag("C");//C:condition仅作为条件
					zd.setXh(++conditionSeq);
					columnMap.put(name, zd);
				}
			}
		}
		List<SysZdxx> allColumns = new ArrayList<>(columnMap.size());
		columnMap.values().forEach(allColumns::add);
		return allColumns;
	}

	@Transactional
	@Override
	public boolean updateOnlineFunc(OnlineFuncVO funcVO) {
		SysBxx bxx = Table2BxxConverter.convertFromOnlineFuncVO(funcVO);
		SysBxx parm = new SysBxx();
		parm.setId(bxx.getId());
		parm.setVersion(bxx.getVersion());
		SysBxx bxxDB = generalMapper.selectOne(parm);
		if(bxxDB==null){
			return false;
		}
		String tableId = bxx.getId();
		List<SysZdxx> allColumns = consolidateColumnConditions(tableId, funcVO.getColumns(), funcVO.getConditions());
		SysZdxx zdParm = new SysZdxx();
		zdParm.setBid(tableId);
		List<SysZdxx> originalColumnCondList = generalMapper.selectList(zdParm);
		Map<String,SysZdxx> existColumnMap = new HashMap<>(originalColumnCondList.size());
		Map<String,String> existColumnIdNameMap = new HashMap<>(originalColumnCondList.size());
		for(SysZdxx zd:originalColumnCondList){
			existColumnMap.put(zd.getName().toUpperCase(), zd);
			existColumnIdNameMap.put(zd.getId(), zd.getName().toUpperCase());
		}
		List<SysZdxx> updateList = new ArrayList<>();
		List<SysZdxx> addList = new ArrayList<>();
		int columnSeq = 0;
		int conditionSeq = 0;
		for(SysZdxx zd:allColumns){
			String colName = zd.getName().toUpperCase();
			String colID = zd.getId();
			if(colID!=null && existColumnIdNameMap.containsKey(colID)){
				colName = existColumnIdNameMap.get(colID);
			}
			if(existColumnMap.containsKey(colName)){
				SysZdxx zdDB = existColumnMap.get(colName);
				if(!zd.compareNonDBChange(zdDB)){
					zd.setId(zdDB.getId());
					zd.setVersion(zdDB.getVersion());
					updateList.add(zd);
				}
				int[] seqArray = getMaxSeq(columnSeq, conditionSeq, zd);
				columnSeq = seqArray[0];
				conditionSeq = seqArray[1];
				existColumnMap.remove(colName);
			}else{
				zd.setId(null);
				addList.add(zd);
			}
		}
		if(!existColumnMap.isEmpty()){
			List<String> deleteIds = new ArrayList<>(existColumnMap.size());
			existColumnMap.values().stream().map((zd)->{
				return zd.getId();
			}).forEach(deleteIds::add);
			generalMapper.deleteBatchIds(deleteIds, SysZdxx.class);
		}
		
		for(SysZdxx zd:updateList){
			zd.setVersion(zd.getVersion()+1);
			generalMapper.updateSelectiveById(zd);
		}
		if(!addList.isEmpty()){
			for(SysZdxx zd:addList){
				String condFlag = zd.getCondflag();
				if(condFlag==null){
					zd.setXh(++columnSeq);
				}else{
					switch (condFlag) {
					case "B":
						zd.setXh(++columnSeq);
						break;
					case "C":
						zd.setXh(++conditionSeq);
						break;
					default:
						zd.setXh(++columnSeq);
						break;
					}
				}
			}
			generalMapper.insertBatch(addList);
		}
		if(!bxx.compareChange(bxxDB)){
			bxx.setVersion(bxx.getVersion()+1);
			generalMapper.updateSelectiveById(bxx);
			funcVO.setVersion(bxx.getVersion());
		}
		return true;
	}
	
	private int[] getMaxSeq(int currColumnSeq, int currConditionSeq, SysZdxx zd){
		Integer seq = zd.getXh();
		String condFlag = zd.getCondflag();
		if(condFlag==null){
			if(seq!=null && seq>currColumnSeq){
				currColumnSeq = seq;
			}
		}else{
			switch (condFlag) {
			case "B":
				if(seq!=null && seq>currColumnSeq){
					currColumnSeq = seq;
				}
				if(seq!=null && seq>currConditionSeq){
					currConditionSeq = seq;
				}
				break;
			case "C":
				if(seq!=null && seq>currConditionSeq){
					currConditionSeq = seq;
				}
				break;
			default:
				if(seq!=null && seq>currColumnSeq){
					currColumnSeq = seq;
				}
				break;
			}
		}
		return new int[]{currColumnSeq, currConditionSeq};
	}

}
