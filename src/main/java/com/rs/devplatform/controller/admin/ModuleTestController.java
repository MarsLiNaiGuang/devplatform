package com.rs.devplatform.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.rs.devplatform.common.BuzException;
import com.rs.devplatform.common.CGConstants;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysCustbt;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.persistent.SysZqjs;
import com.rs.devplatform.persistent.mapper.CGMapper;
import com.rs.devplatform.persistent.mapper.SysCustbtMapper;
import com.rs.devplatform.service.admin.ModueTestService;
import com.rs.devplatform.service.cg.CGService;
import com.rs.devplatform.vo.common.RsCommonResponse;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.PoiController;

@Controller
@RequestMapping("/admin/moduleTest")
public class ModuleTestController extends PoiController{
	
	@Autowired
	CGMapper cgMapper;
	
	@Autowired
	CGService cgService;
	
	@Autowired
	SysCustbtMapper sysCustbtMapper;
	
	@Autowired
	ModueTestService moduleTestService;

	@RequestMapping(value="/page/autolist/{id}", method=RequestMethod.GET)
	public ModelAndView autolistPage(@PathVariable("id") String moduleId, @ModelAttribute("model") ModelMap model) {
		ModelAndView mav=new ModelAndView();
		mav.addObject(CGConstants.CONFIG_ID, moduleId);
//		SysBxx sysBxx = generalMapper.selectById(moduleId, SysBxx.class);
//		mav.addObject(CGConstants.CONFIG_NAME, sysBxx.getNr());
//		mav.addObject(CGConstants.TABLENAME, sysBxx.getBm());
//		SysZdxx zdxxParm = new SysZdxx();
//		zdxxParm.setBid(moduleId);
//		zdxxParm.setDeleted(Constants.DelInd.FALSE);
//		EntityWrapper<SysZdxx> zdxxWrapper = new EntityWrapper<>(zdxxParm);
//		zdxxWrapper.orderBy("ZDXX_XH");
//		List<SysZdxx> zdxxList = generalMapper.selectList(zdxxWrapper);
//		mav.addObject(CGConstants.CONFIG_FIELDLIST, zdxxList);
//
//		Map<String, List<SysCdval>> fieldDictMap = new HashMap<String, List<SysCdval>>();
//		String dictCode = "fieldtype";
//		List<SysCdval> valList = getListByCode(dictCode);
//		fieldDictMap.put(dictCode, valList);
//		mav.addObject(CGConstants.FIELD_DICTMAP, fieldDictMap);
		
		mav.setViewName("cg/autolist");
		return mav;
	}

	@RequestMapping(value="/page/jform/{id}", method=RequestMethod.GET)
	public ModelAndView jformPage(@PathVariable("id") String moduleId, @ModelAttribute("model") ModelMap model) {
		ModelAndView mav=new ModelAndView();
		mav.addObject(CGConstants.CONFIG_ID, moduleId);

		SysBxx sysBxx = generalMapper.selectById(moduleId, SysBxx.class);
		mav.addObject(CGConstants.CONFIG_NAME, sysBxx.getNr());

		SysZdxx zdxxParm = new SysZdxx();
		zdxxParm.setBid(moduleId);
		zdxxParm.setDeleted(Constants.DelInd.FALSE);
		zdxxParm.setIsshowlb(Constants.YES);
		RsEntityWrapper<SysZdxx> wrapper = new RsEntityWrapper<>(zdxxParm);
		List<SysZdxx> zdxxList = generalMapper.selectList(wrapper);
		mav.addObject(CGConstants.CONFIG_FIELDLIST, zdxxList);
		
		mav.setViewName("cg/jform");
		return mav;
	}

	/**
	 * @api {GET} /admin/moduleTest/getForm/{id} 获取sys_bxx里的表单列表
	 * @apiGroup Module Test
	 * @apiParamExample {json} Request-Example: 
	 * 		{"current":1, "size":10, "orderBy":"name", "asc":"true"}
	 		{"name":"123", "orderby":"name", "comments":"不分页,current为null"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} rows.id 表明细主键	
	 * @apiSuccess {String} rows.nr 表明细内容	
	 * @apiSuccess {String} rows.iscbox 是否是多选框	
	 * @apiSuccess {String} rows.isdbsyn 是否数据库同步	
	 * @apiSuccess {String} rows.ispage 是否分页	
	 * @apiSuccess {String} rows.istree 是否显示为树结构	
	 * @apiSuccess {String} rows.pkseq 表单主键序列	
	 * @apiSuccess {String} rows.pklx 主键类型	
	 * @apiSuccess {Integer} rows.lx 表类型：1：单表，2：主表，3：子表	
	 * @apiSuccess {String} rows.cxms 查询模式	
	 * @apiSuccess {Integer} rows.gxlx 关系类型	
	 * @apiSuccess {String} rows.zb 子表字符串sub_table_str	
	 * @apiSuccess {Integer} rows.tabxh tab序号	
	 * @apiSuccess {String} rows.bm 表名	
	 * @apiSuccess {String} rows.sfidzdm 树型结构中的父节点字段名字	
	 * @apiSuccess {String} rows.sidzdm 树结构id字段名字	
	 * @apiSuccess {String} rows.szdm 树字段名	
	 * @apiSuccess {String} rows.lb 表单类别	
	 * @apiSuccess {String} rows.mb 表单模板	
	 * @apiSuccess {String} rows.mbm 表单模板样式(移动端)	
	 * @apiSuccess {String} rows.whr 维护人名字	
	 * @apiSuccess {Date} rows.whsj 维护时间	
	 * @apiSuccess {String} rows.whrid 维护人ID	
	 * @apiSuccess {String} rows.cjr 创建人名字	
	 * @apiSuccess {Date} rows.cjsj 创建时间	
	 * @apiSuccess {String} rows.cjrid 创建人ID	
	 * @apiSuccess {String} rows.deleted 	
	 * @apiSuccess {Integer} rows.version 表单版本	
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * 	{"total",25, "current":1, "size":10, "pages":3, "rows":[
	 		{
			"id" : "表明细主键",	
			"nr" : "表明细内容",	
			"iscbox" : "是否是多选框",	
			"isdbsyn" : "是否数据库同步",	
			"ispage" : "是否分页",	
			"istree" : "是否显示为树结构",	
			"pkseq" : "表单主键序列",	
			"pklx" : "主键类型",	
			"lx" : "表类型：1：单表，2：主表，3：子表",	
			"cxms" : "查询模式",	
			"gxlx" : "关系类型",	
			"zb" : "子表字符串sub_table_str",	
			"tabxh" : "tab序号",	
			"bm" : "表名",	
			"sfidzdm" : "树型结构中的父节点字段名字",	
			"sidzdm" : "树结构id字段名字",	
			"szdm" : "树字段名",	
			"lb" : "表单类别",	
			"mb" : "表单模板",	
			"mbm" : "表单模板样式(移动端)",	
			"whr" : "维护人名字",	
			"whsj" : "维护时间",	
			"whrid" : "维护人ID",	
			"cjr" : "创建人名字",	
			"cjsj" : "创建时间",	
			"cjrid" : "创建人ID",	
			"deleted" : "",	
			"version" : "表单版本",	
	 		}
	 	]}
	 * 	{"rows":[
	 		{
			"id" : "表明细主键",	
			"nr" : "表明细内容",	
			"iscbox" : "是否是多选框",	
			"isdbsyn" : "是否数据库同步",	
			"ispage" : "是否分页",	
			"istree" : "是否显示为树结构",	
			"pkseq" : "表单主键序列",	
			"pklx" : "主键类型",	
			"lx" : "表类型：1：单表，2：主表，3：子表",	
			"cxms" : "查询模式",	
			"gxlx" : "关系类型",	
			"zb" : "子表字符串sub_table_str",	
			"tabxh" : "tab序号",	
			"bm" : "表名",	
			"sfidzdm" : "树型结构中的父节点字段名字",	
			"sidzdm" : "树结构id字段名字",	
			"szdm" : "树字段名",	
			"lb" : "表单类别",	
			"mb" : "表单模板",	
			"mbm" : "表单模板样式(移动端)",	
			"whr" : "维护人名字",	
			"whsj" : "维护时间",	
			"whrid" : "维护人ID",	
			"cjr" : "创建人名字",	
			"cjsj" : "创建时间",	
			"cjrid" : "创建人ID",	
			"deleted" : "",	
			"version" : "表单版本",	
			}
	 	]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/getForm/{id}")
	@ResponseBody
	public Object getForm(@PathVariable("id") String moduleId){
		SysBxx sysBxx = generalMapper.selectById(moduleId, SysBxx.class);
		//根据id获取模板
		JSONObject result = new JSONObject();
		if (sysBxx != null){
			//返回结果
			result.put(ID, sysBxx.getId());
			result.put("nr", sysBxx.getNr());
			result.put("iscbox", sysBxx.getIscbox());
			result.put("isdbsyn", sysBxx.getIsdbsyn());
			result.put("ispage", sysBxx.getIspage());
			result.put("istree", sysBxx.getIstree());
			result.put("pkseq", sysBxx.getPkseq());
			result.put("pklx", sysBxx.getPklx());
			result.put("lx", sysBxx.getLx());
			result.put("cxms", sysBxx.getCxms());
			result.put("gxlx", sysBxx.getGxlx());
			result.put("zb", sysBxx.getZb());
			result.put("tabxh", sysBxx.getTabxh());
			result.put("bm", sysBxx.getBm());
			result.put("sfidzdm", sysBxx.getSfidzdm());
			result.put("sidzdm", sysBxx.getSidzdm());
			result.put("szdm", sysBxx.getSzdm());
			result.put("lb", sysBxx.getLb());
			result.put("mb", sysBxx.getMb());
			result.put("mbm", sysBxx.getMbm());
			result.put("whr", sysBxx.getWhr());
			result.put("whsj", sysBxx.getWhsj());
			result.put("whrid",sysBxx.getWhrid());
			result.put("cjr", sysBxx.getCjr());
			result.put("cjsj", sysBxx.getCjsj());
			result.put("cjrid", sysBxx.getCjrid());
			result.put("child", sysBxx.getChild());
			result.put("parent", sysBxx.getParent());
		};
		return getSuccessResult(result);
	}
	
	@GetMapping("/module/{id}")
	@ResponseBody
	// 获取指定模块的表单对应主从表完整信息
	public Object getModuleMasterDetailInfo(@PathVariable("id") String moduleId){
		JSONObject result = null;
		try {
			result = cgService.getFormMasterDetail(moduleId, null);
		
		} catch (BuzException e) {
			e.printStackTrace();
		}
		return getSuccessResult(result);
	} 
	
	/**
	 * @api {POST} /admin/moduleTest/getCondition/{id} 获取sys_zdxx里所有可以作为查询条件的字段列表
	 * @apiGroup Module Test
	 * @apiParamExample {json} Request-Example: 
	 * 		{"current":1, "size":10, "orderBy":"name", "asc":"true"}
	 		{"name":"123", "orderby":"name", "comments":"不分页,current为null"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} rows.id 字段明细主键	
	 * @apiSuccess {String} rows.nr 字段内容	
	 * @apiSuccess {String} rows.zdzd 字典字段	
	 * @apiSuccess {String} rows.zdbm 字典表名	
	 * @apiSuccess {String} rows.zdwb 字典文本	
	 * @apiSuccess {String} rows.mr 字段默认值	
	 * @apiSuccess {String} rows.href 字段超链接	
	 * @apiSuccess {Integer} rows.cd 字段长度	
	 * @apiSuccess {String} rows.name 字段名字	
	 * @apiSuccess {String} rows.yzlx 字段验证类型	
	 * @apiSuccess {String} rows.iskey 是否主键	
	 * @apiSuccess {String} rows.isnull 是否允许为空	
	 * @apiSuccess {String} rows.isquery 是否允许查询	
	 * @apiSuccess {String} rows.isshow 是否显示在表单	
	 * @apiSuccess {String} rows.isshowlb 是否显示为列表	
	 * @apiSuccess {Integer} rows.len 字段显示长度	
	 * @apiSuccess {String} rows.zzd 主表字段	
	 * @apiSuccess {String} rows.zb 主表	
	 * @apiSuccess {String} rows.jzdm 旧字段名字	
	 * @apiSuccess {Integer} rows.xh 序号	
	 * @apiSuccess {Integer} rows.plen 小数点位数	
	 * @apiSuccess {String} rows.cxms 查询模式	
	 * @apiSuccess {String} rows.xslx 显示类型	
	 * @apiSuccess {String} rows.lx 字段类型	
	 * @apiSuccess {String} rows.bid 表ID	
	 * @apiSuccess {String} rows.kzjson 扩展JSON	
	 * @apiSuccess {String} rows.whr 维护人名字	
	 * @apiSuccess {Date} rows.whsj 维护时间	
	 * @apiSuccess {String} rows.whrid 维护人ID	
	 * @apiSuccess {String} rows.cjr 创建人名字	
	 * @apiSuccess {Date} rows.cjsj 创建时间	
	 * @apiSuccess {String} rows.cjrid 创建人ID	
	 * @apiSuccess {Integer} rows.version 数据记录版本	
	 * @apiSuccess {String} rows.deleted 逻辑删除标记位	
	 * @apiSuccess {String} rows.chgtype 编辑表单时记录字段动态, A:Add,U:Update,D:Delete	
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * 	{"total",25, "current":1, "size":10, "pages":3, "records":[
	 		{
			"id" : "字段明细主键",	
			"nr" : "字段内容",	
			"zdzd" : "字典字段",	
			"zdbm" : "字典表名",	
			"zdwb" : "字典文本",	
			"mr" : "字段默认值",	
			"href" : "字段超链接",	
			"cd" : "字段长度",	
			"name" : "字段名字",	
			"yzlx" : "字段验证类型",	
			"iskey" : "是否主键",	
			"isnull" : "是否允许为空",	
			"isquery" : "是否允许查询",	
			"isshow" : "是否显示在表单",	
			"isshowlb" : "是否显示为列表",	
			"len" : "字段显示长度",	
			"zzd" : "主表字段",	
			"zb" : "主表",	
			"jzdm" : "旧字段名字",	
			"xh" : "序号",	
			"plen" : "小数点位数",	
			"cxms" : "查询模式",	
			"xslx" : "显示类型",	
			"lx" : "字段类型",	
			"bid" : "表ID",	
			"kzjson" : "扩展JSON",	
			"whr" : "维护人名字",	
			"whsj" : "维护时间",	
			"whrid" : "维护人ID",	
			"cjr" : "创建人名字",	
			"cjsj" : "创建时间",	
			"cjrid" : "创建人ID",	
			"version" : "数据记录版本",	
			"deleted" : "逻辑删除标记位",	
			"chgtype" : "编辑表单时记录字段动态, A:Add,U:Update,D:Delete",	
	 		}
	 	]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/getCondition/{id}")
	@ResponseBody
	public Object getCondition(@PathVariable("id") String moduleId) {
		SysZdxx zdxxParm = new SysZdxx();
		zdxxParm.setBid(moduleId);
		zdxxParm.setDeleted(Constants.DelInd.FALSE);
		zdxxParm.setIsquery(Constants.YES);//只取isquery=Y
		RsEntityWrapper<SysZdxx> wrapper = new RsEntityWrapper<>(zdxxParm);
		List<SysZdxx> zdxxList = generalMapper.selectList(wrapper);
		JSONObject result = new JSONObject();
		if (zdxxList != null){
			Object[] arr = zdxxList.stream()
					.map((x)->{
				JSONObject zdxx = new JSONObject();
				zdxx.put("id", x.getId());
				zdxx.put("bid", x.getBid());
				zdxx.put("nr", x.getNr());
				zdxx.put("zdzd", x.getZdzd());
				zdxx.put("zdbm", x.getZdbm());
				zdxx.put("zdwb", x.getZdwb());
				zdxx.put("mr", x.getMr());
				zdxx.put("href", x.getHref());
				zdxx.put("name", x.getName());
				zdxx.put("yzlx", x.getYzlx());
				zdxx.put("iskey", x.getIskey());
				zdxx.put("isnull", x.getIsnull());
				zdxx.put("isshow", x.getIsshow());
				zdxx.put("ishowlb", x.getIsshowlb());
				zdxx.put("len", x.getLen());
				zdxx.put("zzd", x.getZzd());
				zdxx.put("zb", x.getZb());
				zdxx.put("jzdm", x.getJzdm());
				zdxx.put("xh", x.getXh());
				zdxx.put("plen", x.getPlen());
				zdxx.put("cxms", x.getCxms());
				zdxx.put("xslx", x.getXslx());
				zdxx.put("lx", x.getLx());
				zdxx.put("kzjson", x.getKzjson());
				zdxx.put("whr", x.getWhr());
				zdxx.put("whsj", x.getWhsj());
				zdxx.put("whrid",x.getWhrid());
				zdxx.put("cjr", x.getCjr());
				zdxx.put("cjsj", x.getCjsj());
				zdxx.put("cjrid", x.getCjrid());
				return zdxx;	
			}).toArray();
			result.put(RECORDS, arr);
			result.put(TOTAL, zdxxList.size());
		}else{
			result.put(RECORDS, new JSONArray(0));
			result.put(TOTAL, 0);
		}
		return getSuccessResult(result);
	}
	
	/**
	 * @api {GET} /admin/moduleTest/getToolButton/{id} 获取sys_bxx里的表单所含有的index列表
	 * @apiGroup Module Test
	 * @apiParamExample {json} Request-Example: 
	 * 		{"current":1, "size":10, "orderBy":"name", "asc":"true"}
	 		{"name":"123", "orderby":"name", "comments":"不分页,current为null"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} rows.id 自定义按钮主键	
	 * @apiSuccess {String} rows.code 自定义按钮编码	
	 * @apiSuccess {String} rows.icon 自定义按钮图标	
	 * @apiSuccess {String} rows.name 自定义按钮名字	
	 * @apiSuccess {String} rows.zt 自定义按钮状态	
	 * @apiSuccess {String} rows.ys 自定义按钮样式	
	 * @apiSuccess {String} rows.exp 自定义按钮过期时间	
	 * @apiSuccess {String} rows.bdid 表单ID	
	 * @apiSuccess {String} rows.czlx 按钮操作类型	
	 * @apiSuccess {Integer} rows.xh 按钮序号	
	 * @apiSuccess {String} rows.whr 维护人名字	
	 * @apiSuccess {Date} rows.whsj 维护时间	
	 * @apiSuccess {String} rows.whrid 维护人ID	
	 * @apiSuccess {String} rows.cjr 创建人名字	
	 * @apiSuccess {Date} rows.cjsj 创建时间	
	 * @apiSuccess {String} rows.cjrid 创建人ID	
	 * @apiSuccess {Integer} rows.version 数据记录版本	
	 * @apiSuccess {String} rows.deleted 逻辑删除标记位	
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * 	{"total",25, "current":1, "size":10, "pages":3, "rows":[
	 		{
			"id" : "自定义按钮主键",	
			"code" : "自定义按钮编码",	
			"icon" : "自定义按钮图标",	
			"name" : "自定义按钮名字",	
			"zt" : "自定义按钮状态",	
			"ys" : "自定义按钮样式",	
			"exp" : "自定义按钮过期时间",	
			"bdid" : "表单ID",	
			"czlx" : "按钮操作类型",	
			"xh" : "按钮序号",	
			"whr" : "维护人名字",	
			"whsj" : "维护时间",	
			"whrid" : "维护人ID",	
			"cjr" : "创建人名字",	
			"cjsj" : "创建时间",	
			"cjrid" : "创建人ID",	
			"version" : "数据记录版本",	
			"deleted" : "逻辑删除标记位",	
	 		}
	 	]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/getToolButton/{id}")
	@ResponseBody
	public Object getToolButton(@PathVariable("id") String moduleId){
		SysCustbt custbtParm = new SysCustbt();
		custbtParm.setBdid(moduleId);
		custbtParm.setDeleted(Constants.DelInd.FALSE);
		List<SysCustbt> custbtList = generalMapper.selectList(new RsEntityWrapper<>(custbtParm));
		JSONObject result = new JSONObject();
		if (custbtList != null){
			Object[] arr = custbtList.stream().map((x)->{
				JSONObject custbt = new JSONObject();
				custbt.put("id", x.getId());
				custbt.put("bdid", x.getBdid());
				custbt.put("code", x.getCode());
				custbt.put("icon", x.getIcon());
				custbt.put("name", x.getName());
				custbt.put("zt", x.getZt());
				custbt.put("ys", x.getYs());
				custbt.put("exp", x.getExp());
				custbt.put("czlx", x.getCzlx());
				custbt.put("xh", x.getXh());
				custbt.put("whr", x.getWhr());
				custbt.put("whsj", x.getWhsj());
				custbt.put("whrid", x.getWhrid());
				custbt.put("cjr", x.getCjr());
				custbt.put("cjsj", x.getCjsj());
				custbt.put("cjrid", x.getCjrid());
				return custbt;
			}).toArray();
			result.put(RECORDS, arr);
			result.put(TOTAL, arr.length);
		}else{
			result.put(RECORDS, new JSONArray(0));
			result.put(TOTAL, 0);
		}
		return getSuccessResult(result);
	}
	
	/*获取增强JS数据*/
	/**
	 * @api {GET} /admin/moduleTest/getZqjs/{id} 获取sys_bxx里的表单的增强js
	 * @apiGroup Module Test
	 * @apiParamExample {json} Request-Example: 
	 * 		{"current":1, "size":10, "orderBy":"name", "asc":"true"}
	 		{"name":"123", "orderby":"name", "comments":"不分页,current为null"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} rows.id 增强js主键	
	 * @apiSuccess {String} rows.js 增强js	
	 * @apiSuccess {String} rows.jslx 增强js类型  form/list	
	 * @apiSuccess {String} rows.nr 增强js内容
	 * @apiSuccess {String} rows.bdid 表单id
	 * @apiSuccess {String} rows.whr 维护人名字	
	 * @apiSuccess {Date} rows.whsj 维护时间	
	 * @apiSuccess {String} rows.whrid 维护人ID	
	 * @apiSuccess {String} rows.cjr 创建人名字	
	 * @apiSuccess {Date} rows.cjsj 创建时间	
	 * @apiSuccess {String} rows.cjrid 创建人ID	
	 * @apiSuccess {Integer} rows.version 数据记录版本	
	 * @apiSuccess {String} rows.deleted 逻辑删除标记位	
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * 	{"total",25, "current":1, "size":10, "pages":3, "rows":[
	 		{
			"id" : "增强js主键",	
			"js" : "增强js",	
			"jslx" : "增强js类型",	
			"nr" : "增强js内容",	
			"bdid" : "表单ID",	
			"whr" : "维护人名字",	
			"whsj" : "维护时间",	
			"whrid" : "维护人ID",	
			"cjr" : "创建人名字",	
			"cjsj" : "创建时间",	
			"cjrid" : "创建人ID",	
			"version" : "数据记录版本",	
			"deleted" : "逻辑删除标记位",	
	 		}
	 	]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/getzqjs/{id}",method=RequestMethod.POST)
	@ResponseBody
	public Object getZqjsList(@PathVariable("id") String id, @RequestBody JSONObject parm){
		SysZqjs sysZqjsParam = new SysZqjs();
		sysZqjsParam.setBdid(id);
		if (!"".equals(parm.getString("jslx"))){
			sysZqjsParam.setJslx(parm.getString("jslx"));
		}	
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
				zqjs.put("nr", x.getNr());
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
	
	
	/**
	 * @api {POST} /admin/moduleTest/getList/{id}获取sys_zdxx里所有列表中显示的字段列表
	 * @apiGroup Module Test
	 * @apiParamExample {json} Request-Example: 
	 * 		{"current":1, "size":10, "orderBy":"name", "asc":"true"}
	 		{"name":"123", "orderby":"name", "comments":"不分页,current为null"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} rows.id 字段明细主键	
	 * @apiSuccess {String} rows.nr 字段内容	
	 * @apiSuccess {String} rows.zdzd 字典字段	
	 * @apiSuccess {String} rows.zdbm 字典表名	
	 * @apiSuccess {String} rows.zdwb 字典文本	
	 * @apiSuccess {String} rows.mr 字段默认值	
	 * @apiSuccess {String} rows.href 字段超链接	
	 * @apiSuccess {Integer} rows.cd 字段长度	
	 * @apiSuccess {String} rows.name 字段名字	
	 * @apiSuccess {String} rows.yzlx 字段验证类型	
	 * @apiSuccess {String} rows.iskey 是否主键	
	 * @apiSuccess {String} rows.isnull 是否允许为空	
	 * @apiSuccess {String} rows.isquery 是否允许查询	
	 * @apiSuccess {String} rows.isshow 是否显示在表单	
	 * @apiSuccess {String} rows.isshowlb 是否显示为列表	
	 * @apiSuccess {Integer} rows.len 字段显示长度	
	 * @apiSuccess {String} rows.zzd 主表字段	
	 * @apiSuccess {String} rows.zb 主表	
	 * @apiSuccess {String} rows.jzdm 旧字段名字	
	 * @apiSuccess {Integer} rows.xh 序号	
	 * @apiSuccess {Integer} rows.plen 小数点位数	
	 * @apiSuccess {String} rows.cxms 查询模式	
	 * @apiSuccess {String} rows.xslx 显示类型	
	 * @apiSuccess {String} rows.lx 字段类型	
	 * @apiSuccess {String} rows.bid 表ID	
	 * @apiSuccess {String} rows.kzjson 扩展JSON	
	 * @apiSuccess {String} rows.whr 维护人名字	
	 * @apiSuccess {Date} rows.whsj 维护时间	
	 * @apiSuccess {String} rows.whrid 维护人ID	
	 * @apiSuccess {String} rows.cjr 创建人名字	
	 * @apiSuccess {Date} rows.cjsj 创建时间	
	 * @apiSuccess {String} rows.cjrid 创建人ID	
	 * @apiSuccess {Integer} rows.version 数据记录版本	
	 * @apiSuccess {String} rows.deleted 逻辑删除标记位	
	 * @apiSuccess {String} rows.chgtype 编辑表单时记录字段动态, A:Add,U:Update,D:Delete	
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * 	{"total",25, "current":1, "size":10, "pages":3, "rows":[
	 		{
			"id" : "字段明细主键",	
			"nr" : "字段内容",	
			"zdzd" : "字典字段",	
			"zdbm" : "字典表名",	
			"zdwb" : "字典文本",	
			"mr" : "字段默认值",	
			"href" : "字段超链接",	
			"cd" : "字段长度",	
			"name" : "字段名字",	
			"yzlx" : "字段验证类型",	
			"iskey" : "是否主键",	
			"isnull" : "是否允许为空",	
			"isquery" : "是否允许查询",	
			"isshow" : "是否显示在表单",	
			"isshowlb" : "是否显示为列表",	
			"len" : "字段显示长度",	
			"zzd" : "主表字段",	
			"zb" : "主表",	
			"jzdm" : "旧字段名字",	
			"xh" : "序号",	
			"plen" : "小数点位数",	
			"cxms" : "查询模式",	
			"xslx" : "显示类型",	
			"lx" : "字段类型",	
			"bid" : "表ID",	
			"kzjson" : "扩展JSON",	
			"whr" : "维护人名字",	
			"whsj" : "维护时间",	
			"whrid" : "维护人ID",	
			"cjr" : "创建人名字",	
			"cjsj" : "创建时间",	
			"cjrid" : "创建人ID",	
			"version" : "数据记录版本",	
			"deleted" : "逻辑删除标记位",	
			"chgtype" : "编辑表单时记录字段动态, A:Add,U:Update,D:Delete",	
	 		}
	 	]}
	 * 	{"rows":[
	 		{
			"id" : "字段明细主键",	
			"nr" : "字段内容",	
			"zdzd" : "字典字段",	
			"zdbm" : "字典表名",	
			"zdwb" : "字典文本",	
			"mr" : "字段默认值",	
			"href" : "字段超链接",	
			"cd" : "字段长度",	
			"name" : "字段名字",	
			"yzlx" : "字段验证类型",	
			"iskey" : "是否主键",	
			"isnull" : "是否允许为空",	
			"isquery" : "是否允许查询",	
			"isshow" : "是否显示在表单",	
			"isshowlb" : "是否显示为列表",	
			"len" : "字段显示长度",	
			"zzd" : "主表字段",	
			"zb" : "主表",	
			"jzdm" : "旧字段名字",	
			"xh" : "序号",	
			"plen" : "小数点位数",	
			"cxms" : "查询模式",	
			"xslx" : "显示类型",	
			"lx" : "字段类型",	
			"bid" : "表ID",	
			"kzjson" : "扩展JSON",	
			"whr" : "维护人名字",	
			"whsj" : "维护时间",	
			"whrid" : "维护人ID",	
			"cjr" : "创建人名字",	
			"cjsj" : "创建时间",	
			"cjrid" : "创建人ID",	
			"version" : "数据记录版本",	
			"deleted" : "逻辑删除标记位",	
			"chgtype" : "编辑表单时记录字段动态, A:Add,U:Update,D:Delete",	
			}
	 	]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/getList/{id}")
	@ResponseBody
	public Object getList(@PathVariable("id") String moduleId) {
		//SysBxx sysbxx = new SysBxx();
		//sysbxx.setId(moduleId);
		//String bm = sysbxx.getBm();
		SysZdxx zdxxParm = new SysZdxx();
		zdxxParm.setBid(moduleId);
		zdxxParm.setDeleted(Constants.DelInd.FALSE);
		zdxxParm.setIsshowlb(Constants.YES);
		RsEntityWrapper<SysZdxx> wrapper = new RsEntityWrapper<>(zdxxParm);
		//wrapper.andNew("ZDXX_Isshowlb = #{0} or ZDXX_name = #{1} or ZDXX_name = #{2} ", Constants.YES, bm + "_ID", bm + "_VERSION");
		List<SysZdxx> zdxxList = generalMapper.selectList(wrapper);
		JSONObject result = new JSONObject();
		if (zdxxList != null){
			//将查询条件字段全部返回
			Object[] arr = zdxxList.stream()
					.map((x)->{
				JSONObject zdxx = new JSONObject();
				zdxx.put("id", x.getId());
				zdxx.put("bid", x.getBid());
				zdxx.put("nr", x.getNr());
				zdxx.put("zdzd", x.getZdzd());
				zdxx.put("zdbm", x.getZdbm());
				zdxx.put("zdwb", x.getZdwb());
				zdxx.put("mr", x.getMr());
				zdxx.put("href", x.getHref());
				zdxx.put("name", x.getName());
				zdxx.put("yzlx", x.getYzlx());
				zdxx.put("iskey", x.getIskey());
				zdxx.put("isnull", x.getIsnull());
				zdxx.put("isshow", x.getIsshow());
				zdxx.put("ishowlb", x.getIsshowlb());
				zdxx.put("len", x.getLen());
				zdxx.put("zzd", x.getZzd());
				zdxx.put("zb", x.getZb());
				zdxx.put("jzdm", x.getJzdm());
				zdxx.put("xh", x.getXh());
				zdxx.put("plen", x.getPlen());
				zdxx.put("cxms", x.getCxms());
				zdxx.put("xslx", x.getXslx());
				zdxx.put("lx", x.getLx());
				zdxx.put("kzjson", x.getKzjson());
				zdxx.put("whr", x.getWhr());
				zdxx.put("whsj", x.getWhsj());
				zdxx.put("whrid",x.getWhrid());
				zdxx.put("cjr", x.getCjr());
				zdxx.put("cjsj", x.getCjsj());
				zdxx.put("cjrid", x.getCjrid());
				return zdxx;	
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
	 * @api {POST} /admin/moduleTest/getEdit/{id} 获取sys_zdxx里所有在表单中显示的的字段列表
	 * @apiGroup Module Test
	 * @apiParamExample {json} Request-Example: 
	 * 		{"current":1, "size":10, "orderBy":"name", "asc":"true"}
	 		{"name":"123", "orderby":"name", "comments":"不分页,current为null"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} rows.id 字段明细主键	
	 * @apiSuccess {String} rows.nr 字段内容	
	 * @apiSuccess {String} rows.zdzd 字典字段	
	 * @apiSuccess {String} rows.zdbm 字典表名	
	 * @apiSuccess {String} rows.zdwb 字典文本	
	 * @apiSuccess {String} rows.mr 字段默认值	
	 * @apiSuccess {String} rows.href 字段超链接	
	 * @apiSuccess {Integer} rows.cd 字段长度	
	 * @apiSuccess {String} rows.name 字段名字	
	 * @apiSuccess {String} rows.yzlx 字段验证类型	
	 * @apiSuccess {String} rows.iskey 是否主键	
	 * @apiSuccess {String} rows.isnull 是否允许为空	
	 * @apiSuccess {String} rows.isquery 是否允许查询	
	 * @apiSuccess {String} rows.isshow 是否显示在表单	
	 * @apiSuccess {String} rows.isshowlb 是否显示为列表	
	 * @apiSuccess {Integer} rows.len 字段显示长度	
	 * @apiSuccess {String} rows.zzd 主表字段	
	 * @apiSuccess {String} rows.zb 主表	
	 * @apiSuccess {String} rows.jzdm 旧字段名字	
	 * @apiSuccess {Integer} rows.xh 序号	
	 * @apiSuccess {Integer} rows.plen 小数点位数	
	 * @apiSuccess {String} rows.cxms 查询模式	
	 * @apiSuccess {String} rows.xslx 显示类型	
	 * @apiSuccess {String} rows.lx 字段类型	
	 * @apiSuccess {String} rows.bid 表ID	
	 * @apiSuccess {String} rows.kzjson 扩展JSON	
	 * @apiSuccess {String} rows.whr 维护人名字	
	 * @apiSuccess {Date} rows.whsj 维护时间	
	 * @apiSuccess {String} rows.whrid 维护人ID	
	 * @apiSuccess {String} rows.cjr 创建人名字	
	 * @apiSuccess {Date} rows.cjsj 创建时间	
	 * @apiSuccess {String} rows.cjrid 创建人ID	
	 * @apiSuccess {Integer} rows.version 数据记录版本	
	 * @apiSuccess {String} rows.deleted 逻辑删除标记位	
	 * @apiSuccess {String} rows.chgtype 编辑表单时记录字段动态, A:Add,U:Update,D:Delete	
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * 	{"total",25, "current":1, "size":10, "pages":3, "rows":[
	 		{
			"id" : "字段明细主键",	
			"nr" : "字段内容",	
			"zdzd" : "字典字段",	
			"zdbm" : "字典表名",	
			"zdwb" : "字典文本",	
			"mr" : "字段默认值",	
			"href" : "字段超链接",	
			"cd" : "字段长度",	
			"name" : "字段名字",	
			"yzlx" : "字段验证类型",	
			"iskey" : "是否主键",	
			"isnull" : "是否允许为空",	
			"isquery" : "是否允许查询",	
			"isshow" : "是否显示在表单",	
			"isshowlb" : "是否显示为列表",	
			"len" : "字段显示长度",	
			"zzd" : "主表字段",	
			"zb" : "主表",	
			"jzdm" : "旧字段名字",	
			"xh" : "序号",	
			"plen" : "小数点位数",	
			"cxms" : "查询模式",	
			"xslx" : "显示类型",	
			"lx" : "字段类型",	
			"bid" : "表ID",	
			"kzjson" : "扩展JSON",	
			"whr" : "维护人名字",	
			"whsj" : "维护时间",	
			"whrid" : "维护人ID",	
			"cjr" : "创建人名字",	
			"cjsj" : "创建时间",	
			"cjrid" : "创建人ID",	
			"version" : "数据记录版本",	
			"deleted" : "逻辑删除标记位",	
			"chgtype" : "编辑表单时记录字段动态, A:Add,U:Update,D:Delete",	
	 		}
	 	]}
	 * 	{"rows":[
	 		{
			"id" : "字段明细主键",	
			"nr" : "字段内容",	
			"zdzd" : "字典字段",	
			"zdbm" : "字典表名",	
			"zdwb" : "字典文本",	
			"mr" : "字段默认值",	
			"href" : "字段超链接",	
			"cd" : "字段长度",	
			"name" : "字段名字",	
			"yzlx" : "字段验证类型",	
			"iskey" : "是否主键",	
			"isnull" : "是否允许为空",	
			"isquery" : "是否允许查询",	
			"isshow" : "是否显示在表单",	
			"isshowlb" : "是否显示为列表",	
			"len" : "字段显示长度",	
			"zzd" : "主表字段",	
			"zb" : "主表",	
			"jzdm" : "旧字段名字",	
			"xh" : "序号",	
			"plen" : "小数点位数",	
			"cxms" : "查询模式",	
			"xslx" : "显示类型",	
			"lx" : "字段类型",	
			"bid" : "表ID",	
			"kzjson" : "扩展JSON",	
			"whr" : "维护人名字",	
			"whsj" : "维护时间",	
			"whrid" : "维护人ID",	
			"cjr" : "创建人名字",	
			"cjsj" : "创建时间",	
			"cjrid" : "创建人ID",	
			"version" : "数据记录版本",	
			"deleted" : "逻辑删除标记位",	
			"chgtype" : "编辑表单时记录字段动态, A:Add,U:Update,D:Delete",	
			}
	 	]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping("/getEdit/{id}")
	@ResponseBody
	public Object getEdit(@PathVariable("id") String moduleId) {
		SysZdxx zdxxParm = new SysZdxx();
		zdxxParm.setBid(moduleId);
		zdxxParm.setDeleted(Constants.DelInd.FALSE);
		zdxxParm.setIsshow(Constants.YES);
		RsEntityWrapper<SysZdxx> wrapper = new RsEntityWrapper<>(zdxxParm);
		List<SysZdxx> zdxxList = generalMapper.selectList(wrapper);
		JSONObject result = new JSONObject();
		if (zdxxList != null){
			Object[] arr = zdxxList.stream()
					.map((x)->{
				JSONObject zdxx = new JSONObject();
				zdxx.put("id", x.getId());
				zdxx.put("bid", x.getBid());
				zdxx.put("nr", x.getNr());
				zdxx.put("zdzd", x.getZdzd());
				zdxx.put("zdbm", x.getZdbm());
				zdxx.put("zdwb", x.getZdwb());
				zdxx.put("mr", x.getMr());
				zdxx.put("href", x.getHref());
				zdxx.put("name", x.getName());
				zdxx.put("yzlx", x.getYzlx());
				zdxx.put("iskey", x.getIskey());
				zdxx.put("isnull", x.getIsnull());
				zdxx.put("isshow", x.getIsshow());
				zdxx.put("ishowlb", x.getIsshowlb());
				zdxx.put("len", x.getLen());
				zdxx.put("zzd", x.getZzd());
				zdxx.put("zb", x.getZb());
				zdxx.put("jzdm", x.getJzdm());
				zdxx.put("xh", x.getXh());
				zdxx.put("plen", x.getPlen());
				zdxx.put("cxms", x.getCxms());
				zdxx.put("xslx", x.getXslx());
				zdxx.put("lx", x.getLx());
				zdxx.put("kzjson", x.getKzjson());
				zdxx.put("whr", x.getWhr());
				zdxx.put("whsj", x.getWhsj());
				zdxx.put("whrid",x.getWhrid());
				zdxx.put("cjr", x.getCjr());
				zdxx.put("cjsj", x.getCjsj());
				zdxx.put("cjrid", x.getCjrid());
				return zdxx;	
			}).toArray();
			result.put(RECORDS, arr);
			result.put(TOTAL, arr.length);
		}else{
			result.put(RECORDS, new JSONArray(0));
			result.put(TOTAL, 0);
		}
		return getSuccessResult(result);
	}
	
	@RequestMapping("/getDetailTableList/{id}")
	@ResponseBody
	public Object getDetailTableList(@PathVariable("id") String masterId) {
		List<SysBxx> bxxList = cgMapper.getDetailTableList(masterId, Constants.DelInd.FALSE);
		JSONObject result = new JSONObject();
		if (bxxList != null){
			Object[] arr = bxxList.stream()
					.map((x)->{
				JSONObject bxx = new JSONObject();
				bxx.put("id", x.getId());
				bxx.put("bm", x.getBm());
				return bxx;	
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
	 * @api {POST} /admin/moduleTest/query 功能测试查询
	 * @apiGroup Module Test
	 * 
	 * @apiParam {String} bid 表id
	 * @apiParam {String} bm 表名
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * {"(B)TEST_age":"0","(E)TEST_age":"10000","TEST_type":"1","TEST_sex":"1","TEST_name":"1","id":"c35e5a1bf0504495aaca3d07fce977aa"}
	 * @apiSuccess {String} id 表id
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * {"total":4,"rows":[{"stu_whsj":1481272012000,"stu_id":"62ca972a13154078ab4ebed391bb023a","stu_version":1,"stu_cjsj":1481272012000,"stu_whrid":"system","stu_age":1,"stu_name":"1","stu_whr":"system","stu_deleted":"F","stu_cjrid":"system","stu_cjr":"system"},{"stu_whsj":1481272012000,"stu_id":"a243d30041d84fd7a0dfc73d14fcd436","stu_version":3,"stu_cjsj":1481258832000,"stu_whrid":"system","stu_age":100,"stu_name":"test","stu_whr":"system","stu_deleted":"F","stu_cjrid":"system","stu_cjr":"system"},{"stu_whsj":1481249289000,"stu_id":"a400bb5016894e79b4286473a08f28bd","stu_version":1,"stu_cjsj":1481249289000,"stu_whrid":"system","stu_age":1,"stu_name":"1","stu_whr":"system","stu_deleted":"F","stu_cjrid":"system","stu_cjr":"system"},{"stu_whsj":1481192389000,"stu_id":"f1526680ffb544feac447663f4589b6a","stu_version":1,"stu_cjsj":1481192389000,"stu_whrid":"system","stu_age":1,"stu_name":"1","stu_whr":"system","stu_deleted":"F","stu_cjrid":"system","stu_cjr":"system"}]}
	 * 
	 * @return List<Map<String,Object>>
	 * @throws BuzException 
	 */
	@RequestMapping(value="/query", method=RequestMethod.POST)
	@ResponseBody
	public Object moduleQuery(@RequestBody JSONObject parm){
		String bid = parm.getString("id");
		//判断参数有效性
		if (bid == null || "".equals(bid)) {
			return getInvalidParamResult();
		}
		
		JSONObject result =  new JSONObject();
		Pagination page = new Pagination(parm.getIntValue(CURRENT), parm.getIntValue(SIZE));
		List<Map<String,Object>> recordsMaster = moduleTestService.moduleGetList(parm, bid, page);
		result.put(RECORDS, recordsMaster);
//		int RecordCount;
//		if ((recordsMaster != null) && (recordsMaster.size() != 0)){
//			RecordCount = recordsMaster.size();
//		}
//		else{
//			RecordCount = 0;
//		}
//		result.put(TOTAL, RecordCount);
		result.put(TOTAL, moduleTestService.moduleGetRecordCount(parm, bid));
		return getSuccessResult(result);
	}
	
	/**
	 * @api {POST} /admin/moduleTest/query/relationtable 功能测试根据主表查询指定明细表数据
	 * @apiGroup Module Test
	 * 
	 * @apiParam {String} sourceTable 源数据表id
	 * @apiParam {String} targetTable 目标查询表id
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * {"targetTable":"4143ca0e5b6a496487226e95056cfa40","sourceTable":"46864e6d14e44f6aa6c6ae415b67a5ed","resume_id":"bd0a525b3103487ab2601f8473fe1b27"}
	 * @apiSuccess {String} id 表id
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * {"rows":[{"projp_deleted":"F","projp_resumeId":"bd0a525b3103487ab2601f8473fe1b27","projp_name":"项目1","projp_begin":1477978547000,"projp_end":1490938554000,"projp_whrid":"f44ed61e43fd4daaaa13d5aca3b425c6","projp_cjsj":1488778582000,"projp_content":"经理","projp_whr":"f44ed61e43fd4daaaa13d5aca3b425c6","projp_whsj":1488778582000,"projp_id":"65860e36f5d64c649be31fa81989412d","projp_version":1,"projp_cjrid":"f44ed61e43fd4daaaa13d5aca3b425c6","projp_cjr":"f44ed61e43fd4daaaa13d5aca3b425c6"}]}
	 * 
	 * @return List<Map<String,Object>>
	 * @throws BuzException 
	 */
	@RequestMapping(value="/query/relationtable", method=RequestMethod.POST)
	@ResponseBody
	public Object moduleQueryRelationTable(@RequestBody JSONObject parm){
		String sourceTable = parm.getString("sourceTable");
		String targetTable = parm.getString("targetTable");
		//判断参数有效性
		if ("".equals(sourceTable)) {
			return getInvalidParamResult();
		}else if ("".equals(targetTable)) {
			return getInvalidParamResult();
		}
		
		JSONObject result =  new JSONObject();
		List<Map<String,Object>> reocrds = moduleTestService.moduleQueryRelationTable(parm, sourceTable, targetTable);
		result.put(RECORDS, reocrds);
		return getSuccessResult(result);
	}
	
	/**
	 * @api {POST} /admin/moduleTest/add 功能测试增加
	 * @apiGroup Module Test
	 * 
	 * @apiParam {String} id 表id
	 * @apiParam {String} tablename 表名
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * {"id":"ddaaf72259c84a5f81f6ad9b13b39086","detail":[{"records":[{"tag":"A","TBLMX_fid":"1","TBLMX_name":"1"},{"tag":"A","TBLMX_fid":"1","TBLMX_name":"1"},{"tag":"A","TBLMX_fid":"1","TBLMX_name":"1"}],"id":"870b1424b1fb4a2483b7bb3dd0f7c951"}],"tablename":"TBL","master":{"TBL_name":"1","tag":"A"}}
	 * @apiSuccess {String} id 表id
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * {}
	 * 
	 * @return List<Map<String,Object>>
	 * @throws BuzException 
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public Object moduleAdd(@RequestBody JSONObject parm){
		String bid = parm.getString("id");
		JSONObject json = JSONObject.parseObject(parm.toJSONString());
		RsCommonResponse resp = json.toJavaObject(RsCommonResponse.class);
		resp.setId(bid);
		//判断参数有效性
		if ("".equals(bid)){
			return getInvalidParamResult();
		}
		JSONObject result =  new JSONObject();
		if ( moduleTestService.moduleAdd(resp)){
			return getSuccessResult(result);
		}else {
			//返回错误信息
//			result.put(VALIDATE_ERRORS, moduleTestService.getReturnMessage());
			addValidationError(moduleTestService.getReturnMessage());
			return getInvalidParamResult(result);
		}
	}
	
	/**
	 * @api {PUT} /admin/moduleTest/update 功能测试修改
	 * @apiGroup Module Test
	 * 
	 * @apiParam {String} id 表id
	 * @apiParam {String} tablename 表名
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * {"id":"ddaaf72259c84a5f81f6ad9b13b39086","detail":[{"records":[{"tag":"A","TBLMX_fid":"1","TBLMX_name":"1","TBLMX_id":""},{"tag":"D","TBLMX_fid":"1","TBLMX_name":"1","TBLMX_id":"a400bb5016894e79b4286473a08f28bd"},{"tag":"U","TBLMX_fid":"1","TBLMX_name":"1","TBLMX_id":"s400bb5016894edfda08f28bb"}],"id":"870b1424b1fb4a2483b7bb3dd0f7c951"}],"tablename":"TBL","master":{"TBL_name":"1","tag":"A"}}
	 * @apiSuccess {String} id 表id
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * {"id":"ddaaf72259c84a5f81f6ad9b13b39086"}
	 * 
	 * @return List<Map<String,Object>>
	 * @throws BuzException 
	 */
	@RequestMapping(value="/update", method=RequestMethod.PUT)
	@ResponseBody
	public Object moduleUpdate(@RequestBody JSONObject parm){
		String bid = parm.getString("id");
		JSONObject json = JSONObject.parseObject(parm.toJSONString());
		RsCommonResponse resp = json.toJavaObject(RsCommonResponse.class);
		resp.setId(bid);
		//判断参数有效性
		if ("".equals(bid)){
			return getInvalidParamResult();
		}
		JSONObject result =  new JSONObject();
		if (moduleTestService.moduleUpdate(resp)){
			SysBxx bxxParm = new SysBxx();
			bxxParm.setId(bid);
			bxxParm.setDeleted(Constants.DelInd.FALSE);
			String bm = generalMapper.selectById(bid, SysBxx.class).getBm();
			result.put(ID, resp.getMaster().getString(moduleTestService.getField(bm,"id")));
			return getSuccessResult(result);
		}
		else{
			//返回错误信息
//			result.put(VALIDATE_ERRORS, moduleTestService.getReturnMessage());
			addValidationError(moduleTestService.getReturnMessage());
			return getInvalidParamResult(result);
		}
	}	
	
	/**
	 * @api {DELETE} /admin/moduleTest 功能测试删除
	 * @apiGroup Module Test
	 * 
	 * @apiParam {String} id 表id
	 * @apiParam {String} tablename 表名
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * {"id":"adabcbc62c0841b5a320182e24bbee33","tablename":"baoguo_stu","rows":"[\"a243d30041d84fd7a0dfc73d14fcd436\"]"}
	 * @apiSuccess {String} id 表id
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * {"rows":["a243d30041d84fd7a0dfc73d14fcd436"]}
	 * 
	 * @return List<Map<String,Object>>
	 * @throws BuzException 
	 */
	@DeleteMapping
	@ResponseBody
	public Object moduleDelete(@RequestBody JSONObject parm){
		String bid = parm.getString("id");
		String bm = parm.getString("tablename");
		//判断参数有效性
		if (("".equals(bid)) || ("".equals(bm))){
			return getInvalidParamResult();
		}
		JSONObject result = new JSONObject();
		ArrayList<String> arr = new ArrayList<>();
		ArrayList<String> error = new ArrayList<>();
		JSONArray recordArray = parm.getJSONArray(RECORDS);
		for(int i=0; i<recordArray.size(); i++){
			if (moduleTestService.moduleDelete(recordArray.getString(i), bid)){
				arr.add(recordArray.getString(i));
			}else{
				error.add(moduleTestService.getReturnMessage());
			}
		}
		result.put(RECORDS, arr.toArray());
		if (error.size() > 0){
//			result.put(VALIDATE_ERRORS, error.toArray());
			addValidationError(JSON.toJSONString(error));
		}
		//如果部分删除成功，则返回成功，同时带提示语句， 否则返回失败
		if (arr.size() > 0){
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult(result);
		}
		
	}
	
	public List<SysBxx> getDetailBxxListByMaster(@RequestBody JSONObject parm){
		return null;
				
	}
	
	
	/**
	 * @api {POST} /admin/moduleTest/sql/execute/{tableid} 自定义按钮调用后台sql
	 * @apiGroup Module Test
	 * 
	 * @apiParam {String} code 按钮编码
	 * @apiParam {String} id 业务数据id
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * 					{"code":"audit","rows":[{"resume_id":"xxx","resume_name":"admin",...}，{"resume_id":"yyy","resume_name":"admin",...}]}
	 * 
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 *  @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * 		{
	 * 			"verror":"表单参数不能为空！"
	 * 		}
	 * 
	 * @return
	 */
	@RequestMapping(value="/sql/execute/{tableid}", method=RequestMethod.POST)
	@ResponseBody
	public Object executeZqsql(@PathVariable("tableid") String id, @RequestBody JSONObject parm){
		JSONObject result =  new JSONObject();
		String bid = id;                           //表单参数
		Boolean bSuccess = false;
		//String dataId = parm.getString(Constants.ID);
		String code = parm.getString("code");      //按钮参数
		JSONArray recordArray = parm.getJSONArray(RECORDS);
		
		if (bid == null || bid.equals("")){
//			result.put(VALIDATE_ERRORS, "表单参数不能为空！");
			addValidationError("表单参数不能为空！");
			return getInvalidParamResult(result);
		}else if (code == null || code.equals("")){
//			result.put(VALIDATE_ERRORS, "按钮编码不能为空！");
			addValidationError("按钮编码不能为空！");
			return getInvalidParamResult(result);
		}
		
		if (recordArray.size() > 0){
			ArrayList<String> error = new ArrayList<>();
			for (int i = 0; i < recordArray.size(); i++) {
				JSONObject record = recordArray.getJSONObject(i);			
				if (!moduleTestService.executeZqsql(bid, code, record)) {
					error.add( moduleTestService.getReturnMessage());
				}else
					bSuccess = true;
			}
			if (error.size() > 0){
				result.put(VALIDATE_ERRORS, error.toArray());
			}
			//如果部分删除成功，则返回成功，同时带提示语句， 否则返回失败
			if (bSuccess){
				return getSuccessResult(result);
			}else{
				return getInvalidParamResult(result);
			}
		}else{
			return getInvalidParamResult(result);
		}	
	}
	
}
