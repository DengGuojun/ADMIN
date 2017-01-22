package com.lpmas.declare.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.declare.admin.bean.AdminOperationInfoBean;
import com.lpmas.declare.admin.dao.AdminOperationInfoDao;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class AdminOperationInfoBusiness {
	public int addAdminOperationInfo(AdminOperationInfoBean bean) {
		AdminOperationInfoDao dao = new AdminOperationInfoDao();
		return dao.insertAdminOperationInfo(bean);
	}

	public int updateAdminOperationInfo(AdminOperationInfoBean bean) {
		AdminOperationInfoDao dao = new AdminOperationInfoDao();
		return dao.updateAdminOperationInfo(bean);
	}

	public AdminOperationInfoBean getAdminOperationInfoByKey(int operationId) {
		AdminOperationInfoDao dao = new AdminOperationInfoDao();
		return dao.getAdminOperationInfoByKey(operationId);
	}

	public PageResultBean<AdminOperationInfoBean> getAdminOperationInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		AdminOperationInfoDao dao = new AdminOperationInfoDao();
		return dao.getAdminOperationInfoPageListByMap(condMap, pageBean);
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