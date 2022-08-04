package com.gitchain.core.ribbon.support;

import com.gitchain.core.ribbon.rule.DiscoveryEnabledRule;
import com.gitchain.core.ribbon.rule.HttpAwareRule;
import com.gitchain.core.ribbon.rule.MetadataAwareRule;
import com.gitchain.core.ribbon.rule.WeightAwareRule;
import com.gitchain.core.ribbon.utils.BeanUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Chain ribbon rule auto configuration.
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(RibbonClientConfiguration.class)
@EnableConfigurationProperties(ChainRibbonRuleProperties.class)
public class ChainRibbonRuleAutoConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(value = ChainRibbonRuleProperties.PROPERTIES_PREFIX + ".enabled", matchIfMissing = true)
	public static class MetadataAwareRuleConfiguration {

		@Bean
		@ConditionalOnMissingBean
		@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
		public DiscoveryEnabledRule discoveryEnabledRule() {
			return new HttpAwareRule();
		}
	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnProperty(ChainRibbonRuleProperties.PROPERTIES_PREFIX + ".weight")
	public static class WeightAwareRuleConfiguration {

		@Bean
		@ConditionalOnMissingBean
		@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
		public DiscoveryEnabledRule discoveryEnabledRule() {
			return new WeightAwareRule();
		}
	}


	@Bean
	public BeanUtil beanUtil(){
		return new BeanUtil();
	}

}
