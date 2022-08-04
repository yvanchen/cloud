package com.gitchain.core.context;

import lombok.RequiredArgsConstructor;
import com.gitchain.core.context.props.ChainContextProperties;
import com.gitchain.core.tool.utils.StringUtil;
import com.gitchain.core.tool.utils.WebUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * HttpHeaders 获取器
 *
 * @author git
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ServletHttpHeadersGetter implements ChainHttpHeadersGetter {
	private final ChainContextProperties properties;

	@Nullable
	@Override
	public HttpHeaders get() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		return get(request);
	}

	@Nullable
	@Override
	public HttpHeaders get(HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		List<String> crossHeaders = properties.getCrossHeaders();
		// 传递请求头
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			List<String> allowed = properties.getHeaders().getAllowed();
			while (headerNames.hasMoreElements()) {
				String key = headerNames.nextElement();
				// 只支持配置的 header
				if (crossHeaders.contains(key) || allowed.contains(key)) {
					String values = request.getHeader(key);
					// header value 不为空的 传递
					if (StringUtil.isNotBlank(values)) {
						headers.add(key, values);
					}
				}
			}
		}
		return headers;
	}

}
