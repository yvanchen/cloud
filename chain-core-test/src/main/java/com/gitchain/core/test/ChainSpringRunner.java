package com.gitchain.core.test;


import org.junit.runners.model.InitializationError;
import com.gitchain.core.launch.ChainApplication;
import com.gitchain.core.launch.constant.AppConstant;
import com.gitchain.core.launch.constant.NacosConstant;
import com.gitchain.core.launch.constant.SentinelConstant;
import com.gitchain.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 设置启动参数
 *
 * @author git
 */
public class ChainSpringRunner extends SpringJUnit4ClassRunner {

	public ChainSpringRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
		setUpTestClass(clazz);
	}

	private void setUpTestClass(Class<?> clazz) {
		ChainBootTest chainBootTest = AnnotationUtils.getAnnotation(clazz, ChainBootTest.class);
		if (chainBootTest == null) {
			throw new ChainBootTestException(String.format("%s must be @ChainBootTest .", clazz));
		}
		String appName = chainBootTest.appName();
		String profile = chainBootTest.profile();
		boolean isLocalDev = ChainApplication.isLocalDev();
		Properties props = System.getProperties();
		props.setProperty("chain.env", profile);
		props.setProperty("chain.name", appName);
		props.setProperty("chain.is-local", String.valueOf(isLocalDev));
		props.setProperty("chain.dev-mode", profile.equals(AppConstant.PROD_CODE) ? "false" : "true");
		props.setProperty("chain.service.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("spring.application.name", appName);
		props.setProperty("spring.profiles.active", profile);
		props.setProperty("info.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("info.desc", appName);
		props.setProperty("spring.cloud.nacos.discovery.server-addr", NacosConstant.NACOS_ADDR);
		props.setProperty("spring.cloud.nacos.config.server-addr", NacosConstant.NACOS_ADDR);
		props.setProperty("spring.cloud.nacos.config.prefix", NacosConstant.NACOS_CONFIG_PREFIX);
		props.setProperty("spring.cloud.nacos.config.file-extension", NacosConstant.NACOS_CONFIG_FORMAT);
		props.setProperty("spring.cloud.sentinel.transport.dashboard", SentinelConstant.SENTINEL_ADDR);
		props.setProperty("spring.main.allow-bean-definition-overriding", "true");
		// 加载自定义组件
		if (chainBootTest.enableLoader()) {
			SpringApplicationBuilder builder = new SpringApplicationBuilder(clazz);
			List<LauncherService> launcherList = new ArrayList<>();
			ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
			launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).collect(Collectors.toList())
				.forEach(launcherService -> launcherService.launcher(builder, appName, profile, isLocalDev));
		}
		System.err.println(String.format("---[junit.test]:[%s]---启动中，读取到的环境变量:[%s]", appName, profile));
	}

}
