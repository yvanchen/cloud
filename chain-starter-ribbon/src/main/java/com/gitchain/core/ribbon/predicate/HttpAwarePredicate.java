package com.gitchain.core.ribbon.predicate;

/**
 * 基于 Metadata 的服务筛选
 *
 * @author git 
 */
public class HttpAwarePredicate extends DiscoveryEnabledPredicate {
	public static final HttpAwarePredicate INSTANCE = new HttpAwarePredicate();

	@Override
	protected boolean apply(MetadataServer server) {
		return true;
	}

}
