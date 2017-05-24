package com.rs.devplatform.persistent.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysZdxx;

public interface CGMapper {
	
	/**
	 * 只是为取zdxx_id
	 * 
	 * @param tableId
	 * @param delInd
	 * @return
	 */
	@Select(" select zdxx_id as id from sys_zdxx where zdxx_bid=#{tableId} and ZDXX_DELETED=#{delInd} ")
	public List<SysZdxx> getColumns4Table(@Param("tableId") String tableId, @Param("delInd") String delInd);

	@Select(" select distinct bxx_id as id, bxx_bm as bm from sys_zdxx, sys_bxx "
		  + " where zdxx_bid = bxx_id and zdxx_zb=#{masterId} and ZDXX_DELETED=#{delInd} ")
	public List<SysBxx> getDetailTableList(@Param("masterId") String masterId, @Param("delInd") String delInd);
	
	@SuppressWarnings("rawtypes")
	@Select(" select * from test")
	@ResultType(HashMap.class)
	public List<Map> testSwitchDB();
	
	/**
	 * 
	 * @param sql 
	 * 				"insert into tbl_sample(sample_id,sample_name) values('id_aaaa','name_aaa')"
	 * 				"update tbl_sample set sample_name='aaa2'"
	 * 
	 * @return 影响到记录条数
	 */
	public int executSql(@Param("sql") String sql);
	
	public List<Map<String,Object>> selectSql(Pagination page, @Param("sql") String sql);
	
	public List<Map<String,Object>> selectSql(@Param("sql") String sql);
	
	@Delete(" drop table #{tableName}")
	public void dropTable(@Param("tableName") String tableName);
	
}
