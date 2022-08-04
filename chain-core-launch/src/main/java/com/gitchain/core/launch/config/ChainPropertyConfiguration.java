package com.gitchain.core.launch.config;

import com.gitchain.core.launch.props.ChainPropertySourcePostProcessor;
import com.gitchain.core.launch.props.ChainProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * chain property config
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(ChainProperties.class)
public class ChainPropertyConfiguration {

	@Bean
	public ChainPropertySourcePostProcessor chainPropertySourcePostProcessor() {
		return new ChainPropertySourcePostProcessor();
	}

}
