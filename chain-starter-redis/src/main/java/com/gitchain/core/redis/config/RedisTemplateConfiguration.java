package com.gitchain.core.redis.config;

import com.gitchain.core.jwt.config.JwtRedisConfiguration;
import com.gitchain.core.redis.cache.ChainRedis;
import com.gitchain.core.redis.serializer.RedisKeySerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisTemplate  配置
 *
 * @author git
 */
@EnableCaching
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({JwtRedisConfiguration.class, RedisAutoConfiguration.class})
@EnableConfigurationProperties(ChainRedisProperties.class)
public class RedisTemplateConfiguration implements ChainRedisSerializerConfigAble {

	/**
	 * value 值 序列化
	 *
	 * @return RedisSerializer
	 */
	@Bean
	@ConditionalOnMissingBean(RedisSerializer.class)
	@Override
	public RedisSerializer<Object> redisSerializer(ChainRedisProperties properties) {
		return defaultRedisSerializer(properties);
	}

	@Bean(name = "redisTemplate")
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<Object> redisSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// key 序列化
		RedisKeySerializer keySerializer = new RedisKeySerializer();
		redisTemplate.setKeySerializer(keySerializer);
		redisTemplate.setHashKeySerializer(keySerializer);
		// value 序列化
		redisTemplate.setValueSerializer(redisSerializer);
		redisTemplate.setHashValueSerializer(redisSerializer);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	@ConditionalOnMissingBean(ValueOperations.class)
	public ValueOperations valueOperations(RedisTemplate redisTemplate) {
		return redisTemplate.opsForValue();
	}

	@Bean
	public ChainRedis chainRedis(RedisTemplate<String, Object> redisTemplate) {
		return new ChainRedis(redisTemplate);
	}

}
