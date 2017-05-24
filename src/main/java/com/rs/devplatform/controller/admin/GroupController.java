package com.rs.devplatform.controller.admin;

import java.util.ArrayList;
import java.util.List;

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
import com.rs.devplatform.service.admin.GroupService;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.entity.persistent.SysGroup;
import com.rs.framework.common.entity.persistent.SysUsers;

@Controller
@RequestMapping("/admin/group")
public class GroupController extends BaseController {
	@Autowired
	private GroupService groupService;
	
	/**
	 * @api {GET} /admin/group 转到用户组管理页面
	 * @apiGroup Admin/Group
	 * @apiSuccess {String} html 对应用户组管理页面 
	 * @apiSuccessExample {json} 页面命名:
	 * 			{"页面":"/manage/group.html"}
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index() {
		return "manage/group";
	}
	
	/**
	 * @api {POST} /admin/group 新增用户组 
	 * @apiGroup Admin/Group Manager
	 * 
	 * @apiParam {String} name 组名称
	 * @apiParam {String} actor 用户IDs
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * 					{"name":"test2","actor":"1,4,a,"}
	 * 
	 * @apiSuccess {String} id 用户组ID
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * {
		  "id": "80b66b2220064ff3a7fa7997fa51edc0"
		}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * 
	 * @param parm
	 * @return
	 */
	@PostMapping
	@ResponseBody
	public Object addGroup(@RequestBody JSONObject parm){
		SysGroup group = parm.toJavaObject(SysGroup.class);
		if(!validateData(group)){
			return getInvalidParamResult();
		}
		String[] actor = group.getActor().split(",");
		List<String> actorIds = new ArrayList<String>();
		for(int i = 0; i< actor.length; i++){
			if(!actorIds.contains(actor[i])){
				actorIds.add(actor[i]);
			}
		}
		SysUsers user = new SysUsers();
		RsEntityWrapper<SysUsers> wrapper = new RsEntityWrapper<>(user);
		wrapper.in("users_id", actorIds);
		if((actorIds.size() < 2) || (generalMapper.selectCountByEW(wrapper) < actorIds.size()) ){
			return getInvalidParamResult();
		}
		
		SysGroup entity = new SysGroup();
		entity.setId(group.getId());
		entity.setName(group.getName());
		if(isDuplicateBeforeCreate(entity)){
			return getDuplicateResult();
		}
		if(groupService.addGroup(group)){
			JSONObject result = new JSONObject();
			result.put(ID, group.getId());
			return getSuccessResult(result);
		}else{
			return getModifiedResult();
		}
	}
	
	/**
	 * @api {PUT} /admin/group/{groupId} 修改group 
	 * @apiGroup Admin/Group Manager
	 * 
	 * @apiParam {String} groupId 用户组ID
	 * @apiParam {String} name 组名称
	 * @apiParam {String} actor 用户IDs
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * 					{"name":"test1","actor":"1,4,admin,"}
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * 
	 * @param parm
	 * @return
	 */
	@PutMapping("/{groupId}")
	@ResponseBody
	public Object updateGroup(@PathVariable("groupId") String groupId, @RequestBody JSONObject parm){
		SysGroup group = parm.toJavaObject(SysGroup.class);
		group.setId(groupId);
		if(!validateData(group)){
			return getInvalidParamResult();
		}
		SysGroup entity = new SysGroup();
		entity.setName(group.getName());
		entity.setId(groupId);
		if(isDuplicateBeforeUpdate(entity)){
			return getDuplicateResult();
		}
		if(groupService.updateGroup(group)){
			return getSuccessResult();
		}else{
			return getModifiedResult();
		}
	}
	
	/**
	 * @api {DELETE} /admin/group 删除用户组（单个/多个删除） 
	 * @apiGroup Admin/Group Manager
	 * 
	 * @apiParam {JSONArray} ids 用户组ID
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * 					{"ids":["1","2","3"]}
	 * 
	 * @apiSuccess {String/int/json/JSONArray} descp 描述(多个)
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * 
	 * @param parm
	 * @return
	 */
	@DeleteMapping
	@ResponseBody
	public Object deleteGroup(@RequestBody JSONObject parm){
		JSONArray array = parm.getJSONArray("ids");
		if(array == null || array.isEmpty()){
			return getInvalidParamResult();
		}
		List<String> groupIds = new ArrayList<String>();
		for(int i = 0; i< array.size(); i++){
			groupIds.add(array.getString(i));
		}
		if(groupService.deleteGroup(groupIds)){
			return getSuccessResult();
		}else{
			return getModifiedResult();
		}
	}
	
	/**
	 * @api {GET} /admin/group/list 获取所有的用户组 
	 * @apiGroup Admin/Group Manager
	 * 
	 * @apiSuccess {String} id 用户组ID
	 * @apiSuccess {String} name 用户组名称
	 * @apiSuccess {String} actor 用户组参与人员id
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * {
		  "total": 2,
		  "rows": [
		    {
		      "id": "01",
		      "name": "test1",
		      "actor": "1,4,admin,",
		      "version": null
		    },
		    {
		      "id": "80b66b2220064ff3a7fa7997fa51edc0",
		      "name": "test3",
		      "actor": "1,4,admin,",
		      "version": 1
		    }
		  ]
		}
	 * 
	 * @param parm
	 * @return
	 */
	@GetMapping("/list")
	@ResponseBody
	public Object listGroup(){
		SysGroup entity = new SysGroup();
		entity.setDeleted("F");
		List<SysGroup> groupList = generalMapper.selectList(entity);
		return getSuccessResult(groupList);
	}
}
