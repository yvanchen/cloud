package com.gitchain.core.cache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Cache配置类
 *
 * @author git 
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CacheConfiguration {
}
