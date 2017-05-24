/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
<#if subModule?? && subModule!="">
package ${basePackage}.controller.${subModule};
<#else>
package ${basePackage}.controller;
</#if>

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
<#if subModule?? && subModule!="">
import ${basePackage}.persistent.${subModule}.${beanName};
import ${basePackage}.vo.${subModule}.${beanName}VO;
import ${basePackage}.service.${subModule}.${beanName}Service;
<#else>
import ${basePackage}.persistent.${beanName};
import ${basePackage}.vo.${beanName}VO;
import ${basePackage}.service.${beanName}Service;
</#if>
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.validator.ValidateError;

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
	<#list entityFields as field>
		<#if field.query>
			<#if field.fuzzyQuery>
	 * @apiParam ${r"{"}${field.typeSimple}${r"}"} ${field.beanPropertyName} ${field.formPropertyName},模糊查询
			<#elseif field.rangeQuery>
				<#if field.typeSimple=="Date">
	 * @apiParam ${r"{"}${field.typeSimple}${r"}"} ${field.beanPropertyName}From ${field.formPropertyName}范围开始,1488344117269, 13位时间戳(前端补齐13位)
	 * @apiParam ${r"{"}${field.typeSimple}${r"}"} ${field.beanPropertyName}To ${field.formPropertyName}范围结束,1488344127269, 13位时间戳(前端补齐13位)
				<#else>
	 * @apiParam ${r"{"}${field.typeSimple}${r"}"} ${field.beanPropertyName}From ${field.formPropertyName}范围开始
	 * @apiParam ${r"{"}${field.typeSimple}${r"}"} ${field.beanPropertyName}To ${field.formPropertyName}范围结束
				</#if>
			<#else>
				<#if field.typeSimple=="Date">
	 * @apiParam ${r"{"}${field.typeSimple}${r"}"} ${field.beanPropertyName} ${field.formPropertyName},1488344117269,13位时间戳
	 			<#else>
	 * @apiParam ${r"{"}${field.typeSimple}${r"}"} ${field.beanPropertyName} ${field.formPropertyName}
	 			</#if>
			</#if>
		</#if>
	</#list>
	 * @apiParamExample {json} Request-Example: 
	 *  {
	 *    "current":1, "size":10, "orderBy":"name", "asc":"true",
	 		<#list entityFields as field>
	 		<#if field.query>
				<#if field.fuzzyQuery>
	 *    "${field.beanPropertyName}":"abc",
				<#elseif field.rangeQuery>
					<#if field.typeSimple=="Date">
	 *    "${field.beanPropertyName}From": 1488344117269, "${field.beanPropertyName}To": 1488344127269,
					<#else>
	 *    "${field.beanPropertyName}From": 18,	"${field.beanPropertyName}To": 28,
					</#if>
				<#else>
					<#if field.typeSimple=="String">
	 *    "${field.beanPropertyName}":"abc",
	 				<#elseif field.typeSimple=="Date">
	 *    "${field.beanPropertyName}":1488344117269,
	 				<#else>
	 *    "${field.beanPropertyName}":123,	 				
	 				</#if>
				</#if>
			</#if>
	 		</#list>
	 *  }
	 *  {"name":"123", "orderby":"name", "comments":"不分页,current为null"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 <#list entityFields as field>
		<#if field.pk || field.gridData>
			<#if field.typeSimple=="Date">
	 * @apiSuccess {${field.typeSimple}} rows.${field.beanPropertyName} ${field.formPropertyName},1488344127269
			<#else>
	 * @apiSuccess {${field.typeSimple}} rows.${field.beanPropertyName} ${field.formPropertyName}	
			</#if>
		</#if>
	</#list>
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {"total",25, "current":1, "size":10, "pages":3, "rows":[
	 *    {
	 <#list entityFields as field>
		<#if field.pk || field.gridData>
	 *      "${field.beanPropertyName}" : "${field.formPropertyName}",	
		</#if>
	</#list>	
	 *    }
	 *  ]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestBody JSONObject parm) {
		${beanName}VO ${beanVar}VO = parm.toJavaObject(${beanName}VO.class);
		RsEntityWrapper<${beanName}VO> ew = new RsEntityWrapper<>(${beanVar}VO);
	<#list entityFields as field>
		<#if field.query>
			<#if field.fuzzyQuery>
		String ${field.beanPropertyName} = ${beanVar}VO.get${field.beanPropertyName?cap_first}();
		${beanVar}VO.set${field.beanPropertyName?cap_first}(null);
		if(StringUtils.isNotBlank(${field.beanPropertyName})){
			ew.like("${field.columnName}", ${field.beanPropertyName});
		}
			<#elseif field.rangeQuery>
			<#assign propFrom=field.beanPropertyName+"From"/>
			<#assign propTo=field.beanPropertyName+"To"/>
			<#assign rangeSql=field.beanPropertyName+"RangeSql"/>
			<#assign rangeParams=field.beanPropertyName+"RangeParams"/>
		${field.typeSimple} ${propFrom} = ${beanVar}VO.get${field.beanPropertyName?cap_first}From();
		${field.typeSimple} ${propTo} = ${beanVar}VO.get${field.beanPropertyName?cap_first}To();
		StringBuilder ${rangeSql} = new StringBuilder();
		List<Object> ${rangeParams} = new ArrayList<>();
		if(${propFrom}!=null){
			${rangeSql}.append("${field.columnName}>=${r"#{"}${propFrom}${r"}"} and ");
			${rangeParams}.add(${propFrom});
		}
				<#assign propFrom=field.beanPropertyName+"To"/>
		if(${propTo}!=null){
			${rangeSql}.append("${field.columnName}<${r"#{"}${propTo}${r"}"} and ");
			${rangeParams}.add(${propTo});
		}
		if(${rangeSql}.length()!=0){
			${rangeSql}.delete(${rangeSql}.length()-5, ${rangeSql}.length());
			ew.andNew(${rangeSql}.toString(), ${rangeParams}.toArray());
		}
			<#else>
			</#if>
		</#if>
	</#list>	
		return getSuccessResult(generalMapper.selectByPageOrder(ew));
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
			<#if field.typeSimple=="Date">
	 * @apiParam ${r"{"}${field.typeSimple}${r"}"} ${field.beanPropertyName} ${field.formPropertyName},1488344117269,13位时间戳
	 		<#else>
	 * @apiParam ${r"{"}${field.typeSimple}${r"}"} ${field.beanPropertyName} ${field.formPropertyName}
	 		</#if>
		</#if>
	</#list>
	 * @apiParamExample {json} Request-Example: 
	 *  {
	 <#list entityFields as field>
		<#if !field.pk && field.formData>
	 		<#if field.typeSimple=="String">
	 *    "${field.beanPropertyName}":"abc",
	 		<#elseif field.typeSimple=="Date">
	 *    "${field.beanPropertyName}":1488344117269,
	 		<#else>
	 *    "${field.beanPropertyName}":123,	 				
	 		</#if>
		</#if>
	</#list>
	 *  }
	 * 
	 * @apiSuccess {String} id 返回id
	 * @apiSuccess {Integer} version 返回version
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {"id":"0f077145cc894f7990387c9458091e1b", "version":1}
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 *  {
	 *    "verrors":[
	 <#list entityFields as field>
		<#if !field.pk && field.formData>
	 		<#if !field.nullable && !field.hasDefaultVal>
	 *      {"errCode":"${field.beanPropertyName}","errorMsg":"${field.formPropertyName}不能为空"},
	 		</#if>
		</#if>
	</#list>
	 *    ]
	 *  }
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
	 	<#if field.beanPropertyName=="id" || field.beanPropertyName=="version" 
	 		|| field.formData || field.gridData>
	 		<#if field.typeSimple=="Date">
	 * @apiSuccess {${field.typeSimple}} ${field.beanPropertyName} ${field.formPropertyName},1488344117269,13位时间戳
	 		<#else>
	 * @apiSuccess {${field.typeSimple}} ${field.beanPropertyName} ${field.formPropertyName}
	 		</#if>
	 	</#if>
	</#list>
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {
	 <#list entityFields as field>
	 	<#if field.beanPropertyName=="id" || field.beanPropertyName=="version" 
	 		|| field.formData || field.gridData>
	 *    "${field.beanPropertyName}":"${field.formPropertyName}",	
	 	</#if>
	</#list>
	 *  }
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 * {"verrors":[{"errCode":"","errorMsg":"无效的记录"}]}
	 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Object get(@PathVariable("id") String id){
		${beanName}VO ${beanVar}VO = generalMapper.selectById(id, ${beanName}VO.class);
		if(${beanVar}!=null) {
			return getSuccessResult(${beanVar}VO);
		}else{
			addValidationError(new ValidateError("","无效的记录"));
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
			<#if field.typeSimple="Date">
	 * @apiParam {${field.typeSimple}} ${field.beanPropertyName} ${field.formPropertyName}, 13位时间戳（前端补齐13位）
			<#else>
	 * @apiParam {${field.typeSimple}} ${field.beanPropertyName} ${field.formPropertyName}	
			</#if>
		</#if>
	</#list>
	 * @apiParamExample {json} Request-Example: 
	 *  PUT /${subModule}/${beanVar}/0f077145cc894f7990387c9458091e1b
	 * 	{
	 *    "version":1,
	 <#list entityFields as field>
		<#if !field.pk && field.formData>
			<#if field.typeSimple="Date">
	 *    "${field.beanPropertyName}":1488344117269,	
			<#else>
	 *    "${field.beanPropertyName}":"${field.formPropertyName}",	
			</#if>
		</#if>
	</#list>
	 *  }
	 * 
	 * @apiSuccess {String} id 返回id
	 * @apiSuccess {Integer} version 返回version
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {"id":"0f077145cc894f7990387c9458091e1b", "version":2}
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 *  {
	 *    "verrors":[
	 <#list entityFields as field>
		<#if !field.pk && field.formData>
	 		<#if !field.nullable && !field.hasDefaultVal>
	 *      {"errCode":"${field.beanPropertyName}","errorMsg":"${field.formPropertyName}不能为空"},
	 		</#if>
		</#if>
	</#list>
	 *    ]
	 *  }
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 409 Conflict
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
