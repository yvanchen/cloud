package com.gitchain.core.tool.config;


import com.gitchain.core.tool.support.BinderSupplier;
import com.gitchain.core.tool.utils.SpringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

/**
 * 工具配置类
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
public class ToolConfiguration {

	/**
	 * Spring上下文缓存
	 */
	@Bean
	public SpringUtil springUtil() {
		return new SpringUtil();
	}

	/**
	 * Binder支持类
	 */
	@Bean
	@ConditionalOnMissingBean
	public Supplier<Object> binderSupplier() {
		return new BinderSupplier();
	}

}
