package com.gitchain.core.prometheus.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * Config
 *
 * @author git 
 */
@Getter
@Builder
public class Config {

	@JsonProperty("Datacenter")
	private String dataCenter;

}
