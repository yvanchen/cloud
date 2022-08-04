package com.gitchain.core.oss.config;

import lombok.AllArgsConstructor;
import com.gitchain.core.oss.props.OssProperties;
import com.gitchain.core.oss.rule.ChainOssRule;
import com.gitchain.core.oss.rule.OssRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Oss配置类
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties(OssProperties.class)
public class OssConfiguration {

	private final OssProperties ossProperties;

	@Bean
	@ConditionalOnMissingBean(OssRule.class)
	public OssRule ossRule() {
		return new ChainOssRule(ossProperties.getTenantMode());
	}

}
