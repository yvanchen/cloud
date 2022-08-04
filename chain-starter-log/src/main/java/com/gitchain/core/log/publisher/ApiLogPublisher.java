package com.gitchain.core.log.publisher;

import com.gitchain.core.log.annotation.ApiLog;
import com.gitchain.core.log.constant.EventConstant;
import com.gitchain.core.log.event.ApiLogEvent;
import com.gitchain.core.log.model.LogApi;
import com.gitchain.core.log.utils.LogAbstractUtil;
import com.gitchain.core.tool.constant.ChainConstant;
import com.gitchain.core.tool.utils.SpringUtil;
import com.gitchain.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * API日志信息事件发送
 *
 * @author git
 */
public class ApiLogPublisher {

	public static void publishEvent(String methodName, String methodClass, ApiLog apiLog, long time) {
		HttpServletRequest request = WebUtil.getRequest();
		LogApi logApi = new LogApi();
		logApi.setType(ChainConstant.LOG_NORMAL_TYPE);
		logApi.setTitle(apiLog.value());
		logApi.setTime(String.valueOf(time));
		logApi.setMethodClass(methodClass);
		logApi.setMethodName(methodName);
		LogAbstractUtil.addRequestInfoToLog(request, logApi);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logApi);
		SpringUtil.publishEvent(new ApiLogEvent(event));
	}

}
