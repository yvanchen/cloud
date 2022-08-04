package com.gitchain.core.api.crypto.annotation.decrypt;

import com.gitchain.core.api.crypto.enums.CryptoType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * des 解密
 *
 * @author git
 * @see ApiDecrypt
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiDecrypt(CryptoType.DES)
public @interface ApiDecryptDes {

	/**
	 * Alias for {@link ApiDecrypt#secretKey()}.
	 *
	 * @return {String}
	 */
	@AliasFor(annotation = ApiDecrypt.class)
	String secretKey() default "";

}
