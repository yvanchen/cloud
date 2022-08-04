package com.gitchain.core.log.config;

import com.gitchain.core.launch.props.ChainProperties;
import com.gitchain.core.launch.props.ChainPropertySource;
import com.gitchain.core.launch.server.ServerInfo;
import com.gitchain.core.log.aspect.ApiLogAspect;
import com.gitchain.core.log.aspect.LogTraceAspect;
import com.gitchain.core.log.event.ApiLogListener;
import com.gitchain.core.log.event.ErrorLogListener;
import com.gitchain.core.log.event.UsualLogListener;
import com.gitchain.core.log.feign.ILogClient;
import com.gitchain.core.log.filter.LogTraceFilter;
import com.gitchain.core.log.logger.ChainLogger;
import com.gitchain.core.log.props.ChainRequestLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.DispatcherType;

/**
 * 日志工具自动配置
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@EnableConfigurationProperties(ChainRequestLogProperties.class)
@ChainPropertySource(value = "classpath:/chain-log.yml")
public class ChainLogToolAutoConfiguration {

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public LogTraceAspect logTraceAspect() {
		return new LogTraceAspect();
	}

	@Bean
	public ChainLogger chainLogger() {
		return new ChainLogger();
	}

	@Bean
	public FilterRegistrationBean<LogTraceFilter> logTraceFilterRegistration() {
		FilterRegistrationBean<LogTraceFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new LogTraceFilter());
		registration.addUrlPatterns("/*");
		registration.setName("LogTraceFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}

	@Bean
	@ConditionalOnMissingBean(name = "apiLogListener")
	public ApiLogListener apiLogListener(ILogClient logService, ServerInfo serverInfo, ChainProperties chainProperties) {
		return new ApiLogListener(logService, serverInfo, chainProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "errorEventListener")
	public ErrorLogListener errorEventListener(ILogClient logService, ServerInfo serverInfo, ChainProperties chainProperties) {
		return new ErrorLogListener(logService, serverInfo, chainProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "usualEventListener")
	public UsualLogListener usualEventListener(ILogClient logService, ServerInfo serverInfo, ChainProperties chainProperties) {
		return new UsualLogListener(logService, serverInfo, chainProperties);
	}

}
