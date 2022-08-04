package com.gitchain.core.transaction.config;

import com.gitchain.core.launch.props.ChainPropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Seata配置类
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@ChainPropertySource(value = "classpath:/chain-transaction.yml")
public class TransactionConfiguration {

}
