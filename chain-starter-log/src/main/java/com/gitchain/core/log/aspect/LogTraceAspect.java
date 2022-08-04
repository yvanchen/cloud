package com.gitchain.core.log.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import com.gitchain.core.log.utils.LogTraceUtil;

/**
 * 为异步方法添加traceId
 *
 * @author git 
 */
@Aspect
public class LogTraceAspect {

	@Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
	public void logPointCut() {
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		try {
			LogTraceUtil.insert();
			return point.proceed();
		} finally {
			LogTraceUtil.remove();
		}
	}
}
