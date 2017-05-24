/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
<#if subModule?? && subModule!="">
package ${basePackage}.service.${subModule}.impl;
<#else>
package ${basePackage}.service.impl;
</#if>

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
  <#assign index=tableName?index_of('_')>
  <#assign tableNamePrex="${tableName?substring(0,index)}">
  <#assign tableNameAppend="${tableName?substring(index+1)}">
  <#assign mapPrefix="${tableNamePrex + tableNameAppend?cap_first}">
  
  <#assign beanVar="${beanName[0..1]?lower_case}${beanName[2..]}">
<#if isNeedMapXML>
import com.rs.devplatform.persistent.mapper.${beanName}Mapper;
</#if> 


<#if subModule?? && subModule!="">
import ${basePackage}.persistent.${subModule}.${beanName};
import ${basePackage}.service.${subModule}.${beanName}Service;
<#else>
import ${basePackage}.persistent.${beanName};
import ${basePackage}.service.${beanName}Service;
</#if>
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsGeneralMapper;


@Service
public class ${beanName}ServiceImpl implements ${beanName}Service {

	@Autowired
	RsGeneralMapper generalMapper;
	<#if isNeedMapXML>
	@Autowired
	${beanName}Mapper ${beanVar}Mapper;
	</#if>
	<#assign beanVar="${beanName[0..1]?lower_case}${beanName[2..]}">	
	
	@Transactional
	public boolean add(${beanName} ${beanVar}){
	    ${beanVar}.setDeleted(Constants.DelInd.FALSE);
		generalMapper.insert(${beanVar});
		return true;
	}
	
	@Transactional
	public boolean update(${beanName} ${beanVar}){
		String id = ${beanVar}.getId();
		Integer version = ${beanVar}.getVersion();
		if(id==null || version==null){
			return false;
		}
		${beanName} entityWhere = new ${beanName}();
		entityWhere.setId(id);
		entityWhere.setDeleted(Constants.DelInd.FALSE);
		entityWhere.setVersion(version);
		${beanVar}.setVersion(version+1);
		${beanVar}.setDeleted(Constants.DelInd.FALSE);
		int effRows = generalMapper.update(${beanVar}, entityWhere);
		if(effRows==0){//update failed
			return false;
		}else{
			return true;
		}
	}
	
	@Transactional
	public boolean delete(${beanName} ${beanVar}){
		String id = ${beanVar}.getId();
		Integer version = ${beanVar}.getVersion();
		if(id==null || version==null){
			return false;
		}
		${beanName} entityWhere = new ${beanName}();
		entityWhere.setId(id);
		entityWhere.setDeleted(Constants.DelInd.FALSE);
		entityWhere.setVersion(version);
		${beanVar}.setDeleted(Constants.DelInd.TRUE);
		${beanVar}.setVersion(version + 1);
		int effRows = generalMapper.updateSelective(${beanVar}, entityWhere);
		if(effRows==0){//update failed
			return false;
		}else{
			return true;
		}
	}
	
	<#if isSql & isNeedMapXML>
	public List<Map<String,Object>> retrieveList(Pagination page, String sql) {
	   
	    if (page != null && page.getCurrent() != 0 && page.getSize() != 0){
			return ${beanVar}Mapper.selectSql(page, sql);
			
		}else {
			return ${beanVar}Mapper.selectSql(sql);
			
		      }	   
       }
       
       
    public Integer retrieveCount(String sql) {
       
           return ${beanVar}Mapper.selectSql(sql).size();
       
       }
    
    	<#list subTableNames as name>
	    
	    <#assign index=name?index_of('|')>
	    
	    <#assign subTableCol="${name?substring(0,index)}">
	    
	    <#assign sub_index=subTableCol?index_of(':')>
	    
		<#assign tName="${subTableCol?substring(0,sub_index)}">
		<#assign underIndex=tName?index_of('_')>
		<#assign tNamePrex="${tName?substring(0,underIndex)}">
		<#assign tNameAppend="${tName?substring(underIndex + 1)}">
		<#assign subeanVar="${tNamePrex + tNameAppend}">
		<#assign subeanName="${subeanVar?cap_first}">
			
	public List<Map<String,Object>> retrieve${subeanName}List(String id) {		
			return ${beanVar}Mapper.retrieve${subeanName}List(id);
				
		}	
						
	   </#list>
	   
	   @Transactional
	   public boolean deleteBatch(String[] ids){
		List<String> idList = Arrays.asList(ids);
		int effRow = ${beanVar}Mapper.logicDeleteSql(idList);
		if(effRow!=0){
			return true;
		}
		return false;
	}
	
	<#else>
	
	@Transactional
	public boolean deleteBatch(String[] ids){
		List<String> idList = Arrays.asList(ids);
		int effRow = generalMapper.deleteBatchIds(idList, ${beanName}.class);
		if(effRow!=0){
			return true;
		}
		return false;
	}	
	</#if>
	
	<#list cusCodeList as codeNames>	
	  <#assign codeIndex=codeNames?index_of(':')>
	  <#assign codeName ="${codeNames?substring(0,codeIndex)}" >
	  <#assign codeNameBean ="${codeName?cap_first}">
	@Transactional  
	public boolean execute${codeNameBean}(String parm1, String parm2, String parm3) {	
		int rows = zpgradeMapper.execute${codeNameBean}(parm1, parm2, parm3);
		if (rows > 0) {
			return true;
		}
		return false;
	}  
	</#list>

	
	
}
