package com.gitchain.core.tenant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置
 *
 * @author git 
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "chain.tenant")
public class ChainTenantProperties {

	/**
	 * 是否增强多租户
	 */
	private Boolean enhance = Boolean.FALSE;

	/**
	 * 是否开启授权码校验
	 */
	private Boolean license = Boolean.FALSE;

	/**
	 * 是否开启动态数据源功能
	 */
	private Boolean dynamicDatasource = Boolean.FALSE;

	/**
	 * 是否开启动态数据源全局扫描
	 */
	private Boolean dynamicGlobal = Boolean.FALSE;

	/**
	 * 多租户字段名称
	 */
	private String column = "tenant_id";

	/**
	 * 是否开启注解排除
	 */
	private Boolean annotationExclude = Boolean.FALSE;

	/**
	 * 需要排除进行自定义的多租户表
	 */
	private List<String> excludeTables = new ArrayList<>();

}
