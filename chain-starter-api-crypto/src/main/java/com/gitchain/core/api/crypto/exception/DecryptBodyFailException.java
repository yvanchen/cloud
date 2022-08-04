package com.gitchain.core.api.crypto.exception;

/**
 * <p>解密数据失败异常</p>
 *
 * @author git
 */
public class DecryptBodyFailException extends RuntimeException {

	public DecryptBodyFailException(String message) {
		super(message);
	}
}
