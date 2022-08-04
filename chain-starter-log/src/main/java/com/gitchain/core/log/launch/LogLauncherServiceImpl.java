package com.gitchain.core.log.launch;

import com.gitchain.core.auto.service.AutoService;
import com.gitchain.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

import java.util.Properties;

/**
 * 日志启动配置类
 *
 * @author git
 */
@AutoService(LauncherService.class)
public class LogLauncherServiceImpl implements LauncherService {
	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile, boolean isLocalDev) {
		Properties props = System.getProperties();
		props.setProperty("logging.config", "classpath:log/logback-" + profile + ".xml");
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
