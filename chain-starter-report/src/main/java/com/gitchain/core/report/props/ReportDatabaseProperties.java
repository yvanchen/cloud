package com.gitchain.core.report.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * UReport配置类
 *
 * @author git 
 */
@Data
@ConfigurationProperties(prefix = "report.database.provider")
public class ReportDatabaseProperties {
	private String name = "数据库文件系统";
	private String prefix = "chain-";
	private boolean disabled = false;
}
