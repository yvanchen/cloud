package com.gitchain.core.sms.config;

import com.yunpian.sdk.YunpianClient;
import lombok.AllArgsConstructor;
import com.gitchain.core.redis.cache.ChainRedis;
import com.gitchain.core.sms.YunpianSmsTemplate;
import com.gitchain.core.sms.props.SmsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 云片短信配置类
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@ConditionalOnClass(YunpianClient.class)
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(value = "sms.name", havingValue = "yunpian")
public class YunpianSmsConfiguration {

	private final ChainRedis chainRedis;

	@Bean
	public YunpianSmsTemplate yunpianSmsTemplate(SmsProperties smsProperties) {
		YunpianClient client = new YunpianClient(smsProperties.getAccessKey()).init();
		return new YunpianSmsTemplate(smsProperties, client, chainRedis);
	}

}
