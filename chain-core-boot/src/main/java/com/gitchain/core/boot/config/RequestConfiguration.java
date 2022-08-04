package com.gitchain.core.boot.config;

import lombok.AllArgsConstructor;
import com.gitchain.core.boot.request.ChainRequestFilter;
import com.gitchain.core.boot.request.RequestProperties;
import com.gitchain.core.boot.request.XssProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;

/**
 * 过滤器配置类
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties({RequestProperties.class, XssProperties.class})
public class RequestConfiguration {

	private final RequestProperties requestProperties;
	private final XssProperties xssProperties;

	/**
	 * 全局过滤器
	 */
	@Bean
	public FilterRegistrationBean<ChainRequestFilter> chainFilterRegistration() {
		FilterRegistrationBean<ChainRequestFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new ChainRequestFilter(requestProperties, xssProperties));
		registration.addUrlPatterns("/*");
		registration.setName("chainRequestFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}
}
