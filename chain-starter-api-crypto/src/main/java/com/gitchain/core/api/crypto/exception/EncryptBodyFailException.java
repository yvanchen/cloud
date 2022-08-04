package com.gitchain.core.api.crypto.exception;

/**
 * <p>加密数据失败异常</p>
 *
 * @author git
 */
public class EncryptBodyFailException extends RuntimeException {

	public EncryptBodyFailException() {
		super("Encrypted data failed. (加密数据失败)");
	}

	public EncryptBodyFailException(String message) {
		super(message);
	}
}
