package com.lpmas.admin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.framework.bean.StatusBean;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.PropertiesKit;
import com.lpmas.framework.util.StatusKit;

public class AdminConfig {
	// APP ID
	public static final String APP_ID = "Admin";

	// 配置文件路径
	public static final String ADMIN_PROP_FILE_NAME = Constants.PROP_FILE_PATH + "/admin_config";

	// 页面路径
	public final static String PAGE_PATH = Constants.PAGE_PATH + "admin/";

	// 用户密码加密串
	public final static String PASSWORD_KEY = PropertiesKit.getBundleProperties(ADMIN_PROP_FILE_NAME, "PASSWORK_KEY");

	// 默认域
	public final static String ADMIN_DOMAIN = PropertiesKit.getBundleProperties(ADMIN_PROP_FILE_NAME, "ADMIN_DOMAIN");

	public final static String ADMIN_USER_KEY = "AdminUserKey";

	public final static int ADMIN_TYPE_COMMON = 1;// 普通用户
	public final static int ADMIN_TYPE_ADMIN = 2;// 管理员用户

	public static List<StatusBean<Integer, String>> ADMIN_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static HashMap<Integer, String> ADMIN_TYPE_MAP = new HashMap<Integer, String>();

	public final static int MENU_TYPE_DICTIONARY = 1;// 目录
	public final static int MENU_TYPE_ITEM = 2;// 菜单项

	public static List<StatusBean<Integer, String>> MENU_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
	public static HashMap<Integer, String> MENU_TYPE_MAP = new HashMap<Integer, String>();

	public final static String MENU_ROOT_CODE = "ROOT";// 菜单根目录代码

	static {
		new AdminConfig().init();
	}

	private void init() {
		initAdminTypeList();
		initAdminTypeMap();

		initMenuTypeList();
		initMenuTypeMap();
	}

	private void initAdminTypeList() {
		ADMIN_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		ADMIN_TYPE_LIST.add(new StatusBean<Integer, String>(ADMIN_TYPE_COMMON, "普通用户"));
		ADMIN_TYPE_LIST.add(new StatusBean<Integer, String>(ADMIN_TYPE_ADMIN, "管理员用户"));
	}

	private void initAdminTypeMap() {
		ADMIN_TYPE_MAP = StatusKit.toMap(ADMIN_TYPE_LIST);
	}

	private void initMenuTypeList() {
		MENU_TYPE_LIST = new ArrayList<StatusBean<Integer, String>>();
		MENU_TYPE_LIST.add(new StatusBean<Integer, String>(MENU_TYPE_DICTIONARY, "目录"));
		MENU_TYPE_LIST.add(new StatusBean<Integer, String>(MENU_TYPE_ITEM, "菜单项"));
	}

	private void initMenuTypeMap() {
		MENU_TYPE_MAP = StatusKit.toMap(MENU_TYPE_LIST);
	}
}
