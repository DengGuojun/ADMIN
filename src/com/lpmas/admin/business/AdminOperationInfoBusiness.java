package com.lpmas.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.admin.bean.AdminOperationInfoBean;
import com.lpmas.admin.cache.AdminOperationInfoCache;
import com.lpmas.admin.dao.AdminOperationInfoDao;

public class AdminOperationInfoBusiness {
	public int addAdminOperationInfo(AdminOperationInfoBean bean) {
		AdminOperationInfoDao dao = new AdminOperationInfoDao();
		int result = dao.insertAdminOperationInfo(bean);
		if (result > 0) {
			AdminOperationInfoCache cache = new AdminOperationInfoCache();
			cache.refreshAdminOperationNameAllMap();
			cache.refreshAdminOperationCodeAllMap();
		}
		return result;
	}

	public int updateAdminOperationInfo(AdminOperationInfoBean bean) {
		AdminOperationInfoDao dao = new AdminOperationInfoDao();
		int result = dao.updateAdminOperationInfo(bean);

		if (result > 0) {
			AdminOperationInfoCache cache = new AdminOperationInfoCache();
			cache.refreshAdminOperationNameAllMap();
			cache.refreshAdminOperationCodeAllMap();
		}
		return result;
	}

	public AdminOperationInfoBean getAdminOperationInfoByKey(int operationId) {
		AdminOperationInfoDao dao = new AdminOperationInfoDao();
		return dao.getAdminOperationInfoByKey(operationId);
	}

	public List<AdminOperationInfoBean> getAdminOperationInfoAllList() {
		AdminOperationInfoDao dao = new AdminOperationInfoDao();
		return dao.getAdminOperationInfoAllList();
	}

	public HashMap<Integer, String> getAdminOperationNameAllMap() {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		List<AdminOperationInfoBean> list = getAdminOperationInfoAllList();
		for (AdminOperationInfoBean bean : list) {
			map.put(bean.getOperationId(), bean.getOperationName());
		}
		return map;
	}

	public HashMap<Integer, String> getAdminOperationCodeAllMap() {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		List<AdminOperationInfoBean> list = getAdminOperationInfoAllList();
		for (AdminOperationInfoBean bean : list) {
			map.put(bean.getOperationId(), bean.getOperationCode());
		}
		return map;
	}
}
