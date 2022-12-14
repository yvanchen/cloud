package com.gitchain.core.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.gitchain.core.oss.model.ChainFile;
import com.gitchain.core.oss.model.OssFile;
import com.gitchain.core.oss.props.OssProperties;
import com.gitchain.core.oss.rule.OssRule;
import com.gitchain.core.tool.jackson.JsonUtil;
import com.gitchain.core.tool.utils.StringPool;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AliossTemplate
 *
 * @author git
 */
@AllArgsConstructor
public class AliossTemplate implements OssTemplate {
	private final OSSClient ossClient;
	private final OssProperties ossProperties;
	private final OssRule ossRule;

	@Override
	@SneakyThrows
	public void makeBucket(String bucketName) {
		if (!bucketExists(bucketName)) {
			ossClient.createBucket(getBucketName(bucketName));
		}
	}

	@Override
	@SneakyThrows
	public void removeBucket(String bucketName) {
		ossClient.deleteBucket(getBucketName(bucketName));
	}

	@Override
	@SneakyThrows
	public boolean bucketExists(String bucketName) {
		return ossClient.doesBucketExist(getBucketName(bucketName));
	}

	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		ossClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		ossClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}

	@Override
	@SneakyThrows
	public OssFile statFile(String fileName) {
		return statFile(ossProperties.getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public OssFile statFile(String bucketName, String fileName) {
		ObjectMetadata stat = ossClient.getObjectMetadata(getBucketName(bucketName), fileName);
		OssFile ossFile = new OssFile();
		ossFile.setName(fileName);
		ossFile.setLink(fileLink(ossFile.getName()));
		ossFile.setHash(stat.getContentMD5());
		ossFile.setLength(stat.getContentLength());
		ossFile.setPutTime(stat.getLastModified());
		ossFile.setContentType(stat.getContentType());
		return ossFile;
	}

	@Override
	@SneakyThrows
	public String filePath(String fileName) {
		return getOssHost().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String filePath(String bucketName, String fileName) {
		return getOssHost(bucketName).concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String fileLink(String fileName) {
		return getOssHost().concat(StringPool.SLASH).concat(fileName);
	}

	@Override
	@SneakyThrows
	public String fileLink(String bucketName, String fileName) {
		return getOssHost(bucketName).concat(StringPool.SLASH).concat(fileName);
	}

	/**
	 * ????????????
	 *
	 * @param file ???????????????
	 * @return
	 */
	@Override
	@SneakyThrows
	public ChainFile putFile(MultipartFile file) {
		return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file);
	}

	/**
	 * @param fileName ???????????????
	 * @param file     ???????????????
	 * @return
	 */
	@Override
	@SneakyThrows
	public ChainFile putFile(String fileName, MultipartFile file) {
		return putFile(ossProperties.getBucketName(), fileName, file);
	}

	@Override
	@SneakyThrows
	public ChainFile putFile(String bucketName, String fileName, MultipartFile file) {
		return putFile(bucketName, fileName, file.getInputStream());
	}

	@Override
	@SneakyThrows
	public ChainFile putFile(String fileName, InputStream stream) {
		return putFile(ossProperties.getBucketName(), fileName, stream);
	}

	@Override
	@SneakyThrows
	public ChainFile putFile(String bucketName, String fileName, InputStream stream) {
		return put(bucketName, stream, fileName, false);
	}

	@SneakyThrows
	public ChainFile put(String bucketName, InputStream stream, String key, boolean cover) {
		makeBucket(bucketName);
		String originalName = key;
		key = getFileName(key);
		// ????????????
		if (cover) {
			ossClient.putObject(getBucketName(bucketName), key, stream);
		} else {
			PutObjectResult response = ossClient.putObject(getBucketName(bucketName), key, stream);
			int retry = 0;
			int retryCount = 5;
			while (StringUtils.isEmpty(response.getETag()) && retry < retryCount) {
				response = ossClient.putObject(getBucketName(bucketName), key, stream);
				retry++;
			}
		}
		ChainFile file = new ChainFile();
		file.setOriginalName(originalName);
		file.setName(key);
		file.setDomain(getOssHost(bucketName));
		file.setLink(fileLink(bucketName, key));
		return file;
	}

	@Override
	@SneakyThrows
	public void removeFile(String fileName) {
		ossClient.deleteObject(getBucketName(), fileName);
	}

	@Override
	@SneakyThrows
	public void removeFile(String bucketName, String fileName) {
		ossClient.deleteObject(getBucketName(bucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void removeFiles(List<String> fileNames) {
		fileNames.forEach(this::removeFile);
	}

	@Override
	@SneakyThrows
	public void removeFiles(String bucketName, List<String> fileNames) {
		fileNames.forEach(fileName -> removeFile(getBucketName(bucketName), fileName));
	}

	/**
	 * ???????????????????????????????????????
	 *
	 * @return String
	 */
	private String getBucketName() {
		return getBucketName(ossProperties.getBucketName());
	}

	/**
	 * ???????????????????????????????????????
	 *
	 * @param bucketName ???????????????
	 * @return String
	 */
	private String getBucketName(String bucketName) {
		return ossRule.bucketName(bucketName);
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

	public String getUploadToken() {
		return getUploadToken(ossProperties.getBucketName());
	}

	/**
	 * TODO ????????????
	 * <p>
	 * ?????????????????????????????????
	 */
	public String getUploadToken(String bucketName) {
		// ??????????????????2??????
		return getUploadToken(bucketName, ossProperties.getArgs().get("expireTime", 3600L));
	}

	/**
	 * TODO ?????????????????????????????????
	 * <p>
	 * ?????????????????????????????????
	 */
	public String getUploadToken(String bucketName, long expireTime) {
		String baseDir = "upload";

		long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
		Date expiration = new Date(expireEndTime);

		PolicyConditions policyConds = new PolicyConditions();
		// ??????????????????10M
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, ossProperties.getArgs().get("contentLengthRange", 10485760));
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, baseDir);

		String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
		byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);
		String postSignature = ossClient.calculatePostSignature(postPolicy);

		Map<String, String> respMap = new LinkedHashMap<>(16);
		respMap.put("accessid", ossProperties.getAccessKey());
		respMap.put("policy", encodedPolicy);
		respMap.put("signature", postSignature);
		respMap.put("dir", baseDir);
		respMap.put("host", getOssHost(bucketName));
		respMap.put("expire", String.valueOf(expireEndTime / 1000));
		return JsonUtil.toJson(respMap);
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
