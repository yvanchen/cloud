package com.gitchain.core.secure.utils;

import io.jsonwebtoken.Claims;
import com.gitchain.core.jwt.JwtUtil;
import com.gitchain.core.jwt.props.JwtProperties;
import com.gitchain.core.launch.constant.TokenConstant;
import com.gitchain.core.secure.ChainUser;
import com.gitchain.core.tool.constant.RoleConstant;
import com.gitchain.core.tool.support.Kv;
import com.gitchain.core.tool.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * Auth工具类
 *
 * @author git 
 */
public class AuthUtil {
	private static final String CHAIN_USER_REQUEST_ATTR = "_CHAIN_USER_REQUEST_ATTR_";

	private final static String HEADER = TokenConstant.HEADER;
	private final static String ACCOUNT = TokenConstant.ACCOUNT;
	private final static String USER_NAME = TokenConstant.USER_NAME;
	private final static String NICK_NAME = TokenConstant.NICK_NAME;
	private final static String USER_ID = TokenConstant.USER_ID;
	private final static String DEPT_ID = TokenConstant.DEPT_ID;
	private final static String POST_ID = TokenConstant.POST_ID;
	private final static String ROLE_ID = TokenConstant.ROLE_ID;
	private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
	private final static String TENANT_ID = TokenConstant.TENANT_ID;
	private final static String OAUTH_ID = TokenConstant.OAUTH_ID;
	private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
	private final static String DETAIL = TokenConstant.DETAIL;

	private static JwtProperties jwtProperties;

	/**
	 * 获取配置类
	 *
	 * @return jwtProperties
	 */
	private static JwtProperties getJwtProperties() {
		if (jwtProperties == null) {
			jwtProperties = SpringUtil.getBean(JwtProperties.class);
		}
		return jwtProperties;
	}

	/**
	 * 获取用户信息
	 *
	 * @return ChainUser
	 */
	public static ChainUser getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		// 优先从 request 中获取
		Object chainUser = request.getAttribute(CHAIN_USER_REQUEST_ATTR);
		if (chainUser == null) {
			chainUser = getUser(request);
			if (chainUser != null) {
				// 设置到 request 中
				request.setAttribute(CHAIN_USER_REQUEST_ATTR, chainUser);
			}
		}
		return (ChainUser) chainUser;
	}

	/**
	 * 获取用户信息
	 *
	 * @param request request
	 * @return ChainUser
	 */
	@SuppressWarnings("unchecked")
	public static ChainUser getUser(HttpServletRequest request) {
		Claims claims = getClaims(request);
		if (claims == null) {
			return null;
		}
		String clientId = Func.toStr(claims.get(AuthUtil.CLIENT_ID));
		Long userId = Func.toLong(claims.get(AuthUtil.USER_ID));
		String tenantId = Func.toStr(claims.get(AuthUtil.TENANT_ID));
		String oauthId = Func.toStr(claims.get(AuthUtil.OAUTH_ID));
		String deptId = Func.toStrWithEmpty(claims.get(AuthUtil.DEPT_ID), StringPool.MINUS_ONE);
		String postId = Func.toStrWithEmpty(claims.get(AuthUtil.POST_ID), StringPool.MINUS_ONE);
		String roleId = Func.toStrWithEmpty(claims.get(AuthUtil.ROLE_ID), StringPool.MINUS_ONE);
		String account = Func.toStr(claims.get(AuthUtil.ACCOUNT));
		String roleName = Func.toStr(claims.get(AuthUtil.ROLE_NAME));
		String userName = Func.toStr(claims.get(AuthUtil.USER_NAME));
		String nickName = Func.toStr(claims.get(AuthUtil.NICK_NAME));
		Kv detail = Kv.create().setAll((Map<? extends String, ?>) claims.get(AuthUtil.DETAIL));
		ChainUser chainUser = new ChainUser();
		chainUser.setClientId(clientId);
		chainUser.setUserId(userId);
		chainUser.setTenantId(tenantId);
		chainUser.setOauthId(oauthId);
		chainUser.setAccount(account);
		chainUser.setDeptId(deptId);
		chainUser.setPostId(postId);
		chainUser.setRoleId(roleId);
		chainUser.setRoleName(roleName);
		chainUser.setUserName(userName);
		chainUser.setNickName(nickName);
		chainUser.setDetail(detail);
		return chainUser;
	}

	/**
	 * 是否为超管
	 *
	 * @return boolean
	 */
	public static boolean isAdministrator() {
		return StringUtil.containsAny(getUserRole(), RoleConstant.ADMINISTRATOR);
	}

	/**
	 * 是否为管理员
	 *
	 * @return boolean
	 */
	public static boolean isAdmin() {
		return StringUtil.containsAny(getUserRole(), RoleConstant.ADMIN);
	}

	/**
	 * 获取用户id
	 *
	 * @return userId
	 */
	public static Long getUserId() {
		ChainUser user = getUser();
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户id
	 *
	 * @param request request
	 * @return userId
	 */
	public static Long getUserId(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户账号
	 *
	 * @return userAccount
	 */
	public static String getUserAccount() {
		ChainUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户账号
	 *
	 * @param request request
	 * @return userAccount
	 */
	public static String getUserAccount(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户名
	 *
	 * @return userName
	 */
	public static String getUserName() {
		ChainUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户名
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserName(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取昵称
	 *
	 * @return userName
	 */
	public static String getNickName() {
		ChainUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getNickName();
	}

	/**
	 * 获取昵称
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getNickName(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getNickName();
	}

	/**
	 * 获取用户部门
	 *
	 * @return userName
	 */
	public static String getDeptId() {
		ChainUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getDeptId();
	}

	/**
	 * 获取用户部门
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getDeptId(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getDeptId();
	}

	/**
	 * 获取用户岗位
	 *
	 * @return userName
	 */
	public static String getPostId() {
		ChainUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getPostId();
	}

	/**
	 * 获取用户岗位
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getPostId(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getPostId();
	}

	/**
	 * 获取用户角色
	 *
	 * @return userName
	 */
	public static String getUserRole() {
		ChainUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取用角色
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserRole(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取租户ID
	 *
	 * @return tenantId
	 */
	public static String getTenantId() {
		ChainUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getTenantId();
	}

	/**
	 * 获取租户ID
	 *
	 * @param request request
	 * @return tenantId
	 */
	public static String getTenantId(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getTenantId();
	}

	/**
	 * 获取第三方认证ID
	 *
	 * @return tenantId
	 */
	public static String getOauthId() {
		ChainUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getOauthId();
	}

	/**
	 * 获取第三方认证ID
	 *
	 * @param request request
	 * @return tenantId
	 */
	public static String getOauthId(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getOauthId();
	}

	/**
	 * 获取客户端id
	 *
	 * @return clientId
	 */
	public static String getClientId() {
		ChainUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取客户端id
	 *
	 * @param request request
	 * @return clientId
	 */
	public static String getClientId(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取用户详情
	 *
	 * @return clientId
	 */
	public static Kv getDetail() {
		ChainUser user = getUser();
		return (null == user) ? Kv.create() : user.getDetail();
	}

	/**
	 * 获取用户详情
	 *
	 * @param request request
	 * @return clientId
	 */
	public static Kv getDetail(HttpServletRequest request) {
		ChainUser user = getUser(request);
		return (null == user) ? Kv.create() : user.getDetail();
	}

	/**
	 * 获取Claims
	 *
	 * @param request request
	 * @return Claims
	 */
	public static Claims getClaims(HttpServletRequest request) {
		String auth = request.getHeader(AuthUtil.HEADER);
		Claims claims = null;
		String token;
		// 获取 Token 参数
		if (StringUtil.isNotBlank(auth)) {
			token = JwtUtil.getToken(auth);
		} else {
			String parameter = request.getParameter(AuthUtil.HEADER);
			token = JwtUtil.getToken(parameter);
		}
		// 获取 Token 值
		if (StringUtil.isNotBlank(token)) {
			claims = AuthUtil.parseJWT(token);
		}
		// 判断 Token 状态
		if (ObjectUtil.isNotEmpty(claims) && getJwtProperties().getState()) {
			String tenantId = Func.toStr(claims.get(AuthUtil.TENANT_ID));
			String userId = Func.toStr(claims.get(AuthUtil.USER_ID));
			String accessToken = JwtUtil.getAccessToken(tenantId, userId, token);
			if (!token.equalsIgnoreCase(accessToken)) {
				return null;
			}
		}
		return claims;
	}

	/**
	 * 获取请求头
	 *
	 * @return header
	 */
	public static String getHeader() {
		return getHeader(Objects.requireNonNull(WebUtil.getRequest()));
	}

	/**
	 * 获取请求头
	 *
	 * @param request request
	 * @return header
	 */
	public static String getHeader(HttpServletRequest request) {
		return request.getHeader(HEADER);
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken jsonWebToken
	 * @return Claims
	 */
	public static Claims parseJWT(String jsonWebToken) {
		return JwtUtil.parseJWT(jsonWebToken);
	}

}
