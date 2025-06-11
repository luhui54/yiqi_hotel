package com.yiqi.api_backend.security;

import cn.dev33.satoken.jwt.StpLogicJwtForStateless;
import cn.dev33.satoken.stp.StpLogic;

/**
 * StpLogic 门面类，管理项目中所有的 StpLogic 账号体系
 */
public class StpKit {

	/**
	 * 默认原生会话对象
	 * fix bug: 因为改为jwt后，权限注解无法识别，需要指定loginType为admin才行
	 */
	public static final StpLogic ADMIN = new StpLogicJwtForStateless("admin");

	/**
	 * User 会话对象，管理 User 表所有账号的登录、权限认证
	 */
	public static final StpLogic USER = new StpLogicJwtForStateless("user");

	public static Long getUserId() {
		return USER.getLoginIdAsLong();
	}

	public static Long getAdminId() {
		return ADMIN.getLoginIdAsLong();
	}

}
