package com.gitchain.core.context.listener;

import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import com.gitchain.core.context.ChainHttpHeadersGetter;
import com.gitchain.core.context.props.ChainContextProperties;
import com.gitchain.core.tool.constant.ChainConstant;
import com.gitchain.core.tool.utils.StringUtil;
import com.gitchain.core.tool.utils.ThreadLocalUtil;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet 请求监听器
 *
 * @author git 
 */
@RequiredArgsConstructor
public class ChainServletRequestListener implements ServletRequestListener {
	private final ChainContextProperties contextProperties;
	private final ChainHttpHeadersGetter httpHeadersGetter;

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		// MDC 获取透传的 变量
		ChainContextProperties.Headers headers = contextProperties.getHeaders();
		String requestId = request.getHeader(headers.getRequestId());
		if (StringUtil.isNotBlank(requestId)) {
			MDC.put(ChainConstant.MDC_REQUEST_ID_KEY, requestId);
		}
		String accountId = request.getHeader(headers.getAccountId());
		if (StringUtil.isNotBlank(accountId)) {
			MDC.put(ChainConstant.MDC_ACCOUNT_ID_KEY, accountId);
		}
		String tenantId = request.getHeader(headers.getTenantId());
		if (StringUtil.isNotBlank(tenantId)) {
			MDC.put(ChainConstant.MDC_TENANT_ID_KEY, tenantId);
		}
		// 处理 context，直接传递 request，因为 spring 中的尚未初始化完成
		HttpHeaders httpHeaders = httpHeadersGetter.get(request);
		ThreadLocalUtil.put(ChainConstant.CONTEXT_KEY, httpHeaders);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		// 会话销毁时，清除上下文
		ThreadLocalUtil.clear();
		// 会话销毁时，清除 mdc
		MDC.remove(ChainConstant.MDC_REQUEST_ID_KEY);
		MDC.remove(ChainConstant.MDC_ACCOUNT_ID_KEY);
		MDC.remove(ChainConstant.MDC_TENANT_ID_KEY);
	}

}
