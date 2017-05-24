package com.rs.devplatform.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.service.admin.RoleService;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.entity.persistent.SysRoles;
import com.rs.framework.common.entity.persistent.SysU2r;
import com.rs.framework.common.entity.persistent.SysUsers;
import com.rs.framework.common.entity.vo.SysRoleVO;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {
	
	@Autowired
	RoleService roleService;
	
	/**
	 * @api {GET} /admin/role 转到角色管理页面
	 * @apiGroup Admin/Role Manager
	 * @apiSuccess {String} html 对应角色管理页面 
	 * @apiSuccessExample {json} 页面命名:
	 * 			{"页面":"manage/roles.html"}
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index() {
		return "manage/roles";
	}
	
	/**
	 * @api {POST} /admin/role/list 获取角色列表
	 * @apiGroup Admin/Role Manager
	 * @apiParamExample {json} Request-Example: 
	 * 		{
			    "current": 1,
			    "size": 10,
			    "comments": "easyUI分页参数为page/rows, form提交方式server端做了转换，自动mapping到current/size",
			    "name":"roleName模糊匹配"
			}
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess rows.name 角色名
	 * @apiSuccess rows.id	角色ID
	 * @apiSuccess rows.version	角色version
	 *  @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{"rows":[
	 * 					{"name":"dept50","id":"271123425f994616ae1b7edaceb70070","version":1},
	 * 					{"name":"dept1","id":"a98f0e3d5fd84c059b364a9bf0d0c587","version":2}
	 * 				],
	 * 		"total":"2"}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Object roleList(@RequestBody JSONObject parm) {
		SysRoleVO roleParm = JSONObject.toJavaObject(parm, SysRoleVO.class);
		String roleName = roleParm.getName();
		RsEntityWrapper<SysRoleVO> ew = new RsEntityWrapper<>();
		roleParm.setName(null);
		if(!StringUtils.isBlank(roleName)){
			ew.like("ROLES_NAME", roleName.trim());
		}
		ew.setEntity(roleParm);
		return getSuccessResult(generalMapper.selectByPageOrder(ew));
	}
	
	/**
	 * @api {GET} /admin/role/list 获取所有角色（不分页） 
	 * @apiGroup Admin/Role Manager
	 * 
	 * @apiSuccess {String} id 角色ID
	 * @apiSuccess {String} name 角色名称
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * {
		  "total": 18,
		  "rows": [
		    {
		      "id": "0f07cc04b62f419da0a13b0301856896",
		      "name": "销售业务员",
		      "version": 1
		    },
		    {
		      "id": "22149f1cee43432abae40fd48298964e",
		      "name": "HR",
		      "version": 1
		    }]
		}
	 * @param
	 * @return
	 */
	@GetMapping("/list")
	@ResponseBody
	public Object roleAllList(){
		SysRoles role = new SysRoles();
		role.setDeleted("F");
		List<SysRoles> roleList = generalMapper.selectList(role);
		return getSuccessResult(roleList);
	}
	
	/**
	 * @api {POST} /admin/role 增加角色
	 * @apiGroup Admin/Role Manager
	 * 
	 * @apiParam {String} name 角色名
	 * @apiParamExample {json} Request-Example: 
	 * 					{"name":"销售部"}
	 * 
	 * @apiSuccess {String} id 返回角色id
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{"id":"0f077145cc894f7990387c9458091e1b"}
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
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Object roleAdd(@RequestBody JSONObject parm){
		SysRoles role = JSONObject.toJavaObject(parm, SysRoles.class);
		if(validateData(role)){
			SysRoles roleParm = new SysRoles();
			if(role.getName().equalsIgnoreCase("admin")){
				return getInvalidParamResult();
			}
			roleParm.setName(role.getName().trim());
			if(isDuplicateBeforeCreate(roleParm)){
				return getDuplicateResult();
			}
			roleService.addRole(role);
			JSONObject result = new JSONObject(); 
			result.put("id", role.getId());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {PUT} /admin/role/{roleId} 更新角色
	 * @apiGroup Admin/Role Manager
	 * 
	 * @apiParam {String} roleId 角色ID
	 * @apiParam {String} name 角色名
	 * @apiParamExample {json} Request-Example: 
	 * 					{"name":"销售一部"}
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
	@RequestMapping(value="/{roleId}", method=RequestMethod.PUT)
	@ResponseBody
	public Object roleEdit(@PathVariable("roleId") String roleId, @RequestBody JSONObject parm){
		SysRoles role = JSONObject.toJavaObject(parm, SysRoles.class);
		role.setId(roleId);
		if(!StringUtils.isEmpty(roleId) && validateData(role)){
			SysRoles roleParm = new SysRoles();
			String roleName = role.getName();
			roleParm.setName(roleName);
			roleParm.setId(roleId);
			if(isDuplicateBeforeUpdate(roleParm)){
				return getDuplicateResult();
			}
			SysRoles roleParam = generalMapper.selectById(roleId, SysRoles.class);
			//can't edit admin
			if(roleParam.getName().equalsIgnoreCase("admin") && !role.getName().equalsIgnoreCase("admin")){
				return getInvalidParamResult();
			}
			if(roleService.updateRole(role))
				return getSuccessResult();
			else
				return getModifiedResult();
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {DELETE} /admin/role 删除角色(单个和批量)
	 * @apiGroup Admin/Role Manager
	 * 
	 * @apiParam {JsonArray} _ 角色ID
	 * @apiParamExample {json} Request-Example: 
	 * 					["1","2","3"]
	 * 
	 *  @apiSuccessExample {json} Success-Response:
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
	@RequestMapping(value="", method=RequestMethod.DELETE)
	@ResponseBody
	public Object roleDelete(@RequestBody JSONArray parm){
		if(!parm.isEmpty()){
			int size = parm.size();
			List<String> roleIds = new ArrayList<String>(size);
			for(int i=0;i<size;++i){
				roleIds.add(parm.getString(i));
			}
			
			SysU2r u2r = new SysU2r();
			RsEntityWrapper<SysU2r> u2rwrapper = new RsEntityWrapper<>(u2r);
			u2rwrapper.in("u2r_roleid", roleIds);
			if(generalMapper.selectCountByEW(u2rwrapper) > 0) {
				addValidationError("角色仍然有绑定的用户，无法删除！");
				return getInvalidParamResult();
			}
			
			SysRoles role = new SysRoles();
			role.setName("admin");
			RsEntityWrapper<SysRoles> wrapper = new RsEntityWrapper<>(role);
			wrapper.in("ROLES_ID", roleIds);
			List<SysRoles> roleList = generalMapper.selectList(wrapper);
			
			if(roleList == null || roleList.isEmpty()){
				roleService.deleteRoles(roleIds);
				return getSuccessResult();
			}else{
				return getInvalidParamResult();
			}
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {POST} /admin/role/{roleId}/users 为角色添加用户
	 * @apiGroup Admin/Role Manager
	 * 
	 * @apiParam {String} roleId 角色ID
	 * @apiParam {JSONArray} users User ID
	 * @apiParamExample {json} Request-Example: 
	 * 					{"users":"['U1','U2','U3']"}
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
	@RequestMapping(value="/{roleId}/users", method=RequestMethod.POST)
	@ResponseBody
	public Object addUser2Role(@PathVariable("roleId") String roleId,@RequestBody JSONObject parm){
		JSONArray userIdArray = parm.getJSONArray("users");
		if(StringUtils.isEmpty(roleId) || userIdArray==null || userIdArray.isEmpty()){
			return getInvalidParamResult();
		}
		int size = userIdArray.size();
		List<String> userIdList = new ArrayList<String>(size);
		for(int i=0;i<size;++i){
			userIdList.add(userIdArray.getString(i));
		}
		roleService.addUsers2Role(userIdList, roleId);
		return getSuccessResult();
	}
	
	/**
	 * @api {GET} /admin/role/{roleId}/userlist 获取角色的用户
	 * @apiGroup Admin/Role Manager
	 * 
	 * @apiParam {String} roleId 角色ID
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess rows.name 角色名
	 * @apiSuccess rows.id 角色ID
	 *  @apiSuccessExample {json} Success-Response:
	 *  	HTTP/1.1 200 OK
	 * 		{"rows":[
	 * 				{"name":"test923","id":"b0e8c62b236a4cb6b121f165c6915f44"},
	 * 				{"name":"test-791","id":"6a6dd2c8360648b9acbb619bcf45c8f0"},
	 * 				{"name":"test407","id":"9c5f0af9d3554cc3bb2905200fa1c393"}],
	 * 		"total": 3}
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/{roleId}/userlist", method=RequestMethod.GET)
	@ResponseBody
	public Object getUser2Role(@PathVariable("roleId") String roleId){
		if(StringUtils.isEmpty(roleId)){
			return getInvalidParamResult();
		}
		JSONObject result = new JSONObject();
		List<SysUsers> userList = roleService.getUsers2Role(roleId);
		if(userList != null){
			Object[] arr = userList.stream().map((x)->{
				JSONObject user = new JSONObject();
				user.put("id", x.getId());
				user.put("name", x.getName());
				return user;
			}).toArray();
			result.put(RECORDS, arr);
			result.put(TOTAL, userList.size());
		}else{
			result.put(RECORDS, new JSONArray(0) );
			result.put(TOTAL, 0);
		}
		return getSuccessResult(result);
	}
	
	/**
	 * @api {DELETE} /admin/role/{roleId}/users 为角色删除用户
	 * @apiGroup Admin/Role Manager
	 * 
	 * @apiParam {String} roleId 角色ID
	 * @apiParam {JSONArray} users User ID
	 * @apiParamExample {json} Request-Example: 
	 * 					{"users":"['U1','U2','U3']"}
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/{roleId}/users", method=RequestMethod.DELETE)
	@ResponseBody
	public Object deleteUser2Role(@PathVariable("roleId") String roleId, @RequestBody JSONObject parm){
		JSONArray userIdArray = parm.getJSONArray("users");
		if(StringUtils.isEmpty(roleId) || userIdArray==null || userIdArray.isEmpty()){
			return getInvalidParamResult();
		}
		int size = userIdArray.size();
		List<String> userIdList = new ArrayList<String>(size);
		for(int i=0;i<size;++i){
			userIdList.add(userIdArray.getString(i));
		}
		roleService.deleteUsers2Role(userIdList, roleId);
		return getSuccessResult();
	}
	
	
}
