package com.gitchain.core.boot.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.gitchain.core.boot.error.ErrorType;
import com.gitchain.core.boot.error.ErrorUtil;
import com.gitchain.core.context.ChainContext;
import com.gitchain.core.context.ChainRunnableWrapper;
import com.gitchain.core.launch.props.ChainProperties;
import com.gitchain.core.log.constant.EventConstant;
import com.gitchain.core.log.event.ErrorLogEvent;
import com.gitchain.core.log.model.LogError;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ErrorHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步处理
 *
 * @author git 
 */
@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
@AllArgsConstructor
public class ChainExecutorConfiguration extends AsyncConfigurerSupport {

	private final ChainContext chainContext;
	private final ChainProperties chainProperties;
	private final ApplicationEventPublisher publisher;

	@Bean
	public TaskExecutorCustomizer taskExecutorCustomizer() {
		return taskExecutor -> {
			taskExecutor.setThreadNamePrefix("async-task-");
			taskExecutor.setTaskDecorator(ChainRunnableWrapper::new);
			taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		};
	}

	@Bean
	public TaskSchedulerCustomizer taskSchedulerCustomizer() {
		return taskExecutor -> {
			taskExecutor.setThreadNamePrefix("async-scheduler");
			taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
			taskExecutor.setErrorHandler(new ChainErrorHandler(chainContext, chainProperties, publisher));
		};
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new ChainAsyncUncaughtExceptionHandler(chainContext, chainProperties, publisher);
	}

	@RequiredArgsConstructor
	private static class ChainAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
		private final ChainContext chainContext;
		private final ChainProperties chainProperties;
		private final ApplicationEventPublisher eventPublisher;

		@Override
		public void handleUncaughtException(@NonNull Throwable error, @NonNull Method method, @NonNull Object... params) {
			log.error("Unexpected exception occurred invoking async method: {}", method, error);
			LogError logError = new LogError();
			// 服务信息、环境、异常类型
			logError.setParams(ErrorType.ASYNC.getType());
			logError.setEnv(chainProperties.getEnv());
			logError.setServiceId(chainProperties.getName());
			logError.setRequestUri(chainContext.getRequestId());
			// 堆栈信息
			ErrorUtil.initErrorInfo(error, logError);
			Map<String, Object> event = new HashMap<>(16);
			event.put(EventConstant.EVENT_LOG, logError);
			eventPublisher.publishEvent(new ErrorLogEvent(event));
		}
	}

	@RequiredArgsConstructor
	private static class ChainErrorHandler implements ErrorHandler {
		private final ChainContext chainContext;
		private final ChainProperties chainProperties;
		private final ApplicationEventPublisher eventPublisher;

		@Override
		public void handleError(@NonNull Throwable error) {
			log.error("Unexpected scheduler exception", error);
			LogError logError = new LogError();
			// 服务信息、环境、异常类型
			logError.setParams(ErrorType.SCHEDULER.getType());
			logError.setServiceId(chainProperties.getName());
			logError.setEnv(chainProperties.getEnv());
			logError.setRequestUri(chainContext.getRequestId());
			// 堆栈信息
			ErrorUtil.initErrorInfo(error, logError);
			Map<String, Object> event = new HashMap<>(16);
			event.put(EventConstant.EVENT_LOG, logError);
			eventPublisher.publishEvent(new ErrorLogEvent(event));
		}
	}

}
