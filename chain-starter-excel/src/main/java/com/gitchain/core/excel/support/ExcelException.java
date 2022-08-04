package com.gitchain.core.excel.support;

/**
 * Excel异常处理类
 *
 * @author git
 */
public class ExcelException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcelException(String message) {
		super(message);
	}
}
