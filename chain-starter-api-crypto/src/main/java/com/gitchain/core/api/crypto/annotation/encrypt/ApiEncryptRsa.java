package com.gitchain.core.api.crypto.annotation.encrypt;

import com.gitchain.core.api.crypto.enums.CryptoType;

import java.lang.annotation.*;

/**
 * rsa 加密
 *
 * @author git,
 * @see ApiEncrypt
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiEncrypt(CryptoType.RSA)
public @interface ApiEncryptRsa {
}
