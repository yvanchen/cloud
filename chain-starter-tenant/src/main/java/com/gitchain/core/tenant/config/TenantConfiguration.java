package com.gitchain.core.tenant.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.AllArgsConstructor;
import com.gitchain.core.mp.config.MybatisPlusConfiguration;
import com.gitchain.core.tenant.*;
import com.gitchain.core.tenant.aspect.ChainTenantAspect;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 多租户配置类
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@AutoConfigureBefore(MybatisPlusConfiguration.class)
@EnableConfigurationProperties(ChainTenantProperties.class)
public class TenantConfiguration {

	/**
	 * 自定义多租户处理器
	 *
	 * @param tenantProperties 多租户配置类
	 * @return TenantHandler
	 */
	@Bean
	@Primary
	public TenantLineHandler chainTenantHandler(ChainTenantProperties tenantProperties) {
		return new ChainTenantHandler(tenantProperties);
	}

	/**
	 * 自定义租户拦截器
	 *
	 * @param tenantHandler    多租户处理器
	 * @param tenantProperties 多租户配置类
	 * @return ChainTenantInterceptor
	 */
	@Bean
	@Primary
	public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantLineHandler tenantHandler, ChainTenantProperties tenantProperties) {
		ChainTenantInterceptor tenantInterceptor = new ChainTenantInterceptor();
		tenantInterceptor.setTenantLineHandler(tenantHandler);
		tenantInterceptor.setTenantProperties(tenantProperties);
		return tenantInterceptor;
	}

	/**
	 * 自定义租户id生成器
	 *
	 * @return TenantId
	 */
	@Bean
	@ConditionalOnMissingBean(TenantId.class)
	public TenantId tenantId() {
		return new ChainTenantId();
	}

	/**
	 * 自定义租户切面
	 */
	@Bean
	public ChainTenantAspect chainTenantAspect() {
		return new ChainTenantAspect();
	}

}
