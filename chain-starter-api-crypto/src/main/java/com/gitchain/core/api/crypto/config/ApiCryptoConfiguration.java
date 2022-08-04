package com.gitchain.core.api.crypto.config;

import lombok.RequiredArgsConstructor;
import com.gitchain.core.api.crypto.core.ApiDecryptParamResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * api 签名自动配置
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(ApiCryptoProperties.class)
@ConditionalOnProperty(value = ApiCryptoProperties.PREFIX + ".enabled", havingValue = "true", matchIfMissing = true)
public class ApiCryptoConfiguration implements WebMvcConfigurer {
	private final ApiCryptoProperties apiCryptoProperties;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new ApiDecryptParamResolver(apiCryptoProperties));
	}

}
