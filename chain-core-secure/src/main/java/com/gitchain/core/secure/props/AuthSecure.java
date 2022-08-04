package com.gitchain.core.secure.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gitchain.core.secure.provider.HttpMethod;

/**
 * 自定义授权规则
 *
 * @author git
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthSecure {

	/**
	 * 请求方法
	 */
	private HttpMethod method;
	/**
	 * 请求路径
	 */
	private String pattern;
	/**
	 * 规则表达式
	 */
	private String expression;

}
