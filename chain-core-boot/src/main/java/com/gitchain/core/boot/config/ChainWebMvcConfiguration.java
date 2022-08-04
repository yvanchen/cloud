package com.gitchain.core.boot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.gitchain.core.boot.props.ChainFileProperties;
import com.gitchain.core.boot.props.ChainUploadProperties;
import com.gitchain.core.boot.resolver.TokenArgumentResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WEB配置
 *
 * @author git
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
@EnableConfigurationProperties({
	ChainUploadProperties.class, ChainFileProperties.class
})
public class ChainWebMvcConfiguration implements WebMvcConfigurer {

	private final ChainUploadProperties chainUploadProperties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String path = chainUploadProperties.getSavePath();
		registry.addResourceHandler("/upload/**")
			.addResourceLocations("file:" + path + "/upload/");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new TokenArgumentResolver());
	}

}
