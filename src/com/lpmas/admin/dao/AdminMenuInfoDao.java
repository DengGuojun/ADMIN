package com.lpmas.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminMenuInfoBean;
import com.lpmas.admin.factory.AdminDBFactory;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;

public class AdminMenuInfoDao {
	private static Logger log = LoggerFactory.getLogger(AdminMenuInfoDao.class);

	public int insertAdminMenuInfo(AdminMenuInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_menu_info ( menu_name, menu_code, parent_menu_id, menu_type, menu_url, menu_privilege, menu_desc, priority, is_display, status, create_time, create_user, memo)"
					+ " value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getMenuName());
			ps.setString(2, bean.getMenuCode());
			ps.setInt(3, bean.getParentMenuId());
			ps.setInt(4, bean.getMenuType());
			ps.setString(5, bean.getMenuUrl());
			ps.setString(6, bean.getMenuPrivilege());
			ps.setString(7, bean.getMenuDesc());
			ps.setInt(8, bean.getPriority());
			ps.setInt(9, bean.getIsDisplay());
			ps.setInt(10, bean.getStatus());
			ps.setInt(11, bean.getCreateUser());
			ps.setString(12, bean.getMemo());

			result = db.executePstmtInsert();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public int updateAdminMenuInfo(AdminMenuInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update admin_menu_info set menu_name = ?, menu_code = ?, parent_menu_id = ?, menu_type = ?, menu_url = ?, menu_privilege = ?,"
					+ " menu_desc = ?, priority = ?, is_display = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where menu_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getMenuName());
			ps.setString(2, bean.getMenuCode());
			ps.setInt(3, bean.getParentMenuId());
			ps.setInt(4, bean.getMenuType());
			ps.setString(5, bean.getMenuUrl());
			ps.setString(6, bean.getMenuPrivilege());
			ps.setString(7, bean.getMenuDesc());
			ps.setInt(8, bean.getPriority());
			ps.setInt(9, bean.getIsDisplay());
			ps.setInt(10, bean.getStatus());
			ps.setInt(11, bean.getModifyUser());
			ps.setString(12, bean.getMemo());

			ps.setInt(13, bean.getMenuId());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e);
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}

	public AdminMenuInfoBean getAdminMenuInfoByKey(int menuId) {
		AdminMenuInfoBean bean = null;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_menu_info where menu_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, menuId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new AdminMenuInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminMenuInfoBean.class);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
			bean = null;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return bean;
	}

	public AdminMenuInfoBean getAdminMenuInfoByMenuCode(String menuCode) {
		AdminMenuInfoBean bean = null;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_menu_info where menu_code = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, menuCode);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new AdminMenuInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminMenuInfoBean.class);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
			bean = null;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return bean;
	}

	public List<AdminMenuInfoBean> getAdminMenuInfoListByParentMenuId(int parentMenuId) {
		List<AdminMenuInfoBean> list = new ArrayList<AdminMenuInfoBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_menu_info where parent_menu_id = ? order by priority asc";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, parentMenuId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminMenuInfoBean bean = new AdminMenuInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminMenuInfoBean.class);
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e.toString());
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle.toString());
			}
		}
		return list;
	}

	public List<AdminMenuInfoBean> getAdminMenuInfoListByParentMenuId(int parentMenuId, int menuType) {
		List<AdminMenuInfoBean> list = new ArrayList<AdminMenuInfoBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_menu_info where parent_menu_id = ? and menu_type = ? order by priority asc";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, parentMenuId);
			ps.setInt(2, menuType);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminMenuInfoBean bean = new AdminMenuInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminMenuInfoBean.class);
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return list;
	}

	public List<AdminMenuInfoBean> getAdminMenuInfoListByParentMenuCode(String menuCode) {
		List<AdminMenuInfoBean> list = new ArrayList<AdminMenuInfoBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_menu_info where parent_menu_id = (select menu_id from admin_menu_info where menu_code = ?) order by priority asc";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, menuCode);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminMenuInfoBean bean = new AdminMenuInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminMenuInfoBean.class);
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return list;
	}

	public PageResultBean<AdminMenuInfoBean> getAdminMenuInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<AdminMenuInfoBean> result = new PageResultBean<AdminMenuInfoBean>();

		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_menu_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String menuCode = condMap.get("menuCode");
			if (StringKit.isValid(menuCode)) {
				condList.add("menu_code like ?");
				paramList.add(menuCode + "%");
			}
			String menuName = condMap.get("menuName");
			if (StringKit.isValid(menuName)) {
				condList.add("menu_name like ?");
				paramList.add("%" + menuName + "%");
			}
			String menuType = condMap.get("menuType");
			if (StringKit.isValid(menuType)) {
				condList.add("menu_type = ?");
				paramList.add(menuType);
			}
			String orderQuery = "order by menu_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, AdminMenuInfoBean.class, pageBean,
					db);
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return result;
	}
}
