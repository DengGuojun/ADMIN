package com.lpmas.declare.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.declare.admin.bean.MajorInfoBean;
import com.lpmas.declare.admin.bean.MajorTypeBean;
import com.lpmas.declare.admin.dao.MajorInfoDao;
import com.lpmas.declare.admin.dao.MajorTypeDao;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;

public class MajorTypeBusiness {
	public int addMajorType(MajorTypeBean bean) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.insertMajorType(bean);
	}

	public int updateMajorType(MajorTypeBean bean) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.updateMajorType(bean);
	}

	public MajorTypeBean getMajorTypeByKey(int majorId) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.getMajorTypeByKey(majorId);
	}

	public PageResultBean<MajorTypeBean> getMajorTypePageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.getMajorTypePageListByMap(condMap, pageBean);
	}

	// 增加专业类型验证
	public ReturnMessageBean verifyAddMajorType(MajorTypeBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getMajorName())) {
			result.setMessage("专业类型名不能为空");
		} else if (isExistsMajorTypeName(bean, Constants.STATUS_VALID)) {
			result.setMessage("已存在相同的专业类型");
		}
		return result;
	}

	public ReturnMessageBean verifyMajorType(MajorTypeBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		MajorTypeDao dao = new MajorTypeDao();
		MajorTypeBean originalBean = dao.getMajorTypeByKey(bean.getMajorId());

		if (!StringKit.isValid(bean.getMajorName())) {
			result.setMessage("专业类型名不能为空");
		} else if (bean.getStatus() == Constants.STATUS_VALID) {
			boolean flag = isExistsMajorTypeName(bean, Constants.STATUS_VALID);
			if (flag) {
				result.setMessage("该专业类型已存在相同的专业");
			}
		} else if (originalBean.getStatus() == Constants.STATUS_VALID && bean.getStatus() == Constants.STATUS_NOT_VALID) {
			MajorInfoBusiness majorInfoBusiness = new MajorInfoBusiness();
			List<MajorInfoBean> majorInfoList = majorInfoBusiness.getMajorInfoListByTypeId(bean.getMajorId());
			if (!majorInfoList.isEmpty()) {
				result.setMessage("该专业类型下包含专业信息，不能设置为无效");
			}
		}
		return result;
	}

	public List<MajorTypeBean> getMajorTypeAllList() {
		MajorTypeDao dao = new MajorTypeDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getMajorTypeListByMap(condMap);
	}

	// 判断是否存在相同的专业信息
	public boolean isExistsMajorTypeName(MajorTypeBean bean, int status) {
		MajorTypeBean existsBean = getMajorTypeByNameAndSatus(bean.getMajorName(), status, bean.getMajorYear(),
				bean.getProvince(), bean.getCity(), bean.getRegion());
		if (existsBean == null) {
			return false;
		} else {
			if (existsBean.getMajorId() == bean.getMajorId()) {
				return false;
			}
		}
		return true;
	}

	// 查询是否存在相同的专业(在同一年份下,同一地方)
	public MajorTypeBean getMajorTypeByNameAndSatus(String majorName, int statusValid, String majorYear, String provice,
			String city, String region) {
		MajorTypeDao dao = new MajorTypeDao();
		return dao.getMajorTypeByNameAndStatus(majorName, statusValid, majorYear, provice, city, region);
	}

}