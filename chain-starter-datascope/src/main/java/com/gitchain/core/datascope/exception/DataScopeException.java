package com.gitchain.core.datascope.exception;

/**
 * 数据权限异常
 *
 * @author git
 */
public class DataScopeException extends RuntimeException {

	public DataScopeException() {
	}

	public DataScopeException(String message) {
		super(message);
	}

	public DataScopeException(Throwable cause) {
		super(cause);
	}
}
