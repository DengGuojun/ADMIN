package com.lpmas.declare.admin.business;

import java.util.HashMap;

import com.lpmas.declare.admin.bean.AdminUserInfoBean;
import com.lpmas.declare.admin.dao.AdminUserInfoDao;
import com.lpmas.framework.crypto.MD5;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class AdminUserInfoBusiness {
	public int addAdminUserInfo(AdminUserInfoBean bean) {
		AdminUserInfoDao dao = new AdminUserInfoDao();
		return dao.insertAdminUserInfo(bean);
	}

	public int updateAdminUserInfo(AdminUserInfoBean bean) {
		AdminUserInfoDao dao = new AdminUserInfoDao();
		return dao.updateAdminUserInfo(bean);
	}

	public AdminUserInfoBean getAdminUserInfoByKey(int userId) {
		AdminUserInfoDao dao = new AdminUserInfoDao();
		return dao.getAdminUserInfoByKey(userId);
	}

	public PageResultBean<AdminUserInfoBean> getAdminUserInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		AdminUserInfoDao dao = new AdminUserInfoDao();
		return dao.getAdminUserInfoPageListByMap(condMap, pageBean);
	}

	public String getCryptoPassword(String password) {
		password = MD5.getMD5(password);
		return password;
	}

}