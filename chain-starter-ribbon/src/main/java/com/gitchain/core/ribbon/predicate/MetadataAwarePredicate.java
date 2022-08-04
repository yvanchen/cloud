package com.gitchain.core.ribbon.predicate;

import org.apache.commons.lang.StringUtils;
import com.gitchain.core.ribbon.support.ChainRibbonRuleProperties;
import com.gitchain.core.ribbon.utils.BeanUtil;

import java.util.Map;

/**
 * 基于 Metadata 的服务筛选
 *
 * @author git 
 */
public class MetadataAwarePredicate extends DiscoveryEnabledPredicate {
	public static final MetadataAwarePredicate INSTANCE = new MetadataAwarePredicate();

	@Override
	protected boolean apply(MetadataServer server) {
		// 获取配置
		ChainRibbonRuleProperties properties = BeanUtil.getBean(ChainRibbonRuleProperties.class);
		// 服务里的配置
		String localTag = properties.getTag();
		if (StringUtils.isBlank(localTag)) {
			return true;
		}
		Map<String, String> metadata = server.getMetadata();
		// 本地有 tag，服务没有，返回 false
		String metaDataTag = metadata.get("tag");
		if (StringUtils.isBlank(metaDataTag)) {
			return false;
		}
		return metaDataTag.equals(localTag);
	}

}
