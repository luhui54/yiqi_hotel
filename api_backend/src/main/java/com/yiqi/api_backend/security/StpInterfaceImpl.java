package com.yiqi.api_backend.security;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口实现
 */
@Component
public class StpInterfaceImpl implements StpInterface {

	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		return ListUtil.empty();
	}

	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		if (!StpKit.ADMIN.isLogin()) {
			return ListUtil.empty();
		}
		return ListUtil.of(Convert.toStr(StpKit.ADMIN.getExtra("role")));
	}

}
