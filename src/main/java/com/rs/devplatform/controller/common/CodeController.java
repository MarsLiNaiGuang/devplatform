package com.rs.devplatform.controller.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.rs.devplatform.persistent.SysCdval;
import com.rs.devplatform.service.common.CodeService;
import com.rs.framework.common.controller.base.BaseController;

@RestController
@RequestMapping("/common/code")
public class CodeController extends BaseController {
	
	private static final String CD_TYPE = "cdtype";
	private static final String DICT_TABLE = "dictTable";
	private static final String CD_VAL_COL = "cdvalCol";
	private static final String CD_DESCP_COL = "cddescpCol";
	
	@Autowired
	CodeService codeService;

	/**
	 * @api {GET} /common/code/values 获取字典values
	 * @apiDescription 根据dictTable, cdvalCol,cddescpCol查询某个【自定义字典类型】所有codeValues；或者根据cdtype获取字典表单codeValues
	 * @apiGroup Common/Code
	 * 
	 * @apiParam {String} dictTable 字典Table（从【字典表】中取，无需指定dictTable/cdvalCol/cddescpCol）
	 * @apiParam {String} cdvalCol 字典Code
	 * @apiParam {String} cddescpCol 字典Value
	 * @apiParam {String} cdtype 数据字典里的类型（从【自定义字典表】获取不需要指定该值）
	 * 
	 * @apiExample {curl} Example usage:
 *     curl -i http://localhost/common/code/values?dictTable=xx_table&cdvalCol=xx_col&cddescpCol=xx_descp
 *     curl -i http://localhost/common/code/values?cdtype=GENDER	到字典表获取类型为GENDER的所有字典values
	 * 
	 * @apiSuccess {JSONArray} cdvals CodeType下对应的CodeValues
	 * @apiSuccess {String} cdvals.cdval 	CodeVal
	 * @apiSuccess {String} cdvals.cddescp  CodeVal Descp
	 * @apiSuccessExample {json} Success-Response:
	 * 		HTTP/1.1 200 OK
	 * 		{
	 * 			"cdvals":[
		 * 			{"cddescp":"value3","cdval":"3"},
		 * 			{"cddescp":"value1","cdval":"1"},
		 * 			{"cddescp":"value2","cdval":"2"}
	 * 			]
	 * 		}
	 */
	@GetMapping(value="/values")
	@ResponseBody
	public Object getCodeTypeValues(HttpServletRequest req){
		String dictTable = req.getParameter(DICT_TABLE);
		String cdvalCol = req.getParameter(CD_VAL_COL);
		String cddescpCol = req.getParameter(CD_DESCP_COL);
		String cdtype = req.getParameter(CD_TYPE);
		List<SysCdval> valList = null;
		if(!StringUtils.isEmpty(cdtype)){
			valList = codeService.getCodeListByCodeType(cdtype);
		}else{
			valList = codeService.getCodeListByDistTable(dictTable, cdvalCol, cddescpCol);
		}
		JSONObject result = new JSONObject();
		if(valList!=null){
			Object[] arr = valList.stream().map((x)->{
				JSONObject obj = new JSONObject();
				obj.put("cdval", x.getCdval());
				obj.put("cddescp", x.getCddescp());
				return obj;
			}).toArray();
			result.put("cdvals", arr);
		}
		return getSuccessResult(result);
	}
	
	//TODO:
	//clear code cache
	
	
}
