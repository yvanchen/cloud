package com.gitchain.core.report.datasource;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * UReport数据源配置
 *
 * @author git 
 */
@Slf4j
@AllArgsConstructor
public class ReportDataSource implements BuildinDatasource {
	private static final String NAME = "ReportDataSource";

	private final DataSource dataSource;

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			log.error("report数据源链接失败");
			e.printStackTrace();
		}
		return null;
	}

}
