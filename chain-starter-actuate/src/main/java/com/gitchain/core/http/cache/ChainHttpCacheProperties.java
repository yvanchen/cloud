package com.gitchain.core.http.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Http Cache 配置
 *
 * @author git
 */
@ConfigurationProperties("chain.http.cache")
public class ChainHttpCacheProperties {
	/**
	 * Http-cache 的 spring cache名，默认：chainHttpCache
	 */
	@Getter
	@Setter
	private String cacheName = "chainHttpCache";
	/**
	 * 默认拦截/**
	 */
	@Getter
	private final List<String> includePatterns = new ArrayList<String>() {{
		add("/**");
	}};
	/**
	 * 默认排除静态文件目录
	 */
	@Getter
	private final List<String> excludePatterns = new ArrayList<>();
}
