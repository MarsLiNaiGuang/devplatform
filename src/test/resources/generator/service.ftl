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

public interface ${beanName}Service {

	<#assign beanVar="${beanName[0..1]?lower_case}${beanName[2..]}">	
	public boolean add(${beanName} ${beanVar});
	
	public boolean update(${beanName} ${beanVar});
	
	public boolean delete(${beanName} ${beanVar});
	
	public boolean deleteBatch(String[] ids);
}
