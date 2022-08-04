package com.gitchain.core.log.event;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.gitchain.core.launch.props.ChainProperties;
import com.gitchain.core.launch.server.ServerInfo;
import com.gitchain.core.log.constant.EventConstant;
import com.gitchain.core.log.feign.ILogClient;
import com.gitchain.core.log.model.LogUsual;
import com.gitchain.core.log.utils.LogAbstractUtil;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * 异步监听日志事件
 *
 * @author git 
 */
@Slf4j
@AllArgsConstructor
public class UsualLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final ChainProperties chainProperties;

	@Async
	@Order
	@EventListener(UsualLogEvent.class)
	public void saveUsualLog(UsualLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogUsual logUsual = (LogUsual) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logUsual, chainProperties, serverInfo);
		logService.saveUsualLog(logUsual);
	}

}
