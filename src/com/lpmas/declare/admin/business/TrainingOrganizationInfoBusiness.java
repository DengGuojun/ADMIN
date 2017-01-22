package com.lpmas.declare.admin.business;

import java.util.HashMap;

import com.lpmas.declare.admin.bean.TrainingOrganizationInfoBean;
import com.lpmas.declare.admin.dao.TrainingOrganizationInfoDao;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class TrainingOrganizationInfoBusiness {
	public int addTrainingOrganizationInfo(TrainingOrganizationInfoBean bean) {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		return dao.insertTrainingOrganizationInfo(bean);
	}

	public int updateTrainingOrganizationInfo(TrainingOrganizationInfoBean bean) {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		return dao.updateTrainingOrganizationInfo(bean);
	}

	public TrainingOrganizationInfoBean getTrainingOrganizationInfoByKey(int organizationId) {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		return dao.getTrainingOrganizationInfoByKey(organizationId);
	}

	public PageResultBean<TrainingOrganizationInfoBean> getTrainingOrganizationInfoPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		TrainingOrganizationInfoDao dao = new TrainingOrganizationInfoDao();
		return dao.getTrainingOrganizationInfoPageListByMap(condMap, pageBean);
	}

}