package com.rs.devplatform.controller.manager;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.common.BuzException;
import com.rs.devplatform.conf.db.DynamicDataSource;
import com.rs.devplatform.controller.admin.ProjectController;
import com.rs.devplatform.persistent.SysPj;
import com.rs.devplatform.persistent.mapper.CGMapper;
import com.rs.devplatform.service.admin.ProjectService;
import com.rs.devplatform.service.manager.ManagerService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.entity.persistent.SysUsers;

@Controller
@RequestMapping("/manager")
public class ManagerController extends BaseController {
	
	private static final String USER_TYPE = "userType";
	private static final String USERS = "users";
	
	@Autowired
	RsGeneralMapper generalMapper;
	@Autowired
	CGMapper cgMapper;

	@Autowired
	ProjectService pjService;
	
	@Autowired
	ManagerService mgnService;
	
	@Autowired
	ProjectController pjController;
	
	/**
	 * @api {POST} /manager/projects 查询当前用户的项目列表
	 * @apiDescription 查询当前用户所分配的项目列表
	 * @apiGroup Project Manager
	 * @apiPermission Project Manager
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * 					{"预留":"查询过滤"}
	 * @apiSuccess {JSONArray} records 返回项目Records
	 * @apiSuccess {String} records.name 项目名
	 * @apiSuccess {String} records.cjr 创建人
	 * @apiSuccess {String} records.whr 维护人
	 * @apiSuccess {String} records.cjsj 创建时间
	 * @apiSuccess {String} records.whsj 维护时间
	 * @apiSuccessExample {json} Success-Response:
	 * HTTP/1.1 200 OK 
	 * {"records":[
		 * {"cjsj":1478156290000,"name":"ju-pj-432","cjr":"system","id":"7d22f1be89f34f6b8ed8f7709c91bc08"},
		 * {"cjsj":1478153396000,"name":"ju-pj--771","cjr":"system","id":"1505dc9adb5541e5b641f0a09affe8e8"}
		 * ],
	 * "total":2}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/projects")
	@ResponseBody
	public Object projectList(@RequestBody JSONObject parm, HttpServletRequest req) {
		String currentProjectId = getCurrentProjectId(req);
		DynamicDataSource.setDs4Project(null);//特殊api从default datasource
		List<SysPj> projectList = pjService.getPjList4User(getUserInfo(req));
		DynamicDataSource.setDs4Project(currentProjectId);
		JSONObject result = new JSONObject();
		if(projectList != null){
			Object[] arr = projectList.stream().map((x)->{
				JSONObject project = new JSONObject();
				project.put(ID, x.getId());
				project.put(NAME, x.getName());
				project.put("cjr", x.getCjr());
				project.put("cjsj", x.getCjsj());
				project.put("whr", x.getWhr());
				project.put("whsj", x.getWhsj());
				project.put(VERSION, x.getVersion());
				return project;
			}).toArray();
			result.put(RECORDS, arr);
			result.put(TOTAL, projectList.size());
		}else{
			result.put(RECORDS, new JSONArray(0));
			result.put(TOTAL, 0);
		}
		return getSuccessResult(result);
	}
	
	
	/**
	 * @api {PUT} /manager/project/{pjId} 更新项目
	 * @apiGroup Project Manager
	 * 
	 * @apiParam {String} name 项目name
	 * @apiParam {Integer} version 项目version
	 * @apiParam {JSONObject} db 项目db(不是必须的)
	 * @apiParam {String} db.dbType 项目对应数据库类型：oracle,mysql
	 * @apiParam {String} db.dbUrl 项目对应数据库地址
	 * @apiParam {String} db.dbUser 项目对应数据库用户名
	 * @apiParam {String} db.dbPwd 项目对应数据库密码
	 * @apiParamExample {json} Request-Example: 
	 * 					{"id":"123456", "name":"只更新项目名称，不修改db资源", "version":1}
	 * 					{"id":"123456", "name":"更新项目名和db资源", "version":1, "db":{"dbType":"mysql", "dbUrl":"192.168.10.64:3306/test_db1", "dbUser":"root", "dbPwd":"123456"}}
	 * 
	 * @apiSuccess {String} id 项目id
	 * @apiSuccess {Integer} version 项目version
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{"id":"12345", "version":2}
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
	@RequestMapping(value="/project/{pjId}", method=RequestMethod.PUT)
	@ResponseBody
	public Object projectEdit(@PathVariable("pjId") String pjId, @RequestBody JSONObject parm, HttpServletRequest req){
		return pjController.projectEdit(pjId, parm, req);
	}
	
	/**
	 * @api {GET} /manager/project/{pjId}/users 查询项目分配的人员
	 * @apiGroup Project Manager
	 * @apiPermission Project Manager
	 * 
	 * @apiParam {String} userType 项目人员Type(PM:Project Manager/TM:Team Member/Empty-All)
	 * @apiSampleRequest /manager/project/da2f0108587c476e9f3cf4f8466c2779/users
	 * @apiSampleRequest /manager/project/da2f0108587c476e9f3cf4f8466c2779/users?userType=PM
	 *  
	 * @apiSuccess {JSONArray} records 返回项目Records
	 * @apiSuccess {String} records.id 项目名
	 * @apiSuccess {String} records.name 项目version
	 * @apiSuccessExample {json} Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 			{
			    "records": [
			        {
			            "name": "test-77",
			            "id": "0f4934679ab24a1a80316a801f56e1b8",
			            "userType": "PM"
			        },
			        {
			            "name": "test-198",
			            "id": "088b1c492e584cda89950de98965fdbb",
			            "userType": "TM"
			        }
			    ],
			    "total": 2
			}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/project/{pjId}/users", method=RequestMethod.GET)
	@ResponseBody
	public Object assignManager2Pj(@PathVariable("pjId") String pjId, HttpServletRequest req) {
		if(!StringUtils.isEmpty(pjId)){
			String userType = req.getParameter(USER_TYPE);
			if(!Constants.UserType.isValidUserType(userType)){
				userType = null;
			}
			List<SysUsers> userList = mgnService.getPjMembers(pjId, userType, null);
			JSONObject result = new JSONObject();
			if(userList!=null && !userList.isEmpty()){
				Object[] arr = userList.stream().map((x)->{
					JSONObject user = new JSONObject();
					user.put(ID, x.getId());
					user.put(NAME, x.getName());
					user.put(USER_TYPE, x.getUserType());
					return user;
				}).toArray();
				result.put(RECORDS, arr);
				result.put(TOTAL, userList.size());
			}else{
				result.put(RECORDS, new JSONArray(0));
				result.put(TOTAL, 0);
			}
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {POST} /manager/project/{pjId}/users 增加/修改/删除项目人员
	 * @apiGroup Project Manager
	 * @apiPermission Project Manager
	 * 
	 * @apiParam {String} id 项目ID
	 * @apiParam {JSONArray} users 新增的项目人员数组
	 * @apiParam {String} users.id 项目人员ID
	 * @apiParam {String} users.userType 项目人员类型,PM:Project Manager, TM:Team Member, ""：为空表示该人员不是项目成员/或者不传（以前是成员就删除）
	 * @apiParamExample {json} Request-Example: 
	 * 		{
	 * 		"id":"123", "users":[{"id":"u1", "userType":"PM"},{"id":"u2", "userType":"TM"},{"id":"u3", "userType":""}]
	 * 		}
	 * @apiSuccessExample {json} Success-Response:
	 *  	HTTP/1.1 200 OK
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/project/{pjId}/users",method=RequestMethod.POST)
	@ResponseBody
	public Object addUser4Project(@PathVariable("pjId") String pjId, @RequestBody JSONObject parm, HttpServletRequest req) {
		JSONArray users = parm.getJSONArray(USERS);
		if(users!=null && !StringUtils.isEmpty(pjId)){
			boolean isvalid = true;
			for(int i=0;i<users.size();++i){
				JSONObject user = users.getJSONObject(i);
				if(StringUtils.isEmpty(user.getString(ID)) || StringUtils.isEmpty(user.getString(USER_TYPE))){
					isvalid = false;
					break;
				}
			}
			if(isvalid){
				mgnService.addUpdateOrDeletePjMembers(pjId, users);
				return getSuccessResult();
			}else{
				logger.info("addUser4Project(): user.id or userType is empty");
				return getInvalidParamResult();
			}
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {GET} /manager/project/{pjId}/test 测试项目切换api
	 * @apiGroup Project Manager
	 * @apiPermission Project Manager
	 * 
	 * @apiParam {String} id 项目ID
	 * @apiParamExample {json} Request-Example: 
	 * GET /manager/project/{pjId}/test
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{
	 * 		"pjId":"1234“
	 * 		}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/project/{pjId}/test",method=RequestMethod.GET)
	@ResponseBody
	public Object switchProjectTest(@PathVariable("pjId") String pjId){
		DynamicDataSource.setDs4Project(pjId);
		JSONObject result = new JSONObject();
		result.put("pjId", pjId);
		result.put("records", cgMapper.testSwitchDB());
		//TODO: should store pjId in session
		return getSuccessResult(result);
	}
	
	/**
	 * TODO: 2016-12-2
	 * 场景：
	 * 开发登录系统后，需要选择打开哪一个项目，这时是需要调用该API来做一次切换（session中的值）
	 * 
	 * @param pjId
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/project/{pjId}/switch",method=RequestMethod.GET)
	@ResponseBody
	public Object switchProject(@PathVariable("pjId") String pjId, HttpSession session){
		session.setAttribute(Constants.PJID, pjId);
		return getSuccessResult();
	}
	
	
	/**
	 * @api {POST} /manager/project/{pjId}/init 初始化新项目的基础表
	 * @apiDescription 暂只支持项目数据库第一次新建同步("sys_bxx", "sys_zdxx", "sys_cdtype", "sys_cdval", "sys_custbt", "sys_menus").TODO:@20161205
	 * @apiGroup Project Manager
	 * @apiPermission Project Manager
	 * 
	 * @apiParam {String} pjId 项目ID
	 * @apiParam {Integer} version 项目版本
	 * @apiParamExample {json} Request-Example: 
	 * POST /manager/project/abc1234/init
	 * 		{"version":1}
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{
	 * 		"version":2
	 * 		}
	 * @param pjId
	 * @param parm {"version":1}
	 * @return
	 * @throws BuzException
	 */
	@RequestMapping(value="/project/{pjId}/init",method=RequestMethod.POST)
	@ResponseBody
	public Object initProjectDB(@PathVariable("pjId") String pjId, @RequestBody JSONObject parm) throws BuzException{
		Integer version = parm.getInteger(VERSION);
		if(version==null){
			return getInvalidParamResult();
		}
		if(mgnService.initProjectDB(pjId, version)){
			JSONObject result = new JSONObject();
			result.put(VERSION, version+1);
			return getSuccessResult(result);
		}else{
			return getModifiedResult();
		}
		
	}
	
}
