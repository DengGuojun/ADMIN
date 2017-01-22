package com.lpmas.declare.admin.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.declare.admin.bean.MajorInfoBean;
import com.lpmas.declare.admin.bean.MajorTypeBean;
import com.lpmas.declare.admin.factory.DeclareDBFactory;
import com.lpmas.declare.bean.JobTypeBean;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;

public class MajorTypeDao {
	private static Logger log = LoggerFactory.getLogger(MajorTypeDao.class);

	public int insertMajorType(MajorTypeBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into major_type ( major_name, major_level, major_year, province, city, region, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getMajorName());
			ps.setString(2, bean.getMajorLevel());
			ps.setString(3, bean.getMajorYear());
			ps.setString(4, bean.getProvince());
			ps.setString(5, bean.getCity());
			ps.setString(6, bean.getRegion());
			ps.setInt(7, bean.getStatus());
			ps.setInt(8, bean.getCreateUser());
			ps.setString(9, bean.getMemo());

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

	public int updateMajorType(MajorTypeBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update major_type set major_name = ?, major_level = ?, major_year = ?, province = ?, city = ?, region = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where major_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getMajorName());
			ps.setString(2, bean.getMajorLevel());
			ps.setString(3, bean.getMajorYear());
			ps.setString(4, bean.getProvince());
			ps.setString(5, bean.getCity());
			ps.setString(6, bean.getRegion());
			ps.setInt(7, bean.getStatus());
			ps.setInt(8, bean.getModifyUser());
			ps.setString(9, bean.getMemo());

			ps.setInt(10, bean.getMajorId());

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

	public MajorTypeBean getMajorTypeByKey(int majorId) {
		MajorTypeBean bean = null;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_type where major_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, majorId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new MajorTypeBean();
				bean = BeanKit.resultSet2Bean(rs, MajorTypeBean.class);
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

	public PageResultBean<MajorTypeBean> getMajorTypePageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<MajorTypeBean> result = new PageResultBean<MajorTypeBean>();
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_type";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理

			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}

			String majorName = condMap.get("majorName");
			if (StringKit.isValid(majorName)) {
				condList.add("major_name like ? ");
				paramList.add("%" + majorName + "%");
			}

			String orderQuery = "order by major_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList, MajorTypeBean.class, pageBean, db);
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

	public List<MajorTypeBean> getMajorTypeListByMap(HashMap<String, String> condMap) {
		List<MajorTypeBean> result = new ArrayList<MajorTypeBean>();
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_type";
			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			String orderQuery = "order by major_id desc";

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getRecordListResult(sql, orderQuery, condList, paramList, MajorTypeBean.class, db);
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

	public MajorTypeBean getMajorTypeByNameAndStatus(String majorName, int statusValid,String majorYear,String province,String city,String region) {
		MajorTypeBean bean = null;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from major_type where major_name = ? and status = ? major_year = ? and city = ? and province = ? and region = ? " ;
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, majorName);
			ps.setInt(2, statusValid);
			ps.setString(3,majorYear);
			ps.setString(4, province);
			ps.setString(5, city);
			ps.setString(6, region);
			
			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new MajorTypeBean();
				bean = BeanKit.resultSet2Bean(rs, MajorTypeBean.class);
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
}
