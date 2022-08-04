package com.gitchain.core.tool.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * bean map key，提高性能
 *
 * @author git 
 */
@EqualsAndHashCode
@AllArgsConstructor
public class ChainBeanMapKey {
	private final Class type;
	private final int require;
}
