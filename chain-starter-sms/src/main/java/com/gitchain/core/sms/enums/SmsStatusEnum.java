package com.gitchain.core.sms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Sms类型枚举
 *
 * @author git 
 */
@Getter
@AllArgsConstructor
public enum SmsStatusEnum {

	/**
	 * 关闭
	 */
	DISABLE(1),
	/**
	 * 启用
	 */
	ENABLE(2),
	;

	/**
	 * 类型编号
	 */
	final int num;

}
