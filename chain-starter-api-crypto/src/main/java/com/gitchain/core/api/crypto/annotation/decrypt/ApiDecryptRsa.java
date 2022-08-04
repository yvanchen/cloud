package com.gitchain.core.api.crypto.annotation.decrypt;

import com.gitchain.core.api.crypto.enums.CryptoType;

import java.lang.annotation.*;

/**
 * rsa 解密
 *
 * @author git
 * @see ApiDecrypt
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiDecrypt(CryptoType.RSA)
public @interface ApiDecryptRsa {
}
