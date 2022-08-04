package com.gitchain.core.report.endpoint;

import com.gitchain.core.launch.constant.AppConstant;
import com.gitchain.core.report.service.IReportFileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * UReport Boot版 API端点
 *
 * @author git
 */
@ApiIgnore
@RestController
@RequestMapping(AppConstant.APPLICATION_REPORT_NAME + "/report/rest")
public class ReportBootEndpoint extends ReportEndpoint {

	public ReportBootEndpoint(IReportFileService service) {
		super(service);
	}

}
