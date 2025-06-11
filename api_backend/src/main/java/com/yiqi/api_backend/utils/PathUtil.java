package com.yiqi.api_backend.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 路径工具类
 */
public class PathUtil {

	public static String getImageDomain(HttpServletRequest req) {
		// 协议 域名 端口 项目虚拟路径
		String basePath = req.getScheme() + "://" + req.getServerName();

		if (req.getServerPort() != 80) {
			basePath += ":" + req.getServerPort();
		}

		basePath += req.getContextPath();

		// 去掉最后的斜杠
		if (basePath.endsWith("/")) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}

		return basePath;
	}

}
