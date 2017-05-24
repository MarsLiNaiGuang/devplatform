package com.rs.devplatform.vo.cg;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.tamper.BeanMapping;
import com.alibaba.tamper.core.builder.BeanMappingBuilder;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.vo.TableVO;
import com.rs.devplatform.vo.onlinefc.OnlineFuncVO;
import com.rs.devplatform.vo.onlinefc.SubtablesVO;

public class Table2BxxConverter {

	private static BeanMappingBuilder bxx2VObuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(SysBxx.class,TableVO.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("id"), targetField("tableId")).convertor("convertor");
			fields(srcField("bm"), targetField("tablename")).convertor("convertor");
			fields(srcField("nr"), targetField("tabledescp")).convertor("convertor");
			fields(srcField("pklx"), targetField("pktype")).convertor("convertor");
			fields(srcField("lx"), targetField("tabletype")).convertor("convertor");
			fields(srcField("iscbox"), targetField("multiselect")).convertor("convertor");
			fields(srcField("ispage"), targetField("ispage")).convertor("convertor");
			fields(srcField("istree"), targetField("istree")).convertor("convertor");
			fields(srcField("child"), targetField("child")).convertor("convertor");
			fields(srcField("parent"), targetField("parent")).convertor("convertor");
			fields(srcField("gxlx"), targetField("relationType")).convertor("convertor");
			fields(srcField("tabxh"), targetField("relationSeq")).convertor("convertor");
			fields(srcField("version"), targetField("version")).convertor("convertor");
			fields(srcField("fcflag"), targetField("fcflag")).convertor("convertor");
		}
	};
	private static BeanMappingBuilder bxx2OnlineFuncVObuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(SysBxx.class,OnlineFuncVO.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("id"), targetField("tableId")).convertor("convertor");
			fields(srcField("bm"), targetField("tablename")).convertor("convertor");
			fields(srcField("nr"), targetField("tabledescp")).convertor("convertor");
			fields(srcField("pklx"), targetField("pktype")).convertor("convertor");
			fields(srcField("lx"), targetField("tabletype")).convertor("convertor");
			fields(srcField("iscbox"), targetField("multiselect")).convertor("convertor");
			fields(srcField("ispage"), targetField("ispage")).convertor("convertor");
			fields(srcField("istree"), targetField("istree")).convertor("convertor");
			fields(srcField("child"), targetField("child")).convertor("convertor");
			fields(srcField("parent"), targetField("parent")).convertor("convertor");
			fields(srcField("gxlx"), targetField("relationType")).convertor("convertor");
			fields(srcField("version"), targetField("version")).convertor("convertor");
			fields(srcField("isql"), targetField("sql")).convertor("convertor");
			fields(srcField("fcflag"), targetField("fcflag")).convertor("convertor");
		}
	};
	
	private static BeanMappingBuilder OnlineFuncVO2bxxbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(OnlineFuncVO.class,SysBxx.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("tableId"), targetField("id")).convertor("convertor");
			fields(srcField("tablename"), targetField("bm")).convertor("convertor");
			fields(srcField("tabledescp"), targetField("nr")).convertor("convertor");
			fields(srcField("pktype"), targetField("pklx")).convertor("convertor");
			fields(srcField("tabletype"), targetField("lx")).convertor("convertor");
			fields(srcField("multiselect"), targetField("iscbox")).convertor("convertor");
			fields(srcField("ispage"), targetField("ispage")).convertor("convertor");
			fields(srcField("istree"), targetField("istree")).convertor("convertor");
			fields(srcField("child"), targetField("child")).convertor("convertor");
			fields(srcField("parent"), targetField("parent")).convertor("convertor");
			fields(srcField("relationType"), targetField("gxlx")).convertor("convertor");
			fields(srcField("version"), targetField("version")).convertor("convertor");
			fields(srcField("sql"), targetField("isql")).convertor("convertor");
			fields(srcField("fcflag"), targetField("fcflag")).convertor("convertor");
		}
	};
	
	
	private static BeanMappingBuilder vo2Bxxbuilder = new BeanMappingBuilder(){
		@Override
		protected void configure() {
			mapping(TableVO.class, SysBxx.class).batch(false).reversable(true).keys("src", "target");
			fields(srcField("tableId"), targetField("id")).convertor("convertor");
			fields(srcField("tablename"), targetField("bm")).convertor("convertor");
			fields(srcField("tabledescp"), targetField("nr")).convertor("convertor");
			fields(srcField("pktype"), targetField("pklx")).convertor("convertor");
			fields(srcField("tabletype"), targetField("lx")).convertor("convertor");
			fields(srcField("multiselect"), targetField("iscbox")).convertor("convertor");
			fields(srcField("ispage"), targetField("ispage")).convertor("convertor");
			fields(srcField("istree"), targetField("istree")).convertor("convertor");
			fields(srcField("child"), targetField("child")).convertor("convertor");
			fields(srcField("parent"), targetField("parent")).convertor("convertor");
			fields(srcField("isdbsyn"), targetField("isdbsyn")).convertor("convertor");
			fields(srcField("relationType"), targetField("gxlx")).convertor("convertor");
			fields(srcField("relationSeq"), targetField("tabxh")).convertor("convertor");
			fields(srcField("version"), targetField("version")).convertor("convertor");
			fields(srcField("fcflag"), targetField("fcflag")).convertor("convertor");
		}
	};
	
	public static TableVO convert(SysBxx bxx){
		BeanMapping mapping = new BeanMapping(bxx2VObuilder);
		TableVO tableVO = new TableVO();
		mapping.mapping(bxx, tableVO);
		String subtablesStr = bxx.getZb();
		if(!StringUtils.isBlank(subtablesStr)){
			JSONArray json = (JSONArray)JSON.parse(subtablesStr);
			tableVO.setSubtables(JSON.toJavaObject(json, SubtablesVO[].class));
		}
		return tableVO;
	}
	
	public static SysBxx convert(TableVO vo){
		BeanMapping mapping = new BeanMapping(vo2Bxxbuilder);
		SysBxx bxx = new SysBxx();
		mapping.mapping(vo, bxx);
		bxx.setZb(vo.getSubtablesJSONString());
		return bxx;
	}
	
	public static SysBxx convertFromOnlineFuncVO(OnlineFuncVO funcVO){
		SysBxx bxx = new SysBxx();
		BeanMapping mapping = new BeanMapping(OnlineFuncVO2bxxbuilder);
		mapping.mapping(funcVO, bxx);
		bxx.setZb(funcVO.getSubtablesJSONString());
		return bxx;
	}
	
	public static OnlineFuncVO convert2OnlineFuncVO(SysBxx bxx){
		BeanMapping mapping = new BeanMapping(bxx2OnlineFuncVObuilder);
		OnlineFuncVO funcVO = new OnlineFuncVO();
		mapping.mapping(bxx, funcVO);
		String subtablesStr = bxx.getZb();
		if(!StringUtils.isBlank(subtablesStr)){
			JSONArray json = (JSONArray)JSON.parse(subtablesStr);
			funcVO.setSubtables(JSON.toJavaObject(json, SubtablesVO[].class));
		}
		return funcVO;
	}
	
}
