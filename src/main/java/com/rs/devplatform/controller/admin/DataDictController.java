package com.rs.devplatform.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.rs.devplatform.persistent.SysCdtype;
import com.rs.devplatform.persistent.SysCdval;
import com.rs.devplatform.service.admin.DataDictService;
import com.rs.devplatform.vo.CodeTypeVO;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;

@Controller
@RequestMapping("/admin/datadict")
public class DataDictController extends BaseController {
	
	private static final String CDTYPE = "cdtype";
	
	@Autowired
	DataDictService ddService;

	/**
	 * @api {GET} /admin/datadict 转到数据字典管理页面
	 * @apiGroup Admin/datadict
	 * @apiSuccess {String} html 对应数据字典管理页面 
	 * @apiSuccessExample {json} 页面命名:
	 * 			{"页面":"manage/datadict.html"}
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index() {
		return "manage/datadict";
	}
	
	/**
	 * @api {POST} /admin/datadict/list CodeType获取List
	 * @apiGroup Admin/datadict
	 * @apiParam {String} name 数据字典名(模糊查询)
	 * @apiParam {String} cdtype 数据字典type（模糊查询）
	 * @apiParamExample {json} Request-Example: 
	 * 		{
			    "current": 1,
			    "size": 10,
			    "comments": "easyUI分页参数为page/rows, form提交方式server端做了转换，自动mapping到current/size",
			    "name":"字典类型描述",
			    "cdtype":"字典类型"
			}
	 * @apiSuccess {JSONArray} records 返回数据字典Records
	 * @apiSuccess {String} records.id CodeType ID
	 * @apiSuccess {String} records.name CodeType
	 * @apiSuccess {String} records.cdtype CodeType
	 * @apiSuccess {String} records.version CodeType version
	 * @apiSuccessExample {json} Success-Response:
	 *			HTTP/1.1 200 OK
	 * 			{"records":[
	 * 				{"name":"示例数据字典名称","cdtype":"sample_type", "id":"29bb254e7ddd4c65a4c2c8ee4f91b66d", "version":1},
	 * 				{"name":"示例数据字典名称","cdtype":"sample_type", "id":"29bb254e7ddd4c65a4c2c8ee4f91b66d", "version":1}
	 * 			],"total":2}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object dataDictList(@RequestBody JSONObject parm, HttpSession session) {
		CodeTypeVO typeVO = parm.toJavaObject(CodeTypeVO.class);
		String codeType = typeVO.getCdtype();
		String typeDescp = typeVO.getName();
		typeVO.setName(null);
		typeVO.setCdtype(null);
		RsEntityWrapper<CodeTypeVO> ew = new RsEntityWrapper<>(typeVO);
		if(!StringUtils.isBlank(codeType)){
			ew.like("CDTYPE_CDTYPE", codeType.trim());
		}
		if(!StringUtils.isBlank(typeDescp)){
			ew.like("CDTYPE_NAME", typeDescp.trim());
		}
		Page<CodeTypeVO> page = generalMapper.selectByPageOrder(ew);
		List<CodeTypeVO> codeTypes = page.getRecords();
		
		JSONObject result = new JSONObject();
		if(codeTypes != null){
			Object[] arr = codeTypes.stream().map((x)->{
				JSONObject project = new JSONObject();
				project.put(ID, x.getId());
				project.put(NAME, x.getName());
				project.put(CDTYPE, x.getCdtype());
				project.put(VERSION, x.getVersion());
				return project;
			}).toArray();
			result.put(RECORDS, arr);
			result.put(TOTAL, page.getTotal());
		}else{
			result.put(RECORDS, new JSONArray(0));
			result.put(TOTAL, 0);
		}
		return getSuccessResult(result);
	}
	
	/**
	 * @api {POST} /admin/datadict CodeType增加
	 * @apiGroup Admin/datadict
	 * 
	 * @apiParam {String} name 数据字典名
	 * @apiParam {String} cdtype 数据字典type
	 * @apiParamExample {json} Request-Example: 
	 * 					{"name":"测试类型1","cdtype":"test_type"}
	 * 
	 * @apiSuccess {String} id 返回数据字典Type id
	 * @apiSuccess {Integer} version 返回数据字典Typeversion
	 * @apiSuccessExample {json} Success-Response:
	 * 			HTTP/1.1 200 OK
	 * 			{"id":"0f077145cc894f7990387c9458091e1b", "version":1}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.POST)
	@ResponseBody
	public Object dictAdd(@RequestBody JSONObject parm, HttpServletRequest req){
		SysCdtype codeType = JSONObject.toJavaObject(parm, SysCdtype.class);
		if(validateData(codeType)){
			SysCdtype parmCd = new SysCdtype();
			parmCd.setCdtype(codeType.getCdtype());
			if(isDuplicateBeforeCreate(parmCd)){
				return getDuplicateResult();
			}
			ddService.createCodeType(codeType);
			JSONObject result = new JSONObject();
			result.put(ID, codeType.getId());
			result.put(VERSION, codeType.getVersion());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {PUT} /admin/datadict/{typeId} CodeType更新
	 * @apiGroup Admin/datadict
	 * @apiDescription 只更新类型描述(name)，不更新codeType
	 * 
	 * @apiParam {String} typeId 数据字典TypeID
	 * @apiParam {String} name 数据字典Type name
	 * @apiParam {Integer} version 数据字典Type version
	 * @apiParamExample {json} Request-Example: 
	 * 					{"name":"测试类型1", "version":1}
	 * 
	 * @apiSuccess {String} id 数据字典Typeid
	 * @apiSuccess {Integer} version 数据字典Typeversion
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *		HTTP/1.1 200 OK
	 * 		{"version":2}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict

	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{typeId}", method=RequestMethod.PUT)
	@ResponseBody
	public Object dictEdit(@RequestBody JSONObject parm, @PathVariable("typeId") String typeId){
		SysCdtype codeType = JSONObject.toJavaObject(parm, SysCdtype.class);
		if(!StringUtils.isEmpty(codeType.getName()) && codeType.getVersion()!=null){
			codeType.setId(typeId);
			if(ddService.updateCodeType(codeType)){
				JSONObject result =  new JSONObject();
				result.put(ID, codeType.getId());
				result.put(VERSION, codeType.getVersion());
				return getSuccessResult(result);
			}else{
				return getModifiedResult();
			}
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {DELETE} /admin/datadict/{typeId} CodeType删除
	 * @apiGroup Admin/datadict
	 * 
	 * @apiParam {String} typeId 数据字典TypeID
	 * @apiParam {Integer} version 数据字典Type version
	 * @apiParamExample {json} Request-Example: 
	 * 					{"version":1}
	 * 
	 *  @apiSuccessExample {json} Success-Response:
	 * 			HTTP/1.1 200 OK
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict (Modified by others)
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{typeId}", method=RequestMethod.DELETE)
	@ResponseBody
	public Object dictDelete(@RequestBody JSONObject parm, @PathVariable("typeId") String typeId){
		Integer version = parm.getInteger(VERSION);
		if(!StringUtils.isEmpty(typeId) && version!=null){
			SysCdtype cdtype = new SysCdtype();
			cdtype.setId(typeId);
			cdtype.setVersion(version);
			if(ddService.deleteCodeType(cdtype)){
				return getSuccessResult();
			}else{
				return getModifiedResult();
			}
		}else{
			return getInvalidParamResult();
		}
	}
	/**
	 * @api {GET} /admin/datadict/{typeId} CodeType根据typeId查询
	 * @apiGroup Admin/datadict
	 * 
	 * @apiSuccess {String} id 数据字典Typeid
	 * @apiSuccess {String} name 数据字典Type name
	 * @apiSuccess {String} cdtype 数据字典cdtype
	 * @apiSuccess {Integer} version 数据字典Type version
	 * @apiSuccess {JSONArray} cdvals CodeType下对应的CodeValues
	 * @apiSuccess {String} cdvals.id 	CodeVal ID
	 * @apiSuccess {String} cdvals.cdval 	CodeVal
	 * @apiSuccess {String} cdvals.cddescp  CodeVal Descp
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{
	 * 			"id":"123", "name":"abc", "cdtype":"test_type", "version":1,
	 * 			"cdvals":[
		 * 			{"cddescp":"value3","cdval":"3","id":"06f60e31bb9b45a4b19638a4055aa5c7"},
		 * 			{"cddescp":"value1","cdval":"1","id":"bf42bd9b261140a2ae3b26be34936827"},
		 * 			{"cddescp":"value2","cdval":"2","id":"c252232850c242d295859afac318dffc"}
	 * 			]
	 * 		}
	 * 
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict (Modified by others)
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{typeId}", method=RequestMethod.GET)
	@ResponseBody
	public Object dictDelete(@PathVariable("typeId") String typeId){
		SysCdtype type = generalMapper.selectById(typeId, SysCdtype.class);
		if(type==null || Constants.DelInd.TRUE.equals(type.getDeleted())){
			return getModifiedResult();
		}
		JSONObject result = (JSONObject) JSON.toJSON(type);
		SysCdval cdvalParm = new SysCdval();
		cdvalParm.setCdtype(type.getCdtype());
		cdvalParm.setDeleted(Constants.DelInd.FALSE);
		List<SysCdval> valList = generalMapper.selectList(new RsEntityWrapper<>(cdvalParm));
		if(valList!=null){
			Object[] arr = valList.stream().map((x)->{
				JSONObject obj = new JSONObject();
				obj.put(ID, x.getId());
				obj.put("cdval", x.getCdval());
				obj.put("cddescp", x.getCddescp());
				return obj;
			}).toArray();
			result.put("cdvals", arr);
		}
		return getSuccessResult(result);
	}
	
	/*************************************************************/
	
	/**
	 * @api {GET} /admin/datadict/{typeId}/cdval CodeValues获取指定typeId的valList
	 * @apiGroup Admin/datadict
	 * 
	 * @apiParam {String} typeId 数据字典TypeID
	 * 
	 * @apiSuccess {JSONArray} records 返回数据字典CodeVal Records
	 * @apiSuccess {Integer} total 返回数据字典CodeVal count
	 * @apiSuccess {String} records.id 数据字典CodeValID
	 * @apiSuccess {String} records.cdval 数据字典CodeVal
	 * @apiSuccess {String} records.cddescp 数据字典CodeValDescp
	 * @apiSuccessExample {json} Success-Response:
	 * HTTP/1.1 200 OK
	 * 		{
	 * 			"records":[
	 * 				{"cdval":"PM", "cddescp":"Project Manager","id":"abc1"},
	 *				{"cdval":"TM", "cddescp":"Team Member", "id":"cdf1"}
	 * 			],
	 * 			"total":2
	 * 		}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{typeId}/cdval", method=RequestMethod.GET)
	@ResponseBody
	public Object getCodeVals(@PathVariable("typeId") String typeId, HttpServletRequest req) {
		SysCdtype cdtype = generalMapper.selectById(typeId, SysCdtype.class);
		if(cdtype==null){
			return getInvalidParamResult();
		}
		SysCdval cdvalParm = new SysCdval();
		cdvalParm.setCdtype(cdtype.getCdtype());
		cdvalParm.setDeleted(Constants.DelInd.FALSE);
		RsEntityWrapper<SysCdval> wrapper = new RsEntityWrapper<>(cdvalParm);
		wrapper.orderBy("CDVAL_CDVAL");
		List<SysCdval> codevals = generalMapper.selectList(wrapper);
		JSONObject result = new JSONObject();
		result.put(ID, cdtype.getId());
		result.put(NAME, cdtype.getName());
		result.put(CDTYPE, cdtype.getCdtype());
		result.put(VERSION, cdtype.getVersion());
		if(codevals!=null){
			Object[] arr = codevals.stream().map((x)->{
				JSONObject cdval = new JSONObject();
				cdval.put(ID, x.getId());
				cdval.put("cdval", x.getCdval());
				cdval.put("cddescp", x.getCddescp());
				return cdval;
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
	 * @api {POST} /admin/datadict/{typeId} CodeVal新增
	 * @apiGroup Admin/datadict
	 * 
	 * @apiParam {String} typeId 数据字典TypeID
	 * @apiParam {JSONArray} _ Code Vals
	 * @apiParamExample {json} Request-Example:
	 * 	{"cdvals": 
	 * 		[
	 * 			{"cdval":"PM", "cddescp":"Project Manager"},
	 *			{"cdval":"TM", "cddescp":"Team Member"}
	 * 		]
	 * }
	 * @apiSuccessExample {json} Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	{"cdvals": 
	 * 		[
	 * 			{"cdval":"PM", "cddescp":"Project Manager","id":"abc1"},
	 *			{"cdval":"TM", "cddescp":"Team Member", "id":"cdf1"}
	 * 		]
	 * }
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{typeId}", method=RequestMethod.POST)
	@ResponseBody
	public Object addCodeVal(@RequestBody JSONObject parm, @PathVariable("typeId") String typeId) {
		JSONArray cdvals = parm.getJSONArray("cdvals");
		SysCdval[] valArray = JSONArray.toJavaObject(cdvals, SysCdval[].class);
		if(valArray!=null){
			if(ddService.addCodeVal4Type(typeId, valArray)){
				JSONArray array = new JSONArray(valArray.length);
				for(SysCdval val:valArray){
					JSONObject json = new JSONObject();
					json.put(ID, val.getId());
					json.put("cdval", val.getCdval());
					json.put("cddescp", val.getCddescp());
					array.add(json);
				}
				parm.put("cdvals", array);
				return getSuccessResult(parm);
			}
		}
		return getInvalidParamResult();
	}
	
	/**
	 * @api {DELETE} /admin/datadict/cdval/{cdvalId} CodeVal删除
	 * @apiGroup Admin/datadict
	 * 
	 * @apiParam {String} cdvalId CodeVal ID
	 * @apiSuccessExample {json} Success-Response:
	 * 			HTTP/1.1 200 OK
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
	@RequestMapping(value="/cdval/{cdvalId}", method=RequestMethod.DELETE)
	@ResponseBody
	public Object deleteCodeVal(@PathVariable("cdvalId") String cdvalId) {
		if(ddService.deleteCodeVal(cdvalId)){
			return getSuccessResult();
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {PUT} /admin/datadict/cdval/{cdvalId} CodeVal更新
	 * @apiGroup Admin/datadict
	 * 
	 * @apiParam {String} cdvalId CodeVal ID
	 * @apiParam {String} cdval CodeVal
	 * @apiParam {String} cddescp CodeVal Description
	 * @apiParamExample {json} Request-Example: 
	 * 		{"cdval":"PM", "cddescp":"Project Manager"}
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 * 			HTTP/1.1 200 OK
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
	@RequestMapping(value="/cdval/{cdvalId}", method=RequestMethod.PUT)
	@ResponseBody
	public Object updateCodeVal(@PathVariable("cdvalId") String cdvalId, @RequestBody JSONObject parm) {
		SysCdval cdval = JSONArray.toJavaObject(parm, SysCdval.class);
		if(!StringUtils.isEmpty(cdval.getCdval()) && !StringUtils.isEmpty(cdval.getCddescp())){
			cdval.setId(cdvalId);
			if(ddService.updateCodeVal(cdval)){
				return getSuccessResult();
			}else{
				return getModifiedResult();
			}
		}else{
			return getInvalidParamResult();
		}
	}
	
}
