package com.rs.devplatform.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.service.admin.MenuService;
import com.rs.framework.common.Constants;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.entity.persistent.SysMenus;
import com.rs.framework.common.entity.vo.SysMenuVO;

@Controller
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {
	
	@Autowired
	MenuService menuService;
	
	/**
	 * @api {GET} /admin/menu 转到Menu管理页面
	 * @apiGroup Admin/Menu
	 * @apiSuccess {String} html 对应数据字典管理页面 
	 * @apiSuccessExample {json} 页面命名:
	 * 			{"页面":"/manage/menu.html"}
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index() {
		return "manage/menu";
	}
	
	/**
	 * @api {GET} /admin/menu/sidelist 获取左侧列表Menu List
	 * @apiDescription 左边栏的菜单显示（不显示访问类型）
	 * @apiGroup Admin/Menu
	 * 
	 * @apiSuccess {JSONArray} rows 返回Menu
	 * @apiSuccess {String} rows.id 菜单ID
	 * @apiSuccess {String} rows.name 菜单名称
	 * @apiSuccess {String} rows.url 菜单url地址
	 * @apiSuccess {String} rows.pid 菜单父节点ID
	 * @apiSuccess {String} rows.type 菜单类型（A——文件夹类型，B——url节点）
	 * @apiSuccess {String} rows.funcId 功能组ID
	 * @apiSuccess {String} rows.icon 菜单图标
	 * @apiSuccessExample {json} Success-Response:
	 *			{
					"total": 2,
					"rows": [
						{
							"id": "c5a298c0bcb84733aa68413a7da9715c",
							"name": "测试文件夹",
							"url": "",
							"pid": "",
							"type": "A",
							"funcId": null,
							"version": null
							"sequence":1
							"icon":"1.jpg"
						},
						{
							"id": "fd0ab70a24ab44219d4ebba5f8117ad4",
							"name": "dddsa",
							"url": "/test",
							"pid": "c5a298c0bcb84733aa68413a7da9715c",
							"type": "B",
							"funcId": null,
							"version": 1,
							"sequence":2,
							"icon":null
						}
					]
				}
	 *
	 * @return
	 */
	@GetMapping("/sidelist")
	@ResponseBody
	public Object menuSideList(HttpServletRequest req) {
		List<SysMenuVO> menuList = menuService.getSideMenus(getUserInfo(req), getCurrentProjectId(req));
		JSONObject result = new JSONObject();
		if(menuList != null){
			return getSuccessResult(menuList);
		}else{
			result.put(RECORDS, new JSONArray(0));
			result.put(TOTAL, 0);
		}
		return getSuccessResult(result);
	}
	
	/**
	 * @api {GET} /admin/menu/list 获取Menu List
	 * @apiDescription 所有有权限的菜单的显示
	 * @apiGroup Admin/Menu
	 * 
	 * @apiSuccess {JSONArray} rows 返回Menu
	 * @apiSuccess {String} rows.id 菜单ID
	 * @apiSuccess {String} rows.name 菜单名称
	 * @apiSuccess {String} rows.url 菜单url地址
	 * @apiSuccess {String} rows.pid 菜单父节点ID
	 * @apiSuccess {String} rows.type 菜单类型（A——文件夹类型，B——url节点,C——访问类型）
	 * @apiSuccess {String} rows.funcId 功能组ID
	 * @apiSuccess {String} rows.icon 菜单图标
	 * @apiSuccessExample {json} Success-Response:
	 *			{
					"total": 2,
					"rows": [
						{
							"id": "c5a298c0bcb84733aa68413a7da9715c",
							"name": "测试文件夹",
							"url": "",
							"pid": "",
							"type": "A",
							"funcId": null,
							"version": null
							"sequence":1
							"icon":"1.jpg"
						},
						{
							"id": "fd0ab70a24ab44219d4ebba5f8117ad4",
							"name": "dddsa",
							"url": "/test",
							"pid": "c5a298c0bcb84733aa68413a7da9715c",
							"type": "B",
							"funcId": null,
							"version": 1,
							"sequence":2,
							"icon":null
						}
					]
				}
	 *
	 * @return
	 */
	@GetMapping("/list")
	@ResponseBody
	public Object menuList(HttpServletRequest req) {
		List<SysMenuVO> menuList = menuService.getMenus(getUserInfo(req));
		JSONObject result = new JSONObject();
		if(menuList != null){
			return getSuccessResult(menuList);
		}else{
			result.put(RECORDS, new JSONArray(0));
			result.put(TOTAL, 0);
		}
		return getSuccessResult(result);
	}
	
	/**
	 * @api {POST} /admin/menu 增加Menu
	 * @apiDescription type 菜单类型为A时，不填url,不填funcid;菜单类型为B时，必填url
	 * @apiGroup Admin/Menu
	 * 
	 * @apiParam {String} id 菜单ID（八位数字）
	 * @apiParam {String} name 菜单名称
	 * @apiParam {String} url 菜单Url（菜单类型为B时必填，为A是不能填）
	 * @apiParam {String} type 菜单类型（A——文件夹类型，B——url节点,C——访问类型）
	 * @apiParam {String} pid 菜单父节点ID
	 * @apiParam {String} icon 菜单图标
	 * @apiParamExample {json} Request-Example: 
	 * 					{"id":"12345678", "name":"新建菜单", "pid":"1c5f8cdf97af43cda857e4cbcb9a9417", "url":"/admin/new", "type":"B", "icon": "a.png"}
	 * @apiSuccess {String} id Menu id
	 * @apiSuccessExample {json} Success-Response:
	 * 			HTTP/1.1 200 OK
	 * 			{"id":"0f077145cc894f7990387c9458091e1b"}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * @param parm
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Object menuAdd(@RequestBody JSONObject parm){
		SysMenus menu = JSONObject.toJavaObject(parm, SysMenus.class);
		if(validateData(menu)){
			if(menu.getType().equals("A")){
				if((menu.getUrl() !=null && !menu.getUrl().equals(""))) {
					addValidationError("文件夹类型的菜单不需要设置url");
					return getInvalidParamResult();
				}
			}
			if(menu.getType().equals("B") || menu.getType().equals("C")){
				if((menu.getUrl() == null || menu.getUrl().equals(""))) {
					addValidationError("访问类型和url类型必须设置访问的url地址");
					return getInvalidParamResult();
				}
			}
			if(!StringUtils.isEmpty(menu.getPid())){
				SysMenus entity = generalMapper.selectById(menu.getPid(), SysMenus.class);
				if(entity.getType().equals("C")){
					addValidationError("父节点不能是访问类型");
					return getInvalidParamResult();
				}
			}
			
			String funcid = null;
			if(! StringUtils.isEmpty(menu.getUrl())){
				String bxxId = getBxxId(menu.getUrl());
				SysBxx bxx = generalMapper.selectById(bxxId, SysBxx.class);
				if(bxx != null){
					funcid = bxx.getFcflag();
				}
			}
			menu.setFuncId(funcid);
			String url = menu.getUrl();
			if(!StringUtils.isEmpty(url)){
				url = getUrl(url) + "?menuId=" +menu.getId();
				menu.setUrl(url);
			}
			SysMenus entity = new SysMenus();
			entity.setId(menu.getId());
			if(isDuplicateBeforeCreate(entity)){
				return getDuplicateResult();
			}
			entity.setId(null);
			entity.setPid(menu.getPid());
			entity.setName(menu.getName());
			entity.setType(menu.getType());
			if(isDuplicateBeforeCreate(entity)){
				return getDuplicateResult();
			}
			menuService.addMenu(menu);
			JSONObject result = new JSONObject();
			result.put(ID, menu.getId());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult();
		}
	}
	
	private String getBxxId(String url){
		/*String regex = "";
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);*/
		int sp = url.lastIndexOf("/") + 1;
		int ep = url.indexOf('?');
		if(ep < 0)
			return url.substring(sp);
		else
			return url.substring(sp, ep);
	}
	
	private String getUrl(String url){
		int n = url.indexOf('?');
		if(n < 0){
			return url;
		}else{
			return url.substring(0, n);
		}
	}
	
	/**
	 * @api {PUT} /admin/menu/{menuId} 修改Menu
	 * @apiDescription type 菜单类型为A时，不填url,不填funcid;菜单类型为B时，必填url
	 * @apiGroup Admin/Menu
	 * 
	 * @apiParam {String} id 菜单ID（八位数字）
	 * @apiParam {String} name 菜单名称
	 * @apiParam {String} url 菜单Url（菜单类型为B时必填，为A是不能填）
	 * @apiParam {String} type 菜单类型（A——文件夹类型，B——url节点,C——访问类型）
	 * @apiParam {String} pid 菜单父节点ID
	 * @apiParam {String} icon 菜单图标
	 * @apiParamExample {json} Request-Example: 
	 * 					{"id":"12345678", "name":"test2","pid":"0e6f6534d9424f569ffb5276ad939ce6", "url":"/test1/2", "type":"B", "icon":"1.jpg"}
	 * @apiSuccess {String} id Menu id
	 * @apiSuccessExample {json} Success-Response:
	 * 			HTTP/1.1 200 OK
	 * 			{"id":"0f077145cc894f7990387c9458091e1b"}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * @param parm
	 * @return
	 */
	@RequestMapping(value = "/{menuId}", method = RequestMethod.PUT)
	@ResponseBody
	public Object menuUpdate(@PathVariable String menuId, @RequestBody JSONObject parm){
		SysMenus menu = JSONObject.toJavaObject(parm, SysMenus.class);
		menu.setId(menuId);
		if(validateData(menu)){
			SysMenus menuparm = new SysMenus();
			menuparm.setPid(menuId);
			if(generalMapper.selectCount(menuparm) > 0 && menu.getType().equalsIgnoreCase("B"))
				return getInvalidParamResult();
			SysMenus parmMenu = new SysMenus();
			parmMenu.setName(menu.getName());
			parmMenu.setPid(menu.getPid());
			parmMenu.setId(menuId);
			if(isDuplicateBeforeUpdate(parmMenu)){
				return getDuplicateResult();
			}
			if(menu.getType().equals("A")){
				if((menu.getUrl() !=null && !menu.getUrl().equals(""))) {
					addValidationError("文件夹类型的菜单不需要设置url");
					return getInvalidParamResult();
				}
				menu.setUrl("");
				menu.setFuncId("");
			}
			if(menu.getType().equals("B") || menu.getType().equals("C")){
				if((menu.getUrl() == null || menu.getUrl().equals(""))) {
					addValidationError("访问类型和url类型必须设置访问的url地址");
					return getInvalidParamResult();
				}
			}
			if(StringUtils.isEmpty(menu.getDeleted())){
				menu.setDeleted(Constants.DelInd.FALSE);
			}
			String funcid = null;
			if(! StringUtils.isEmpty(menu.getUrl())){
				String bxxId = getBxxId(menu.getUrl());
				SysBxx bxx = generalMapper.selectById(bxxId, SysBxx.class);
				if(bxx != null){
					funcid = bxx.getFcflag();
				}
			}
			String url = menu.getUrl();
			if(!StringUtils.isEmpty(url)){
				url = getUrl(url) + "?menuId=" +menu.getId();
				menu.setUrl(url);
			}
			menu.setFuncId(funcid);
			menuService.updateMenu(menu);
			JSONObject result = new JSONObject();
			result.put(ID, menu.getId());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult();
		}
	}
	
	
	
	/**
	 * @api {DELETE} /admin/menu/{menuId} 删除Menu
	 * @apiGroup Admin/Menu
	 * @apiParamExample {json} Request-Example: 
	 * 		DELETE /admin/menu/1
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * @return
	 */
	@RequestMapping(value="/{menuId}", method=RequestMethod.DELETE)
	@ResponseBody
	public Object menuDelete(@PathVariable("menuId") String menuId){
		SysMenus menu = new SysMenus();
		menu.setPid(menuId);
		if(generalMapper.selectCount(menu) > 0)
			return getInvalidParamResult();
		menuService.deleteMenu(menuId);
		return getSuccessResult();
	}
	
}
