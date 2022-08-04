package com.gitchain.core.datascope.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.gitchain.core.datascope.constant.DataScopeConstant;
import com.gitchain.core.datascope.enums.DataScopeEnum;

import java.io.Serializable;

/**
 * 数据权限实体类
 *
 * @author git
 */
@Data
@NoArgsConstructor
public class DataScopeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 构造器创建
	 */
	public DataScopeModel(Boolean searched) {
		this.searched = searched;
	}

	/**
	 * 是否已查询
	 */
	private Boolean searched = Boolean.FALSE;
	/**
	 * 资源编号
	 */
	private String resourceCode;
	/**
	 * 数据权限字段
	 */
	private String scopeColumn = DataScopeConstant.DEFAULT_COLUMN;
	/**
	 * 数据权限规则
	 */
	private Integer scopeType = DataScopeEnum.ALL.getType();
	/**
	 * 可见字段
	 */
	private String scopeField;
	/**
	 * 数据权限规则值
	 */
	private String scopeValue;
}
