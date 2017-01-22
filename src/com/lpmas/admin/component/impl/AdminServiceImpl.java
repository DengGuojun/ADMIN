package com.lpmas.admin.component.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminGroupInfoBean;
import com.lpmas.admin.bean.AdminUserInfoBean;
import com.lpmas.admin.cache.AdminGroupInfoCache;
import com.lpmas.admin.cache.AdminGroupUserCache;
import com.lpmas.admin.cache.AdminPrivilegeInfoCache;
import com.lpmas.admin.cache.AdminUserInfoCache;
import com.lpmas.admin.component._AdminServiceDisp;
import com.lpmas.admin.config.AdminClientConfig;
import com.lpmas.framework.util.JsonKit;

import Ice.Current;

public class AdminServiceImpl extends _AdminServiceDisp {
	private static Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 8506561092289629469L;

	@Override
	public String rpc(String method, String params, Current __current) {
		// log.info("rpc method : {}", method);
		String result = "";
		if (method.equals(AdminClientConfig.GET_ADMIN_USER_INFO_BY_KEY)) {
			result = getAdminUserInfoByKey(params);
		} else if (method.equals(AdminClientConfig.GET_ADMIN_GROUP_VALID_SET_BY_USER_ID)) {
			result = getAdminGroupValidSetByUserId(params);
		} else if (method.equals(AdminClientConfig.GET_ADMIN_PRIVILEGE_CODE_SET_BY_USER_ID)) {
			result = getAdminPrivilegeCodeSetByUserId(params);
		} else if (method.equals(AdminClientConfig.GET_ADMIN_GROUP_INFO_VALID_LIST)) {
			result = getAdminGroupInfoValidList(params);
		} else if (method.equals(AdminClientConfig.GET_ADMIN_GROUP_INFO_BY_KEY)) {
			result = getAdminGroupInfoByKey(params);
		}
		// log.info("result : {}", result);
		return result;
	}

	private String getAdminUserInfoByKey(String params) {
		AdminUserInfoCache cache = new AdminUserInfoCache();
		HashMap<String, String> paramMap = JsonKit.toBean(params, HashMap.class);
		AdminUserInfoBean bean = cache.getAdminUserInfoByKey(Integer.parseInt(paramMap.get("userId")));
		return JsonKit.toJson(bean);
	}

	private String getAdminGroupValidSetByUserId(String params) {
		AdminGroupUserCache cache = new AdminGroupUserCache();
		HashMap<String, String> paramMap = JsonKit.toBean(params, HashMap.class);
		HashSet<Integer> set = cache.getGroupValidSetByUserId(Integer.parseInt(paramMap.get("userId")));
		return JsonKit.toJson(set);
	}

	private String getAdminPrivilegeCodeSetByUserId(String params) {
		AdminPrivilegeInfoCache cache = new AdminPrivilegeInfoCache();
		HashMap<String, String> paramMap = JsonKit.toBean(params, HashMap.class);
		HashSet<String> set = cache.getAdminPrivilegeCodeSetByUserId(Integer.parseInt(paramMap.get("userId")));
		return JsonKit.toJson(set);
	}

	private String getAdminGroupInfoValidList(String params) {
		AdminGroupInfoCache cache = new AdminGroupInfoCache();
		List<AdminGroupInfoBean> list = cache.getAdminGroupInfoValidList();
		return JsonKit.toJson(list);
	}

	private String getAdminGroupInfoByKey(String params) {
		AdminGroupInfoCache cache = new AdminGroupInfoCache();
		HashMap<String, String> paramMap = JsonKit.toBean(params, HashMap.class);
		AdminGroupInfoBean bean = cache.getAdminGroupInfoByKey(Integer.parseInt(paramMap.get("groupId")));
		return JsonKit.toJson(bean);
	}
}
