package com.rs.devplatform.controller.admin;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.persistent.SysTables;
import com.rs.devplatform.service.admin.DBMaintainService;
import com.rs.devplatform.vo.MaintainTableVO;
import com.rs.devplatform.vo.SysTableVO;
import com.rs.devplatform.vo.TableUpdateConfirmVO;
import com.rs.framework.common.Constants;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;
import com.rs.framework.common.exception.RsCommonException;
import com.rs.framework.common.validator.ValidateError;

@Controller
@RequestMapping("/admin/db")
public class DBMaintainController extends BaseController {
	
	@Autowired
	DBMaintainService dbService;
	/**
	 * @api {GET} /admin/db 1.转到数据表维护页面
	 * @apiGroup Admin/Maintain Table
	 * @apiSuccess {String} html 数据表维护页面 
	 * @apiSuccessExample {json} 页面命名:
	 *  {"页面":"/manage/tablelist.html"}
	 * @return
	 */
	@GetMapping("")
	public String index(){
		return "manage/tablelist";
	}
	/**
	 * @api {POST} /admin/db/tables 2.查询数据库表list
	 * @apiGroup Admin/Maintain Table
	 * @apiParam {String} tablename 表名字(模糊查询)
	 * @apiParam {String} tabledescp 表描述(模糊查询)
	 * @apiParam {String} isdbsyn 是否同步数据库(Y/N/empty)
	 * @apiParamExample {json} Request-Example: 
	 *   {"tablename":"test", "isdbsyn":"", "tabledescp":""}
	 * 
	 * @apiSuccess {Integer} total 总记录条数
	 * @apiSuccess {Integer} current 当前页码
	 * @apiSuccess {Integer} size 每页显示记录条数
	 * @apiSuccess {Integer} pages 总页数
	 * @apiSuccess {JsonArray} rows JSONArray
	 * @apiSuccess {String} rows.id id主键	
	 * @apiSuccess {String} rows.tablename 数据库表名	
	 * @apiSuccess {String} rows.tabledescp 数据库表描述	
	 * @apiSuccess {String} rows.isdbsyn 是否同步（Y:已同步，其他未同步）	
	 * @apiSuccess {Integer} rows.version 版本
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * 	{
	 *    "total":2,
	 *    "records":[
	 *      {"tabledescp":"ju table 2674", "id":"ec9ec852205c4c288e320ff6f029fc6f","isdbsyn":"N","tablename":"tbl_ju_2674","version":1},
	 *      {"tabledescp":"ju table 1970", "id":"fd5a58d8f09c41549f4bbd4131a5d990","isdbsyn":"N","tablename":"tbl_ju_1970","version":1}
	 *     ]
	 *   }
	 * 
	 * @param parm
	 * @return
	 */
	@PostMapping("/tables")
	@ResponseBody
	public Object listTable(@RequestBody JSONObject parm){
		SysTableVO sysTableVO = parm.toJavaObject(SysTableVO.class);
		String tablename = sysTableVO.getName();
		String tabledescp = sysTableVO.getComment();
		String sync = sysTableVO.getSync();
		sysTableVO.setName(null);
		sysTableVO.setComment(null);
		RsEntityWrapper<SysTableVO> ew = new RsEntityWrapper<>(sysTableVO);
		if(!StringUtils.isBlank(tablename)){
			ew.like("TABLES_NAME", tablename);
		}
		if(!StringUtils.isBlank(tabledescp)){
			ew.like("TABLES_COMMENT", tabledescp);
		}
		if(StringUtils.isBlank(sync)){
			sysTableVO.setSync(null);
		}
		return getSuccessResult(generalMapper.selectByPageOrder(ew));
	}
	
	/**
	 * @api {GET} /admin/db/table/{tableId} 3.查询表详情
	 * @apiGroup Admin/Maintain Table

 	 * @apiSuccess {String} id 表id
 	 * @apiSuccess {String} tablename 表名字
	 * @apiSuccess {String} tabledescp 表描述
	 * @apiSuccess {String} pktype PK类型(UUID:32位唯一编码)
	 * @apiSuccess {String} pkseq PK Seq
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
	 * @apiSuccess {String} columns.version 字段version
	 * 
	 * @apiSuccess {JSONArray} indexs 索引
	 * @apiSuccess {String} indexs.id 索引id
	 * @apiSuccess {Integer} indexs.indexSeq 索引序号
	 * @apiSuccess {String} indexs.colName 索引字段
	 * @apiSuccess {String} indexs.indexType 索引类型
	 * @apiSuccess {String} indexs.indexName 索引名称
	 * 
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * {
	 *  "tabledescp": "tbl_ju98889888",
	 *   "columns": [{
	 *     "colSeq": 1,
	 *     "colName": "name_0",
	 *     "colIspk": "Y",
	 *     "deleted": "F",
	 *     "colMark": "mark_0",
	 *     "colDecpoint": 0,
	 *     "colIsnull": "",
	 *     "colType": "string",
	 *     "colLen": 10,
	 *     "id": "84573ec295a14426bf5147d33bce8c4f",
	 *     "colDefval": ""
	 *   }],
	 *   "indexs": [{
	 *     "colName": "name_0",
	 *     "indexType": "normal",
	 *     "indexName": "name_0_index",
	 *     "indexSeq": 1,
	 *     "id": "564144e1c378401a932aaf6430587e47"
	 *   }],
	 *   "id": "9cfa6836297d4c95924b7974a6eb8877",
	 *   "tablename": "tbl_ju9888",
	 *   "pktype": "UUID",
	 *   "version": 1
	 * }
	 * @param tableId
	 * @return
	 */
	@GetMapping("/table/{tableId}")
	@ResponseBody
	public Object viewTable(@PathVariable("tableId") String tableId){
		MaintainTableVO tableVO = dbService.getTableWithColumnById(tableId);
		if(tableVO==null){
			addValidationError(new ValidateError("", "没有找到对应记录"));
			return getInvalidParamResult();
		}
		return getSuccessResult(tableVO.toJSON());
	}
	
	/**
	 * @api {POST} /admin/db/table 4.新增数据表
	 * @apiGroup Admin/Maintain Table
	 * @apiParam {String} tablename 表名字(必填)
	 * @apiParam {String} tabledescp 表描述(必填)
	 * @apiParam {String} pktype 主键类型(必填)
	 * 
	 * @apiParam {JSONArray} columns 字段(必填)
	 * @apiParam {Integer} columns.colSeq 字段序号
	 * @apiParam {String} columns.colName 字段名称
	 * @apiParam {String} columns.colMark 字段备注
	 * @apiParam {Integer} columns.coLen 字段长度
	 * @apiParam {Integer} columns.colDecpoint 字段小数点位数
	 * @apiParam {String} columns.colDefval 字段默认值
	 * @apiParam {String} columns.colType 字段类型(string/int/double/date/decimal/text/blob)
	 * @apiParam {String} columns.colIspk 字段是否主键，Y:是主键
	 * @apiParam {String} columns.colIsnull 字段是否允许空值，Y:可为空
	 * 
	 * @apiParam {JSONArray} indexs 索引
	 * @apiParam {Integer} indexs.indexSeq 索引序号
	 * @apiParam {String} indexs.colName 索引字段
	 * @apiParam {String} indexs.indexType 索引类型
	 * @apiParam {String} indexs.indexName 索引名称
	 * @apiParamExample {json} Request-Example: 
	 * 	{
	 *    "tablename": "tbl_ju-8793",
	 *    "tabledescp": "tbl_ju-8793-8793",
	 *    "pktype": "UUID",
	 *    "columns": [{
	 *      "colSeq": 1,
	 *      "colName": "name_0",
	 *      "colIsnull": "",
	 *      "colLen": 10,
	 *      "colDecpoint": 0,
	 *      "colIspk": "Y",
	 *      "colMark": "mark_0",
	 *      "colType": "string",
	 *      "colDefval": ""
	 *      },
	 *      {
	 *      "colSeq": 2,
	 *      "colName": "name_1",
	 *      "colIsnull": "Y",
	 *      "colLen": 11,
	 *      "colDecpoint": 0,
	 *      "colIspk": "",
	 *      "colMark": "mark_1",
	 *      "colType": "string",
	 *      "colDefval": ""
	 *      }],
	 *    "indexs": [{
	 *      "colName": "name_0",
	 *      "indexType": "normal",
	 *      "indexName": "name_0_index",
	 *      "indexSeq": 1
	 *      }]
	 *  }
	 * @apiSuccess {String} id 表id
	 * @apiSuccess {Integer} version 表version
	 * @apiSuccess {String} isdbsyn 是否同步数据库（Y：已经同步数据库，N:没有同步）
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * {"id":"1234567asdf","version":1, "isdbsyn":"N"}
	 * 
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 *  {
	 *    "verrors":[
	 *      { "errCode": "pktype", "errorMsg": "主键类型不能为空" },
	 *      { "errCode": "tablename", "errorMsg": "表名不能为空" },
	 *      { "errCode": "tabledescp", "errorMsg": "表描述不能为空" },
	 *      { "errCode": "columns", "errorMsg": "字段不能为空" }
	 *    ]
	 *  }
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * 
	 * @param parm
	 * @return
	 */
	@PostMapping("/table")
	@ResponseBody
	public Object addTable(@RequestBody JSONObject parm){
		MaintainTableVO tableVO = parm.toJavaObject(MaintainTableVO.class);
		if(!validateData(tableVO)){
			return getInvalidParamResult();
		}
		SysTables table = new SysTables();
		table.setName(tableVO.getTablename());
		if(isDuplicateBeforeCreate(table)){
			return getDuplicateResult();
		}
		tableVO.formatColumnPrefix();
		if(dbService.addTableWithColumn(tableVO)){
			JSONObject result = new JSONObject(2);
			result.put(ID, tableVO.getTableid());
			result.put(VERSION, tableVO.getVersion());
			result.put("isdbsyn", Constants.NO);
			return getSuccessResult(result);
		}else{
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {DELETE} /admin/db/table/{tableId} 5.删除数据表
	 * @apiGroup Admin/Maintain Table

	 * @apiParam {String} tableId Table ID
	 * @apiParam {Integer} version Table version
	 * @apiParam {Boolean} forceDrop 是否删除物理表, true:删除物理表，false/null表示不删除
	 * @apiParamExample {json} Request-Example: 
	 * {
	 *  "version":1
	 *  "forceDrop":true
	 * }
	 * @apiSuccessExample {json} Success-Response:
	 * 	HTTP/1.1 200 OK
	 * @apiErrorExample {json} Error-Response:
	 * HTTP/1.1 400 Bad Request
	 * {
	 *   "verror":[
	 *     { "errCode": "", "errorMsg": "version不允许为空" },
	 *     { "errCode": "", "errorMsg": "系统表不允许删除" },
	 *     { "errCode": "", "errorMsg": "记录不存在" },
	 *    ]
	 * }
	 * @apiErrorExample {json} Error-Response:
	 * HTTP/1.1 409 Conflict
	 *  {
	 *    "verror":[
	 *        { "errCode": "", "errorMsg": "删除失败" },
	 *     ]
	 *  }
	 * @param tableId
	 * @param parm
	 * @return
	 * @throws RsCommonException
	 */
	@DeleteMapping("/table/{tableId}")
	@ResponseBody
	public Object deleteTable(@PathVariable("tableId") String tableId, @RequestBody JSONObject parm) throws RsCommonException{
		Integer version = parm.getInteger(VERSION);
		if(version==null){
			addValidationError(new ValidateError("", "version不允许为空"));
			return getInvalidParamResult();
		}
		SysTables table = generalMapper.selectById(tableId, SysTables.class);
		if(table==null){
			addValidationError(new ValidateError("", "记录不存在"));
			return getInvalidParamResult();
		}
		String tableName = table.getName();
		String tablePrefix = tableName.length()>3?table.getName().substring(0,3):tableName;
		if("sys".equalsIgnoreCase(tablePrefix)){
			addValidationError(new ValidateError("", "系统表不允许删除"));
			return getInvalidParamResult();
		}
		Boolean forceDrop = parm.getBoolean("forceDrop");
		if(forceDrop==null){
			forceDrop = false;
		}
		if(dbService.deleteTableById(getCurrentProjectId(), tableId, version, forceDrop)){
			return getSuccessResult();
		}else{
			addValidationError(new ValidateError("", "删除失败"));
			return getModifiedResult();
		}
	}
	
	/**
	 * @api {PUT} /admin/db/table/{tableId} 7.更新表
	 * @apiDescription 更新表改动到数据库（不同步）, 支持2种数据格式，1）表信息、字段索引信息，2）confirm的数据结构。 支持第二种，为了当用户确认的时候，发现问题，可以直接修改这个格式，然后提交
	 * @apiGroup Admin/Maintain Table
	 * 
	 * @apiParam {String} tabledescp 表描述(必填)
	 * @apiParam {String} pktype PK类型(UUID:32位唯一编码)(必填)
	 * @apiParam {String} pkseq PK SEQ
	 * @apiParam {Integer} version Version(必填)
	 * @apiParam {JSONArray} columns 字段
	 * @apiParam {String} columns.id 字段id,新增的为空
	 * @apiParam {Integer} columns.colSeq 字段序号
	 * @apiParam {String} columns.colName 字段名称
	 * @apiParam {String} columns.colMark 字段备注
	 * @apiParam {Integer} columns.colLen 字段长度
	 * @apiParam {Integer} columns.colDecpoint 字段小数点位数
	 * @apiParam {String} columns.colDefval 字段默认值
	 * @apiParam {String} columns.colType 字段类型(string/int/double/date/decimal/text/blob)
	 * @apiParam {String} columns.colIspk 字段是否主键，Y:是主键
	 * @apiParam {String} columns.colIsnull 字段是否允许空值，Y:可为空
	 * @apiParam {JSONArray} indexs 索引
	 * @apiParam {String} indexs.id 字段id,新增的为空
	 * @apiParam {String} indexs.indexName 字段序号
	 * @apiParam {String} indexs.colName 字段名称
	 * @apiParam {String} indexs.indexType 字段备注
	 * @apiParam {String} indexs.indexSeq 字段长度
	 * 
	 * @apiParam {-} ORORORORORORORORORORROROROROR **********或者Confirm的数据结构**********
	 * @apiParam {String} id 表id
	 * @apiParam {String} tablename 表名
	 * @apiParam {Integer} version 表version
	 * @apiParam {JSONArray} tableChgs 表改动
	 * @apiParam {String} tableChgs.name 表改动属性（哪个属性改了）
	 * @apiParam {String} tableChgs.descp 表改动属性描述（这个属性的中文描述）
	 * @apiParam {String} tableChgs.bf 属性改动前的值
	 * @apiParam {String} tableChgs.af 属性改动后的值
	 * @apiParam {JSONArray} columnChgs 表字段改动
	 * @apiParam {String} columnChgs.colName 字段名
	 * @apiParam {String} columnChgs.id 字段ID
	 * @apiParam {String} columnChgs.chgFlag 改变类型，A：新增，U：更新，D:删除
	 * @apiParam {JSONArray} columnChgs.chgs 具体改变项
	 * @apiParam {String} columnChgs.chgs.name 改动属性（哪个属性改了）
	 * @apiParam {String} columnChgs.chgs.descp表改动属性描述（这个属性的中文描述）
	 * @apiParam {String} columnChgs.chgs.bf 属性改动前的值
	 * @apiParam {String} columnChgs.chgs.af 属性改动后的值
	 * @apiParam {JSONArray} indexChgs 表索引改动
	 * @apiParam {String} indexChgs.indexName 索引名
	 * @apiParam {String} indexChgs.id 索引ID
	 * @apiParam {String} indexChgs.chgFlag 改变类型，A：新增，U：更新，D:删除
	 * @apiParam {JSONArray} indexChgs.chgs 具体改变项
	 * @apiParam {String} indexChgs.chgs.name 改动属性（哪个属性改了）
	 * @apiParam {String} indexChgs.chgs.descp 改动属性描述（这个属性的中文描述）
	 * @apiParam {String} indexChgs.chgs.bf 属性改动前的值
	 * @apiParam {String} indexChgs.chgs.af 属性改动后的值
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * 参考6.确认表改动
	 * @apiSuccessExample {json} Success-Response:
	 * 	HTTP/1.1 200 OK
	 *  {
	 *    "version":2
	 *  }
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 *  {
	 *    "verrors":[
	 *      { "errCode": "pktype", "errorMsg": "主键类型不能为空" },
	 *      { "errCode": "tablename", "errorMsg": "表名不能为空" },
	 *      { "errCode": "tabledescp", "errorMsg": "表描述不能为空" },
	 *      { "errCode": "columns", "errorMsg": "字段不能为空" }
	 *      { "errCode": "", "errorMsg": "更新失败" }
	 *    ]
	 *  }
	 * 
	 * @param tableId
	 * @param parm
	 * @return
	 * @throws RsCommonException
	 */
	@PutMapping("/table/{tableId}")
	@ResponseBody
	public Object updateTable(@PathVariable("tableId") String tableId, @RequestBody JSONObject parm) throws RsCommonException{
		if(parm.getString(TableUpdateConfirmVO.CHG_FLAG)!=null){
			TableUpdateConfirmVO confirmVO = TableUpdateConfirmVO.parseJSON(parm);
			if(dbService.updateTableWithColumnConfirm(confirmVO)){
				JSONObject result = new JSONObject();
				result.put(VERSION, confirmVO.getVersion());
				return getSuccessResult(result);
			}else{
				addValidationError(new ValidateError("", "更新失败"));
				return getInvalidParamResult();
			}
		}else{
			MaintainTableVO tableVO = parm.toJavaObject(MaintainTableVO.class);
			tableVO.setTableid(tableId);
			if(!validateData(tableVO)){
				return getInvalidParamResult();
			}
			tableVO.formatColumnPrefix();
			if(dbService.updateTableWithColumn(tableVO)){
				JSONObject result = new JSONObject();
				result.put(VERSION, tableVO.getVersion());
				return getSuccessResult(result);
			}else{
				addValidationErrors(dbService.getServiceErrors());
				return getInvalidParamResult();
			}
		}
	}
	
	/**
	 * @api {PUT} /admin/db/table/{tableId}/confirm 6.确认表改动
	 * @apiDescription 给用户确认一下表改动
	 * @apiGroup Admin/Maintain Table
	 * 
	 * @apiParam {String} tabledescp 表描述(必填)
	 * @apiParam {String} pktype PK类型(UUID:32位唯一编码)(必填)
	 * @apiParam {String} pkseq PK SEQ
	 * @apiParam {Integer} version Version(必填)
	 * 
	 * @apiParam {JSONArray} columns 字段
	 * @apiParam {String} columns.id 字段id,新增的为空
	 * @apiParam {Integer} columns.colSeq 字段序号
	 * @apiParam {String} columns.colName 字段名称
	 * @apiParam {String} columns.colMark 字段备注
	 * @apiParam {Integer} columns.colLen 字段长度
	 * @apiParam {Integer} columns.colDecpoint 字段小数点位数
	 * @apiParam {String} columns.colDefval 字段默认值
	 * @apiParam {String} columns.colType 字段类型(string/int/double/date/decimal/text/blob)
	 * @apiParam {String} columns.colIspk 字段是否主键，Y:是主键
	 * @apiParam {String} columns.colIsnull 字段是否允许空值，Y:可为空
	 * 
	 * @apiParam {JSONArray} indexs 索引
	 * @apiParam {String} indexs.id 字段id,新增的为空
	 * @apiParam {String} indexs.indexName 字段序号
	 * @apiParam {String} indexs.colName 字段名称
	 * @apiParam {String} indexs.indexType 字段备注
	 * @apiParam {String} indexs.indexSeq 字段长度
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * {
     * "tablename": "tbl_ju-6846",
     * "pktype": "UUID",
     * "version": 1,
     * "tabledescp": "upd tabledescp",
     * "columns": [{
     *  "colDecpoint": 0,
     *  "colDefval": "abc",
     *  "colIsnull": "",
     *  "colIspk": "Y",
     *  "colLen": 10,
     *  "colMark": "mark_0",
     *  "colName": "name_0",
     *  "colSeq": 1,
     *  "colType": "string",
     *  "deleted": "F",
     *  "id": "f7f7a87f19174ba98da0c5084f02123e"
     * },
     * {
     *  "colSeq": 6,
     *  "colName": "name_0",
     *  "colIspk": "",
     *  "colMark": "mark_0",
     *  "colDecpoint": 0,
     *  "colIsnull": "",
     *  "colType": "string",
     *  "colLen": 10,
     *  "colDefval": ""
     * }],
     * "indexs": [{
     *  "colName": "name_0",
     *  "id": "cc98be1af0974ff6afa69e08adaea23f",
     *  "indexName": "name_0_index",
     *  "indexSeq": 1,
     *  "indexType": "normal"
     * },
     * {
     *  "colName": "name_2",
     *  "indexName": "name_2_index_new",
     *  "indexSeq": 3,
     *  "indexType": "normal"
     * }],
	 * }
	 * 
	 * @apiSuccess {String} id 表id
	 * @apiSuccess {String} tablename 表名
	 * @apiSuccess {Integer} version 表version
	 * 
	 * @apiSuccess {JSONArray} tableChgs 表改动
	 * @apiSuccess {String} tableChgs.name 表改动属性（哪个属性改了）
	 * @apiSuccess {String} tableChgs.descp 表改动属性描述（这个属性的中文描述）
	 * @apiSuccess {String} tableChgs.bf 属性改动前的值
	 * @apiSuccess {String} tableChgs.af 属性改动后的值
	 * 
	 * @apiSuccess {JSONArray} columnChgs 表字段改动
	 * @apiSuccess {String} columnChgs.colName 字段名
	 * @apiSuccess {String} columnChgs.id 字段ID
	 * @apiSuccess {String} columnChgs.chgFlag 改变类型，A：新增，U：更新，D:删除
	 * @apiSuccess {JSONArray} columnChgs.chgs 具体改变项
	 * @apiSuccess {String} columnChgs.chgs.name 改动属性（哪个属性改了）
	 * @apiSuccess {String} columnChgs.chgs.descp表改动属性描述（这个属性的中文描述）
	 * @apiSuccess {String} columnChgs.chgs.bf 属性改动前的值
	 * @apiSuccess {String} columnChgs.chgs.af 属性改动后的值
	 * 
	 * @apiSuccess {JSONArray} indexChgs 表索引改动
	 * @apiSuccess {String} indexChgs.indexName 索引名
	 * @apiSuccess {String} indexChgs.id 索引ID
	 * @apiSuccess {String} indexChgs.chgFlag 改变类型，A：新增，U：更新，D:删除
	 * @apiSuccess {JSONArray} indexChgs.chgs 具体改变项
	 * @apiSuccess {String} indexChgs.chgs.name 改动属性（哪个属性改了）
	 * @apiSuccess {String} indexChgs.chgs.descp 改动属性描述（这个属性的中文描述）
	 * @apiSuccess {String} indexChgs.chgs.bf 属性改动前的值
	 * @apiSuccess {String} indexChgs.chgs.af 属性改动后的值
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 * 	HTTP/1.1 200 OK
	 * {
     *  "id": "57d936ff2bff48f9860914811aaf9b69",
     *  "tablename": "tbl_ju8521",
     *  "version":1,
     *  "chgFlag":"U",
     *  "tableChgs": [
     *    {"bf": "tbl_ju85218521","af": "upd tabledescp","name": "tabledescp","descp": "表描述"}
     *  ],
     *  "indexChgs": [
     *   {
     *    "chgFlag": "A","indexName": "name_2_index_new","id": "",
     *    "chgs": [
     *     {"bf": "", "af": "name_2_index_new", "name": "indexName","descp": "索引名称"},
     *     {"bf": "","af": "name_2","name": "colName","descp": "索引栏位"},
     *     {"bf": "","af": "normal","name": "indexType","descp": "索引类型"}
     *    ]
     *   },
     *   {
     *    "chgFlag": "U","indexName": "name_0_index","id": "12b94c85392c4affb73653b18f77e29e",
     *    "chgs": [
     *     {"bf": "ju8521_name_0","af": "name_0","name": "colName","descp": "索引栏位"}
     *    ]
     *   },
     *   {
     *    "chgFlag": "D","indexName": "name_1_index","id": "7cf20e394117489fa83dc15a326e1a89",
     *    "chgs":[
     *     {"bf": "name_1_index","af": "","name": "indexName","descp": "索引名称" },
     *     {"bf": "ju8521_name_1", "af": "", "name": "colName", "descp": "索引栏位"},
     *     {"bf": "unique", "af": "", "name": "indexType", "descp": "索引类型"}
     *    ]
     *   }
     *  ],
     *  "columnChgs": [
     *   {
     *    "colName": "name_0",  "chgFlag": "A",  "id": "",
     *    "chgs": [
     *     {"bf": "","af": "name_0","name": "colName","descp": "字段名称"},
     *     {"bf": "","af": "mark_0","name": "colMark","descp": "字段备注"},
     *     {"bf": "","af": 10,"name": "colLen","descp": "字段长度"},
     *     {"bf": "","af": 0,"name": "colDecpoint","descp": "小数点"},
     *     {"bf": "","af": "","name": "colDefval","descp": "默认值"},
     *     {"bf": "","af": "string","name": "colType","descp": "字段类型"},
     *     {"bf": "","af": "","name": "colIspk","descp": "主键"},
     *     {"bf": "","af": "","name": "colIsnull","descp": "允许空值"}
     *    ]
     *   },
     *   {
     *    "colName": "ju8521_name_0","chgFlag": "U","id": "620b9d891b4141d8868f5d3c179e8855",
     *    "chgs": [
     *      {"bf": "ju8521_name_0","af": "name_0","name": "colName","descp": "字段名称"},
     * 	    {"bf": "","af": "abc","name": "colDefval","descp": "默认值"}
     *    ]
     *   },
     *   {
     *    "colName": "ju8521_name_3","chgFlag": "D","id": "b3904af705954fd3ab36c88f15109798",
     *    "chgs": [
     *      {"bf": "ju8521_name_3","af": "","name": "colName","descp": "字段名称"},
     *      {"bf": "mark_3","af": "","name": "colMark","descp": "字段备注"},
     * 	    {"bf": 13,"af": "","name": "colLen","descp": "字段长度"},
     * 	    {"bf": 0,"af": "","name": "colDecpoint","descp": "小数点"},
     * 	    {"bf": "","af": "","name": "colDefval","descp": "默认值"},
     * 	    {"bf": "string","af": "","name": "colType","descp": "字段类型"},
     * 	    {"bf": "","af": "","name": "colIspk","descp": "主键"},
     * 	    {"bf": "Y","af": "","name": "colIsnull","descp": "允许空值"}
     *    ]
     *   },
     *  ]
     * }
	 * @param tableId
	 * @param parm
	 * @return
	 * @throws RsCommonException
	 */
	@PutMapping("/table/{tableId}/confirm")
	@ResponseBody
	public Object confirmUpdateTable(@PathVariable("tableId") String tableId, @RequestBody JSONObject parm) throws RsCommonException{
		MaintainTableVO tableVO = parm.toJavaObject(MaintainTableVO.class);
		tableVO.setTableid(tableId);
		if(!validateData(tableVO)){
			return getInvalidParamResult();
		}
		TableUpdateConfirmVO confirmVO = dbService.confirmUpdateTable(tableVO);
		if(confirmVO==null){
			addValidationErrors(dbService.getServiceErrors());
			return getInvalidParamResult();
		}
		return getSuccessResult(confirmVO.getCompareResult());
	}
	
	/**
	 * @api {PUT} /admin/db/table/{tableId}/sync 8.同步表改动到数据库
	 * @apiGroup Admin/Maintain Table

	 * @apiParam {String} tableId Table ID
	 * @apiParam {Integer} version Table version
	 * @apiParam {Boolean} forceDrop 是否强制同步:true/false/不传(默认false:不强制同步,同步可能会失败)
	 * @apiParamExample {json} Request-Example: 
	 * {"version":1, "forceDrop": false}
	 * 
	 * @apiSuccessExample {json} Success-Response:
	 * HTTP/1.1 200 OK
	 * { "version":2 }
	 * 
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 *  {
	 *    "verrors":[
	 *      { "errCode": "", "errorMsg": "version不允许为空" },
	 *      { "errCode": "", "errorMsg": "记录不存在或者已经被更新" },
	 *      { "errCode": "", "errorMsg": "已经同步过了" },
	 *      { "errCode": "", "errorMsg": "同步失败" }
	 *      { "errCode": "", "errorMsg": "同步失败,请保存好数据,并尝试强制同步" }
	 *    ]
	 *  }
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 400 Bad Request
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 302 Found
	 * @apiErrorExample {json} Error-Response:
	 * 		HTTP/1.1 409 Conflict
	 * 
	 * @param tableId
	 * @param parm
	 * @return
	 * @throws RsCommonException
	 */
	@PutMapping("/table/{tableId}/sync")
	@ResponseBody
	public Object syncTable(@PathVariable("tableId") String tableId, @RequestBody JSONObject parm) throws RsCommonException{
		Integer version = parm.getInteger(VERSION);
		if(version==null){
			addValidationError(new ValidateError("", "version不允许为空"));
			return getInvalidParamResult();
		}
		SysTables table = generalMapper.selectById(tableId, SysTables.class);
		if(table==null){
			addValidationError(new ValidateError("", "记录不存在或者已经被更新"));
			return getInvalidParamResult();
		}
		if(Constants.YES.equalsIgnoreCase(table.getSync())){
			addValidationError(new ValidateError("", "已经同步过了"));
			return getInvalidParamResult();
		}
		Boolean forceDrop = parm.getBoolean("forceDrop");
		if(forceDrop==null){
			forceDrop = false;
		}
		if(dbService.syncTable(getCurrentProjectId(), table, forceDrop)){
			JSONObject result = new JSONObject();
			result.put(VERSION, table.getVersion());
			return getSuccessResult(result);
		}else{
			if(forceDrop){
				addValidationError(new ValidateError("", "同步失败"));
			}else{
				addValidationError(new ValidateError("", "同步失败,请保存好数据,并尝试强制同步"));
			}
			return getInvalidParamResult();
		}
	}
	
}
