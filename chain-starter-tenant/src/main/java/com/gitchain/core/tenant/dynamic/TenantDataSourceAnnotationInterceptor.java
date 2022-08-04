package com.gitchain.core.tenant.dynamic;

import com.baomidou.dynamic.datasource.aop.DynamicDataSourceAnnotationInterceptor;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import lombok.Setter;
import org.aopalliance.intercept.MethodInvocation;
import com.gitchain.core.secure.utils.AuthUtil;
import com.gitchain.core.tenant.exception.TenantDataSourceException;

/**
 * 租户数据源切换拦截器
 *
 * @author git
 */
public class TenantDataSourceAnnotationInterceptor extends DynamicDataSourceAnnotationInterceptor {

	@Setter
	private TenantDataSourceHolder holder;

	public TenantDataSourceAnnotationInterceptor(Boolean allowedPublicOnly, DsProcessor dsProcessor) {
		super(allowedPublicOnly, dsProcessor);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		try {
			holder.handleDataSource(AuthUtil.getTenantId());
			return super.invoke(invocation);
		} catch (Exception exception) {
			throw new TenantDataSourceException(exception.getMessage());
		}
	}

}
