package com.gitchain.core.prometheus.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.gitchain.core.launch.props.ChainPropertySource;
import com.gitchain.core.prometheus.endpoint.AgentEndpoint;
import com.gitchain.core.prometheus.endpoint.ServiceEndpoint;
import com.gitchain.core.prometheus.service.RegistrationService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Prometheus配置类
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@ChainPropertySource(value = "classpath:/chain-prometheus.yml")
public class PrometheusConfiguration {

	@Bean
	public RegistrationService registrationService(DiscoveryClient discoveryClient) {
		return new RegistrationService(discoveryClient);
	}

	@Bean
	public AgentEndpoint agentController(NacosDiscoveryProperties properties) {
		return new AgentEndpoint(properties);
	}

	@Bean
	public ServiceEndpoint serviceController(RegistrationService registrationService) {
		return new ServiceEndpoint(registrationService);
	}

}
