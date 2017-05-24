/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
<#if subModule?? && subModule!="">
package ${basePackage}.persistent.${subModule};
<#else>
package ${basePackage}.persistent;
</#if>

<#list imports as impo> 
import ${impo};
</#list>
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.rs.framework.common.entity.base.CUBaseTO;

/**
 *
 * ${tableComments}
 *
 *	@author ${author}
 */
@TableName("${tableName}")
public class ${beanName} extends CUBaseTO implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
	
<#list entityFields as field>
	<#if field.pk>
	/** PK */
	@TableId(value = "${field.columnName}", type = IdType.UUID)
	</#if>
	<#if !field.pk>
	/** ${field.formPropertyName} */
	@TableField(value = "${field.columnName}")
	</#if>
	<#if field.hasDefaultVal>
	private ${field.typeSimple} ${field.beanPropertyName} = ${field.defaultVal};
	<#else>
	private ${field.typeSimple} ${field.beanPropertyName};
	</#if>
</#list>

	<#list entityFields as field>
	public ${field.typeSimple} get${field.beanPropertyName?cap_first} () {
		return this.${field.beanPropertyName};
	}

	public void set${field.beanPropertyName?cap_first} (${field.typeSimple} ${field.beanPropertyName}) {
		<#if field.typeSimple=="String">
		this.${field.beanPropertyName} = ${field.beanPropertyName}==null?null:${field.beanPropertyName}.trim();
		<#else>
		this.${field.beanPropertyName} = ${field.beanPropertyName};
		</#if>
	}
	</#list>
	
	public String asSqlString(){
		StringBuilder sb = new StringBuilder();
		<#list entityFields as field>
		sb.append("${field.columnName}").append(" as ").append("${field.beanPropertyName}").append(",");
		</#list>
		return sb.toString();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		<#list entityFields as field>
		sb.append("${field.beanPropertyName}").append("=").append(${field.beanPropertyName}).append(",");
		</#list>
		return sb.toString();
	}
}
