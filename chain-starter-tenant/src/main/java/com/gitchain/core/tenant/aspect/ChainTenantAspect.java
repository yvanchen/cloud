package com.gitchain.core.tenant.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import com.gitchain.core.tenant.ChainTenantHolder;
import com.gitchain.core.tenant.annotation.TenantIgnore;

/**
 * 自定义租户切面
 *
 * @author git
 */
@Slf4j
@Aspect
public class ChainTenantAspect {

	@Around("@annotation(tenantIgnore)")
	public Object around(ProceedingJoinPoint point, TenantIgnore tenantIgnore) throws Throwable {
		try {
			//开启忽略
			ChainTenantHolder.setIgnore(Boolean.TRUE);
			//执行方法
			return point.proceed();
		} finally {
			//关闭忽略
			ChainTenantHolder.clear();
		}
	}

}
