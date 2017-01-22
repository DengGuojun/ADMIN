package com.lpmas.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminOperationInfoBean;
import com.lpmas.admin.factory.AdminDBFactory;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.util.BeanKit;

public class AdminOperationInfoDao {
	private static Logger log = LoggerFactory.getLogger(AdminOperationInfoDao.class);

	public int insertAdminOperationInfo(AdminOperationInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_operation_info ( operation_name, operation_code, memo) value( ?, ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getOperationName());
			ps.setString(2, bean.getOperationCode());
			ps.setString(3, bean.getMemo());

			result = db.executePstmtInsert();
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

	public int updateAdminOperationInfo(AdminOperationInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update admin_operation_info set operation_name = ?, operation_code = ?, memo = ? where operation_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getOperationName());
			ps.setString(2, bean.getOperationCode());
			ps.setString(3, bean.getMemo());

			ps.setInt(4, bean.getOperationId());

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

	public AdminOperationInfoBean getAdminOperationInfoByKey(int operationId) {
		AdminOperationInfoBean bean = null;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_operation_info where operation_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, operationId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new AdminOperationInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminOperationInfoBean.class);
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

	public List<AdminOperationInfoBean> getAdminOperationInfoAllList() {
		List<AdminOperationInfoBean> list = new ArrayList<AdminOperationInfoBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_operation_info";
			PreparedStatement ps = db.getPreparedStatement(sql);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminOperationInfoBean bean = new AdminOperationInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminOperationInfoBean.class);
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
