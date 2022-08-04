package com.gitchain.core.context;

import lombok.RequiredArgsConstructor;
import com.gitchain.core.context.props.ChainContextProperties;
import com.gitchain.core.tool.utils.StringUtil;
import com.gitchain.core.tool.utils.ThreadLocalUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import java.util.function.Function;

import static com.gitchain.core.tool.constant.ChainConstant.CONTEXT_KEY;

/**
 * chain servlet 上下文，跨线程失效
 *
 * @author git 
 */
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ChainServletContext implements ChainContext {
	private final ChainContextProperties contextProperties;
	private final ChainHttpHeadersGetter httpHeadersGetter;

	@Nullable
	@Override
	public String getRequestId() {
		return get(contextProperties.getHeaders().getRequestId());
	}

	@Nullable
	@Override
	public String getAccountId() {
		return get(contextProperties.getHeaders().getAccountId());
	}

	@Nullable
	@Override
	public String getTenantId() {
		return get(contextProperties.getHeaders().getTenantId());
	}

	@Nullable
	@Override
	public String get(String ctxKey) {
		HttpHeaders headers = ThreadLocalUtil.getIfAbsent(CONTEXT_KEY, httpHeadersGetter::get);
		if (headers == null || headers.isEmpty()) {
			return null;
		}
		return headers.getFirst(ctxKey);
	}

	@Nullable
	@Override
	public <T> T get(String ctxKey, Function<String, T> function) {
		String ctxValue = get(ctxKey);
		if (StringUtil.isBlank(ctxValue)) {
			return null;
		}
		return function.apply(ctxKey);
	}

}
