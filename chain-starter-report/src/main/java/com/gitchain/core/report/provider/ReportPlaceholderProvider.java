package com.gitchain.core.report.provider;

import com.bstek.ureport.UReportPropertyPlaceholderConfigurer;
import com.gitchain.core.report.props.ReportProperties;

import java.util.Properties;

/**
 * UReport自定义配置
 *
 * @author git 
 */
public class ReportPlaceholderProvider extends UReportPropertyPlaceholderConfigurer {

	public ReportPlaceholderProvider(ReportProperties properties) {
		Properties props = new Properties();
		props.setProperty("ureport.disableHttpSessionReportCache", properties.getDisableHttpSessionReportCache().toString());
		props.setProperty("ureport.disableFileProvider", properties.getDisableFileProvider().toString());
		props.setProperty("ureport.fileStoreDir", properties.getFileStoreDir());
		props.setProperty("ureport.debug", properties.getDebug().toString());
		this.setProperties(props);
	}

}
