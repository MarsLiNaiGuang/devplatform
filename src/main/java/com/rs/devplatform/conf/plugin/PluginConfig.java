package com.rs.devplatform.conf.plugin;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={
		"com.rs.framework.*.conf",
//		"com.rs.framework.common.conf",
//		"com.rs.framework.sample.conf", 
//		"com.rs.framework.auth.conf", 
//		"com.rs.framework.param.conf"
		})
public class PluginConfig {
	
}

