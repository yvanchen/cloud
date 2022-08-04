package com.gitchain.core.tool.spel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * ExpressionRootObject
 *
 * @author git
 */
@Getter
@AllArgsConstructor
public class ChainExpressionRootObject {
	private final Method method;

	private final Object[] args;

	private final Object target;

	private final Class<?> targetClass;

	private final Method targetMethod;
}
