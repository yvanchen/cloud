package com.gitchain.core.db.config;

import com.gitchain.core.launch.props.ChainPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * 数据源配置类
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@ChainPropertySource(value = "classpath:/chain-db.yml")
public class DbConfiguration {

}
