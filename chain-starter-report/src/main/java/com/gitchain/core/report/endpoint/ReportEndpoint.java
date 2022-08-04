package com.gitchain.core.report.endpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import com.gitchain.core.mp.support.Condition;
import com.gitchain.core.mp.support.Query;
import com.gitchain.core.report.entity.ReportFileEntity;
import com.gitchain.core.tool.api.R;
import com.gitchain.core.tool.utils.Func;
import com.gitchain.core.report.service.IReportFileService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * UReport API端点
 *
 * @author git 
 */
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping("/report/rest")
public class ReportEndpoint {

	private final IReportFileService service;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	public R<ReportFileEntity> detail(ReportFileEntity file) {
		ReportFileEntity detail = service.getOne(Condition.getQueryWrapper(file));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	public R<IPage<ReportFileEntity>> list(@RequestParam Map<String, Object> file, Query query) {
		IPage<ReportFileEntity> pages = service.page(Condition.getPage(query), Condition.getQueryWrapper(file, ReportFileEntity.class));
		return R.data(pages);
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	public R remove(@RequestParam String ids) {
		boolean temp = service.removeByIds(Func.toLongList(ids));
		return R.status(temp);
	}

}
