package com.gitchain.core.mp.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.gitchain.core.mp.base.BaseEntity;
import com.gitchain.core.mp.base.BaseServiceImpl;
import com.gitchain.core.mp.injector.ChainSqlMethod;
import com.gitchain.core.mp.mapper.ChainMapper;
import com.gitchain.core.mp.service.ChainService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

/**
 * ChainService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 *
 * @author git ,
 */
@Validated
public class ChainServiceImpl<M extends ChainMapper<T>, T extends BaseEntity> extends BaseServiceImpl<M, T> implements ChainService<T> {

	@Override
	public boolean saveIgnore(T entity) {
		return SqlHelper.retBool(baseMapper.insertIgnore(entity));
	}

	@Override
	public boolean saveReplace(T entity) {
		return SqlHelper.retBool(baseMapper.replace(entity));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveIgnoreBatch(Collection<T> entityList, int batchSize) {
		return saveBatch(entityList, batchSize, ChainSqlMethod.INSERT_IGNORE_ONE);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveReplaceBatch(Collection<T> entityList, int batchSize) {
		return saveBatch(entityList, batchSize, ChainSqlMethod.REPLACE_ONE);
	}

	private boolean saveBatch(Collection<T> entityList, int batchSize, ChainSqlMethod sqlMethod) {
		String sqlStatement = chainSqlStatement(sqlMethod);
		executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
		return true;
	}

	/**
	 * 获取 chainSqlStatement
	 *
	 * @param sqlMethod ignore
	 * @return sql
	 */
	protected String chainSqlStatement(ChainSqlMethod sqlMethod) {
		return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
	}
}
