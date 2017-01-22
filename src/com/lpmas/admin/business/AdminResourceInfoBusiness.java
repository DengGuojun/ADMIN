package com.lpmas.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.admin.bean.AdminResourceInfoBean;
import com.lpmas.admin.cache.AdminResourceInfoCache;
import com.lpmas.admin.dao.AdminResourceInfoDao;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class AdminResourceInfoBusiness {
	public int addAdminResourceInfo(AdminResourceInfoBean bean) {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		int result = dao.insertAdminResourceInfo(bean);
		if(result>0){
			AdminResourceInfoCache cache = new AdminResourceInfoCache();
			cache.refreshAdminResourceNameAllMap();
			cache.refreshAdminResourceCodeAllMap();
		}
		return result;
	}

	public int updateAdminResourceInfo(AdminResourceInfoBean bean) {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		int result=  dao.updateAdminResourceInfo(bean);
		if(result>0){
			AdminResourceInfoCache cache = new AdminResourceInfoCache();
			cache.refreshAdminResourceNameAllMap();
			cache.refreshAdminResourceCodeAllMap();
		}
		return result;
	}

	public AdminResourceInfoBean getAdminResourceInfoByKey(int resourceId) {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		return dao.getAdminResourceInfoByKey(resourceId);
	}

	public AdminResourceInfoBean getAdminResourceInfoByCode(String resourceCode) {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		return dao.getAdminResourceInfoByCode(resourceCode);
	}

	public List<AdminResourceInfoBean> getAdminResourceInfoAllList() {
		AdminResourceInfoDao dao = new AdminResourceInfoDao();
		return dao.getAdminResourceInfoAllList();
	}

	public HashMap<Integer, String> getAdminResourceNameAllMap() {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		List<AdminResourceInfoBean> list = getAdminResourceInfoAllList();
		for (AdminResourceInfoBean bean : list) {
			map.put(bean.getResourceId(), bean.getResourceName());
		}
		return map;
	}

	public HashMap<Integer, String> getAdminResourceCodeAllMap() {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		List<AdminResourceInfoBean> list = getAdminResourceInfoAllList();
		for (AdminResourceInfoBean bean : list) {
			map.put(bean.getResourceId(), bean.getResourceCode());
		}
		return map;
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
