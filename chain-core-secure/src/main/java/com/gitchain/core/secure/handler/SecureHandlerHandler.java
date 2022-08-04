package com.gitchain.core.secure.handler;

import com.gitchain.core.secure.interceptor.*;
import com.gitchain.core.secure.props.AuthSecure;
import com.gitchain.core.secure.props.BasicSecure;
import com.gitchain.core.secure.props.SignSecure;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;

/**
 * Secure处理器
 *
 * @author git
 */
public class SecureHandlerHandler implements ISecureHandler {

	@Override
	public HandlerInterceptorAdapter tokenInterceptor() {
		return new TokenInterceptor();
	}

	@Override
	public HandlerInterceptorAdapter authInterceptor(List<AuthSecure> authSecures) {
		return new AuthInterceptor(authSecures);
	}

	@Override
	public HandlerInterceptorAdapter basicInterceptor(List<BasicSecure> basicSecures) {
		return new BasicInterceptor(basicSecures);
	}

	@Override
	public HandlerInterceptorAdapter signInterceptor(List<SignSecure> signSecures) {
		return new SignInterceptor(signSecures);
	}

	@Override
	public HandlerInterceptorAdapter clientInterceptor(String clientId) {
		return new ClientInterceptor(clientId);
	}

}
