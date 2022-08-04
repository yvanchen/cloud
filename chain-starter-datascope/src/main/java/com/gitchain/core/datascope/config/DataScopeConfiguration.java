package com.gitchain.core.datascope.config;

import lombok.AllArgsConstructor;
import com.gitchain.core.datascope.interceptor.DataScopeInterceptor;
import com.gitchain.core.datascope.props.DataScopeProperties;
import com.gitchain.core.datascope.handler.ChainDataScopeHandler;
import com.gitchain.core.datascope.handler.ChainScopeModelHandler;
import com.gitchain.core.datascope.handler.DataScopeHandler;
import com.gitchain.core.datascope.handler.ScopeModelHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据权限配置类
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableConfigurationProperties(DataScopeProperties.class)
public class DataScopeConfiguration {

	private final JdbcTemplate jdbcTemplate;

	@Bean
	@ConditionalOnMissingBean(ScopeModelHandler.class)
	public ScopeModelHandler scopeModelHandler() {
		return new ChainScopeModelHandler(jdbcTemplate);
	}

	@Bean
	@ConditionalOnBean(ScopeModelHandler.class)
	@ConditionalOnMissingBean(DataScopeHandler.class)
	public DataScopeHandler dataScopeHandler(ScopeModelHandler scopeModelHandler) {
		return new ChainDataScopeHandler(scopeModelHandler);
	}

	@Bean
	@ConditionalOnBean(DataScopeHandler.class)
	@ConditionalOnMissingBean(DataScopeInterceptor.class)
	public DataScopeInterceptor interceptor(DataScopeHandler dataScopeHandler, DataScopeProperties dataScopeProperties) {
		return new DataScopeInterceptor(dataScopeHandler, dataScopeProperties);
	}

}
