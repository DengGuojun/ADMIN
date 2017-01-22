package com.lpmas.declare.admin.config;

import java.util.HashSet;
import java.util.Set;

import com.lpmas.framework.util.PropertiesKit;
import com.lpmas.framework.util.SetKit;
import com.lpmas.framework.util.StringKit;

public class DeclareImageAdminConfig {

	public static final int MAX_SIZE = 1024 * 1024; // 设置上传文件最大为512K
	public static final String ALLOWED_FILE_TYPE = "jpg,jpeg,gif,png";// 设置允许上传的文件类型
	public static Set<String> ALLOWED_FILE_TYPE_SET = new HashSet<String>();
	// 临时文件路径
	public static final String TEMP_FILE_PATH = PropertiesKit
			.getBundleProperties(DeclareAdminConfig.DECLARE_PROP_FILE_NAME, "TEMP_FILE_PATH");

	// 是否删除临时文件
	public static boolean IS_DELETE_TEMP_FILE = true;

	// 默认删除
	static {
		String isDeleteTempFileStr = PropertiesKit.getBundleProperties(DeclareAdminConfig.DECLARE_PROP_FILE_NAME,
				"IS_DELETE_TEMP_FILE");
		if (StringKit.isValid(isDeleteTempFileStr)) {
			isDeleteTempFileStr.trim().toLowerCase();
			if (isDeleteTempFileStr.equals("true")) {
				IS_DELETE_TEMP_FILE = true;
			} else if (isDeleteTempFileStr.equals("false")) {
				IS_DELETE_TEMP_FILE = false;
			}
		}
		ALLOWED_FILE_TYPE_SET = SetKit.string2Set(ALLOWED_FILE_TYPE, ",");
	}

}
