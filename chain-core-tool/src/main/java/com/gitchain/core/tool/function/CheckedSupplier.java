package com.gitchain.core.tool.function;

import org.springframework.lang.Nullable;

/**
 * 受检的 Supplier
 *
 * @author git 
 */
@FunctionalInterface
public interface CheckedSupplier<T> {

	/**
	 * Run the Supplier
	 *
	 * @return T
	 * @throws Throwable CheckedException
	 */
	@Nullable
	T get() throws Throwable;

}
