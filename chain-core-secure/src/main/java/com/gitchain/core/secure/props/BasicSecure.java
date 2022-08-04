package com.gitchain.core.secure.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gitchain.core.secure.provider.HttpMethod;

/**
 * 基础授权规则
 *
 * @author git
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicSecure {

	/**
	 * 请求方法
	 */
	private HttpMethod method;
	/**
	 * 请求路径
	 */
	private String pattern;
	/**
	 * 客户端id
	 */
	private String username;
	/**
	 * 客户端密钥
	 */
	private String password;

}
