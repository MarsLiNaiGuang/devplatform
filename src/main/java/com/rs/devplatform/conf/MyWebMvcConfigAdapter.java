package com.rs.devplatform.conf;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.rs.devplatform.conf.converter.FormData2JsonHttpConverter;

public class MyWebMvcConfigAdapter extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(
				"classpath:/static/");
		super.addResourceHandlers(registry);
	}

	/*@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/wfapi/**");
	}*/

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(new FormData2JsonHttpConverter());
	}

	
}
