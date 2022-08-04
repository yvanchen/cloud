package com.gitchain.core.swagger;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.lang.annotation.*;

/**
 * Swagger配置开关
 *
 * @author git 
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableSwagger2WebMvc
public @interface EnableSwagger {
}
