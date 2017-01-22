package com.lpmas.declare.admin.business;

import java.util.HashMap;
import java.util.List;

import com.lpmas.declare.admin.bean.MajorInfoBean;
import com.lpmas.declare.admin.dao.MajorInfoDao;
import com.lpmas.framework.config.Constants;
import com.lpmas.framework.page.PageBean;
import com.lpmas.framework.page.PageResultBean;
import com.lpmas.framework.util.StringKit;
import com.lpmas.framework.web.ReturnMessageBean;

public class MajorInfoBusiness {
	public int addMajorInfo(MajorInfoBean bean) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.insertMajorInfo(bean);
	}

	public int updateMajorInfo(MajorInfoBean bean) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.updateMajorInfo(bean);
	}

	public MajorInfoBean getMajorInfoByKey(int majorId) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.getMajorInfoByKey(majorId);
	}

	public PageResultBean<MajorInfoBean> getMajorInfoPageListByMap(HashMap<String, String> condMap, PageBean pageBean) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.getMajorInfoPageListByMap(condMap, pageBean);
	}

	public List<MajorInfoBean> getMajorInfoListByTypeId(int typeId) {
		MajorInfoDao dao = new MajorInfoDao();
		HashMap<String, String> condMap = new HashMap<String, String>();
		condMap.put("typeId", String.valueOf(typeId));
		condMap.put("status", String.valueOf(Constants.STATUS_VALID));
		return dao.getMajorInfoListByMap(condMap);
	}

	// 增加时验证的信息
	public ReturnMessageBean verifyAddMajorInfo(MajorInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		if (!StringKit.isValid(bean.getMajorName())) {
			result.setMessage("专业名称不能为空");
		} else if (isExistsMajorInfoName(bean, Constants.STATUS_VALID)) {
			result.setMessage("该专业类型已存在相同的专业");
		}
		return result;
	}

	// 修改时验证的信息
	public ReturnMessageBean verifyModifyMajorInfo(MajorInfoBean bean) {
		ReturnMessageBean result = new ReturnMessageBean();
		MajorInfoDao dao = new MajorInfoDao();
		MajorInfoBean originalBean = dao.getMajorInfoByKey(bean.getMajorId()); // 获取原始的bean
		if (!StringKit.isValid(bean.getMajorName())) {
			result.setMessage("专业名称不能为空");
		} else if (bean.getStatus() == Constants.STATUS_VALID) {
			// 此时是将删除状态的专业转化为存在状态的专业 或者修改专业分类
			boolean flag = isExistsMajorInfoName(bean, Constants.STATUS_VALID);
			// 判断是否存在专业分类相同而且名字也相同的信息
			if (flag) {
				result.setMessage("该专业类型已存在相同的专业");
			}
		} else if (bean.getStatus() == Constants.STATUS_NOT_VALID
				&& originalBean.getStatus() == Constants.STATUS_VALID) {
			// 删除节目分类
			// 是否有培训班引用 有的话无法删除
		}
		return result;
	}

	// 判断是否存在相同的专业信息
	public boolean isExistsMajorInfoName(MajorInfoBean bean, int status) {
		MajorInfoBean existsBean = getMajorInfoByNameAndSatus(bean.getMajorName(), status, bean.getTypeId());
		if (existsBean == null) {
			return false;
		} else {
			if (existsBean.getMajorId() == bean.getMajorId()) {
				return false;
			}
		}
		return true;
	}

	// 查询是否存在相同的专业(在同一专业类型下)
	public MajorInfoBean getMajorInfoByNameAndSatus(String majorName, int statusValid, int typeId) {
		MajorInfoDao dao = new MajorInfoDao();
		return dao.getMajorInfoByNameAndSatus(majorName, statusValid, typeId);
	}

}