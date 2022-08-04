package com.gitchain.core.tool.config;

import com.gitchain.core.tool.convert.EnumToStringConverter;
import com.gitchain.core.tool.convert.StringToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * chain enum 《-》 String 转换配置
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
public class ChainConverterConfiguration implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new EnumToStringConverter());
		registry.addConverter(new StringToEnumConverter());
	}

}
