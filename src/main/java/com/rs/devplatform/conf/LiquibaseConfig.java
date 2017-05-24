package com.rs.devplatform.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rs.devplatform.conf.db.DynamicDataSource;
import com.rs.framework.common.Constants;
import com.rs.framework.common.exception.SystemException;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.FileSystemResourceAccessor;

@Configuration
public class LiquibaseConfig {
	
	@Bean
	public MyLiquibase liquibase(DataSource ds) {
		MyLiquibase liquibase = new MyLiquibase();
	    liquibase.setDataSource(ds);
	    
	    return liquibase;
	}

	public static class MyLiquibase extends SpringLiquibase{
		
		private final Logger logger = LoggerFactory.getLogger(MyLiquibase.class);
		private static Connection basicConnection = null;//始终维护一个基础数据库的连接
		private Liquibase liquibase;
		
		/**
		 * Executed automatically when the bean is initialized.
		 */
		@Override
		public void afterPropertiesSet() throws LiquibaseException {
			try {
				basicConnection = getDataSource().getConnection();
			} catch (SQLException e) {
				throw new LiquibaseException("cannot get connection");
			}
		};
		
		public MyLiquibase setup(String changeLogFilePath) throws LiquibaseException{
			FileSystemResourceAccessor resourceAccessor = new FileSystemResourceAccessor();
			liquibase = new Liquibase(changeLogFilePath, resourceAccessor, createDatabase(basicConnection, resourceAccessor));
			return this;
		}
		
		public MyLiquibase update() throws LiquibaseException{
			if(liquibase==null){
				FileSystemResourceAccessor resourceAccessor = new FileSystemResourceAccessor();
				liquibase = new Liquibase(changeLog, resourceAccessor, createDatabase(basicConnection, resourceAccessor));
			}
			liquibase.update(new Contexts(getContexts()), new LabelExpression(getLabels()));
			return this;
		}
		
		public MyLiquibase update(String changeLogFilepath) throws LiquibaseException{
			this.setChangeLog(changeLogFilepath);
			if(liquibase==null){
				FileSystemResourceAccessor resourceAccessor = new FileSystemResourceAccessor();
				liquibase = new Liquibase(changeLog, resourceAccessor, createDatabase(basicConnection, resourceAccessor));
			}
			liquibase.update(new Contexts(getContexts()), new LabelExpression(getLabels()));
			return this;
		}
		
		public MyLiquibase update4Project(String pjId, String changeLogFilePath) throws LiquibaseException{
			try(
					Connection pjConn = getConnection4Pj(pjId);
					){
					JdbcConnection dbConn = new JdbcConnection(pjConn);
					liquibase = new Liquibase(changeLogFilePath, new FileSystemResourceAccessor(), dbConn);
					liquibase.update(new Contexts(getContexts()), new LabelExpression(getLabels()));
			} catch (SQLException e) {
				logger.error("update4Project(pjId="+pjId+", changeLogFilePath="+changeLogFilePath+"): SQLException", e);
			}
			return this;
		}
		
		public Connection getConnection4Pj(String pjId) throws LiquibaseException{
			//get real connection by pjId and basicConnection
			String dbUrl = null;
			String username = null;
			String password = null;
			String dbDriver = null;
			boolean isMysql = false;
			if(Constants.DEFAULT_PJID.equals(pjId)){
				throw new LiquibaseException("getConnection4Pj(): pjId should not be basic project");
			}
			try(
					PreparedStatement ps = basicConnection.prepareStatement(DynamicDataSource.dataSourceSQL);
					) {
				ps.setString(1, pjId);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					String dbType = rs.getString("dbType");
					if (DynamicDataSource.DBTypes.mysql.equals(dbType)) {
						dbDriver = DynamicDataSource.DBDrivers.mysqlDriver;
						dbUrl = DynamicDataSource.DBDrivers.mysqlDriverPrefix+rs.getString(DynamicDataSource.DBParamNames.dbUrl);
						isMysql = true;
					} else if (DynamicDataSource.DBTypes.oracle.equals(dbType)) {
						dbDriver = DynamicDataSource.DBDrivers.oracleDriver;
						dbUrl = DynamicDataSource.DBDrivers.oracleDriverPrefix+rs.getString(DynamicDataSource.DBParamNames.dbUrl);
					} else {
						logger.error("getConnection4Pj(pjId="+pjId+"): not support db type=" + dbType);
					}
					username = rs.getString(DynamicDataSource.DBParamNames.dbUser);
					password = rs.getString(DynamicDataSource.DBParamNames.dbPwd);
				}
			} catch (SQLException e1) {
			}
			if(dbUrl==null || username==null||password==null||dbDriver==null){
				throw new LiquibaseException("cannot get correct DB information for pjId="+pjId);
			}
			try {
				Class.forName(dbDriver);
			} catch (ClassNotFoundException e1) {
				logger.error("getConnection4Pj(pjId="+pjId+"): cannot find DB driver="+dbDriver, e1);
				throw new LiquibaseException("cannot find DB driver for "+dbDriver);
			}
			try {
				//dbURL should add charEncoding, otherwise will luanma@20161205
				if(isMysql && !dbUrl.contains("characterEncoding")){
					dbUrl += DynamicDataSource.mysqlUrlSuffix;
				}
				return DriverManager.getConnection(dbUrl, username, password);
			} catch (SQLException e) {
				String errorMsg = "getConnection4Pj(pjId="+pjId+"): error when get connection for dbUrl="+dbUrl;
				logger.error(errorMsg, e);
				throw new LiquibaseException(errorMsg);
			}
		}

		public Connection getConnection() {
			try {
				if(basicConnection.isClosed() || !basicConnection.isValid(1)){
					basicConnection = getDataSource().getConnection();
				}
			} catch (SQLException e) {
				logger.warn("getConnection(): failed to get new connection. try to do again.", e);
				try {
					basicConnection = getDataSource().getConnection();
				} catch (SQLException e1) {
					logger.error("getConnection(): FATAL ERROR >>>>>>>>failed to re-connect for liquibase", e1);
					throw new SystemException("Failed to re-connect for liquibase.");
				}
			}
			return basicConnection;
		}

//		public void setConnection(Connection connection) {
//			MyLiquibase.basicConnection = connection;
//		}

		public Liquibase getLiquibase() {
			return liquibase;
		}

		public void setLiquibase(Liquibase liquibase) {
			this.liquibase = liquibase;
		}

	}
}
