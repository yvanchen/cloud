package com.gitchain.core.oss;

import com.obs.services.ObsClient;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.gitchain.core.oss.model.ChainFile;
import com.gitchain.core.oss.model.OssFile;
import com.gitchain.core.oss.props.OssProperties;
import com.gitchain.core.oss.rule.OssRule;
import com.gitchain.core.tool.utils.StringPool;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author git Tonny
 */
@AllArgsConstructor
public class HuaweiObsTemplate implements OssTemplate {

	private final ObsClient obsClient;
	private final OssProperties ossProperties;
	private final OssRule ossRule;

	@Override
	public void makeBucket(String bucketName) {
		if (!bucketExists(bucketName)) {
			obsClient.createBucket(getBucketName(bucketName));
		}
	}

	@Override
	public void removeBucket(String bucketName) {
		obsClient.deleteBucket(getBucketName(bucketName));
	}

	@Override
	public boolean bucketExists(String bucketName) {
		return obsClient.headBucket(getBucketName(bucketName));
	}

	@Override
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		obsClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}

	@Override
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		obsClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}

	@Override
	public OssFile statFile(String fileName) {
		return statFile(ossProperties.getBucketName(), fileName);
	}

	@Override
	public OssFile statFile(String bucketName, String fileName) {
		ObjectMetadata stat = obsClient.getObjectMetadata(getBucketName(bucketName), fileName);
		OssFile ossFile = new OssFile();
		ossFile.setName(fileName);
		ossFile.setLink(fileLink(ossFile.getName()));
		ossFile.setHash(stat.getContentMd5());
		ossFile.setLength(stat.getContentLength());
		ossFile.setPutTime(stat.getLastModified());
		ossFile.setContentType(stat.getContentType());
		return ossFile;
	}

	@Override
	public String filePath(String fileName) {
		return getOssHost(getBucketName()).concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	public String filePath(String bucketName, String fileName) {
		return getOssHost(getBucketName(bucketName)).concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	public String fileLink(String fileName) {
		return getOssHost().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	public String fileLink(String bucketName, String fileName) {
		return getOssHost(getBucketName(bucketName)).concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	public ChainFile putFile(MultipartFile file) {
		return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file);
	}

	@Override
	public ChainFile putFile(String fileName, MultipartFile file) {
		return putFile(ossProperties.getBucketName(), fileName, file);
	}

	@Override
	@SneakyThrows
	public ChainFile putFile(String bucketName, String fileName, MultipartFile file) {
		return putFile(bucketName, fileName, file.getInputStream());
	}

	@Override
	public ChainFile putFile(String fileName, InputStream stream) {
		return putFile(ossProperties.getBucketName(), fileName, stream);
	}

	@Override
	public ChainFile putFile(String bucketName, String fileName, InputStream stream) {
		return put(bucketName, stream, fileName, false);
	}

	@Override
	public void removeFile(String fileName) {
		obsClient.deleteObject(getBucketName(), fileName);
	}

	@Override
	public void removeFile(String bucketName, String fileName) {
		obsClient.deleteObject(getBucketName(bucketName), fileName);
	}

	@Override
	public void removeFiles(List<String> fileNames) {
		fileNames.forEach(this::removeFile);
	}

	@Override
	public void removeFiles(String bucketName, List<String> fileNames) {
		fileNames.forEach(fileName -> removeFile(getBucketName(bucketName), fileName));
	}

	/**
	 * ???????????????
	 *
	 * @param bucketName
	 * @param stream
	 * @param key
	 * @param cover
	 * @return
	 */
	@SneakyThrows
	public ChainFile put(String bucketName, InputStream stream, String key, boolean cover) {
		makeBucket(bucketName);

		String originalName = key;

		key = getFileName(key);

		// ????????????
		if (cover) {
			obsClient.putObject(getBucketName(bucketName), key, stream);
		} else {
			PutObjectResult response = obsClient.putObject(getBucketName(bucketName), key, stream);
			int retry = 0;
			int retryCount = 5;
			while (StringUtils.isEmpty(response.getEtag()) && retry < retryCount) {
				response = obsClient.putObject(getBucketName(bucketName), key, stream);
				retry++;
			}
		}

		ChainFile file = new ChainFile();
		file.setOriginalName(originalName);
		file.setName(key);
		file.setLink(fileLink(bucketName, key));
		return file;
	}

	/**
	 * ????????????????????????????????????
	 *
	 * @param originalFilename ???????????????
	 * @return string
	 */
	private String getFileName(String originalFilename) {
		return ossRule.fileName(originalFilename);
	}

	/**
	 * ???????????????????????????????????????  ?????????
	 *
	 * @return String
	 */
	private String getBucketName() {
		return getBucketName(ossProperties.getBucketName());
	}

	/**
	 * ??????????????????????????????????????? ?????????
	 *
	 * @param bucketName ???????????????
	 * @return String
	 */
	private String getBucketName(String bucketName) {
		return ossRule.bucketName(bucketName);
	}

	/**
	 * ????????????
	 *
	 * @param bucketName ???????????????
	 * @return String
	 */
	public String getOssHost(String bucketName) {
		String prefix = ossProperties.getEndpoint().contains("https://") ? "https://" : "http://";
		return prefix + getBucketName(bucketName) + StringPool.DOT + ossProperties.getEndpoint().replaceFirst(prefix, StringPool.EMPTY);
	}

	/**
	 * ????????????
	 *
	 * @return String
	 */
	public String getOssHost() {
		return getOssHost(ossProperties.getBucketName());
	}
}
