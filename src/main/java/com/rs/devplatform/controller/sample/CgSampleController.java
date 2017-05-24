/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.controller.sample;

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
import com.rs.devplatform.persistent.sample.CgSample;
import com.rs.devplatform.service.sample.CgSampleService;
import com.rs.devplatform.vo.sample.CgSampleVO;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.validator.ValidateError;

@Controller
@RequestMapping("/sample/cgSample")
public class CgSampleController extends BaseController{

	@Autowired
	CgSampleService cgSampleService;
	
	@GetMapping
	public String index(){
		return "sample/cgSample";
	}
	
	/**
	 * @api {POST} /sample/cgSample/list 获取示例列表
	 * @apiGroup sample/cgSample
	 * @apiParam {String} name Username用户名,模糊查询
	 * @apiParam {Integer} ageFrom 年龄范围开始
	 * @apiParam {Integer} ageTo 年龄范围结束
	 * @apiParam {Date} dtFrom 测试日期范围开始,1488344117269, 13位时间戳(前端补齐13位)
	 * @apiParam {Date} dtTo 测试日期范围结束,1488344127269, 13位时间戳(前端补齐13位)
	 * @apiParamExample {json} Request-Example: 
	 * {
	 *  "current":1, "size":10, "orderBy":"name", "asc":"true",
	 *  "name":"abc",
	 *  "ageFrom": 18,	"ageTo": 28,
	 *  "dtFrom": 1488344117269, "dtTo": 1488344127269,
	 *  }
	 *  {"name":"123", "orderby":"name", "comments":"不分页,current为null"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} rows.id id主键	
	 * @apiSuccess {String} rows.name Username用户名	
	 * @apiSuccess {String} rows.nkname NickName昵称	
	 * @apiSuccess {Integer} rows.age 年龄	
	 * @apiSuccess {Date} rows.dt 测试日期	
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {"total",25, "current":1, "size":10, "pages":3, "rows":[
	 *    {
	 *      "id" : "id主键",	
	 *      "name" : "Username用户名",	
	 *      "nkname" : "NickName昵称",	
	 *      "age" : "年龄",	
	 *      "dt" : "测试日期",	
	 *    }
	 *  ]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object list(@RequestBody JSONObject parm) {
		CgSampleVO cgSampleVO = parm.toJavaObject(CgSampleVO.class);
		RsEntityWrapper<CgSampleVO> ew = new RsEntityWrapper<>(cgSampleVO);
		String name = cgSampleVO.getName();
		cgSampleVO.setName(null);
		if(StringUtils.isNotBlank(name)){
			ew.like("sample_name", name);
		}
		Integer ageFrom = cgSampleVO.getAgeFrom();
		Integer ageTo = cgSampleVO.getAgeTo();
		StringBuilder ageRangeSql = new StringBuilder();
		List<Object> ageRangeParams = new ArrayList<>();
		if(ageFrom!=null){
			ageRangeSql.append("sample_age>=#{ageFrom} and ");
			ageRangeParams.add(ageFrom);
		}
		if(ageTo!=null){
			ageRangeSql.append("sample_age<#{ageTo} and ");
			ageRangeParams.add(ageTo);
		}
		if(ageRangeSql.length()!=0){
			ageRangeSql.delete(ageRangeSql.length()-5, ageRangeSql.length());
			ew.andNew(ageRangeSql.toString(), ageRangeParams.toArray());
		}
		Date dtFrom = cgSampleVO.getDtFrom();
		Date dtTo = cgSampleVO.getDtTo();
		StringBuilder dtRangeSql = new StringBuilder();
		List<Object> dtRangeParams = new ArrayList<>();
		if(dtFrom!=null){
			dtRangeSql.append("sample_dt>=#{dtFrom} and ");
			dtRangeParams.add(dtFrom);
		}
		if(dtTo!=null){
			dtRangeSql.append("sample_dt<#{dtTo} and ");
			dtRangeParams.add(dtTo);
		}
		if(dtRangeSql.length()!=0){
			dtRangeSql.delete(dtRangeSql.length()-5, dtRangeSql.length());
			ew.andNew(dtRangeSql.toString(), dtRangeParams.toArray());
		}
		return getSuccessResult(generalMapper.selectByPageOrder(ew));
	}
	
	/**
	 *  @api {POST} /sample/cgSample 增加示例
	 *  @apiGroup sample/cgSample
	 * 
	 * @apiParam {String} name Username用户名
	 * @apiParam {String} nkname NickName昵称
	 * @apiParam {Integer} age 年龄
	 * @apiParam {Date} dt 测试日期,1488344117269,13位时间戳
	 * @apiParam {Date} dt2 测试日期,1488344117269,13位时间戳
	 * @apiParamExample {json} Request-Example: 
	 *  {
	 *    "name":"abc",
	 *    "nkname":"abc",
	 *    "age":123,	 				
	 *    "dt":1488344117269,
	 *    "dt2":1488344117269,
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
	 *      {"errCode":"name","errorMsg":"Username用户名不能为空"},
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
		CgSampleVO cgSample = JSONObject.toJavaObject(parm, CgSampleVO.class);
		if(validateData(cgSample)) {
			CgSample cgSampleParm = new CgSample();
			cgSampleParm.setName(cgSample.getName());
			if(isDuplicateBeforeCreate(cgSampleParm)){
				return getDuplicateResult();
			}
			cgSampleService.add(cgSample);
			JSONObject result =  new JSONObject();
			result.put(ID, cgSample.getId());
			result.put(VERSION, cgSample.getVersion());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {GET} /sample/cgSample/{id} 获取示例详细
	 * @apiGroup sample/cgSample
	 *	
	 * @apiParam {String} id ID,是路径上的参数id	
	 * @apiParamExample {json} Request-Example: 
	 *  GET /sample/cgSample/0f077145cc894f7990387c9458091e1b
	 * 
	 * @apiSuccess {String} id id主键
	 * @apiSuccess {String} name Username用户名
	 * @apiSuccess {String} nkname NickName昵称
	 * @apiSuccess {Integer} age 年龄
	 * @apiSuccess {Date} dt 测试日期
	 * @apiSuccess {String} whr whr
	 * @apiSuccess {String} whrid whrid
	 * @apiSuccess {Date} whsj whsj
	 * @apiSuccess {String} cjr cjr
	 * @apiSuccess {String} cjrid cjrid
	 * @apiSuccess {Date} cjsj cjsj
	 * @apiSuccess {String} deleted deleted
	 * @apiSuccess {Integer} version version
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 *  {
	 * 		"id":"id主键",	
	 * 		"name":"Username用户名",	
	 * 		"nkname":"NickName昵称",	
	 * 		"age":"年龄",	
	 * 		"dt":"测试日期",	
	 * 		"whr":"whr",	
	 * 		"whrid":"whrid",	
	 * 		"whsj":"whsj",	
	 * 		"cjr":"cjr",	
	 * 		"cjrid":"cjrid",	
	 * 		"cjsj":"cjsj",	
	 * 		"deleted":"deleted",	
	 * 		"version":"version",	
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
		CgSampleVO cgSampleVO = generalMapper.selectById(id, CgSampleVO.class);
		if(cgSampleVO!=null) {
			return getSuccessResult(cgSampleVO);
		}else{
			addValidationError(new ValidateError("","无效的记录"));
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {PUT} /sample/cgSample/{id} 更新示例
	 * @apiGroup sample/cgSample
	 *	
	 * @apiParam {String} id ID,是路径上的参数id	
	 * @apiParam {Integer} version Version
	 * @apiParam {String} name Username用户名	
	 * @apiParam {String} nkname NickName昵称	
	 * @apiParam {Integer} age 年龄	
	 * @apiParam {Date} dt 测试日期	
	 * @apiParamExample {json} Request-Example: 
	 *  PUT /sample/cgSample/0f077145cc894f7990387c9458091e1b
	 * 	{
	 		"version":1,
	 * 		"name":"Username用户名",	
	 * 		"nkname":"NickName昵称",	
	 * 		"age":"年龄",	
	 * 		"dt":"测试日期",	
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
		CgSampleVO cgSample = JSONObject.toJavaObject(parm, CgSampleVO.class);
		cgSample.setId(id);
		if(validateData(cgSample)) {
			CgSample cgSampleParm = new CgSample();
			cgSampleParm.setId(cgSample.getId());
			cgSampleParm.setVersion(cgSample.getVersion());
			cgSampleParm.setName(cgSample.getName());
			if(isDuplicateBeforeUpdate(cgSampleParm)){
				return getDuplicateResult();
			}
			if(cgSampleService.update(cgSample)){
				JSONObject result =  new JSONObject();
				result.put(ID, cgSample.getId());
				result.put(VERSION, cgSample.getVersion());
				return getSuccessResult(result);
			}else{
				return getModifiedResult();
			}
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {DELETE} /sample/cgSample 删除示例
	 * @apiGroup sample/cgSample
	 *	
	 * @apiParam {StringArray} ids IDs,id数组	
	 * @apiParamExample {json} Request-Example: 
	 *  DELETE /sample/cgSample
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
		if(cgSampleService.deleteBatch(idArray)){
			return getSuccessResult();
		}else{
			return getModifiedResult();
		}
	}
}
