package com.gitchain.core.log.utils;

import com.gitchain.core.tool.utils.StringPool;

import java.util.Properties;

/**
 * Elk配置工具
 *
 * @author git
 */
public class ElkPropsUtil {

	/**
	 * 获取elk服务地址
	 *
	 * @return 服务地址
	 */
	public static String getDestination() {
		Properties props = System.getProperties();
		return props.getProperty("chain.log.elk.destination", StringPool.EMPTY);
	}

}
