package com.gitchain.core.tenant;

import com.gitchain.core.tool.utils.RandomType;
import com.gitchain.core.tool.utils.StringUtil;

/**
 * chain租户id生成器
 *
 * @author git
 */
public class ChainTenantId implements TenantId {
	@Override
	public String generate() {
		return StringUtil.random(6, RandomType.INT);
	}
}
