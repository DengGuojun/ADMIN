package com.lpmas.admin.config;

public class AdminClientCacheConfig {
	public static final String ADMIN_USER_NAME_BY_KEY = "ADMIN_USER_NAME_";

	public static String getAdminUserNameByKey(int userId) {
		return ADMIN_USER_NAME_BY_KEY + userId;
	}
}
