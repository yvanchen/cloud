package com.gitchain.core.context;

import org.slf4j.MDC;
import com.gitchain.core.tool.utils.ThreadLocalUtil;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 多线程中传递 context 和 mdc
 *
 * @author git 
 */
public class ChainCallableWrapper<V> implements Callable<V> {
	private final Callable<V> delegate;
	private final Map<String, Object> tlMap;
	/**
	 * logback 下有可能为 null
	 */
	@Nullable
	private final Map<String, String> mdcMap;

	public ChainCallableWrapper(Callable<V> callable) {
		this.delegate = callable;
		this.tlMap = ThreadLocalUtil.getAll();
		this.mdcMap = MDC.getCopyOfContextMap();
	}

	@Override
	public V call() throws Exception {
		if (!tlMap.isEmpty()) {
			ThreadLocalUtil.put(tlMap);
		}
		if (mdcMap != null && !mdcMap.isEmpty()) {
			MDC.setContextMap(mdcMap);
		}
		try {
			return delegate.call();
		} finally {
			tlMap.clear();
			if (mdcMap != null) {
				mdcMap.clear();
			}
			ThreadLocalUtil.clear();
			MDC.clear();
		}
	}
}
