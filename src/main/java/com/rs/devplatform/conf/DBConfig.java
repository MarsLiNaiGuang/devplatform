package com.rs.devplatform.conf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.rs.devplatform.conf.db.DynamicDataSource;

/**
 * platform对应的数据库配置
 * 
 * @author yuxiaobin
 *
 */
@Configuration
@EnableTransactionManagement
@Import(value={MybatisConfig.class})
public class DBConfig {

	private static final String DRUID_PREFIX = "druid.";

	@Autowired
	public Environment environment;

	private DataSource getDataSource(){
		Map<String, Object> map = new HashMap<String, Object>();
		MutablePropertySources properties = ((AbstractEnvironment) environment).getPropertySources();
		Iterator<PropertySource<?>> it = properties.iterator();
		while (it.hasNext()) {
			PropertySource<?> propertySource = it.next();
			getPropertiesFromSource(propertySource, map);
		}
		String driverName = (String)map.get("driverClassName");
        if(driverName.contains("Oracle")){
        	map.put("validationQuery", "select 'x' from dual");
        }else if(driverName.contains("mysql")){
        	map.put("validationQuery", "select 'x' ");
        }
        Properties dbProperties = new Properties();
        dbProperties.putAll(map);
        return getDataSourceFromProperties(dbProperties);
	}
	
	public static DataSource getDataSourceFromProperties(Properties properties){
		DruidDataSource dataSource = null;
		try {
			dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
			if (null != dataSource) {
				dataSource.setConnectionErrorRetryAttempts(3);
				// dataSource.setFilters("wall,stat");
				// dataSource.setTimeBetweenLogStatsMillis(5000);
				dataSource.init();
			}
		} catch (Exception e) {
			throw new RuntimeException("load datasource error, dbProperties is :" + properties, e);
		}
		return dataSource;
	}
	
	public static void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {
		if (propertySource instanceof MapPropertySource) {
			for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
				if (key.startsWith(DRUID_PREFIX)) {
					map.put(key.replaceFirst(DRUID_PREFIX, ""), propertySource.getProperty(key));
				}
			}
		}
		if (propertySource instanceof CompositePropertySource) {
			for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
				getPropertiesFromSource(s, map);
			}
		}
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dynamicDataSource());
	}

	@Bean
	public DynamicDataSource dynamicDataSource(){
		return new DynamicDataSource(getDataSource());
	}
}
