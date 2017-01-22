package com.lpmas.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminPrivilegeDefineBean;
import com.lpmas.admin.factory.AdminDBFactory;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;

public class AdminPrivilegeDefineDao {
	private static Logger log = LoggerFactory.getLogger(AdminPrivilegeDefineDao.class);

	public int insertAdminPrivilegeDefine(AdminPrivilegeDefineBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_privilege_define ( resource_id, operation_id, privilege_desc) value( ?, ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getResourceId());
			ps.setInt(2, bean.getOperationId());
			ps.setString(3, bean.getPrivilegeDesc());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e.toString());
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle.toString());
			}
		}
		return result;
	}

	public int updateAdminPrivilegeDefine(AdminPrivilegeDefineBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update admin_privilege_define set privilege_desc = ? where resource_id = ? and operation_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getPrivilegeDesc());

			ps.setInt(2, bean.getResourceId());
			ps.setInt(3, bean.getOperationId());

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e.toString());
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle.toString());
			}
		}
		return result;
	}

	public int removeAdminPrivilegeDefineByKey(int resourceId, int operationId) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from admin_privilege_define where resource_id = ? and operation_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, resourceId);
			ps.setInt(2, operationId);

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e.toString());
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle.toString());
			}
		}
		return result;
	}

	public int removeAdminPrivilegeDefineByResourceId(int resourceId) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from admin_privilege_define where resource_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, resourceId);

			result = db.executePstmtUpdate();
		} catch (Exception e) {
			log.error("", e.toString());
			result = -1;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle.toString());
			}
		}
		return result;
	}

	public AdminPrivilegeDefineBean getAdminPrivilegeDefineByKey(int resourceId, int operationId) {
		AdminPrivilegeDefineBean bean = null;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_privilege_define where resource_id = ? and operation_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, resourceId);
			ps.setInt(2, operationId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new AdminPrivilegeDefineBean();
				bean = BeanKit.resultSet2Bean(rs, AdminPrivilegeDefineBean.class);
			}
			rs.close();
		} catch (Exception e) {
			log.error("", e.toString());
			bean = null;
		} finally {
			try {
				db.close();
			} catch (SQLException sqle) {
				log.error("", sqle.toString());
			}
		}
		return bean;
	}

	public List<AdminPrivilegeDefineBean> getAdminPrivilegeDefineListByResourceId(int resourceId) {
		List<AdminPrivilegeDefineBean> list = new ArrayList<AdminPrivilegeDefineBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_privilege_define where resource_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, resourceId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminPrivilegeDefineBean bean = new AdminPrivilegeDefineBean();
				bean = BeanKit.resultSet2Bean(rs, AdminPrivilegeDefineBean.class);
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
}
