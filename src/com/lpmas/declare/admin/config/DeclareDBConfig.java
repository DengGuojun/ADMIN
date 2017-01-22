package com.lpmas.declare.admin.config;

import com.lpmas.framework.util.PropertiesKit;

public class DeclareDBConfig {

	public static String DB_LINK_DECLARE_W = PropertiesKit.getBundleProperties(DeclareAdminConfig.DECLARE_PROP_FILE_NAME,
			"DB_LINK_DECLARE_W");

	public static String DB_LINK_DECLARE_R = PropertiesKit.getBundleProperties(DeclareAdminConfig.DECLARE_PROP_FILE_NAME,
			"DB_LINK_DECLARE_R");

}
