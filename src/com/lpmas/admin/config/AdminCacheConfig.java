package com.lpmas.admin.config;

public class AdminCacheConfig {
	public static final String ADMIN_USER_INFO_KEY = "ADMIN_USER_INFO_";
	public static final String ADMIN_GROUP_VALID_SET_BY_USER_ID_KEY = "ADMIN_GROUP_VALID_SET_BY_USER_ID_";
	public static final String ADMIN_USER_PRIVILEGE_KEY = "ADMIN_USER_PRIVILEGE_";
	public static final String ADMIN_USER_PRIVILEGE_CODE_KEY = "ADMIN_USER_PRIVILEGE_CODE_";
	public static final String ADMIN_OPERATION_NAME_ALL_MAP_KEY = "ADMIN_OPERATION_NAME_ALL_MAP_KEY";
	public static final String ADMIN_OPERATION_CODE_ALL_MAP_KEY = "ADMIN_OPERATION_CODE_ALL_MAP_KEY";
	public static final String ADMIN_RESOURCE_NAME_ALL_MAP_KEY = "ADMIN_RESOURCE_NAME_ALL_MAP_KEY";
	public static final String ADMIN_RESOURCE_CODE_ALL_MAP_KEY = "ADMIN_RESOURCE_CODE_ALL_MAP";
	public static final String ADMIN_GROUP_INFO_VALID_LIST_KEY = "ADMIN_GROUP_INFO_VALID_LIST_KEY";
	public static final String ADMIN_GROUP_INFO_KEY = "ADMIN_GROUP_INFO_";
	public static final String ADMIN_MENU_INFO_LIST_BY_PARENT_MENU_ID_KEY = "ADMIN_MENU_INFO_LIST_BY_PARENT_MENU_ID_";
	public static final String ADMIN_MENU_INFO_LIST_BY_PARENT_MENU_CODE_KEY = "ADMIN_MENU_INFO_LIST_BY_PARENT_MENU_CODE_";

	public static String getAdminUserInfoKey(int userId) {
		return ADMIN_USER_INFO_KEY + userId;
	}

	public static String getAdminGroupValidSetByUserIdKey(int userId) {
		return ADMIN_GROUP_VALID_SET_BY_USER_ID_KEY + userId;
	}

	public static String getAdminUserPrivilegeKey(int userId) {
		return ADMIN_USER_PRIVILEGE_KEY + userId;
	}

	public static String getAdminUserPrivilegeCodeKey(int userId) {
		return ADMIN_USER_PRIVILEGE_CODE_KEY + userId;
	}

	public static String getAdminGroupInfoKey(int groupId) {
		return ADMIN_GROUP_INFO_KEY + groupId;
	}

	public static String getAdminMenuInfoListByParentMenuIdKey(int parentMenuId) {
		return ADMIN_MENU_INFO_LIST_BY_PARENT_MENU_ID_KEY + parentMenuId;
	}

	public static String getAdminMenuInfoListByParentMenuIdKey(int parentMenuId, int menuType) {
		return ADMIN_MENU_INFO_LIST_BY_PARENT_MENU_ID_KEY + parentMenuId + "_" + menuType;
	}

	public static String getAdminMenuInfoListByParentMenuCodeKey(String parentMenuCode) {
		return ADMIN_MENU_INFO_LIST_BY_PARENT_MENU_CODE_KEY + parentMenuCode;
	}
}
