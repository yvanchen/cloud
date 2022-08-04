package com.gitchain.core.tenant;

import org.springframework.core.NamedThreadLocal;

/**
 * 租户线程处理
 *
 * @author git
 */
public class ChainTenantHolder {

	private static final ThreadLocal<Boolean> TENANT_KEY_HOLDER = new NamedThreadLocal<Boolean>("chain-tenant") {
		@Override
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	public static void setIgnore(Boolean ignore) {
		TENANT_KEY_HOLDER.set(ignore);
	}

	public static Boolean isIgnore() {
		return TENANT_KEY_HOLDER.get();
	}


	public static void clear() {
		TENANT_KEY_HOLDER.remove();
	}


}
