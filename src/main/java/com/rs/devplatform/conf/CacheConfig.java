package com.rs.devplatform.conf;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager("menuCache","codeCache");//可以多个缓存集合，方便后期对不同缓存管理：比如要刷新某一个缓存
		cacheManager.setCacheBuilder(
			CacheBuilder.newBuilder()
			.expireAfterAccess(30, TimeUnit.MINUTES)//缓存过期时间：最后一次访问时间+30分钟
			.maximumSize(50)//设置最大缓存记录条数
			.initialCapacity(5)//初始化缓存记录容量
			.concurrencyLevel(8)
		);
		return cacheManager;
	}

}
