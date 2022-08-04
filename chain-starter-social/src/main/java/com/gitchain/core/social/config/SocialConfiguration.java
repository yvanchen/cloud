package com.gitchain.core.social.config;

import com.xkcoding.http.HttpUtil;
import com.xkcoding.http.support.Http;
import com.xkcoding.http.support.httpclient.HttpClientImpl;
import me.zhyd.oauth.cache.AuthStateCache;
import com.gitchain.core.launch.props.ChainPropertySource;
import com.gitchain.core.redis.config.RedisTemplateConfiguration;
import com.gitchain.core.social.cache.AuthStateRedisCache;
import com.gitchain.core.social.props.SocialProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * SocialConfiguration
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SocialProperties.class)
@AutoConfigureAfter(RedisTemplateConfiguration.class)
@ChainPropertySource(value = "classpath:/chain-social.yml")
public class SocialConfiguration {

	@Bean
	@ConditionalOnMissingBean(Http.class)
	public Http simpleHttp() {
		HttpClientImpl httpClient = new HttpClientImpl();
		HttpUtil.setHttp(httpClient);
		return httpClient;
	}

	@Bean
	@ConditionalOnMissingBean(AuthStateCache.class)
	public AuthStateCache authStateCache(RedisTemplate<String, Object> redisTemplate) {
		return new AuthStateRedisCache(redisTemplate, redisTemplate.opsForValue());
	}

}
