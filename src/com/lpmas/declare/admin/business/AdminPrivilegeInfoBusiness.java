package com.lpmas.declare.admin.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.lpmas.declare.admin.bean.AdminPrivilegeInfoBean;
import com.lpmas.declare.admin.dao.AdminPrivilegeInfoDao;

public class AdminPrivilegeInfoBusiness {
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


}