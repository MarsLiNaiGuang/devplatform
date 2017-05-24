/**
 *Copyright (c) 2016-2017, Roadshare.com.
 */
package com.rs.devplatform.controller.jcsj;
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
import com.rs.devplatform.persistent.jcsj.Bmpz;
import com.rs.devplatform.vo.jcsj.BmpzVO;
import com.rs.devplatform.service.jcsj.BmpzService;

import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.validator.ValidateError;
import com.rs.framework.common.Constants;

@Controller
@RequestMapping("/jcsj/bmpz")
public class BmpzController extends BaseController{

	@Autowired
	BmpzService bmpzService;
	
	
	

	@Autowired
	CGService cgService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ModelAndView index() {
	   ModelAndView mav=new ModelAndView();
		mav.setViewName("jcsj/bmpz");
	   return mav;
	}
	
	
	@RequestMapping("/{id}")
	@ResponseBody
	public Object getMasterInfoByTableId(@PathVariable("id") String tableId)
	{
		JSONObject result = null;
		
		try {
			  result = cgService.getFormMasterDetail(tableId, Constants.TO_LOWCASE_FULL);
			
		}catch(BuzException e){
			
			e.printStackTrace();			
		}
		
		return getSuccessResult(result);
	}
	
	/**
	 * @api {POST} /jcsj/bmpz/list 获取示例列表
	 * @apiGroup jcsj/bmpz
	 * @apiParamExample {json} Request-Example: 
	 *  {
	 *    "current":1, "size":10, "orderBy":"name", "asc":"true",
	 *  }
	 *  {"name":"123", "orderby":"name", "comments":"不分页,current为null"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} rows.whr 维护人	
	 * @apiSuccess {Date} rows.whsj 维护时间,1488344127269
	 * @apiSuccess {String} rows.mc 部门名称	
	 * @apiSuccess {String} rows.bmzz 部门职责	
	 * @apiSuccess {Integer} rows.bzrs 编制人数	
	 * @apiSuccess {String} rows.bz 备注	
	 * @apiSuccess {String} rows.id id	
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {"total",25, "current":1, "size":10, "pages":3, "rows":[
	 *    {
	 *      "whr" : "维护人",	
	 *      "whsj" : "维护时间",	
	 *      "mc" : "部门名称",	
	 *      "bmzz" : "部门职责",	
	 *      "bzrs" : "编制人数",	
	 *      "bz" : "备注",	
	 *      "id" : "id",	
	 *    }
	 *  ]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public Object list(@RequestBody JSONObject parm) {
	    BmpzVO bmpzVO = parm.toJavaObject(BmpzVO.class);
		RsEntityWrapper<BmpzVO> ew = new RsEntityWrapper<>(bmpzVO);
		bmpzVO.setId(null);
		return getSuccessResult(generalMapper.selectByPageOrder(ew));
	  	  
	}

	
	
	
	
	
	
	
	
	

	
	
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
	 *  @api {POST} /jcsj/bmpz 增加示例
	 *  @apiGroup jcsj/bmpz
	 * 
	 * @apiParam {String} sjbm 上级部门
	 * @apiParam {String} bmid 部门编码
	 * @apiParam {String} mc 部门名称
	 * @apiParam {String} bmzz 部门职责
	 * @apiParam {Integer} bzrs 编制人数
	 * @apiParam {String} bz 备注
	 * @apiParamExample {json} Request-Example: 
	 *  {
	 *    "sjbm":"abc",
	 *    "bmid":"abc",
	 *    "mc":"abc",
	 *    "bmzz":"abc",
	 *    "bzrs":123,	 				
	 *    "bz":"abc",
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
		BmpzVO bmpz = JSONObject.toJavaObject(processJSONObject(resp.getMaster()), BmpzVO.class);
		JSONObject result =  new JSONObject();
		if(validateData(bmpz)) {
			bmpzService.add(bmpz);
			
			result.put(ID, bmpz.getId());
			result.put(VERSION, bmpz.getVersion());
			
		}else{
			return getInvalidParamResult();
		}
		
		
		//process sub-tables
		if (resp.getDetail() != null && resp.getDetail().length > 0) {
			for (int i = 0; i < resp.getDetail().length; i++) {
			    SysBxx bxx = getBxx(resp.getDetail()[i].getId());
			}
	     }

		return getSuccessResult(result);
		
	}
	
	/**
	 * @api {GET} /jcsj/bmpz/{id} 获取示例详细
	 * @apiGroup jcsj/bmpz
	 *	
	 * @apiParam {String} id ID,是路径上的参数id	
	 * @apiParamExample {json} Request-Example: 
	 *  GET /jcsj/bmpz/0f077145cc894f7990387c9458091e1b
	 * 
	 * @apiSuccess {String} whr 维护人
	 * @apiSuccess {Date} whsj 维护时间,1488344117269,13位时间戳
	 * @apiSuccess {String} sjbm 上级部门
	 * @apiSuccess {String} bmid 部门编码
	 * @apiSuccess {String} mc 部门名称
	 * @apiSuccess {String} bmzz 部门职责
	 * @apiSuccess {Integer} bzrs 编制人数
	 * @apiSuccess {String} bz 备注
	 * @apiSuccess {String} id id
	 * @apiSuccess {Integer} version version
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {
	 *    "whr":"维护人",	
	 *    "whsj":"维护时间",	
	 *    "sjbm":"上级部门",	
	 *    "bmid":"部门编码",	
	 *    "mc":"部门名称",	
	 *    "bmzz":"部门职责",	
	 *    "bzrs":"编制人数",	
	 *    "bz":"备注",	
	 *    "id":"id",	
	 *    "version":"version",	
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
		BmpzVO bmpzVO = generalMapper.selectById(id, BmpzVO.class);
		if(bmpzVO!=null) {
			return getSuccessResult(bmpzVO);
		}else{
			addValidationError(new ValidateError("","无效的记录"));
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {PUT} /jcsj/bmpz/{id} 更新示例
	 * @apiGroup jcsj/bmpz
	 *	
	 * @apiParam {String} id ID,是路径上的参数id	
	 * @apiParam {Integer} version Version
	 * @apiParam {String} sjbm 上级部门	
	 * @apiParam {String} bmid 部门编码	
	 * @apiParam {String} mc 部门名称	
	 * @apiParam {String} bmzz 部门职责	
	 * @apiParam {Integer} bzrs 编制人数	
	 * @apiParam {String} bz 备注	
	 * @apiParamExample {json} Request-Example: 
	 *  PUT /jcsj/bmpz/0f077145cc894f7990387c9458091e1b
	 * 	{
	 *    "version":1,
	 *    "sjbm":"上级部门",	
	 *    "bmid":"部门编码",	
	 *    "mc":"部门名称",	
	 *    "bmzz":"部门职责",	
	 *    "bzrs":"编制人数",	
	 *    "bz":"备注",	
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
		BmpzVO bmpz = JSONObject.toJavaObject(processJSONObject(resp.getMaster()), BmpzVO.class);

        if (StringUtils.isBlank(bmpz.getId())) {
			return getInvalidParamResult();
		}
		
		JSONObject result =  new JSONObject();
		if(validateData(bmpz)) {
			if(bmpzService.update(bmpz)){
				
				result.put(ID, bmpz.getId());
				result.put(VERSION, bmpz.getVersion());
				
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
			}
	     }

		return getSuccessResult(result);
	}
	
	/**
	 * @api {DELETE} /jcsj/bmpz 删除示例
	 * @apiGroup jcsj/bmpz
	 *	
	 * @apiParam {StringArray} ids IDs,id数组	
	 * @apiParamExample {json} Request-Example: 
	 *  DELETE /jcsj/bmpz
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
		if(bmpzService.deleteBatch(idArray)){
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
