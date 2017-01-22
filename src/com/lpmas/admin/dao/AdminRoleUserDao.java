package com.lpmas.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminRoleUserBean;
import com.lpmas.admin.factory.AdminDBFactory;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;

public class AdminRoleUserDao {
	private static Logger log = LoggerFactory.getLogger(AdminRoleUserDao.class);

	public int insertAdminRoleUser(AdminRoleUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_role_user ( role_id, user_id) value( ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getRoleId());
			ps.setInt(2, bean.getUserId());

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

	public int removeAdminRoleUserByKey(int roleId, int userId) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from admin_role_user where role_id = ? and user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, roleId);
			ps.setInt(2, userId);

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

	public int removeAdminRoleUserByUserId(int userId) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from admin_role_user where user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, userId);

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

	public List<AdminRoleUserBean> getAdminRoleUserListByUserId(int userId) {
		List<AdminRoleUserBean> list = new ArrayList<AdminRoleUserBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_role_user where user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, userId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminRoleUserBean bean = new AdminRoleUserBean();
				bean = BeanKit.resultSet2Bean(rs, AdminRoleUserBean.class);
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

	public List<AdminRoleUserBean> getAdminRoleUserListByUserId(int userId, int status) {
		List<AdminRoleUserBean> list = new ArrayList<AdminRoleUserBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_role_user ru, admin_role_info r where r.role_id = ru.role_id and ru.user_id = ? and r.status = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, status);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminRoleUserBean bean = new AdminRoleUserBean();
				bean = BeanKit.resultSet2Bean(rs, AdminRoleUserBean.class);
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

	public List<AdminRoleUserBean> getAdminRoleUserListByRoleId(int roleId) {
		List<AdminRoleUserBean> list = new ArrayList<AdminRoleUserBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_role_user where role_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, roleId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminRoleUserBean bean = new AdminRoleUserBean();
				bean = BeanKit.resultSet2Bean(rs, AdminRoleUserBean.class);
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
}
