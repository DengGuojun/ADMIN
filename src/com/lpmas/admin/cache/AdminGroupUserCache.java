package com.lpmas.admin.cache;

import java.util.HashSet;

import com.lpmas.admin.business.AdminGroupUserBusiness;
import com.lpmas.admin.config.AdminCacheConfig;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.cache.RemoteCache;
import com.lpmas.framework.config.Constants;

public class AdminGroupUserCache {
	public HashSet<Integer> getGroupValidSetByUserId(int userId) {
		HashSet<Integer> set = new HashSet<Integer>();
		String key = AdminCacheConfig.getAdminGroupValidSetByUserIdKey(userId);

		RemoteCache remoteCache = RemoteCache.getInstance();
		Object obj = remoteCache.get(AdminConfig.APP_ID, key);
		if (obj != null) {
			set = (HashSet<Integer>) obj;
		} else {
			AdminGroupUserBusiness business = new AdminGroupUserBusiness();
			set = business.getGroupValidSetByUserId(userId);
			if (set != null) {
				remoteCache.set(AdminConfig.APP_ID, key, set, Constants.CACHE_TIME_2_HOUR);
			}
		}
		return set;
	}

	public boolean refreshGroupValidSetByUserId(int userId) {
		String key = AdminCacheConfig.getAdminGroupValidSetByUserIdKey(userId);
		RemoteCache remoteCache = RemoteCache.getInstance();
		return remoteCache.delete(AdminConfig.APP_ID, key);
	}
}
