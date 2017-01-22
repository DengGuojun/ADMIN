package com.lpmas.admin.config;

import com.lpmas.framework.util.PropertiesKit;

public class AdminDBConfig {

	public static String DB_LINK_ADMIN_W = PropertiesKit.getBundleProperties(AdminConfig.ADMIN_PROP_FILE_NAME,
			"DB_LINK_ADMIN_W");

	public static String DB_LINK_ADMIN_R = PropertiesKit.getBundleProperties(AdminConfig.ADMIN_PROP_FILE_NAME,
			"DB_LINK_ADMIN_R");
}
