/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
<#if subModule?? && subModule!="">
package ${basePackage}.controller.${subModule};
<#else>
package ${basePackage}.controller;
</#if>

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;

<#if subModule?? && subModule!="">
import ${basePackage}.persistent.${subModule}.${beanName};
import ${basePackage}.vo.${subModule}.${beanName}VO;
import ${basePackage}.service.${subModule}.${beanName}Service;
<#else>
import ${basePackage}.persistent.${beanName};
import ${basePackage}.vo.${beanName}VO;
import ${basePackage}.service.${beanName}Service;
</#if>
import com.rs.devplatform.conf.framework.RsEntityWrapper;
import com.rs.devplatform.controller.base.BaseController;

<#assign beanVar="${beanName[0..1]?lower_case}${beanName[2..]}">	
@Controller
<#if subModule?? && subModule!="">
@RequestMapping("/${subModule}/${beanVar}")
<#else>
@RequestMapping("/${beanVar}")
</#if>
public class ${beanName}Controller extends BaseController{

	@Autowired
	${beanName}Service ${beanVar}Service;
	
	@GetMapping
	public String index(){
	<#if subModule?? && subModule!="">
		return "${subModule}/${beanVar}";
	<#else>
		 return "${beanVar}";
	</#if>
	}
	
	/**
	<#if subModule?? && subModule!="">
	 * @api {POST} /${subModule}/${beanVar}/list 获取${tableComments}列表
	 * @apiGroup ${subModule}/${beanVar}
	<#else>
	 * @api {POST} /${beanVar}/list 获取${tableComments}列表
	 * @apiGroup ${beanVar}
	</#if>
	 * @apiParamExample {json} Request-Example: 
	 * 		{"current":1, "size":10, "orderBy":"name", "asc":"true"}
	 		{"name":"123", "orderby":"name", "comments":"不分页,current为null"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 <#list entityFields as field>
		<#if field.pk || field.gridData>
	 * @apiSuccess {${field.typeSimple}} rows.${field.beanPropertyName} ${field.formPropertyName}	
		</#if>
	</#list>
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * 	{"total",25, "current":1, "size":10, "pages":3, "rows":[
	 		{
	 <#list entityFields as field>
		<#if field.pk || field.gridData>
			"${field.beanPropertyName}" : "${field.formPropertyName}",	
		</#if>
	</#list>	
	 		}
	 	]}
	 * 	{"rows":[
	 		{
	 <#list entityFields as field>
		<#if field.pk || field.gridData>
			"${field.beanPropertyName}" : "${field.formPropertyName}",	
		</#if>
	</#list>	
			}
	 	]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestBody JSONObject parm) {
		${beanName}VO ${beanVar}VO = parm.toJavaObject(${beanName}VO.class);
		Integer current = ${beanVar}VO.getCurrent();
		if(current!=null){
			RsEntityWrapper<${beanName}VO> wrapper = new RsEntityWrapper<${beanName}VO>(${beanVar}VO);
			if(!StringUtils.isEmpty(${beanVar}VO.getOrderBy())){
				if(!StringUtils.isEmpty(${beanVar}VO.getAsc()) || ORDER_BY_ASC.equals(${beanVar}VO.getAsc())){
					wrapper.orderBy(${beanVar}VO.getOrderBy());
				}else{
					wrapper.orderBy(${beanVar}VO.getOrderBy(), false);
				}
			}//使用like，请参考RsEntityWrapper的说明
			int size = ${beanVar}VO.getSize()==null?DEFAULT_PAGE_SIZE:${beanVar}VO.getSize();
			return getSuccessResult(generalMapper.selectByPage(new Page<${beanName}VO>(current, size), wrapper));
		}else{
			return getSuccessResult(generalMapper.selectListVO(${beanVar}VO));
		}
	}
	
	/**
	 	<#if subModule?? && subModule!="">
	 *  @api {POST} /${subModule}/${beanVar} 增加${tableComments}
	 *  @apiGroup ${subModule}/${beanVar}
	 	<#else>
	 *  @api {POST} /${beanVar} 增加${tableComments}
	 *  @apiGroup ${beanVar}
	 	</#if>
	 * 
	<#list entityFields as field>
		<#if !field.pk && field.formData>
	 * @apiParam {${field.typeSimple}} ${field.beanPropertyName} ${field.formPropertyName}	
		</#if>
	</#list>
	 * @apiParamExample {json} Request-Example: 
	 
	 * 	{
	 <#list entityFields as field>
		<#if !field.pk && field.formData>
	 * 		"${field.beanPropertyName}":"${field.formPropertyName}",	
		</#if>
	</#list>
	 	 }
	 * 
	 * @apiSuccess {String} id 返回id
	 * @apiSuccess {Integer} version 返回version
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{"id":"0f077145cc894f7990387c9458091e1b", "version":1}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 */
	@PostMapping
	@ResponseBody
	public Object add(@RequestBody JSONObject parm){
		${beanName}VO ${beanVar} = JSONObject.toJavaObject(parm, ${beanName}VO.class);
		if(validateData(${beanVar})) {
	<#assign hasUnique=false>	
	<#list entityFields as field>
		<#if !field.pk && field.unique>
		<#assign hasUnique=true>	
		</#if>
	</#list>
			<#if hasUnique>
			${beanName} ${beanVar}Parm = new ${beanName}();
	<#list entityFields as field>
		<#if !field.pk && field.unique>
			${beanVar}Parm.set${field.beanPropertyName?cap_first}(${beanVar}.get${field.beanPropertyName?cap_first}());
		</#if>
	</#list>
			if(isDuplicateBeforeCreate(${beanVar}Parm)){
				return getDuplicateResult();
			}
			</#if>
			${beanVar}Service.add(${beanVar});
			JSONObject result =  new JSONObject();
			result.put(ID, ${beanVar}.getId());
			result.put(VERSION, ${beanVar}.getVersion());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 	<#if subModule?? && subModule!="">
	 * @api {GET} /${subModule}/${beanVar}/{id} 获取${tableComments}详细
	 * @apiGroup ${subModule}/${beanVar}
	 	<#else>
	 * @api {GET} /${beanVar}/{id} 获取${tableComments}详细
	 * @apiGroup ${beanVar}
	 	</#if>
	 *	
	 * @apiParam {String} id ID,是路径上的参数id	
	 * @apiParamExample {json} Request-Example: 
	 *  GET /${subModule}/${beanVar}/0f077145cc894f7990387c9458091e1b
	 * 
	 <#list entityFields as field>
	 * @apiSuccess {${field.typeSimple}} ${field.beanPropertyName} ${field.formPropertyName}
	</#list>
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 *  {
	 <#list entityFields as field>
	 * 		"${field.beanPropertyName}":"${field.formPropertyName}",	
	</#list>
	 	 }
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Object get(@PathVariable("id") String id){
		${beanName} ${beanVar} = generalMapper.selectById(id, ${beanName}.class);
		if(${beanVar}!=null) {
			${beanName}VO ${beanVar}VO = new ${beanName}VO();
			BeanUtils.copyProperties(${beanVar}, ${beanVar}VO);
			return getSuccessResult(${beanVar}VO);
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 	<#if subModule?? && subModule!="">
	 * @api {PUT} /${subModule}/${beanVar}/{id} 更新${tableComments}
	 * @apiGroup ${subModule}/${beanVar}
	 	<#else>
	 * @api {PUT} /${beanVar}/{id} 更新${tableComments}
	 * @apiGroup ${beanVar}
	 	</#if>
	 *	
	 * @apiParam {String} id ID,是路径上的参数id	
	 * @apiParam {Integer} version Version
	<#list entityFields as field>
		<#if !field.pk && field.formData>
	 * @apiParam {${field.typeSimple}} ${field.beanPropertyName} ${field.formPropertyName}	
		</#if>
	</#list>
	 * @apiParamExample {json} Request-Example: 
	 *  PUT /${subModule}/${beanVar}/0f077145cc894f7990387c9458091e1b
	 * 	{
	 		"version":1,
	 <#list entityFields as field>
		<#if !field.pk && field.formData>
	 * 		"${field.beanPropertyName}":"${field.formPropertyName}",	
		</#if>
	</#list>
	 	 }
	 * 
	 * @apiSuccess {String} id 返回id
	 * @apiSuccess {Integer} version 返回version
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{"id":"0f077145cc894f7990387c9458091e1b", "version":2}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	@ResponseBody
	public Object update(@PathVariable("id") String id,@RequestBody JSONObject parm){
		${beanName}VO ${beanVar} = JSONObject.toJavaObject(parm, ${beanName}VO.class);
		${beanVar}.setId(id);
		if(validateData(${beanVar})) {
	<#assign hasUnique=false>	
	<#list entityFields as field>
		<#if !field.pk && field.unique>
		<#assign hasUnique=true>	
		</#if>
	</#list>
			<#if hasUnique>
			${beanName} ${beanVar}Parm = new ${beanName}();
			${beanVar}Parm.setId(${beanVar}.getId());
			${beanVar}Parm.setVersion(${beanVar}.getVersion());
	<#list entityFields as field>
		<#if !field.pk && field.unique>
			${beanVar}Parm.set${field.beanPropertyName?cap_first}(${beanVar}.get${field.beanPropertyName?cap_first}());
		</#if>
	</#list>
			if(isDuplicateBeforeUpdate(${beanVar}Parm)){
				return getDuplicateResult();
			}
			</#if>
			if(${beanVar}Service.update(${beanVar})){
				JSONObject result =  new JSONObject();
				result.put(ID, ${beanVar}.getId());
				result.put(VERSION, ${beanVar}.getVersion());
				return getSuccessResult(result);
			}else{
				return getModifiedResult();
			}
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 	<#if subModule?? && subModule!="">
	 * @api {DELETE} /${subModule}/${beanVar} 删除${tableComments}
	 * @apiGroup ${subModule}/${beanVar}
	 	<#else>
	 * @api {PUT} /${beanVar} 删除${tableComments}
	 * @apiGroup ${beanVar}
	 	</#if>
	 *	
	 * @apiParam {StringArray} ids IDs,id数组	
	 * @apiParamExample {json} Request-Example: 
	 *  DELETE /${subModule}/${beanVar}
	 * 	{
		 	ids : [
		 		"id1", "id3", "id8"
		 	]
	 	}
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 */
	@DeleteMapping
	@ResponseBody
	public Object delete(@RequestBody JSONObject parm){
		JSONArray ids = parm.getJSONArray("ids");
		if(ids==null || ids.isEmpty()) {
			return getInvalidParamResult();
		}
		String[] idArray = new String[ids.size()];
		for(int i=0;i<ids.size();++i){
			idArray[i] = ids.getString(i);
		}
		if(${beanVar}Service.deleteBatch(idArray)){
			return getSuccessResult();
		}else{
			return getModifiedResult();
		}
	}
}
