package com.gitchain.core.boot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.gitchain.core.launch.props.ChainPropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * chain自动配置类
 *
 * @author git
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ChainPropertySource(value = "classpath:/chain-boot.yml")
public class ChainBootAutoConfiguration {

}
