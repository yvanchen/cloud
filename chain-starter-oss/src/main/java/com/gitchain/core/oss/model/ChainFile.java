package com.gitchain.core.oss.model;

import lombok.Data;

/**
 * ChainFile
 *
 * @author git 
 */
@Data
public class ChainFile {
	/**
	 * 文件地址
	 */
	private String link;
	/**
	 * 域名地址
	 */
	private String domain;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 初始文件名
	 */
	private String originalName;
	/**
	 * 附件表ID
	 */
	private Long attachId;
}
