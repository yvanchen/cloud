package com.gitchain.core.log.props;

import lombok.Getter;
import lombok.Setter;
import com.gitchain.core.launch.log.ChainLogLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 日志配置
 *
 * @author git 
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(ChainLogLevel.REQ_LOG_PROPS_PREFIX)
public class ChainRequestLogProperties {

	/**
	 * 是否开启请求日志
	 */
	private Boolean enabled = true;

	/**
	 * 日志级别配置，默认：BODY
	 */
	private ChainLogLevel level = ChainLogLevel.BODY;
}
