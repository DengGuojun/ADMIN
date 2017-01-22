package com.lpmas.declare.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.declare.admin.bean.AdminPrivilegeInfoBean;
import com.lpmas.declare.admin.factory.DeclareDBFactory;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.db.SqlKit;
import com.lpmas.framework.util.BeanKit;

public class AdminPrivilegeInfoDao {
	private static Logger log = LoggerFactory.getLogger(AdminPrivilegeInfoDao.class);

	public int insertAdminPrivilegeInfo(AdminPrivilegeInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_privilege_info ( role_id, resource_id, operation_id) value( ?, ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, bean.getRoleId());
			ps.setInt(2, bean.getResourceId());
			ps.setInt(3, bean.getOperationId());

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

	public int deleteAdminPrivilegeInfoByKey(int roleId, int resourceId, int operationId) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from admin_privilege_info where role_id = ? and resource_id = ? and operation_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, roleId);
			ps.setInt(2, resourceId);
			ps.setInt(3, operationId);

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

	public int deleteAdminPrivilegeInfoByRoleId(int roleId) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "delete from admin_privilege_info where role_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, roleId);

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

	public AdminPrivilegeInfoBean getAdminPrivilegeInfoByKey(int roleId, int resourceId, int operationId) {
		AdminPrivilegeInfoBean bean = null;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_privilege_info where role_id = ? and resource_id = ? and operation_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, roleId);
			ps.setInt(2, resourceId);
			ps.setInt(3, operationId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new AdminPrivilegeInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminPrivilegeInfoBean.class);
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

	public List<AdminPrivilegeInfoBean> getAdminPrivilegeInfoListByRoleId(int roleId) {
		List<AdminPrivilegeInfoBean> list = new ArrayList<AdminPrivilegeInfoBean>();
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_privilege_info where role_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, roleId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminPrivilegeInfoBean bean = new AdminPrivilegeInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminPrivilegeInfoBean.class);
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

	public List<AdminPrivilegeInfoBean> getAdminPrivilegeInfoListByRoleIdSet(HashSet<Integer> roleSet) {
		List<AdminPrivilegeInfoBean> list = new ArrayList<AdminPrivilegeInfoBean>();
		if (roleSet.isEmpty()) {
			return list;
		}
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_privilege_info where role_id in ("
					+ SqlKit.getInQueryPreparedStmt(roleSet.size()) + ")";
			PreparedStatement ps = db.getPreparedStatement(sql);
			int count = 0;
			for (Integer param : roleSet) {
				count = count + 1;
				ps.setInt(count, param);
			}

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminPrivilegeInfoBean bean = new AdminPrivilegeInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminPrivilegeInfoBean.class);
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
