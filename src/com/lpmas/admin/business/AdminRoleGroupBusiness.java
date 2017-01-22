package com.lpmas.admin.business;

import java.util.HashSet;
import java.util.List;

import com.lpmas.admin.bean.AdminRoleGroupBean;
import com.lpmas.admin.dao.AdminRoleGroupDao;

public class AdminRoleGroupBusiness {
	public int addAdminRoleGroup(AdminRoleGroupBean bean) {
		AdminRoleGroupDao dao = new AdminRoleGroupDao();
		return dao.insertAdminRoleGroup(bean);
	}

	public int removeAdminRoleGroupByKey(int roleId, int groupId) {
		AdminRoleGroupDao dao = new AdminRoleGroupDao();
		return dao.removeAdminRoleGroupByKey(roleId, groupId);
	}

	public int removeAdminRoleGroupByGroupId(int groupId) {
		AdminRoleGroupDao dao = new AdminRoleGroupDao();
		return dao.removeAdminRoleGroupByGroupId(groupId);
	}

	public List<AdminRoleGroupBean> getAdminRoleGroupListByRoleId(int roleId) {
		AdminRoleGroupDao dao = new AdminRoleGroupDao();
		return dao.getAdminRoleGroupListByRoleId(roleId);
	}

	public List<AdminRoleGroupBean> getAdminRoleGroupListByGroupId(int groupId) {
		AdminRoleGroupDao dao = new AdminRoleGroupDao();
		return dao.getAdminRoleGroupListByGroupId(groupId);
	}

	public List<AdminRoleGroupBean> getAdminRoleGroupListByUserId(int userId, int status) {
		AdminRoleGroupDao dao = new AdminRoleGroupDao();
		return dao.getAdminRoleGroupListByUserId(userId, status);
	}

	public HashSet<Integer> getRoleSetByGroupId(int groupId) {
		HashSet<Integer> set = new HashSet<Integer>();
		List<AdminRoleGroupBean> list = getAdminRoleGroupListByGroupId(groupId);
		for (AdminRoleGroupBean bean : list) {
			set.add(bean.getRoleId());
		}
		return set;
	}

	public boolean saveAdminRoleGroup(int groupId, int[] roleIds) {
		// 删除原来用户组的角色
		removeAdminRoleGroupByGroupId(groupId);

		// 增加用户组的角色
		if (roleIds != null) {
			for (int roleId : roleIds) {
				AdminRoleGroupBean bean = new AdminRoleGroupBean();
				bean.setRoleId(roleId);
				bean.setGroupId(groupId);
				addAdminRoleGroup(bean);
			}
		}

		return true;
	}
}
