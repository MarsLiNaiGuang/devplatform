package com.rs.devplatform.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class RSCodeGenerator {

	public static void main(String[] args) throws IOException, TemplateException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
		cfg.setClassLoaderForTemplateLoading(RSCodeGenerator.class.getClassLoader(),"cg/templates");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		String beanName = "CgSample";
		String SubModule = "sample";
		String basePackage = "com.xx";
		EntityTable table = new EntityTable();
		table.setBasePackage(basePackage);
		table.setSubModule(SubModule);
		table.setTableComments("示例");
		table.setTableName("tbl_sample");
		table.setBeanName(beanName);
		table.setAuthor("sample test");
		
		table.setImports(Arrays.asList(new String[]{"java.util.Date"}));

		List<EntityField> fieldList = new ArrayList<EntityField>();
		EntityField pkfield = new EntityField();
		pkfield.setPk(true);
		pkfield.setColumnName("sample_id");
		pkfield.setBeanPropertyName("id");
		pkfield.setFormPropertyName("id主键");
		pkfield.setFormData(false);
		pkfield.setGridData(false);
		pkfield.setTypeSimple("String");
		fieldList.add(pkfield);
		
		EntityField field = new EntityField();
		field.setColumnName("sample_name");
		field.setBeanPropertyName("name");
		field.setFormPropertyName("Username用户名");
		field.setFormData(true);
		field.setGridData(true);
		field.setTypeSimple("String");
		field.setUnique(true);
		field.setNullable(false);
		field.setQuery(true);
		field.setFuzzyQuery(true);
		fieldList.add(field);
		
		field = new EntityField();
		field.setColumnName("sample_nkname");
		field.setBeanPropertyName("nkname");
		field.setFormPropertyName("NickName昵称");
		field.setFormData(true);
		field.setGridData(true);
		field.setTypeSimple("String");
		field.setUnique(false);
		field.setNullable(false);
		field.setHasDefaultVal(true);
		field.setDefaultVal("\"nkname\"");
		field.setQuery(true);
		fieldList.add(field);
		
		field = new EntityField();
		field.setColumnName("sample_age");
		field.setBeanPropertyName("age");
		field.setFormPropertyName("年龄");
		field.setFormData(true);
		field.setGridData(true);
		field.setTypeSimple("Integer");
		field.setUnique(false);
		field.setNullable(true);
		field.setHasDefaultVal(true);
		field.setDefaultVal("1");
		field.setQuery(true);
		field.setRangeQuery(true);
		fieldList.add(field);
		
		field = new EntityField();
		field.setColumnName("sample_dt");
		field.setBeanPropertyName("dt");
		field.setFormPropertyName("测试日期");
		field.setFormData(true);
		field.setGridData(true);
		field.setTypeSimple("Date");
		field.setUnique(true);
		field.setNullable(true);
		field.setHasDefaultVal(false);
		field.setDefaultVal(null);
		field.setQuery(true);
		field.setRangeQuery(true);
		fieldList.add(field);
		
		field = new EntityField();
		field.setColumnName("sample_dt2");
		field.setBeanPropertyName("dt2");
		field.setFormPropertyName("测试日期");
		field.setFormData(true);
		field.setGridData(true);
		field.setTypeSimple("Date");
		field.setUnique(true);
		field.setNullable(false);
		field.setHasDefaultVal(false);
		field.setDefaultVal(null);
		field.setQuery(true);
		field.setRangeQuery(false);
		fieldList.add(field);
		
		List<String> basicColumns = new ArrayList<String>();
//		basicColumns.add("id");
		basicColumns.add("whr");
		basicColumns.add("whrid");
		basicColumns.add("whsj,Date");
		basicColumns.add("cjr");
		basicColumns.add("cjrid");
		basicColumns.add("cjsj,Date");
		basicColumns.add("deleted");
		basicColumns.add("version,Integer");
		
		String columnPrefix = "sample_";
		for(String str:basicColumns){
			field = new EntityField();
			String[] arr = str.split(",");
			String simpleType = arr.length==1?"String":arr[1];
			String basicColumn = columnPrefix+arr[0];
			field.setColumnName(basicColumn);
			field.setBeanPropertyName(arr[0]);
			field.setFormPropertyName(arr[0]);
			field.setFormData(false);
			field.setGridData(false);
			field.setTypeSimple(simpleType);
			field.setUnique(false);
			field.setNullable(false);
			field.setBasicColumn(true);
			fieldList.add(field);
		}
		
		
		table.setEntityFields(fieldList);
		
		Template temp = null;
		String path = RSCodeGenerator.class.getClassLoader().getResource("").getPath();
		System.err.println("path======================="+path);
		File folder = new File(path+(StringUtils.isEmpty(SubModule)?"":SubModule));
		if(!folder.exists()){
			folder.mkdirs();
		}
		try(
			FileOutputStream foEntity = new FileOutputStream(new File(path+beanName+".java"));
				FileOutputStream foService = new FileOutputStream(new File(path+beanName+"Service.java"));
				FileOutputStream foServiceImpl = new FileOutputStream(new File(path+beanName+"ServiceImpl.java"));
				FileOutputStream foController = new FileOutputStream(new File(path+beanName+"Controller.java"));
				FileOutputStream foEntityVO = new FileOutputStream(new File(path+beanName+"VO.java"));
				FileOutputStream foPage = new FileOutputStream(new File(path+(StringUtils.isEmpty(SubModule)?beanName+".html":SubModule+"/"+beanName+".html")));
			Writer outEntity = new OutputStreamWriter(foEntity);
				Writer outService = new OutputStreamWriter(foService);
				Writer outServiceImpl = new OutputStreamWriter(foServiceImpl);
				Writer outController = new OutputStreamWriter(foController);
				Writer outEntityVO = new OutputStreamWriter(foEntityVO);
				Writer outEntityPage = new OutputStreamWriter(foPage);
				){
			temp = cfg.getTemplate("entity.java.ftl");
			temp.process(table, outEntity);
			
			System.err.println("============================");
			temp = cfg.getTemplate("service.java.ftl");
			temp.process(table, outService);
	//		Map<String,Object> map = new HashMap<String,Object>(1);
	//		map.put("beanName", beanName);
	//		map.put("basePackage", basePackage);
	//		map.put("subModule", SubModule);
	//		temp.process(map, out);
			System.err.println("------------------------------");
			temp = cfg.getTemplate("serviceImpl.java.ftl");
			temp.process(table, outServiceImpl);
			
			temp = cfg.getTemplate("entityVO.java.ftl");
			temp.process(table, outEntityVO);
			temp = cfg.getTemplate("controller.java.ftl");
			temp.process(table, outController);
//			temp = cfg.getTemplate("page-ui.ftl");
//			temp.process(table, outEntityPage);
		}
		
		
	}
}
