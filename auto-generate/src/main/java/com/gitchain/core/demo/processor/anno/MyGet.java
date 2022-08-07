package com.gitchain.core.demo.processor.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author chenyuwen
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface MyGet {
}
