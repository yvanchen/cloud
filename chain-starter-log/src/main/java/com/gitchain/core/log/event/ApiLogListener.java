package com.gitchain.core.log.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.gitchain.core.launch.props.ChainProperties;
import com.gitchain.core.launch.server.ServerInfo;
import com.gitchain.core.log.constant.EventConstant;
import com.gitchain.core.log.feign.ILogClient;
import com.gitchain.core.log.model.LogApi;
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
public class ApiLogListener {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final ChainProperties chainProperties;


	@Async
	@Order
	@EventListener(ApiLogEvent.class)
	public void saveApiLog(ApiLogEvent event) {
		Map<String, Object> source = (Map<String, Object>) event.getSource();
		LogApi logApi = (LogApi) source.get(EventConstant.EVENT_LOG);
		LogAbstractUtil.addOtherInfoToLog(logApi, chainProperties, serverInfo);
		logService.saveApiLog(logApi);
	}

}
