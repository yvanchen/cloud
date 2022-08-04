package com.gitchain.core.log.publisher;

import com.gitchain.core.log.constant.EventConstant;
import com.gitchain.core.log.event.ErrorLogEvent;
import com.gitchain.core.log.model.LogError;
import com.gitchain.core.log.utils.LogAbstractUtil;
import com.gitchain.core.tool.utils.Exceptions;
import com.gitchain.core.tool.utils.Func;
import com.gitchain.core.tool.utils.SpringUtil;
import com.gitchain.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常信息事件发送
 *
 * @author git
 */
public class ErrorLogPublisher {

	public static void publishEvent(Throwable error, String requestUri) {
		HttpServletRequest request = WebUtil.getRequest();
		LogError logError = new LogError();
		logError.setRequestUri(requestUri);
		if (Func.isNotEmpty(error)) {
			logError.setStackTrace(Exceptions.getStackTraceAsString(error));
			logError.setExceptionName(error.getClass().getName());
			logError.setMessage(error.getMessage());
			StackTraceElement[] elements = error.getStackTrace();
			if (Func.isNotEmpty(elements)) {
				StackTraceElement element = elements[0];
				logError.setMethodName(element.getMethodName());
				logError.setMethodClass(element.getClassName());
				logError.setFileName(element.getFileName());
				logError.setLineNumber(element.getLineNumber());
			}
		}
		LogAbstractUtil.addRequestInfoToLog(request, logError);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logError);
		SpringUtil.publishEvent(new ErrorLogEvent(event));
	}

}
