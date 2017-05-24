/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
<#if subModule?? && subModule!="">
package ${basePackage}.controller.${subModule};
<#else>
package ${basePackage}.controller;
</#if>
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rs.devplatform.service.cg.CGService;
import com.rs.devplatform.vo.common.RsCommonResponse;
import com.rs.devplatform.common.BuzException;
import com.rs.devplatform.persistent.SysCustbt;
import com.rs.devplatform.persistent.SysZqjs;
import com.rs.devplatform.persistent.SysZqsql;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.common.CGConstants;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
<#if subModule?? && subModule!="">
import ${basePackage}.persistent.${subModule}.${beanName};
import ${basePackage}.vo.${subModule}.${beanName}VO;
import ${basePackage}.service.${subModule}.${beanName}Service;
<#list subTableNames as name>
	
	    <#assign index=name?index_of(':')>
		<#assign tName="${name?substring(0,index)}">

		<#assign underIndex=tName?index_of('_')>
		<#assign tNamePrex="${tName?substring(0,underIndex)}">
		<#assign tNameAppend="${tName?substring(underIndex + 1)}">
		<#assign subeanVar="${tNamePrex + tNameAppend}">
		<#assign subeanName="${subeanVar?cap_first}">
import ${basePackage}.persistent.${subModule}.${subeanName};		
import ${basePackage}.service.${subModule}.${subeanName}Service;
import ${basePackage}.vo.${subModule}.${subeanName}VO;

</#list>

<#else>
import ${basePackage}.persistent.${beanName};
import ${basePackage}.vo.${beanName}VO;
import ${basePackage}.service.${beanName}Service;
<#list subTableNames as name>
	
	    <#assign index=name?index_of(':')>
		<#assign tName="${name?substring(0,index)}">

		<#assign underIndex=tName?index_of('_')>
		<#assign tNamePrex="${tName?substring(0,underIndex)}">
		<#assign tNameAppend="${tName?substring(underIndex + 1)}">
		<#assign subeanVar="${tNamePrex + tNameAppend}">
		<#assign subeanName="${subeanVar?cap_first}">
		
import ${basePackage}.persistent.${subeanName};
import ${basePackage}.vo.${subeanName}VO;
import ${basePackage}.service.${subeanName}Service;

</#list>
</#if>
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.validator.ValidateError;
import com.rs.framework.common.Constants;

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
	
	<#list subTableNames as name>
	
	    <#assign index=name?index_of(':')>
		<#assign tName="${name?substring(0,index)}">

		<#assign underIndex=tName?index_of('_')>
		<#assign tNamePrex="${tName?substring(0,underIndex)}">
		<#assign tNameAppend="${tName?substring(underIndex + 1)}">
		<#assign subeanVar="${tNamePrex + tNameAppend}">
		<#assign subeanName="${subeanVar?cap_first}">
		
		@Autowired
	    ${subeanName}Service ${subeanVar}Service;

	</#list>
	
	<#assign in_dex=tableName?index_of('_')>
	<#assign tableNameAppend=tableName?substring(in_dex+1)>
	<#assign mainTableAppend="${tableNameAppend?cap_first}">
	

	@Autowired
	CGService cgService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ModelAndView index() {
	   ModelAndView mav=new ModelAndView();
	<#if subModule?? && subModule!="">
		mav.setViewName("${subModule}/${beanVar}");
	<#else>
		mav.setViewName("${beanVar}");
	</#if>
	   return mav;
	}
	
	
	@RequestMapping("/{id}")
	@ResponseBody
	public Object getMasterInfoByTableId(@PathVariable("id") String tableId)
	{
		JSONObject result = null;
		
		try {
			<#if isSql>
			  result = cgService.getFormMasterDetail(tableId, Constants.TO_LOWCASE_PART);
			<#else>
			  result = cgService.getFormMasterDetail(tableId, Constants.TO_LOWCASE_FULL);
			</#if>			
			
		}catch(BuzException e){
			
			e.printStackTrace();			
		}
		
		return getSuccessResult(result);
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
	@RequestMapping("/query")
	@ResponseBody
	public Object list(@RequestBody JSONObject parm) {
	  <#if isSql>
	    StringBuilder sql = new StringBuilder();
	   sql.append(" 1=1 ");
	       <#list entityFields as field>
		     <#if field.query>
			  <#if field.fuzzyQuery>
		String ${field.beanPropertyName} = parm.getString("${field.columnName}");
		
		if(StringUtils.isNotBlank(${field.beanPropertyName})){
			sql.append(" and ${field.columnName} like '%" + ${field.beanPropertyName} +"%'");
		}
			<#elseif field.rangeQuery>
			<#assign propFrom=field.beanPropertyName+"From"/>
			<#assign propTo=field.beanPropertyName+"To"/>
		${field.typeSimple} ${propFrom} = parm.getIntValue("(B)${field.columnName}");
		${field.typeSimple} ${propTo} = parm.getIntValue("(E)${field.columnName}");

		if(${propFrom}!=null && ${propFrom} >= 0){
		    sql.append(" and ${field.columnName}>=" + ${propFrom});
		}

		if(${propTo}!=null && ${propTo} > 0){
			sql.append(" and ${field.columnName}<=" + ${propTo});

		}

			    <#else>
			  </#if>
		     </#if>
	       </#list>
	
		JSONObject result =  new JSONObject();
		Pagination page = new Pagination(parm.getIntValue(CURRENT), parm.getIntValue(SIZE));
		
		List<Map<String,Object>> recordsMaster = ${beanVar}Service.retrieveList(page, sql.toString());
		result.put(RECORDS, recordsMaster);

		result.put(TOTAL, ${beanVar}Service.retrieveCount(sql.toString()));
		return getSuccessResult(result);
	  
	  <#else>
	    ${beanName}VO ${beanVar}VO = parm.toJavaObject(${beanName}VO.class);
		RsEntityWrapper<${beanName}VO> ew = new RsEntityWrapper<>(${beanVar}VO);
		${beanVar}VO.setId(null);
	     <#list entityFields as field>
		   <#if field.query>
			<#if field.fuzzyQuery>
		String ${field.beanPropertyName} = ${beanVar}VO.get${field.beanPropertyName?cap_first}();
		
		if(StringUtils.isNotBlank(${field.beanPropertyName})){
			ew.like("${field.columnName}", ${field.beanPropertyName});
		}
			<#elseif field.rangeQuery>
			<#assign propFrom=field.beanPropertyName+"From"/>
			<#assign propTo=field.beanPropertyName+"To"/>
			<#assign rangeSql=field.beanPropertyName+"RangeSql"/>
			<#assign rangeParams=field.beanPropertyName+"RangeParams"/>
		${field.typeSimple} ${propFrom} = parm.getIntValue("(B)${field.beanPropertyName}");
		${field.typeSimple} ${propTo} = parm.getIntValue("(E)${field.beanPropertyName}");

		StringBuilder ${rangeSql} = new StringBuilder();
		List<Object> ${rangeParams} = new ArrayList<>();
		if(${propFrom}!=null && ${propFrom} >= 0){
			${rangeSql}.append("${field.columnName}>=${r"#{"}${propFrom}${r"}"} and ");
			${rangeParams}.add(${propFrom});
		}
				<#assign propFrom=field.beanPropertyName+"To"/>
		if(${propTo}!=null && ${propTo} > 0){
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
	  	  
	  </#if>	
	}

	
	<#if !isSql>
	  <#list subTableNames as name>
	
	    <#assign index=name?index_of(':')>
		<#assign tName="${name?substring(0,index)}">
		<#assign subColumnVar="${name?substring(index + 1)}">
		<#assign underIndex=tName?index_of('_')>
		<#assign tNamePrex="${tName?substring(0,underIndex)}">
		<#assign tNameAppend="${tName?substring(underIndex + 1)}">
		<#assign subeanVar="${tNamePrex + tNameAppend}">
		<#assign subeanName="${subeanVar?cap_first}">		
		<#assign columnIndex=tableName?index_of('_')>
		<#assign columnAppend="${tableName?substring(columnIndex + 1)}">
		<#assign subCol="${columnAppend?cap_first}">
	
	@RequestMapping("/query/${tName}")
	@ResponseBody
	public Object retrieve${subeanName}List(@RequestBody JSONObject parm) {		

		String id = parm.getString("id");
		${subeanName}VO ${subeanVar}VO = new ${subeanName}VO();
		${subeanVar}VO.set${subCol}id(id);
		${subeanVar}VO.setDeleted(Constants.DelInd.FALSE);
		RsEntityWrapper<${subeanName}VO> rsEntity = new RsEntityWrapper<${subeanName}VO> (${subeanVar}VO);
		return getSuccessResult(generalMapper.selectByPageOrder(rsEntity));	
	}
	 	
	 </#list>
	
	
	<#else>
	 <#list subTableNames as name>
	
	    <#assign index=name?index_of(':')>
		<#assign tName="${name?substring(0,index)}">
		<#assign underIndex=tName?index_of('_')>
		<#assign tNamePrex="${tName?substring(0,underIndex)}">
		<#assign tNameAppend="${tName?substring(underIndex + 1)}">
		<#assign subeanVar="${tNamePrex + tNameAppend}">
		<#assign subeanName="${subeanVar?cap_first}">		
	
	@RequestMapping("/query/${tName}")
	@ResponseBody
	public Object retrieve${subeanName}List(@RequestBody JSONObject parm) {		

		String id = parm.getString("id");
		if (id==null || "".equals(id)) {
			return getInvalidParamResult();
		}

		JSONObject result =  new JSONObject();
		
		List<Map<String,Object>> records = ${beanVar}Service.retrieve${subeanName}List(id);
		result.put(RECORDS, records);
		
		return getSuccessResult(result);
	}
	 	
	  </#list>		
	</#if>
		
	<#list cusCodeList as codeNames>	
	  <#assign codeIndex=codeNames?index_of(':')>
	  <#assign codeName ="${codeNames?substring(0,codeIndex)}" >
	  <#assign codeNameBean ="${codeName?cap_first}">
	  	  	  
	@RequestMapping("/execute/${codeName}")
	@ResponseBody
	public Object execute${codeNameBean}(@RequestBody JSONObject parm)
	{
		JSONObject result =  new JSONObject();		
		JSONArray recordArray = parm.getJSONArray(RECORDS);
		Boolean bSuccess = false;
		if (recordArray.size() > 0){
			for (int i = 0; i<recordArray.size(); i++ ) {
				JSONObject record = recordArray.getJSONObject(i);
				if (${beanVar}Service.execute${codeNameBean}("", "", "")) {//TODO
					bSuccess=true;
				}				
			}		
		}
		if (bSuccess) {
			return getSuccessResult(result);
		}else {
			return getInvalidParamResult(result);
		}		
	}  	
	</#list>	
	
	@RequestMapping(value="/buttons/{tableId}",method=RequestMethod.GET )
	@ResponseBody
	public Object getCustButtons(@PathVariable("tableId") String tableId) {
		SysCustbt custbtParm = new SysCustbt();
		custbtParm.setBdid(tableId);
		custbtParm.setDeleted(Constants.DelInd.FALSE);
		return getSuccessResult(generalMapper.selectList(new RsEntityWrapper<>(custbtParm)));
	}
	
	@RequestMapping(value="/js/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Object getJsList(@PathVariable("id") String id, @RequestBody JSONObject parm){
		SysZqjs sysZqjsParam = new SysZqjs();
		sysZqjsParam.setBdid(id);
		sysZqjsParam.setJslx(parm.getString("jslx"));
		sysZqjsParam.setDeleted(Constants.DelInd.FALSE);
		RsEntityWrapper<SysZqjs> wrapper = new RsEntityWrapper<>(sysZqjsParam);
		List<SysZqjs> zqjsList = generalMapper.selectList(wrapper);
		JSONObject result = new JSONObject();
		if (zqjsList != null){
			Object[] arr = zqjsList.stream()
					.map((x)->{
				JSONObject zqjs = new JSONObject();
				zqjs.put("id", x.getId());
				zqjs.put("js", new String(x.getJs()));
				zqjs.put("jslx", x.getJslx());
				zqjs.put("nr",x.getNr());
				zqjs.put("bdid", x.getBdid());
				zqjs.put("whr", x.getWhr());
				zqjs.put("whsj", x.getWhsj());
				zqjs.put("whrid",x.getWhrid());
				zqjs.put("cjr", x.getCjr());
				zqjs.put("cjsj", x.getCjsj());
				zqjs.put("cjrid", x.getCjrid());
				zqjs.put("version", x.getVersion());
				return zqjs;	
			}).toArray();
			result.put(RECORDS, arr);
			result.put(TOTAL, arr.length);
		}else{
			result.put(RECORDS, new JSONArray(0));
			result.put(TOTAL, 0);
		}
		return getSuccessResult(result);
	}
	
	@RequestMapping(value="/sql/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Object getSqlList(@PathVariable("id") String id, @RequestBody JSONObject parm){
		SysZqsql zqsqlParam = new SysZqsql();
		zqsqlParam.setBdid(id);
		zqsqlParam.setCode(parm.getString("code"));
		zqsqlParam.setDeleted(Constants.DelInd.FALSE);
		List<SysZqsql> zqsqlList = generalMapper.selectList(new RsEntityWrapper<>(zqsqlParam));
		JSONObject result = new JSONObject();
		if (zqsqlList != null){
			Object[] arr = zqsqlList.stream()
					.map((x)->{
				JSONObject zqsql = new JSONObject();
				zqsql.put("id", x.getId());
				zqsql.put("code", x.getCode());
				zqsql.put("sql", new String(x.getZqsql()));
				zqsql.put("nr",x.getNr());
				zqsql.put("bdid", x.getBdid());
				zqsql.put("whr", x.getWhr());
				zqsql.put("whsj", x.getWhsj());
				zqsql.put("whrid",x.getWhrid());
				zqsql.put("cjr", x.getCjr());
				zqsql.put("cjsj", x.getCjsj());
				zqsql.put("cjrid", x.getCjrid());
				zqsql.put("version", x.getVersion());
				return zqsql;	
			}).toArray();
			result.put(RECORDS, arr);
			result.put(TOTAL, arr.length);
		}else{
			result.put(RECORDS, new JSONArray(0));
			result.put(TOTAL, 0);
		}
		return getSuccessResult(result);
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
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public Object add(@RequestBody JSONObject parm){
	    JSONObject json = JSONObject.parseObject(parm.toJSONString());
		RsCommonResponse resp = json.toJavaObject(RsCommonResponse.class);
		${beanName}VO ${beanVar} = JSONObject.toJavaObject(processJSONObject(resp.getMaster()), ${beanName}VO.class);
		JSONObject result =  new JSONObject();
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
			
			result.put(ID, ${beanVar}.getId());
			result.put(VERSION, ${beanVar}.getVersion());
			
		}else{
			return getInvalidParamResult();
		}
		
		
		//process sub-tables
		if (resp.getDetail() != null && resp.getDetail().length > 0) {
			for (int i = 0; i < resp.getDetail().length; i++) {
			    SysBxx bxx = getBxx(resp.getDetail()[i].getId());
			   <#list subTableNames as name>
	
	          <#assign index=name?index_of(':')>
		      <#assign tName="${name?substring(0,index)}">
		      <#assign underIndex=tName?index_of('_')>
		      <#assign tNamePrex="${tName?substring(0,underIndex)}">
		      <#assign tNameAppend="${tName?substring(underIndex + 1)}">
		      <#assign subeanVar="${tNamePrex + tNameAppend}">
		      <#assign subeanName="${subeanVar?cap_first}">
		     
		     if ("${tName}".equals(bxx.getBm().toLowerCase())) {
			       JSONArray jsonArray = resp.getDetail()[i].getRecords();
					for (int j = 0; j < jsonArray.size(); j++) {
						JSONObject object = jsonArray.getJSONObject(j);
						String tag = object.getString("tag");
						${subeanName}VO ${subeanVar}VO = JSONObject.toJavaObject(processJSONObject(object), ${subeanName}VO.class);
						${subeanVar}VO.set${mainTableAppend}id(${beanVar}.getId());
						if (validateData(${subeanVar}VO)) {
							if (tag.equals(CGConstants.ColChgTypes.ADD)) {							
								${subeanVar}Service.add(${subeanVar}VO);
							} 
						} else {
							return getInvalidParamResult();// TODO
						}
					}
			     }
	          </#list>
			}
	     }

		return getSuccessResult(result);
		
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
	@RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Object get(@PathVariable("id") String id){
		${beanName}VO ${beanVar}VO = generalMapper.selectById(id, ${beanName}VO.class);
		if(${beanVar}VO!=null) {
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
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
	public Object update(@RequestBody JSONObject parm){

		JSONObject json = JSONObject.parseObject(parm.toJSONString());
		RsCommonResponse resp = json.toJavaObject(RsCommonResponse.class);	
		${beanName}VO ${beanVar} = JSONObject.toJavaObject(processJSONObject(resp.getMaster()), ${beanName}VO.class);

        if (StringUtils.isBlank(${beanVar}.getId())) {
			return getInvalidParamResult();
		}
		
		JSONObject result =  new JSONObject();
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
				
				result.put(ID, ${beanVar}.getId());
				result.put(VERSION, ${beanVar}.getVersion());
				
			}else{
				return getModifiedResult();
			}
		}else{
			return getInvalidParamResult();
		}
		
		//process sub-tables
		if (resp.getDetail() != null && resp.getDetail().length > 0) {
			for (int i = 0; i < resp.getDetail().length; i++) {
			    SysBxx bxx = getBxx(resp.getDetail()[i].getId());
			   <#list subTableNames as name>
	
	          <#assign index=name?index_of(':')>
		      <#assign tName="${name?substring(0,index)}">
		      <#assign underIndex=tName?index_of('_')>
		      <#assign tNamePrex="${tName?substring(0,underIndex)}">
		      <#assign tNameAppend="${tName?substring(underIndex + 1)}">
		      <#assign subeanVar="${tNamePrex + tNameAppend}">
		      <#assign subeanName="${subeanVar?cap_first}">
		     
		     if ("${tName}".equals(bxx.getBm().toLowerCase())) {
			       JSONArray jsonArray = resp.getDetail()[i].getRecords();
					for (int j = 0; j < jsonArray.size(); j++) {
						JSONObject object = jsonArray.getJSONObject(j);
						String tag = object.getString("tag");
						${subeanName}VO ${subeanVar}VO = JSONObject.toJavaObject(processJSONObject(object), ${subeanName}VO.class);
						${subeanVar}VO.set${mainTableAppend}id(${beanVar}.getId());
						if (validateData(${subeanVar}VO)) {
							if (tag.equals(CGConstants.ColChgTypes.ADD)) {
							    							
								${subeanVar}Service.add(${subeanVar}VO);
							} else if (tag.equals(CGConstants.ColChgTypes.UPDATE)) {

								${subeanVar}Service.update(${subeanVar}VO);
							} else if (tag.equals(CGConstants.ColChgTypes.DELETE)) {
								${subeanVar}Service.delete(${subeanVar}VO);
							}
						} else {
							return getInvalidParamResult();// TODO
						}
					}
			     }
	          </#list>
			}
	     }

		return getSuccessResult(result);
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
		JSONArray ids = parm.getJSONArray("rows");
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
	
	private SysBxx getBxx(String bid){	
		SysBxx bxxParm = new SysBxx();
		bxxParm.setId(bid);
		bxxParm.setDeleted(Constants.DelInd.FALSE);
		return generalMapper.selectById(bid, SysBxx.class);
	}
	
	private JSONObject processJSONObject(JSONObject jsonObject) {		
		Map<String, Object> parmMap = new HashMap<> (jsonObject);		
		Map<String, Object> resultMap = new HashMap<> ();
		Iterator<Entry <String, Object>> iterator = parmMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entity= iterator.next();
			int index = entity.getKey().indexOf("_");
			if (index > 0) {
				resultMap.put(entity.getKey().substring(index + 1).toLowerCase(), entity.getValue());
			}else {
				resultMap.put(entity.getKey().toLowerCase(), entity.getValue());
			}
		}
		return new JSONObject(resultMap);
	}
	
}
