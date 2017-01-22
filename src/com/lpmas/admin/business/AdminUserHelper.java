package com.lpmas.admin.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lpmas.admin.bean.AdminGroupInfoBean;
import com.lpmas.admin.bean.AdminUserInfoBean;
import com.lpmas.admin.client.AdminServiceClient;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.CookiesKit;
import com.lpmas.framework.web.HttpResponseKit;

public class AdminUserHelper {
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;

	private int userId = 0;
	private AdminUserInfoBean userInfo = null;

	private List<AdminGroupInfoBean> userGroupList = null;
	private HashMap<Integer, AdminGroupInfoBean> userGroupMap = null;
	private HashMap<Integer, String> userGroupNameMap = null;
	private HashSet<Integer> userGroupSet = null;
	private HashSet<String> userPrivilegeCodeSet = null;

	public AdminUserHelper(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public AdminUserHelper(HttpServletRequest request) {
		this.request = request;
	}

	public AdminUserHelper(int userId) {
		this.userId = userId;
	}

	public boolean isAdminUserLogon() {
		String key = CookiesKit.getCookies(request, AdminConfig.ADMIN_USER_KEY);
		if (getAdminUserId(key) > 0) {
			return true;
		}
		return false;
	}

	private int getAdminUserId(String key) {
		int result = 0;
		if (StringKit.isValid(key)) {
			result = Integer.parseInt(AdminLogonUtil.decryptLogonSign(key));
		}
		return result;
	}

	public int getAdminUserId() {
		if (userId <= 0) {
			String key = CookiesKit.getCookies(request, AdminConfig.ADMIN_USER_KEY);
			userId = getAdminUserId(key);
		}
		return userId;
	}

	public AdminUserInfoBean getAdminUserInfo() {
		if (userInfo == null) {
			if (getAdminUserId() > 0) {
				AdminServiceClient client = new AdminServiceClient();
				userInfo = client.getAdminUserInfoByKey(getAdminUserId());
			}
		}
		return userInfo;
	}

	public boolean isSuperAdminUser() {
		boolean result = false;
		AdminUserInfoBean bean = getAdminUserInfo();
		if (bean.getAdminUserType() == AdminConfig.ADMIN_TYPE_ADMIN) {
			result = true;
		}

		return result;
	}

	public HashSet<Integer> getUserGroupSet() {
		if (userGroupSet == null) {
			AdminServiceClient client = new AdminServiceClient();
			userGroupSet = client.getAdminGroupValidSetByUserId(getAdminUserId());
		}
		return userGroupSet;
	}

	public List<AdminGroupInfoBean> getUserGroupList() {
		if (userGroupList == null) {
			AdminServiceClient client = new AdminServiceClient();
			List<AdminGroupInfoBean> groupList = client.getAdminGroupInfoValidList();
			if (isSuperAdminUser()) {
				userGroupList = groupList;
			} else {
				userGroupList = new ArrayList<AdminGroupInfoBean>();
				HashSet<Integer> groupSet = getUserGroupSet();
				for (AdminGroupInfoBean bean : groupList) {
					if (groupSet.contains(bean.getGroupId())) {
						userGroupList.add(bean);
					}
				}
			}
		}
		return userGroupList;
	}

	public HashMap<Integer, AdminGroupInfoBean> getUserGroupMap() {
		if (userGroupMap == null) {
			userGroupMap = new HashMap<Integer, AdminGroupInfoBean>();
			List<AdminGroupInfoBean> list = getUserGroupList();
			for (AdminGroupInfoBean bean : list) {
				userGroupMap.put(bean.getGroupId(), bean);
			}
		}
		return userGroupMap;
	}

	public HashMap<Integer, String> getUserGroupNameMap() {
		if (userGroupNameMap == null) {
			userGroupNameMap = new HashMap<Integer, String>();
			List<AdminGroupInfoBean> list = getUserGroupList();
			for (AdminGroupInfoBean bean : list) {
				userGroupNameMap.put(bean.getGroupId(), bean.getGroupName());
			}
		}
		return userGroupNameMap;
	}

	public boolean hasUserGroupPermission(int groupId) {
		if (isSuperAdminUser()) {
			return true;
		}

		if (getUserGroupSet().contains(groupId)) {
			return true;
		} else {
			return false;
		}
	}

	public HashSet<String> getUserPrivilegeCodeSet() {
		if (userPrivilegeCodeSet == null) {
			AdminServiceClient client = new AdminServiceClient();
			userPrivilegeCodeSet = client.getAdminPrivilegeCodeSetByUserId(getAdminUserId());
		}
//		System.out.println(userPrivilegeCodeSet.toString());
		return userPrivilegeCodeSet;
	}

	public boolean hasPermission(String resourceCode, String operationCode) {
		if (isSuperAdminUser()) {
			return true;
		}

		String key = AdminUtil.getPrivilegeCode(resourceCode, operationCode);
		if (getUserPrivilegeCodeSet().contains(key)) {
			return true;
		}
		return false;
	}

	public boolean hasAnyPermission(String[][] permissionArray) {
		if (isSuperAdminUser()) {
			return true;
		}

		for (int i = 0; i < permissionArray.length; i++) {
			String key = AdminUtil.getPrivilegeCode(permissionArray[i][0], permissionArray[i][1]);
			if (getUserPrivilegeCodeSet().contains(key)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAnyPermission(List<String[]> permissionList) {
		if (isSuperAdminUser()) {
			return true;
		}

		for (String[] permissionArray : permissionList) {
			String key = AdminUtil.getPrivilegeCode(permissionArray[0], permissionArray[1]);
			if (getUserPrivilegeCodeSet().contains(key)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkPermission(String resourceCode, String operationCode) {
		if (!hasPermission(resourceCode, operationCode)) {
			HttpResponseKit.alertMessage(response, "你没有该功能的操作权限！", HttpResponseKit.ACTION_HISTORY_BACK);
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkUserGroupPermission(int groupId) {
		if (!hasUserGroupPermission(groupId)) {
			HttpResponseKit.alertMessage(response, "你没有该用户组功能的操作权限！", HttpResponseKit.ACTION_HISTORY_BACK);
			return false;
		} else {
			return true;
		}
	}

}
