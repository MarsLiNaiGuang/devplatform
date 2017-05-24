package com.rs.devplatform.generator.util;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.rs.devplatform.generator.AutoGenerator;
import com.rs.devplatform.generator.EntityField;
import com.rs.devplatform.generator.EntityTable;
import com.rs.devplatform.generator.MybatisGenerator;
import com.rs.devplatform.generator.RSCodeGenerator;
import com.rs.devplatform.generator.RSConfigGenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class GenerateDocFromDB {

	
	public static void main(String[] args) throws SQLException {
		String subModule = "";//子模块
		GenerateDocFromDB.generateDoc4TableName("sys_custbt", subModule);
	}
	
	public static void generateDoc4TableName(String tableName, String subModule) throws SQLException{
		RSConfigGenerator cg = new RSConfigGenerator();
		cg.setTableNames(new String[]{tableName});
		MybatisGenerator.setupMysql(cg);
		AutoGenerator autoGenerator = new AutoGenerator(cg);
		Map<String,String> tableComments = autoGenerator.getTableComment();
		Set<Entry<String, String>> set = tableComments.entrySet();
		Entry<String,String> ety = set.iterator().next();
		String tableComment = ety.getValue();
		
		Map<String,Object> dbInfoMap = new AutoGenerator(cg).getTableColumnInfoFromDB(tableName);
		@SuppressWarnings("unchecked")
		List<String> columns = (List<String>) dbInfoMap.get("columns");
		@SuppressWarnings("unchecked")
		List<String> types = (List<String>) dbInfoMap.get("types");
		@SuppressWarnings("unchecked")
		List<String> comments = (List<String>) dbInfoMap.get("comments");
		List<EntityField> fields = new ArrayList<>();
		EntityField field = null;
		if(columns!=null){
			int size = columns.size();
			String suffix_ = (tableName.substring(tableName.lastIndexOf("_")+1)+"_").toUpperCase();
			for(int i=0;i<size;++i){
				String columnName = columns.get(i).toUpperCase();
				String type = types.get(i);
				String comment = comments.get(i);
				String simpleType = AutoGenerator.processType(type, cg);
				field = new EntityField();
				field.setBeanPropertyName(AutoGenerator.processFieldWithoutTblSuffix(columnName, suffix_));
				field.setColumnName(columnName);
				field.setFormPropertyName(comment);
				field.setFormData(true);
				field.setGridData(true);
				field.setTypeSimple(simpleType);
				fields.add(field);
			}
		}
		
		EntityTable tableEntity = new EntityTable();
		tableEntity.setBasePackage("");
		tableEntity.setBeanName("doc");
		tableEntity.setTableName(tableName);
		tableEntity.setEntityFields(fields);
		tableEntity.setTableComments(tableComment);
		tableEntity.setSubModule(subModule);
		
		Template temp = null;
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
		cfg.setClassLoaderForTemplateLoading(RSCodeGenerator.class.getClassLoader(),"generator");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		try(
			Writer outEntity = new OutputStreamWriter(System.out);
				){
			temp = cfg.getTemplate("apidoc/apidoc.ftl");
			temp.process(tableEntity, outEntity);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
