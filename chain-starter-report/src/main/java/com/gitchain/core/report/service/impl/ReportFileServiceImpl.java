package com.gitchain.core.report.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitchain.core.report.entity.ReportFileEntity;
import com.gitchain.core.report.mapper.ReportFileMapper;
import com.gitchain.core.report.service.IReportFileService;
import org.springframework.stereotype.Service;

/**
 * UReport Service
 *
 * @author git
 */
@Service
public class ReportFileServiceImpl extends ServiceImpl<ReportFileMapper, ReportFileEntity> implements IReportFileService {
}
