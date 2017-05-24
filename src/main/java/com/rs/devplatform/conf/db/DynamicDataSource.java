package com.rs.devplatform.conf.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.rs.devplatform.conf.DBConfig;

public class DynamicDataSource extends AbstractRoutingDataSource {
	
	private final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
	
	private static final String driverClassName = "driverClassName";
	private static final String validationQuery = "validationQuery";
	private static final String validationQuery_Mysql = "select 'x' ";
	private static final String validationQuery_Oracle = "select 'x' from dual";
	
	public static class DBParamNames{
		public static final String dbUrl = "dbUrl";
		public static final String dbUser = "dbUser";
		public static final String dbPwd = "dbPwd";
	}
	
	public static class DBTypes{
		public static final String mysql = "mysql";
		public static final String oracle = "oracle";
	}
	public static class DBDrivers{
		public static final String mysqlDriver = "com.mysql.jdbc.Driver";
		public static final String oracleDriver = "oracle.jdbc.driver.OracleDriver";
		
		public static final String mysqlDriverPrefix = "jdbc:mysql://";
		public static final String oracleDriverPrefix = "jdbc:oracle:thin:@";
		
	}
	public static final String mysqlUrlSuffix = "?useUnicode=true&characterEncoding=UTF8";
	
	
	public static final String dataSourceSQL = "select r.PJ2RES_DB_TYPE as dbType, r.PJ2RES_DB_URL as dbUrl, r.PJ2RES_DB_USER as dbUser, r.PJ2RES_DB_PWD as dbPwd "
			+ "from sys_pj2res r where r.PJ2RES_PJ_ID=?";

	private static final String defaultDataSourceKey = "0";
	@Autowired
	public Environment environment;
	
	private DataSource defaultDataSource;

	private LoadingCache<Object, Object> dataSourceCache = CacheBuilder.newBuilder()
			.expireAfterAccess(30, TimeUnit.MINUTES).maximumSize(20).initialCapacity(5).concurrencyLevel(8)
			.build(new CacheLoader<Object, Object>() {
				@Override
				public Object load(Object pjId) throws RuntimeException {
					if(pjId==defaultDataSourceKey){
						return defaultDataSource;
					}else{
						DataSource ds = parseDataSource((String) pjId);
						if (ds == null) {
							throw new RuntimeException("invalid ds configured for pjId=" + pjId);
						}
						return ds;
					}
				}
			});

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	@Override
	protected Object determineCurrentLookupKey() {
		String key = contextHolder.get();
		if (key == null) {
			key = defaultDataSourceKey;
		}
		if (dataSourceCache.getIfPresent(key) == null) {
			try {
				dataSourceCache.get(key);
			} catch (ExecutionException e) {
				logger.error("determineCurrentLookupKey");
				throw new RuntimeException(e);
			}
			super.setTargetDataSources(dataSourceCache.asMap());
			super.afterPropertiesSet();
		}
		return key;
	}

	public DynamicDataSource(DataSource dataSource){
		super();
		defaultDataSource = dataSource;
		setDefaultTargetDataSource(dataSource);
		dataSourceCache.put(defaultDataSourceKey, dataSource);
		setTargetDataSources(dataSourceCache.asMap());
	}
	
	public DynamicDataSource() {
		super();
		defaultDataSource = getDefaultDataSource();
		setDefaultTargetDataSource(defaultDataSource);
		dataSourceCache.put(defaultDataSourceKey, defaultDataSource);
		setTargetDataSources(dataSourceCache.asMap());
	}

	public static void setDs4Project(String pjId) {
		contextHolder.set(pjId);
	}
	
	public DataSource parseDataSource(String pjId) {
		setDs4Project(null);
		Map<String,Object> map = getDBPropertiesFromConfig();
		Connection conn = null;
		try {
			conn = defaultDataSource.getConnection();
		} catch (SQLException e) {
			logger.error("parseDataSource() >> defaultDataSource.getConnection() error", e);
			throw new RuntimeException(e);
		}
		try(
				PreparedStatement ps = conn.prepareStatement(dataSourceSQL);
				) {
			ps.setString(1, pjId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String dbType = rs.getString("dbType");
				if (DBTypes.mysql.equals(dbType)) {
					map.put(driverClassName, DBDrivers.mysqlDriver);
					map.put(validationQuery, validationQuery_Mysql);
					map.put("url", DBDrivers.mysqlDriverPrefix+rs.getString(DBParamNames.dbUrl));
				} else if (DBTypes.oracle.equals(dbType)) {
					map.put(driverClassName, DBDrivers.oracleDriver);
					map.put(validationQuery, validationQuery_Oracle);
					map.put("url", DBDrivers.oracleDriverPrefix+rs.getString(DBParamNames.dbUrl));
				} else {
					System.err.println("not support db type=" + dbType);
				}
				map.put("username", rs.getString(DBParamNames.dbUser));
				map.put("password", rs.getString(DBParamNames.dbPwd));
			}
		} catch (SQLException e1) {
			logger.error("parseDataSource(pjId="+pjId+"): SQLException", e1);
		}
		Properties dbProperties = new Properties();
		dbProperties.putAll(map);
		return DBConfig.getDataSourceFromProperties(dbProperties);
	}

	public DataSource getDefaultDataSource() {
		Properties properties = new Properties();
		properties.putAll(getDBPropertiesFromConfig());
		return DBConfig.getDataSourceFromProperties(properties);
	}
	
	public Map<String, Object> getDBPropertiesFromConfig(){
		Map<String, Object> map = new HashMap<String, Object>();
		MutablePropertySources properties = ((AbstractEnvironment) environment).getPropertySources();
		Iterator<PropertySource<?>> it = properties.iterator();
		while (it.hasNext()) {
			PropertySource<?> propertySource = it.next();
			DBConfig.getPropertiesFromSource(propertySource, map);
		}
		String driverName = (String)map.get(driverClassName);
        if(driverName.contains("Oracle")){
        	map.put(validationQuery, validationQuery_Oracle);
        }else if(driverName.contains("mysql")){
        	map.put(validationQuery, validationQuery_Mysql);
        }
        return map;
	}
	
}
