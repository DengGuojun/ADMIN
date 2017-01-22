package com.lpmas.admin.business;

import java.util.HashMap;

import com.lpmas.admin.bean.AdminUserInfoBean;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.dao.AdminUserInfoDao;
import com.lpmas.framework.config.Constants;
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

	public AdminUserInfoBean getAdminUserInfoByLoginId(String loginId) {
		AdminUserInfoDao dao = new AdminUserInfoDao();
		return dao.getAdminUserInfoByLoginId(loginId);
	}

	public PageResultBean<AdminUserInfoBean> getAdminUserInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		AdminUserInfoDao dao = new AdminUserInfoDao();
		return dao.getAdminUserInfoPageListByMap(condMap, pageBean);
	}

	public boolean isDuplicateLoginId(int userId, String loginId) {
		AdminUserInfoBean bean = getAdminUserInfoByLoginId(loginId);
		if (bean == null) {
			return false;
		} else {
			if (userId > 0 && userId == bean.getUserId()) {
				return false;
			}
		}
		return true;
	}

	public AdminUserInfoBean isValidAdminUser(String loginId, String loginPassword) {
		AdminUserInfoBean bean = getAdminUserInfoByLoginId(loginId);
		if (bean == null || bean.getUserId() <= 0 || bean.getStatus() == Constants.STATUS_NOT_VALID) {
			return null;
		}

		if (bean.getLoginPassword().equals(getCryptoPassword(loginPassword))) {
			return bean;
		}
		return null;
	}

	public String getCryptoPassword(String password) {
		password = MD5.getMD5(password, AdminConfig.PASSWORD_KEY);
		return password;
	}
}
