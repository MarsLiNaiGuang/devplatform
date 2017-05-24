package com.rs.devplatform.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;


import com.baomidou.mybatisplus.generator.ConfigDataSource;
import com.baomidou.mybatisplus.generator.ConfigGenerator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class RSCodeGenerator4SingleTable {

	public static ConfigGenerator getConfig() {
		RSConfigGenerator cg = new RSConfigGenerator();
		cg.setConfigDataSource(ConfigDataSource.MYSQL);
		cg.setDbDriverName("com.mysql.jdbc.Driver");
		cg.setDbUser("root");
		cg.setDbPassword("root");
		cg.setDbUrl("jdbc:mysql://192.168.10.64:3306/dev-platform?useUnicode=true&characterEncoding=UTF8");
		
		
		//cg.setConfigDataSource(ConfigDataSource.ORACLE);
		//cg.setDbDriverName("oracle.jdbc.driver.OracleDriver");
		//cg.setDbUser("rsdev");
		//cg.setDbPassword("rsdev");
		//cg.setDbUrl("jdbc:oracle:thin:@192.168.10.64:1521/orcl3");

		return cg;
	}

	public static Connection getConnection() {
		try {
			Class.forName(getConfig().getDbDriverName());
			Connection conn = DriverManager.getConnection(getConfig().getDbUrl(), getConfig().getDbUser(),
					getConfig().getDbPassword());
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static EntityTable getTableColumnInfoFromDB(String table, String fcFlag) throws SQLException {
		return getSQLTableColumnInfoFromDB(getConnection(), table, fcFlag);
	}

	public static EntityTable processResult(ResultSet resultSet) throws SQLException {
		EntityTable entityTable = new EntityTable();

		List<EntityField> fieldList = new ArrayList<EntityField>();
		while (resultSet.next()) {
			String columnName = resultSet.getString(1).toLowerCase();
				String columnNr = resultSet.getString(2);
				String isKey = resultSet.getString(3);
				String isNull = resultSet.getString(4);
				String isQuery = resultSet.getString(5);
				String isShow = resultSet.getString(6);
				String isShowlb = resultSet.getString(7);
				String type = resultSet.getString(8);
				String style = resultSet.getString(9);
				EntityField field = new EntityField();
				field.setColumnName(columnName);
				field.setBeanPropertyName(columnName.substring(columnName.indexOf("_") + 1));
				field.setFormPropertyName(columnNr);

				field.setFormData("Y".equals(isShow));
				field.setGridData("Y".equals(isShowlb));

				field.setTypeSimple(processType(type));

				field.setPk("Y".equals(isKey));

				field.setNullable("Y".equals(isNull));
				// field.setDefaultVal("1");
				field.setQuery("Y".equals(isQuery));
				field.setRangeQuery("range".equals(style));
				field.setFuzzyQuery("fuzzy".equals(style));
				fieldList.add(field);
		}
		entityTable.setEntityFields(fieldList);
		return entityTable;

	}

	/**
	 * 获取数据库表相关信息
	 */
	public static EntityTable getTableColumnInfoFromDB(Connection conn, String table) throws SQLException {

		// String tableFieldsSql = "select zdxx_name, zdxx_nr, zdxx_iskey,
		// zdxx_isnull, zdxx_isquery, zdxx_isshow,zdxx_isshowlb, zdxx_lx,
		// zdxx_cxms from sys_zdxx where ZDXX_BID=select bxx_id from sys_bxx
		// where BXX_BM= '" + table +"' and BXX_ISQL is null";

		String tableFieldsSql = "select zdxx_name, zdxx_nr, zdxx_iskey, zdxx_isnull, zdxx_isquery, zdxx_isshow,zdxx_isshowlb, zdxx_lx, zdxx_cxms from sys_zdxx where ZDXX_BID = ( select bxx_id from sys_bxx where BXX_BM='"
				+ table + "' and BXX_ISQL is null)";
		ResultSet results = conn.prepareStatement(tableFieldsSql).executeQuery();

		EntityTable entityTable = processResult(results);

		String subtableSql = "select bxx_zb from sys_bxx where BXX_BM='" + table
				+ "' and bxx_zb is not null and BXX_ISQL is null";
		results = conn.prepareStatement(subtableSql).executeQuery();
		List<String> subTableList = new ArrayList<>();
		while (results.next()) {

			JSONArray jsonArray = new JSONArray(results.getString(1));

			for (int i = 0; i < jsonArray.length(); i++) {
				String subTableName = jsonArray.getJSONObject(i).getString("tablename");
				String sunCloumnName = jsonArray.getJSONObject(i).getString("colName");
				subTableList.add(subTableName + ":" + sunCloumnName);
			}
		}

		if (subTableList.size() > 0) {
			entityTable.setIsMainTable(true);
		} else {
			entityTable.setIsMainTable(false);
		}
		entityTable.setSubTableNames(subTableList);
		if (conn != null) {
			conn.close();
		}

		return entityTable;
	}
	
	private static List<String> getCustCodeList (Connection conn, String fcFlag) throws SQLException {
		
		String sql = "select zqsql_code, zqsql_sql from sys_zqsql, sys_custbt where sys_zqsql.ZQSQL_BDID = sys_custbt.custbt_bdid"
				+ " and sys_zqsql.zqsql_code= sys_custbt.custbt_code" 
				+ " and ZQSQL_BDID = (select bxx_id from sys_bxx where bxx_fcflag='" +fcFlag +"')"
				+ " and ZQSQL_deleted='F'";
		
		List<String> custButList = new ArrayList<>();
		
		ResultSet results = conn.prepareStatement(sql).executeQuery();
		while (results.next()) {
			String code  = results.getString(1);
			Blob sqlBlob = results.getBlob(2);
			if (sqlBlob != null) {
				String innerSql = 	new String (sqlBlob.getBytes((long)1, (int)sqlBlob.length()));
				custButList.add(code + ":" + innerSql);
			}		
		}		
		return custButList;
				
	}
	
	public static EntityTable getSQLTableColumnInfoFromDB(Connection conn, String table, String fcFlag) throws SQLException {

		String tableFieldsSql = "select zdxx_name, zdxx_nr, zdxx_iskey, zdxx_isnull, zdxx_isquery, zdxx_isshow,zdxx_isshowlb, zdxx_lx, zdxx_cxms from sys_zdxx where ZDXX_BID = ( select bxx_id from sys_bxx where BXX_BM='"
				+ table + "' and BXX_FCFLAG='" + fcFlag+"')";
		ResultSet results = conn.prepareStatement(tableFieldsSql).executeQuery();

		EntityTable entityTable = processResult(results);

		String subtableSql = "select bxx_zb, BXX_ISQL, BXX_ID from sys_bxx where BXX_BM='" + table
				+ "' and  BXX_FCFLAG='"+fcFlag+"'";
		results = conn.prepareStatement(subtableSql).executeQuery();
		List<String> subTableList = new ArrayList<>();
		List<String> subFcFlagList = new ArrayList<>();
		String tableId = null;
		while (results.next()) {
			JSONArray jsonArray = null;
			if (results.getString(1) != null) {
				jsonArray = new JSONArray(results.getString(1));
			}

			String sqlInTable  = results.getString(2);
			tableId = results.getString(3);
			if ( sqlInTable!= null && !"".equals(sqlInTable)) {
				//Remove select as will be added back on the mapper.xml
				entityTable.setSqlInTable(sqlInTable.toUpperCase().replaceFirst("SELECT", " "));
				entityTable.setIsSql(true);
			}			
			if (jsonArray != null && jsonArray.length() > 0) {
				
				for (int i = 0; i < jsonArray.length(); i++) {
					String subTableName = jsonArray.getJSONObject(i).getString("tablename");
					String flag = jsonArray.getJSONObject(i).getString("fcflag");
					String sunCloumnName = jsonArray.getJSONObject(i).getString("colName");
					String subSqlInTable =null;
					if (flag != null && !"".equals(flag)) {
						String subSql = "select bxx_isql from sys_bxx where bxx_fcflag='" + flag +"'";						
						ResultSet subResults = conn.prepareStatement(subSql).executeQuery();
						while (subResults.next()) {
							subSqlInTable = subResults.getString(1);
							break;
						}						
						subFcFlagList.add(subTableName + ":" + flag);
					}				
					if (subSqlInTable==null) {
						subTableList.add(subTableName + ":" + sunCloumnName); 
					}else {
						subTableList.add(subTableName + ":" + sunCloumnName + "|" + subSqlInTable.toUpperCase().replaceFirst("SELECT", " "));
					}										
				}
			}			
		}
		
		List<String> custCodeList = getCustCodeList(conn, fcFlag);
		if (custCodeList.size() > 0) {
			entityTable.setCusCodeList(custCodeList);
		}

		if (subTableList.size() > 0) {			
			entityTable.setIsMainTable(true);
		} else {
			entityTable.setIsMainTable(false);
		}
		entityTable.setSubTableNames(subTableList);
		entityTable.setSubFcFlagNames(subFcFlagList);
		entityTable.setTableId(tableId);
		if (conn != null) {
			conn.close();
		}
		return entityTable;
	}

	protected static String processType(String type) {
		if (getConfig().getConfigDataSource() == ConfigDataSource.ORACLE) {
			return oracleProcessType(type);
		}
		return mysqlProcessType(type);
	}

	/**
	 * add static method
	 * 
	 * @param type
	 * @param config
	 * @return
	 */
	public static String processType(String type, ConfigGenerator config) {
		if (config.getConfigDataSource() == ConfigDataSource.ORACLE) {
			return oracleProcessType(type);
		}
		return mysqlProcessType(type);
	}

	/**
	 * MYSQL字段类型转换
	 *
	 * @param type
	 *            字段类型
	 * @return
	 */
	public static String mysqlProcessType(String type) {
		String t = type.toLowerCase();
		if (t.contains("char") || t.contains("string")) {
			return "String";
		} else if (t.contains("bigint")) {
			return "Long";
		} else if (t.contains("int")) {
			return "Integer";
		} else if (t.contains("date") || t.contains("time") || t.contains("year")) {
			return "Date";
		} else if (t.contains("text")) {
			return "String";
		} else if (t.contains("bit")) {
			return "Boolean";
		} else if (t.contains("decimal")) {
			return "BigDecimal";
		} else if (t.contains("blob")) {
			return "byte[]";
		} else if (t.contains("float")) {
			return "Float";
		} else if (t.contains("double")) {
			return "Double";
		} else if (t.contains("json") || t.contains("enum")) {
			return "String";
		}
		return null;
	}

	/**
	 * ORACLE字段类型转换
	 *
	 * @param type
	 *            字段类型
	 * @return
	 */
	public static String oracleProcessType(String type) {
		String t = type.toUpperCase();
		if (t.contains("CHAR") || t.contains("STRING")) {
			return "String";
		} else if (t.contains("DATE") || t.contains("TIMESTAMP") || t.contains("DATETIME")) {
			return "Date";
		} else if (t.contains("NUMBER")) {
			if (t.matches("NUMBER\\(+\\d{1}+\\)")) {
				return "Integer";
			} else if (t.matches("NUMBER\\(+\\d{2}+\\)")) {
				return "Long";
			}
			return "Double";
			
			
		} else if (t.contains("FLOAT")) {
			return "Float";
		} else if (t.contains("BLOB")) {
			return "Object";
		} else if (t.contains("RAW")) {
			return "byte[]";
		} else if (t.contains("INT")) {
			return "Integer";
		}
		return null;
	}

	public static Boolean isColumnExists(String columnName, EntityTable table) {

		List<EntityField> list = table.getEntityFields();

		for (EntityField field : list) {
			if (field.getColumnName().equalsIgnoreCase(columnName)) {
				return true;
			}
		}
		return false;
	}

	public static EntityTable generateEntities(String beanName, String SubModule, String tableName, Configuration cfg, boolean isMainGenerated, String fcFlag)
			throws IOException, TemplateException, SQLException {

		// Process database columns
		EntityTable table = getTableColumnInfoFromDB(tableName, fcFlag);
		table.setBasePackage("com.rs.devplatform");
		table.setSubModule(SubModule);
		table.setTableComments("示例");
		table.setTableName(tableName);
		table.setBeanName(beanName);
		table.setAuthor("RoadShare");

		table.setImports(Arrays.asList(new String[] { "java.util.Date" }));

		// Process basic columns
		List<EntityField> fieldList = new ArrayList<EntityField>();
		EntityField field = new EntityField();

		List<String> basicColumns = new ArrayList<String>();
		basicColumns.add("id");
		basicColumns.add("whr");
		basicColumns.add("whrid");
		basicColumns.add("whsj,Date");
		basicColumns.add("cjr");
		basicColumns.add("cjrid");
		basicColumns.add("cjsj,Date");
		basicColumns.add("deleted");
		basicColumns.add("version,Integer");

		String columnPrefix = tableName.substring(tableName.indexOf("_") + 1);

		for (String str : basicColumns) {
			field = new EntityField();
			String[] arr = str.split(",");
			String simpleType = arr.length == 1 ? "String" : arr[1];
			String basicColumn = columnPrefix + '_' + arr[0];
			if (isColumnExists(basicColumn, table)) {
				continue;
			}
			if ("id".equals(arr[0])) {
				field.setPk(true);
			}
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
		table.getEntityFields().addAll(fieldList);
		if (!isMainGenerated) {
			table.setIsNeedMapXML(false);
		}

		Template temp = null;
		String path = RSCodeGenerator.class.getClassLoader().getResource("").getPath();
		System.err.println("path=======================" + path);
		File folder = new File(path + (StringUtils.isEmpty(SubModule) ? "" : SubModule));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		try {
			FileOutputStream foEntity = new FileOutputStream(new File(path + beanName + ".java"));
			FileOutputStream foService = new FileOutputStream(new File(path + beanName + "Service.java"));
			FileOutputStream foServiceImpl = new FileOutputStream(new File(path + beanName + "ServiceImpl.java"));
			FileOutputStream foController = null;
			FileOutputStream foPage = null;
			FileOutputStream foMapperJava=null;
			FileOutputStream foMapperXml=null;
			if (isMainGenerated) {
				foController = new FileOutputStream(new File(path + beanName + "Controller.java"));
				foPage = new FileOutputStream(new File(path + (StringUtils.isEmpty(SubModule) ? beanName + ".html"
						: SubModule + "/" + beanName + ".html")));
				foMapperJava = new FileOutputStream(new File(path + beanName + "Mapper.java"));
				foMapperXml = new FileOutputStream(new File(path + beanName + "Mapper.xml"));
			}

			FileOutputStream foEntityVO = new FileOutputStream(new File(path + beanName + "VO.java"));
			

			Writer outEntity = new OutputStreamWriter(foEntity);
			Writer outService = new OutputStreamWriter(foService);
			Writer outServiceImpl = new OutputStreamWriter(foServiceImpl);
			Writer outController = null;
			Writer outEntityPage = null;
			Writer outMapperJava = null;
			Writer outMapperXml = null;
			if (isMainGenerated) {
				outController = new OutputStreamWriter(foController);
				outEntityPage = new OutputStreamWriter(foPage);
				outMapperJava = new OutputStreamWriter(foMapperJava);
				outMapperXml = new OutputStreamWriter(foMapperXml);
				
			}
			Writer outEntityVO = new OutputStreamWriter(foEntityVO);

			temp = cfg.getTemplate("myentity.java.ftl");
			temp.process(table, outEntity);

			System.err.println("============================");
			temp = cfg.getTemplate("myservice.java.ftl");
			temp.process(table, outService);

			System.err.println("------------------------------");
			temp = cfg.getTemplate("myserviceImpl.java.ftl");
			temp.process(table, outServiceImpl);

			temp = cfg.getTemplate("myentityVO.java.ftl");
			temp.process(table, outEntityVO);

			if (isMainGenerated) {
				temp = cfg.getTemplate("mycontroller.java.ftl");
				temp.process(table, outController);

				temp = cfg.getTemplate("mypage.html.ftl");
				temp.process(table, outEntityPage);
				
				temp = cfg.getTemplate("mymapper.java.ftl");
				temp.process(table, outMapperJava);
				
				temp = cfg.getTemplate("mymapper.xml.ftl");
				temp.process(table, outMapperXml);
			}

		}		
		finally {
		}
		;

		return table;

	}

	public static void main(String[] args) throws IOException, TemplateException, SQLException {
		// TODO Auto-generated method stub
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
		cfg.setClassLoaderForTemplateLoading(RSCodeGenerator.class.getClassLoader(), "cg/templates");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		//for testing
		String beanName = "Zpgrade";
		String SubModule = "test";
		String tableName = "zp_grade";
		String fcFlag = "zp_grade";
		
		EntityTable mainTable = generateEntities(beanName, SubModule, tableName, cfg, true, fcFlag);

		if (mainTable.isMainTable) {
			List<String> lists = mainTable.getSubFcFlagNames();
			for (int i = 0; i < lists.size(); i++) {
				String name = lists.get(i);
				String subTableName = name.substring(0, name.indexOf(":"));
				String flag = name.substring(name.indexOf(":") + 1);
				System.out.println("*********sub table name**************" + subTableName);
				String beanPrex = subTableName.substring(0, subTableName.indexOf("_"));
				String beanAppend = subTableName.substring(subTableName.indexOf("_") + 1);
				String subBeanName = beanPrex.substring(0, 1).toUpperCase() + beanPrex.substring(1) + beanAppend;
				generateEntities(subBeanName, SubModule, subTableName, cfg, false, flag);
			}

		}

	}

}
