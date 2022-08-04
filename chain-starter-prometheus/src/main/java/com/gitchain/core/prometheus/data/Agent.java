package com.gitchain.core.prometheus.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * Agent
 *
 * @author git
 */
@Getter
@Builder
public class Agent {

	@JsonProperty("Config")
	private Config config;

}
