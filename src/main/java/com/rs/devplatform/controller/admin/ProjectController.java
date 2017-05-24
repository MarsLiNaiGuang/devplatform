package com.rs.devplatform.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.persistent.SysPj;
import com.rs.devplatform.persistent.SysPj2res;
import com.rs.devplatform.service.admin.ProjectService;
import com.rs.devplatform.vo.PjDBInfoVO;
import com.rs.devplatform.vo.ProjectInfo;
import com.rs.devplatform.vo.SysPjVO;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.entity.persistent.SysUsers;

@Controller
@RequestMapping("/admin/project")
public class ProjectController extends BaseController {
	
	@Autowired
	ProjectService pjService;
	
	/**
	 * @api {GET} /admin/project 转到项目管理页面
	 * @apiGroup Admin/Project Manager
	 * @apiSuccess {String} html 对应项目管理页面 
	 * @apiSuccessExample {json} 页面命名:
	 * 			{"页面":"manage/projects.html"}
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index() {
		return "manage/projects";
	}
	
	/**
	 * @api {POST} /admin/project/list 获取项目列表
	 * @apiGroup Admin/Project Manager
	 * @apiParamExample {json} Request-Example: 
	 * 		{
			    "current": 1,
			    "size": 10,
			    "comments": "easyUI分页参数为page/rows, form提交方式server端做了转换，自动mapping到current/size",
			    "name":"projectName模糊匹配"
			}
	 * @apiSuccess {JSONArray} records 返回项目Records
	 * @apiSuccess {String} records.name 项目名
	 * @apiSuccess {String} records.init 是否初始化标记(Y : 表示已经初始化过，N : 没有初始化)
	 * @apiSuccess {String} records.version 项目version
	 * @apiSuccess {String} records.cjr 创建人
	 * @apiSuccess {String} records.whr 维护人
	 * @apiSuccessExample {json} Success-Response:
	 *			HTTP/1.1 200 OK
	 * 			{
					"total": 42,
					"current": 1,
					"pages": 9,
					"size": 5,
					"rows": [{
						"whr": "aaa",
						"init": "N",
						"name": "ju-pj--152",
						"cjr": "system",
						"id": "04a01896f8124937bd01224359ffb7fa",
						"version": 1
					},
					{
						"whr": "aaa",
						"init": "N",
						"name": "ju-pj--118",
						"cjr": "system",
						"id": "1795fbba854e44dd8873bc2911a3e684",
						"version": 1
					}]
				}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object projectList(@RequestBody JSONObject parm) {
		SysPjVO parmPj = parm.toJavaObject(SysPjVO.class);
		parmPj.setDeleted(Constants.DelInd.FALSE);
		RsEntityWrapper<SysPjVO> ew = new RsEntityWrapper<>();
		String pjName = parmPj.getName();
		parmPj.setName(null);
		if(!StringUtils.isBlank(pjName)){
			ew.like("PJS_NAME", pjName.trim());
		}
		ew.setEntity(parmPj);
		return getSuccessResult(generalMapper.selectByPageOrder(ew));
	}
	
	/**
	 * @api {POST} /admin/project 增加项目
	 * @apiGroup Admin/Project Manager
	 * 
	 * @apiParam {String} name 项目名
	 * @apiParam {JSONObject} db 项目db(不是必须的)
	 * @apiParam {String} db.dbType 项目对应数据库类型：oracle,mysql
	 * @apiParam {String} db.dbUrl 项目对应数据库地址
	 * @apiParam {String} db.dbUser 项目对应数据库用户名
	 * @apiParam {String} db.dbPwd 项目对应数据库密码
	 * @apiParamExample {json} Request-Example: 
	 * 					{"name":"测试项目1：只建项目，不建DB资源"}
	 * 					{"name":"测试项目2","db":{"dbPwd":"123456","dbUser":"root","dbType":"mysql","dbUrl":"192.168.10.64:3306/test_db930"}}
	 * 					{"name":"测试项目3","db":{"dbPwd":"123456","dbUser":"root","dbType":"oracle","dbUrl":"192.168.10.64:1521/test_db930"}}
	 * 
	 * @apiSuccess {String} id 返回项目id
	 * @apiSuccess {Integer} version 返回项目version
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
	public Object projectAdd(@RequestBody JSONObject parm, HttpServletRequest req){
		ProjectInfo projectInfo = JSONObject.toJavaObject(parm, ProjectInfo.class);
		if(!StringUtils.isEmpty(projectInfo.getName()) && validateData(projectInfo) ){
			SysPj parmPj = new SysPj();
			parmPj.setName(projectInfo.getName());
			parmPj.setDeleted(Constants.DelInd.FALSE);
			if(isDuplicateBeforeCreate(parmPj)){
				return getDuplicateResult();
			}
			SysPj project = pjService.createProject(projectInfo, getUserInfo(req));
			JSONObject result = new JSONObject();
			result.put(ID, project.getId());
			result.put(NAME, project.getName());
			result.put(VERSION, project.getVersion());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {PUT} /admin/project/{pjId} 更新项目
	 * @apiGroup Admin/Project Manager
	 * 
	 * @apiParam {String} pjId 项目ID
	 * @apiParam {String} name 项目name
	 * @apiParam {Integer} version 项目version
	 * @apiParam {JSONObject} db 项目db(不是必须的)
	 * @apiParam {String} db.dbType 项目对应数据库类型：oracle,mysql
	 * @apiParam {String} db.dbUrl 项目对应数据库地址
	 * @apiParam {String} db.dbUser 项目对应数据库用户名
	 * @apiParam {String} db.dbPwd 项目对应数据库密码
	 * @apiParamExample {json} Request-Example: 
	 * 					{ "name":"只更新项目名称，不修改db资源", "version":1}
	 * 					{ "name":"更新项目名和db资源", "version":1, "db":{"dbType":"mysql", "dbUrl":"192.168.10.64:3306/test_db1", "dbUser":"root", "dbPwd":"123456"}}
	 * 
	 * @apiSuccess {Integer} version 项目version
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *		HTTP/1.1 200 OK
	 * 		{"id":"12345", "version":2}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict

	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{pjId}", method=RequestMethod.PUT)
	@ResponseBody
	public Object projectEdit(@PathVariable("pjId") String pjId,@RequestBody JSONObject parm, HttpServletRequest req){
		ProjectInfo projectInfo = JSONObject.toJavaObject(parm, ProjectInfo.class);
		projectInfo.setId(pjId);
		if(!StringUtils.isEmpty(projectInfo.getId()) && validateData(projectInfo) && projectInfo.getVersion()!=null){
			SysPj pjParm = new SysPj();
			pjParm.setName(projectInfo.getName());
			pjParm.setId(projectInfo.getId());
			pjParm.setVersion(projectInfo.getVersion());
			if(isDuplicateBeforeUpdate(pjParm)){
				return getDuplicateResult();
			}
			if(pjService.updateProject(projectInfo, getUserInfo(req))){
				JSONObject result =  new JSONObject();
				result.put(VERSION, projectInfo.getVersion());
				return getSuccessResult(result);
			}else{
				return getModifiedResult();
			}
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {POST} /admin/project/testdb 测试数据库连接
	 * @apiGroup Admin/Project Manager
	 * 
	 * @apiParam {String} dbType 项目对应数据库类型：oracle,mysql
	 * @apiParam {String} dbUrl 项目对应数据库地址: 192.168.10.64:3306/test_db-446
	 * @apiParam {String} dbUser 项目对应数据库用户名(不能为空)
	 * @apiParam {String} dbPwd 项目对应数据库密码（不能为null，没有密码就传空string“”，否则校验不通过）
	 * @apiParamExample {json} Request-Example: 
	 * 				{"dbType":"mysql", "dbUrl":"192.168.10.64:3306/test_db1", "dbUser":"root", "dbPwd":""}
	 * 				{"dbType":"mysql", "dbUrl":"192.168.10.64:3306/test_db1", "dbUser":"root", "dbPwd":"123456"}
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *		HTTP/1.1 200 OK - 测试连接通过
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request - 测试连接不通过
	 * @param parm
	 * @return
	 */
	@PostMapping("/testdb")
	@ResponseBody
	public Object testdb(@RequestBody JSONObject parm){
		PjDBInfoVO db = JSONObject.toJavaObject(parm, PjDBInfoVO.class);
		if(validateData(db)){
			if(pjService.testdb(db)){
				return getSuccessResult();
			}else{
				return getInvalidParamResult();
			}
		}else{
			return getInvalidParamResult();
		}
		
	}
	
	/**
	 * @api {DELETE} /admin/project/{pjId} 删除项目
	 * @apiGroup Admin/Project Manager
	 * 
	 * @apiParam {String} id 项目ID
	 * @apiParam {Integer} version 项目version
	 * @apiParamExample {json} Request-Example: 
	 * 					{"version":1}
	 * 
	 *  @apiSuccessExample {json} Success-Response:
	 * 			HTTP/1.1 200 OK
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{pjId}", method=RequestMethod.DELETE)
	@ResponseBody
	public Object projectDelete(@PathVariable("pjId") String pjId, @RequestBody JSONObject parm, HttpServletRequest req){
		Integer version = parm.getInteger(VERSION);
		if(!StringUtils.isEmpty(pjId) && version!=null){
			if(pjService.deleteProject(pjId, getUserInfo(req), version)){
				return getSuccessResult();
			}else{
				return getModifiedResult();
			}
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {GET} /admin/project/{pjId} 查询项目详情
	 * @apiGroup Admin/Project Manager
	 * 
	 * @apiParam {String} id 项目ID
	 * @apiParamExample {json} Request-Example: 
	 * GET /admin/project/abc
	 * 
	 * @apiSuccess {String} id 返回项目id
	 * @apiSuccess {String} name 返回项目name
	 * @apiSuccess {Integer} version 返回项目version
	 * @apiSuccess {String} init 返回项目是否已经初始化（Y:表示已经初始化，其他表示未初始化）
	 * @apiSuccess {JSONArray} resList 返回项目资源
	 * @apiSuccess {String} resList.dbType 项目资源dbType
	 * @apiSuccess {String} resList.dbUser 项目资源dbUser
	 * @apiSuccess {String} resList.dbPwd 项目资源dbPwd
	 * @apiSuccess {String} resList.dbUrl 项目资源dbUrl
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 * HTTP/1.1 200 OK
	 * {
			"init": "Y",
			"resList": [{
				"pjId": "a405ce0f3d934caea84ed190a7eededb",
				"dbPwd": "",
				"dbUser": "root",
				"dbType": "mysql",
				"id": "c12e146c8f1949a3b444adf89ecb63d1",
				"version": 2,
				"dbUrl": "127.0.0.1:3306/rs_test"
			}],
			"name": "ju-pj--21",
			"id": "a405ce0f3d934caea84ed190a7eededb",
			"version": 6
		}
	 * @param pjId
	 * @return
	 */
	@RequestMapping(value="/{pjId}", method=RequestMethod.GET)
	@ResponseBody
	public Object pjdetail(@PathVariable("pjId") String pjId){
		SysPj pj = generalMapper.selectById(pjId, SysPj.class);
		if(pj!=null){
			SysPjVO pjVO = new SysPjVO();
			BeanUtils.copyProperties(pj, pjVO);
			SysPj2res res = new SysPj2res();
			res.setPjId(pjId);
			res.setDeleted(Constants.DelInd.FALSE);
			pjVO.setResList(generalMapper.selectList(new RsEntityWrapper<>(res)));
			return getSuccessResult(pjVO);
		}
		return getInvalidParamResult();
	}
	
	/*************************************************************/
	
	/**
	 * @api {GET} /admin/project/{pjId}/managers 获取项目分配的managers
	 * @apiGroup Admin/Project Manager
	 * 
	 * @apiParam {String} pjId 项目ID
	 * @apiParamExample {json} Request-Example: 
	 * 					{"预留":"查询过滤"}
	 * @apiSuccess {JSONArray} records 返回项目Records
	 * @apiSuccess {String} records.id 项目ManagerID
	 * @apiSuccess {String} records.name 项目ManagerName
	 * @apiSuccessExample {json} Success-Response:
	 * 		{
	 * 			"records":[
	 * 				{"name":"test-77","id":"0f4934679ab24a1a80316a801f56e1b8"},
	 * 				{"name":"test-198","id":"088b1c492e584cda89950de98965fdbb"}
	 * 			],
	 * 			"total":2
	 * 		}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{pjId}/managers", method=RequestMethod.GET)
	@ResponseBody
	public Object getAssignManager2Pj(@PathVariable("pjId") String pjId, HttpServletRequest req) {
		if(!StringUtils.isEmpty(pjId)){
			List<SysUsers> userList = pjService.getAssignedPM4Pj(pjId);
			JSONObject result = new JSONObject();
			if(userList!=null){
				Object[] arr = userList.stream().map((x)->{
					JSONObject user = new JSONObject();
					user.put(ID, x.getId());
					user.put(NAME, x.getName());
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
	 * @api {POST} /admin/project/{pjId}/managers 项目新增managers
	 * @apiGroup Admin/Project Manager
	 * 
	 * @apiParam {String} pjId 项目ID
	 * @apiParam {JSONArray} managers 项目manager ids
	 * @apiParam {String} managers._ 项目manager id
	 * @apiParamExample {json} Request-Example: 
	 * 		{
	 * 			"managers":[ "m1","m2"]
	 * 		}
	 * @apiSuccessExample {json} Success-Response:
	 * 	HTTP/1.1 200 OK
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{pjId}/managers", method=RequestMethod.POST)
	@ResponseBody
	public Object assignManager2Pj(@PathVariable("pjId") String pjId,@RequestBody JSONObject parm, HttpServletRequest req) {
		System.err.println("===============parm="+parm.toJSONString());
//		ProjectInfo projectInfo = JSONObject.toJavaObject(parm, ProjectInfo.class);
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setId(pjId);
		projectInfo.setManagers(parm.getJSONArray("managers"));
		JSONArray managers = projectInfo.getManagers();
		if(!StringUtils.isEmpty(pjId) && managers!=null && !managers.isEmpty()){
			pjService.assignManager2Pj(projectInfo, getUserInfo(req), Constants.OptTypes.ADD);
			return getSuccessResult();
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {DELETE} /admin/project/{pjId}/managers 项目删除managers
	 * @apiGroup Admin/Project Manager
	 * 
	 * @apiParam {String} pjId 项目ID
	 * @apiParam {JSONArray} managers 项目manager ids
	 * @apiParam {String} managers._ 项目manager id
	 * @apiParamExample {json} Request-Example: 
	 * 		{
	 * 			"managers":[ "m1","m2"]
	 * 		}
	 * @apiSuccessExample {json} Success-Response:
	 * 			HTTP/1.1 200 OK
	 * 			{"id":"12345", "version":2}
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
	@RequestMapping(value="/{pjId}/managers", method=RequestMethod.DELETE)
	@ResponseBody
	public Object deleteManager2Pj(@PathVariable("pjId") String pjId, @RequestBody JSONObject parm, HttpServletRequest req) {
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setId(pjId);
		projectInfo.setManagers(parm.getJSONArray("managers"));
		JSONArray managers = projectInfo.getManagers();
		if(!StringUtils.isEmpty(pjId) && managers!=null && !managers.isEmpty()){
			pjService.assignManager2Pj(projectInfo, getUserInfo(req), Constants.OptTypes.DELETE);
			return getSuccessResult();
		}else{
			return getInvalidParamResult();
		}
	}
	
	
}
