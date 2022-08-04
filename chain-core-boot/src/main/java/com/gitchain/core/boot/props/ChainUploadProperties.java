package com.gitchain.core.boot.props;

import lombok.Getter;
import lombok.Setter;
import com.gitchain.core.tool.utils.PathUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.lang.Nullable;


/**
 * 文件上传配置
 *
 * @author git 
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties("chain.upload")
public class ChainUploadProperties {

	/**
	 * 文件保存目录，默认：jar 包同级目录
	 */
	@Nullable
	private String savePath = PathUtil.getJarPath();
}
