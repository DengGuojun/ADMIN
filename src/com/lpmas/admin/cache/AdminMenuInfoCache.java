package com.lpmas.admin.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminMenuInfoBean;
import com.lpmas.admin.business.AdminMenuInfoBusiness;
import com.lpmas.admin.config.AdminCacheConfig;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.cache.LocalCache;
import com.lpmas.framework.cache.RemoteCache;
import com.lpmas.framework.config.Constants;

public class AdminMenuInfoCache {
	private static Logger log = LoggerFactory.getLogger(AdminMenuInfoCache.class);

	public List<AdminMenuInfoBean> getAdminMenuInfoListByParentMenuId(int parentMenuId) {
		List<AdminMenuInfoBean> list = null;
		String key = AdminCacheConfig.getAdminMenuInfoListByParentMenuIdKey(parentMenuId);

		RemoteCache remoteCache = RemoteCache.getInstance();
		Object obj = remoteCache.get(AdminConfig.APP_ID, key);
		if (obj != null) {
			list = (List<AdminMenuInfoBean>) obj;
		} else {
			AdminMenuInfoBusiness business = new AdminMenuInfoBusiness();
			list = business.getAdminMenuInfoListByParentMenuId(parentMenuId);
			if (list != null) {
				remoteCache.set(AdminConfig.APP_ID, key, list, Constants.CACHE_TIME_2_HOUR);
			}
		}
		return list;
	}

	public boolean refreshAdminMenuInfoListByParentMenuId(int parentMenuId) {
		String key = AdminCacheConfig.getAdminMenuInfoListByParentMenuIdKey(parentMenuId);
		RemoteCache remoteCache = RemoteCache.getInstance();
		return remoteCache.delete(AdminConfig.APP_ID, key);
	}

	public List<AdminMenuInfoBean> getAdminMenuInfoListByParentMenuId(int parentMenuId, int menuType) {
		List<AdminMenuInfoBean> list = null;
		String key = AdminCacheConfig.getAdminMenuInfoListByParentMenuIdKey(parentMenuId, menuType);

		RemoteCache remoteCache = RemoteCache.getInstance();
		Object obj = remoteCache.get(AdminConfig.APP_ID, key);
		if (obj != null) {
			list = (List<AdminMenuInfoBean>) obj;
		} else {
			AdminMenuInfoBusiness business = new AdminMenuInfoBusiness();
			list = business.getAdminMenuInfoListByParentMenuId(parentMenuId);
			if (list != null) {
				remoteCache.set(AdminConfig.APP_ID, key, list, Constants.CACHE_TIME_2_HOUR);
			}
		}

		return list;
	}

	public boolean refreshAdminMenuInfoListByParentMenuId(int parentMenuId, int menuType) {
		String key = AdminCacheConfig.getAdminMenuInfoListByParentMenuIdKey(parentMenuId, menuType);
		RemoteCache remoteCache = RemoteCache.getInstance();
		return remoteCache.delete(AdminConfig.APP_ID, key);
	}

	public List<AdminMenuInfoBean> getAdminMenuInfoListByParentMenuCode(String parentMenuCode) {
		List<AdminMenuInfoBean> list = null;
		String key = AdminCacheConfig.getAdminMenuInfoListByParentMenuCodeKey(parentMenuCode);

		RemoteCache remoteCache = RemoteCache.getInstance();
		Object obj = remoteCache.get(AdminConfig.APP_ID, key);
		if (obj != null) {
			list = (List<AdminMenuInfoBean>) obj;
		} else {
			AdminMenuInfoBusiness business = new AdminMenuInfoBusiness();
			list = business.getAdminMenuInfoListByParentMenuCode(parentMenuCode);
			if (list != null) {
				remoteCache.set(AdminConfig.APP_ID, key, list, Constants.CACHE_TIME_2_HOUR);
			}
		}

		return list;
	}

	public boolean refreshAdminMenuInfoListByParentMenuCode(String parentMenuCode) {
		String key = AdminCacheConfig.getAdminMenuInfoListByParentMenuCodeKey(parentMenuCode);
		RemoteCache remoteCache = RemoteCache.getInstance();
		return remoteCache.delete(AdminConfig.APP_ID, key);
	}
}
