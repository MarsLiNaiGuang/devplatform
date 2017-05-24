package com.rs.devplatform.conf;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.github.yuxiaobin.mybatis.gm.GeneralSqlSessionFactoryBean;
import com.github.yuxiaobin.mybatis.gm.conf.GeneralMapperBootstrapConfiguration;
import com.github.yuxiaobin.mybatis.gm.intcpt.GeneralPaginationInterceptor;
import com.github.yuxiaobin.mybatis.gm.plus.GeneralMybatisXMLLanguageDriver;
import com.rs.framework.common.RsGeneralMapper;

@Configuration
@MapperScan("com.rs.devplatform.persistent.mapper")
@Import({GeneralMapperBootstrapConfiguration.class})
@EnableConfigurationProperties(MybatisProperties.class)
//@AutoConfigureAfter(PluginConfig.class)
public class MybatisConfig{

	private final MybatisProperties properties;

	  private final Interceptor[] interceptors;

	  private final ResourceLoader resourceLoader;

	  private final DatabaseIdProvider databaseIdProvider;
	
	public MybatisConfig(MybatisProperties properties,
								            ObjectProvider<Interceptor[]> interceptorsProvider,
								            ResourceLoader resourceLoader,
								            ObjectProvider<DatabaseIdProvider> databaseIdProvider){
		this.properties = properties;
	    this.interceptors = interceptorsProvider.getIfAvailable();
	    this.resourceLoader = resourceLoader;
	    this.databaseIdProvider = databaseIdProvider.getIfAvailable();
	}
	
	@Bean
	public RsGeneralMapper generalMapper(GeneralSqlSessionFactoryBean factoryBean) throws Exception{
		RsGeneralMapper generalMapper = new RsGeneralMapper();
		generalMapper.setSqlSessionFactory(factoryBean.getObject());
		return generalMapper;
	}
	
	/**
	 * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定
	 * <BR>
	 * 配置文件key以mybatis.开头<BR>
	 * 
	 * 配置文件和mybatis-boot的配置文件同步
	 * 
	 * @return
	 */
	@Bean
	public GeneralSqlSessionFactoryBean sqlSessionFactory (DataSource dataSource){
		GeneralSqlSessionFactoryBean sqlSessionFactory = new GeneralSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setVfs(SpringBootVFS.class);
		if (!ObjectUtils.isEmpty(this.interceptors)) {
			sqlSessionFactory.setPlugins(this.interceptors);
		}
		else{
			sqlSessionFactory.setPlugins(new Interceptor[]{new GeneralPaginationInterceptor(null)});
		}
		if (this.databaseIdProvider != null) {
			sqlSessionFactory.setDatabaseIdProvider(this.databaseIdProvider);
		}
		if(!StringUtils.isEmpty(properties.getTypeAliasesPackage())){
			sqlSessionFactory.setTypeAliasesPackage(properties.getTypeAliasesPackage());//多个不同的包，可以用 , ; 分隔
		}else{
			sqlSessionFactory.setTypeAliasesPackage("com.rs.devplatform.persistent");
		}
		 if (StringUtils.hasText(this.properties.getConfigLocation())) {
			 sqlSessionFactory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
		    }
		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setDefaultScriptingLanguage(GeneralMybatisXMLLanguageDriver.class);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		org.apache.ibatis.session.Configuration config = properties.getConfiguration();
		if(config!=null){//20161115: 注入mybatis默认下划线转驼峰, 使得字段为test_id可以自动映射到testId(实体属性)
			configuration.setMapUnderscoreToCamelCase(config.isMapUnderscoreToCamelCase());
		}
		sqlSessionFactory.setConfiguration(configuration);
		return sqlSessionFactory;
	}

	/*@Bean
	public MapperScannerConfigurer getMapperScannerConfigurer() {
		MapperScannerConfigurer scanConf = new MapperScannerConfigurer();
		scanConf.setBasePackage("com.xb.persistent.mapper");//scan the *Mapper.java
		return scanConf;
	}*/
}
