/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
<#if subModule?? && subModule!="">
package ${basePackage}.service.${subModule};
<#else>
package ${basePackage}.service;
</#if>

<#if subModule?? && subModule!="">
import ${basePackage}.persistent.${subModule}.${beanName};
<#else>
import ${basePackage}.persistent.${beanName};
</#if>
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import java.util.List;


public interface ${beanName}Service {

	<#assign beanVar="${beanName[0..1]?lower_case}${beanName[2..]}">	
	public boolean add(${beanName} ${beanVar});
	
	public boolean update(${beanName} ${beanVar});
	
	public boolean delete(${beanName} ${beanVar});
		
	<#if isSql>
	public List<Map<String,Object>> retrieveList(Pagination page, String sql);
    
    public Integer retrieveCount(String sql);
    
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
			
	public List<Map<String,Object>> retrieve${subeanName}List(String id);							
	   </#list>	
	</#if>
	
	<#list cusCodeList as codeNames>	
	  <#assign codeIndex=codeNames?index_of(':')>
	  <#assign codeName ="${codeNames?substring(0,codeIndex)}" >
	  <#assign codeNameBean ="${codeName?cap_first}">
	public boolean execute${codeNameBean}(String parm1, String parm2, String parm3);  

	</#list>

	public boolean deleteBatch(String[] ids);
}
