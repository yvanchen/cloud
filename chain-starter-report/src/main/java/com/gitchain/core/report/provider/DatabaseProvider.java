package com.gitchain.core.report.provider;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import lombok.AllArgsConstructor;
import com.gitchain.core.tool.constant.ChainConstant;
import com.gitchain.core.tool.utils.DateUtil;
import com.gitchain.core.report.entity.ReportFileEntity;
import com.gitchain.core.report.props.ReportDatabaseProperties;
import com.gitchain.core.report.service.IReportFileService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据库文件处理
 *
 * @author git
 */
@AllArgsConstructor
public class DatabaseProvider implements ReportProvider {

	private final ReportDatabaseProperties properties;
	private final IReportFileService service;

	@Override
	public InputStream loadReport(String file) {
		ReportFileEntity reportFileEntity = service.getOne(Wrappers.<ReportFileEntity>lambdaQuery().eq(ReportFileEntity::getName, getFileName(file)));
		byte[] content = reportFileEntity.getContent();
		return new ByteArrayInputStream(content);
	}

	@Override
	public void deleteReport(String file) {
		service.remove(Wrappers.<ReportFileEntity>lambdaUpdate().eq(ReportFileEntity::getName, getFileName(file)));
	}

	@Override
	public List<ReportFile> getReportFiles() {
		List<ReportFileEntity> list = service.list();
		List<ReportFile> reportFiles = new ArrayList<>();
		list.forEach(reportFileEntity -> reportFiles.add(new ReportFile(reportFileEntity.getName(), reportFileEntity.getUpdateTime())));
		return reportFiles;
	}

	@Override
	public void saveReport(String file, String content) {
		String fileName = getFileName(file);
		ReportFileEntity reportFileEntity = service.getOne(Wrappers.<ReportFileEntity>lambdaQuery().eq(ReportFileEntity::getName, fileName));
		Date now = DateUtil.now();
		if (reportFileEntity == null) {
			reportFileEntity = new ReportFileEntity();
			reportFileEntity.setName(fileName);
			reportFileEntity.setContent(content.getBytes());
			reportFileEntity.setCreateTime(now);
			reportFileEntity.setIsDeleted(ChainConstant.DB_NOT_DELETED);
		} else {
			reportFileEntity.setContent(content.getBytes());
		}
		reportFileEntity.setUpdateTime(now);
		service.saveOrUpdate(reportFileEntity);
	}

	@Override
	public String getName() {
		return properties.getName();
	}

	@Override
	public boolean disabled() {
		return properties.isDisabled();
	}

	@Override
	public String getPrefix() {
		return properties.getPrefix();
	}

	/**
	 * 获取标准格式文件名
	 *
	 * @param name 原文件名
	 */
	private String getFileName(String name) {
		if (name.startsWith(getPrefix())) {
			name = name.substring(getPrefix().length());
		}
		return name;
	}

}
