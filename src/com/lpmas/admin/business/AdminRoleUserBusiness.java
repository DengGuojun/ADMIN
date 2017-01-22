package com.lpmas.admin.business;

import java.util.HashSet;
import java.util.List;

import com.lpmas.admin.bean.AdminRoleUserBean;
import com.lpmas.admin.cache.AdminPrivilegeInfoCache;
import com.lpmas.admin.dao.AdminRoleUserDao;

public class AdminRoleUserBusiness {
	public int addAdminRoleUser(AdminRoleUserBean bean) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.insertAdminRoleUser(bean);
	}

	public int removeAdminRoleUserByKey(int roleId, int userId) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.removeAdminRoleUserByKey(roleId, userId);
	}

	public int removeAdminRoleUserByUserId(int userId) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.removeAdminRoleUserByUserId(userId);
	}

	public List<AdminRoleUserBean> getAdminRoleUserListByUserId(int userId) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.getAdminRoleUserListByUserId(userId);
	}

	public List<AdminRoleUserBean> getAdminRoleUserListByUserId(int userId, int status) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.getAdminRoleUserListByUserId(userId, status);
	}

	public List<AdminRoleUserBean> getAdminRoleUserListByRoleId(int roleId) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.getAdminRoleUserListByRoleId(roleId);
	}

	public HashSet<Integer> getRoleSetByUserId(int userId) {
		HashSet<Integer> set = new HashSet<Integer>();
		List<AdminRoleUserBean> list = getAdminRoleUserListByUserId(userId);
		for (AdminRoleUserBean bean : list) {
			set.add(bean.getRoleId());
		}
		return set;
	}

	public boolean saveAdminRoleUser(int userId, int[] roleIds) {
		// 删除原来用户的角色
		removeAdminRoleUserByUserId(userId);

		// 增加用户的角色
		if (roleIds != null) {
			for (int roleId : roleIds) {
				AdminRoleUserBean bean = new AdminRoleUserBean();
				bean.setRoleId(roleId);
				bean.setUserId(userId);
				addAdminRoleUser(bean);
			}
		}

		AdminPrivilegeInfoCache cache = new AdminPrivilegeInfoCache();
		cache.refreshAdminPrivilegeSetByUserId(userId);
		return true;
	}
}
