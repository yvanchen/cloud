package com.gitchain.core.sms;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.constant.Code;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsBatchSend;
import lombok.AllArgsConstructor;
import com.gitchain.core.redis.cache.ChainRedis;
import com.gitchain.core.sms.model.SmsCode;
import com.gitchain.core.sms.model.SmsData;
import com.gitchain.core.sms.model.SmsResponse;
import com.gitchain.core.sms.props.SmsProperties;
import com.gitchain.core.tool.support.Kv;
import com.gitchain.core.tool.utils.PlaceholderUtil;
import com.gitchain.core.tool.utils.StringPool;
import com.gitchain.core.tool.utils.StringUtil;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;

/**
 * 云片短信发送类
 *
 * @author git 
 */
@AllArgsConstructor
public class YunpianSmsTemplate implements SmsTemplate {

	private final SmsProperties smsProperties;
	private final YunpianClient client;
	private final ChainRedis chainRedis;

	@Override
	public SmsResponse sendMessage(SmsData smsData, Collection<String> phones) {
		String templateId = smsProperties.getTemplateId();
		// 云片短信模板内容替换, 占位符格式为官方默认的 #code#
		String templateText = PlaceholderUtil.getResolver(StringPool.HASH, StringPool.HASH).resolveByMap(
			templateId, Kv.create().setAll(smsData.getParams())
		);
		Map<String, String> param = client.newParam(2);
		param.put(YunpianClient.MOBILE, StringUtil.join(phones));
		param.put(YunpianClient.TEXT, templateText);
		Result<SmsBatchSend> result = client.sms().multi_send(param);
		return new SmsResponse(result.getCode() == Code.OK, result.getCode(), result.toString());
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
