package com.gitchain.core.redis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redis 配置
 *
 * @author git
 */
@Getter
@Setter
@ConfigurationProperties("chain.redis")
public class ChainRedisProperties {

	/**
	 * 序列化方式
	 */
	private SerializerType serializerType = SerializerType.ProtoStuff;

	public enum SerializerType {
		/**
		 * 默认:ProtoStuff 序列化
		 */
		ProtoStuff,
		/**
		 * json 序列化
		 */
		JSON,
		/**
		 * jdk 序列化
		 */
		JDK
	}
}
