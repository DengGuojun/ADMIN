package com.lpmas.declare.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.declare.admin.bean.AdminResourceTypeBean;
import com.lpmas.declare.admin.dao.AdminResourceTypeDao;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class AdminResourceTypeBusiness {
	public int addAdminResourceType(AdminResourceTypeBean bean) {
		AdminResourceTypeDao dao = new AdminResourceTypeDao();
		return dao.insertAdminResourceType(bean);
	}

	public int updateAdminResourceType(AdminResourceTypeBean bean) {
		AdminResourceTypeDao dao = new AdminResourceTypeDao();
		return dao.updateAdminResourceType(bean);
	}

	public AdminResourceTypeBean getAdminResourceTypeByKey(int typeId) {
		AdminResourceTypeDao dao = new AdminResourceTypeDao();
		return dao.getAdminResourceTypeByKey(typeId);
	}
	
	public List<AdminResourceTypeBean> getAdminResourceTypeAllList() {
		AdminResourceTypeDao dao = new AdminResourceTypeDao();
		return dao.getAdminResourceTypeAllList();
	}

	public HashMap<Integer, String> getAdminResourceTypeAllMap() {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		List<AdminResourceTypeBean> list = getAdminResourceTypeAllList();
		for (AdminResourceTypeBean bean : list) {
			map.put(bean.getTypeId(), bean.getTypeName());
		}
		return map;
	}

	public PageResultBean<AdminResourceTypeBean> getAdminResourceTypePageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		AdminResourceTypeDao dao = new AdminResourceTypeDao();
		return dao.getAdminResourceTypePageListByMap(condMap, pageBean);
	}

}