package com.gitchain.core.context.config;

import com.gitchain.core.context.ChainHttpHeadersGetter;
import com.gitchain.core.context.listener.ChainServletRequestListener;
import com.gitchain.core.context.props.ChainContextProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Servlet 监听器自动配置
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ChainServletListenerConfiguration {

	@Bean
	public ServletListenerRegistrationBean<?> registerCustomListener(ChainContextProperties properties,
																	 ChainHttpHeadersGetter httpHeadersGetter) {
		return new ServletListenerRegistrationBean<>(new ChainServletRequestListener(properties, httpHeadersGetter));
	}

}
