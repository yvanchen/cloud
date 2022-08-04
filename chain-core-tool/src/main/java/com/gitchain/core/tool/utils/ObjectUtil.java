package com.gitchain.core.tool.utils;

import org.springframework.lang.Nullable;

/**
 * 对象工具类
 *
 * @author git
 */
public class ObjectUtil extends org.springframework.util.ObjectUtils {

	/**
	 * 判断元素不为空
	 * @param obj object
	 * @return boolean
	 */
	public static boolean isNotEmpty(@Nullable Object obj) {
		return !ObjectUtil.isEmpty(obj);
	}

}
