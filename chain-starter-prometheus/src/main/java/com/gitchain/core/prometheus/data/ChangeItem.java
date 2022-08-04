package com.gitchain.core.prometheus.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * ChangeItem
 *
 * @author git
 */
@Getter
@RequiredArgsConstructor
public class ChangeItem<T> {
	private final T item;
	private final long changeIndex;
}
