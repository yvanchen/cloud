package com.gitchain.core.tenant.annotation;

import java.lang.annotation.*;

/**
 * 排除租户逻辑.
 *
 * @author git
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TenantIgnore {
}
