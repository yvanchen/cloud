package com.gitchain.core.log.utils;

import com.gitchain.core.launch.props.ChainProperties;
import com.gitchain.core.launch.server.ServerInfo;
import com.gitchain.core.log.model.LogAbstract;
import com.gitchain.core.secure.utils.AuthUtil;
import com.gitchain.core.tool.constant.ChainConstant;
import com.gitchain.core.tool.utils.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Log 相关工具
 *
 * @author git
 */
public class LogAbstractUtil {

	/**
	 * 向log中添加补齐request的信息
	 *
	 * @param request     请求
	 * @param logAbstract 日志基础类
	 */
	public static void addRequestInfoToLog(HttpServletRequest request, LogAbstract logAbstract) {
		if (ObjectUtil.isNotEmpty(request)) {
			logAbstract.setTenantId(Func.toStrWithEmpty(AuthUtil.getTenantId(), ChainConstant.ADMIN_TENANT_ID));
			logAbstract.setRemoteIp(WebUtil.getIP(request));
			logAbstract.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
			logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
			logAbstract.setMethod(request.getMethod());
			logAbstract.setParams(WebUtil.getRequestContent(request));
			logAbstract.setCreateBy(AuthUtil.getUserAccount(request));
		}
	}

	/**
	 * 向log中添加补齐其他的信息（eg：chain、server等）
	 *
	 * @param logAbstract     日志基础类
	 * @param chainProperties 配置信息
	 * @param serverInfo      服务信息
	 */
	public static void addOtherInfoToLog(LogAbstract logAbstract, ChainProperties chainProperties, ServerInfo serverInfo) {
		logAbstract.setServiceId(chainProperties.getName());
		logAbstract.setServerHost(serverInfo.getHostName());
		logAbstract.setServerIp(serverInfo.getIpWithPort());
		logAbstract.setEnv(chainProperties.getEnv());
		logAbstract.setCreateTime(DateUtil.now());
		if (logAbstract.getParams() == null) {
			logAbstract.setParams(StringPool.EMPTY);
		}
	}
}
