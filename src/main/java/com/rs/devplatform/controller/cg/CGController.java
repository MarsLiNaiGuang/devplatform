package com.rs.devplatform.controller.cg;

import java.io.UnsupportedEncodingException;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.rs.devplatform.common.BuzException;
import com.rs.devplatform.common.CGConstants;
import com.rs.devplatform.common.CGConstants.ColTypes;
import com.rs.devplatform.common.CGConstants.InputTypes;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysColumns;
import com.rs.devplatform.persistent.SysCustbt;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.persistent.SysZqjs;
import com.rs.devplatform.persistent.SysZqsql;
import com.rs.devplatform.service.cg.CGService;
import com.rs.devplatform.vo.BaseColumnVO;
import com.rs.devplatform.vo.ColumnVO;
import com.rs.devplatform.vo.TableVO;
import com.rs.devplatform.vo.onlinefc.OnlineFuncVO;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.controller.base.BaseController;

@Controller
@RequestMapping("/cg")
public class CGController extends BaseController {
	
	private static final String FORCE_DROP = "forceDrop";
	
	@Autowired
	CGService cgService;
	@Autowired
	OnlineFuncController onlineFcController;
	
	private final String TABLE_NAME = "tablename";
	private final String FORM_TYPE = "formtype";
	private final String TABLE_DESCP = "tabledescp";
	private final String TABLE_VERSION = "version";
	private final String TABLE_TYPE = "tabletype";
	private final String FC_FLAG = "fcflag";
	private final String IS_DB_SYN = "isdbsyn";
	
	@Autowired
	RsGeneralMapper generalMapper;

	/**
	 * @api {GET} /cg/formlist 1.跳转表单list页面
	 * @apiGroup CG

	 * @apiSuccessExample {json} Sample:
	 * 			{"page":"cg/formlist.html"}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/formlist", method=RequestMethod.GET)
	public String formListPage() {
		return "cg/formlist";
	}
	
	/**
	 * @api {POST} /cg/form/list 2.查询表单list
	 * @apiGroup CG
	 * @apiParam {String} tablename 表名字(模糊查询)
	 * @apiParam {String} fcflag 功能编码(模糊查询)
	 * @apiParam {Integer} tabletype 表单类型(1:单表，2：主表，3：附表)
	 * @apiParamExample {json} Request-Example: 
	 *  {"tablename":"test", "tabletype":1}
	 * 
	 * @apiSuccess {String} id 表单id
	 * @apiSuccess {Integer} version 表单version
	 * @apiSuccess {String} tablename 表名称
	 * @apiSuccess {String} tabledescp 表描述
	 * @apiSuccess {String} fcflag 功能编码
	 * @apiSuccess {String} tabletype 表单类型(1:单表，2：主表，3：附表)
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * 	{
	 * 		"total":2,
			"records":[
				{"tabletype":1,"tabledescp":"ju table 2674","fcflag":"tbl_ju1510","formtype":"N","id":"ec9ec852205c4c288e320ff6f029fc6f","tablename":"tbl_ju_2674","version":1},
				{"tabletype":1,"tabledescp":"ju table 1970","fcflag":"tbl_ju1510","formtype":"N","id":"fd5a58d8f09c41549f4bbd4131a5d990","tablename":"tbl_ju_1970","version":1}
			]
		}
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/form/list",method=RequestMethod.POST )
	@ResponseBody
	public Object formList(@RequestBody JSONObject parm, HttpServletRequest request) {
		SysBxx bxx = new SysBxx();
		String name = parm.getString(TABLE_NAME);
		String fcflag = parm.getString(FC_FLAG);
		Integer type = parm.getInteger(TABLE_TYPE);
		Integer current = parm.getInteger(CURRENT);
		Integer size = parm.getInteger(SIZE);
		if(type!=null){
			bxx.setLx(type);
		}
		RsEntityWrapper<SysBxx> wrapper = new RsEntityWrapper<>(bxx);
		if(!StringUtils.isEmpty(name)){
			wrapper.like("BXX_BM", name.toLowerCase());
		}
		if(!StringUtils.isEmpty(fcflag)){
			wrapper.like("BXX_FCFLAG", fcflag);
		}
		List<SysBxx> formList = null;
		int total = 0;
		if(current!=null){
			if(size==null){
				size = DEFAULT_PAGE_SIZE;
			}
			Page<Object> pagination = new Page<>(current, size);
			formList = generalMapper.selectPage(pagination, wrapper);
			total = generalMapper.selectCountByEW(wrapper);
		}else{
			formList = generalMapper.selectList(wrapper);
			total = formList==null?0:formList.size();
		}
		JSONObject result = new JSONObject();
		if(formList!=null){
			Object[] array = formList.stream().map((x)->{
				JSONObject obj = new JSONObject();
				obj.put(ID, x.getId());
				obj.put(TABLE_NAME, x.getBm());
				obj.put(FORM_TYPE, "N");//TODO：
				obj.put(TABLE_DESCP, x.getNr());
				obj.put(TABLE_VERSION, x.getVersion());
				obj.put(IS_DB_SYN, x.getIsdbsyn());
				obj.put(TABLE_TYPE, x.getLx());
				obj.put(FC_FLAG, x.getFcflag());
				return obj;
			}).toArray();
			result.put(RECORDS, array);
			
		}else{
			result.put(RECORDS, new JSONArray(0));
		}
		result.put(TOTAL, total);
		return getSuccessResult(result);
	}
	
	/**
	 * @api {POST} /cg/form 4.新增表单
	 * @apiDescription 当用树形展示时必须填入父/子节点对应的ID，并且不能相同，父/子节点的来源是columns
	 * @apiGroup CG
	 * 
	 * @apiParam {String} sql SQL语句（不是必须的）
	 * @apiParam {String} tablename 主表名称（**必填**）
	 * @apiParam {String} tabledescp 主表名称（**必填**）
	 * @apiParam {String} pktype 主表主键类型（**必填**）
	 * @apiParam {String} fcflag 功能编码（**必填**）
	 * @apiParam {Integer} relationType 附表类型(0：一对多，1：一对一)
	 * @apiParam {Integer} relationSeq 附表顺序号
	 * @apiParam {String} pktype PK类型(UUID:32位唯一编码)
	 * @apiParam {String} multiselect 是否允许复选(Y: Yes, N: No)
	 * @apiParam {String} ispage 是否分页(Y: Yes, N: No)
	 * @apiParam {String} istree 是否树(Y: Yes, N: No)
	 * @apiParam {String} child	子节点对应的字段
	 * @apiParam {String} parent 父节点对应的字段
	 * 
	 * @apiParam {JSONArray} columns 查询字段列表
	 * @apiParam {String} columns.column 查询字段名
	 * @apiParam {String} columns.alias 查询字段别名,SQL中出现的AS后面的值
	 * @apiParam {String} columns.descp 查询字段描述(数据列名)
	 * 
	 * @apiParam {JSONArray} columns 查询数据列表
	 * @apiParam {String} columns.colName 字段名称（**必填**）
	 * @apiParam {String} columns.alias 字段别名
	 * @apiParam {String} columns.colMark 字段备注（**必填**）
	 * @apiParam {String} columns.colDefval 字段默认值
	 * @apiParam {String} columns.colType 字段类型(string/int/double/date/decimal/text/blob)（**必填**）
	 * @apiParam {String} columns.colIspk 字段是否主键，Y:是主键
	 * @apiParam {String} columns.isFormdisp 字段是否表单显示，Y:为显示在表单
	 * @apiParam {String} columns.isGriddisp 字段是否列表显示，**默认Y**:为显示在列表
	 * @apiParam {String} columns.colIsnull 字段是否允许空值，Y:可为空
	 * @apiParam {String} columns.inputType 字段控件类型(text/password/radio/checkbox/date/datetime/file/textarea/list/popup/image/umeditor)
	 * @apiParam {Integer} columns.inputLen 字段控件长度
	 * @apiParam {String} columns.isSearch 字段是否查询，Y:该字段可作为查询条件
	 * @apiParam {String} columns.searchType 字段查询类型，normal:普通查询，range:范围查询，fuzzy:模糊查询
	 * @apiParam {String} columns.validType 验证规则
	 * @apiParam {String} columns.dictTable 字典Table
	 * @apiParam {String} columns.dictCode 字典Code
	 * @apiParam {String} columns.dictText 字典Text
	 * 
	 * @apiParam {JSONArray} subtables 子表管理关系列表
	 * @apiParam {String} subtables.tablename 子表名称（**subtables有记录时必填**）
	 * @apiParam {String} subtables.colName 子表关联字段（**subtables有记录时必填**）
	 * @apiParam {String} subtables.mainColName 主表关联字段（**subtables有记录时必填**）
	 * 
	 * @apiParam {JSONArray} conditions 查询条件(SQL)
	 * @apiParam {String} conditions.colName 字段名称（**必填**）
	 * @apiParam {String} conditions.colMark 字段备注（**必填**）
	 * @apiParam {String} conditions.colType 字段类型(string/int/double/date/decimal/text/blob)（**必填**）
	 * @apiParam {String} conditions.inputType 字段控件类型(text/password/radio/checkbox/date/datetime/file/textarea/list/popup/image/umeditor)
	 * @apiParam {Integer} conditions.inputLen 字段控件长度
	 * @apiParam {String} conditions.searchType 字段查询类型，normal:普通查询，range:范围查询，fuzzy:模糊查询
	 * @apiParam {String} conditions.expr 条件表达式
	 * @apiParamExample {json} Request-Example: 
	 * {
 "tabledescp": "test",
 "fcflag": "unionFuncID",
 "istree":"Y",
 "child":"username", 
 "parent":"password",
 "columns": [{
  "colIspk": "Y",
  "colIsnull": "N",
  "isFormdisp": "N",
  "isgriddisp": "Y",
  "isSearch": "N",
  "colType": "string",
  "alias": "name",
  "inputType": "text",
  "colName": "username",
  "colMark": "mark0"
 },
 {
  "colIsnull": "Y",
  "isFormdisp": "Y",
  "isgriddisp": "Y",
  "isSearch": "Y",
  "colType": "string",
  "alias": "",
  "inputType": "text",
  "colName": "password",
  "colMark": "mark1",
  "searchType": "fuzzy"
 },
 {
  "colIsnull": "Y",
  "isFormdisp": "Y",
  "isgriddisp": "Y",
  "isSearch": "Y",
  "colType": "string",
  "alias": "",
  "inputType": "text",
  "colName": "dept.dept_name",
  "colMark": "mark2",
  "searchType": "fuzzy"
 }],
 "subtables":[{"colName":"subcolname_0","mainColName":"maincol1","tablename":"subtable0"},{"colName":"subcolname_1","mainColName":"main_col2","tablename":"subtable0"}],
 "conditions": [{
  "issearch": "Y",
  "expr": "username = #{abc}",
  "inputType": "text",
  "colName": "abc",
  "colMark": "mark0",
  "searchType": "fuzzy"
 }],
 "pktype": "UUID",
 "tablename": "user",
 "sql": "select username as name, password, dept.dept_name from user, sys_dept dept where dept.dept_id=user.dept_id and  username=#{abc} and password='123456'"
}
	 * @apiSuccess {String} id 表ID 
	 * @apiSuccess {Integer} version 表version
	 * @apiSuccessExample {json} Sample:
	 *  HTTP/1.1 200 OK
	 *  {
	 *    "id":"1234567",
	 *    "version":1
	 *  }
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 *  {
	 *    "verrors":[
	 *      {"errCode":"sql","errorMsg":"SQL不能为空"},
	 *      {"errCode":"sql","errorMsg":"SQL格式错误"},
	 *      {"errCode":"tablename","errorMsg":"表名不能为空"},
	 *      {"errCode":"tabledescp","errorMsg":"表描述不能为空"},
	 *      {"errCode":"pktype","errorMsg":"主键类型不能为空"},
	 *      {"errCode":"columns","errorMsg":"数据列不能为空"},
	 *      {"errCode":"tree","errorMsg":"树形展示不能不设置父节点和子节点"},
	 *      {"errCode":"tree","errorMsg":"父子节点不能相同"},
	 *      {"errCode":"child","errorMsg":"子节点在字段中不存在"},
	 *      {"errCode":"parent","errorMsg":"父节点在字段中不存在"}
	 *      ]
	 *  }
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 302 Duplicate record
	 *  {
	 *    "verrors":[
	 *      {"errCode":"fcflag","errorMsg":"功能编码重复"},
	 *      ]
	 *  }
	 * @return
	 * @throws BuzException 
	 */
	@RequestMapping(value="/form",method=RequestMethod.POST )
	@ResponseBody
	public Object addForm(@RequestBody JSONObject parm) throws BuzException {
		String sql = parm.getString(OnlineFuncController.SQL_PARM);
		if(StringUtils.isBlank(sql)){
			TableVO tableVO = parm.toJavaObject(TableVO.class);
			if(validateData(tableVO)){
				SysBxx bxxparm = new SysBxx();
				bxxparm.setFcflag(tableVO.getFcflag());
				if(isDuplicateBeforeCreate(bxxparm)){
					addValidationError("fcflag", "功能编码重复");
					return getDuplicateResult();
				}
				if("Y".equals(tableVO.getIstree())){
					if(StringUtils.isEmpty(tableVO.getChild()) || StringUtils.isEmpty(tableVO.getParent())){
						addValidationError("tree","树形展示不能不设置父节点和子节点");
						return getInvalidParamResult();
					}
					else if(tableVO.getChild().equals(tableVO.getParent())){
						addValidationError("tree","父子节点不能相同");
						return getInvalidParamResult();
					}
					else if(!tableVO.isInColumn(tableVO.getParent())){
						addValidationError("parent","父节点在字段中不存在");
						return getInvalidParamResult();
					}
					else if(!tableVO.isInColumn(tableVO.getChild())){
						addValidationError("child","子节点在字段中不存在");
						return getInvalidParamResult();
					}
				}
				if(cgService.addForm(tableVO)){
					JSONObject result = new JSONObject();
					result.put(ID, tableVO.getTableId());
					result.put(VERSION, tableVO.getVersion());
					return getSuccessResult(result);
				}
				return getModifiedResult();
			}else{
				return getInvalidParamResult();
			}
		}else{
			return onlineFcController.saveOnlineFunc(parm);
		}
	}
	
	/**
	 * @api {GET} /cg/form/{tableId} 3.查询表单详情
	 * @apiGroup CG

 	 * @apiSuccess {String} id 表id
 	 * @apiSuccess {String} tablename 表名字
	 * @apiSuccess {String} tabledescp 表描述
	 * @apiSuccess {String} fcflag 功能编码
	 * @apiSuccess {Integer} tabletype 表类型(1:单表，2：主表，3：附表)
	 * @apiSuccess {Integer} relationType 附表类型(0：一对多，1：一对一)
	 * @apiSuccess {Integer} relationSeq 附表顺序号
	 * @apiSuccess {String} pktype PK类型(UUID:32位唯一编码)
	 * @apiSuccess {String} multiselect 是否允许复选(Y: Yes, N: No)
	 * @apiSuccess {String} ispage 是否分页(Y: Yes, N: No)
	 * @apiSuccess {String} istree 是否树(Y: Yes, N: No)
	 * @apiSuccess {String} sql SQL
	 * 
	 * @apiSuccess {JSONArray} columns 字段
	 * @apiSuccess {String} columns.id 字段id
	 * @apiSuccess {Integer} columns.colSeq 字段序号
	 * @apiSuccess {String} columns.colName 字段名称
	 * @apiSuccess {String} columns.colMark 字段备注
	 * @apiSuccess {Integer} columns.colLen 字段长度
	 * @apiSuccess {Integer} columns.colDecpoint 字段小数点位数
	 * @apiSuccess {String} columns.colDefval 字段默认值
	 * @apiSuccess {String} columns.colType 字段类型
	 * @apiSuccess {String} columns.colIspk 字段是否主键，Y:是主键
	 * @apiSuccess {String} columns.colIsnull 字段是否允许空值，Y:可为空
	 * @apiSuccess {String} columns.isFormdisp 字段是否表单显示，Y:为显示在表单
	 * @apiSuccess {String} columns.isGriddisp 字段是否列表显示，Y:为显示在列表
	 * @apiSuccess {String} columns.inputType 字段控件类型
	 * @apiSuccess {Integer} columns.inputLen 字段控件长度
	 * @apiSuccess {String} columns.isSearch 字段是否查询，Y:该字段可作为查询条件
	 * @apiSuccess {String} columns.searchType 字段查询类型，normal:普通查询，range:范围查询
	 * @apiSuccess {String} columns.extjson 字段扩展参数
	 * @apiSuccess {String} columns.version 字段version
	 * @apiSuccess {String} columns.colHref 字段Href
	 * @apiSuccess {String} columns.validType 验证规则
	 * @apiSuccess {String} columns.dictTable 字典Table
	 * @apiSuccess {String} columns.dictCode 字典Code
	 * @apiSuccess {String} columns.dictText 字典Text
	 * 
	 * @apiSuccess {JSONArray} conditions 条件（只有SQL的情况下才有）
	 * @apiSuccess {String} conditions.id 条件字段id
	 * @apiSuccess {Integer} conditions.colSeq 条件字段序号
	 * @apiSuccess {String} conditions.colName 条件字段名称
	 * @apiSuccess {String} conditions.colMark 条件字段备注
	 * @apiSuccess {String} conditions.colType 条件字段类型
	 * @apiSuccess {String} conditions.inputType 字段控件类型
	 * @apiSuccess {Integer} conditions.inputLen 字段控件长度
	 * @apiSuccess {String} conditions.isSearch 字段是否查询，Y
	 * @apiSuccess {String} conditions.searchType 字段查询类型，normal:普通查询，range:范围查询
	 * @apiSuccess {String} conditions.version 字段version
	 * 
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * {
  "multiselect":"Y","tabletype":1,"tabledescp":"ju table -2657","fcflag": "tbl_ju9272",
  "id":"1e5b01331128477bb21fa917b4c52ea8","isdbsyn":"Y","pktype":"UUID","tablename":"tbl_ju-2657","version":1,"istree":"N",
  "columns":[
    {"colSeq":1,"colIspk":"Y","validType":"validType","colIsnull":"","isGriddisp":"","isFormdisp":"","colLen":10,"dictCode":"dictCode","isSearch":"","colType":"string","inputType":"text","id":"e5824ff05de6449290448742e714d3cb","dictText":"dictText","colDefval":"","colName":"name_0","colMark":"mark_0","inputLen":100,"searchType":"normal","dictTable":"dictTable","deleted":"F","colDecpoint":0},
    {"colSeq":2,"colIspk":"","validType":"validType","colIsnull":"Y","isGriddisp":"Y","isFormdisp":"Y","colLen":11,"dictCode":"dictCode","isSearch":"Y","colType":"string","inputType":"text","id":"e911e5efe0a54e829939e691e3880e4d","dictText":"dictText","colDefval":"","colName":"name_1","colMark":"mark_1","inputLen":100,"searchType":"range","dictTable":"dictTable","deleted":"F","colDecpoint":0},
  ],
  "conditions": [{
  "colSeq": 1,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "issearch": "Y",
  "colType": "string",
  "inputType": "text",
  "id": "f4b4de173a7e4611a1843009f1a2b112",
  "colName": "abc",
  "colMark": "mark0",
  "searchType": "fuzzy"
 },
 {
  "colSeq": 2,
  "isGriddisp": "Y",
  "isFormdisp": "Y",
  "issearch": "Y",
  "colType": "string",
  "inputType": "text",
  "id": "00239a735d984fd48674200d6c63b28a",
  "colName": "password",
  "colMark": "mark1",
  "searchType": "fuzzy"
 },
 {
  "colSeq": 3,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "issearch": "Y",
  "colType": "int",
  "inputType": "text",
  "id": "a3ec22545d79444290d3da9e50319135",
  "colName": "newColumn",
  "colMark": "newColumn mark",
  "inputLen": 10,
  "searchType": "range"
 }],
  }
	 * @return
	 */
	@GetMapping(value="/form/{tableId}")
	@ResponseBody
	public Object getFormDetail(@PathVariable("tableId") String tableId) {
		SysBxx bxx = generalMapper.selectById(tableId, SysBxx.class);
		if(bxx==null){
			return getInvalidParamResult();
		}
		if(StringUtils.isBlank(bxx.getIsql())){
			SysZdxx ziduanParm = new SysZdxx();
			ziduanParm.setBid(bxx.getId());
			RsEntityWrapper<SysZdxx> wrapper = new RsEntityWrapper<>(ziduanParm);
			wrapper.orderBy("ZDXX_XH");
			bxx.setZiduanList(generalMapper.selectList(wrapper));
			return getSuccessResult((JSONObject) JSON.toJSON(TableVO.toTableVO(bxx)));
		}else{
			return onlineFcController.getOnlineFunc(bxx);
		}
	}
	
	@RequestMapping(value="/form/{tableId}/fieldlistshown",method=RequestMethod.GET )
	@ResponseBody
	public Object getFieldList(@PathVariable("tableId") String tableId) {
		SysZdxx ziduanParm = new SysZdxx();
		ziduanParm.setBid(tableId);
		ziduanParm.setIsshow(Constants.YES);
		RsEntityWrapper<SysZdxx> wrapper = new RsEntityWrapper<>(ziduanParm);
		wrapper.orderBy("ZDXX_XH");
		return getSuccessResult(generalMapper.selectList(wrapper));
	}
	
	/**
	 * @api {GET} /cg/form/{tableId}/buttons 获取表单自定义按钮
	 * @apiGroup CG
	 * 
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
	 * 	{"rows":[
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
	 */
	@RequestMapping(value="/form/{tableId}/buttons",method=RequestMethod.GET )
	@ResponseBody
	public Object getCustButtons(@PathVariable("tableId") String tableId) {
		SysCustbt custbtParm = new SysCustbt();
		custbtParm.setBdid(tableId);
		custbtParm.setDeleted(Constants.DelInd.FALSE);
		return getSuccessResult(generalMapper.selectList(new RsEntityWrapper<>(custbtParm)));
	}
	
	/**
	 * @api {PUT} /cg/form/{tableId} 5.更新表单
	 * @apiDescription 当用树形展示时必须填入父/子节点对应的ID，并且不能相同，父/子节点的来源是columns
	 * @apiGroup CG

	 * @apiParam {String} tabledescp 表描述(**必填**)
	 * @apiParam {Integer} tabletype 表类型(1:单表，2：主表，3：附表)(**必填**)
	 * @apiParam {Integer} relationType 附表类型(0：一对多，1：一对一)(**必填**)
	 * @apiParam {String} pktype PK类型(UUID:32位唯一编码)(**必填**)
	 * @apiParam {String} multiselect 是否允许复选(Y: Yes, N: No)
	 * 
	 * @apiParam {String} ispage 是否分页(Y: Yes, N: No)
	 * @apiParam {String} istree 是否树(Y: Yes, N: No)
	 * @apiParam {Integer} version 表version(**必填**)
	 * @apiParam {String} child	子节点对应的字段
	 * @apiParam {String} parent 父节点对应的字段
	 * 
	 * @apiParam {JSONArray} columns 字段(**必填**)
	 * @apiParam {String} columns.id 字段id,新增的为空
	 * @apiParam {String} columns.colName 字段名称(**必填**)
	 * @apiParam {String} columns.colMark 字段备注(**必填**)
	 * @apiParam {String} columns.colType 字段类型(**必填**)
	 * @apiParam {String} columns.colIspk 字段是否主键，Y:是主键
	 * @apiParam {String} columns.colIsnull 字段是否允许空值，Y:可为空
	 * @apiParam {String} columns.isFormdisp 字段是否表单显示，Y:为显示在表单
	 * @apiParam {String} columns.isGriddisp 字段是否列表显示，Y:为显示在列表
	 * @apiParam {String} columns.inputType 字段控件类型
	 * @apiParam {Integer} columns.inputLen 字段控件长度
	 * @apiParam {String} columns.isSearch 字段是否查询，Y:该字段可作为查询条件
	 * @apiParam {String} columns.searchType 字段查询类型，normal:普通查询，range:范围查询
	 * @apiParam {String} columns.validType 验证规则
	 * @apiParam {String} columns.dictTable 字典Table
	 * @apiParam {String} columns.dictCode 字典Code
	 * @apiParam {String} columns.dictText 字典Text
	 * 
	 * @apiParam {JSONArray} subtables 子表管理关系列表
	 * @apiParam {String} subtables.tablename 子表名称（**subtables有记录时必填**）
	 * @apiParam {String} subtables.colName 子表关联字段（**subtables有记录时必填**）
	 * @apiParam {String} subtables.mainColName 主表关联字段（**subtables有记录时必填**）
	 * 
	 * @apiParam {JSONArray} conditions 查询条件(SQL情况)
	 * @apiParam {String} conditions.colName 字段名称(**必填**)
	 * @apiParam {String} conditions.colMark 字段备注(**必填**)
	 * @apiParam {String} conditions.colType 字段类型(string/int/double/date/decimal/text/blob)(**必填**)
	 * @apiParam {String} conditions.inputType 字段控件类型(text/password/radio/checkbox/date/datetime/file/textarea/list/popup/image/umeditor)
	 * @apiParam {Integer} conditions.inputLen 字段控件长度
	 * @apiParam {String} conditions.searchType 字段查询类型，normal:普通查询，range:范围查询，fuzzy:模糊查询
	 * 
	 * @apiParamExample {json} Request-Example: 
PUT /cg/form/abc
{
"tabledescp":"upd tabledescp","version":1,
  "columns":[
    {"colIsnull":"","colIspk":"Y","colLen":10,"colMark":"mark_0","colName":"name_0", "colType":"string","deleted":"F","dictCode":"dictCode","dictTable":"dictTable","dictText":"dictText","id":"3db92d77287c46bfb3bb44d753c8d32b","inputLen":100,"inputType":"text","isFormdisp":"","isGriddisp":"","isSearch":"","searchType":"normal","validType":"validType"},
    {"colIsnull":"Y","colIspk":"","colLen":12,"colMark":"mark_2","colName":"name_2", "colType":"string","deleted":"F","dictCode":"dictCode","dictTable":"dictTable","dictText":"dictText","id":"cc5d50274b8d44039131131eba59c825","inputLen":100,"inputType":"text","isFormdisp":"Y","isGriddisp":"Y","isSearch":"Y","searchType":"normal","validType":"validType"},
    {"colIsnull":"Y","colIspk":"","colLen":14,"colMark":"mark_4","colName":"name_4",  "colType":"string","deleted":"F","dictCode":"dictCode","dictTable":"dictTable","dictText":"dictText","id":"b45b25543d5c427192ccaf092efc1fa8","inputLen":100,"inputType":"text","isFormdisp":"Y","isGriddisp":"Y","isSearch":"Y","searchType":"normal","validType":"validType"},
    {"colName":"name_0","colIspk":"Y","validType":"validType2","colMark":"mark_0","colIsnull":"","inputLen":100,"searchType":"normal","isGriddisp":"","extjson":"","dictTable":"dictTable2","isFormdisp":"", "isSearch":"","colType":"string","inputType":"text","dictText":"dictText2","colDefval":""},
    {"colName":"name_1","colIspk":"","validType":"validType2","colMark":"mark_1","colIsnull":"Y","inputLen":100,"searchType":"range","isGriddisp":"Y","extjson":"","dictTable":"dictTable2","isFormdisp":"Y", "isSearch":"Y","colType":"string","inputType":"text","dictText":"dictText2","colDefval":""},
    {"colName":"name_2","colIspk":"","validType":"validType2","colMark":"mark_2","colIsnull":"Y","inputLen":100,"searchType":"normal","isGriddisp":"Y","extjson":"","dictTable":"dictTable2","isFormdisp":"Y", "isSearch":"Y","colType":"string","inputType":"text","dictText":"dictText2","colDefval":""}
  ],
  "conditions": [{
   "issearch": "Y",
   "expr": "username = #{abc}",
   "inputType": "text",
  "colName": "abc",
   "colMark": "mark0",
   "searchType": "fuzzy"
  }],
}
	 * 
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 *  {
	 *  "version":2
	 *  }
	 *  @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 *  {
	 *    "verrors":[
	 *      {"errCode":"sql","errorMsg":"SQL不能为空"},
	 *      {"errCode":"sql","errorMsg":"SQL格式错误"},
	 *      {"errCode":"tablename","errorMsg":"表名不能为空"},
	 *      {"errCode":"tabledescp","errorMsg":"表描述不能为空"},
	 *      {"errCode":"pktype","errorMsg":"主键类型不能为空"},
	 *      {"errCode":"columns","errorMsg":"数据列不能为空"},
	 *      {"errCode":"tree","errorMsg":"树形展示不能不设置父节点和子节点"},
	 *      {"errCode":"tree","errorMsg":"父子节点不能相同"},
	 *      {"errCode":"child","errorMsg":"子节点在字段中不存在"},
	 *      {"errCode":"parent","errorMsg":"父节点在字段中不存在"}
	 *      ]
	 *  }
	 *  @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 409 Conflict - 被人修改了或者表记录不存在
	 *  
	 * @return
	 * @throws BuzException 
	 */
	@RequestMapping(value="/form/{tableId}",method=RequestMethod.PUT )
	@ResponseBody
	public Object updateForm(@PathVariable("tableId") String tableId, @RequestBody JSONObject parm) throws BuzException {
		String sql = parm.getString(OnlineFuncController.SQL_PARM);
		if(StringUtils.isBlank(sql)){
			TableVO tableVO = parm.toJavaObject(TableVO.class);
			if(tableVO.getVersion()!=null&&tableVO.getColumns()!=null && tableVO.getColumns().length!=0){
				tableVO.setTableId(tableId);
				if("Y".equals(tableVO.getIstree())){
					if(StringUtils.isEmpty(tableVO.getChild()) || StringUtils.isEmpty(tableVO.getParent())){
						addValidationError("tree","树形展示不能不设置父节点和子节点");
						return getInvalidParamResult();
					}
					else if(tableVO.getChild().equals(tableVO.getParent())){
						addValidationError("tree","父子节点不能相同");
						return getInvalidParamResult();
					}
					else if(!tableVO.isInColumn(tableVO.getParent())){
						addValidationError("parent","父节点在字段中不存在");
						return getInvalidParamResult();
					}
					else if(!tableVO.isInColumn(tableVO.getChild())){
						addValidationError("child","子节点在字段中不存在");
						return getInvalidParamResult();
					}
				}
				if(cgService.updateForm(tableVO)){
					JSONObject result = new JSONObject();
					result.put(VERSION, tableVO.getVersion());
					result.put(IS_DB_SYN, tableVO.getIsdbsyn());
					return getSuccessResult(result);
				}else{
					return getModifiedResult();
				}
			}else{
				return getInvalidParamResult();
			}
		}else{
			OnlineFuncVO funcVO = parm.toJavaObject(OnlineFuncVO.class);
			funcVO.setTableId(tableId);
			return onlineFcController.updateOnlieFuncVO(funcVO);
		}
	}
	
	
	/**
	 * @api {DELETE} /cg/form/{tableId} 6.删除表单
	 * @apiGroup CG

	 * @apiParam {String} tableId Table ID
	 * @apiParam {Integer} version Table version
	 * @apiParamExample {json} Request-Example: 
	 *  {
	 *    "version":1
	 *  }
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 *  {
	 *    "verror":[
	 *    	{"errCode":"","errorMsg":"系统表不允许删除"},
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
	@RequestMapping(value="/form/{tableId}",method=RequestMethod.DELETE )
	@ResponseBody
	public Object deleteForm(@PathVariable("tableId") String tableId, @RequestBody JSONObject parm) {
		Integer version = parm.getInteger(VERSION);
		Boolean forceDrop = parm.getBoolean(FORCE_DROP);
		if(version!=null && !StringUtils.isEmpty(tableId)){
			SysBxx bxxParm = new SysBxx();
			bxxParm.setId(tableId);
			bxxParm.setVersion(version);
			SysBxx bxx = generalMapper.selectOne(bxxParm);
			if(bxx==null){
				return getModifiedResult();
			}
			String tableName = bxx.getBm();
			if(tableName.length()>4){
				String prefix = tableName.substring(0,4);
				if("sys_".equalsIgnoreCase(prefix)){
					JSONObject result = new JSONObject();
					addValidationError("系统表不允许删除");
					return getInvalidParamResult(result);
				}
			}
			if(cgService.deleteForm(getCurrentProjectId(), bxx, forceDrop==null?false:forceDrop)){
				return getSuccessResult();
			}else{
				return getModifiedResult();
			}
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {GET} /cg/form/basecolumns 获取表基础字段s
	 * @apiGroup CG

	 * @apiParamExample {json} Request-Example: 
	 * 	GET /cg/form/basecolumns
	 * 
	 * @apiSuccess {Integer} colSeq 
	 * @apiSuccess {String} colName 
	 * @apiSuccess {String} colIspk 
	 * @apiSuccess {String} colMark 
	 * @apiSuccess {String} colIsnull 
	 * @apiSuccess {String} inputLen 
	 * @apiSuccess {String} isGriddisp 
	 * @apiSuccess {String} isFormdisp 
	 * @apiSuccess {String} colType 
	 * @apiSuccess {String} colLen 
	 * @apiSuccess {String} isSearch 
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * {
 "rows": [{
  "colSeq": 1,
  "colName": "id",
  "colIspk": "Y",
  "colMark": "主键",
  "colIsnull": "N",
  "inputLen": 50,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "colLen": 50,
  "colDecpoint": 0,
  "isSearch": "N",
  "colType": "string",
  "inputType": "text"
 },
 {
  "colSeq": 2,
  "colName": "whr",
  "colIspk": "N",
  "colMark": "维护人",
  "colIsnull": "N",
  "inputLen": 50,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "colLen": 50,
  "colDecpoint": 0,
  "isSearch": "N",
  "colType": "string",
  "inputType": "text"
 },
 {
  "colSeq": 3,
  "colName": "whrid",
  "colIspk": "N",
  "colMark": "维护人ID",
  "colIsnull": "N",
  "inputLen": 50,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "colLen": 50,
  "colDecpoint": 0,
  "isSearch": "N",
  "colType": "string",
  "inputType": "text"
 },
 {
  "colSeq": 4,
  "colName": "whsj",
  "colIspk": "N",
  "colMark": "维护时间",
  "colIsnull": "N",
  "inputLen": 50,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "colLen": 50,
  "colDecpoint": 0,
  "isSearch": "N",
  "colType": "datetime",
  "inputType": "datetime"
 },
 {
  "colSeq": 5,
  "colName": "cjr",
  "colIspk": "N",
  "colMark": "创建人",
  "colIsnull": "N",
  "inputLen": 50,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "colLen": 50,
  "colDecpoint": 0,
  "isSearch": "N",
  "colType": "string",
  "inputType": "text"
 },
 {
  "colSeq": 6,
  "colName": "cjrid",
  "colIspk": "N",
  "colMark": "创建人ID",
  "colIsnull": "N",
  "inputLen": 50,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "colLen": 50,
  "colDecpoint": 0,
  "isSearch": "N",
  "colType": "string",
  "inputType": "text"
 },
 {
  "colSeq": 7,
  "colName": "cjsj",
  "colIspk": "N",
  "colMark": "创建时间",
  "colIsnull": "N",
  "inputLen": 50,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "colLen": 50,
  "colDecpoint": 0,
  "isSearch": "N",
  "colType": "datetime",
  "inputType": "datetime"
 },
 {
  "colSeq": 8,
  "colName": "version",
  "colIspk": "N",
  "colMark": "版本",
  "colIsnull": "N",
  "inputLen": 50,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "colLen": 50,
  "colDecpoint": 0,
  "isSearch": "N",
  "colType": "int",
  "inputType": "text"
 },
 {
  "colSeq": 9,
  "colName": "deleted",
  "colIspk": "N",
  "colMark": "逻辑删除标记",
  "colIsnull": "N",
  "inputLen": 50,
  "isGriddisp": "N",
  "isFormdisp": "N",
  "colLen": 50,
  "colDecpoint": 0,
  "isSearch": "N",
  "colType": "string",
  "inputType": "text"
 }]
}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @return
	 */
	@RequestMapping(value="/form/basecolumns",method=RequestMethod.GET)
	@ResponseBody
	public Object getBaseColumns(){
		if(baseColumns==null){
			baseColumns = new ArrayList<>();
			baseColumns.add(new BaseColumnVO("id", "主键", CGConstants.ColTypes.string, 50));
			baseColumns.add(new BaseColumnVO("whr", "维护人", CGConstants.ColTypes.string, 50));
			baseColumns.add(new BaseColumnVO("whrid", "维护人ID", CGConstants.ColTypes.string, 50));
			baseColumns.add(new BaseColumnVO("whsj", "维护时间", CGConstants.ColTypes.datetime, 50));
			baseColumns.add(new BaseColumnVO("cjr", "创建人", CGConstants.ColTypes.string, 50));
			baseColumns.add(new BaseColumnVO("cjrid", "创建人ID", CGConstants.ColTypes.string, 50));
			baseColumns.add(new BaseColumnVO("cjsj", "创建时间", CGConstants.ColTypes.datetime, 50));
			baseColumns.add(new BaseColumnVO("version", "版本", CGConstants.ColTypes.int_, 10));
			baseColumns.add(new BaseColumnVO("deleted", "逻辑删除标记", CGConstants.ColTypes.string, 1));
		}
		List<ColumnVO> list = new ArrayList<>(baseColumns.size());
		ColumnVO id = new ColumnVO();
		id.setColName("id");
		id.setColMark("主键");
		id.setColType(ColTypes.string);
		id.setCol_length(50);
		id.setInputlen(50);
		id.setIsformdisp(Constants.NO);
		id.setIsgriddisp(Constants.NO);
		id.setCol_decpoint(0);
		id.setCol_ispk(Constants.YES);
		id.setCol_isnull(Constants.NO);
		id.setInputtype(InputTypes.text);
		id.setCol_seq(1);
		id.setIssearch(Constants.NO);
		list.add(id);
		for(int i=1;i<baseColumns.size();++i){
			BaseColumnVO baseCol = baseColumns.get(i);
			ColumnVO col = new ColumnVO();
			col.setColName(baseCol.getColumnName());
			col.setColMark(baseCol.getColumnDescp());
			col.setColType(baseCol.getColumnType());
			col.setCol_length(50);
			col.setInputlen(50);
			col.setIsformdisp(Constants.NO);
			col.setIsgriddisp(Constants.NO);
			col.setCol_decpoint(0);
			col.setCol_ispk(Constants.NO);
			col.setCol_isnull(Constants.YES);
			switch (baseCol.getColumnType()) {
			case CGConstants.ColTypes.string:
				col.setInputtype(InputTypes.text);
				break;
			case CGConstants.ColTypes.datetime:
				col.setInputtype(InputTypes.datetime);
				break;
			case CGConstants.ColTypes.int_:
				col.setInputtype(InputTypes.text);
				break;
			default:
				col.setInputtype(InputTypes.text);
				break;
			}
			col.setCol_seq(i+1);
			col.setIssearch(Constants.NO);
			list.add(col);
		}
		return getSuccessResult(list);
	}
	
	private static List<BaseColumnVO> baseColumns = null;
	
	/**
	 * @api {POST} /cg/form/custbt 增加自定义按钮
	 * @apiGroup CG
	 * 
	 * @apiParam {String} code 自定义按钮编码	
	 * @apiParam {String} icon 自定义按钮图标	
	 * @apiParam {String} name 自定义按钮名字	
	 * @apiParam {String} zt 自定义按钮状态
	 * @apiParam {String} ys 自定义按钮样式
	 * @apiParam {String} exp 自定义按钮过期时间	
	 * @apiParam {String} bdid 表单ID	
	 * @apiParam {String} czlx 操作类型  js/action	
	 * @apiParam {String} xh 序号
	 * @apiParamExample {json} Request-Example: 
	 *  {"xh":1,"code":"1","bdid":"1","icon":"icon1","name":"name1","zt":"zt","ys":"zt","exp":1486704078740,"czlx":"js"}
	 * 
	 * @apiSuccess {String} id 返回增强JS id
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {"id":"5179ff002fe6456b866bed0f25943faf"}
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 */
	@PostMapping("/form/custbt")
	@ResponseBody
	public Object custbtAdd(@RequestBody JSONObject parm){
		SysCustbt custbt = JSONObject.toJavaObject(parm, SysCustbt.class);
		if(validateData(custbt)){
			cgService.addCustbt(custbt);
			JSONObject result =  new JSONObject();
			result.put(ID, custbt.getId());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult(getValidateErrors());
		}
	}
	
	/**
	 * @api {POST} /cg/form/custbt 增加自定义按钮
	 * @apiGroup CG
	 * 
	 * @apiParam {String} code 自定义按钮编码	
	 * @apiParam {String} icon 自定义按钮图标	
	 * @apiParam {String} name 自定义按钮名字	
	 * @apiParam {String} zt 自定义按钮状态
	 * @apiParam {String} ys 自定义按钮样式
	 * @apiParam {String} exp 自定义按钮过期时间	
	 * @apiParam {String} bdid 表单ID	
	 * @apiParam {String} czlx 操作类型  js/action	
	 * @apiParam {String} xh 序号	
	 * @apiParamExample {json} Request-Example: 
	 *  {"xh":1,"code":"1","bdid":"1","icon":"icon1","name":"update","zt":"zt","ys":"ys","exp":1486704365030,"czlx":"js"}
	 * 
	 * @apiSuccess {String} id 返回增强JS id
	 * @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 *  {"id":"5179ff002fe6456b866bed0f25943faf"}
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 */
	@PutMapping("/form/custbt/{id}")
	@ResponseBody
	public Object custbtUpdate(@PathVariable("id") String id, @RequestBody JSONObject parm){
		SysCustbt custbt = JSONObject.toJavaObject(parm, SysCustbt.class);
		custbt.setId(id);
		if(validateData(custbt)){
			cgService.updateCustbt(custbt);
			JSONObject result =  new JSONObject();
			result.put(ID, custbt.getId());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult(getValidateErrors());
		}
	}
	
	/**
	 * @api {DELETE} /cg/form/custbt删除自定义按钮（单个/批量）
	 * @apiGroup CG
	 * 
	 * @apiParam {JSONArray} - 自定义按钮ids
	 * @apiParamExample {json} Request-Example: 
	 *   ["1","2","3"]
	 * 
	 *  @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * @apiErrorExample {json} Error-Response:
	 *   HTTP/1.1 400 Bad Request
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 */
	@DeleteMapping("/form/custbt")
	@ResponseBody
	public Object custbtDelete(@RequestBody JSONArray parm){
		if(!parm.isEmpty()){
			int size = parm.size();
			List<String> idList = new ArrayList<String>(size);
			for(int i=0;i<size;++i){
				idList.add(parm.getString(i));
			}
			cgService.deleteCustbt(idList);
			return getSuccessResult();
		}else{
			return getInvalidParamResult();
		}
	}
	
	/*获取增强JS数据*/
	/**
	 * @api {GET} /cg/form/js/getlist/{id} 获取表单的增强js
	 * @apiGroup CG
	 * @apiParamExample {json} Request-Example: 
	 * 					{"jslx":"form"}
	 
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
	 * 	{"total",25, "current":1, "size":10, "pages":3,"rows":[
	 * 		{"whr":"system","cjsj":1486697241000,"whrid":"system","cjrid":"system",
	 * 		 "bdid":"1","jslx":"form","nr":"unr","js":"12313","cjr":"system",
	 * 		 "id":"fd9a95bf50864ef39c9366beab1d5369","version":1,
	 *       "whsj":1486701551000}
	 *  ]}
	 * 
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/form/js/getlist/{id}",method=RequestMethod.POST)
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
	
	
	/**
	 * @api {POST} /cg/form/js 增加增强js
	 * @apiGroup CG
	 * 
	 * @apiParam {String} bdid 表单id
	 * @apiParam {String} js 增强js代码
	 * @apiParam {String} jslx 增强js类型
	 * @apiParam {String} nr 内容
	 * @apiParamExample {json} Request-Example: 
	 * 					{"bdid":"1","jslx":"form","nr":"zqjsnr","js":"zqjs"}
	 * 
	 * @apiSuccess {String} id 返回增强JS id
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{"id":"5179ff002fe6456b866bed0f25943faf"}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@PostMapping("/form/js")
	@ResponseBody
	public Object zqjsAdd(@RequestBody JSONObject parm) throws UnsupportedEncodingException{
		SysZqjs zqjs = JSONObject.toJavaObject(parm, SysZqjs.class);
		if (parm.getString("js") == null){
			zqjs.setJs("".getBytes());
		}else{
			zqjs.setJs(parm.getString("js").getBytes("utf-8"));
		}
		if(validateData(zqjs)){
			cgService.addZqjs(zqjs);
			JSONObject result =  new JSONObject();
			result.put(ID, zqjs.getId());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult(getValidateErrors());
		}
	}
	
	/*更新增强JS*/
	/**
	 * @api {PUT} /cg/form/js 更新增强js
	 * @apiGroup CG
	 * 
	 * @apiParam {String} bdid 表单id
	 * @apiParam {String} js 增强js代码
	 * @apiParam {String} jslx 增强js类型
	 * @apiParam {String} nr 内容
	 * @apiParamExample {json} Request-Example: 
	 * 					{"bdid":"1","jslx":"form","nr":"zqjsnr","js":"zqjs"}
	 * 
	 * @apiSuccess {String} id 返回增强JS id
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{"id":"5179ff002fe6456b866bed0f25943faf"}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@PutMapping("/form/js/{id}")
	@ResponseBody
	public Object zqjsUpdate(@PathVariable("id") String id, @RequestBody JSONObject parm) throws UnsupportedEncodingException{
		SysZqjs zqjs = JSONObject.toJavaObject(parm, SysZqjs.class);
		zqjs.setId(id);
		if (parm.getString("js") != null){
			zqjs.setJs(parm.getString("js").getBytes("utf-8"));
		}
		if(validateData(zqjs)){
			cgService.updateZqjs(zqjs);
			JSONObject result =  new JSONObject();
			result.put(ID, zqjs.getId());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult(getValidateErrors());
		}
	}
	
	/**
	 * @api {DELETE} /cg/form/js/{id}删除增强JS单个
	 * @apiGroup CG
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
	/*删除增强JS*/   //前台应该用不到
	@DeleteMapping("/form/js/{id}")
	@ResponseBody
	public Object userDelete(@PathVariable("id") String id){
		if(!"".equals(id)){
			cgService.deleteZqjs(id);
			return getSuccessResult();
		}else{
			return getInvalidParamResult();
		}
	}
	
	/*获取增强sql数据*/
	/**
	 * @api {GET} /cg/form/sql/getlist/{id} 获取表单的增强sql
	 * @apiGroup doc
	 * @apiParamExample {json} Request-Example: 
	 * 					{"code":"add"}
	 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} rows.id 增强sql主键	
	 * @apiSuccess {String} rows.sql 增强sql
	 * @apiSuccess {String} rows.code 增强sql关联的控件编码	
	 * @apiSuccess {String} rows.nr 增强sql内容
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
	 * 	{"total":1,"rows":[{"cjsj":1487038184000,"cjrid":"system","code":"add","bdid":"1","nr":"nr","cjr":"system","id":"8be490227ab842f089086338de00d0b4","version":1,"sql":"select * from users"}]}
	 * @param parm
	 * @return
	 */
	@RequestMapping(value="/form/sql/getlist/{id}",method=RequestMethod.POST)
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
	 * @api {POST} /cg/form/sql 增加增强sql
	 * @apiGroup cg Manager
	 * 
	 * @apiParam {String} bdid 表单id
	 * @apiParam {String} sql 增强sql代码
	 * @apiParam {String} code增强sql关联的控件代码
	 * @apiParam {String} nr sql说明
	 * @apiParamExample {json} Request-Example: 
	 * 					{"bdid":"1","code":"add","nr":"nr","sql":"select * from users"}
	 * 
	 * @apiSuccess {String} id 返回增强JS id
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{"id":"5179ff002fe6456b866bed0f25943faf"}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@PostMapping("/form/sql")
	@ResponseBody
	public Object zqsqlAdd(@RequestBody JSONObject parm) throws UnsupportedEncodingException{
		SysZqsql zqsql = JSONObject.toJavaObject(parm, SysZqsql.class);
		if (parm.getString("sql") == null){
			zqsql.setZqsql("".getBytes());
		}else{
			zqsql.setZqsql(parm.getString("sql").getBytes("utf-8"));
		}
		if(validateData(zqsql)){
			cgService.addZqsql(zqsql);
			JSONObject result =  new JSONObject();
			result.put(ID, zqsql.getId());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult(getValidateErrors());
		}
	}
	
	/*更新增强sql*/
	/**
	 * @api {PUT} /cg/form/sql 更新增强sql
	 * @apiGroup cg Manager
	 * 
	 * @apiParam {String} bdid 表单id
	 * @apiParam {String} sql 增强sql代码
	 * @apiParam {String} code 增强sql关联按钮
	 * @apiParam {String} nr 内容
	 * @apiParamExample {json} Request-Example: 
	 * 					{"code":"update","bdid":"1","nr":"unr","sql":"select * from users for update"}
	 * 
	 * @apiSuccess {String} id 返回增强JS id
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{"id":"5179ff002fe6456b866bed0f25943faf"}
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@PutMapping("/form/sql/{id}")
	@ResponseBody
	public Object zqsqlUpdate(@PathVariable("id") String id, @RequestBody JSONObject parm) throws UnsupportedEncodingException{
		SysZqsql zqsql = JSONObject.toJavaObject(parm, SysZqsql.class);
		zqsql.setId(id);
		if (parm.getString("sql") != null){
			zqsql.setZqsql(parm.getString("sql").getBytes("utf-8"));
		}
		if(validateData(zqsql)){
			cgService.updateZqsql(zqsql);
			JSONObject result =  new JSONObject();
			result.put(ID, zqsql.getId());
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult(getValidateErrors());
		}
	}
	
	/**
	 * @api {DELETE} /cg/form/js/{id}删除增强sql单个
	 * @apiGroup cg Manager
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
	/*删除增强sql*/   //前台应该用不到
	@DeleteMapping("/form/sql/{id}")
	@ResponseBody
	public Object zqsqlDelete(@PathVariable("id") String id){
		if(!"".equals(id)){
			cgService.deleteZqsql(id);
			return getSuccessResult();
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {GET} /cg/form/{tablename}/columns 7.根据表名获取字段
	 * @apiGroup CG
	 * @apiDescription ID和VERSION会被过滤掉
	 * 
	 *  @apiSuccessExample {json} Success-Response:
	 *  HTTP/1.1 200 OK
	 * {
 "total": 5,
 "rows": [{
  "colName": "COLUMNS_NAME_1",
  "colMark": "mark_1",
  "colType": "string"
  "colIsnull": ""
 },
 {
  "colName": "COLUMNS_NAME_4",
  "colMark": "mark_4",
  "colType": "string"
 },
 {
  "colName": "COLUMNS_NAME_3",
  "colMark": "mark_3",
  "colType": "string"
 },
 {
  "colName": "COLUMNS_NAME_2",
  "colMark": "mark_2",
  "colType": "string"
 },
 {
  "colName": "COLUMNS_NAME_0",
  "colMark": "mark_0",
  "colType": "string"
 }]
}
	 * @param tablename
	 * @return
	 */
	@GetMapping("/form/{tablename}/columns")
	public Object getCurrentAvailableColumnsByTableName(@PathVariable("tablename") String tablename){
		List<SysColumns> columns = cgService.getCurrentAvailableColumns(tablename.toLowerCase());
		if(columns!=null){
			List<JSONObject> result = new ArrayList<>(columns.size());
			columns.stream().filter((col)->{
				if(col.getName().endsWith("_ID") || col.getName().endsWith("_VERSION")){
					return false;
				}
				return true;
			}).map((col)->{
				JSONObject record = new JSONObject();
				record.put("colName", col.getName());
				record.put("colMark", col.getComment());
				record.put("colType", col.getType());
				record.put("colIsnull", col.getIsnull());
				return record;
			}).forEach(result::add);
			return getSuccessResult(result);
		}else{
			addValidationError("表名输入有误");
			return getInvalidParamResult();
		}
	}
		
}
