package com.gitchain.core.sms.config;

import lombok.AllArgsConstructor;
import com.gitchain.core.sms.props.SmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Sms配置类
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties(SmsProperties.class)
public class SmsConfiguration {
}
