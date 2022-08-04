package com.gitchain.core.context.config;

import com.gitchain.core.context.ChainContext;
import com.gitchain.core.context.ChainHttpHeadersGetter;
import com.gitchain.core.context.ChainServletContext;
import com.gitchain.core.context.ServletHttpHeadersGetter;
import com.gitchain.core.context.props.ChainContextProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * chain 服务上下文配置
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(ChainContextProperties.class)
public class ChainContextAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ChainHttpHeadersGetter chainHttpHeadersGetter(ChainContextProperties contextProperties) {
		return new ServletHttpHeadersGetter(contextProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public ChainContext chainContext(ChainContextProperties contextProperties, ChainHttpHeadersGetter httpHeadersGetter) {
		return new ChainServletContext(contextProperties, httpHeadersGetter);
	}

}
