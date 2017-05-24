package com.rs.devplatform.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.persistent.SysPj;
import com.rs.devplatform.service.common.LoginService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.entity.persistent.SysUsers;
import com.rs.framework.common.validator.ValidateError;

@Controller
public class LoginController extends BaseController {

	private final String USER_FLAG_ADMIN = "A";
	private final String USER_FLAG_USER = "U";
	private final String PARM_USER_FLAG = Constants.USER_FLAG;
	
	@Autowired
	LoginService loginService;
	
	@RequestMapping("/index")
	String index(HttpSession session) {
		Object userInfoSession = session.getAttribute(Constants.USERINFO);
		if (null != userInfoSession) {
			@SuppressWarnings("unchecked")
			Map<String, Object> userInfoMap = (Map<String, Object>) userInfoSession;
			if (USER_FLAG_ADMIN.equals(userInfoMap.get(PARM_USER_FLAG))){
				return "admin-index";
			}
			return "user-index";
		}
		return "forward:/login";
	}
	
	/**
	 * @api {post} /login 页面提交登录
	 * @apiGroup Login
	 * 
	 * @apiParam {String} username 用户名
	 * @apiParam {String} password 密码
	 * @apiParam {String} projectId 项目ID(可以为空，不为空时，userFlag不用传值，传值也会忽略)
	 * @apiParam {String} userFlag 用户类型: A:admin, U:user
	 * @apiSuccess {String} html 登录成功跳转页面,页面名称见下面example
	 * @apiSuccessExample {json} 页面命名:
	 *  {"admin的页面":"admin-index.html"}
	 *  {"user的页面":"user-index.html"}
	 *  {"测试人员对应项目的页面":"user-index.html"}
	 * @apiErrorExample {json} Error-Response:
	 *  login.html
	 *  {"verror":"用户名密码不正确"}
	 *  
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, HttpSession session) {
		Object userInfoSession = session.getAttribute(Constants.USERINFO);
		if (null != userInfoSession) {
			@SuppressWarnings("unchecked")
			Map<String, Object> userInfoMap = (Map<String, Object>) userInfoSession;
			if (USER_FLAG_ADMIN.equals(userInfoMap.get(PARM_USER_FLAG))){
				return "admin-index";
			}
			return "user-index";
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String projectId = request.getParameter("projectId");
		String userFlag = request.getParameter(PARM_USER_FLAG);
		if(!StringUtils.isBlank(username) && !StringUtils.isBlank(password)){
			SysUsers user = null;
			if(StringUtils.isBlank(projectId)){
				user = loginService.login(username, password);
			}else{
				user = loginService.login(username, password, projectId);
			}
			if(user!=null){
				Map<String,Object> userInfo = new HashMap<String,Object>();
				userInfo.put(Constants.ID, user.getId());
				userInfo.put(Constants.USERNAME, user.getName());
				userInfo.put(Constants.NICKNAME, user.getNkname());
				request.getSession().setAttribute(Constants.USERINFO, userInfo);
				if(!StringUtils.isBlank(projectId)){
					session.setAttribute(Constants.PJID, projectId);
				}else{
					userInfo.put(PARM_USER_FLAG, userFlag);
				}
				if(USER_FLAG_ADMIN.equals(userFlag) && StringUtils.isBlank(projectId)){
					return "admin-index";
				} else {
					return "user-index";
				}
			}
		}
		request.setAttribute(VALIDATE_ERRORS, "用户名密码不正确");
		return "login-navy";
		
	}
	
	/**
	 * @api {post} /loginrest 	ajax提交登录
	 * @apiGroup Login
	 * @apiDescription 根据用户类型，转到不同的主页.
	 * 
	 * @apiParam {String} username 用户名
	 * @apiParam {String} password 密码
	 * @apiParam {String} projectId 项目ID(可以为空，不为空时，userFlag不用传值，传值也会忽略)
	 * @apiParam {String} userFlag 用户类型: A:admin, U:user
	 * @apiParamExample {json} Request-Example: 
	 * 	{"username":"admin", "password":"123456", "userFlag":"A"}
	 * 	{"username":"admin", "password":"123456", "projectId":"123123123"}
	 * 
	 * @apiSuccess {String} userFlag 用户类型, A:Admin, U:User
	 * @apiSuccessExample {json} Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	  {"userFlag":"A"}
	 * 	  {"userFlag":"U"}
	 *  @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 *  {"verrors":[{"errCode":"","errorMsg":"用户名密码不正确"}]}
	 *  
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/loginrest",method=RequestMethod.POST )
	@ResponseBody
	public Object loginRest(@RequestBody JSONObject parm, HttpServletRequest request) {
		String userFlag = parm.getString(PARM_USER_FLAG);
		String username = parm.getString("username");
		String password = parm.getString("password");
		String projectId = parm.getString("projectId");
		if(!StringUtils.isBlank(username) && !StringUtils.isBlank(password)){
			SysUsers user = null;
			if(StringUtils.isBlank(projectId)){
				user = loginService.login(username, password);
			}else{
				user = loginService.login(username, password, projectId);
			}
			if(user!=null){
				Map<String,Object> userInfo = new HashMap<String,Object>();
				userInfo.put(Constants.ID, user.getId());
				userInfo.put(Constants.USERNAME, user.getName());
				userInfo.put(Constants.NICKNAME, user.getNkname());
				userInfo.put(PARM_USER_FLAG, userFlag);
				request.getSession().setAttribute(Constants.USERINFO, userInfo);
				JSONObject result = new JSONObject();
				if(USER_FLAG_ADMIN.equals(userFlag) && StringUtils.isBlank(projectId)){
					result.put(PARM_USER_FLAG, USER_FLAG_ADMIN);
				}else{
					result.put(PARM_USER_FLAG, USER_FLAG_USER);
				}
				result.put(Constants.ID, user.getId());
				return getSuccessResult(result);
			}
		}
		addValidationError(new ValidateError("", "用户名密码不正确"));
		return getInvalidParamResult();
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Constants.USERINFO);
		session.invalidate();
		return "redirect:/";
	}
	
	/**
	 * @api {GET} /projects 	获取想要登录的项目列表
	 * @apiGroup Login
	 * @apiDescription 获取想要登录的项目列表
	 * 
	 * @apiSuccess {String} id 项目ID
	 * @apiSuccess {String} name 项目名称
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {
	 *    "rows":[
	 *      {"id":"04fda30ffaa04b8b97c217c43342b04d", "name":"projectA"},
	 *      {"id":"04fda30ffaa04b8b97c217c43342sssss", "name":"projectB"},
	 *    ]
	 *  }
	 *  
	 *  @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 * 
	 * @return
	 */
	@GetMapping("/projects")
	@ResponseBody
	public Object getProjects4Login(){
		SysPj pj = new SysPj();
		pj.setDeleted(Constants.DelInd.FALSE);
		pj.setInit(Constants.YES);
		RsEntityWrapper<SysPj> ew = new RsEntityWrapper<>(pj);
		ew.orderBy("PJS_NAME");
		List<SysPj> pjList = generalMapper.selectList(ew);
		if(pjList!=null){
			List<JSONObject> result = new ArrayList<>(pjList.size());
			pjList.stream().map((p)->{
				JSONObject rcd = new JSONObject(2);
				rcd.put(ID, p.getId());
				rcd.put(NAME, p.getName());
				return rcd;
			}).forEach(result::add);
			return getSuccessResult(result);
		}
		return getInvalidParamResult();
	}
	
	
	
}
