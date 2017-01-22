package com.lpmas.admin.cache;

import java.util.HashSet;

import com.lpmas.admin.business.AdminPrivilegeInfoBusiness;
import com.lpmas.admin.config.AdminCacheConfig;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.cache.RemoteCache;
import com.lpmas.framework.config.Constants;

public class AdminPrivilegeInfoCache {
	public HashSet<String> getAdminPrivilegeSetByUserId(int userId) {
		HashSet<String> set = new HashSet<String>();
		String key = AdminCacheConfig.getAdminUserPrivilegeKey(userId);

		RemoteCache remoteCache = RemoteCache.getInstance();
		Object obj = remoteCache.get(AdminConfig.APP_ID, key);
		if (obj != null) {
			set = (HashSet<String>) obj;
		} else {
			AdminPrivilegeInfoBusiness business = new AdminPrivilegeInfoBusiness();
			set = business.getAdminPrivilegeSetByUserId(userId);
			if (set != null) {
				remoteCache.set(AdminConfig.APP_ID, key, set, Constants.CACHE_TIME_2_HOUR);
			}
		}
		return set;
	}

	public boolean refreshAdminPrivilegeSetByUserId(int userId) {
		String key = AdminCacheConfig.getAdminUserPrivilegeKey(userId);
		RemoteCache remoteCache = RemoteCache.getInstance();
		return remoteCache.delete(AdminConfig.APP_ID, key);
	}

	public HashSet<String> getAdminPrivilegeCodeSetByUserId(int userId) {
		HashSet<String> set = new HashSet<String>();
		String key = AdminCacheConfig.getAdminUserPrivilegeCodeKey(userId);

		RemoteCache remoteCache = RemoteCache.getInstance();
		Object obj = remoteCache.get(AdminConfig.APP_ID, key);
		if (obj != null) {
			set = (HashSet<String>) obj;
		} else {
			AdminPrivilegeInfoBusiness business = new AdminPrivilegeInfoBusiness();
			set = business.getAdminPrivilegeCodeSetByUserId(userId);
			if (set != null) {
				remoteCache.set(AdminConfig.APP_ID, key, set, Constants.CACHE_TIME_2_HOUR);
			}
		}
		return set;
	}

	public boolean refreshAdminPrivilegeCodeSetByUserId(int userId) {
		String key = AdminCacheConfig.getAdminUserPrivilegeCodeKey(userId);
		RemoteCache remoteCache = RemoteCache.getInstance();
		return remoteCache.delete(AdminConfig.APP_ID, key);
	}
}
