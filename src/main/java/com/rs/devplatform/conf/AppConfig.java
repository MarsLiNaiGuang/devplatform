package com.rs.devplatform.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.rs.devplatform.service","com.rs.devplatform.aop"})
public class AppConfig {

	
}
