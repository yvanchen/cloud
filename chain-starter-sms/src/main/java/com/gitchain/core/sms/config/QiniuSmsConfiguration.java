package com.gitchain.core.sms.config;

import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import com.gitchain.core.redis.cache.ChainRedis;
import com.gitchain.core.sms.QiniuSmsTemplate;
import com.gitchain.core.sms.props.SmsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云短信配置类
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@ConditionalOnClass(SmsManager.class)
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(value = "sms.name", havingValue = "qiniu")
public class QiniuSmsConfiguration {

	private final ChainRedis chainRedis;

	@Bean
	public QiniuSmsTemplate qiniuSmsTemplate(SmsProperties smsProperties) {
		Auth auth = Auth.create(smsProperties.getAccessKey(), smsProperties.getSecretKey());
		SmsManager smsManager = new SmsManager(auth);
		return new QiniuSmsTemplate(smsProperties, smsManager, chainRedis);
	}

}
