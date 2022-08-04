package com.gitchain.core.tenant.dynamic;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import org.aopalliance.intercept.MethodInvocation;
import com.gitchain.core.secure.utils.AuthUtil;

/**
 * 租户动态数据源解析器
 *
 * @author git 
 */
public class DsTenantIdProcessor extends DsProcessor {

	public static final String TENANT_ID_KEY = "#token.tenantId";

	@Override
	public boolean matches(String key) {
		return key.equals(TENANT_ID_KEY);
	}

	@Override
	public String doDetermineDatasource(MethodInvocation invocation, String key) {
		return AuthUtil.getTenantId();
	}

}
