package com.gitchain.core.redis.lock;

/**
 * 锁类型
 *
 * @author git lcm
 */
public enum LockType {
	/**
	 * 重入锁
	 */
	REENTRANT,
	/**
	 * 公平锁
	 */
	FAIR
}
