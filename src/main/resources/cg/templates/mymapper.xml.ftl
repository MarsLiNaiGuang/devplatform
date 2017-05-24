<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper  
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
    <#assign index=tableName?index_of('_')>

    <#assign tableNamePrex="${tableName?substring(0,index)}">
	<#assign tableNameAppend="${tableName?substring(index+1)}">
	<#assign mapPrefix="${tableNamePrex + tableNameAppend?cap_first}">
  
<mapper namespace="${basePackage}.persistent.mapper.${beanName}Mapper">

	<update id="executSql">
		 ${r'${sql}'}
	</update>
	
	<update id="logicDeleteSql" parameterType="java.util.List">
		update ${tableName} set ${tableNameAppend}_deleted='T' where ${tableNameAppend}_id in
		<foreach collection="idList" index="index" item="id" open="("
                 separator="," close=")">
                 ${r'#{id}'}
        </foreach>
	</update>
	
	<select id="selectSql" resultType="java.util.HashMap" >
		select ${tableNameAppend}_id AS id, ${tableNameAppend}_version as version,  ${sqlInTable}
        WHERE  ${r'${sql}'}        
	</select>

	<#list subTableNames as name>	    
	    <#assign index=name?index_of('|')>
	    <#if index !=-1>
	      <#assign subTableCol="${name?substring(0,index)}">
	      <#assign subSql="${name?substring(index+1)}">	    
	      <#assign sub_index=subTableCol?index_of(':')>    
		  <#assign tName="${subTableCol?substring(0,sub_index)}">
		  <#assign underIndex=tName?index_of('_')>
		  <#assign tNamePrex="${tName?substring(0,underIndex)}">
		  <#assign tNameAppend="${tName?substring(underIndex + 1)}">
		  <#assign subeanVar="${tNamePrex + tNameAppend}">
		  <#assign subeanName="${subeanVar?cap_first}">		
		  <#assign subColumnVar="${subTableCol?substring(sub_index + 1)}">				
	  <select id="retrieve${subeanName}List" resultType="java.util.HashMap" >
		select ${tNameAppend}_id as id, ${tNameAppend}_version as version, ${subSql}
		where ${subColumnVar}=${r'#{id}'}
		and ${tNameAppend}_deleted='F'
	  </select>	    
	 </#if>    				
	</#list>
	
	<#list cusCodeList as codeNames>	
	  <#assign codeIndex=codeNames?index_of(':')>
	  <#assign codeName ="${codeNames?substring(0,codeIndex)}" >
	  <#assign codeNameBean ="${codeName?cap_first}">
	  <#assign innerSql = "${codeNames?substring(codeIndex + 1)}">
	<update id="execute${codeNameBean}">	
         ${innerSql} 
	</update>  
	</#list>

	
	
</mapper>
