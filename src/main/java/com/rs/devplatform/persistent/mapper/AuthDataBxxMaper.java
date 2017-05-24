package com.rs.devplatform.persistent.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AuthDataBxxMaper {
	
	@Select(
			" select zdxx_id as id, zdxx_nr as columnDescp,zdxx_name as columnName,ZDXX_LX as columnType, "
			+" ZDXX_ISQUERY as isquery,ZDXX_ISSHOW as isshow,ZDXX_ISSHOWLB as isshowlb "
					+" from sys_bxx bxx, sys_zdxx zdxx "
			+" where bxx.BXX_ID=zdxx.ZDXX_BID and ZDXX_ISSHOW='Y' "
					+" and bxx.BXX_BM=#{tableName} "
			)
	public List<Map<String,Object>> getColumnsFromBxxByTableName(@Param("tableName") String tableName);
	
	/**
	 * 根据表名，关联BXX，去button表获取自定义按钮
	 * 
	 * @param tableName
	 * @return
	 */
	@Select(
			" SELECT BTN.CUSTBT_CODE AS BTN_CODE, CUSTBT_ICON AS BTN_ICON, CUSTBT_NAME AS BTN_NAME, CUSTBT_YS AS BTN_STYLE, CUSTBT_ZT AS BTN_STATUS "
			+" from sys_custbt btn "
			+" join sys_bxx bxx on bxx.BXX_ID=btn.CUSTBT_BDID and bxx.bxx_bm=#{tableName} "
			+" order by CUSTBT_XH"
			)
	public List<Map<String,Object>> getCustButtonsFromDBByTableName(@Param("tableName") String tableName);
	
}
