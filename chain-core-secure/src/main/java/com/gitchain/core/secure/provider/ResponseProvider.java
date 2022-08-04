package com.gitchain.core.secure.provider;

import lombok.extern.slf4j.Slf4j;
import com.gitchain.core.tool.api.R;
import com.gitchain.core.tool.api.ResultCode;
import com.gitchain.core.tool.constant.ChainConstant;
import com.gitchain.core.tool.jackson.JsonUtil;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * ResponseProvider
 *
 * @author git 
 */
@Slf4j
public class ResponseProvider {

	public static void write(HttpServletResponse response) {
		R result = R.fail(ResultCode.UN_AUTHORIZED);
		response.setCharacterEncoding(ChainConstant.UTF_8);
		response.addHeader(ChainConstant.CONTENT_TYPE_NAME, MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		try {
			response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}

}
