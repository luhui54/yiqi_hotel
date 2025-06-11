package com.yiqi.api_backend.utils;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;

/**
 * 本地缓存工具类
 */
public class LocalCache {

	public static TimedCache<String, String> timedCache = CacheUtil.newTimedCache(60 * 1000L);

	static {
		// 启动定时任务，每分钟清理过期缓存
		timedCache.schedulePrune(60 * 1000L);
	}

	public static void set(String key, String value, Integer expire) {
		timedCache.put(key, value, DateUnit.SECOND.getMillis() * expire);
	}

	public static String get(String key) {
		return timedCache.get(key, false);
	}

	public static void del(String key) {
		timedCache.remove(key);
	}
}
