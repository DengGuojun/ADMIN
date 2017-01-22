package com.lpmas.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.admin.bean.AdminGroupInfoBean;
import com.lpmas.admin.cache.AdminGroupInfoCache;
import com.lpmas.admin.dao.AdminGroupInfoDao;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class AdminGroupInfoBusiness {
	public int addAdminGroupInfo(AdminGroupInfoBean bean) {
		AdminGroupInfoDao dao = new AdminGroupInfoDao();
		int result = dao.insertAdminGroupInfo(bean);
		if (result > 0) {
			AdminGroupInfoCache cache = new AdminGroupInfoCache();
			cache.refreshAdminGroupInfoValidList();
			cache.refreshAdminGroupInfoByKey(bean.getGroupId());
		}
		return result;
	}

	public int updateAdminGroupInfo(AdminGroupInfoBean bean) {
		AdminGroupInfoDao dao = new AdminGroupInfoDao();
		int result = dao.updateAdminGroupInfo(bean);
		if (result > 0) {
			AdminGroupInfoCache cache = new AdminGroupInfoCache();
			cache.refreshAdminGroupInfoValidList();
			cache.refreshAdminGroupInfoByKey(bean.getGroupId());
		}
		return result;
	}

	public AdminGroupInfoBean getAdminGroupInfoByKey(int groupId) {
		AdminGroupInfoDao dao = new AdminGroupInfoDao();
		return dao.getAdminGroupInfoByKey(groupId);
	}

	public List<AdminGroupInfoBean> getAdminGroupInfoAllList() {
		AdminGroupInfoDao dao = new AdminGroupInfoDao();
		return dao.getAdminGroupInfoAllList();
	}

	public List<AdminGroupInfoBean> getAdminGroupInfoValidList() {
		AdminGroupInfoDao dao = new AdminGroupInfoDao();
		return dao.getAdminGroupInfoListByStatus(Constants.STATUS_VALID);
	}

	public PageResultBean<AdminGroupInfoBean> getAdminGroupInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		AdminGroupInfoDao dao = new AdminGroupInfoDao();
		return dao.getAdminGroupInfoPageListByMap(condMap, pageBean);
	}
}
