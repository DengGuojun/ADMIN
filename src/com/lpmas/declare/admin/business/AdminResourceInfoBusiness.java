package com.lpmas.declare.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.declare.admin.bean.AdminResourceInfoBean;
import com.lpmas.declare.admin.dao.AdminResourceInfoDao;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class AdminResourceInfoBusiness {
	public int addAdminResourceInfo(AdminResourceInfoBean bean) {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		return dao.insertAdminResourceInfo(bean);
	}

	public int updateAdminResourceInfo(AdminResourceInfoBean bean) {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		return dao.updateAdminResourceInfo(bean);
	}

	public AdminResourceInfoBean getAdminResourceInfoByKey(int resourceId) {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		return dao.getAdminResourceInfoByKey(resourceId);
	}
	
	public AdminResourceInfoBean getAdminResourceInfoByCode(String resourceCode) {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		return dao.getAdminResourceInfoByCode(resourceCode);
	}
	
	public List<AdminResourceInfoBean> getAdminResourceInfoListByType(int typeId) {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		return dao.getAdminResourceInfoListByType(typeId);
	}

	public PageResultBean<AdminResourceInfoBean> getAdminResourceInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		return dao.getAdminResourceInfoPageListByMap(condMap, pageBean);
	}
	
	public boolean isDuplicateResourceCode(int resourceId, String resourceCode) {
		AdminResourceInfoBean bean = getAdminResourceInfoByCode(resourceCode);
		if (bean == null) {
			return false;
		} else {
			if (resourceId > 0 && resourceId == bean.getResourceId()) {
				return false;
			}
		}
		return true;
	}

}