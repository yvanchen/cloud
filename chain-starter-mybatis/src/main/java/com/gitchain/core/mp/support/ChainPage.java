package com.gitchain.core.mp.support;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页模型
 *
 * @author git
 */
@Data
public class ChainPage<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 查询数据列表
	 */
	private List<T> records = Collections.emptyList();

	/**
	 * 总数
	 */
	private long total = 0;
	/**
	 * 每页显示条数，默认 10
	 */
	private long size = 10;

	/**
	 * 当前页
	 */
	private long current = 1;

	/**
	 * mybatis-plus分页模型转化
	 *
	 * @param page 分页实体类
	 * @return ChainPage<T>
	 */
	public static <T> ChainPage<T> of(IPage<T> page) {
		ChainPage<T> chainPage = new ChainPage<>();
		chainPage.setRecords(page.getRecords());
		chainPage.setTotal(page.getTotal());
		chainPage.setSize(page.getSize());
		chainPage.setCurrent(page.getCurrent());
		return chainPage;
	}

}
