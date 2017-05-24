package com.rs.devplatform.generator;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.generator.ConfigBaseEntity;
import com.baomidou.mybatisplus.generator.ConfigDataSource;
import com.baomidou.mybatisplus.generator.ConfigGenerator;
import com.baomidou.mybatisplus.generator.ConfigIdType;

public class MybatisGenerator {
	
	public static void main( String[] args ) {
		/**
		 * column2PropertyIncludeTableSuffix=false:
		 * 	数据库字段 | 实体属性名
		 * 	sys_users.users_name | name
		 * 
		 * column2PropertyIncludeTableSuffix=true:
		 * 数据库字段 | 实体属性名
		 * 	sys_users.users_name | usersName
		 * @注意：如果想要自定义sql查询不添加as xxx,请使用true来生成实体
		 */
		boolean column2PropertyIncludeTableSuffix = false;
		RSConfigGenerator cg = getConfigGenerator(column2PropertyIncludeTableSuffix);
		
		/* oracle 数据库相关配置 */
		setupMysql(cg);

		/*
		 * 指定生成表名（默认，所有表）
		 */
		String[] tableNames = {"tbl_samplechild"};
//        String[] tableNames = {"rs_module","rs_workflow","wf_def","wf_task",
//        		"wf_task_conn","wf_group","wf_instance","wf_inst_hist"};
        cg.setTableNames(tableNames);
		
		AutoGenerator.run(cg);
	}
	

	public static ConfigGenerator setupMysql(ConfigGenerator cg){
		cg.setConfigDataSource(ConfigDataSource.MYSQL);
		cg.setDbDriverName("com.mysql.jdbc.Driver");
		cg.setDbUser("root");
		cg.setDbPassword("root");
		cg.setDbUrl("jdbc:mysql://192.168.10.64:3306/dev-platform?useUnicode=true&characterEncoding=UTF8");
		return cg;
	}
	
	public static ConfigGenerator setupOracle(ConfigGenerator cg){
		cg.setConfigDataSource(ConfigDataSource.ORACLE);
		cg.setDbDriverName("oracle.jdbc.driver.OracleDriver");
		cg.setDbUser("rsmgr");
		cg.setDbPassword("rsmgr");
		cg.setDbUrl("jdbc:oracle:thin:@localhost:1521:mgrdb");
		return cg;
	}
	
	private static RSConfigGenerator getConfigGenerator(boolean column2PropertyIncludeTableSufix){
		RSConfigGenerator cg = new RSConfigGenerator();
        cg.setEntityPackage("com.rs.devplatform.persistent");//entity 实体包路径
        cg.setServicePackage("com.rs.devplatform.service");//service 层路径
        cg.setServiceImplPackage("com.rs.devplatform.service.impl");//serviceimpl层路径（可以不写）
        ConfigBaseEntity baseEntity = new ConfigBaseEntity();
        baseEntity.setClassName("CUBaseTO");
        baseEntity.setPackageName("com.rs.devplatform.persistent.base");
        baseEntity.setColumns(new String[]{});
        cg.setConfigBaseEntity(baseEntity);
//        cg.setMapperPackage("com.xb.persistent.mapper");//mapper 映射文件路径
//        cg.setXmlPackage("com.xb.persistent.mapper");//xml层路径（可以不写）
		/* 此处可以配置 SuperServiceImpl 子类路径，默认如下 */
        //cg.setSuperServiceImpl("com.baomidou.framework.service.impl.SuperServiceImpl");
		/* 此处设置 String 类型数据库ID，默认Long类型 */
        //cg.setConfigIdType(ConfigIdType.STRING);
        cg.setSaveDir("D:/rsdevplatform/");// 生成文件保存位置
        cg.setColumn2PropertyIncludeTableSuffix(column2PropertyIncludeTableSufix);
		/*
         * 设置数据库字段是否为驼峰命名，驼峰 true 下划线分割 false
		 */
        cg.setDbColumnUnderline(false);
//        cg.setColumnHump(false);
        /*
         * 表是否包括前缀
		 * <p>
		 * 例如 mp_user 生成实体类 false 为 MpUser , true 为 User
		 * </p>
		 */
        cg.setDbPrefix(false);
        /*
         * 默认值为true , 是否覆盖当前路径下已有文件
         */
        cg.setFileOverride(true);
        
        /* 此处设置 String 类型数据库ID，默认Long类型 */
		cg.setConfigIdType(ConfigIdType.STRING);
		/*
		 * 表主键 ID 生成类型, 自增该设置无效。
		 * <p>
		 * IdType.AUTO 			数据库ID自增
		 * IdType.INPUT			用户输入ID
		 * IdType.ID_WORKER		全局唯一ID，内容为空自动填充（默认配置）
		 * IdType.UUID			全局唯一ID，内容为空自动填充
		 * </p>
		 */
		cg.setIdType(IdType.UUID);
		
//        String[] tableNames = {"sys_users"};
//        String[] tableNames = {"rs_module","rs_workflow","wf_def","wf_task",
//        		"wf_task_conn","wf_group","wf_instance","wf_inst_hist"};
//        cg.setTableNames(tableNames);
        return cg;
	}
}
