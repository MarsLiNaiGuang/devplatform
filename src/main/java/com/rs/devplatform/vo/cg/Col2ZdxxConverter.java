package com.rs.devplatform.vo.cg;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.tamper.BeanMapping;
import com.alibaba.tamper.core.builder.BeanMappingBuilder;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.vo.ColumnVO;
import com.rs.devplatform.vo.onlinefc.OnlineFcColumn;
import com.rs.devplatform.vo.onlinefc.OnlineFcCondition;

public class Col2ZdxxConverter {

	private static BeanMappingBuilder zd2VObuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(SysZdxx.class,ColumnVO.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("xh"), targetField("col_seq")).convertor("convertor");
			fields(srcField("name"), targetField("colName")).convertor("convertor");
			fields(srcField("nr"), targetField("colMark")).convertor("convertor");
			fields(srcField("cd"), targetField("col_length")).convertor("convertor");
			fields(srcField("plen"), targetField("col_decpoint")).convertor("convertor");
			fields(srcField("mr"), targetField("col_defval")).convertor("convertor");
			fields(srcField("lx"), targetField("colType")).convertor("convertor");
			fields(srcField("iskey"), targetField("col_ispk")).convertor("convertor");
			fields(srcField("isnull"), targetField("col_isnull")).convertor("convertor");
			fields(srcField("isshow"), targetField("isformdisp")).convertor("convertor");
			fields(srcField("isshowlb"), targetField("isgriddisp")).convertor("convertor");
			fields(srcField("xslx"), targetField("inputtype")).convertor("convertor");
			fields(srcField("len"), targetField("inputlen")).convertor("convertor");
			fields(srcField("isquery"), targetField("issearch")).convertor("convertor");
			fields(srcField("cxms"), targetField("searchtype")).convertor("convertor");
			fields(srcField("id"), targetField("col_id")).convertor("convertor");
			fields(srcField("deleted"), targetField("deleted")).convertor("convertor");
			
			fields(srcField("href"), targetField("colHref")).convertor("convertor");
			fields(srcField("yzlx"), targetField("validType")).convertor("convertor");
			fields(srcField("zdbm"), targetField("dictTable")).convertor("convertor");
			fields(srcField("zdzd"), targetField("dictCode")).convertor("convertor");
			fields(srcField("zdwb"), targetField("dictText")).convertor("convertor");
			fields(srcField("zb"), targetField("fkTableName")).convertor("convertor");
			fields(srcField("zzd"), targetField("fkColName")).convertor("convertor");
		}
	};
	private static BeanMappingBuilder vo2ZDbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(ColumnVO.class, SysZdxx.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("col_seq"), targetField("xh")).convertor("convertor");
			fields(srcField("colName"), targetField("name")).convertor("convertor");
			fields(srcField("colMark"), targetField("nr")).convertor("convertor");
			fields(srcField("col_length"), targetField("cd")).convertor("convertor");
			fields(srcField("col_decpoint"), targetField("plen")).convertor("convertor");
			fields(srcField("col_defval"), targetField("mr")).convertor("convertor");
			fields(srcField("colType"), targetField("lx")).convertor("convertor");
			fields(srcField("col_ispk"), targetField("iskey")).convertor("convertor");
			fields(srcField("col_isnull"), targetField("isnull")).convertor("convertor");
			fields(srcField("isformdisp"), targetField("isshow")).convertor("convertor");
			fields(srcField("isgriddisp"), targetField("isshowlb")).convertor("convertor");
			fields(srcField("inputtype"), targetField("xslx")).convertor("convertor");
			fields(srcField("inputlen"), targetField("len")).convertor("convertor");
			fields(srcField("issearch"), targetField("isquery")).convertor("convertor");
			fields(srcField("searchtype"), targetField("cxms")).convertor("convertor");
			fields(srcField("col_id"), targetField("id")).convertor("convertor");
			fields(srcField("deleted"), targetField("deleted")).convertor("convertor");
			
			fields(srcField("colHref"), targetField("href")).convertor("convertor");
			fields(srcField("validType"), targetField("yzlx")).convertor("convertor");
			fields(srcField("dictTable"), targetField("zdbm")).convertor("convertor");
			fields(srcField("dictCode"), targetField("zdzd")).convertor("convertor");
			fields(srcField("dictText"), targetField("zdwb")).convertor("convertor");
			fields(srcField("fkTableName"), targetField("zb")).convertor("convertor");
			fields(srcField("fkColName"), targetField("zzd")).convertor("convertor");
		}
	};
	
	private static BeanMappingBuilder OnlineFcCondition2ZDbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(OnlineFcCondition.class, SysZdxx.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("col_seq"), targetField("xh")).convertor("convertor");
			fields(srcField("colName"), targetField("name")).convertor("convertor");
			fields(srcField("colMark"), targetField("nr")).convertor("convertor");
			fields(srcField("colType"), targetField("lx")).convertor("convertor");
			fields(srcField("isformdisp"), targetField("isshow")).convertor("convertor");
			fields(srcField("isgriddisp"), targetField("isshowlb")).convertor("convertor");
			fields(srcField("inputtype"), targetField("xslx")).convertor("convertor");
			fields(srcField("inputlen"), targetField("len")).convertor("convertor");
			fields(srcField("issearch"), targetField("isquery")).convertor("convertor");
			fields(srcField("searchtype"), targetField("cxms")).convertor("convertor");
			fields(srcField("col_id"), targetField("id")).convertor("convertor");
			fields(srcField("expr"), targetField("kzjson")).convertor("convertor");
		}
	};
	private static BeanMappingBuilder ZD2OnlineFcConditionbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(SysZdxx.class, OnlineFcCondition.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("xh"), targetField("col_seq")).convertor("convertor");
			fields(srcField("name"), targetField("colName")).convertor("convertor");
			fields(srcField("nr"), targetField("colMark")).convertor("convertor");
			fields(srcField("lx"), targetField("colType")).convertor("convertor");
			fields(srcField("isshow"), targetField("isformdisp")).convertor("convertor");
			fields(srcField("isshowlb"), targetField("isgriddisp")).convertor("convertor");
			fields(srcField("xslx"), targetField("inputtype")).convertor("convertor");
			fields(srcField("len"), targetField("inputlen")).convertor("convertor");
			fields(srcField("isquery"), targetField("issearch")).convertor("convertor");
			fields(srcField("cxms"), targetField("searchtype")).convertor("convertor");
			fields(srcField("id"), targetField("col_id")).convertor("convertor");
			fields(srcField("kzjson"), targetField("expr")).convertor("convertor");
		}
	};
	private static BeanMappingBuilder OnlineFcColumn2ZDbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(OnlineFcCondition.class, SysZdxx.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("col_seq"), targetField("xh")).convertor("convertor");
			fields(srcField("colName"), targetField("name")).convertor("convertor");
			fields(srcField("colMark"), targetField("nr")).convertor("convertor");
			fields(srcField("col_length"), targetField("cd")).convertor("convertor");
			fields(srcField("col_decpoint"), targetField("plen")).convertor("convertor");
			fields(srcField("col_defval"), targetField("mr")).convertor("convertor");
			fields(srcField("colType"), targetField("lx")).convertor("convertor");
			fields(srcField("col_ispk"), targetField("iskey")).convertor("convertor");
			fields(srcField("col_isnull"), targetField("isnull")).convertor("convertor");
			fields(srcField("isformdisp"), targetField("isshow")).convertor("convertor");
			fields(srcField("isgriddisp"), targetField("isshowlb")).convertor("convertor");
			fields(srcField("inputtype"), targetField("xslx")).convertor("convertor");
			fields(srcField("inputlen"), targetField("len")).convertor("convertor");
			fields(srcField("issearch"), targetField("isquery")).convertor("convertor");
			fields(srcField("searchtype"), targetField("cxms")).convertor("convertor");
			fields(srcField("col_id"), targetField("id")).convertor("convertor");
			fields(srcField("deleted"), targetField("deleted")).convertor("convertor");
			fields(srcField("validType"), targetField("yzlx")).convertor("convertor");
			fields(srcField("dictTable"), targetField("zdbm")).convertor("convertor");
			fields(srcField("dictCode"), targetField("zdzd")).convertor("convertor");
			fields(srcField("dictText"), targetField("zdwb")).convertor("convertor");
			fields(srcField("expr"), targetField("kzjson")).convertor("convertor");
		}
	};
	private static BeanMappingBuilder ZD2OnlineFcColumnbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(SysZdxx.class, OnlineFcCondition.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("xh"), targetField("col_seq")).convertor("convertor");
			fields(srcField("name"), targetField("colName")).convertor("convertor");
			fields(srcField("nr"), targetField("colMark")).convertor("convertor");
			fields(srcField("lx"), targetField("colType")).convertor("convertor");
			fields(srcField("iskey"), targetField("col_ispk")).convertor("convertor");
			fields(srcField("isnull"), targetField("col_isnull")).convertor("convertor");
			fields(srcField("isshow"), targetField("isformdisp")).convertor("convertor");
			fields(srcField("isshowlb"), targetField("isgriddisp")).convertor("convertor");
			fields(srcField("xslx"), targetField("inputtype")).convertor("convertor");
			fields(srcField("len"), targetField("inputlen")).convertor("convertor");
			fields(srcField("isquery"), targetField("issearch")).convertor("convertor");
			fields(srcField("cxms"), targetField("searchtype")).convertor("convertor");
			fields(srcField("id"), targetField("col_id")).convertor("convertor");
			fields(srcField("deleted"), targetField("deleted")).convertor("convertor");
			fields(srcField("yzlx"), targetField("validType")).convertor("convertor");
			fields(srcField("zdbm"), targetField("dictTable")).convertor("convertor");
			fields(srcField("zdzd"), targetField("dictCode")).convertor("convertor");
			fields(srcField("zdwb"), targetField("dictText")).convertor("convertor");
		}
	};
	
	public static ColumnVO convert(SysZdxx zdxx){
		BeanMapping mapping = new BeanMapping(zd2VObuilder);
		ColumnVO columnVO = new ColumnVO();
		mapping.mapping(zdxx, columnVO);
		return columnVO;
	}
	
	public static SysZdxx convert(ColumnVO vo){
		BeanMapping mapping = new BeanMapping(vo2ZDbuilder);
		SysZdxx zd = new SysZdxx();
		mapping.mapping(vo, zd);
		return zd;
	}
	
	public static List<SysZdxx> convertBatch(String tableId, String columnPrefix, OnlineFcCondition[] columns){
		BeanMapping mapping = new BeanMapping(OnlineFcCondition2ZDbuilder);
		List<SysZdxx> result = new ArrayList<>(columns.length);
		SysZdxx ziduan = null;
		int colSeq = 0;
		for(ColumnVO col:columns){
			ziduan = new SysZdxx();
			mapping.mapping(col, ziduan);
			ziduan.setName(columnPrefix+col.getColName());
			ziduan.setBid(tableId);
			ziduan.setXh(++colSeq);
			result.add(ziduan);
		}
		return result;
	}
	public static OnlineFcCondition[] convertBatch2OnlineFcCondition(String columnPrefix, List<SysZdxx> conditionList){
		if(conditionList==null || conditionList.isEmpty()){
			return null;
		}
		BeanMapping mapping = new BeanMapping(ZD2OnlineFcConditionbuilder);
		int size = conditionList.size();
		OnlineFcCondition[] result = new OnlineFcCondition[size];
		OnlineFcCondition cond = null;
		for(int i=0;i<size;++i){
			SysZdxx col = conditionList.get(i);
			cond = new OnlineFcCondition();
			mapping.mapping(col, cond);
			result[i] = cond;
		}
		return result;
	}
	
	public static List<SysZdxx> convertBatch(String tableId, String columnPrefix, OnlineFcColumn[] columns){
		BeanMapping mapping = new BeanMapping(OnlineFcColumn2ZDbuilder);
		List<SysZdxx> result = new ArrayList<>(columns.length);
		SysZdxx ziduan = null;
		int colSeq = 0;
		for(ColumnVO col:columns){
			ziduan = new SysZdxx();
			mapping.mapping(col, ziduan);
			ziduan.setName(columnPrefix+col.getColName());
			ziduan.setBid(tableId);
			ziduan.setXh(++colSeq);
			result.add(ziduan);
		}
		return result;
	}
	
	public static OnlineFcColumn[] convertBatch2OnlineFcColumn(String columnPrefix, List<SysZdxx> columnList){
		if(columnList==null || columnList.isEmpty()){
			return null;
		}
		BeanMapping mapping = new BeanMapping(ZD2OnlineFcColumnbuilder);
		int size = columnList.size();
		OnlineFcColumn[] result = new OnlineFcColumn[size];
		OnlineFcColumn col = null;
		for(int i=0;i<size;++i){
			col = new OnlineFcColumn();
			mapping.mapping(columnList.get(i), col);
			result[i] = col;
		}
		return result;
	}
	
	public static List<SysZdxx> convertBatch(String tableId, String columnPrefix, ColumnVO[] columns){
		BeanMapping mapping = new BeanMapping(vo2ZDbuilder);
		List<SysZdxx> result = new ArrayList<>(columns.length);
		SysZdxx ziduan = null;
		int colSeq = 0;
		for(ColumnVO col:columns){
			ziduan = new SysZdxx();
			mapping.mapping(col, ziduan);
			ziduan.setName(columnPrefix+col.getColName());
			ziduan.setBid(tableId);
			ziduan.setXh(++colSeq);
			result.add(ziduan);
		}
		return result;
	}
	
	
	
	/*public static void main(String[] args) {
		ColumnVO vo = new ColumnVO();
		vo.setCol_decpoint(0);
		vo.setCol_defval(null);
		vo.setCol_isnull("N");
		vo.setCol_ispk("Y");
		vo.setCol_length(10);
		vo.setcolMark("mk");
		vo.setcolName("name");
		vo.setCol_seq(1);
		vo.setcolType("colType");
		vo.setDeleted("deleted");
		vo.setInputlen(30);
		vo.setIsformdisp("isformdisp");
		vo.setSearchtype("searchtype");
		vo.setIssearch("issearch");
		vo.setVersion(12);
		System.out.println(vo.toString());
		SysZdxx zd = convert(vo);
		System.out.println(zd);
	}*/
}
