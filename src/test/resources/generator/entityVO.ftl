/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
<#if subModule?? && subModule!="">
package ${basePackage}.vo.${subModule};
<#else>
package ${basePackage}.vo;
</#if>

<#list imports as impo> 
import ${impo};
</#list>
import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
<#if subModule?? && subModule!="">
import ${basePackage}.persistent.${subModule}.${beanName};
<#else>
import ${basePackage}.persistent.${beanName};
</#if>
import com.rs.devplatform.vo.page.PageVO;

/**
 * Entity VO:
 * 支持fastjson&jackson两种方式序列化对象
 *	@author RSDevPlatform
 */
public class ${beanName}VO extends ${beanName} implements PageVO {

	private static final long serialVersionUID = 1L;
	/*
	 * for pagination
	*/
	private Integer current;
	private Integer size;
	/*
	 * order by
	*/
	private String orderBy;
	private String asc;
	private String orderByGroup;//orderByGroup = user_name asc, user_age desc
	
	<#list entityFields as field>
	<#if field.formData && !field.nullable && field.beanPropertyName!="version" && field.beanPropertyName!="id">
	@NotEmpty
	</#if>
	<#if field.formData || field.gridData>
	@JSONField(name="${field.beanPropertyName}")
	@JsonProperty(value="${field.beanPropertyName}")
	<#elseif field.beanPropertyName="id" || field.beanPropertyName=="version">
	
	<#else>
	@JSONField(serialize=false)
	@JsonProperty(access=Access.WRITE_ONLY)
	</#if>
	public ${field.typeSimple} get${field.beanPropertyName?cap_first} () {
		return super.get${field.beanPropertyName?cap_first} ();
	}
	</#list>

	public Integer getCurrent(){
		return current;
	}
	public void setCurrent(Integer current){
		this.current = current;
	}
	public Integer getSize(){
		return size;
	}
	public void setSize(Integer size){
		this.size = size;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getAsc() {
		return asc;
	}
	public void setAsc(String asc) {
		this.asc = asc;
	}
	public String getOrderByGroup() {
		return orderByGroup;
	}
	public void setOrderByGroup(String orderByGroup) {
		this.orderByGroup = orderByGroup;
	}
}
