package com.gitchain.core.api.crypto.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.gitchain.core.api.crypto.enums.CryptoType;

/**
 * <p>加密注解信息</p>
 *
 * @author git, 
 */
@Getter
@RequiredArgsConstructor
public class CryptoInfoBean {

	/**
	 * 加密类型
	 */
	private final CryptoType type;
	/**
	 * 私钥
	 */
	private final String secretKey;

}
