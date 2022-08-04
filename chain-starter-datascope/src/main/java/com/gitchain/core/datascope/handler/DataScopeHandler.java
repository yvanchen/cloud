package com.gitchain.core.datascope.handler;

import com.gitchain.core.datascope.model.DataScopeModel;
import com.gitchain.core.secure.ChainUser;

/**
 * 数据权限规则
 *
 * @author git
 */
public interface DataScopeHandler {

	/**
	 * 获取过滤sql
	 *
	 * @param mapperId    数据查询类
	 * @param dataScope   数据权限类
	 * @param chainUser   当前用户信息
	 * @param originalSql 原始Sql
	 * @return sql
	 */
	String sqlCondition(String mapperId, DataScopeModel dataScope, ChainUser chainUser, String originalSql);

}
