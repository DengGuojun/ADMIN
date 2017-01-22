package com.lpmas.admin.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminPrivilegeInfoBean;
import com.lpmas.admin.bean.AdminRoleGroupBean;
import com.lpmas.admin.bean.AdminRoleUserBean;
import com.lpmas.admin.cache.AdminOperationInfoCache;
import com.lpmas.admin.cache.AdminResourceInfoCache;
import com.lpmas.admin.dao.AdminPrivilegeInfoDao;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.util.MapKit;

public class AdminPrivilegeInfoBusiness {
	private static Logger log = LoggerFactory.getLogger(AdminPrivilegeInfoBusiness.class);

	public int addAdminPrivilegeInfo(AdminPrivilegeInfoBean bean) {
		AdminPrivilegeInfoDao dao = new AdminPrivilegeInfoDao();
		return dao.insertAdminPrivilegeInfo(bean);
	}

	public int removeAdminPrivilegeInfoByKey(int roleId, int resourceId, int operationId) {
		AdminPrivilegeInfoDao dao = new AdminPrivilegeInfoDao();
		return dao.deleteAdminPrivilegeInfoByKey(roleId, resourceId, operationId);
	}

	public int removeAdminPrivilegeInfoByRoleId(int roleId) {
		AdminPrivilegeInfoDao dao = new AdminPrivilegeInfoDao();
		return dao.deleteAdminPrivilegeInfoByRoleId(roleId);
	}

	public AdminPrivilegeInfoBean getAdminPrivilegeInfoByKey(int roleId, int resourceId, int operationId) {
		AdminPrivilegeInfoDao dao = new AdminPrivilegeInfoDao();
		return dao.getAdminPrivilegeInfoByKey(roleId, resourceId, operationId);
	}

	public List<AdminPrivilegeInfoBean> getAdminPrivilegeInfoListByRoleId(int roleId) {
		AdminPrivilegeInfoDao dao = new AdminPrivilegeInfoDao();
		return dao.getAdminPrivilegeInfoListByRoleId(roleId);
	}

	public List<AdminPrivilegeInfoBean> getAdminPrivilegeInfoListByRoleIdSet(HashSet<Integer> roleIdSet) {
		if (!roleIdSet.isEmpty()) {
			AdminPrivilegeInfoDao dao = new AdminPrivilegeInfoDao();
			return dao.getAdminPrivilegeInfoListByRoleIdSet(roleIdSet);
		} else {
			return new ArrayList<AdminPrivilegeInfoBean>();
		}

	}

	public boolean saveAdminPrivilegeInfo(int roleId, List<AdminPrivilegeInfoBean> list) {
		removeAdminPrivilegeInfoByRoleId(roleId);

		for (AdminPrivilegeInfoBean bean : list) {
			addAdminPrivilegeInfo(bean);
		}
		return true;
	}

	public HashSet<String> getAdminPrivilegeSetByUserId(int userId) {
		HashSet<String> set = new HashSet<String>();
		HashSet<Integer> roleIdSet = new HashSet<Integer>();
		// 获取用户拥有的角色
		AdminRoleUserBusiness roleUserBusiness = new AdminRoleUserBusiness();
		List<AdminRoleUserBean> roleUserList = roleUserBusiness.getAdminRoleUserListByUserId(userId,
				Constants.STATUS_VALID);
		for (AdminRoleUserBean roleUserBean : roleUserList) {
			roleIdSet.add(roleUserBean.getRoleId());
		}

		// 获取用户组拥有的角色
		AdminRoleGroupBusiness roleGroupBusiness = new AdminRoleGroupBusiness();
		List<AdminRoleGroupBean> roleGroupList = roleGroupBusiness.getAdminRoleGroupListByUserId(userId,
				Constants.STATUS_VALID);
		for (AdminRoleGroupBean roleGroupBean : roleGroupList) {
			roleIdSet.add(roleGroupBean.getRoleId());
		}
		log.info("roleSet userID[{}] = {}", userId, roleIdSet);

		List<AdminPrivilegeInfoBean> list = getAdminPrivilegeInfoListByRoleIdSet(roleIdSet);
		for (AdminPrivilegeInfoBean bean : list) {
			String key = AdminUtil.getPrivilegeCode(bean.getResourceId(), bean.getOperationId());
			set.add(key);
		}

		return set;
	}

	public HashSet<String> getAdminPrivilegeCodeSetByUserId(int userId) {
		HashSet<String> privilegeSet = getAdminPrivilegeSetByUserId(userId);
		return getAdminPrivilegeCodeSet(privilegeSet);
	}

	public HashSet<String> getAdminPrivilegeCodeSet(HashSet<String> privilegeSet) {
		HashSet<String> set = new HashSet<String>();
		// 处理用户权限
		AdminResourceInfoCache resourceInfoCache = new AdminResourceInfoCache();
		HashMap<Integer, String> resourceMap = resourceInfoCache.getAdminResourceCodeAllMap();

		AdminOperationInfoCache operationInfoCache = new AdminOperationInfoCache();
		HashMap<Integer, String> operationMap = operationInfoCache.getAdminOperationCodeAllMap();

		for (String privilegeCode : privilegeSet) {
			int[] array = AdminUtil.parsePrivilegeCode(privilegeCode);
			String key = AdminUtil.getPrivilegeCode(MapKit.getValueFromMap(array[0], resourceMap),
					MapKit.getValueFromMap(array[1], operationMap));
			set.add(key);

		}
		return set;
	}
}
