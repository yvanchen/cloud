package com.gitchain.core.tool.function;

/**
 * 受检的 runnable
 *
 * @author git 
 */
@FunctionalInterface
public interface CheckedRunnable {

	/**
	 * Run this runnable.
	 *
	 * @throws Throwable CheckedException
	 */
	void run() throws Throwable;

}
