package com.lpmas.admin.business;

import java.util.HashSet;
import java.util.List;

import com.lpmas.admin.bean.AdminGroupUserBean;
import com.lpmas.admin.cache.AdminGroupUserCache;
import com.lpmas.admin.cache.AdminPrivilegeInfoCache;
import com.lpmas.admin.dao.AdminGroupUserDao;
import com.lpmas.framework.config.Constants;

public class AdminGroupUserBusiness {
	public int addAdminGroupUser(AdminGroupUserBean bean) {
		AdminGroupUserDao dao = new AdminGroupUserDao();
		return dao.insertAdminGroupUser(bean);
	}

	public int removeAdminGroupUserByKey(int groupId, int userId) {
		AdminGroupUserDao dao = new AdminGroupUserDao();
		return dao.removeAdminGroupUserByKey(groupId, userId);
	}

	public int removeAdminGroupUserByUserId(int userId) {
		AdminGroupUserDao dao = new AdminGroupUserDao();
		return dao.removeAdminGroupUserByUserId(userId);
	}

	public List<AdminGroupUserBean> getAdminGroupUserListByUserId(int userId) {
		AdminGroupUserDao dao = new AdminGroupUserDao();
		return dao.getAdminGroupUserListByUserId(userId);
	}

	public List<AdminGroupUserBean> getAdminGroupUserListByUserId(int userId, int status) {
		AdminGroupUserDao dao = new AdminGroupUserDao();
		return dao.getAdminGroupUserListByUserId(userId, status);
	}

	public List<AdminGroupUserBean> getAdminGroupUserListByGroupId(int groupId) {
		AdminGroupUserDao dao = new AdminGroupUserDao();
		return dao.getAdminGroupUserListByGroupId(groupId);
	}

	public HashSet<Integer> getGroupSetByUserId(int userId) {
		HashSet<Integer> set = new HashSet<Integer>();
		List<AdminGroupUserBean> list = getAdminGroupUserListByUserId(userId);
		for (AdminGroupUserBean bean : list) {
			set.add(bean.getGroupId());
		}
		return set;
	}

	public HashSet<Integer> getGroupValidSetByUserId(int userId) {
		HashSet<Integer> set = new HashSet<Integer>();
		List<AdminGroupUserBean> list = getAdminGroupUserListByUserId(userId, Constants.STATUS_VALID);
		for (AdminGroupUserBean bean : list) {
			set.add(bean.getGroupId());
		}
		return set;
	}

	public boolean saveAdminGroupUser(int userId, int[] groupIds) {
		// 删除原来用户的角色
		removeAdminGroupUserByUserId(userId);

		// 增加用户的角色
		if (groupIds != null) {
			for (int groupId : groupIds) {
				AdminGroupUserBean bean = new AdminGroupUserBean();
				bean.setGroupId(groupId);
				bean.setUserId(userId);
				addAdminGroupUser(bean);
			}
		}

		AdminGroupUserCache gCache = new AdminGroupUserCache();
		gCache.refreshGroupValidSetByUserId(userId);

		AdminPrivilegeInfoCache pCache = new AdminPrivilegeInfoCache();
		pCache.refreshAdminPrivilegeSetByUserId(userId);
		return true;
	}
}
