package com.rs.devplatform.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.service.admin.UserService;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.entity.persistent.SysUsers;
import com.rs.framework.common.entity.vo.SysUserVO;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {
	
	@Autowired
	UserService userService;
	
	/**
	 * @api {GET} /admin/user 转到用户管理页面
	 * @apiGroup Admin/User Manager
	 * @apiSuccess {String} html 对应用户管理页面 
	 * @apiSuccessExample {json} 页面命名:
	 * 			{"页面":"manage/user.html"}
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index() {
		return "manage/users";
	}
	
	/**
	 * @api {POST} /admin/user/list 获取用户列表
	 * @apiGroup Admin/User Manager
	 * @apiParamExample {json} Request-Example: 
	 * 		{
			    "current": 1,
			    "size": 10,
			    "comments": "easyUI分页参数为page/rows, form提交方式server端做了转换，自动mapping到current/size",
			    "name":"用户名模糊匹配",
			    "nkname":"昵称模糊匹配",
			}
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} records.name 用户名
	 * @apiSuccess {String} records.nkname 昵称
	 * @apiSuccess {String} records.tel 电话号码
	 * @apiSuccess {String} records.id 用户ID
	 * @apiSuccess {String} records.email 用户邮箱
	 *  @apiSuccessExample {json} Success-Response:
	 *  	HTTP/1.1 200 OK
	 * 		{
				"total": 1,
				"current": 1,
				"pages": 1,
				"size": 5,
				"rows": [{
					"nkname": "admin",
					"name": "admin",
					"tel": "1234546",
					"id": "123456",
					"email": "admin@123.com"
				}]
			}
	 * 
	 * @param parm
	 * @return
	 */
	@PostMapping("/list")
	@ResponseBody
	public Object userList(@RequestBody JSONObject parm) {
		SysUserVO userVO = parm.toJavaObject(SysUserVO.class);
		String name = userVO.getName();
		String nkname = userVO.getNkname();
		userVO.setName(null);
		userVO.setNkname(null);
		RsEntityWrapper<SysUserVO> ew = new RsEntityWrapper<>(userVO);
		if(!StringUtils.isBlank(name)){
			ew.like("USERS_NAME", name.trim());
		}
		if(!StringUtils.isBlank(nkname)){
			ew.like("USERS_NKNAME", nkname.trim());
		}
		return getSuccessResult(generalMapper.selectByPageOrder(ew));
	}
	
	
	/**
	 * @api {GET} /admin/user/list 获取所有用户（不分页） 
	 * @apiGroup Admin/User Manager
	 * 
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} records.name 用户名
	 * @apiSuccess {String} records.nkname 昵称
	 * @apiSuccess {String} records.tel 电话号码
	 * @apiSuccess {String} records.id 用户ID
	 * @apiSuccess {String} records.email 用户邮箱
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * {
		  "total": 2,
		  "rows": [
		    {
		      "id": "085a3347a44a44de828a74b3ee269db8",
		      "name": "tang1",
		      "nkname": "tang1",
		      "email": "abc@qq.com",
		      "tel": "",
		      "pwdConfirm": null,
		      "userType": null,
		      "icon": null,
		      "version": 1
		    },
		    {
		      "id": "0bd498b5fd274a62a2dc9484fc953511",
		      "name": "test-8877",
		      "nkname": "nk_test-8877",
		      "email": "test-8877@123.com",
		      "tel": null,
		      "pwdConfirm": null,
		      "userType": null,
		      "icon": null,
		      "version": 1
		    }]
	 * @return
	 */
	@GetMapping("/list")
	@ResponseBody
	public Object userAllList(){
		SysUsers user = new SysUsers();
		user.setDeleted("F");
		List<SysUsers> userList = generalMapper.selectList(user);
		return getSuccessResult(userList);
	}
	
	/**
	 * @api {POST} /admin/user 增加用户
	 * @apiGroup Admin/User Manager
	 * 
	 * @apiParam {String} name 用户名
	 * @apiParam {String} nkname 用户昵称（可以是中文名等）
	 * @apiParam {String} pwd 密码
	 * @apiParam {String} pwdConfirm 确认密码
	 * @apiParam {String} email 邮箱
	 * @apiParam {String} tel 电话
	 * @apiParamExample {json} Request-Example: 
	 * 					{"name":"zhangsan", "nkname":"张三", "pwd":"123456", "pwdConfirm":"123456", "email":"123@abc.com", "tel":"133133131313"}
	 * 
	 * @apiSuccess {String} id 返回用户id
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{"id":"0f077145cc894f7990387c9458091e1b"}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * 		{
				"verrors": [
				{
					"errCode": "email",
					"errorMsg": "不是一个合法的电子邮件地址"
				},
				{
					"errCode": "pwd",
					"errorMsg": "不能为空"
				},
				{
					"errCode": "pwd",
					"errorMsg": "长度需要在6和50之间"
				}
				]
			}
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
	public Object userAdd(@RequestBody JSONObject parm){
		SysUsers user = JSONObject.toJavaObject(parm, SysUsers.class);
		if(validateData(user) && user.getPwd().equals(user.getPwdConfirm())){
			SysUsers parmUser = new SysUsers();
			parmUser.setName(user.getName().trim());
			if(isDuplicateBeforeCreate(parmUser)){
				return getDuplicateResult();
			}
			userService.addUser(user);
			JSONObject result =  new JSONObject();
			result.put(ID, user.getId());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {PUT} /admin/user/{userId} 更新用户
	 * @apiGroup Admin/User Manager
	 * 
	 * @apiParam {String} id 用户ID
	 * @apiParam {String} email email
	 * @apiParam {String} tel 电话
	 * @apiParamExample {json} Request-Example: 
	 * 					{"id":"1234566", "email":"abc@123.com", "tel":"123456"}
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
	@PutMapping("/{userId}")
	@ResponseBody
	public Object userEdit(@PathVariable("userId") String userId, @RequestBody JSONObject parm){
		SysUsers user = JSONObject.toJavaObject(parm, SysUsers.class);
		user.setId(userId);
		if(!StringUtils.isEmpty(user.getId())){
			if(!validateEmail(user.getEmail())){
				return getInvalidParamResult();
			}
			user.setPwd(null);
			userService.updateUser(user);
			return getSuccessResult();
		}else{
			return getInvalidParamResult();
		}
	}

	
	/**
	 * @api {DELETE} /admin/user 删除用户(单个/批量)
	 * @apiGroup Admin/User Manager
	 * 
	 * @apiParam {JSONArray} - 用户IDs
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
	@DeleteMapping
	@ResponseBody
	public Object userDelete(@RequestBody JSONArray parm, HttpServletRequest req){
		if(!parm.isEmpty()){
			int size = parm.size();
			List<String> userIds = new ArrayList<String>(size);
			for(int i=0;i<size;++i){
				userIds.add(parm.getString(i));
			}
			String userId = getUserInfo(req);
			if(userIds.contains(userId)){
				return getInvalidParamResult();
			}
			userService.deleteUsers(userIds);
			return getSuccessResult();
		}else{
			return getInvalidParamResult();
		}
	}
	
}
