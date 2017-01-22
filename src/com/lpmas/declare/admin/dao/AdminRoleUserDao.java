package com.lpmas.declare.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.declare.admin.bean.AdminRoleUserBean;
import com.lpmas.declare.admin.factory.DeclareDBFactory;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;

public class AdminRoleUserDao {
	private static Logger log = LoggerFactory.getLogger(AdminRoleUserDao.class);

	public int insertAdminRoleUser(AdminRoleUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_role_user ( role_id, user_id, status, create_time, create_user, memo) value( ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getRoleId());
			ps.setInt(2, bean.getUserId());
			ps.setInt(3, bean.getStatus());
			ps.setInt(4, bean.getCreateUser());
			ps.setString(5, bean.getMemo());

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

	public int updateAdminRoleUser(AdminRoleUserBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update admin_role_user set status = ?, modify_time = now(), modify_user = ?, memo = ? where role_id = ? and user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getStatus());
			ps.setInt(2, bean.getModifyUser());
			ps.setString(3, bean.getMemo());

			ps.setInt(4, bean.getRoleId());
			ps.setInt(5, bean.getUserId());

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

	public AdminRoleUserBean getAdminRoleUserByKey(int roleId, int userId) {
		AdminRoleUserBean bean = null;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_role_user where role_id = ? and user_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, roleId);
			ps.setInt(2, userId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new AdminRoleUserBean();
				bean = BeanKit.resultSet2Bean(rs, AdminRoleUserBean.class);
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

	public PageResultBean<AdminRoleUserBean> getAdminRoleUserPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<AdminRoleUserBean> result = new PageResultBean<AdminRoleUserBean>();
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_role_user";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by role_id,user_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, AdminRoleUserBean.class, pageBean,
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
	
	public List<AdminRoleUserBean> getAdminRoleUserListByUserId(int userId) {
		List<AdminRoleUserBean> list = new ArrayList<AdminRoleUserBean>();
		DBFactory dbFactory = new DeclareDBFactory();
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

}
