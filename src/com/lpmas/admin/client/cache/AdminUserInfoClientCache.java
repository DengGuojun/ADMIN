package com.lpmas.admin.client.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminUserInfoBean;
import com.lpmas.admin.client.AdminServiceClient;
import com.lpmas.admin.config.AdminClientCacheConfig;
import com.lpmas.framework.cache.LocalCache;
import com.lpmas.framework.config.Constants;
import com.opensymphony.oscache.base.NeedsRefreshException;

public class AdminUserInfoClientCache {
	private static Logger log = LoggerFactory.getLogger(AdminUserInfoClientCache.class);

	public String getAdminUserNameByKey(int userId) {
		String adminUserName = "";
		Object obj = null;
		String key = AdminClientCacheConfig.getAdminUserNameByKey(userId);
		LocalCache localCache = LocalCache.getInstance();
		try {
			obj = localCache.get(key);
		} catch (NeedsRefreshException nre) {
			boolean updated = false;
			try {
				AdminServiceClient client = new AdminServiceClient();
				AdminUserInfoBean bean = client.getAdminUserInfoByKey(userId);

				if (bean != null) {
					obj = bean.getAdminUserName();
					localCache.set(key, obj, Constants.CACHE_TIME_1_HOUR);
					updated = true;
				}
			} catch (Exception e) {
				log.error("", e);
			} finally {
				// 缓存更新失败
				if (!updated) {
					// 取得一个老的版本
					obj = nre.getCacheContent();
					// 取消更新
					localCache.cancelUpdate(key);
				}
			}

		}
		if (obj != null) {
			adminUserName = (String) obj;
		}
		return adminUserName;
	}
}
