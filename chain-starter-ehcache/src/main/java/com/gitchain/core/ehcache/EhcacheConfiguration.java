package com.gitchain.core.ehcache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Ehcache配置类
 *
 * @author git
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class EhcacheConfiguration {
}
