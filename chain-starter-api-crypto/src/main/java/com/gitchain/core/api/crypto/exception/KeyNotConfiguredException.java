package com.gitchain.core.api.crypto.exception;


/**
 * <p>未配置KEY运行时异常</p>
 *
 * @author git, 
 */
public class KeyNotConfiguredException extends RuntimeException {

	public KeyNotConfiguredException(String message) {
		super(message);
	}
}
