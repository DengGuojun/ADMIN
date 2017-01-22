package com.lpmas.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminRoleGroupBean;
import com.lpmas.admin.factory.AdminDBFactory;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;

public class AdminRoleGroupDao {
	private static Logger log = LoggerFactory.getLogger(AdminRoleGroupDao.class);

	public int insertAdminRoleGroup(AdminRoleGroupBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_role_group ( role_id, group_id) value( ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getRoleId());
			ps.setInt(2, bean.getGroupId());

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

	public int removeAdminRoleGroupByKey(int roleId, int groupId) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from admin_role_group where role_id = ? and group_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, roleId);
			ps.setInt(2, groupId);

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

	public int removeAdminRoleGroupByGroupId(int groupId) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from admin_role_group where group_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, groupId);

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

	public List<AdminRoleGroupBean> getAdminRoleGroupListByRoleId(int roleId) {
		List<AdminRoleGroupBean> list = new ArrayList<AdminRoleGroupBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_role_group where role_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, roleId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminRoleGroupBean bean = new AdminRoleGroupBean();
				bean = BeanKit.resultSet2Bean(rs, AdminRoleGroupBean.class);
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

	public List<AdminRoleGroupBean> getAdminRoleGroupListByGroupId(int groupId) {
		List<AdminRoleGroupBean> list = new ArrayList<AdminRoleGroupBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_role_group where group_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, groupId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminRoleGroupBean bean = new AdminRoleGroupBean();
				bean = BeanKit.resultSet2Bean(rs, AdminRoleGroupBean.class);
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

	public List<AdminRoleGroupBean> getAdminRoleGroupListByUserId(int userId, int status) {
		List<AdminRoleGroupBean> list = new ArrayList<AdminRoleGroupBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select rg.* from admin_role_group rg,admin_role_info r where r.role_id = rg.role_id and group_id in"
					+ " (select group_id from admin_group_user where user_id = ?) and status = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, status);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminRoleGroupBean bean = new AdminRoleGroupBean();
				bean = BeanKit.resultSet2Bean(rs, AdminRoleGroupBean.class);
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
