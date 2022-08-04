package com.gitchain.core.http.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理设置
 *
 * @author git 
 */
@Slf4j
public class ChainProxySelector extends ProxySelector {

	@Override
	public List<Proxy> select(URI uri) {
		// 注意代理都不可用
		List<Proxy> proxyList = new ArrayList<>();
		proxyList.add(getProxy("127.0.0.1", 8080));
		proxyList.add(getProxy("127.0.0.1", 8081));
		proxyList.add(getProxy("127.0.0.1", 8082));
		proxyList.add(getProxy("127.0.0.1", 3128));
		return proxyList;
	}

	@Override
	public void connectFailed(URI uri, SocketAddress address, IOException ioe) {
		// 注意：经过测试，此处不会触发
		log.error("ConnectFailed uri:{}, address:{}, ioe:{}", uri, address, ioe);
	}

	/**
	 * 构造 Proxy
	 *
	 * @param host host
	 * @param port 端口
	 * @return Proxy 对象
	 */
	public static Proxy getProxy(String host, int port) {
		return new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(host, port));
	}
}
