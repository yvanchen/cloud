package com.gitchain.core.secure.constant;

import com.gitchain.core.tool.utils.StringUtil;

/**
 * 权限校验常量
 *
 * @author git
 */
public interface PermissionConstant {

	/**
	 * 获取角色所有的权限编号
	 *
	 * @param size 数量
	 * @return string
	 */
	static String permissionAllStatement(int size) {
		return "select scope_path as path from base_scope_api where id in (select scope_id from base_role_scope where scope_category = 2 and role_id in (" + buildHolder(size) + "))";
	}

	/**
	 * 获取角色指定的权限编号
	 *
	 * @param size 数量
	 * @return string
	 */
	static String permissionStatement(int size) {
		return "select resource_code as code from base_scope_api where resource_code = ? and id in (select scope_id from base_role_scope where scope_category = 2 and role_id in (" + buildHolder(size) + "))";
	}

	/**
	 * 获取Sql占位符
	 *
	 * @param size 数量
	 * @return String
	 */
	static String buildHolder(int size) {
		StringBuilder builder = StringUtil.builder();
		for (int i = 0; i < size; i++) {
			builder.append("?,");
		}
		return StringUtil.removeSuffix(builder.toString(), ",");
	}

}
