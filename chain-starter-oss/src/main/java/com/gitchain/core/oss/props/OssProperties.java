package com.gitchain.core.oss.props;

import lombok.Data;
import com.gitchain.core.tool.support.Kv;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Minio参数配置类
 *
 * @author git 
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

	/**
	 * 是否启用
	 */
	private Boolean enabled;

	/**
	 * 对象存储名称
	 */
	private String name;

	/**
	 * 是否开启租户模式
	 */
	private Boolean tenantMode = false;

	/**
	 * 对象存储服务的URL
	 */
	private String endpoint;

	/**
	 * 应用ID TencentCOS需要
	 */
	private String appId;

	/**
	 * 区域简称 TencentCOS需要
	 */
	private String region;

	/**
	 * Access key就像用户ID，可以唯一标识你的账户
	 */
	private String accessKey;

	/**
	 * Secret key是你账户的密码
	 */
	private String secretKey;

	/**
	 * 默认的存储桶名称
	 */
	private String bucketName = "ChainCloud";

	/**
	 * 自定义属性
	 */
	private Kv args;

}
