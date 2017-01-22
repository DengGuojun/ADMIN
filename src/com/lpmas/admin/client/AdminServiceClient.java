package com.lpmas.admin.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminGroupInfoBean;
import com.lpmas.admin.bean.AdminUserInfoBean;
import com.lpmas.admin.component.AdminServicePrx;
import com.lpmas.admin.config.AdminClientConfig;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.component.ComponentClient;
import com.lpmas.framework.util.JsonKit;

public class AdminServiceClient {
	private static Logger log = LoggerFactory.getLogger(AdminServiceClient.class);

	public AdminUserInfoBean getAdminUserInfoByKey(int userId) {
		AdminUserInfoBean bean = null;

		ComponentClient client = new ComponentClient();
		AdminServicePrx adminService = (AdminServicePrx) client.getProxy(AdminConfig.APP_ID, AdminServicePrx.class);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", String.valueOf(userId));
		String result = adminService.rpc(AdminClientConfig.GET_ADMIN_USER_INFO_BY_KEY, JsonKit.toJson(params));
		log.debug("getAdminUserInfoByKey : {}", result);
		bean = JsonKit.toBean(result, AdminUserInfoBean.class);
		return bean;
	}

	public HashSet<Integer> getAdminGroupValidSetByUserId(int userId) {
		HashSet<Integer> set = new HashSet<Integer>();

		ComponentClient client = new ComponentClient();
		AdminServicePrx adminService = (AdminServicePrx) client.getProxy(AdminConfig.APP_ID, AdminServicePrx.class);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", String.valueOf(userId));
		String result = adminService.rpc(AdminClientConfig.GET_ADMIN_GROUP_VALID_SET_BY_USER_ID, JsonKit.toJson(params));
		log.debug("getAdminGroupValidSetByUserId : {}", result);
		set = JsonKit.toBean(result, HashSet.class);
		return set;
	}

	public HashSet<String> getAdminPrivilegeCodeSetByUserId(int userId) {
		HashSet<String> set = new HashSet<String>();

		ComponentClient client = new ComponentClient();
		AdminServicePrx adminService = (AdminServicePrx) client.getProxy(AdminConfig.APP_ID, AdminServicePrx.class);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("userId", String.valueOf(userId));
		String result = adminService.rpc(AdminClientConfig.GET_ADMIN_PRIVILEGE_CODE_SET_BY_USER_ID, JsonKit.toJson(params));
		log.debug("getAdminPrivilegeCodeSetByUserId : {}", result);
		set = JsonKit.toBean(result, HashSet.class);
		return set;
	}

	public List<AdminGroupInfoBean> getAdminGroupInfoValidList() {
		List<AdminGroupInfoBean> list = new ArrayList<AdminGroupInfoBean>();

		ComponentClient client = new ComponentClient();
		AdminServicePrx adminService = (AdminServicePrx) client.getProxy(AdminConfig.APP_ID, AdminServicePrx.class);

		HashMap<String, String> params = new HashMap<String, String>();
		String result = adminService.rpc(AdminClientConfig.GET_ADMIN_GROUP_INFO_VALID_LIST, JsonKit.toJson(params));
		log.debug("getAdminGroupInfoValidList : {}", result);
		list = JsonKit.toList(result, AdminGroupInfoBean.class);
		return list;
	}

	public AdminGroupInfoBean getAdminGroupInfoByKey(int groupId) {
		AdminGroupInfoBean bean = null;

		ComponentClient client = new ComponentClient();
		AdminServicePrx adminService = (AdminServicePrx) client.getProxy(AdminConfig.APP_ID, AdminServicePrx.class);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("groupId", String.valueOf(groupId));
		String result = adminService.rpc(AdminClientConfig.GET_ADMIN_GROUP_INFO_BY_KEY, JsonKit.toJson(params));
		log.debug("getAdminGroupInfoByKey : {}", result);
		bean = JsonKit.toBean(result, AdminGroupInfoBean.class);
		return bean;
	}
}
