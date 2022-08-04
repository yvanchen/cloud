package com.gitchain.core.sms.config;

import com.github.qcloudsms.SmsMultiSender;
import lombok.AllArgsConstructor;
import com.gitchain.core.redis.cache.ChainRedis;
import com.gitchain.core.sms.TencentSmsTemplate;
import com.gitchain.core.sms.props.SmsProperties;
import com.gitchain.core.tool.utils.Func;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云短信配置类
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@ConditionalOnClass(SmsMultiSender.class)
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(value = "sms.name", havingValue = "tencent")
public class TencentSmsConfiguration {

	private final ChainRedis chainRedis;

	@Bean
	public TencentSmsTemplate tencentSmsTemplate(SmsProperties smsProperties) {
		SmsMultiSender smsSender = new SmsMultiSender(Func.toInt(smsProperties.getAccessKey()), smsProperties.getSecretKey());
		return new TencentSmsTemplate(smsProperties, smsSender, chainRedis);
	}

}
