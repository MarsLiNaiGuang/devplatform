package com.rs.devplatform.common;

import java.util.Arrays;
import java.util.List;

public class CGConstants {
	
	/**
	 * 表单新增/编辑时，修改的字段临时标记
	 * A: 新增表单，或者增加字段，同步之前，该字段的CHGTYPE=A
	 * U: 更新表单，修改字段，同步之前，该字段的CHGTYPE=U
	 * D: 更新表单，删除字段，同步之前，该字段的CHGTYPE=D
	 * 
	 * 当字段的CHGTYPE为A/U/D,表示临时修改的字段&未同步到数据库，在线功能测试不能使用这些字段
	 * 
	 * 当同步完成后，更新掉该记录(set CHGTYPE=null or delete CHGTYPE=D)
	 * 
	 * @author yuxiaobin
	 *
	 */
	public static class ColChgTypes{
		public static final String ADD = "A";
		public static final String UPDATE = "U";
		public static final String DELETE = "D";
	}
	/**
	 * 表单中的表类型
	 * @author songjinke
	 *
	 */
	public static class TableType {
		public static final Integer SINGLE = 1;
		public static final Integer MASTER = 2;
		public static final Integer DETAIL = 3;
	}
	/**
	 * 数据库字段类型
	 * @author yuxiaobin
	 *
	 */
	public static class ColTypes{
		public static final String string = "string";
		public static final String int_ = "int";
		public static final String long_ = "long";
		public static final String double_ = "double";
		public static final String float_ = "float";
		public static final String date = "date";
		public static final String time = "time";
		public static final String datetime = "datetime";
		public static final String decimal = "decimal";
		public static final String text = "text";
		public static final String blob = "blob";
		public static final String clob = "clob";
		public static final String boolean_ = "bool";
		public static final String bytes = "bytes";
	}
	/**
	 * 控件类型
	 * @author yuxiaobin
	 *
	 */
	public static class InputTypes{
		public static final String text = "text";
		public static final String password = "password";
		public static final String radio = "radio";
		public static final String checkbox = "checkbox";
		public static final String date = "date";
		public static final String datetime = "datetime";
		public static final String file = "file";
		public static final String textarea = "textarea";
		public static final String list = "list";
		public static final String popup = "popup";
		public static final String image = "image";
		public static final String umeditor = "umeditor";
	}
	
	public static class SearchTypes{
		public static final String NORMAL = "normal";
		public static final String RANGE = "range";
		public static final String FUZZY = "fuzzy";
	}
	
	public static final String GEN_CHANGELOG_FILE_PATH = CGConstants.class.getClassLoader().getResource("").getPath();
	
	/**
	 * 初始化一个项目的时候，需要加载的表信息
	 * @author yuxiaobin
	 *
	 */
	public static class InitTables{
		public static final List<String> baseTables = Arrays.asList(new String[]{
				"sys_bxx", "sys_zdxx", "sys_cdtype", "sys_cdval", "sys_custbt", "sys_menus",
				"sys_funcgp","sys_gp2tb","sys_indexs", "sys_mb",
				"sys_roles","sys_u2r","sys_users",
				"sys_zqjava","sys_zqjs","sys_zqsql"
				});
	}

	/** 配置的id */
	public static final String CONFIG_ID = "config_id";
	/** 配置的名称 */
	public static final String CONFIG_NAME = "config_name";
	/** 表名 */
	public static final String TABLENAME = "tableName";
	/** 字段列表 */
	public static final String CONFIG_FIELDLIST = "config_fieldList";
	/** 字段字典map */
	public static final String FIELD_DICTMAP = "field_dictMap";
}
