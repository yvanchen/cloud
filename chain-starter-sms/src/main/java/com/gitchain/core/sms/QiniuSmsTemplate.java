package com.gitchain.core.sms;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import lombok.AllArgsConstructor;
import com.gitchain.core.redis.cache.ChainRedis;
import com.gitchain.core.sms.model.SmsCode;
import com.gitchain.core.sms.model.SmsData;
import com.gitchain.core.sms.model.SmsResponse;
import com.gitchain.core.sms.props.SmsProperties;
import com.gitchain.core.tool.utils.StringUtil;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.util.Collection;

/**
 * 七牛云短信发送类
 *
 * @author git
 */
@AllArgsConstructor
public class QiniuSmsTemplate implements SmsTemplate {

	private final SmsProperties smsProperties;
	private final SmsManager smsManager;
	private final ChainRedis chainRedis;

	@Override
	public SmsResponse sendMessage(SmsData smsData, Collection<String> phones) {
		try {
			Response response = smsManager.sendMessage(smsProperties.getTemplateId(), StringUtil.toStringArray(phones), smsData.getParams());
			return new SmsResponse(response.isOK(), response.statusCode, response.toString());
		} catch (QiniuException e) {
			e.printStackTrace();
			return new SmsResponse(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	@Override
	public SmsCode sendValidate(SmsData smsData, String phone) {
		SmsCode smsCode = new SmsCode();
		boolean temp = sendSingle(smsData, phone);
		if (temp && StringUtil.isNotBlank(smsData.getKey())) {
			String id = StringUtil.randomUUID();
			String value = smsData.getParams().get(smsData.getKey());
			chainRedis.setEx(cacheKey(phone, id), value, Duration.ofMinutes(30));
			smsCode.setId(id).setValue(value);
		} else {
			smsCode.setSuccess(Boolean.FALSE);
		}
		return smsCode;
	}

	@Override
	public boolean validateMessage(SmsCode smsCode) {
		String id = smsCode.getId();
		String value = smsCode.getValue();
		String phone = smsCode.getPhone();
		String cache = chainRedis.get(cacheKey(phone, id));
		if (StringUtil.isNotBlank(value) && StringUtil.equalsIgnoreCase(cache, value)) {
			chainRedis.del(cacheKey(phone, id));
			return true;
		}
		return false;
	}

}
