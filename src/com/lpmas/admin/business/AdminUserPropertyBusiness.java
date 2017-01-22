package com.lpmas.admin.business;

import java.util.List;

import com.lpmas.admin.bean.AdminUserPropertyBean;
import com.lpmas.admin.dao.AdminUserPropertyDao;

public class AdminUserPropertyBusiness {
	public int addAdminUserProperty(AdminUserPropertyBean bean) {
		AdminUserPropertyDao dao = new AdminUserPropertyDao();
		return dao.insertAdminUserProperty(bean);
	}

	public int updateAdminUserProperty(AdminUserPropertyBean bean) {
		AdminUserPropertyDao dao = new AdminUserPropertyDao();
		return dao.updateAdminUserProperty(bean);
	}

	public AdminUserPropertyBean getAdminUserPropertyByKey(int userId, int propertyCode) {
		AdminUserPropertyDao dao = new AdminUserPropertyDao();
		return dao.getAdminUserPropertyByKey(userId, propertyCode);
	}

	public List<AdminUserPropertyBean> getAdminUserPropertyListByUserId(int userId) {
		AdminUserPropertyDao dao = new AdminUserPropertyDao();
		return dao.getAdminUserPropertyListByUserId(userId);
	}
}
