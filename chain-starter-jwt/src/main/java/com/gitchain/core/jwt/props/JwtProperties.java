package com.gitchain.core.jwt.props;

import lombok.Data;
import com.gitchain.core.jwt.constant.JwtConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置
 *
 * @author git 
 */
@Data
@ConfigurationProperties("chain.token")
public class JwtProperties {

	/**
	 * token是否有状态
	 */
	private Boolean state = Boolean.FALSE;

	/**
	 * 是否只可同时在线一人
	 */
	private Boolean single = Boolean.FALSE;

	/**
	 * token签名
	 */
	private String signKey = JwtConstant.DEFAULT_SECRET_KEY;

	/**
	 * 获取签名规则
	 */
	public String getSignKey() {
		if (this.signKey.length() < JwtConstant.SECRET_KEY_LENGTH) {
			return JwtConstant.DEFAULT_SECRET_KEY;
		}
		return this.signKey;
	}

}
