package com.rs.devplatform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Import;

import com.rs.devplatform.conf.ImportConfig;

@SpringBootApplication
@EnableAutoConfiguration
@Import(value={ImportConfig.class})
public class DevPlatformApp implements EmbeddedServletContainerCustomizer{
	
	private static final Logger logger = LoggerFactory.getLogger(DevPlatformApp.class);
	
	public static void main(String[] args) {
		logger.info("**********start*********");
		SpringApplication.run(DevPlatformApp.class, args);
	}

	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(8080);
	}
}
