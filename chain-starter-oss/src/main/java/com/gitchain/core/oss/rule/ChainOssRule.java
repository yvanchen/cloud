package com.gitchain.core.oss.rule;

import lombok.AllArgsConstructor;
import com.gitchain.core.secure.utils.AuthUtil;
import com.gitchain.core.tool.utils.DateUtil;
import com.gitchain.core.tool.utils.FileUtil;
import com.gitchain.core.tool.utils.StringPool;
import com.gitchain.core.tool.utils.StringUtil;

/**
 * 默认存储桶生成规则
 *
 * @author git 
 */
@AllArgsConstructor
public class ChainOssRule implements OssRule {

	/**
	 * 租户模式
	 */
	private final Boolean tenantMode;

	@Override
	public String bucketName(String bucketName) {
		String prefix = (tenantMode) ? AuthUtil.getTenantId().concat(StringPool.DASH) : StringPool.EMPTY;
		return prefix + bucketName;
	}

	@Override
	public String fileName(String originalFilename) {
		return "upload" + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + StringUtil.randomUUID() + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
	}

}
