package com.gitchain.core.oss.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * minio策略配置
 *
 * @author git SCMOX
 */
@Getter
@AllArgsConstructor
public enum PolicyType {

	/**
	 * 只读
	 */
	READ("read", "只读"),

	/**
	 * 只写
	 */
	WRITE("write", "只写"),

	/**
	 * 读写
	 */
	READ_WRITE("read_write", "读写");

	/**
	 * 类型
	 */
	private final String type;
	/**
	 * 描述
	 */
	private final String policy;

}
