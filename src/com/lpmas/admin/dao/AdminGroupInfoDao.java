package com.lpmas.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.admin.bean.AdminGroupInfoBean;
import com.lpmas.admin.factory.AdminDBFactory;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;

public class AdminGroupInfoDao {
	private static Logger log = LoggerFactory.getLogger(AdminGroupInfoDao.class);

	public int insertAdminGroupInfo(AdminGroupInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into admin_group_info ( group_name, status, create_time, create_user, memo) value( ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getGroupName());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getCreateUser());
			ps.setString(4, bean.getMemo());

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

	public int updateAdminGroupInfo(AdminGroupInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update admin_group_info set group_name = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where group_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getGroupName());
			ps.setInt(2, bean.getStatus());
			ps.setInt(3, bean.getModifyUser());
			ps.setString(4, bean.getMemo());

			ps.setInt(5, bean.getGroupId());

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

	public AdminGroupInfoBean getAdminGroupInfoByKey(int groupId) {
		AdminGroupInfoBean bean = null;
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_group_info where group_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, groupId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new AdminGroupInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminGroupInfoBean.class);
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

	public List<AdminGroupInfoBean> getAdminGroupInfoAllList() {
		List<AdminGroupInfoBean> list = new ArrayList<AdminGroupInfoBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_group_info";
			PreparedStatement ps = db.getPreparedStatement(sql);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminGroupInfoBean bean = new AdminGroupInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminGroupInfoBean.class);
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

	public List<AdminGroupInfoBean> getAdminGroupInfoListByStatus(int status) {
		List<AdminGroupInfoBean> list = new ArrayList<AdminGroupInfoBean>();
		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_group_info where status = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, status);

			ResultSet rs = db.executePstmtQuery();
			while (rs.next()) {
				AdminGroupInfoBean bean = new AdminGroupInfoBean();
				bean = BeanKit.resultSet2Bean(rs, AdminGroupInfoBean.class);
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

	public PageResultBean<AdminGroupInfoBean> getAdminGroupInfoPageListByMap(HashMap<String, String> condMap,
			PageBean pageBean) {
		PageResultBean<AdminGroupInfoBean> result = new PageResultBean<AdminGroupInfoBean>();

		DBFactory dbFactory = new AdminDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from admin_group_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String groupName = condMap.get("groupName");
			if (StringKit.isValid(groupName)) {
				condList.add("group_name like ?");
				paramList.add("%" + groupName + "%");
			}
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by group_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, AdminGroupInfoBean.class, pageBean,
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
