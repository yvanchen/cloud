package com.gitchain.core.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 简化 测试
 *
 * @author git
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest
public @interface ChainBootTest {
	/**
	 * 服务名：appName
	 * @return appName
	 */
	@AliasFor("appName")
	String value() default "chain-test";
	/**
	 * 服务名：appName
	 * @return appName
	 */
	@AliasFor("value")
	String appName() default "chain-test";
	/**
	 * profile
	 * @return profile
	 */
	String profile() default "dev";
	/**
	 * 启用 ServiceLoader 加载 launcherService
	 * @return 是否启用
	 */
	boolean enableLoader() default false;
}
