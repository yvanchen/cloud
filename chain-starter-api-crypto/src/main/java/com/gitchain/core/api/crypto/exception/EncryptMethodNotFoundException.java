package com.gitchain.core.api.crypto.exception;

/**
 * <p>加密方式未找到或未定义异常</p>
 *
 * @author git
 */
public class EncryptMethodNotFoundException extends RuntimeException {

	public EncryptMethodNotFoundException() {
		super("Encryption method is not defined. (加密方式未定义)");
	}

}
