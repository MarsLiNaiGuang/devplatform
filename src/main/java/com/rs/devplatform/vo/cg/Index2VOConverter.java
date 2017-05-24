package com.rs.devplatform.vo.cg;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.tamper.BeanMapping;
import com.alibaba.tamper.core.builder.BeanMappingBuilder;
import com.rs.devplatform.persistent.SysIndexs;
import com.rs.devplatform.vo.ColumnIndexVO;
import com.rs.devplatform.vo.MaintainIndexVO;

public class Index2VOConverter {

	private static BeanMappingBuilder index2VObuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(SysIndexs.class, ColumnIndexVO.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("id"), targetField("indexId")).convertor("convertor");
			fields(srcField("type"), targetField("indexType")).convertor("convertor");
			fields(srcField("field"), targetField("colName")).convertor("convertor");
			fields(srcField("name"), targetField("indexName")).convertor("convertor");
			fields(srcField("seq"), targetField("indexSeq")).convertor("convertor");
		}
	};
	private static BeanMappingBuilder maintainIndexVO2SysIndexbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(MaintainIndexVO.class, SysIndexs.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("indexId"), targetField("id")).convertor("convertor");
			fields(srcField("indexType"), targetField("type")).convertor("convertor");
			fields(srcField("colName"), targetField("field")).convertor("convertor");
			fields(srcField("indexName"), targetField("name")).convertor("convertor");
			fields(srcField("indexSeq"), targetField("seq")).convertor("convertor");
		}
	};
	private static BeanMappingBuilder columnIndexVO2SysIndexbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(ColumnIndexVO.class, SysIndexs.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("indexId"), targetField("id")).convertor("convertor");
			fields(srcField("indexType"), targetField("type")).convertor("convertor");
			fields(srcField("colName"), targetField("field")).convertor("convertor");
			fields(srcField("indexName"), targetField("name")).convertor("convertor");
			fields(srcField("indexSeq"), targetField("seq")).convertor("convertor");
		}
	};
	
	private static BeanMappingBuilder sysIndex2MaintainIndexVObuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(SysIndexs.class, MaintainIndexVO.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("id"), targetField("indexId")).convertor("convertor");
			fields(srcField("type"), targetField("indexType")).convertor("convertor");
			fields(srcField("field"), targetField("colName")).convertor("convertor");
			fields(srcField("name"), targetField("indexName")).convertor("convertor");
			fields(srcField("seq"), targetField("indexSeq")).convertor("convertor");
		}
	};
	
	public static MaintainIndexVO[] convertSysIndexList(List<SysIndexs> indexList){
		if(indexList==null||indexList.isEmpty()){
			return null;
		}
		BeanMapping mapping = new BeanMapping(sysIndex2MaintainIndexVObuilder);
		int size = indexList.size();
		MaintainIndexVO[] voArray = new MaintainIndexVO[size];
		for(int i=0;i<size;++i){
			MaintainIndexVO vo = new MaintainIndexVO();
			mapping.mapping(indexList.get(i), vo);
			voArray[i]=vo;
		}
		return voArray;
	}
	
	public static ColumnIndexVO convert(SysIndexs index){
		BeanMapping mapping = new BeanMapping(index2VObuilder);
		ColumnIndexVO indexVO = new ColumnIndexVO();
		mapping.mapping(index, indexVO);
		return indexVO;
	}
	
	public static SysIndexs convertColumnIndexVO(ColumnIndexVO indexVO){
		BeanMapping mapping = new BeanMapping(columnIndexVO2SysIndexbuilder);
		SysIndexs index = new SysIndexs();
		mapping.mapping(indexVO, index);
		return index;
	}
	
	public static List<SysIndexs> convertColumnIndexVOList(ColumnIndexVO[] indexVOs, String tableId, String columnPrefix){
		if(indexVOs==null || indexVOs.length==0){
			return null;
		}
		BeanMapping mapping = new BeanMapping(columnIndexVO2SysIndexbuilder);
		List<SysIndexs> indexList = new ArrayList<>(indexVOs.length);
		for(ColumnIndexVO vo:indexVOs){
			SysIndexs index = new SysIndexs();
			mapping.mapping(vo, index);
			String columnName = index.getField();
			index.setTableid(tableId);
			if(!columnName.contains(columnPrefix)){
				index.setField(columnPrefix+columnName);
			}
			indexList.add(index);
		}
		return indexList;
	}
	
	public static SysIndexs convertMaintainVO(MaintainIndexVO indexVO){
		BeanMapping mapping = new BeanMapping(maintainIndexVO2SysIndexbuilder);
		SysIndexs index = new SysIndexs();
		mapping.mapping(indexVO, index);
		return index;
	}
	
	public static List<SysIndexs> convertIndexVOArray(MaintainIndexVO[] array, String tableid){
		if(array==null || array.length==0){
			return null;
		}
		int size = array.length;
		List<SysIndexs> result = new ArrayList<>(size);
		BeanMapping mapping = new BeanMapping(maintainIndexVO2SysIndexbuilder);
		for(MaintainIndexVO vo: array){
			SysIndexs index = new SysIndexs();
			mapping.mapping(vo, index);
			index.setTableid(tableid);
			result.add(index);
		}
		return result;
	}
	
	public static List<SysIndexs> convertIndexVOList(List<MaintainIndexVO> list, String tableid){
		if(list==null || list.isEmpty()){
			return null;
		}
		MaintainIndexVO[] array = new MaintainIndexVO[list.size()];
		list.toArray(array);
		return convertIndexVOArray(array, tableid);
	}
	
	public static List<SysIndexs> convertMaintainIndexUpdateList(List<MaintainIndexVO[]> updateVOList, String tableid){
		if(updateVOList==null || !updateVOList.isEmpty()){
			return null;
		}
		List<MaintainIndexVO> updateList = new ArrayList<>(updateVOList.size());
		for(MaintainIndexVO[] voPair : updateVOList){
			MaintainIndexVO bfVO = voPair[0];
			if(bfVO.compareAndSetChange(voPair[1])){
				updateList.add(bfVO);
			}
		}
		return convertIndexVOList(updateList, tableid);
	}
	
}
