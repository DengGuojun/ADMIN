package com.lpmas.declare.admin.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpmas.declare.admin.bean.GovernmentOrganizationInfoBean;
import com.lpmas.declare.admin.factory.DeclareDBFactory;
import com.lpmas.framework.db.DBExecutor;
import com.lpmas.framework.db.DBFactory;
import com.lpmas.framework.db.DBObject;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.BeanKit;
import com.lpmas.framework.util.StringKit;

public class GovernmentOrganizationInfoDao {
	private static Logger log = LoggerFactory.getLogger(GovernmentOrganizationInfoDao.class);

	public int insertGovernmentOrganizationInfo(GovernmentOrganizationInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "insert into government_organization_info ( organization_name, department, responsible_person_name, responsible_person_mobile, operator_name, operator_mobile, fax, address, zip_code, is_demonstration_area, status, create_time, create_user, memo) value( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getOrganizationName());
			ps.setString(2, bean.getDepartment());
			ps.setString(3, bean.getResponsiblePersonName());
			ps.setString(4, bean.getResponsiblePersonMobile());
			ps.setString(5, bean.getOperatorName());
			ps.setString(6, bean.getOperatorMobile());
			ps.setString(7, bean.getFax());
			ps.setString(8, bean.getAddress());
			ps.setString(9, bean.getZipCode());
			ps.setInt(10, bean.getIsDemonstrationArea());
			ps.setInt(11, bean.getStatus());
			ps.setInt(12, bean.getCreateUser());
			ps.setString(13, bean.getMemo());

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

	public int updateGovernmentOrganizationInfo(GovernmentOrganizationInfoBean bean) {
		int result = -1;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectW();
			String sql = "update government_organization_info set organization_name = ?, department = ?, responsible_person_name = ?, responsible_person_mobile = ?, operator_name = ?, operator_mobile = ?, fax = ?, address = ?, zip_code = ?, is_demonstration_area = ?, status = ?, modify_time = now(), modify_user = ?, memo = ? where organization_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setString(1, bean.getOrganizationName());
			ps.setString(2, bean.getDepartment());
			ps.setString(3, bean.getResponsiblePersonName());
			ps.setString(4, bean.getResponsiblePersonMobile());
			ps.setString(5, bean.getOperatorName());
			ps.setString(6, bean.getOperatorMobile());
			ps.setString(7, bean.getFax());
			ps.setString(8, bean.getAddress());
			ps.setString(9, bean.getZipCode());
			ps.setInt(10, bean.getIsDemonstrationArea());
			ps.setInt(11, bean.getStatus());
			ps.setInt(12, bean.getModifyUser());
			ps.setString(13, bean.getMemo());

			ps.setInt(14, bean.getOrganizationId());

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

	public GovernmentOrganizationInfoBean getGovernmentOrganizationInfoByKey(int organizationId) {
		GovernmentOrganizationInfoBean bean = null;
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from government_organization_info where organization_id = ?";
			PreparedStatement ps = db.getPreparedStatement(sql);
			ps.setInt(1, organizationId);

			ResultSet rs = db.executePstmtQuery();
			if (rs.next()) {
				bean = new GovernmentOrganizationInfoBean();
				bean = BeanKit.resultSet2Bean(rs, GovernmentOrganizationInfoBean.class);
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

	public PageResultBean<GovernmentOrganizationInfoBean> getGovernmentOrganizationInfoPageListByMap(
			HashMap<String, String> condMap, PageBean pageBean) {
		PageResultBean<GovernmentOrganizationInfoBean> result = new PageResultBean<GovernmentOrganizationInfoBean>();
		DBFactory dbFactory = new DeclareDBFactory();
		DBObject db = null;
		try {
			db = dbFactory.getDBObjectR();
			String sql = "select * from government_organization_info";

			List<String> condList = new ArrayList<String>();
			List<String> paramList = new ArrayList<String>();
			// 条件处理
			
			String organizationId = condMap.get("organizationId");
			if (StringKit.isValid(organizationId)) {
				condList.add("organization_id = ?");
				paramList.add(organizationId);
			}
			
			String status = condMap.get("status");
			if (StringKit.isValid(status)) {
				condList.add("status = ?");
				paramList.add(status);
			}
			
			String organizationName = condMap.get("organizationName");
			if (StringKit.isValid(organizationName)) {
				condList.add("organization_name like ?");
				paramList.add("%" + organizationName + "%");
			}
			
			
			String orderQuery = "order by organization_id desc";
			String orderBy = condMap.get("orderBy");
			if (StringKit.isValid(orderBy)) {
				orderQuery = " order by " + orderBy;
			}

			DBExecutor dbExecutor = dbFactory.getDBExecutor();
			result = dbExecutor.getPageResult(sql, orderQuery, condList, paramList,
					GovernmentOrganizationInfoBean.class, pageBean, db);
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
