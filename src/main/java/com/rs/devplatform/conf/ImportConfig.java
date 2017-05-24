package com.rs.devplatform.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rs.devplatform.conf.plugin.PluginConfig;

@Configuration
@Import(value={DBConfig.class,AppConfig.class,WebConfig.class,LiquibaseConfig.class, CacheConfig.class, PluginConfig.class})
public class ImportConfig {

}
