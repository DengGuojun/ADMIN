package com.lpmas.declare.admin.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.declare.admin.bean.AdminOperationInfoBean;
import com.lpmas.declare.admin.factory.DeclareDBFactory;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;


public class AdminOperationInfoDao {
	private static Logger log = LoggerFactory.getLogger(AdminOperationInfoDao.class);

	public int insertAdminOperationInfo(AdminOperationInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_operation_info ( operation_name, operation_code) value( ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getOperationName());
			ps.setString(2, bean.getOperationCode());

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

	public int updateAdminOperationInfo(AdminOperationInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update admin_operation_info set operation_name = ?, operation_code = ? where operation_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getOperationName());
			ps.setString(2, bean.getOperationCode());

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

	public AdminOperationInfoBean getAdminOperationInfoByKey(int operationId) {
		AdminOperationInfoBean bean = null;
		DBFactory dbFactory = new DeclareDBFactory();
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

	public PageResultBean<AdminOperationInfoBean> getAdminOperationInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<AdminOperationInfoBean> result = new PageResultBean<AdminOperationInfoBean>();
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_operation_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by operation_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, AdminOperationInfoBean.class,
					pageBean, db);
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
	
	public List<AdminOperationInfoBean> getAdminOperationInfoAllList() {
		List<AdminOperationInfoBean> list = new ArrayList<AdminOperationInfoBean>();
		DBFactory dbFactory = new DeclareDBFactory();
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
