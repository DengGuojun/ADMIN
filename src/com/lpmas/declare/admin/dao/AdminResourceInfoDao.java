package com.lpmas.declare.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.declare.admin.bean.AdminResourceInfoBean;
import com.lpmas.declare.admin.factory.DeclareDBFactory;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;

public class AdminResourceInfoDao {
	private static Logger log = LoggerFactory.getLogger(AdminResourceInfoDao.class);

	public int insertAdminResourceInfo(AdminResourceInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_resource_info ( resource_name, resource_code, type_id, status, memo) value( ?, ?, ?, ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getResourceName());
			ps.setString(2, bean.getResourceCode());
			ps.setInt(3, bean.getTypeId());
			ps.setInt(4, bean.getStatus());
			ps.setString(5, bean.getMemo());

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

	public int updateAdminResourceInfo(AdminResourceInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update admin_resource_info set resource_name = ?, resource_code = ?, type_id = ?, status = ?, memo = ? where resource_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getResourceName());
			ps.setString(2, bean.getResourceCode());
			ps.setInt(3, bean.getTypeId());
			ps.setInt(4, bean.getStatus());
			ps.setString(5, bean.getMemo());

			ps.setInt(6, bean.getResourceId());

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

	public AdminResourceInfoBean getAdminResourceInfoByKey(int resourceId) {
		AdminResourceInfoBean bean = null;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_resource_info where resource_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, resourceId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new AdminResourceInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminResourceInfoBean.class);
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

	public AdminResourceInfoBean getAdminResourceInfoByCode(String resourceCode) {
		AdminResourceInfoBean bean = null;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_resource_info where resource_code = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, resourceCode);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new AdminResourceInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminResourceInfoBean.class);
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
	
	public List<AdminResourceInfoBean> getAdminResourceInfoListByType(int typeId) {
		List<AdminResourceInfoBean> list = new ArrayList<AdminResourceInfoBean>();
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_resource_info where type_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, typeId);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminResourceInfoBean bean = new AdminResourceInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminResourceInfoBean.class);
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

	public PageResultBean<AdminResourceInfoBean> getAdminResourceInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<AdminResourceInfoBean> result = new PageResultBean<AdminResourceInfoBean>();
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_resource_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String orderQuery = "order by resource_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, AdminResourceInfoBean.class,
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

}
