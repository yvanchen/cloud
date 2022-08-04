package com.gitchain.core.http.test;

import com.gitchain.core.http.HttpRequest;

public class HttpRequestProxyTest {

	//@Test(expected = IOException.class)
	public void test1() {
		// 代理都不可用
		HttpRequest.get("https://www.baidu.com")
			.log()
			.retry()
			.proxySelector(new ChainProxySelector())
			.execute()
			.asString();
	}
}
