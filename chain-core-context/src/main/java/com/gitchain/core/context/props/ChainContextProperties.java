package com.gitchain.core.context.props;

import lombok.Getter;
import lombok.Setter;
import com.gitchain.core.launch.constant.TokenConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Headers 配置
 *
 * @author git 
 */
@Getter
@Setter
@ConfigurationProperties(ChainContextProperties.PREFIX)
public class ChainContextProperties {
	/**
	 * 配置前缀
	 */
	public static final String PREFIX = "chain.context";
	/**
	 * 上下文传递的 headers 信息
	 */
	private Headers headers = new Headers();

	@Getter
	@Setter
	public static class Headers {
		/**
		 * 请求id，默认：chain-RequestId
		 */
		private String requestId = "chain-RequestId";
		/**
		 * 用于 聚合层 向调用层传递用户信息 的请求头，默认：chain-AccountId
		 */
		private String accountId = "chain-AccountId";
		/**
		 * 用于 聚合层 向调用层传递租户id 的请求头，默认：chain-TenantId
		 */
		private String tenantId = "chain-TenantId";
		/**
		 * 自定义 RestTemplate 和 Feign 透传到下层的 Headers 名称列表
		 */
		private List<String> allowed = Arrays.asList("X-Real-IP", "x-forwarded-for", "authorization", "Authorization", TokenConstant.HEADER.toLowerCase(), TokenConstant.HEADER);
	}

	/**
	 * 获取跨服务的请求头
	 *
	 * @return 请求头列表
	 */
	public List<String> getCrossHeaders() {
		List<String> headerList = new ArrayList<>();
		headerList.add(headers.getRequestId());
		headerList.add(headers.getAccountId());
		headerList.add(headers.getTenantId());
		headerList.addAll(headers.getAllowed());
		return headerList;
	}

}
