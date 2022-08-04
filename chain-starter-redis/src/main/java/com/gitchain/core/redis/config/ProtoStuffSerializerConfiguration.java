package com.gitchain.core.redis.config;

import com.gitchain.core.redis.serializer.ProtoStuffSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * ProtoStuff 序列化配置
 *
 * @author git 
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(RedisTemplateConfiguration.class)
@ConditionalOnClass(name = "io.protostuff.Schema")
public class ProtoStuffSerializerConfiguration implements ChainRedisSerializerConfigAble {

	@Bean
	@ConditionalOnMissingBean
	@Override
	public RedisSerializer<Object> redisSerializer(ChainRedisProperties properties) {
		if (ChainRedisProperties.SerializerType.ProtoStuff == properties.getSerializerType()) {
			return new ProtoStuffSerializer();
		}
		return defaultRedisSerializer(properties);
	}

}
