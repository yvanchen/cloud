package com.gitchain.core.mp.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * MybatisPlus配置类
 *
 * @author git
 */
@Data
@ConfigurationProperties(prefix = "chain.mybatis-plus")
public class MybatisPlusProperties {

	/**
	 * 开启租户模式
	 */
	private Boolean tenantMode = true;

	/**
	 * 开启sql日志
	 */
	private Boolean sqlLog = true;

	/**
	 * sql日志忽略打印关键字
	 */
	private List<String> sqlLogExclude = new ArrayList<>();

	/**
	 * 分页最大数
	 */
	private Long pageLimit = 500L;

	/**
	 * 溢出总页数后是否进行处理
	 */
	protected Boolean overflow = false;

	/**
	 * join优化
	 */
	private Boolean optimizeJoin = false;

}
