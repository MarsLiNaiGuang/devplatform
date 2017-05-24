package com.rs.devplatform.controller.admin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.service.admin.FuncApiService;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.entity.vo.FuncEntry;
import com.rs.framework.common.entity.vo.SysFuncgpVO;

@Controller
@RequestMapping("/admin/func")
public class FuncController extends BaseController {
	@Autowired
	FuncApiService funcapiService;
	
	/**
	 * @api {GET} /admin/func 转到功能组管理页面
	 * @apiGroup Admin/Func Manager
	 * @apiSuccess {String} html 对应数据字典管理页面 
	 * @apiSuccessExample {json} 页面命名:
	 * 			{"页面":"/manage/func.html"}
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index() {
		return "manage/func";
	}
	
	
	/**
	 * @api {POST} /admin/func/list 获取所有的功能组 
	 * @apiGroup Admin/Func Manager
	 * 
	 * @apiParam {String} current 当前页数
	 * @apiParam {String} size 每页的行数
	 * @apiParam {String} orderBy 按照什么字段进行排序
	 * @apiParam {String} asc 是否从小到大排序（true--是；false--否）
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * 	{
	 * 		"current":1, 
	 * 		"size":10, 
	 * 		"orderBy":"name", 
	 * 		"asc":"true",
	 * 		"name":"功能名称模糊查询"
	 * }
	 * 
	 * @apiSuccess {JSONArray} funcs 功能列表
	 * @apiSuccess {String} funcs.name 功能名称
	 * @apiSuccess {String} funcs.id 功能ID
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * {
			"total": 6,
			"current": 1,
			"pages": 2,
			"size": 5,
			"rows": [{
				"name": "a",
				"id": "1",
				"version": 1
			},
			{
				"name": "x2",
				"id": "x2",
				"version": 1
			}]
		}
	 * @param parm
	 * @return
	 */
	@PostMapping("/list")
	@ResponseBody
	public Object getAllFunc(@RequestBody JSONObject parm){
		SysFuncgpVO funcgpvo = parm.toJavaObject(SysFuncgpVO.class);
		String funcgpName = funcgpvo.getName();
		funcgpvo.setName(null);
		RsEntityWrapper<SysFuncgpVO> ew = new RsEntityWrapper<>(funcgpvo);
		if(!StringUtils.isBlank(funcgpName)){
			ew.like("FUNCGP_NAME", funcgpName.trim());
		}
		return getSuccessResult(generalMapper.selectByPageOrder(ew));
	}
	
	
	/**
	 * @api {GET} /admin/func/column/{menuId} 获取功能组下所有表字段
	 * @apiGroup Admin/Func Manager
	 * 
	 * @apiParam {String} menuId 菜单ID
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * 					{json example}
	 * 
	 * @apiSuccess {JSONArray} rows 表字段信息
	 * @apiSuccess {String} rows.id 表字段ID
	 * @apiSuccess {String} rows.name 表字段名称
	 * @apiSuccess {String} rows.propertyName 属性名
	 * @apiSuccess {String} rows.columnName 列名
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * {
		  "total": 3,
		  "rows": [
		    {
		      "id": "Stu_name",
		      "name": "员工姓名",
		      "propertyName": null,
		      "columnName": "Stu_name"
		    },
		    {
		      "id": "Stu_age",
		      "name": "年龄",
		      "propertyName": null,
		      "columnName": "Stu_age"
		    },
		    {
		      "id": "Stu_cjr",
		      "name": "创建人",
		      "propertyName": null,
		      "columnName": "Stu_cjr"
		    }
		  ]
		}
	 * @param parm
	 * @return
	 */
	@GetMapping("/column/{menuId}")
	@ResponseBody
	public Object getColumnByMenuId(@PathVariable("menuId") String menuId){
		List<FuncEntry> funcentry = funcapiService.getColumnsByMenuId(menuId);
		return getSuccessResult(funcentry);
	}
	
	/**
	 * @api {GET} /admin/func/button/{menuId} 获取菜单的按钮 
	 * @apiGroup Admin/Func Manager
	 * 
	 * @apiParam {String} menuId 菜单ID
	 * 
	 * 
	 * @apiSuccess {JSONArray} rows 按钮
	 * @apiSuccess {String} rows.id 按钮ID
	 * @apiSuccess {String} rows.name 按钮名称
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * {
		  "total": 3,
		  "rows": [
		    {
		      "id": "add",
		      "name": "add",
		      "propertyName": null,
		      "columnName": null
		    },
		    {
		      "id": "edit",
		      "name": "edit",
		      "propertyName": null,
		      "columnName": null
		    },
		    {
		      "id": "delete",
		      "name": "delete",
		      "propertyName": null,
		      "columnName": null
		    }
		  ]
		}
	 * @param parm
	 * @return
	 */
	@GetMapping("/button/{menuId}")
	public Object getButtonByFuncGroupId(@PathVariable("menuId") String menuId){
		List<FuncEntry> funcEntryList = funcapiService.getButtonsByMenuId(menuId);
		return getSuccessResult(funcEntryList);
	}
}
