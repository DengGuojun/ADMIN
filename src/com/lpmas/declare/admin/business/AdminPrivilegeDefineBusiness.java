package com.lpmas.declare.admin.business;

import java.util.List;

import com.lpmas.declare.admin.bean.AdminPrivilegeDefineBean;
import com.lpmas.declare.admin.dao.AdminPrivilegeDefineDao;

public class AdminPrivilegeDefineBusiness {
	public int addAdminPrivilegeDefine(AdminPrivilegeDefineBean bean) {
		AdminPrivilegeDefineDao dao = new AdminPrivilegeDefineDao();
		return dao.insertAdminPrivilegeDefine(bean);
	}

	public int updateAdminPrivilegeDefine(AdminPrivilegeDefineBean bean) {
		AdminPrivilegeDefineDao dao = new AdminPrivilegeDefineDao();
		return dao.updateAdminPrivilegeDefine(bean);
	}

	public int removeAdminPrivilegeDefineByKey(int resourceId, int operationId) {
		AdminPrivilegeDefineDao dao = new AdminPrivilegeDefineDao();
		return dao.removeAdminPrivilegeDefineByKey(resourceId, operationId);
	}

	public int removeAdminPrivilegeDefineByResourceId(int resourceId) {
		AdminPrivilegeDefineDao dao = new AdminPrivilegeDefineDao();
		return dao.removeAdminPrivilegeDefineByResourceId(resourceId);
	}

	public AdminPrivilegeDefineBean getAdminPrivilegeDefineByKey(int resourceId, int operationId) {
		AdminPrivilegeDefineDao dao = new AdminPrivilegeDefineDao();
		return dao.getAdminPrivilegeDefineByKey(resourceId, operationId);
	}

	public List<AdminPrivilegeDefineBean> getAdminPrivilegeDefineListByResourceId(int resourceId) {
		AdminPrivilegeDefineDao dao = new AdminPrivilegeDefineDao();
		return dao.getAdminPrivilegeDefineListByResourceId(resourceId);
	}

	public boolean saveAdminPrivilegeDefine(int resourceId, List<AdminPrivilegeDefineBean> list) {
		// 删除原来权限定义
		removeAdminPrivilegeDefineByResourceId(resourceId);

		// 增加权限定义
		for (AdminPrivilegeDefineBean bean : list) {
			addAdminPrivilegeDefine(bean);
		}

		return true;
	}

}