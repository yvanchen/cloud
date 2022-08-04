package com.gitchain.core.trace;

import com.gitchain.core.launch.props.ChainPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * TraceAutoConfiguration
 *
 * @author git
 */
@Configuration(proxyBeanMethods = false)
@ChainPropertySource(value = "classpath:/chain-trace.yml")
public class TraceAutoConfiguration {
}
