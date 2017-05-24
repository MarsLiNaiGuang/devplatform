package com.rs.devplatform.controller.cg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.persistent.SysBxx;
import com.rs.devplatform.persistent.SysZdxx;
import com.rs.devplatform.service.cg.OnlineFuncService;
import com.rs.devplatform.utils.onlinefunc.RsSqlColumnConditionParser;
import com.rs.devplatform.utils.onlinefunc.RsSqlTokenHandler;
import com.rs.devplatform.vo.cg.Col2ZdxxConverter;
import com.rs.devplatform.vo.cg.Table2BxxConverter;
import com.rs.devplatform.vo.onlinefc.OnlineFcColumn;
import com.rs.devplatform.vo.onlinefc.OnlineFcCondition;
import com.rs.devplatform.vo.onlinefc.OnlineFuncVO;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.controller.base.BaseController;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * 在线功能开发<BR>
 * 通过SQL（包含了多张辅助表的查询）定义功能：<BR>
 * 1)主表记录查询+辅助表信息拉取；<BR>
 * 2)主表数据的维护（增删改）
 * 
 * @author yuxiaobin
 *
 */
@RestController
@RequestMapping("/cg/onlinefc")
public class OnlineFuncController extends BaseController {

	public static final String SQL_PARM = "sql";
	
	@Autowired
	OnlineFuncService onlineFcService;

	/**
	 * @api {POST} /cg/onlinefc/sql 1.解析sql
	 * @apiDescription 解析sql并返回字段和条件参数
	 * @apiGroup OnlineFunc
	 * @apiParam {String} sql SQL语句
	 * @apiParamExample {json} Request-Example: 
	 * {"sql":"select username as name, password from user,test_group where username=#{abc} and password='123456'"}
	 * 
	 * @apiSuccess {Array} tables sql出现的表名列表
	 * 
	 * @apiSuccess {JSONArray} columns 查询字段列表
	 * @apiSuccess {String} columns.colName 查询字段名
	 * @apiSuccess {String} columns.alias 查询字段别名,SQL中出现的AS后面的值
	 * @apiSuccess {String} columns.isgriddisp 是否表格显示，**默认Y**为显示
	 * 
	 * @apiSuccess {JSONArray} conditions 查询条件
	 * @apiSuccess {String} conditions.colName 条件变量名(SQL变量统一命名规则username=#{var}, 变量名:var)
	 * @apiSuccess {String} conditions.expr 条件表达式(SQL变量统一命名规则: username=#{var})
	 * @apiSuccess {String} conditions.issearch 是否作为查询条件，**默认Y**
	 * @apiSuccessExample {json} Sample:
	 * HTTP/1.1 200 OK
	 * {
 "tables": ["user"],
 "columns": [{
  "isgriddisp": "Y",
  "alias": "name",
  "colName": "username"
 },
 {
  "isgriddisp": "Y",
  "alias": "",
  "colName": "password"
 }],
 "conditions": [{
  "issearch": "Y",
  "expr": "username = #{abc}",
  "colName": "abc"
 }]
}
	 * @apiSuccessExample {json} Sample:
	 * 	HTTP/1.1 200 OK
	 * {"id":"9ae3951049e145a2a50cb351497aad5c","version":1}
	 * @apiErrorExample {json} Error-Response:
	 *  HTTP/1.1 400 Bad Request
	 *  {
	 *    "verrors":[
	 *      {"errCode":"","errorMsg":"SQL不能为空"},
	 *      {"errCode":"","errorMsg":"SQL格式错误"},
	 *      {"errCode":"tree","errorMsg":"树形展示不能不设置父节点和子节点"},
	 *      {"errCode":"tree","errorMsg":"父子节点不能相同"},
	 *      {"errCode":"child","errorMsg":"子节点在字段中不存在"},
	 *      {"errCode":"parent","errorMsg":"父节点在字段中不存在"}
	 *      ]
	 *  }
	 * @param parm
	 * @return
	 */
	@PostMapping("/sql")
	public Object preprocessSQL(@RequestBody JSONObject parm) {
		String sql = parm.getString(SQL_PARM);
		if (StringUtils.isBlank(sql)) {
			addValidationError("sql不能为空");
			return getInvalidParamResult();
		}
		try{
			JSONObject result = new JSONObject();
			RsSqlTokenHandler handler = new RsSqlTokenHandler();
			GenericTokenParser parser = new GenericTokenParser(RsSqlTokenHandler.OPEN_TOKEN,
					RsSqlTokenHandler.CLOSE_TOKEN, handler);
			String formatSql = parser.parse(sql);
			Statement stmt = CCJSqlParserUtil.parse(formatSql);
			Select selstmt = (Select) stmt;
			RsSqlColumnConditionParser myparser = new RsSqlColumnConditionParser();
			myparser.parseSelect(selstmt);			
			List<OnlineFcColumn> columnNames = myparser.getColumnNames();
			
			List<String> conditionParams = handler.getParamNames();//testAge,age
			List<OnlineFcCondition> paramNames = new ArrayList<>(conditionParams.size());
			for(Expression exp:myparser.getConditions()){//u.age = GENVAL0, a.age > GENVAL1
				String expStr = handler.revertExp(exp.toString());//u.age = #{age}
				for(String paramName: conditionParams){
					if(expStr.contains(RsSqlTokenHandler.OPEN_TOKEN+paramName+RsSqlTokenHandler.CLOSE_TOKEN)){
						OnlineFcCondition cond = new OnlineFcCondition();
						cond.setColName(paramName);
						cond.setExpr(expStr);
						paramNames.add(cond);
					}
				}
			}
			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
			result.put("columns", columnNames);
			result.put("conditions", paramNames);
			result.put("tables", tablesNamesFinder.getTableList(stmt));
			return getSuccessResult(result);
		}catch (JSQLParserException e) {
			logger.error("preprocessSQL(), failed to parse sql={}", sql);
			addValidationError("sql格式错误");
			return getInvalidParamResult();
		}
	}
	
	/**
	 * @api {POST} /cg/form 2.保存在线功能数据
	 * @apiDescription 保存数据,参考{POST} /cg/form
	 * @apiGroup OnlineFunc
	 * @param parm
	 * @return
	 */
	@PostMapping()
	public Object saveOnlineFunc(@RequestBody JSONObject parm){
		OnlineFuncVO funcVO = parm.toJavaObject(OnlineFuncVO.class);
		if(!validateData(funcVO)){
			return getInvalidParamResult();
		}
		if(!funcVO.isValidSQL()) {
			logger.error("saveOnlineFunc(), invalid sql format={}", funcVO.getSql());
			addValidationError("sql","SQL格式错误");
			return getInvalidParamResult();
		}
		SysBxx bxxparm = new SysBxx();
		bxxparm.setFcflag(funcVO.getFcflag());
		if(isDuplicateBeforeCreate(bxxparm)){
			addValidationError("fcflag", "功能编码重复");
			return getDuplicateResult();
		}
		if("Y".equalsIgnoreCase(funcVO.getIstree())){
			if(StringUtils.isEmpty(funcVO.getChild()) || StringUtils.isEmpty(funcVO.getParent())){
				addValidationError("tree","树形展示不能不设置父节点和子节点");
				return getInvalidParamResult();
			}
			else if(funcVO.getChild().equals(funcVO.getParent())){
				addValidationError("tree","父子节点不能相同");
				return getInvalidParamResult();
			}
			else if(!funcVO.isInColumn(funcVO.getParent())){
				addValidationError("parent","父节点在字段中不存在");
				return getInvalidParamResult();
			}
			else if(!funcVO.isInColumn(funcVO.getChild())){
				addValidationError("child","子节点在字段中不存在");
				return getInvalidParamResult();
			}
		}
		if(onlineFcService.saveOnlineFunc(funcVO)){
			JSONObject result = new JSONObject();
			result.put(ID, funcVO.getTableId());
			result.put(VERSION, funcVO.getVersion());
			return getSuccessResult(result);
		}else{
			addValidationError("保存失败");
			return getInvalidParamResult();
		}
	}
	
	
	/**
	 * @api {GET} /cg/form/{tableId} 3.获取在线功能详情
	 * @apiDescription 获取在线功能详情，请参考CG：查询表单详情{GET} /cg/form/{tableId}
	 * @apiGroup OnlineFunc
	 * @return
	 */
	@GetMapping("/{tableId}")
	public Object getOnlineFunc(@PathVariable("tableId") String tableId){
		SysBxx bxx = generalMapper.selectById(tableId, SysBxx.class);
		if(bxx==null){
			addValidationError("没有匹配的记录");
			return getInvalidParamResult();
		}
		return getOnlineFunc(bxx);
	}
	
	/**
	 * @param bxx
	 * @return
	 */
	public Object getOnlineFunc(SysBxx bxx){
		OnlineFuncVO funcVO = Table2BxxConverter.convert2OnlineFuncVO(bxx);
		SysZdxx zdParm = new SysZdxx();
		zdParm.setBid(bxx.getId());
		RsEntityWrapper<SysZdxx> ew = new RsEntityWrapper<>(zdParm); 
		ew.orderBy("ZDXX_XH");
		List<SysZdxx> ziduanList = generalMapper.selectList(ew);
		if(ziduanList!=null && !ziduanList.isEmpty()){
			List<SysZdxx> columnList = new ArrayList<>();
			List<SysZdxx> conditionList = new ArrayList<>();
			for(SysZdxx zd:ziduanList){
				String condFlag = zd.getCondflag();
				if(condFlag==null){
					columnList.add(zd);
				}else{
					switch (condFlag) {
					case "B":
						columnList.add(zd);
						conditionList.add(zd);
						break;
					case "C":
						conditionList.add(zd);
						break;
					default:
						columnList.add(zd);
						break;
					}
				}
			}
			funcVO.setColumns(Col2ZdxxConverter.convertBatch2OnlineFcColumn("", columnList));
			funcVO.setConditions(Col2ZdxxConverter.convertBatch2OnlineFcCondition("", conditionList));
		}
		return getSuccessResult((JSONObject)JSON.toJSON(funcVO));
	}
	
	/**
	 * @api {PUT} /cg/form/{tableId} 4.更新表单(SQL)
	 * @apiDescription 更新表单，请参考CG：查询表单详情{PUT} /cg/form/{tableId}
	 * @apiGroup OnlineFunc
	 * @apiParam {String} tableId 表ID

	 * @param tableId
	 * @param parm
	 * @return
	 */
	@PutMapping("/{tableId}")
	public Object updateOnlineFunc(@PathVariable("tableId") String tableId, @RequestBody JSONObject parm){
		OnlineFuncVO funcVO = parm.toJavaObject(OnlineFuncVO.class);
		return updateOnlieFuncVO(funcVO);
	}
	
	public Object updateOnlieFuncVO(OnlineFuncVO funcVO){
		if(!validateData(funcVO)){
			return getInvalidParamResult();
		}
		if(!funcVO.isValidSQL()) {
			logger.error("saveOnlineFunc(), invalid sql format={}", funcVO.getSql());
			addValidationError("sql","SQL格式错误");
			return getInvalidParamResult();
		}
		if(funcVO.getVersion()==null){
			addValidationError("","Version不能为空");
			return getInvalidParamResult();
		}
		if("Y".equalsIgnoreCase(funcVO.getIstree())){
			if(StringUtils.isEmpty(funcVO.getChild()) || StringUtils.isEmpty(funcVO.getParent())){
				addValidationError("tree","树形展示不能不设置父节点和子节点");
				return getInvalidParamResult();
			}
			else if(funcVO.getChild().equals(funcVO.getParent())){
				addValidationError("tree","父子节点不能相同");
				return getInvalidParamResult();
			}
			else if(!funcVO.isInColumn(funcVO.getParent())){
				addValidationError("parent","父节点在字段中不存在");
				return getInvalidParamResult();
			}
			else if(!funcVO.isInColumn(funcVO.getChild())){
				addValidationError("child","子节点在字段中不存在");
				return getInvalidParamResult();
			}
		}
		if(onlineFcService.updateOnlineFunc(funcVO)){
			JSONObject result = new JSONObject();
			result.put(VERSION, funcVO.getVersion());
			return getSuccessResult(result);
		}else{
			return getModifiedResult();
		}
	}
	
	/**
	 * @api {DELETE} /cg/form/{tableId} 5.删除表单(SQL)
	 * @apiDescription 删除表单，请参考CG：查询表单详情{DELETE} /cg/form/{tableId}
	 * @apiGroup OnlineFunc
	 * @apiParam {String} tableId 表ID

	 * @param tableId
	 * @param parm
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@DeleteMapping("/{tableId}")
	public void deleteOnlieFunc(@PathVariable("tableId") String tableId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/cg/form/"+tableId).forward(request, response);
	}
	
}
