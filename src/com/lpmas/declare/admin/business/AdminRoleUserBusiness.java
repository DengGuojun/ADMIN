package com.lpmas.declare.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.declare.admin.bean.AdminRoleUserBean;
import com.lpmas.declare.admin.dao.AdminRoleUserDao;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class AdminRoleUserBusiness {
	public int addAdminRoleUser(AdminRoleUserBean bean) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.insertAdminRoleUser(bean);
	}

	public int updateAdminRoleUser(AdminRoleUserBean bean) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.updateAdminRoleUser(bean);
	}

	public AdminRoleUserBean getAdminRoleUserByKey(int roleId, int userId) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.getAdminRoleUserByKey(roleId, userId);
	}

	public PageResultBean<AdminRoleUserBean> getAdminRoleUserPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.getAdminRoleUserPageListByMap(condMap, pageBean);
	}
	
	public List<AdminRoleUserBean> getAdminRoleUserListByUserId(int userId) {
		AdminRoleUserDao dao = new AdminRoleUserDao();
		return dao.getAdminRoleUserListByUserId(userId);
	}

}