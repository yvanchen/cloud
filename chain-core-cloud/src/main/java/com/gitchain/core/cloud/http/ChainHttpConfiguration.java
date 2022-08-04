package com.gitchain.core.cloud.http;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * http 配置
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ChainHttpProperties.class)
public class ChainHttpConfiguration {
}
