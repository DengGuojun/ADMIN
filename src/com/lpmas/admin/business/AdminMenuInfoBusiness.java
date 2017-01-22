package com.lpmas.admin.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lpmas.admin.bean.AdminMenuInfoBean;
import com.lpmas.admin.cache.AdminMenuInfoCache;
import com.lpmas.admin.config.AdminConfig;
import com.lpmas.admin.dao.AdminMenuInfoDao;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;

public class AdminMenuInfoBusiness {
	public int addAdminMenuInfo(AdminMenuInfoBean bean) {
		AdminMenuInfoDao dao = new AdminMenuInfoDao();
		int result = dao.insertAdminMenuInfo(bean);
		if (result > 0) {
			refreshCache(bean);
		}
		return result;
	}

	public int updateAdminMenuInfo(AdminMenuInfoBean bean) {
		AdminMenuInfoDao dao = new AdminMenuInfoDao();
		int result = dao.updateAdminMenuInfo(bean);
		if (result > 0) {
			refreshCache(bean);
		}
		return result;
	}

	private void refreshCache(AdminMenuInfoBean bean) {
		AdminMenuInfoCache cache = new AdminMenuInfoCache();
		cache.refreshAdminMenuInfoListByParentMenuId(bean.getParentMenuId());
		cache.refreshAdminMenuInfoListByParentMenuId(bean.getParentMenuId(), bean.getMenuType());

		String parentMenuCode = AdminConfig.MENU_ROOT_CODE;
		if (bean.getParentMenuId() > 0) {
			AdminMenuInfoBean parentBean = getAdminMenuInfoByKey(bean.getParentMenuId());
			parentMenuCode = parentBean.getMenuCode();
		}
		cache.refreshAdminMenuInfoListByParentMenuCode(parentMenuCode);
	}

	public AdminMenuInfoBean getAdminMenuInfoByKey(int menuId) {
		AdminMenuInfoDao dao = new AdminMenuInfoDao();
		return dao.getAdminMenuInfoByKey(menuId);
	}

	public AdminMenuInfoBean getAdminMenuInfoByMenuCode(String menuCode) {
		AdminMenuInfoDao dao = new AdminMenuInfoDao();
		return dao.getAdminMenuInfoByMenuCode(menuCode);
	}

	public List<AdminMenuInfoBean> getAdminMenuInfoListByParentMenuId(int parentMenuId) {
		AdminMenuInfoDao dao = new AdminMenuInfoDao();
		return dao.getAdminMenuInfoListByParentMenuId(parentMenuId);
	}

	public List<AdminMenuInfoBean> getAdminMenuInfoListByParentMenuId(int parentMenuId, int menuType) {
		AdminMenuInfoDao dao = new AdminMenuInfoDao();
		return dao.getAdminMenuInfoListByParentMenuId(parentMenuId, menuType);
	}

	public List<AdminMenuInfoBean> getAdminMenuInfoListByParentMenuCode(String menuCode) {
		AdminMenuInfoDao dao = new AdminMenuInfoDao();
		return dao.getAdminMenuInfoListByParentMenuCode(menuCode);
	}

	public List<String> getMenuPrivilegeListByParentMenuId(int menuId) {
		List<String> list = new ArrayList<String>();

		List<AdminMenuInfoBean> menuList = getAdminMenuInfoListByParentMenuId(menuId);
		for (AdminMenuInfoBean bean : menuList) {
			if (bean.getMenuType() == AdminConfig.MENU_TYPE_DICTIONARY) {
				list.addAll(getMenuPrivilegeListByParentMenuId(bean.getMenuId()));
			} else {
				list.add(bean.getMenuPrivilege());
			}
		}

		return list;
	}

	public PageResultBean<AdminMenuInfoBean> getAdminMenuInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		AdminMenuInfoDao dao = new AdminMenuInfoDao();
		return dao.getAdminMenuInfoPageListByMap(condMap, pageBean);
	}

	public boolean isDuplicateMenuCode(int menuId, String menuCode) {
		AdminMenuInfoBean bean = getAdminMenuInfoByMenuCode(menuCode);
		if (bean == null) {
			return false;
		} else {
			if (menuId > 0 && menuId == bean.getMenuId()) {
				return false;
			}
		}
		return true;
	}
}