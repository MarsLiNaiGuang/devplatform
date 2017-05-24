package com.rs.devplatform.vo.cg;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.tamper.BeanMapping;
import com.alibaba.tamper.core.builder.BeanMappingBuilder;
import com.rs.devplatform.common.CGConstants.ColChgTypes;
import com.rs.devplatform.persistent.SysColumns;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.vo.MaintainColumnVO;

public class SysColumnsConverter {

	private static BeanMappingBuilder zd2Columnbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(SysZdxx.class,SysColumns.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("id"), 			targetField("id")).convertor("convertor");
			fields(srcField("bid"), 			targetField("tableid")).convertor("convertor");
			fields(srcField("name"), 		targetField("name")).convertor("convertor");
			fields(srcField("nr"), 			targetField("comment")).convertor("convertor");
			fields(srcField("iskey"), 		targetField("ispk")).convertor("convertor");
			fields(srcField("lx"), 			targetField("type")).convertor("convertor");
			fields(srcField("cd"), 			targetField("length")).convertor("convertor");
			fields(srcField("plen"), 		targetField("decpoint")).convertor("convertor");
			fields(srcField("isnull"), 		targetField("isnull")).convertor("convertor");
			fields(srcField("mr"), 			targetField("defval")).convertor("convertor");
			fields(srcField("xh"), 			targetField("seq")).convertor("convertor");
			fields(srcField("deleted"), 	targetField("deleted")).convertor("convertor");
		}
	};
	
	/*private static BeanMappingBuilder columnVO2Columnbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(ColumnVO.class,SysColumns.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("col_id"), 			targetField("id")).convertor("convertor");
			fields(srcField("col_name"), 		targetField("name")).convertor("convertor");
			fields(srcField("col_mark"), 			targetField("comment")).convertor("convertor");
			fields(srcField("col_ispk"), 		targetField("ispk")).convertor("convertor");
			fields(srcField("col_type"), 			targetField("type")).convertor("convertor");
			fields(srcField("col_length"), 			targetField("length")).convertor("convertor");
			fields(srcField("col_decpoint"), 		targetField("decpoint")).convertor("convertor");
			fields(srcField("col_isnull"), 		targetField("isnull")).convertor("convertor");
			fields(srcField("col_defval"), 			targetField("defval")).convertor("convertor");
			fields(srcField("col_seq"), 			targetField("seq")).convertor("convertor");
			fields(srcField("deleted"), 	targetField("deleted")).convertor("convertor");
		}
	};*/
	
	private static BeanMappingBuilder sysColumn2MaintainColumnVObuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(SysColumns.class,MaintainColumnVO.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("id"), 			targetField("col_id")).convertor("convertor");
			fields(srcField("name"), 		targetField("col_name")).convertor("convertor");
			fields(srcField("comment"), 	targetField("col_mark")).convertor("convertor");
			fields(srcField("ispk"), 			targetField("col_ispk")).convertor("convertor");
			fields(srcField("type"), 		targetField("col_type")).convertor("convertor");
			fields(srcField("length"), 		targetField("col_length")).convertor("convertor");
			fields(srcField("decpoint"), 	targetField("col_decpoint")).convertor("convertor");
			fields(srcField("isnull"), 		targetField("col_isnull")).convertor("convertor");
			fields(srcField("defval"), 		targetField("col_defval")).convertor("convertor");
			fields(srcField("seq"), 			targetField("col_seq")).convertor("convertor");
			fields(srcField("deleted"), 	targetField("deleted")).convertor("convertor");
			fields(srcField("version"), 	targetField("version")).convertor("convertor");
		}
	};
	
	private static BeanMappingBuilder maintainColumnVO2SysColumnbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(MaintainColumnVO.class,SysColumns.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("col_id"), 			targetField("id")).convertor("convertor");
			fields(srcField("col_name"), 		targetField("name")).convertor("convertor");
			fields(srcField("colOldname"), 		targetField("oldname")).convertor("convertor");
			fields(srcField("col_mark"), 		targetField("comment")).convertor("convertor");
			fields(srcField("col_ispk"), 		targetField("ispk")).convertor("convertor");
			fields(srcField("col_type"), 		targetField("type")).convertor("convertor");
			fields(srcField("col_length"), 	targetField("length")).convertor("convertor");
			fields(srcField("col_decpoint"), targetField("decpoint")).convertor("convertor");
			fields(srcField("col_isnull"), 		targetField("isnull")).convertor("convertor");
			fields(srcField("col_defval"), 	targetField("defval")).convertor("convertor");
			fields(srcField("col_seq"), 		targetField("seq")).convertor("convertor");
			fields(srcField("deleted"), 		targetField("deleted")).convertor("convertor");
			fields(srcField("version"), 	targetField("version")).convertor("convertor");
		}
	};
	
	@SuppressWarnings("unused")
	private static BeanMappingBuilder column2ZDbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(SysColumns.class, SysZdxx.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("id"), 			targetField("id")).convertor("convertor");
			fields(srcField("tableid"), 	targetField("bid")).convertor("convertor");
			fields(srcField("name"), 		targetField("name")).convertor("convertor");
			fields(srcField("comment"), 	targetField("nr")).convertor("convertor");
			fields(srcField("ispk"), 			targetField("iskey")).convertor("convertor");
			fields(srcField("type"), 		targetField("lx")).convertor("convertor");
			fields(srcField("length"), 		targetField("cd")).convertor("convertor");
			fields(srcField("decpoint"), 	targetField("plen")).convertor("convertor");
			fields(srcField("isnull"), 		targetField("isnull")).convertor("convertor");
			fields(srcField("defval"), 		targetField("mr")).convertor("convertor");
			fields(srcField("seq"), 			targetField("xh")).convertor("convertor");
			fields(srcField("deleted"), 	targetField("deleted")).convertor("convertor");
			fields(srcField("version"), 	targetField("version")).convertor("convertor");
		}
	};
	
	public static SysColumns convertZdxx(SysZdxx zdxx){
		BeanMapping mapping = new BeanMapping(zd2Columnbuilder);
		SysColumns column = new SysColumns();
		mapping.mapping(zdxx, column);
		return column;
	}
	
	public static List<SysColumns> convertZdxxList(List<SysZdxx> zdxxList){
		if(zdxxList==null || zdxxList.isEmpty()){
			return null;
		}
		BeanMapping mapping = new BeanMapping(zd2Columnbuilder);
		List<SysColumns> columnList = new ArrayList<>(zdxxList.size());
		SysColumns column = null;
		for(SysZdxx zdxx:zdxxList){
			column = new SysColumns();
			mapping.mapping(zdxx, column);
			columnList.add(column);
		}
		return columnList;
	}
	
	public static MaintainColumnVO[] convertSysColumnList(List<SysColumns> columnList){
		if(columnList==null || columnList.isEmpty()){
			return null;
		}
		BeanMapping mapping = new BeanMapping(sysColumn2MaintainColumnVObuilder);
		int size = columnList.size();
		MaintainColumnVO[] result = new MaintainColumnVO[size];
		MaintainColumnVO vo = null;
		for(int i=0;i<size;++i){
			vo = new MaintainColumnVO();
			mapping.mapping(columnList.get(i), vo);
			result[i] = vo;
		}
		return result;
	}
	public static MaintainColumnVO[] convertSysColumnList(String columnPrefix, List<SysColumns> columnList){
		if(columnList==null || columnList.isEmpty()){
			return null;
		}
		BeanMapping mapping = new BeanMapping(sysColumn2MaintainColumnVObuilder);
		int size = columnList.size();
		MaintainColumnVO[] result = new MaintainColumnVO[size];
		MaintainColumnVO vo = null;
		for(int i=0;i<size;++i){
			vo = new MaintainColumnVO();
			mapping.mapping(columnList.get(i), vo);
			vo.setCol_name(vo.getCol_name().replace(columnPrefix, ""));
			result[i] = vo;
		}
		return result;
	}
	
	public static MaintainColumnVO convertSysColumn(SysColumns column){
		if(column==null){
			return null;
		}
		BeanMapping mapping = new BeanMapping(sysColumn2MaintainColumnVObuilder);
		MaintainColumnVO vo = new MaintainColumnVO();
		mapping.mapping(column, vo);
		return vo;
	}
	
	/*
	public static SysColumns convertColumnVO(ColumnVO columnVO){
		BeanMapping mapping = new BeanMapping(columnVO2Columnbuilder);
		SysColumns column = new SysColumns();
		mapping.mapping(columnVO, column);
		return column;
	}
	
	public static List<SysColumns> convertColumnVOList(List<ColumnVO> columnVOList){
		if(columnVOList==null || columnVOList.isEmpty()){
			return null;
		}
		BeanMapping mapping = new BeanMapping(columnVO2Columnbuilder);
		List<SysColumns> columnList = new ArrayList<>(columnVOList.size());
		SysColumns column = null;
		for(ColumnVO colVO:columnVOList){
			column = new SysColumns();
			mapping.mapping(colVO, column);
			columnList.add(column);
		}
		return columnList;
	}*/
	
	public static List<SysColumns> convertMaintainColumnArray(MaintainColumnVO[] array, String tableid){
		if(array==null || array.length==0){
			return null;
		}
		int size = array.length;
		List<SysColumns> result = new ArrayList<>(size);
		BeanMapping mapping = new BeanMapping(maintainColumnVO2SysColumnbuilder);
		for(MaintainColumnVO vo:array){
			SysColumns col = new SysColumns();
			mapping.mapping(vo, col);
			col.setTableid(tableid);
			result.add(col);
		}
		return result;
	}
	
	public static List<SysColumns> convertMaintainColumnList(List<MaintainColumnVO> list, String tableid){
		if(list==null || list.isEmpty()){
			return null;
		}
		int size = list.size();
		List<SysColumns> result = new ArrayList<>(size);
		BeanMapping mapping = new BeanMapping(maintainColumnVO2SysColumnbuilder);
		for(MaintainColumnVO vo:list){
			SysColumns col = new SysColumns();
			mapping.mapping(vo, col);
			col.setTableid(tableid);
			result.add(col);
		}
		return result;
	}
	public static List<SysColumns> convertMaintainColumnList(List<MaintainColumnVO> list, String tableid, String chgType){
		if(list==null || list.isEmpty()){
			return null;
		}
		int size = list.size();
		List<SysColumns> result = new ArrayList<>(size);
		BeanMapping mapping = new BeanMapping(maintainColumnVO2SysColumnbuilder);
		for(MaintainColumnVO vo:list){
			SysColumns col = new SysColumns();
			mapping.mapping(vo, col);
			col.setTableid(tableid);
			col.setChgtype(chgType);
			col.setColumnChangeTypes(vo.getColumnChangeTypes());
			result.add(col);
		}
		return result;
	}
	
	public static List<SysColumns> convertMaintainColumnUpdateList(List<MaintainColumnVO[]> updateColList, String tableid ){
		if(updateColList==null || updateColList.isEmpty()){
			return null;
		}
		List<MaintainColumnVO> updateList = new ArrayList<>(updateColList.size());
		for(MaintainColumnVO[] colPair : updateColList){
			MaintainColumnVO bfVO = colPair[0];
			if(bfVO.compareAndSetChange(colPair[1])){
				updateList.add(bfVO);
			}
		}
		return convertMaintainColumnList(updateList, tableid, ColChgTypes.UPDATE);
	}
	
	
}
