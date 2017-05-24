package com.rs.devplatform.persistent.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;



public interface BmpzMapper {


	public List<Map<String,Object>> selectSql(Pagination page, @Param("sql") String sql);
	
	public List<Map<String,Object>> selectSql(@Param("sql") String sql);
	
	

	
	public int executSql(@Param("sql") String sql);
	
	public int logicDeleteSql(@Param("idList") List<String> IdList);

}