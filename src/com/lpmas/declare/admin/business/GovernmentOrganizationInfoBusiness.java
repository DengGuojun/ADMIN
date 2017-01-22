package com.lpmas.declare.admin.business;

import java.util.HashMap;

import com.lpmas.declare.admin.bean.GovernmentOrganizationInfoBean;
import com.lpmas.declare.admin.dao.GovernmentOrganizationInfoDao;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class GovernmentOrganizationInfoBusiness {
	public int addGovernmentOrganizationInfo(GovernmentOrganizationInfoBean bean) {
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.insertGovernmentOrganizationInfo(bean);
	}

	public int updateGovernmentOrganizationInfo(GovernmentOrganizationInfoBean bean) {
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.updateGovernmentOrganizationInfo(bean);
	}

	public GovernmentOrganizationInfoBean getGovernmentOrganizationInfoByKey(int organizationId) {
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.getGovernmentOrganizationInfoByKey(organizationId);
	}

	public PageResultBean<GovernmentOrganizationInfoBean> getGovernmentOrganizationInfoPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		GovernmentOrganizationInfoDao dao = new GovernmentOrganizationInfoDao();
		return dao.getGovernmentOrganizationInfoPageListByMap(condMap, pageBean);
	}

}