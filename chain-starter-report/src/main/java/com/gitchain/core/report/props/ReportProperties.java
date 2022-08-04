package com.gitchain.core.report.props;

import lombok.Data;
import com.gitchain.core.tool.utils.StringPool;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * UReport配置类
 *
 * @author git 
 */
@Data
@ConfigurationProperties(prefix = "report")
public class ReportProperties {
	private Boolean enabled = true;
	private Boolean disableHttpSessionReportCache = false;
	private Boolean disableFileProvider = true;
	private String fileStoreDir = StringPool.EMPTY;
	private Boolean debug = false;
}
