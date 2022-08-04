package com.gitchain.core.ribbon.rule;

import com.gitchain.core.ribbon.predicate.HttpAwarePredicate;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.net.util.IPAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * @author chenyuwen
 * @date 2022/6/27
 */
@Slf4j
public class HttpAwareRule extends DiscoveryEnabledRule {
    public HttpAwareRule() {
        super(HttpAwarePredicate.INSTANCE);
    }

    @Override
    public List<Server> filterServers(List<Server> serverList) {
        if (serverList.isEmpty()) {
            log.warn("实例找不到");
            return serverList;
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.info("非web请求");
            return serverList;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String ip = request.getHeader("ip");
        if (StringUtils.isEmpty(ip)) {
            for (Server server : serverList) {
                if (IPAddressUtil.isIPv4LiteralAddress(server.getHost())) {
                    log.info("随机分配");
                    return Collections.singletonList(server);
                }
            }
        }
        log.info("ip:{}", ip);
        String[] ips = ip.split(",");
        for (Server server : serverList) {
            for (String s : ips) {
                if (server.getHost().equals(s)) {
                    log.info("存在ip header并匹配成功,host:{}", s);
                    return Collections.singletonList(server);
                }
            }
        }
        log.info("实例未匹配");
        return serverList;
    }
}
