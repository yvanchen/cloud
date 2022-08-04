package org.springframework.cloud.openfeign;

import feign.Feign;
import feign.Target;
import org.springframework.cloud.openfeign.FeignClientFactoryBean;
import org.springframework.cloud.openfeign.FeignContext;

/**
 * @author git
 */
public interface Targeter {
	/**
	 * target
	 *
	 * @param factory
	 * @param feign
	 * @param context
	 * @param target
	 * @param <T>
	 * @return T
	 */
	<T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignContext context,
                 Target.HardCodedTarget<T> target);
}
