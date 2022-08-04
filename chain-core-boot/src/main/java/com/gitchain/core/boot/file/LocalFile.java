package com.gitchain.core.boot.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.gitchain.core.boot.props.ChainFileProperties;
import com.gitchain.core.tool.utils.DateUtil;
import com.gitchain.core.tool.utils.SpringUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 上传文件封装
 *
 * @author git
 */
@Data
public class LocalFile {
	/**
	 * 上传文件在附件表中的id
	 */
	private Object fileId;

	/**
	 * 上传文件
	 */
	@JsonIgnore
	private MultipartFile file;

	/**
	 * 文件外网地址
	 */
	private String domain;

	/**
	 * 上传分类文件夹
	 */
	private String dir;

	/**
	 * 上传物理路径
	 */
	private String uploadPath;

	/**
	 * 上传虚拟路径
	 */
	private String uploadVirtualPath;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 真实文件名
	 */
	private String originalFileName;

	/**
	 * 文件配置
	 */
	private static ChainFileProperties fileProperties;

	private static ChainFileProperties getChainFileProperties() {
		if (fileProperties == null) {
			fileProperties = SpringUtil.getBean(ChainFileProperties.class);
		}
		return fileProperties;
	}

	public LocalFile(MultipartFile file, String dir) {
		this.dir = dir;
		this.file = file;
		this.fileName = file.getName();
		this.originalFileName = file.getOriginalFilename();
		this.domain = getChainFileProperties().getUploadDomain();
		this.uploadPath = ChainFileUtil.formatUrl(File.separator + getChainFileProperties().getUploadRealPath() + File.separator + dir + File.separator + DateUtil.format(DateUtil.now(), "yyyyMMdd") + File.separator + this.originalFileName);
		this.uploadVirtualPath = ChainFileUtil.formatUrl(getChainFileProperties().getUploadCtxPath().replace(getChainFileProperties().getContextPath(), "") + File.separator + dir + File.separator + DateUtil.format(DateUtil.now(), "yyyyMMdd") + File.separator + this.originalFileName);
	}

	public LocalFile(MultipartFile file, String dir, String uploadPath, String uploadVirtualPath) {
		this(file, dir);
		if (null != uploadPath) {
			this.uploadPath = ChainFileUtil.formatUrl(uploadPath);
			this.uploadVirtualPath = ChainFileUtil.formatUrl(uploadVirtualPath);
		}
	}

	/**
	 * 图片上传
	 */
	public void transfer() {
		transfer(getChainFileProperties().getCompress());
	}

	/**
	 * 图片上传
	 *
	 * @param compress 是否压缩
	 */
	public void transfer(boolean compress) {
		IFileProxy fileFactory = FileProxyManager.me().getDefaultFileProxyFactory();
		this.transfer(fileFactory, compress);
	}

	/**
	 * 图片上传
	 *
	 * @param fileFactory 文件上传工厂类
	 * @param compress    是否压缩
	 */
	public void transfer(IFileProxy fileFactory, boolean compress) {
		try {
			File file = new File(uploadPath);

			if (null != fileFactory) {
				String[] path = fileFactory.path(file, dir);
				this.uploadPath = path[0];
				this.uploadVirtualPath = path[1];
				file = fileFactory.rename(file, path[0]);
			}

			File pfile = file.getParentFile();
			if (!pfile.exists()) {
				pfile.mkdirs();
			}

			this.file.transferTo(file);

			if (compress) {
				fileFactory.compress(this.uploadPath);
			}

		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

}
