package ${basePackage}.persistent.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;



public interface ${beanName}Mapper {


	public List<Map<String,Object>> selectSql(Pagination page, @Param("sql") String sql);
	
	public List<Map<String,Object>> selectSql(@Param("sql") String sql);

	<#list subTableNames as name>	    
	    <#assign index=name?index_of('|')>	    
	    <#if index != -1>	    
	      <#assign subTableCol="${name?substring(0,index)}">
	      <#assign subSql="${name?substring(index+1)}">
	      <#assign sub_index=subTableCol?index_of(':')>
		  <#assign tName="${subTableCol?substring(0,sub_index)}">
		  <#assign underIndex=tName?index_of('_')>
		  <#assign tNamePrex="${tName?substring(0,underIndex)}">
		  <#assign tNameAppend="${tName?substring(underIndex + 1)}">
		  <#assign subeanVar="${tNamePrex + tNameAppend}">
		  <#assign subeanName="${subeanVar?cap_first}">	
	public List<Map<String,Object>> retrieve${subeanName}List(@Param("id") String id);
	    
	    </#if>			
	</#list>

	
	public int executSql(@Param("sql") String sql);
	
	public int logicDeleteSql(@Param("idList") List<String> IdList);
	
	<#list cusCodeList as codeNames>	
	  <#assign codeIndex=codeNames?index_of(':')>
	  <#assign codeName ="${codeNames?substring(0,codeIndex)}" >
	  <#assign codeNameBean ="${codeName?cap_first}">
	public int execute${codeNameBean}(@Param("parm1") String parm1, @Param("parm2") String parm2, @Param("parm3") String parm3);  
	  
	</#list>

}