package com.gitchain.core.tenant.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationAdvisor;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.processor.DsHeaderProcessor;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.processor.DsSessionProcessor;
import com.baomidou.dynamic.datasource.processor.DsSpelExpressionProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceCreatorAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import com.gitchain.core.tenant.dynamic.*;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static com.gitchain.core.tenant.constant.TenantBaseConstant.TENANT_DYNAMIC_DATASOURCE_PROP;
import static com.gitchain.core.tenant.constant.TenantBaseConstant.TENANT_DYNAMIC_GLOBAL_PROP;

/**
 * 多租户数据源配置类
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@AutoConfigureBefore({DruidDataSourceAutoConfigure.class, DynamicDataSourceAutoConfiguration.class})
@EnableConfigurationProperties({DataSourceProperties.class, DynamicDataSourceProperties.class})
@Import(value = {DynamicDataSourceCreatorAutoConfiguration.class})
@ConditionalOnProperty(value = TENANT_DYNAMIC_DATASOURCE_PROP, havingValue = "true")
public class TenantDataSourceConfiguration {

	@Bean
	@Primary
	public DynamicDataSourceProvider dynamicDataSourceProvider(DataSourceProperties dataSourceProperties, DynamicDataSourceProperties dynamicDataSourceProperties) {
		String driverClassName = dataSourceProperties.getDriverClassName();
		String url = dataSourceProperties.getUrl();
		String username = dataSourceProperties.getUsername();
		String password = dataSourceProperties.getPassword();
		DataSourceProperty master = dynamicDataSourceProperties.getDatasource().get(dynamicDataSourceProperties.getPrimary());
		if (master != null) {
			driverClassName = master.getDriverClassName();
			url = master.getUrl();
			username = master.getUsername();
			password = master.getPassword();
		}
		return new TenantDataSourceJdbcProvider(dynamicDataSourceProperties, driverClassName, url, username, password);
	}

	@Bean
	@Primary
	public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider, DynamicDataSourceProperties dynamicDataSourceProperties) {
		DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
		dataSource.setPrimary(dynamicDataSourceProperties.getPrimary());
		dataSource.setStrict(dynamicDataSourceProperties.getStrict());
		dataSource.setStrategy(dynamicDataSourceProperties.getStrategy());
		dataSource.setProvider(dynamicDataSourceProvider);
		dataSource.setP6spy(dynamicDataSourceProperties.getP6spy());
		dataSource.setSeata(dynamicDataSourceProperties.getSeata());
		return dataSource;
	}

	@Bean
	@ConditionalOnMissingBean
	public TenantDataSourceAnnotationInterceptor tenantDataSourceAnnotationInterceptor(DsProcessor dsProcessor, DynamicDataSourceProperties dynamicDataSourceProperties) {
		return new TenantDataSourceAnnotationInterceptor(dynamicDataSourceProperties.isAllowedPublicOnly(), dsProcessor);
	}

	@Bean
	@ConditionalOnMissingBean
	@Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
	public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor(TenantDataSourceAnnotationInterceptor tenantDataSourceAnnotationInterceptor, DynamicDataSourceProperties dynamicDataSourceProperties) {
		DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor(tenantDataSourceAnnotationInterceptor);
		advisor.setOrder(dynamicDataSourceProperties.getOrder());
		return advisor;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = TENANT_DYNAMIC_GLOBAL_PROP, havingValue = "true")
	public TenantDataSourceGlobalInterceptor tenantDataSourceGlobalInterceptor() {
		return new TenantDataSourceGlobalInterceptor();
	}

	@Bean
	@ConditionalOnMissingBean
	@Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
	@ConditionalOnProperty(value = TENANT_DYNAMIC_GLOBAL_PROP, havingValue = "true")
	public TenantDataSourceGlobalAdvisor tenantDataSourceGlobalAdvisor(TenantDataSourceGlobalInterceptor tenantDataSourceGlobalInterceptor, DynamicDataSourceProperties dynamicDataSourceProperties) {
		TenantDataSourceGlobalAdvisor advisor = new TenantDataSourceGlobalAdvisor(tenantDataSourceGlobalInterceptor);
		advisor.setOrder(dynamicDataSourceProperties.getOrder() + 1);
		return advisor;
	}

	@Bean
	@ConditionalOnMissingBean
	public DsProcessor dsProcessor() {
		DsHeaderProcessor headerProcessor = new DsHeaderProcessor();
		DsSessionProcessor sessionProcessor = new DsSessionProcessor();
		DsTenantIdProcessor tenantIdProcessor = new DsTenantIdProcessor();
		DsSpelExpressionProcessor spelExpressionProcessor = new DsSpelExpressionProcessor();
		headerProcessor.setNextProcessor(sessionProcessor);
		sessionProcessor.setNextProcessor(tenantIdProcessor);
		tenantIdProcessor.setNextProcessor(spelExpressionProcessor);
		return headerProcessor;
	}

	@Order
	@Configuration(proxyBeanMethods = false)
	@AllArgsConstructor
	@ConditionalOnProperty(value = TENANT_DYNAMIC_DATASOURCE_PROP, havingValue = "true")
	public static class TenantDataSourceAnnotationConfiguration implements SmartInitializingSingleton {

		private final TenantDataSourceAnnotationInterceptor tenantDataSourceAnnotationInterceptor;

		private final DataSource dataSource;
		private final DruidDataSourceCreator dataSourceCreator;
		private final JdbcTemplate jdbcTemplate;

		@Override
		public void afterSingletonsInstantiated() {
			TenantDataSourceHolder tenantDataSourceHolder = new TenantDataSourceHolder(dataSource, dataSourceCreator, jdbcTemplate);
			tenantDataSourceAnnotationInterceptor.setHolder(tenantDataSourceHolder);
		}
	}

	@Order
	@Configuration(proxyBeanMethods = false)
	@AllArgsConstructor
	@ConditionalOnProperty(value = TENANT_DYNAMIC_GLOBAL_PROP, havingValue = "true")
	public static class TenantDataSourceGlobalConfiguration implements SmartInitializingSingleton {

		private final TenantDataSourceGlobalInterceptor tenantDataSourceGlobalInterceptor;

		private final DataSource dataSource;
		private final DruidDataSourceCreator dataSourceCreator;
		private final JdbcTemplate jdbcTemplate;

		@Override
		public void afterSingletonsInstantiated() {
			TenantDataSourceHolder tenantDataSourceHolder = new TenantDataSourceHolder(dataSource, dataSourceCreator, jdbcTemplate);
			tenantDataSourceGlobalInterceptor.setHolder(tenantDataSourceHolder);
		}
	}

}
