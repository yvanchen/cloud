package com.gitchain.core.log.feign;

import lombok.extern.slf4j.Slf4j;
import com.gitchain.core.log.model.LogApi;
import com.gitchain.core.log.model.LogError;
import com.gitchain.core.log.model.LogUsual;
import com.gitchain.core.tool.api.R;
import org.springframework.stereotype.Component;

/**
 * 日志fallback
 *
 * @author git jiang
 */
@Slf4j
@Component
public class LogClientFallback implements ILogClient {

	@Override
	public R<Boolean> saveUsualLog(LogUsual log) {
		return R.fail("usual log send fail");
	}

	@Override
	public R<Boolean> saveApiLog(LogApi log) {
		return R.fail("api log send fail");
	}

	@Override
	public R<Boolean> saveErrorLog(LogError log) {
		return R.fail("error log send fail");
	}
}
