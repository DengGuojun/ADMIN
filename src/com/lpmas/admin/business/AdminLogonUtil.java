package com.lpmas.admin.business;

import java.security.Key;

import com.lpmas.framework.config.Constants;
import com.lpmas.framework.crypto.DES;
import com.lpmas.framework.util.PropertiesKit;

public class AdminLogonUtil {
	public static Key ADMIN_KEY = null;
	private static String ADMIN_KEY_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/admin_key";
	public final static String KEY_CONTENT = PropertiesKit.getBundleProperties(ADMIN_KEY_PROP_FILE_NAME, "KEY_CONTENT");

	static {
		initAdminKey();
	}

	public static void initAdminKey() {
		ADMIN_KEY = DES.getKey(KEY_CONTENT);
	}

	public static String encryptLogonSign(int userId) {
		return DES.encrypt(String.valueOf(userId), ADMIN_KEY);
	}

	public static String decryptLogonSign(String source) {
		return DES.decrypt(source, ADMIN_KEY);
	}
}
