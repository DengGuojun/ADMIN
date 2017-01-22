package com.lpmas.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminGroupUserBean;
import com.lpmas.admin.factory.AdminDBFactory;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;

public class AdminGroupUserDao {
	private static Logger log = LoggerFactory.getLogger(AdminGroupUserDao.class);

	public int insertAdminGroupUser(AdminGroupUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_group_user ( group_id, user_id) value( ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getGroupId());
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

	public int removeAdminGroupUserByKey(int groupId, int userId) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from admin_group_user where group_id = ? and user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, groupId);
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

	public int removeAdminGroupUserByUserId(int userId) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from admin_group_user where user_id = ?";
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

	public List<AdminGroupUserBean> getAdminGroupUserListByUserId(int userId) {
		List<AdminGroupUserBean> list = new ArrayList<AdminGroupUserBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_group_user where user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, userId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminGroupUserBean bean = new AdminGroupUserBean();
				bean = BeanKit.resultSet2Bean(rs, AdminGroupUserBean.class);
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

	public List<AdminGroupUserBean> getAdminGroupUserListByGroupId(int groupId) {
		List<AdminGroupUserBean> list = new ArrayList<AdminGroupUserBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_group_user where group_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, groupId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminGroupUserBean bean = new AdminGroupUserBean();
				bean = BeanKit.resultSet2Bean(rs, AdminGroupUserBean.class);
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

	public List<AdminGroupUserBean> getAdminGroupUserListByUserId(int userId, int status) {
		List<AdminGroupUserBean> list = new ArrayList<AdminGroupUserBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select gu.* from admin_group_user gu,admin_group_info g where g.group_id = gu.group_id and g.status = ? and gu.user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, status);
			ps.setInt(2, userId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminGroupUserBean bean = new AdminGroupUserBean();
				bean = BeanKit.resultSet2Bean(rs, AdminGroupUserBean.class);
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
