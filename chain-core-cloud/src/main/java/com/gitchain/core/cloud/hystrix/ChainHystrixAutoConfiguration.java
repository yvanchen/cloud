package com.gitchain.core.cloud.hystrix;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import feign.hystrix.HystrixFeign;
import com.gitchain.core.cloud.feign.ChainFeignRequestInterceptor;
import com.gitchain.core.cloud.version.ChainSpringMvcContract;
import com.gitchain.core.tool.convert.EnumToStringConverter;
import com.gitchain.core.tool.convert.StringToEnumConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.ChainFeignClientsRegistrar;
import org.springframework.cloud.openfeign.ChainHystrixTargeter;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.Targeter;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

/**
 * Hystrix 配置
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({Hystrix.class, Feign.class})
@Import(ChainFeignClientsRegistrar.class)
@AutoConfigureAfter(EnableFeignClients.class)
@ConditionalOnProperty(value = "feign.hystrix.enabled")
public class ChainHystrixAutoConfiguration {
	@Nullable
	@Autowired(required = false)
	private HystrixConcurrencyStrategy existingConcurrencyStrategy;

	@PostConstruct
	public void init() {
		// Keeps references of existing Hystrix plugins.
		HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
		HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
		HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
		HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();

		HystrixPlugins.reset();

		// Registers existing plugins excepts the Concurrent Strategy plugin.
		HystrixConcurrencyStrategy strategy = new ChainHystrixConcurrencyStrategy(existingConcurrencyStrategy);
		HystrixPlugins.getInstance().registerConcurrencyStrategy(strategy);
		HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
		HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
		HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
		HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
	}

	@Bean
	@ConditionalOnMissingBean(Targeter.class)
	public ChainHystrixTargeter chainFeignTargeter() {
		return new ChainHystrixTargeter();
	}

	@Bean
	@Primary
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Feign.Builder feignHystrixBuilder(RequestInterceptor requestInterceptor, Contract feignContract) {
		return HystrixFeign.builder()
			.contract(feignContract)
			.decode404()
			.requestInterceptor(requestInterceptor);
	}

	@Bean
	@ConditionalOnMissingBean
	public RequestInterceptor requestInterceptor() {
		return new ChainFeignRequestInterceptor();
	}

	/**
	 * chain enum 《-》 String 转换配置
	 *
	 * @param objectProvider ObjectProvider
	 * @return SpringMvcContract
	 */
	@Bean
	public Contract feignContract(@Qualifier("mvcConversionService") ObjectProvider<ConversionService> objectProvider) {
		ConversionService conversionService = objectProvider.getIfAvailable(DefaultConversionService::new);
		ConverterRegistry converterRegistry = ((ConverterRegistry) conversionService);
		converterRegistry.addConverter(new EnumToStringConverter());
		converterRegistry.addConverter(new StringToEnumConverter());
		return new ChainSpringMvcContract(new ArrayList<>(), conversionService);
	}

}
