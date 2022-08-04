package com.gitchain.core.tenant.annotation;

import java.lang.annotation.*;

/**
 * 排除租户数据源自动切换.
 *
 * @author git
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NonDS {
}
