package com.lpmas.admin.cache;

import java.util.ArrayList;
import java.util.List;

import com.lpmas.admin.bean.AdminGroupInfoBean;
import com.lpmas.admin.business.AdminGroupInfoBusiness;
import com.lpmas.admin.config.AdminCacheConfig;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.cache.RemoteCache;
import com.lpmas.framework.config.Constants;

public class AdminGroupInfoCache {
	public List<AdminGroupInfoBean> getAdminGroupInfoValidList() {
		List<AdminGroupInfoBean> list = new ArrayList<AdminGroupInfoBean>();
		String key = AdminCacheConfig.ADMIN_GROUP_INFO_VALID_LIST_KEY;

		RemoteCache remoteCache = RemoteCache.getInstance();
		Object obj = remoteCache.get(AdminConfig.APP_ID, key);
		if (obj != null) {
			list = (List<AdminGroupInfoBean>) obj;
		} else {
			AdminGroupInfoBusiness business = new AdminGroupInfoBusiness();
			list = business.getAdminGroupInfoValidList();
			if (list != null) {
				remoteCache.set(AdminConfig.APP_ID, key, list, Constants.CACHE_TIME_2_HOUR);
			}
		}
		return list;
	}

	public boolean refreshAdminGroupInfoValidList() {
		String key = AdminCacheConfig.ADMIN_GROUP_INFO_VALID_LIST_KEY;
		RemoteCache remoteCache = RemoteCache.getInstance();
		return remoteCache.delete(AdminConfig.APP_ID, key);
	}

	public AdminGroupInfoBean getAdminGroupInfoByKey(int groupId) {
		AdminGroupInfoBean bean = new AdminGroupInfoBean();
		String key = AdminCacheConfig.getAdminGroupInfoKey(groupId);

		RemoteCache remoteCache = RemoteCache.getInstance();
		Object obj = remoteCache.get(AdminConfig.APP_ID, key);
		if (obj != null) {
			bean = (AdminGroupInfoBean) obj;
		} else {
			AdminGroupInfoBusiness business = new AdminGroupInfoBusiness();
			bean = business.getAdminGroupInfoByKey(groupId);
			if (bean != null) {
				remoteCache.set(AdminConfig.APP_ID, key, bean, Constants.CACHE_TIME_2_HOUR);
			}
		}
		return bean;
	}

	public boolean refreshAdminGroupInfoByKey(int groupId) {
		String key = AdminCacheConfig.getAdminGroupInfoKey(groupId);
		RemoteCache remoteCache = RemoteCache.getInstance();
		return remoteCache.delete(AdminConfig.APP_ID, key);
	}
}
