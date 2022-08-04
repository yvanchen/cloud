package com.gitchain.core.tool.api;

import java.io.Serializable;

/**
 * 业务代码接口
 *
 * @author git 
 */
public interface IResultCode extends Serializable {

	/**
	 * 获取消息
	 *
	 * @return
	 */
	String getMessage();

	/**
	 * 获取状态码
	 *
	 * @return
	 */
	int getCode();

}
